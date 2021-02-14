<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%
	List<Member> list = (List<Member>)request.getAttribute("list");
%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<!-- 관리자용 admin.css link -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin.css" />
<style>
	div#search-container {margin:0 0 10px 0; padding:3px; background-color: rgba(0, 188, 212, 0.3);}
	div#search-memberId {display: inline-block;}
	div#search-memberName{display:none;}
	div#search-gender{display:none;}
</style>
<script>
$(function(){
	
	/**
	* 검색타입 변경 이벤트핸들러
	*/
	$("#searchType").change(function(){
		var type = $(this).val();//memberId | memberName | gender
		var $divSearchTypes = $(".search-type");
		//console.log(type);
		$divSearchTypes
		.hide()
		.filter("#search-" + type)
		.css('display', 'inline-block');
	});
	
	
	
	/**
	* 회원권한 변경 이벤트 핸들러
	*/
	$("select.member-role").change(function(){
		var memberRole = $(this).val();
		var memberId = $(this).data("memberId");
		//console.log(memberId, memberRole);
		
		var bool = confirm("[" + memberId + "]회원의 권한을 [" 
						 + (memberRole == "U" ? "일반" : "관리자" ) 
						 + "](으)로 변경하시겠습니까?");
		if(!bool) 
			return;
		
		//GET : 요청 처리이후 DB에 변경내역이 없는 경우. 조회|검색
		//location.href = "/mvc/admin/updateMemberRole?memberId=s&memberRole=U";
		//POST : 요청 처리이후 DB에 변경내역이 있는 경우. 추가|수정|삭제
		var $frm = $("#memberRoleUpdateFrm");
		$frm.find("[name=memberId]").val(memberId);
		$frm.find("[name=memberRole]").val(memberRole);
		$frm.submit();
	});
});

function search() {
	
	//선택된 옵션값
	var $selectedOption = $("#searchType option:selected").val();
	
	//선택된 옵션에서 입력된 값
	if($selectedOption == "memberId") {
		var $inputId = $("#search-memberId [name=searchKeyword]").val();
		//console.log($inputId);

        var tag1 = "memberId";
        $("tbody").children('tr').css('display', 'none');
        $("[name="+tag1+"]:contains("+$inputId+")").closest('tr').css('display','');
	}
	
	if($selectedOption == "memberName") {
		var $inputName = $("#search-memberName [name=searchKeyword]").val();
		//console.log($inputName);
		
        var tag2 = "memberName";
        $("tbody").children('tr').css('display', 'none');
        $("[name="+tag2+"]:contains("+$inputName+")").closest('tr').css('display','');
	}
	
	if($selectedOption == "gender") {
		var $inputGender = $("#search-gender [name=searchKeyword]:checked").val();
		//console.log($inputGender);
		
		if($inputGender == "M") {
			$inputGender = "남";
		} else {
			$inputGender = "여";
		}
		
		//:contains()는 특정 문자열을 포함한 요소를 선택하는 선택자
		//예를 들어, contains("ab") 작성 시 ab라는 문자열을 포함한 요소를 선택해준다.
        var tag3 = "memberGender";
        $("tbody").children('tr').css('display', 'none');
        $("[name="+tag3+"]:contains("+$inputGender+")").closest('tr').css('display','');
	}
}


</script>
<form 
	action="<%= request.getContextPath() %>/admin/updateMemberRole" 
	id="memberRoleUpdateFrm"
	method="POST">
	<input type="hidden" name="memberId" />
	<input type="hidden" name="memberRole" />
</form>
<section id="memberList-container">
	<h2>회원관리</h2>
	
	<div id="search-container">
        검색타입 : 
        <select id="searchType">
            <option value="memberId">아이디</option>		
            <option value="memberName">회원명</option>
            <option value="gender">성별</option>
        </select>
        <div id="search-memberId" class="search-type">
            <form>
                <input type="hidden" name="searchType" value="memberId"/>
                <input type="text" name="searchKeyword"  size="25" placeholder="검색할 아이디를 입력하세요."/>
                <button type="button" onclick="search();">검색</button>			
            </form>	
        </div>
        <div id="search-memberName" class="search-type">
            <form>
                <input type="hidden" name="searchType" value="memberName"/>
                <input type="text" name="searchKeyword" size="25" placeholder="검색할 이름을 입력하세요."/>
                <button type="button" onclick="search();">검색</button>			
            </form>	
        </div>
        <div id="search-gender" class="search-type">
            <form>
                <input type="hidden" name="searchType" value="gender"/>
                <input type="radio" name="searchKeyword" value="M" checked> 남
                <input type="radio" name="searchKeyword" value="F"> 여
                <button type="button" onclick="search();">검색</button>
            </form>
        </div>
    </div>
	
	
	<table id="tbl-member">
		<thead>
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>회원권한</th>
				<th>성별</th>
				<th>생년월일</th>
				<th>이메일</th>
				<th>전화번호</th>
				<th>주소</th>
				<th>취미</th>
				<th>가입일</th>
			</tr>
		</thead>
		<tbody>
		<% if(list == null || list.isEmpty()) { %>
			<%--조회된 결과가 없는 경우 --%>
			<tr>
				<td colspan="10" style="text-align:center;">
					조회된 결과가 없습니다.
				</td>
			</tr>
		
		<% } else { 
			for(Member m : list) { %>
			<%--조회된 결과가 있는 경우 --%>
			<tr>
				<td name="memberId"><%= m.getMemberId() %></td>
				<td name="memberName"><%= m.getMemberName() %></td>
				<td>
					<%-- data속성 : 태그에 data를 직접 저장/불러오기 가능 --%>
					<select class="member-role" data-member-id="<%= m.getMemberId() %>">
						<option value="<%= MemberService.USER_MEMBER_ROLE %>" <%= MemberService.USER_MEMBER_ROLE.equals(m.getMemberRole()) ? "selected" : "" %>>일반</option>
						<option value="<%= MemberService.ADMIN_MEMBER_ROLE %>" <%= MemberService.ADMIN_MEMBER_ROLE.equals(m.getMemberRole()) ? "selected" : "" %>>관리자</option>
					</select>
				</td>
				<td name="memberGender">
					<%= "M".equals(m.getGender()) ? "남" : ("F".equals(m.getGender()) ? "여" : "") %>
				</td>
				<td><%= m.getBirthDay() != null ? m.getBirthDay() : "" %></td>
				<td><%= m.getEmail() != null ?  m.getEmail() : "" %></td>
				<td><%= m.getPhone() %></td>
				<td><%= m.getAddress() != null ? m.getAddress() : "" %></td>
				<td><%= m.getHobby() != null ? m.getHobby() : "" %></td>
				<td><%= m.getEnrollDate() %></td>
			</tr>
		<% 	} 
		  } %>
		</tbody>
	</table>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>

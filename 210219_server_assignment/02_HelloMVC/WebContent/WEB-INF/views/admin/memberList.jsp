<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%
	List<Member> list = (List<Member>)request.getAttribute("list");
	
	String searchType = request.getParameter("searchType");
	String searchKeyword = request.getParameter("searchKeyword");
%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<!-- 관리자용 admin.css link -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin.css" />
<style>
	div#search-container {margin:0 0 10px 0; padding:3px; background-color: rgba(0, 188, 212, 0.3);}
	div#search-memberId {display: <%= "memberId".equals(searchType) || searchType == null ? "inline-block" : "none" %>;}
	div#search-memberName{display:<%= "memberName".equals(searchType) ? "inline-block" : "none" %>;}
	div#search-gender{display:<%= "gender".equals(searchType) ? "inline-block" : "none" %>;}
</style>
<script>
$(function(){
	
	/**
	* 검색타입 변경 이벤트핸들러
	*/
	$("#searchType").change(function(){
		var type = $(this).val();//memberId | memberName | gender
		var $divSearchTypes = $(".search-type");
		
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
            <option value="memberId" <%= "memberId".equals(searchType) ? "selected" : "" %>>아이디</option>		
            <option value="memberName" <%= "memberName".equals(searchType) ? "selected" : "" %>>회원명</option>
            <option value="gender" <%= "gender".equals(searchType) ? "selected" : "" %>>성별</option>
        </select>
        <div id="search-memberId" class="search-type">
            <form action="<%=request.getContextPath()%>/admin/memberFinder">
                <input type="hidden" name="searchType" value="memberId"/>
                <input 
                	type="text" name="searchKeyword"  size="25" 
                	placeholder="검색할 아이디를 입력하세요."
                	value="<%= "memberId".equals(searchType) ? searchKeyword : "" %>"/>
                <button type="submit">검색</button>			
            </form>	
        </div>
        <div id="search-memberName" class="search-type">
            <form action="<%=request.getContextPath()%>/admin/memberFinder">
                <input type="hidden" name="searchType" value="memberName"/>
                <input 
                	type="text" name="searchKeyword" size="25" 
                	placeholder="검색할 이름을 입력하세요."
                	value="<%= "memberName".equals(searchType) ? searchKeyword : "" %>"/>
                <button type="submit">검색</button>			
            </form>	
        </div>
        <div id="search-gender" class="search-type">
            <form action="<%=request.getContextPath()%>/admin/memberFinder">
                <input type="hidden" name="searchType" value="gender"/>
                <input 
                	type="radio" name="searchKeyword" value="M" 
                	<%= "gender".equals(searchType) && "M".equals(searchKeyword) ? "checked" : "" %>> 남
                <input 
                	type="radio" name="searchKeyword" value="F"
                	<%= "gender".equals(searchType) && "F".equals(searchKeyword) ? "checked" : "" %>> 여
                <button type="submit">검색</button>
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
				<td><%= m.getMemberId() %></td>
				<td><%= m.getMemberName() %></td>
				<td>
					<%-- data속성 : 태그에 data를 직접 저장/불러오기 가능 --%>
					<select class="member-role" data-member-id="<%= m.getMemberId() %>">
						<option value="<%= MemberService.USER_MEMBER_ROLE %>" <%= MemberService.USER_MEMBER_ROLE.equals(m.getMemberRole()) ? "selected" : "" %>>일반</option>
						<option value="<%= MemberService.ADMIN_MEMBER_ROLE %>" <%= MemberService.ADMIN_MEMBER_ROLE.equals(m.getMemberRole()) ? "selected" : "" %>>관리자</option>
					</select>
				</td>
				<td>
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
	<div id="pageBar">
		<%= request.getAttribute("pageBar") %>
	</div>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>

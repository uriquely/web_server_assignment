<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
	Member member = (Member)request.getAttribute("member");
	//out.println(member);//응답출력
	
	//String ---split---> String[] ---Arrays.asList()---> ArrayList의 contains로 체크확인
	String hobby = member.getHobby();
	String[] hobbyArr = null;
	List<String> hobbyList = null;
	if(hobby != null){
		hobbyArr = hobby.split(",");
		hobbyList = Arrays.asList(hobbyArr);
	}
	System.out.println(hobbyList);
%>
<script>
function deleteMember(){
    var bool = confirm("정말로 탈퇴하시겠습니까?");
    if(bool)
        location.href = "<%=request.getContextPath() %>/member/memberDelete?memberId=<%=member.getMemberId()%>";
}

function updateMember(){
	var url = "<%=request.getContextPath() %>/member/memberUpdate";
	$("#memberUpdateFrm")
		.attr('action',url)
		.submit();	
}

function updatePassword(){
	location.href = "<%= request.getContextPath() %>/member/updatePassword?memberId=<%= member.getMemberId() %>";
}


$(function(){
	
	/**
	* 비밀번호 일치여부 검사
	*/
    /* $("#password2").blur(function(){
        var $p1 = $("#password_");
        var $p2 = $("#password2");
        
        if($p1.val() != $p2.val()){
            alert("패스워드가 일치하지 않습니다.");
            $p1.select();
        }
    }); */
	
	$("#memberUpdateFrm").submit(function(){
		//유효성 검사
		//password
        /* var $p1 = $("#password_");
        var $p2 = $("#password2");
        if(/^[a-zA-Z0-9!@#$$%^&*()]{4,}/.test($p1.val()) == false){
        	alert("유효한 패스워드를 입력하세요.");
        	$p1.select();
            return false;
        }
        
        if($p1.val() != $p2.val()){
            alert("패스워드가 일치하지 않습니다.");
            $p1.select();
            return false;
        } */
        
        //memberName
        var $memberName = $("#memberName");
        if(/^[가-힣]{2,}$/.test($memberName.val()) == false){
        	alert("이름은 한글 2글자 이상이어야 합니다.");
        	$memberName.select();
        	return false;
        }
        
        //phone
        var $phone = $("#phone");
        //-제거하기
        $phone.val($phone.val().replace(/[^0-9]/g, ""));//숫자아닌 문자(복수개)제거하기
        
        if(/^010[0-9]{8}$/.test($phone.val()) == false){
        	alert("유효한 전화번호가 아닙니다.");
        	$phone.select();
        	return false;
        }
        
        return true;
	});
});

</script>
<section id=enroll-container>
	<h2>회원 정보</h2>
	<form id="memberUpdateFrm" method="post">
		<table>
			<tr>
				<th>아이디</th>
				<td>
					<input type="text" name="memberId" id="memberId_" value="<%= member.getMemberId() %>" readonly>
				</td>
			</tr>
			<%-- <tr>
				<th>패스워드</th>
				<td>
					<input type="password" name="password" id="password_" value="<%= member.getPassword() %>" required>
				</td>
			</tr>
			<tr>
				<th>패스워드확인</th>
				<td>	
					<input type="password" id="password2" value="<%= member.getPassword() %>" required><br>
				</td>
			</tr>  --%>
			<tr>
				<th>이름</th>
				<td>	
				<input type="text"  name="memberName" id="memberName" value="<%= member.getMemberName() %>"  required><br>
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>	
				<input type="date" name="birthDay" id="birthDay" value="<%= member.getBirthDay() %>"><br>
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" placeholder="abc@xyz.com" name="email" id="email" value="<%= member.getEmail() != null ? member.getEmail() : "" %>"><br>
				</td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td>	
					<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" value="<%= member.getPhone() %>" required><br>
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>	
					<input type="text" placeholder="" name="address" id="address" value="<%= member.getAddress() != null ?  member.getAddress() : "" %>"><br>
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>
			       		 <input type="radio" name="gender" id="gender0" value="M" 
			       		 	<%= "M".equals(member.getGender()) ? "checked" : "" %>>
						 <label for="gender0">남</label>
						 <input type="radio" name="gender" id="gender1" value="F"
						 	<%= "F".equals(member.getGender()) ? "checked" : "" %>>
						 <label for="gender1">여</label>
				</td>
			</tr>
			<tr>
				<th>취미 </th>
				<td>
					<input type="checkbox" name="hobby" id="hobby0" value="운동" <%= hobbyList != null && hobbyList.contains("운동") ? "checked" : "" %>><label for="hobby0">운동</label>
					<input type="checkbox" name="hobby" id="hobby1" value="등산" <%= hobbyList != null && hobbyList.contains("등산") ? "checked" : "" %>><label for="hobby1">등산</label>
					<input type="checkbox" name="hobby" id="hobby2" value="독서" <%= hobbyList != null && hobbyList.contains("독서") ? "checked" : "" %>><label for="hobby2">독서</label><br />
					<input type="checkbox" name="hobby" id="hobby3" value="게임" <%= hobbyList != null && hobbyList.contains("게임") ? "checked" : "" %>><label for="hobby3">게임</label>
					<input type="checkbox" name="hobby" id="hobby4" value="여행" <%= hobbyList != null && hobbyList.contains("여행") ? "checked" : "" %>><label for="hobby4">여행</label><br />


				</td>
			</tr>
		</table>
        <input type="button" onclick="updateMember();" value="정보수정"/>
        <input type="button" onclick="updatePassword();" value="비밀번호수정"/>
        <input type="button" onclick="deleteMember();" value="탈퇴"/>
	</form>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>

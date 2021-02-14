<%@page import="member.model.service.MemberService"%>
<%@page import="member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%

	String successMsg = (String)request.getAttribute("successMsg");
	String failMsg = (String)request.getAttribute("failMsg");

	String msg = (String)session.getAttribute("msg");
	
	//새로고침을 할 때마다 발생될 수 있으니 1회 사용 후 폐기한다.
	if(msg != null)session.removeAttribute("msg");
	
	//System.out.println("msg@header.jsp = " + msg);
	
	//테스트한 코드들은 주석하면 좋지만, 웬만해선 이렇게 어디에서 찍었는지 위치를 같이 출력하자
	String loc = (String)request.getAttribute("loc");
	//System.out.println("loc@header.jsp = " + loc);
	
	
	//세션 방식으로 진행할 때에는 request가 아닌 session에서 받아온다.
	Member memberLoggedIn = (Member)session.getAttribute("memberLoggedIn");
	//Member memberLoggedIn = (Member)request.getAttribute("memberLoggedIn");
	//System.out.println("memberLoggedIn@header.jsp = " + memberLoggedIn);
	
	String saveId = null;
	
	//서버로 전송된 쿠키값 확인
	Cookie[] cookies = request.getCookies();
	if(cookies != null) {
		
		for(Cookie c : cookies) {
			//System.out.println(c.getName() + " : " + c.getValue());
			if("saveId".equals(c.getName())) {
				
				//saveId라는 키값이 있는지를 equals로 검사하고,
				saveId = c.getValue();
				break;
			}
		}
		
		//System.out.println("saveId@header.jsp = " + saveId);
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello MVC</title>
<%-- application별칭(context-root, context-path)를 동적으로 얻어오기 /mvc --%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css" />
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-3.5.1.js"></script>
<script>

<% if(successMsg != null) { %> 
alert("<%= successMsg %> "); 
<% } %>

<% if(failMsg != null) { %> 
alert("<%= failMsg %> "); 
<% } %>

<% if(msg != null) { %> 
	alert("<%= msg %> "); 
<% } %>
	
<% if(loc != null) { %>
	location.href = "<%= loc %>";
<% } %>

$(function(){
<% if(memberLoggedIn == null) { %>
	
	/* 온로드함수 : 모든 html코드 등록을 마치고 나서 실행될 것 */
		
		/*
			로그인 폼 유효성 검사
			
			폼 전송을 방지할 수 있다.
			return false
			e.preventDefault()
		*/
		/* 로그인에 성공하고 나면 loginFrm이 존재하지 않아 오류가 발생하게 된다. */
		$(loginFrm).submit(function(e){
			
			//아이디검사
			var $memberId = $(memberId);
			if(/^.{4,}$/.test($memberId.val()) == false){
				
				/* 시작하고 아무 글자나 4글자 이상 */
				alert("유효한 아이디를 입력하세요.");
				
				$memberId.select();
				return false; //폼 전송 방지
			}
			
			//비번검사
			var $password = $(password);
			if(/^.{4,}$/.test($password.val()) == false){
				
				/* 시작하고 아무 글자나 4글자 이상 */
				alert("유효한 비밀번호를 입력하세요.");
				
				$password.select();
				e.preventDefault();
			}
			
		});
	<% } %>	
 
});
</script>
</head>
<body>
	<div id="container">
		<header>
			<h1>Hello MVC</h1>
			<!-- 로그인메뉴 시작 -->
			
			<!-- 로그인을 했을 때 , ㅇㅇ님 환영합니다! 하는 영역을 보여주기 위해 분기처리하는 과정 -->
			<div class="login-container">
			<% if(memberLoggedIn == null){ %>
				<form 
					id="loginFrm"
					action="<%= request.getContextPath() %>/member/login"
					method="POST">
					<!-- 사용자 정보가 url에 노출되면 곤란하므로 get이 아닌 post방식 -->
					<table>
						<tr>
							<td><input 
								type="text" 
								name="memberId" 
								id="memberId" 
								placeholder="아이디" 
								tabindex="1"
								value="<%= saveId != null ? saveId : "" %>">
								</td>
							<td><input type="submit" value="로그인" tabindex="3"></td>
						</tr>
						<tr>
							<td><input type="password" name="password" id="password"
							placeholder="비밀번호" tabindex="2"></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2">
								<input 
									type="checkbox" 
									name="saveId" 
									id="saveId" 
									<%= saveId != null ? "checked" : "" %>
									/>
								<label for="saveId">아이디저장</label>
								&nbsp;&nbsp;
								<input 
									type="button" 
									value="회원가입"
									onclick="location.href='<%= request.getContextPath() %>/member/memberEnroll';"/>
							</td>
						</tr>
					</table>
				</form>
			
			<!-- 상단의 if조건절을 닫는 부분 -->
			
			<% } else { %>
			<!-- 로그인 성공시 -->
			<table id="logged-in">
				<tr>
					<td><%= memberLoggedIn.getMemberName() %>님, 안녕하세요.</td>
				</tr>
				<tr>
					<td>
						<input 
							type="button" 
							value="내 정보보기"
							onclick="location.href='<%= request.getContextPath() %>/member/memberView?memberId=<%= memberLoggedIn.getMemberId() %>';"/>
						<input 
							type="button" 
							value="로그아웃"
							onclick="location.href='<%= request.getContextPath() %>/member/logout'; "/>
					</td>
				</tr>
			</table>
			<% } %>
			</div>
			<!-- 로그인메뉴 끝-->
			<!-- 메인메뉴 시작 -->
			<nav>
				<ul class="main-nav">
					<li class="home"><a href="<%= request.getContextPath() %>">Home</a></li>
					<li id="notice"><a href="#">공지사항</a></li>
					<li id="board"><a href="#">게시판</a></li>
					<!-- 관리자메뉴 : 관리자만 노출 -->
					<% if(memberLoggedIn != null && MemberService.ADMIN_MEMBER_ROLE.equals(memberLoggedIn.getMemberRole())) { %>
					<li id="admin-memberlist"><a href="<%= request.getContextPath() %>/admin/memberList">회원관리</a></li>
					<% } %>
				</ul>
			</nav>
			<!-- 메인메뉴 끝-->
		</header>
		
		<section id="content">
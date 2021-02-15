<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="member.model.vo.Member"%>
<%@ page import="member.model.service.MemberService"%>
<%
	String msg = (String)session.getAttribute("msg");
	if(msg != null) session.removeAttribute("msg");//1회 사용후 폐기
	//System.out.println("msg@header.jsp = " + msg);
	
	String loc = (String)request.getAttribute("loc");
	//System.out.println("loc@header.jsp = " + loc);
	Member memberLoggedIn = (Member)session.getAttribute("memberLoggedIn");
	//System.out.println("memberLoggedIn@header.jsp = " + memberLoggedIn);
	
	//서버로 전송된 쿠키값 확인
	String saveId = null;
	Cookie[] cookies = request.getCookies();
	if(cookies != null){
		for(Cookie c : cookies){
			//System.out.println(c.getName() + " : " + c.getValue());
			if("saveId".equals(c.getName())){
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
<%-- application별칭(context-root, context-path)를 동적으로 얻어오기  /mvc --%>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" />
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-3.5.1.js"></script>
<script>
<% if(msg != null) { %> alert("<%= msg %>"); <% } %>
<% if(loc != null) { %> location.href = "<%= loc %>"; <% } %>


$(function(){
	
<% if(memberLoggedIn == null) { %>
	/*
	* 로그인폼 유효성 검사
	*
	* 폼 전송을 방지
	* return false
	* e.preventDefault()
	*/
	$(loginFrm).submit(function(e){
		//아이디 검사
		var $memberId = $(memberId);
		if(/^.{4,}$/.test($memberId.val()) == false){
			alert("유효한 아이디를 입력하세요.");
			$memberId.select();
			return false;//폼 전송 방지
		}
		//비번검사
		var $password = $(password);
		if(/^.{4,}$/.test($password.val()) == false){
			alert("유효한 비밀번호를 입력하세요.");
			$password.select();
			e.preventDefault();//폼 전송 방지
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
			<div class="login-container">
			<% if(memberLoggedIn == null){ %>
				<form 
					id="loginFrm"
					action="<%= request.getContextPath() %>/member/login"
					method="POST">
					<table>
						<tr>
							<td>	
								<input 
									type="text" 
									name="memberId" 
									id="memberId" 
									placeholder="아이디" 
									tabindex="1"
									value="<%= saveId != null ? saveId : ""%>">
							</td>
							<td><input type="submit" value="로그인" tabindex="3"></td>
						</tr>
						<tr>
							<td><input type="password" name="password" id="password" placeholder="비밀번호" tabindex="2"></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2">
								<input 
									type="checkbox" 
									name="saveId" 
									id="saveId" 
									<%= saveId != null ? "checked" : "" %>/>
								<label for="saveId">아이디저장</label>
								&nbsp;&nbsp;
								<input 
									type="button" 
									value="회원가입"
									onclick="location.href='<%= request.getContextPath() %>/member/memberEnroll';">
							</td>
						</tr>
					</table>
				</form>
			
			<% } else { %>
				<%-- 로그인 성공시 --%>
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
					<li id="board"><a href="<%= request.getContextPath() %>/board/boardList">게시판</a></li>
					<%-- 관리자메뉴 : 관리자만 노출 --%>
					<% if(memberLoggedIn != null && MemberService.ADMIN_MEMBER_ROLE.equals(memberLoggedIn.getMemberRole())){ %>
					<li id="admin-memberList"><a href="<%= request.getContextPath() %>/admin/memberList">회원관리</a></li>
					<% } %>
				</ul>
			</nav>
			<!-- 메인메뉴 끝-->
			
		</header>
		
		<section id="content">
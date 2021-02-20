<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
    
<%--
	isErrorPage="true"
	내장객체 exception에 접근 가능
	
	statusCode로 예외페이지로 넘어온 경우, exception객체는 null
--%>    
    
<%
	//위에서 isErrorPage를 해줬기 때문에 아래의 명령이 가능해진다.
	String msg = exception != null ? 
			exception.getMessage() : String.valueOf(response.getStatus());

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
	<h1 align="center"><%= msg %></h1>
	<div align="center">
		<button onclick="history.back();">이전 페이지</button>
		<button onclick="Location.href='<%= request.getContextPath() %>'">홈으로 돌아가기</button>
	</div>

</body>
</html>
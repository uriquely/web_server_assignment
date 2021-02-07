<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%-- jsp주석 --%>

<% 

	//java 코드 공간 (퍼센트 꺽새가)
	//jsp에서는 HttpServletRequest, HttpServletResponse객체에 
	//request, response명으로 선언 없이 접근 가능
	
	String name = request.getParameter("name");
	String color = request.getParameter("color");
	String animal = request.getParameter("animal");
	String[] foodArr = request.getParameterValues("food");
	
	//servlet생산데이터라면 여기서 꺼낸다(request)
	Date now = (Date)request.getAttribute("now");
		
	System.out.println("name@jsp = " + name);
	System.out.println("color@jsp = " + color);
	System.out.println("animal@jsp = " + animal);
	System.out.println("food@jsp = " + Arrays.toString(foodArr));

%>
<!DOCTYPE html>
<html>
<head>
	<meta charset='utf-8'/>
	<title>테스트 결과</title>
</head>
<body>
	<h1>테스트결과</h1>
	<p><%= now %></p>
	<%-- jsp표현식 : 출력시 사용, ;(세미콜론)사용금지 --%>
	<p>이름 : <%= name %></p>
	<p>색깔 : <%= color %></p>
	<p>동물 : <%= animal %></p>
	<p>음식 : <%= Arrays.toString(foodArr) %></p>
</body>
</html>
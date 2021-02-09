package com.kh.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MenuOrder extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. 한글깨짐 방지 인코딩 처리
		
		request.setCharacterEncoding("utf-8");
		
		//2. 사용자 입력값 처리
		
		String main = request.getParameter("main_menu");
        String side = request.getParameter("side_menu");
        String drink = request.getParameter("drink_menu");
		
		int result = 0;
		result += (main.equals("한우버거") ? 5000 : main.equals("밥버거") ? 4500 : 4000);
		result += (side.equals("감자튀김") ? 1500 : 1700);
		result += (drink.equals("콜라") ? 1000 : drink.equals("사이다") ? 1000 : drink.equals("커피") ? 1500 : 2500);
		
		request.setAttribute("result", result);
		
		//3. 응답처리
		//jsp로 html 작성을 위임.
		RequestDispatcher reqDispatcher
			=  request.getRequestDispatcher("/servlet/menuResult.jsp");
		reqDispatcher.forward(request, response);
	}

}

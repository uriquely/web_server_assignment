package com.kh.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet : 웹서비스를 위한 자바클래스로써, 반드시 HttpServlet클래스를 상속해야 한다.
 * 그래야 서블릿으로서의 기능을 갖출 수 있음
 * 
 * 웹서비스란 브라우저에서 사용자 요청이 들어왔을 때 상응하는 처리를 해줄 수 있다는 것.
 * 
 * <pre>
 * Servlet 인터페이스
 * 	└ GenericSevlet 추상클래스
 * 		└ HttpServlet 추상클래스
 * 			└ TestPersonServlet1
 * 
 * 서블릿의 생명주기
 * - 컨테이너(WAS)에 의해서 싱글톤(프로그램내에서 단 하나의 객체만 생성되고 관리)으로 관리되는 객체
 * 
 * 객체생성 - init(설정) - service(매사용자요청마다 호출) - doGet|doPost
 * 
 * (서버중지 또는 재시작)
 * destroy - 객체반환
 * 
 * 
 * 객체 하나가 서비스
 * 
 * java/xml파일 수정 시 서버를 재시작해야 한다.
 * 메소드의 몸통부분 수정시에만 eclipse에서 reload기능을 이용할 수 있다.
 * 
 * </pre>
 */

public class TestPersonServlet1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestPersonServlet1() {
        super();
        
//      해쉬코드(객체의 고유값)
        System.out.println("객체생성 : " + this.hashCode());
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		System.out.println("init!");
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		
		System.out.println("distroy!");
	}

	/**
	 * 
	 * <pre>
	 * GET방식 요청 시 호출메소드
	 * 
	 * HttpServletRequest 인터페이스
	 * 		사용자 요청관련 정보가 들어있다. : 사용자 입력값, 쿠키, 요청 관련 헤더, 세션
	 * 
	 * HttpServletResponse 인터페이스
	 * 		사용자 응답 관련 정보가 들어있다. : 응답출력스트림, 응답헤더 작성
	 * 
	 * </pre>
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("doGet 호출! : " +  this.hashCode());
		
		//1. 사용자 입력값 확인 : 자바변수에 옮겨담기
		
		String name = request.getParameter("name");
		String color = request.getParameter("color");
		String animal = request.getParameter("animal");
		String[] foodArr = request.getParameterValues("food");
		
		System.out.println("name = " + name);
		System.out.println("color = " + color);
		System.out.println("animal = " + animal);
		System.out.println("food = " + Arrays.toString(foodArr));
		
		
		//2. 응답 작성 : html 작성
		
		//응답헤더 작성
		response.setContentType("text/html; charset=utf-8");
		
		//응답 출력스트림
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='utf-8'/>");
		out.println("<title>테스트 결과</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>테스트결과</h1>");
		out.println("<p>이름 : " + name + "</p>");
		out.println("<p>색깔 : " + color + "</p>");
		out.println("<p>동물 : " + animal + "</p>");
		out.println("<p>음식 : " + Arrays.toString(foodArr) + "</p>");
		out.println("</body>");
		out.println("</html>");
		
	}

	/**
	 * POST방식 요청시 호출 메소드
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("doPost 호출! : " + this.hashCode());
		
		//0.POST방식은 utf-8 인코딩 처리선언
		//http message body 영역의 데이터도 utf-8방식으로 처리
		request.setCharacterEncoding("utf-8");
		
		String name = request.getParameter("name");
		String color = request.getParameter("color");
		String animal = request.getParameter("animal");
		String[] foodArr = request.getParameterValues("food");
		
		System.out.println("name = " + name);
		System.out.println("color = " + color);
		System.out.println("animal = " + animal);
		System.out.println("food = " + Arrays.toString(foodArr));
		
		
		//2. 응답 작성 : html 작성
		
		//응답헤더 작성
		response.setContentType("text/html; charset=utf-8");
		
		//응답 출력스트림
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='utf-8'/>");
		out.println("<title>테스트 결과</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>테스트결과</h1>");
		out.println("<p>이름 : " + name + "</p>");
		out.println("<p>색깔 : " + color + "</p>");
		out.println("<p>동물 : " + animal + "</p>");
		out.println("<p>음식 : " + Arrays.toString(foodArr) + "</p>");
		out.println("</body>");
		out.println("</html>");
		
	}

}

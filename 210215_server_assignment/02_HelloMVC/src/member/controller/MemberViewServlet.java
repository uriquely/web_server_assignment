package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberViewServlet
 */
@WebServlet("/member/memberView")
public class MemberViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. encoding처리
		request.setCharacterEncoding("utf-8");
		//2. 사용자입력값처리
		String memberId = request.getParameter("memberId");
		System.out.println("memberId@servlet = " + memberId);
		//3. 비지니스로직
		Member member = memberService.selectOne(memberId);
		
		//4. view단 처리
		request.setAttribute("member", member);
		request.getRequestDispatcher("/WEB-INF/views/member/memberView.jsp")
			   .forward(request, response);
	}

}








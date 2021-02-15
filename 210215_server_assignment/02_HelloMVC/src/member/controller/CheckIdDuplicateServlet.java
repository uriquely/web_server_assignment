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
 * Servlet implementation class CheckIdDuplicateServlet
 */
@WebServlet("/member/checkIdDuplicate")
public class CheckIdDuplicateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. encoding
		request.setCharacterEncoding("utf-8");
		//2. 사용자 입력값
		String memberId = request.getParameter("memberId");
		System.out.println("memberId@servlet = " + memberId);
		//3. 비지니스 로직 : db를 통해 중복값 체크
		Member member = memberService.selectOne(memberId);
		boolean available = (member == null);
		//4. 처리결과 view단 전달(forwarding)
		request.setAttribute("available", available);
		request.getRequestDispatcher("/WEB-INF/views/member/checkIdDuplicate.jsp")
			   .forward(request, response);
	}

}








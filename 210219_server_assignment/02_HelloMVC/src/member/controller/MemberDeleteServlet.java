package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberDeleteServlet
 */
@WebServlet("/member/memberDelete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.전송값에 한글이 있을 경우 인코딩처리해야함.
		request.setCharacterEncoding("UTF-8");//대소문자 상관없음. 요청한 view단의 charset값과 동일해야 한다.
		
		//2.전송값 꺼내서 변수에 기록하기.
		String memberId = request.getParameter("memberId");
		
		//3.서비스로직호출
		int result = memberService.deleteMember(memberId);
		
		//4. 받은 결과에 따라 뷰페이지 내보내기
		String msg = "";
		String loc = request.getContextPath();
		HttpSession session = request.getSession();
		if(result>0) {
			msg = "성공적으로 회원정보를 삭제했습니다.";
			//세션무효화후 새로 생성
			session.invalidate();
			session = request.getSession();
		}
		else {
			msg = "회원정보삭제에 실패했습니다.";
			loc = request.getContextPath() + "/member/memberView?memberId=" + memberId;
		}
		
		session.setAttribute("msg", msg);
//		request.setAttribute("loc", loc);
		
		response.sendRedirect(loc);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

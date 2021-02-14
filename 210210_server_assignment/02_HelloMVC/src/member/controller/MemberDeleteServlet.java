package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;

@WebServlet("/member/memberdelete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public MemberDeleteServlet() {
        super();
    }

    private MemberService memberService = new MemberService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");//대소문자 상관없음. 요청한 view단의 charset값과 동일해야 한다.
		
		//2.전송값 꺼내서 변수에 기록하기.
		String memberId = request.getParameter("memberId");
		
		//3.서비스로직호출
		int result = memberService.deleteMember(memberId);
		
		//4. 받은 결과에 따라 뷰페이지 내보내기
		String msg = "";
		String loc = null;
		HttpSession session = request.getSession();
		

		if(result > 0) {
			msg = "성공적으로 회원정보를 삭제했습니다.";
			loc = request.getContextPath() + "/member/logout";//회원탈퇴인 경우, 로그아웃 처리함.
		}
		else {
			msg = "회원정보삭제에 실패했습니다.";
			loc = request.getContextPath() + "/member/memberView?memberId=" + memberId;
		}
		
		session.setAttribute("msg", msg);
		response.sendRedirect(loc);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

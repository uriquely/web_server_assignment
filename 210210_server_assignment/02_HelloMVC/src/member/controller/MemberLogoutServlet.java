package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/member/logout")
public class MemberLogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//존재하지 안흔다면, 새로 만들지 않고 null리턴
		HttpSession session = request.getSession(false);
		
		
		//세션 날리는 코드
		if(session != null)
			session.invalidate();
		
		//인덱스페이지를 재요청하는 리다이렉트처리
		response.sendRedirect(request.getContextPath()); // 이걸로 /mvc를 보낼 것
	}

}

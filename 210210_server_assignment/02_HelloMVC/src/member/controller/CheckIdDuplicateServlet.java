package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

@WebServlet("/member/checkIdDuplicate")
public class CheckIdDuplicateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MemberService memberService = new MemberService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. encoding
		request.setCharacterEncoding("utf-8");
		
		//2. 사용자 입력값
		String memberId = request.getParameter("memberId");
//		System.out.println("memberId@servlet = " + memberId);
		
		//3. 비지니스 로직 : db를 통해 중복값 체크
		//조회를 했을 때 없을 경우가 있을텐데, 그 때는 null이 리턴될테고, 그 경우가 그 아이디를 사용 가능한 상태
		Member member = memberService.selectOne(memberId);
		
		//null이 아니면 사용할 수 없겠지(중복이니)
		boolean available = (member == null);
		
		//4. 처리 결과를 view단에 전달(forwarding)
		request.setAttribute("available", available);
		
		request
			.getRequestDispatcher("/WEB-INF/views/member/checkIdDuplicate.jsp")
			.forward(request, response);
		
	}
}

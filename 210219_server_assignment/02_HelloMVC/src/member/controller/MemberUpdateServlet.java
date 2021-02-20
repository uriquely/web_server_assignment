package member.controller;

import java.io.IOException;
import java.sql.Date;

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
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet(name="MemberUpdateServlet", urlPatterns="/member/memberUpdate")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.전송값에 한글이 있을 경우 인코딩처리해야함.
		//void javax.servlet.ServletRequest.setCharacterEncoding(String arg0) throws UnsupportedEncodingException
		request.setCharacterEncoding("UTF-8");//대소문자 상관없음. 요청한 view단의 charset값과 동일해야 한다.
		
		//2.전송값 꺼내서 변수에 기록하기.
		//String javax.servlet.ServletRequest.getParameter(String arg0)
		String memberId = request.getParameter("memberId");
		String password = request.getParameter("password");
		String memberName = request.getParameter("memberName");
		String birthDay = request.getParameter("birthDay");
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		//체크박스같은 경우 선택된 복수의 값이 배열로 전달된다.
		//String[] javax.servlet.ServletRequest.getParameterValues(String arg0)
		String[] hobbies = request.getParameterValues("hobby");
		
		String hobby = "";
		//String java.lang.String.join(CharSequence delimiter, CharSequence... elements)
		//파라미터로 전달한 문자열배열이 null이면, NullPointerException유발.
		if(hobbies!=null) hobby = String.join(",", hobbies);

		//날짜타입으로 변경 : 1990-09-09
		Date birthDay_ = null;
		if(birthDay != null && !"".equals(birthDay))
			birthDay_ = Date.valueOf(birthDay);
		
		Member member = new Member(memberId, password, memberName, "U", gender, birthDay_, email, phone, address, hobby, null);

		System.out.println("입력한 회원정보 : "+member);
		
		//3.서비스로직호출
		int result = memberService.updateMember(member);  
		
		//4. 받은 결과에 따라 뷰페이지 내보내기
//		String view = "/index.jsp";
		String msg = null;
		String loc = request.getContextPath() + "/member/memberView?memberId=" + member.getMemberId();
		HttpSession session = request.getSession();
		if(result>0){
			msg = "성공적으로 회원정보를 수정했습니다.";
			
			//세션의 memberLoggedIn객체도 최신화
			session.setAttribute("memberLoggedIn", memberService.selectOne(memberId));
		}
		else {
			msg = "회원정보수정에 실패했습니다.";	
		}
		
		session.setAttribute("msg", msg);
		
		
		response.sendRedirect(loc);
//		RequestDispatcher reqDispatcher = request.getRequestDispatcher(view);
//		reqDispatcher.forward(request, response);
	}

}

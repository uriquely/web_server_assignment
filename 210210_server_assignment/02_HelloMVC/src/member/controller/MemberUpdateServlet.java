package member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.service.MemberService;
import member.model.vo.Member;

@WebServlet("/member/memberupdate")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MemberService memberService = new MemberService();

    public MemberUpdateServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String memberId = request.getParameter("memberId");
		String password = request.getParameter("password");
		String memberName = request.getParameter("memberName");
		String gender = request.getParameter("gender");
		String birth = request.getParameter("birthDay");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String[] hobbyArr = request.getParameterValues("hobby");
		
		//hobbyArr이 출력되는 형태 = [A, B, C]
		//System.out.println(Arrays.toString(hobbyArr));
		
		//String.join("추가할 문자", "대상 Array")
		String hobby = "";
		
		if(hobbyArr != null) hobby = String.join("," , hobbyArr);
		
		//birth의 타입을 날짜로 변경
	
		Date birthDay = null;
		
		//**가져온 birth가 null이 아니면서(체크를 안한 경우), 
		if(birth != null) {
			birthDay = Date.valueOf(birth);
		}
		
		
		//이전 과제에서의 문제점 해결 : memberRole의 디폴트값인 U를 미리 주고,
		//enrollDate는 여기서 null값으로 미리 주면 기본값인 sysdate로 설정
		Member member = new Member(memberId, 
				password, 
				memberName,
				"U",
				gender,
				birthDay,
				email,
				phone,
				address,
				hobby,
				null);
		
		int result = memberService.updateMember(member);
		
		String msg = "";
		String loc = request.getContextPath() + "/member/memberView?memberId=" + member.getMemberId();
		HttpSession session = request.getSession();
		
		if(result != 0) {
			msg = "정보수정에 성공했습니다!";
		
	        session.setAttribute("memberLoggedIn", memberService.selectOne(memberId));
			
		} else { 
			msg = "정보수정에 실패했습니다!";
		
		}
		session.setAttribute("msg", msg);
		
		response.sendRedirect(loc);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}

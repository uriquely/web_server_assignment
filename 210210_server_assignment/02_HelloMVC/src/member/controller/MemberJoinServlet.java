package member.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.util.MVCUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

@WebServlet(urlPatterns = { "/member/join" })
public class MemberJoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberJoinServlet() {super();}
    
    private MemberService memberService = new MemberService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/*
	 * @실습문제 : 회원가입
		필드별 유의사항
		- memberRole은 기본값 "U"로 처리할 것.
		- hobby는 db의 자료형이 varchar2이므로, 
		    사용자가 체크한 항목을 ,(콤마)로 연결해 하나의 문자열로 만들것.
		- birthDay날짜형식의 문자열은 적절히 java.sql.Date타입으로 변환할것.
		- enrollDate는 member객체 생성시 null로 두었다가, 
		    쿼리에서 sysdate로 처리할 것.
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//2. 사용자 입력값 가져오기
		String memberId = request.getParameter("memberId");
		String password = MVCUtils.getEncryptedPassword(request.getParameter("password"));
		String memberName = request.getParameter("memberName");
		String gender = request.getParameter("gender");
		String birth = request.getParameter("birthDay");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String[] hobbyArr = request.getParameterValues("hobby");
		
		//3. 데이터 변경
		//System.out.println(birth);
		//System.out.println(Arrays.toString(hobbyArr));
		
		Date birthDay = Date.valueOf(birth);
		String hobby = Arrays.toString(hobbyArr);

		
		String memberRole = "U";
		Date enrollDate = null;
		
		//멤버객체에 정보를 담아야 한다.
		//new Member()후 member.setValue();로도 사용 가능
		//매개변수가 있는 생성자에 직접 적어서 넣는 방법
		
		Member member = new Member(memberId, 
									password, 
									memberName,
									memberRole,
									gender,
									birthDay,
									email,
									phone,
									address,
									hobby,
									enrollDate);
		
		int result = memberService.joinMember(member);
		
		if(result != 0) {
			request.setAttribute("successMsg", "회원가입에 성공했습니다!");
			
			RequestDispatcher reqDispatcher = 
					request.getRequestDispatcher("/index.jsp");
			
			reqDispatcher.forward(request, response);
		}
		
		else {
			request.setAttribute("failMsg", "회원가입에 실패했습니다!");
			
			RequestDispatcher reqDispatcher = 
					request.getRequestDispatcher("/WEB-INF/views/member/memberEnroll.jsp");
			
			reqDispatcher.forward(request, response);
		}
		
		
		doGet(request, response);
	}

}

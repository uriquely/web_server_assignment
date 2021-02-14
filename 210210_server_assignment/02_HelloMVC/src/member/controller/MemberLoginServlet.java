package member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.util.MVCUtils;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * @WebServlet :  web.xml에 servlet, servlet-mapping태그 등록을 대신 해 주는 annotation
 */

@WebServlet(urlPatterns={"/member/login"}) //여러 개 사용 가능

public class MemberLoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	//한 번 만들어두고 편하게 재사용한다.
	private MemberService memberService = new MemberService();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//사용자가 입력한 정보가 잘 넘어왔는지 확인하기
		
		//1. 인코딩처리(한글 깨짐 방지)
		request.setCharacterEncoding("utf-8");
		
		//2. 사용자 입력값 처리
		String memberId = request.getParameter("memberId");
		String password = MVCUtils.getEncryptedPassword(request.getParameter("password"));
		String saveId = request.getParameter("saveId");
		//System.out.println("memberId@servlet = " + memberId);
		//System.out.println("password@servlet = " + password);
//		System.out.println("saveId@servlet = " + saveId); //체크한 경우 on, 아닌 경우 null
		
		//3. 업무로직 : 사용자입력 아이디/비번이 DB에 저장된 것과 일치 여부 판단
		//아이디로 member객체를 조회
		// - member객체가 존재할 경우
		//		a. 비밀번호가 일치하는 경우
		//		b. 비밀번호가 일치하지 않는 경우
		// - c. member객체가 존재하지 않을 경우 :  아이디 오류
		
		Member member = memberService.selectOne(memberId);
		//System.out.println("member@servlet = " + member);
		
		//로그인 성공
		
		//비밀번호 일치여부
		if(member != null && password.equals(member.getPassword())) {
			
			//request.setAttribute("msg", "로그인 성공!");
			
			//jsp에서 로그인한 사용자 정보를 출력하기 위해 member객체를 request에 저장 -> 실패!
			//request.setAttribute("memberLoggedIn", member);
			
			//session객체에 로그인한 사용자정보를 기록
			//인자값을 줄 수 있는데, 기본값은 true이다.
			//create여부 : 세션 객체가 존재하면 해당 객체를 리턴하고, 존재하지 않으면 새로 생성 후 리턴.
			HttpSession session = request.getSession(true);
			session.setAttribute("memberLoggedIn", member);
			
			//세션관련메소드
			//System.out.println(session.getId()); //JSESSIONID
			//System.out.println(new Date(session.getCreationTime())); //생성시간
			//System.out.println(new Date(session.getLastAccessedTime())); //마지막 접속시간
			
			
			//초단위 설정. web.xml의 session.timeout보다 우선순위가 높다.
			//session.setMaxInactiveInterval(30); //로그인 후 30초간 아무것도 하지 않으면 세션이 해제됨.
			
			Cookie c = new Cookie("saveId", memberId);
			//쿠키전송 디랙토리(client -> server) /mvc /mvc/member ...
			c.setPath(request.getContextPath()); 
			
			//saveId 쿠키설정
			//saveId체크한 경우
			if(saveId != null) {
				
				//유효기간 설정
				//client(브라우저)에서 쿠키를 보관할 시간설정
				c.setMaxAge(7 * 24 * 60 * 60);
			}
			
			//saveId체크하지 않은 경우 : 브라우저의 쿠키를 삭제
			else {
				c.setMaxAge(0); //즉시삭제
			}
			
			response.addCookie(c);
			
			//4. redirection처리 : 요청 url을 한 번 변경
			//주어진 주소(로케이션 값)로 클라이언트한테 다시 요청하라는 응답.
			//로그인을 해도 이 주어진 주소로 클라이언트에게 보여지게 된다.
			
//			//리다이렉트 뒤에는 코드를 적지 않도록 한다.
			response.sendRedirect(request.getContextPath());
			
		}
		//로그인 실패 : 아이디 존재 X, 비번이 틀린 경우
		else {
			HttpSession session = request.getSession(true);
			session.setAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
			
			//serAttribute
//			request.setAttribute("loc", request.getContextPath());
			
			//4. view단 처리 (jsp)
//			RequestDispatcher reqDispatcher = 
//					request.getRequestDispatcher("/index.jsp");
//			reqDispatcher.forward(request, response);
			
			response.sendRedirect(request.getContextPath());
			
		}
	}

}

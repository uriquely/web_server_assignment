package admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.model.service.AdminService;
import common.util.MvcUtils;
import member.model.vo.Member;

/**
 * 페이징 처리
 * 1. 컨텐츠영역
 * 2. 페이지바영역
 * 
 * 페이징 공식
 * 1. 시작게시물no ~ 마지막게시물no
 * 2. 전체페이지수 
 * 3. 페이지바 시작no
 * 
 */
@WebServlet("/admin/memberList")
public class AdminMemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminService adminService = new AdminService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자입력값 
		int cpage = 1;
		try {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		} catch(NumberFormatException e) {
			//예외가 발생한 경우, cpage는 1로 유지한다.
		}
		int numPerPage = 10;
		
		//2. 업무로직
		List<Member> list = adminService.selectList(cpage, numPerPage);//회원가입일 내림차순
		System.out.println(list);
		
		//페이지바 처리
		int totalContents = adminService.selectTotalMembers();
		System.out.println("totalContents@servlet = " + totalContents);
		String url = request.getRequestURI();
		String pageBar = MvcUtils.getPageBar(totalContents, cpage, numPerPage, url);
		System.out.println("pageBar@servlet = " + pageBar);
		
		//3. view단 처리 : forwarding
		request.setAttribute("list", list);
		request.setAttribute("pageBar", pageBar);
		request.getRequestDispatcher("/WEB-INF/views/admin/memberList.jsp")
			   .forward(request, response);
		
		
	}

}

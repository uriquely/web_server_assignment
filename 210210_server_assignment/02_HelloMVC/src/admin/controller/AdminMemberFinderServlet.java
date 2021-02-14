package admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.model.service.AdminService;
import member.model.vo.Member;

/**
 * Servlet implementation class AdminMemberFinderServlet
 */
@WebServlet("/admin/memberFinder")
public class AdminMemberFinderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private AdminService adminService = new AdminService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		//1. 사용자 입력값 처리
//		String searchType = request.getParameter("searchType");
//		String searchKeyword = request.getParameter("searchKeyword");
//		
//		//2. 업무로직 : 검색
//		List<Member> list = adminService.selectMembersBy(searchType, searchKeyword);
//		System.out.println(list);
//		
//		//3. view단 처리 : fowarding /WEB-INF/views/memberList.jsp
//		request.setAttribute("list", list);
//		request.getRequestDispatcher("/WEB-INF/views/admin/memberList.jsp")
//			   .forward(request, response);
		
	}

}

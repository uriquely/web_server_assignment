package admin.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import admin.model.service.AdminService;
import common.util.MvcUtils;
import member.model.vo.Member;

/**
 * Servlet implementation class AdminMemberFinderServlet
 */
@WebServlet("/admin/memberFinder")
public class AdminMemberFinderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AdminService adminService = new AdminService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 사용자 입력값 처리
		String searchType = request.getParameter("searchType");
		String searchKeyword = request.getParameter("searchKeyword");
		
		int numPerPage = 10;
		int cpage = 1;
		try {
			cpage = Integer.parseInt(request.getParameter("cpage"));
		} catch (NumberFormatException e) {
			//기본값 1
		}
		
		//사용자 입력값을 Map으로 처리
		Map<String, Object> param = new HashMap<>();
		param.put("searchType", searchType);
		param.put("searchKeyword", searchKeyword);
		param.put("cpage", cpage);
		param.put("numPerPage", numPerPage);
		
		
		//2. 업무로직 : 검색
//		List<Member> list = adminService.selectMembersBy(searchType, searchKeyword);
		List<Member> list = adminService.selectMembersBy(param);
		System.out.println(list);
		
		int totalContents = adminService.selectTotalMembersBy(param);
		// /mvc/admin/memberFinder
		String url = request.getRequestURI() 
				   + "?searchType=" + searchType 
				   + "&searchKeyword=" + searchKeyword; 
		String pageBar = MvcUtils.getPageBar(totalContents, cpage, numPerPage, url);
		
		
		
		//3. view단 처리 : fowarding /WEB-INF/views/memberList.jsp
		request.setAttribute("list", list);
		request.setAttribute("pageBar", pageBar);
		request.getRequestDispatcher("/WEB-INF/views/admin/memberList.jsp")
			   .forward(request, response);
		
	}

}

package common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.vo.Member;

/**
 * Servlet Filter implementation class MemberAuthFilter
 */
@WebFilter("/member/memberView")
public class MemberAuthFilter implements Filter {

    /**
     * Default constructor. 
     */
    public MemberAuthFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * 로그인한사용자의 아이디 | 조회아이디가 동일한지 여부
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//조회아이디
		String reqMemberId = request.getParameter("memberId");
		//로그인한사용자의 아이디 
		HttpServletRequest httpReq = (HttpServletRequest)request;
		HttpSession session = httpReq.getSession();
		Member memberLoggedIn = (Member)session.getAttribute("memberLoggedIn");
		
		if(reqMemberId == null 
		|| memberLoggedIn == null
		|| !reqMemberId.equals(memberLoggedIn.getMemberId())) {
			HttpServletResponse httpResp = (HttpServletResponse)response;
			session.setAttribute("msg", "잘못된 경로로 접근하셨습니다.");
			httpResp.sendRedirect(httpReq.getContextPath());
			return;
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

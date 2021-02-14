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

@WebFilter({ "/member/memberView" })
public class MemberAuthFilter implements Filter {

    public MemberAuthFilter() {
    }

	public void destroy() {
	}
	
	/**
	 * 로그인한 사용자의 아이디 | 조회한 아이디가 동일한지
	 * 
	 */

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpReq = (HttpServletRequest)request;
		HttpSession session = httpReq.getSession();
		Member memberLoggedIn = (Member)session.getAttribute("memberLoggedIn");
		
		String memberId = request.getParameter("memberId");
		
		if(memberId == null
			|| memberLoggedIn == null
			|| !memberId.equals(memberLoggedIn.getMemberId())) {
			
			//조기리턴
			HttpServletResponse httpResp = (HttpServletResponse)response;
			session.setAttribute("msg", "잘못된 경로로 접근하셨습니다.");
			httpResp.sendRedirect(httpReq.getContextPath() +  "/member/memberView?memberId=" + memberLoggedIn.getMemberId());
			return;
		}
		
		chain.doFilter(request, response);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}

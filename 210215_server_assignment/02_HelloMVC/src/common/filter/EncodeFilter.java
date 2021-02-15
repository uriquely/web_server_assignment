package common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * @WebFilter 사용시 web.xml에 자동등록. 순서지정 어렵다.
 * web.xml 등록된 순으로 필터 작동. 
 */
//@WebFilter("/EncodeFilter")
public class EncodeFilter implements Filter {

    /**
     * Default constructor. 
     */
    public EncodeFilter() {
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, 
						 ServletResponse response, 
						 FilterChain chain) throws IOException, ServletException {
		//서블릿 가기 전	
		request.setCharacterEncoding("utf-8");
//		System.out.println("[utf-8]인코딩 처리 완료!");
		
		chain.doFilter(request, response);
		//서블릿 종료 후
//		System.out.println("encodeFilter 응답!");
		
	}

	

}

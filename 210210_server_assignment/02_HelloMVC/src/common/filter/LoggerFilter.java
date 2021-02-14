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

//@WebFilter("/LoggerFilter")
public class LoggerFilter implements Filter {

    public LoggerFilter() {
    }

	public void destroy() {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


		//서블릿 가기 전
		HttpServletRequest httpReq = (HttpServletRequest)request;
		String uri = httpReq.getRequestURI();
		
		System.out.println("======================================================");
		System.out.println(uri);
		System.out.println("------------------------------------------------------");
		chain.doFilter(request, response);
		
		//서블릿 종료 후
		System.out.println("______________________________________________________");
		System.out.println();
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

}

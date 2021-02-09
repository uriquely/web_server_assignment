package first;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * 어노테이션(@)이 서블릿을 만들었을 때 자동으로 생성된다.
 * WebServlet 어노테이션의 속성이 잘 정리된 링크
 * https://atoz-develop.tistory.com/entry/WebServlet-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98-%EC%A3%BC%EC%9A%94-%EC%86%8D%EC%84%B1-%EC%A0%95%EB%A6%AC
 * 
 * 이렇게 url에 대한 매핑을 하는 이유는, 보안 관련 이유가 가장 크다.
 */

@WebServlet("/firstsv")


/*
 * FirstServlet가 HttpServlet을 상속받고 있다.
 * 이로 인해서 FirstServlet는 서블릿으로 동작할 수 있게 되었다.
 *
 */
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FirstServlet() {super();}

    /*
     * HttpServlet 클래스에서 사용자 요청을 처리하는 doGet/doPost 메소드는 
     * 모두 HttpServletRequest와 HttpServletResponse 객체를 매개변수로 가지고있다. 
     * 이들 매개변수는 서블릿과 클라이언트 사이를 연결해주는 중요한 객체들로, 
     * HttpServlet 클래스의 특징이 된다. 
     * 또한 JSP에서는 각각 request와 response라는 내장객체로 사용되므로 
     * 이들이 제공하는 메소드에 대해 알아둘 필요가 있다.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html;charset=euc-kr");
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("My First Servlet Program!");
		out.println("<br>");
		out.println("<body>");
		out.println("</html>");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}

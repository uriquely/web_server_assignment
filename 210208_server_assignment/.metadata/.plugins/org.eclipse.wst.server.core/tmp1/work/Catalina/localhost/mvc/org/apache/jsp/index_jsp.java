/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.61
 * Generated at: 2021-02-08 03:19:03 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import member.model.vo.Member;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/views/common/header.jsp", Long.valueOf(1612754260478L));
    _jspx_dependants.put("/WEB-INF/views/common/footer.jsp", Long.valueOf(1612327401118L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("member.model.vo.Member");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("    \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");


	String successMsg = (String)request.getAttribute("successMsg");
	String failMsg = (String)request.getAttribute("failMsg");

	String msg = (String)request.getAttribute("msg");
	//System.out.println("msg@header.jsp = " + msg);
	
	//테스트한 코드들은 주석하면 좋지만, 웬만해선 이렇게 어디에서 찍었는지 위치를 같이 출력하자
	String loc = (String)request.getAttribute("loc");
	//System.out.println("loc@header.jsp = " + loc);
	
	
	//세션 방식으로 진행할 때에는 request가 아닌 session에서 받아온다.
	Member memberLoggedIn = (Member)session.getAttribute("memberLoggedIn");
	//Member memberLoggedIn = (Member)request.getAttribute("memberLoggedIn");
	//System.out.println("memberLoggedIn@header.jsp = " + memberLoggedIn);
	
	String saveId = null;
	
	//서버로 전송된 쿠키값 확인
	Cookie[] cookies = request.getCookies();
	if(cookies != null) {
		
		for(Cookie c : cookies) {
			System.out.println(c.getName() + " : " + c.getValue());
			if("saveId".equals(c.getName())) {
				
				//saveId라는 키값이 있는지를 equals로 검사하고,
				saveId = c.getValue();
				break;
			}
		}
		
		System.out.println("saveId@header.jsp = " + saveId);
	}

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<meta charset=\"UTF-8\">\r\n");
      out.write("<title>Hello MVC</title>\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath() );
      out.write("/css/style.css\" />\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print( request.getContextPath() );
      out.write("/js/jquery-3.5.1.js\"></script>\r\n");
      out.write("<script>\r\n");
      out.write("\r\n");
 if(successMsg != null) { 
      out.write(" \r\n");
      out.write("alert(\"");
      out.print( successMsg );
      out.write(" \"); \r\n");
 } 
      out.write("\r\n");
      out.write("\r\n");
 if(failMsg != null) { 
      out.write(" \r\n");
      out.write("alert(\"");
      out.print( failMsg );
      out.write(" \"); \r\n");
 } 
      out.write("\r\n");
      out.write("\r\n");
 if(msg != null) { 
      out.write(" \r\n");
      out.write("\talert(\"");
      out.print( msg );
      out.write(" \"); \r\n");
 } 
      out.write("\r\n");
      out.write("\t\r\n");
 if(loc != null) { 
      out.write("\r\n");
      out.write("\tlocation.href = \"");
      out.print( loc );
      out.write("\";\r\n");
 } 
      out.write("\r\n");
      out.write("\r\n");
      out.write("$(function(){\r\n");
 if(memberLoggedIn == null) { 
      out.write("\r\n");
      out.write("\t\r\n");
      out.write("\t/* 온로드함수 : 모든 html코드 등록을 마치고 나서 실행될 것 */\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t/*\r\n");
      out.write("\t\t\t로그인 폼 유효성 검사\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t폼 전송을 방지할 수 있다.\r\n");
      out.write("\t\t\treturn false\r\n");
      out.write("\t\t\te.preventDefault()\r\n");
      out.write("\t\t*/\r\n");
      out.write("\t\t/* 로그인에 성공하고 나면 loginFrm이 존재하지 않아 오류가 발생하게 된다. */\r\n");
      out.write("\t\t$(loginFrm).submit(function(e){\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t//아이디검사\r\n");
      out.write("\t\t\tvar $memberId = $(memberId);\r\n");
      out.write("\t\t\tif(/^.{4,}$/.test($memberId.val()) == false){\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t/* 시작하고 아무 글자나 4글자 이상 */\r\n");
      out.write("\t\t\t\talert(\"유효한 아이디를 입력하세요.\");\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$memberId.select();\r\n");
      out.write("\t\t\t\treturn false; //폼 전송 방지\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t//비번검사\r\n");
      out.write("\t\t\tvar $password = $(password);\r\n");
      out.write("\t\t\tif(/^.{4,}$/.test($password.val()) == false){\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t/* 시작하고 아무 글자나 4글자 이상 */\r\n");
      out.write("\t\t\t\talert(\"유효한 비밀번호를 입력하세요.\");\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t\t$password.select();\r\n");
      out.write("\t\t\t\te.preventDefault();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t});\r\n");
      out.write("\t");
 } 
      out.write("\t\r\n");
      out.write(" \r\n");
      out.write("});\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div id=\"container\">\r\n");
      out.write("\t\t<header>\r\n");
      out.write("\t\t\t<h1>Hello MVC</h1>\r\n");
      out.write("\t\t\t<!-- 로그인메뉴 시작 -->\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<!-- 로그인을 했을 때 , ㅇㅇ님 환영합니다! 하는 영역을 보여주기 위해 분기처리하는 과정 -->\r\n");
      out.write("\t\t\t<div class=\"login-container\">\r\n");
      out.write("\t\t\t");
 if(memberLoggedIn == null){ 
      out.write("\r\n");
      out.write("\t\t\t\t<form \r\n");
      out.write("\t\t\t\t\tid=\"loginFrm\"\r\n");
      out.write("\t\t\t\t\taction=\"");
      out.print( request.getContextPath() );
      out.write("/member/login\"\r\n");
      out.write("\t\t\t\t\tmethod=\"POST\">\r\n");
      out.write("\t\t\t\t\t<!-- 사용자 정보가 url에 노출되면 곤란하므로 get이 아닌 post방식 -->\r\n");
      out.write("\t\t\t\t\t<table>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td><input \r\n");
      out.write("\t\t\t\t\t\t\t\ttype=\"text\" \r\n");
      out.write("\t\t\t\t\t\t\t\tname=\"memberId\" \r\n");
      out.write("\t\t\t\t\t\t\t\tid=\"memberId\" \r\n");
      out.write("\t\t\t\t\t\t\t\tplaceholder=\"아이디\" \r\n");
      out.write("\t\t\t\t\t\t\t\ttabindex=\"1\"\r\n");
      out.write("\t\t\t\t\t\t\t\tvalue=\"");
      out.print( saveId != null ? saveId : "" );
      out.write("\">\r\n");
      out.write("\t\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t\t<td><input type=\"submit\" value=\"로그인\" tabindex=\"3\"></td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td><input type=\"password\" name=\"password\" id=\"password\"\r\n");
      out.write("\t\t\t\t\t\t\tplaceholder=\"비밀번호\" tabindex=\"2\"></td>\r\n");
      out.write("\t\t\t\t\t\t\t<td></td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t\t\t<td colspan=\"2\">\r\n");
      out.write("\t\t\t\t\t\t\t\t<input \r\n");
      out.write("\t\t\t\t\t\t\t\t\ttype=\"checkbox\" \r\n");
      out.write("\t\t\t\t\t\t\t\t\tname=\"saveId\" \r\n");
      out.write("\t\t\t\t\t\t\t\t\tid=\"saveId\" \r\n");
      out.write("\t\t\t\t\t\t\t\t\t");
      out.print( saveId != null ? "checked" : "" );
      out.write("\r\n");
      out.write("\t\t\t\t\t\t\t\t\t/>\r\n");
      out.write("\t\t\t\t\t\t\t\t<label for=\"saveId\">아이디저장</label>\r\n");
      out.write("\t\t\t\t\t\t\t\t&nbsp;&nbsp;\r\n");
      out.write("\t\t\t\t\t\t\t\t<input \r\n");
      out.write("\t\t\t\t\t\t\t\t\ttype=\"button\" \r\n");
      out.write("\t\t\t\t\t\t\t\t\tvalue=\"회원가입\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tonclick=\"location.href='");
      out.print( request.getContextPath() );
      out.write("/member/memberEnroll';\"/>\r\n");
      out.write("\t\t\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t\t</table>\r\n");
      out.write("\t\t\t\t</form>\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t<!-- 상단의 if조건절을 닫는 부분 -->\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t");
 } else { 
      out.write("\r\n");
      out.write("\t\t\t<!-- 로그인 성공시 -->\r\n");
      out.write("\t\t\t<table id=\"logged-in\">\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>");
      out.print( memberLoggedIn.getMemberName() );
      out.write("님, 안녕하세요.</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t\t<tr>\r\n");
      out.write("\t\t\t\t\t<td>\r\n");
      out.write("\t\t\t\t\t\t<input \r\n");
      out.write("\t\t\t\t\t\t\ttype=\"button\" \r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"내 정보보기\"\r\n");
      out.write("\t\t\t\t\t\t\tonclick=\"location.href='");
      out.print( request.getContextPath() );
      out.write("/member/memberView?memberId=");
      out.print( memberLoggedIn.getMemberId() );
      out.write("';\"/>\r\n");
      out.write("\t\t\t\t\t\t<input \r\n");
      out.write("\t\t\t\t\t\t\ttype=\"button\" \r\n");
      out.write("\t\t\t\t\t\t\tvalue=\"로그아웃\"\r\n");
      out.write("\t\t\t\t\t\t\tonclick=\"location.href='");
      out.print( request.getContextPath() );
      out.write("/member/logout'; \"/>\r\n");
      out.write("\t\t\t\t\t</td>\r\n");
      out.write("\t\t\t\t</tr>\r\n");
      out.write("\t\t\t</table>\r\n");
      out.write("\t\t\t");
 } 
      out.write("\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<!-- 로그인메뉴 끝-->\r\n");
      out.write("\t\t\t<!-- 메인메뉴 시작 -->\r\n");
      out.write("\t\t\t<nav>\r\n");
      out.write("\t\t\t\t<ul class=\"main-nav\">\r\n");
      out.write("\t\t\t\t\t<li class=\"home\"><a href=\"#\">Home</a></li>\r\n");
      out.write("\t\t\t\t\t<li id=\"notice\"><a href=\"#\">공지사항</a></li>\r\n");
      out.write("\t\t\t\t\t<li id=\"board\"><a href=\"#\">게시판</a></li>\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t</nav>\r\n");
      out.write("\t\t\t<!-- 메인메뉴 끝-->\r\n");
      out.write("\t\t</header>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<section id=\"content\">");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\t\t\t<h2 align=\"center\" style=\"margin-top:200px;\">안녕하세요, MVC입니다.</h2>\t\r\n");
      out.write("\r\n");
      out.write("<!-- 아래의 경로는 자바코드로서의 접근만을 허용한다. 주소창에서 접근 불가.(WEB-INF) -->\r\n");
      out.write("\r\n");
      out.write("\t\t</section>\r\n");
      out.write("​\r\n");
      out.write("\t\t<footer>\r\n");
      out.write("\t\t\t<p>&lt;Copyright 1998-2021 <strong>KH정보교육원</strong>. All rights reserved.&gt;</p>\r\n");
      out.write("\t\t</footer>\r\n");
      out.write("\t</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}

package board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;
import common.util.MvcUtils;

@WebServlet("/board/boardList")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BoardService boardService = new BoardService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//전체를 try절로 감싸기
		//예외처리는 servlet에서 이루어진다.
		try {
		
		//1. 사용자 입력값 처리 cpage, numPerPage = 5
		int cpage = 1;
		
		try {
			cpage = Integer.parseInt(request.getParameter("cpage"));	
		} catch(NumberFormatException e) {
			
		}
		int numPerPage = 10;
		
		//2. 업무로직 : 각 페이지에 해당하는 게시글 가져오기
		List<Board> boardList = boardService.selectBoardList(cpage, numPerPage);
		System.out.println(boardList);
		
		//url 페이지링크를 클릭했을때 이동할 주소
		String url = request.getRequestURI(); // /mvc/board/boardList
		
		//totalContents 총게시물수
		int totalboardContents = boardService.selectTotalBoards();
		System.out.println("totalboardContents@servlet =" + totalboardContents);
		
		//페이지바 작성
		String pageBar = MvcUtils.getPageBar(totalboardContents, cpage, numPerPage, url);

		
		//3. view단 forwarding처리 /WEB-INF/views/board/boardList.jsp
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageBar", pageBar);
		request.getRequestDispatcher("/WEB-INF/views/board/boardList.jsp")
			   .forward(request, response);
		
		//jsp에 전달한 값은 request속성을 이용한다.
		
		} catch(Exception e) {
			//예외처리 
			e.printStackTrace();
			
			//WAS에게 예외를 다시 던지기
			throw e;
		}
		
	}

}

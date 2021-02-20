package board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.startup.SetAllPropertiesRule;

import board.model.exception.BoardException;
import board.model.service.BoardService;
import board.model.vo.Board;
import board.model.vo.BoardComment;

/**
 * Servlet implementation class BoardViewServlet
 */
@WebServlet("/board/boardView")
public class BoardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			//1. 사용자 입력값 처리
			int boardNo = 0;
			try {
				boardNo = Integer.parseInt(request.getParameter("boardNo"));
			} catch (NumberFormatException e) {
				throw new BoardException("유효한 게시글 번호가 아닙니다 : \"" + request.getParameter("boardNo") + "\"", e);
			}
			
			//2. 업무로직 : 게시글 조회
			//조회수 증가
			int result = boardService.updateBoardReadCount(boardNo);// +1
			Board board = boardService.selectOne(boardNo);
			
			if(board == null)
				throw new BoardException("해당 게시글이 존재하지 않습니다. : " + boardNo);
			
			//content 추가처리 
			//XSS공격대비
			String boardContent = board.getBoardContent()
									   .replaceAll("<", "&lt;")
									   .replaceAll(">", "&gt;");
			//개행문자
			boardContent = boardContent.replaceAll("\\n", "<br>");
			
			board.setBoardContent(boardContent);
			System.out.println("board@BoardViewServlet = " + board);
			
			//댓글목록 List<BoardComment>
			List<BoardComment> commentList = boardService.selectBoardCommentList(boardNo);
			System.out.println("board@BoardViewServlet = " + commentList);
			//3. view단처리 : jsp forwarding
			
			request.setAttribute("board", board);
			request.setAttribute("commentList", commentList);
			
			request.getRequestDispatcher("/WEB-INF/views/board/boardView.jsp")
				   .forward(request, response);
		
		} catch (BoardException e) {
			e.printStackTrace();
			throw e;
		}
	
	
	}

}

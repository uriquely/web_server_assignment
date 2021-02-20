package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.BoardComment;

/**
 * Servlet implementation class BoardCommentInsertServlet
 */
@WebServlet("/board/boardCommentInsert")
public class BoardCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.파라미터 처리
		int boardRef = Integer.parseInt(request.getParameter("boardRef"));
		int boardCommentLevel = Integer.parseInt(request.getParameter("boardCommentLevel"));
		int boardCommentRef = Integer.parseInt(request.getParameter("boardCommentRef"));
		String boardCommentWriter = request.getParameter("boardCommentWriter");
		String boardCommentContent = request.getParameter("boardCommentContent");
		
		BoardComment bc = new BoardComment(0, boardCommentLevel, boardCommentWriter, boardCommentContent, boardRef, boardCommentRef, null);
		System.out.println("bc@BoardCommentInsertServlet = " + bc);
		
		//2. 업무로직 : 댓글 등록
		int result = boardService.insertBoardComment(bc);
		String msg = result > 0 ? "댓글 등록 성공!" : "댓글 등록 실패!";
		
		//3. 메세지 session속성 저장 및  리다이렉트처리
		request.getSession().setAttribute("msg", msg);
		response.sendRedirect(request.getContextPath() + "/board/boardView?boardNo=" + boardRef);
	}

}







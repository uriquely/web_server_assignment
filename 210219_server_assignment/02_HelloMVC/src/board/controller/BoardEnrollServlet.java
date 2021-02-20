package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import board.model.service.BoardService;
import board.model.vo.Board;
import common.MvcFileRenamePolicy;

/**
 * Servlet implementation class BoardEnrollServlet
 */
@WebServlet("/board/boardEnroll")
public class BoardEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/board/boardEnroll.jsp")
			   .forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* 파일이 포함된 사용자 요청 처리 MultipartRequest객체 생성 */
		/*
		 new MultipartRequest(
		 			HttpServletRequest request, 
		 			String saveDirectory, 		//업로드파일의 저장경로(절대경로)
		 			int maxPostSize, 			//최대크기제한 10mb
		 			String encoding, 			//인코딩
		 			FileRenamePolicy policy 	//파일이름 재지정 정책 객체
		 		)
		 */
		//application : WAS실행시부터 종료시까지 운영되는 객체
		String saveDirectory = getServletContext().getRealPath("/upload/board");// / -> Web Root Directory
		System.out.println("saveDirectory@BoardEnrollServlet = " + saveDirectory);
		
		//byte단위 : 1mb = 1kb * 1kb
		int maxPostSize = 10 * 1024 * 1024;
		
		String encoding = "utf-8";
		
		//중복파일에 대해서 number부여
//		FileRenamePolicy policy = new DefaultFileRenamePolicy();
		FileRenamePolicy policy = new MvcFileRenamePolicy();
		
		//MultipartRequest객체 생성
		MultipartRequest multipartReq = 
				new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);
		
		//MultipartRequest를 사용하면, 기존 request로 부터 사용자 입력값을 가져올 수 없다.
		//1.사용자 입력값으로 Board객체 생성 
		//boardTitle boardWriter boardContent
		String boardTitle = multipartReq.getParameter("boardTitle");
		String boardWriter= multipartReq.getParameter("boardWriter");
		String boardContent = multipartReq.getParameter("boardContent");
		String boardOriginalFileName = multipartReq.getOriginalFileName("upFile");
		String boardRenamedFileName = multipartReq.getFilesystemName("upFile");
		
		Board board = 
				new Board(0, boardTitle, boardWriter, 
						boardContent, boardOriginalFileName, boardRenamedFileName, 
						null, 0);
		System.out.println("board-before@servlet = " + board);
		
		//2. 업무로직 : Board객체 db저장 요청
		//DML처리결과는  int타입
		int result = boardService.insertBoard(board);
		System.out.println("board-after@servlet = " + board);
		
		String msg = result > 0 ? "게시글 등록 성공!" : "게시글 등록 실패!"; 
		//board.getBoardNo()를 통해서 새로 등록된 게시글 번호 가져오기
		String location = result > 0 ?
							request.getContextPath() + "/board/boardView?boardNo=" + board.getBoardNo() : 
								request.getContextPath() + "/board/boardList";
							
				
		//3.사용자 피드백(msg) 및 redirect처리 (/mvc/board/boardList)
		//DML이후 반드시 요청url을 변경할 것
		request.getSession().setAttribute("msg", msg);
		response.sendRedirect(location);
		
	}

}






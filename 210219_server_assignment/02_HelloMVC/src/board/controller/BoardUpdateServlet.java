package board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import board.model.exception.BoardException;
import board.model.service.BoardService;
import board.model.vo.Board;
import common.MvcFileRenamePolicy;

/**
 * Servlet implementation class BoardUpdateServlet
 */
@WebServlet("/board/boardUpdate")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.parameterHandling
		int boardNo;
		try{
			boardNo = Integer.parseInt(request.getParameter("boardNo"));
		}catch(NumberFormatException e){
			throw new BoardException("유효하지 않은 게시글입니다.");
		}
		//2.businessLogic
		Board b = boardService.selectOne(boardNo);
		
		//3.view
		request.setAttribute("board", b);
		request.getRequestDispatcher("/WEB-INF/views/board/boardUpdate.jsp").forward(request, response);
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
		
		//byte단위 : 1mb = 1kb * 1kb
		int maxPostSize = 10 * 1024 * 1024;
		
		String encoding = "utf-8";
		
		//중복파일에 대해서 number부여
		FileRenamePolicy policy = new MvcFileRenamePolicy();
		
		//MultipartRequest객체 생성
		MultipartRequest multipartReq = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, policy);
		
		//MultipartRequest를 사용하면, 기존 request로 부터 사용자 입력값을 가져올 수 없다.
		//1.사용자 입력값으로 Board객체 생성 
		//boardTitle boardWriter boardContent
		int boardNo = Integer.parseInt(multipartReq.getParameter("boardNo"));
		String boardTitle = multipartReq.getParameter("boardTitle");
		String boardWriter= multipartReq.getParameter("boardWriter");
		String boardContent = multipartReq.getParameter("boardContent");
		String boardOriginalFileName = multipartReq.getOriginalFileName("upFile");
		String boardRenamedFileName = multipartReq.getFilesystemName("upFile");
		String delFile = multipartReq.getParameter("delFile");
		
		//기존첨부파일정보
		String oldBoardOriginalFileName = multipartReq.getParameter("oldBoardOriginalFileName");
		String oldBoardRenamedFileName = multipartReq.getParameter("oldBoardRenamedFileName");
		
		
		//기존파일 삭제처리
		if(oldBoardOriginalFileName != null) {
			
			//업로드한 파일 가져오기
			File upFile = multipartReq.getFile("upFile");
			if(upFile != null || delFile != null) {
				File realDelFile = new File(saveDirectory, delFile);
				boolean bool = realDelFile.delete();
				System.out.println(delFile + " : " + (bool ? "기존 파일 삭제 성공!" : "기존 파일 삭제 실패!"));
			}
			else {
				//기존파일정보를 유지
				boardOriginalFileName = oldBoardOriginalFileName;
				boardRenamedFileName = oldBoardRenamedFileName;
			}
		}
		
		
		Board board = 
				new Board(boardNo, boardTitle, boardWriter, 
						boardContent, boardOriginalFileName, boardRenamedFileName, 
						null, 0);
		
		//2. 업무로직 : Board객체 db수정 요청
		int result = boardService.updateBoard(board);
		String msg = result > 0 ? "게시글 수정 성공!" : "게시글 수정 실패!"; 
		String location = request.getContextPath() + "/board/boardView?boardNo=" + boardNo;
							
				
		//3.사용자 피드백(msg) 및 redirect처리
		//DML이후 반드시 요청url을 변경할 것
		request.getSession().setAttribute("msg", msg);
		response.sendRedirect(location);
		
	
	}

}

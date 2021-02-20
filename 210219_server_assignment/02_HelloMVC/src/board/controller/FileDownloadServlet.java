package board.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/board/fileDownload")
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		//1.사용자 입력값 처리
		String oName = request.getParameter("oName");
		String rName = request.getParameter("rName");
		
		//2. 파일입력스트림/응답출력스트림 준비
		// /WebContent/upload/board/..... -> 절대경로
		String saveDirectory = getServletContext().getRealPath("/upload/board");
		File downFile = new File(saveDirectory, rName);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(downFile));
		
		//응답출력스트림 준비
		//응답에 대해서 쓰기작업을 할 스트림은 만들 필요가 없다.(이미 있어서, 리스폰스로 가져와서 사용)
		BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
		
		//3. 응답헤더 작성 : 파일타입, 파일명
		response.setContentType("application/octet-stream"); //이진처리
		
		//tomcat의 인코딩 iso-8859-1로 변환 후 전달
		oName = new String(oName.getBytes("utf-8"), "iso-8859-1");
		System.out.println("oName = " + oName);
		response.setHeader("Content-Disposition", "attachment;filename=" + oName); //다운로드지시
		//다운로드 받으면 파일네임을 oName으로 하라고 명시해주는것
		
		
		//4. 파일 읽어서 응답 쓰기
		int data = -1;
		while((data = bis.read()) != -1) {
			bos.write(data);
		}
		
		
		//5. 자원반납
		bis.close();
		bos.close();
	}

}

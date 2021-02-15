package board.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import board.model.vo.Board;

public class BoardDao {

	private Properties prop = new Properties();

	/**
	 * build-path의 board-query.properties의 내용을 읽어와 필드 prop에 저장한다.
	 */
	public BoardDao() {
		
		String fileName = "/sql/board/board-query.properties";
		String absPath = BoardDao.class.getResource(fileName).getPath();
		
		try {
			prop.load(new FileReader(absPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Board> selectBoardList(Connection conn, int cpage, int numPerPage) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectBoardList");
		List<Board> boardList = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, (cpage - 1) * numPerPage + 1);//시작 rnum
			pstmt.setInt(2, cpage * numPerPage);//마지막 rnum
			
			rset = pstmt.executeQuery();
			
			boardList = new ArrayList<Board>();
			
			while(rset.next()) {
				Board board = new Board();
				
				board.setBoardNo(rset.getInt("board_no"));
                board.setBoardTitle(rset.getString("board_title"));
                board.setBoardWriter(rset.getString("board_writer"));
                board.setBoardContent(rset.getString("board_content"));
                board.setBoardOriginalFileName(rset.getString("board_original_filename"));
                board.setBoardRenamedFileName(rset.getString("board_renamed_filename"));
                board.setBoardDate(rset.getDate("board_date"));
                board.setBoardReadCount(rset.getInt("board_read_count"));
                
                boardList.add(board);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			//5. 자원반납
			close(rset);
			close(pstmt);
		}
		
		return boardList;
	}

	public int selectTotalBoards(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int totalboardContents = 0;
		String sql = prop.getProperty("selectTotalBoards");
		//select count(*) from member
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				totalboardContents = rset.getInt(1);//컬럼순서로 가져옴.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return totalboardContents;
	}
}
package board.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import board.model.dao.BoardDao;
import board.model.vo.Board;

public class BoardService {
	
	private BoardDao boardDao= new BoardDao();

	public List<Board> selectBoardList(int cpage, int numPerPage) {
		Connection conn = getConnection();
		List<Board> boardList = boardDao.selectBoardList(conn, cpage, numPerPage);
		close(conn);
		return boardList;
	}

	public int selectTotalBoards() {
		Connection conn = getConnection();
		int totalboardContents = boardDao.selectTotalBoards(conn);
		close(conn);
		return totalboardContents;
	}

}

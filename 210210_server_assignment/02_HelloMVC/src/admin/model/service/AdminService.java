package admin.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import admin.model.dao.AdminDao;
import member.model.vo.Member;

public class AdminService {

	private AdminDao adminDao = new AdminDao();

	public List<Member> selectList() {
		Connection conn = getConnection();
		List<Member> list = adminDao.selectList(conn);
		close(conn);
		return list;
	}

	public int updateMemberRole(String memberId, String memberRole) {
		Connection conn = getConnection();
		int result = adminDao.updateMemberRole(conn, memberId, memberRole);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

}

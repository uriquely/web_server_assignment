package admin.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import admin.model.dao.AdminDao;
import member.model.vo.Member;

public class AdminService {

	private AdminDao adminDao = new AdminDao();

	public List<Member> selectList(int cpage, int numPerPage) {
		Connection conn = getConnection();
		List<Member> list = adminDao.selectList(conn, cpage, numPerPage);
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

	public int selectTotalMembers() {
		Connection conn = getConnection();
		int totalContents = adminDao.selectTotalMember(conn);
		close(conn);
		return totalContents;
	}

	public List<Member> selectMembersBy(Map<String, Object> param) {
		Connection conn = getConnection();
		List<Member> list= adminDao.selectMembersBy(conn, param);
		close(conn);
		return list;
	}

	public int selectTotalMembersBy(Map<String, Object> param) {
		Connection conn = getConnection();
		int totalContents = adminDao.selectTotalMembersBy(conn, param);
		close(conn);
		return totalContents;
	}
}

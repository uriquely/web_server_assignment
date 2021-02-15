package member.model.service;

import java.sql.Connection;

import common.JDBCTemplate;
import member.model.dao.MemberDao;
import member.model.vo.Member;
import static common.JDBCTemplate.*;

/**
 * Connection객체생성
 * Dao에 업무로직 하달
 * 트랜잭션처리
 * 자원반납
 */
public class MemberService {
	
	public static final String ADMIN_MEMBER_ROLE = "A";//관리자 롤
	public static final String USER_MEMBER_ROLE = "U";//일반사용자 롤
	
	private MemberDao memberDao = new MemberDao();

	public Member selectOne(String memberId) {
		//1.Connection객체 생성
		Connection conn = getConnection();
		//2.dao요청
		Member member =  memberDao.selectOne(conn, memberId);
		//3.트랜잭션관리(DML만)
		//4.자원반납
		close(conn);
		return member;
	}
	
	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.insertMember(conn, member);
		if(result>0)
			commit(conn);
		else 
			rollback(conn);
		close(conn);
		return result;
	}
	
	public int updateMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.updateMember(conn, member);
		if(result>0)
			commit(conn);
		else 
			rollback(conn);
		close(conn);
		return result;
	}
	
	
	public int deleteMember(String memberId) {
		Connection conn = getConnection();
		int result = memberDao.deleteMember(conn, memberId);
		if(result>0)
			commit(conn);
		else 
			rollback(conn);
		close(conn);
		return result;
	}
	
	public int updatePassword(Member member) {
		Connection conn = getConnection();
		int result = memberDao.updatePassword(conn, member);
		if(result>0)
			commit(conn);
		else 
			rollback(conn);
		close(conn);
		return result;
	}




}

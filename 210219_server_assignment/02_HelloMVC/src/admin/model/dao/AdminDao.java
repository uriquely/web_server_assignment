package admin.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import member.model.vo.Member;

public class AdminDao {
	
	private Properties prop = new Properties();

	public AdminDao() {
		String fileName = "/sql/admin/admin-query.properties";
		String absPath = AdminDao.class.getResource(fileName).getPath();
		try {
			prop.load(new FileReader(absPath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Member> selectList(Connection conn, int cpage, int numPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectPagedList");
		List<Member> list = null;
		
		try {
			//1. PreparedStatement객체 생성
			//2. 미완성 쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			//1 : 1 ~ 10
			//2 : 11 ~ 20
			//3 : 21 ~ 30
			//...
			//12 : 111 ~ 120
			pstmt.setInt(1, (cpage - 1) * numPerPage + 1);//시작 rnum
			pstmt.setInt(2, cpage * numPerPage);//마지막 rnum
			
			//3. 실행 및 ResultSet처리
			rset = pstmt.executeQuery();
			//4. Member --> List에 추가
			list = new ArrayList<>();
			while(rset.next()) {
				Member member = new Member();
				member.setMemberId(rset.getString("member_id"));
				member.setPassword(rset.getString("password"));
				member.setMemberName(rset.getString("member_name"));
				member.setMemberRole(rset.getString("member_role"));
				member.setGender(rset.getString("gender"));
				member.setBirthDay(rset.getDate("birthday"));
				member.setEmail(rset.getString("email"));
				member.setPhone(rset.getString("phone"));
				member.setAddress(rset.getString("address"));
				member.setHobby(rset.getString("hobby"));
				member.setEnrollDate(rset.getDate("enroll_date"));
				list.add(member);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//5. 자원반납
			close(rset);
			close(pstmt);
		}
		return list;
	}
	
	public int updateMemberRole(Connection conn, String memberId, String memberRole) {
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = prop.getProperty("updateMemberRole");
		
		try {
			//1. PreparedStatement객체 생성 및 쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberRole);
			pstmt.setString(2, memberId);
			
			//2. 실행
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//3. 자원반납
			close(pstmt);
		}
		return result;
	}

	public List<Member> selectMembersBy(Connection conn, Map<String, Object> param) {
		List<Member> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectPagedMembersBy");
		//select * from (select M.*, row_number() over(order by enroll_date desc) rnum from member M where # like ? ) where rnum between ? and ?
		switch((String)param.get("searchType")) {
		case "memberId" : sql = sql.replace("#", "member_id"); break;
		case "memberName" : sql = sql.replace("#", "member_name"); break;
		case "gender" : sql = sql.replace("#", "gender"); break;
		}
		
		try{
			//미완성쿼리문을 가지고 객체생성. 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + param.get("searchKeyword") + "%");
			
			//1 : 1 ~ 10
			//2 : 11 ~ 20
			int cpage = (int)param.get("cpage");
			int numPerPage = (int)param.get("numPerPage");
			pstmt.setInt(2, (cpage - 1) * numPerPage + 1);
			pstmt.setInt(3, cpage * numPerPage);
			
			
			//쿼리문실행
			//완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			rset = pstmt.executeQuery();
			
			while(rset.next()){
				Member m = new Member();
				//컬럼명은 대소문자 구분이 없다.
				m.setMemberId(rset.getString("MEMBER_ID"));
				m.setPassword(rset.getString("PASSWORD"));
				m.setMemberName(rset.getString("MEMBER_NAME"));
				m.setMemberRole(rset.getString("MEMBER_ROLE"));
				m.setGender(rset.getString("GENDER"));
				m.setBirthDay(rset.getDate("BIRTHDAY"));
				m.setEmail(rset.getString("EMAIL"));
				m.setPhone(rset.getString("PHONE"));
				m.setAddress(rset.getString("ADDRESS"));
				m.setHobby(rset.getString("HOBBY"));
				m.setEnrollDate(rset.getDate("ENROLL_DATE"));
				
				list.add(m);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int selectTotalMember(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int totalContents = 0;
		String sql = prop.getProperty("selectTotalMember");
		//select count(*) from member
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				totalContents = rset.getInt(1);//컬럼순서로 가져옴.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return totalContents;
	}

	public int selectTotalMembersBy(Connection conn, Map<String, Object> param) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int totalContents = 0;
		String sql = prop.getProperty("selectTotalMembersBy");
		//select count(*) from member where # like ?
		
		switch((String)param.get("searchType")) {
		case "memberId" : sql = sql.replace("#", "member_id"); break;
		case "memberName" : sql = sql.replace("#", "member_name");  break;
		case "gender" : sql = sql.replace("#", "gender"); break;
		}
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + param.get("searchKeyword") + "%");
			rset = pstmt.executeQuery();
			if(rset.next()) {
				totalContents = rset.getInt(1);//컬럼순서로 가져옴.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return totalContents;
	}
	
}










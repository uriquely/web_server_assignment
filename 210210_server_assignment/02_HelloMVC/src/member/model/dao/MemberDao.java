package member.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import member.model.vo.Member;

import static common.JDBCTemplate.close;

/**
 * 
 * DAO Data Access Object
 * 
 * PreparedStatement객체 생성(쿼리 필요)
 * 쿼리 실행 및 결과 Service단으로 리턴
 * 
 * - DML int 리턴
 * - DQL ResultSet 리턴 -> 자바객체로 데이터 이전
 * 
 * 자원반납
 * 
 */

public class MemberDao {
	
	
	//java.util 프로퍼티즈 임포트

	private Properties prop = new Properties();
	
	//객체 생성시 member-querty.properties의 내용을 읽어다 prop필드에 저장
	public MemberDao() {
		
		//resources폴더
		String fileName = "/sql/member/member-query.properties";
		
		//절대경로를 getPath()를 통하여 문자열로 전달받는다.
		String path = MemberDao.class.getResource(fileName).getPath();
		
		try {
			prop.load(new FileReader(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//System.out.println("path@MemberDao = " + path);
		//System.out.println("prop@MemberDao = " + prop);
	}

	public Member selectOne(Connection conn, String memberId) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectOne");
		Member member = null;
		
		try {
			
			//1. PreparedStatement 객체생성(미완성쿼리 값대입)
			pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, memberId);
			
			//2. Statement실행 및 결과처리: ResultSet -> Member객체로 옮겨 담는 작업
			rset = pstmt.executeQuery();
			
			//resultSet 결과를 담아보자
			while(rset.next()) {
				member = new Member();
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
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			
			//3. 자원반납(ResultSet, PreparedStatement) connection은 여기서 반납하지 않는다.(서비스가 주도적으로 닫는다.)
			close(rset);
			close(pstmt);
		}

		return member;
	}

	public int joinMember(Connection conn, Member member) {
		
	      PreparedStatement pstmt = null;
	      int result = 0;
	      String sql = prop.getProperty("joinMember");
	      
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	      Date time = new Date();
	      String date = sdf.format(time);
	      java.sql.Date enrollDate = java.sql.Date.valueOf(date);

	      try {
		    //쿼리문 생성 및 Statement객체(PreperedStatement) 생성
	    	//statement를 상속받는 인터페이스로 SQL구문을 실행시키는 기능을 갖는 객체
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
	        pstmt.setString(3, member.getMemberName());
	        pstmt.setString(4, member.getMemberRole());
	        pstmt.setString(5, member.getGender());
	        pstmt.setDate(6, member.getBirthDay());
	        pstmt.setString(7, member.getEmail());
	        pstmt.setString(8, member.getPhone());
	        pstmt.setString(9, member.getAddress());
	        pstmt.setString(10, member.getHobby());
	        pstmt.setDate(11, enrollDate);
	        
			//쿼리전송(실행) - 결과값 받아내기
	        result = pstmt.executeUpdate(); 
	        //DML인 경우 executeUpdate
	        
		} catch (Exception e) {
			e.getStackTrace();
			
		} finally {
			//5. 자원반납(PreparedStetement, ResultSet)
			close(pstmt);
		}
		
		return result;
	}

	public int updateMember(Connection conn, Member member) {
		
		//sql구문을 실행하기 위한 객체
		int result = 0;
		PreparedStatement pstmt = null;
	    String sql = prop.getProperty("updateMember");
	     
	    try {
	    	pstmt = conn.prepareStatement(sql);
	    	
//			pstmt.setString(1, member.getPassword());
	        pstmt.setString(1, member.getMemberName());
	        pstmt.setString(2, member.getGender());
	        pstmt.setDate(3, member.getBirthDay());
	        pstmt.setString(4, member.getEmail());
	        pstmt.setString(5, member.getPhone());
	        pstmt.setString(6, member.getAddress());
	        pstmt.setString(7, member.getHobby());
	        pstmt.setString(8, member.getMemberId());
	        
	        result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			
			close(pstmt);
		}
	     return result;
	}
	
	public int deleteMember(Connection conn, String memberId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("deleteMember"); 

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updatePassword(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("updatePassword"); 

		try {
			//미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			//쿼리문미완성
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getMemberId());
			
			//쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			//DML은 executeUpdate()
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			
			close(pstmt);
		}
		
		return result;
	}

}

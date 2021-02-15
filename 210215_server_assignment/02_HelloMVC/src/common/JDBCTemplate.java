package common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DB Connection객체생성
 * 트랜잭션처리
 * 자원반납 
 * 관련한 공통코드를 작성(예외처리 포함)
 * 
 * static메소드로 작성해서 객체생성없이 바로 호출
 */
public class JDBCTemplate {
	
	static String driverClass;
	static String url;
	static String user;
	static String password;
	
	static {
		Properties prop = new Properties();
		try {
			//dynamic web project
			String fileName = "/data-source.properties";//classpath의 루트디렉토리
			String path = JDBCTemplate.class.getResource(fileName).getPath();//절대경로
			prop.load(new FileReader(path));
			//System.out.println("path@JDBCTemplate = " + path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		driverClass = prop.getProperty("driverClass");
		url = prop.getProperty("url");
		user = prop.getProperty("user");
		password = prop.getProperty("password");
		
		//1. jdbc driver 클래스 등록(dbms별로 제공) : 최초 1회
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			//2. db connection객체 생성 : dbserver url, user, password
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return conn;
	}

	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed())
				conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed())
				conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(PreparedStatement pstmt) {
		try {
			if(pstmt != null && !pstmt.isClosed())
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed())
				rset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}






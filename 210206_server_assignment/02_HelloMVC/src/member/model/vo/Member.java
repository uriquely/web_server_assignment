package member.model.vo;

import java.io.Serializable;
import java.sql.Date;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;


/**
 * 
 * member 테이블의 한 행에 상응하는 vo클래스
 * 
 * (dto, entity, bean)
 * 
 * 
 * HttpSessionBindingListener 인터페이스 구현
 * 현재 객체가 session에 속성으로 등록 혹은 해제되는 이벤트 감지처리
 *
 */
public class Member implements Serializable, HttpSessionBindingListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	MEMBER_ID   NOT NULL VARCHAR2(15)  
	PASSWORD    NOT NULL VARCHAR2(300) 
	MEMBER_NAME NOT NULL VARCHAR2(30)  
	MEMBER_ROLE NOT NULL CHAR(1)       
	GENDER               CHAR(1)       
	BIRTHDAY             DATE          
	EMAIL                VARCHAR2(100) 
	PHONE       NOT NULL CHAR(11)      
	ADDRESS              VARCHAR2(200) 
	HOBBY                VARCHAR2(100) 
	ENROLL_DATE          DATE        
	*/
	
	private String memberId;	//필수
	private String password;	//필수
	private String memberName;	//필수
	private String memberRole;	//필수
	private String gender;
	
	//sql.date로 임포트
	private Date birthDay;
	
	private String email;
	private String phone;		//필수
	private String address;
	private String hobby;
	private Date enrollDate;
	
	
	public Member() {
		super();
	}


	public Member(String memberId, String password, String memberName, String memberRole, String gender, Date birthDay,
			String email, String phone, String address, String hobby, Date enrollDate) {
		super();
		this.memberId = memberId;
		this.password = password;
		this.memberName = memberName;
		this.memberRole = memberRole;
		this.gender = gender;
		this.birthDay = birthDay;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
		this.enrollDate = enrollDate;
	}


	public String getMemberId() {
		return memberId;
	}


	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getMemberName() {
		return memberName;
	}


	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}


	public String getMemberRole() {
		return memberRole;
	}


	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public Date getBirthDay() {
		return birthDay;
	}


	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getHobby() {
		return hobby;
	}


	public void setHobby(String hobby) {
		this.hobby = hobby;
	}


	public Date getEnrollDate() {
		return enrollDate;
	}


	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}


	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", password=" + password + ", memberName=" + memberName
				+ ", memberRole=" + memberRole + ", gender=" + gender + ", birthDay=" + birthDay + ", email=" + email
				+ ", phone=" + phone + ", address=" + address + ", hobby=" + hobby + ", enrollDate=" + enrollDate + "]";
	}


	/**
	 *  세션에 memberLoggedIn등록되는 경우
	 */
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		
		//System.out.println("[" + memberId + "]님이 로그인 하였습니다.");
	}


	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		
		//System.out.println("[" + memberId + "]님이 로그아웃 하였습니다.");
	}
	
	
}

package common.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
	서버에서 운영중인 세션객체의 수를 관리하는 리스너
*/

@WebListener
public class SessionCounterListener  implements HttpSessionListener{
	
	
	private static int activeSessions;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
		activeSessions++;
		System.out.println("[[ 세션 생성! - 현재 세션수 : " + activeSessions + " ]]");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
		//음수가 떨어지는 경우를 대비하여 리미트를 설정하는 것
		if(activeSessions > 0) 
			activeSessions--;		
		
		System.out.println("[[ 세션 반환! - 현재 세션수 : " + activeSessions + " ]]");
	}

}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userlogin.web;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @파일명        : SessionConfig.java
 * @프로그램 설명 : 통합 로그인
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 6. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 6.
 * @수정내용      : 
 * -                
 * -                
 */
//@WebListener
public class SessionConfig implements HttpSessionListener {
	
	private static final Logger log = LoggerFactory.getLogger(SessionConfig.class);
	
	private static final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();
	
	// 중복 로그인 지우기
	public synchronized static String getSessionIdCheck(String type, String compareId) {
		String result = "";
		for (String key : sessions.keySet()) {
			try {
				HttpSession hs = sessions.get(key);
				if (hs != null && hs.getAttribute(type) != null
						&& hs.getAttribute(type).toString().equals(compareId)) {
					result = key;
					break;
				}
			} catch (Exception e) {
				log.info("#### error 1 : " + e.getMessage());
				e.printStackTrace();
				continue;
			}
		}
		removeSessionForDoubleLogin(result);
		return result;
	}
	
	private static void removeSessionForDoubleLogin(String userId) {
		log.info("#### sessionRemove userId : " + userId);
		if (userId != null && userId.length() > 0) {
			try {
				sessions.get(userId).invalidate();
			} catch (Exception e) {
				log.info("#### error 2 : " + e.getMessage());
				e.printStackTrace();
			}
			sessions.remove(userId);
		}
	}
	
	/**
	 * @Method명   : sessionCreated
	 * @param se
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2021. 12. 6. 
	 * @Method설명 :
	 */
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		log.info("#### sessionCreated : " + se.getSession().getId());
		sessions.put(se.getSession().getId(), se.getSession());
	}

	/**
	 * @Method명   : sessionDestroyed
	 * @param se
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2021. 12. 6. 
	 * @Method설명 :
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		log.info("#### sessionDestroyed : " + se.getSession().getId());
		if (sessions.get(se.getSession().getId()) != null) {
			//try {
				//sessions.get(se.getSession().getId()).invalidate();
			//} catch (IllegalStateException e) {}
			sessions.remove(se.getSession().getId());
		}
	}

}

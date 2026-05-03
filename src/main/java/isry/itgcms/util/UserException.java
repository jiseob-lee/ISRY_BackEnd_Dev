/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.util;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import egovframework.com.cmm.EgovMessageSource;

/**
 * @파일명        : UserException.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 6. 23. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 6. 23.
 * @수정내용      : 
 * -                
 * -                
 */
public class UserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 636826445850390923L;

	//@Resource(name = "msg")
	//private static EgovMessageSource msg;

	private String message;
	
	public UserException() {}
	
	public UserException(String id) {
		super(id);
		//super(id, null, true, false); // 스택트레이스 안 나오게 하는 조건

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"/egovframework/spring/context-common.xml", 
				"/egovframework/spring/context-mapper.xml", 
				"/egovframework/spring/context-datasource.xml",
				"/egovframework/spring/context-redis.xml");
		
		EgovMessageSource msg = context.getBean(EgovMessageSource.class);
		
		
		if (id.endsWith("권한이 없습니다.")) {
			this.message = id;
			
		} else {
			//msg = new EgovMessageSource();
			this.message = msg.getMessage(id);
		}
		
		((ConfigurableApplicationContext)context).close();
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}

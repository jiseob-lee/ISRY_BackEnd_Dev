/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.gitple.service;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;


/**
 * @파일명        : GitpleEventMapper.java
 * @프로그램 설명 	: 깃플챗 이벤트를 저장한다.
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 5. 26. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 5. 26.
 * @수정내용      : 
 * -                
 * -                
 */
public interface GitpleEventService {

	public void insertAYBData(JSONObject body) throws Exception;
	
	public void gitpleSearch() throws Exception;
	
	public String existsGitpleID(HttpServletRequest request) throws Exception;
	
	public String gitpleManager(HttpServletRequest request) throws Exception;
	
	public String gitpleId(String qtoken) throws Exception;
	
	public String gitpleLogout(HttpServletRequest request) throws Exception;
}

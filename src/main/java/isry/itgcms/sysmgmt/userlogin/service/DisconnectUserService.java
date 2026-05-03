/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userlogin.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : DisconnectUserService.java
 * @프로그램 설명 : 사용자 접속 차단
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 3. 31. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 3. 31.
 * @수정내용      : 
 * -                
 * -                
 */
public interface DisconnectUserService {
	
	public List<Map<String, Object>> selectDisconnectUser(Map<String, Object> dmSearchMap) throws Exception;
	
	public void saveDisconnectUser(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Integer selectDisconnectUserCount(Map<String, Object> map) throws Exception;
	
	public Map<String, String> selectDisconnectUserInfo(String userId) throws Exception;
	
}

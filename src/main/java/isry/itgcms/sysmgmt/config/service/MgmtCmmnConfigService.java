/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.config.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : MgmtCmmnConfigService.java
 * @프로그램 설명 : 환경설정 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 30. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 30.
 * @수정내용      : 
 * -                
 * -                
 */
public interface MgmtCmmnConfigService {
	
	public List<Map<String, Object>> selectConfigList(String stngId) throws Exception;
	
	public void insertConfigList(HttpServletRequest request, Map<String, String> map) throws Exception;
	
	public void updateConfigList(HttpServletRequest request, Map<String, String> map) throws Exception;
	
	public void deleteConfigList(String stngId) throws Exception;
	
	public List<Map<String, Object>> selectConfigListLog(String stngId) throws Exception;
	
	public void insertConfigListLog(HttpServletRequest request, Map<String, String> map) throws Exception;
	
}

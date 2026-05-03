/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.consttsincry.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface ConsttSincryService {
	 	
	/**
	 * 
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : 
	 * @작성일     : 2022. 3. 22. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCombo1List(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectCombo3List(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectConsttSincryCnsltntList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectConsttSincryDalyList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception;
	
}

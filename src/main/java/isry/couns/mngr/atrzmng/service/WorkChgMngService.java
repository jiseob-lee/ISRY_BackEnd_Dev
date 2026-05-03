/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.atrzmng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface WorkChgMngService {
	
	List<Map<String, Object>> searchComboBoxAprv(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWorkChgMngList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWorkChgMngDetail(Map<String, Object> mapParam) throws Exception;	
		
	int processWorkChgMng(Map<String, Object> mapParam) throws Exception;
	
	String processWorkChgMngBatch(Map<String, Object> mapParam) throws Exception;
	
	int processWorkChgMngSms1(Map<String, Object> mapParam) throws Exception;
	
	int processWorkChgMngSms2(Map<String, Object> mapParam) throws Exception;
	
	int processWorkChgMngSms3(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWorkChgMngSms(Map<String, Object> mapParam) throws Exception;
	
	
}

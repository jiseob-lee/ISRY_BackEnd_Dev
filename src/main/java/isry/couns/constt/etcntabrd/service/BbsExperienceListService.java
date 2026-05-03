/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.etcntabrd.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface BbsExperienceListService {
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> bbsExperienceCode(String codeId) throws Exception;
	
	List<Map<String, Object>> selectBbsExperienceList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectbbsExperienceDetail(Map<String, Object> mapParam) throws Exception;
	
	Map<String, Object> saveBbsExperience(HttpServletRequest request, DataRequest dataRequest);
	
}

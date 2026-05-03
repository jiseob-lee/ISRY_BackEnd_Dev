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

public interface BbsVocListService {
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsVocList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsVocDetail(Map<String, Object> mapParam) throws Exception;
	
	Map<String, Object> saveBbsVocProc(HttpServletRequest request, DataRequest dataRequest);
	
//	-----------------------------------------답글
	//답글(추가(insertBbsRespodVoc), 수정(updateBbsRespodVoc), 삭제(deleteBbsRespodVoc))
	Map<String, Object> saveBbsRespodVoc(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	//답글상세
	List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) throws Exception;
	
}

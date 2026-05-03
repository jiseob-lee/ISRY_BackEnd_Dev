/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.unansntabrd.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface BbsopcListService {
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsopcList(Map<String, Object> mapParam) throws Exception;

	//답변완료
	List<Map<String, Object>> selectBbsopcListY(Map<String, Object> mapParam) throws Exception;
	
	//미답변
	List<Map<String, Object>> selectBbsopcListN(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectBbsopcDetail(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> counselorList(Map<String, Object> mapParam) throws Exception;
	
	void insertCrisis(HttpServletRequest request, DataRequest dataRequest);
	
	Map<String, Object> saveBbsopc(HttpServletRequest request, DataRequest dataRequest);
	
	Map<String, Object> saveCounselor(HttpServletRequest request, DataRequest dataRequest);
	
	List<Map<String, Object>> counselorBoardList(Map<String, Object> mapParam) throws Exception;
	

//	-----------------------------------------답글
	//답글저장
	Map<String, Object> saveRespod(HttpServletRequest request, DataRequest dataRequest);
	
	//답글상세
	List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) throws Exception;
	
	
}

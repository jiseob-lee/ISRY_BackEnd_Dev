/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.medscsnntabrd.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : BbsonmService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Song.Young.Il
 * @작성일        : 2022. 5. 13. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2022. 5. 13.
 * @수정내용      : 
 * -                
 * -                
 */
public interface BbssoldListService {
	
	List<Map<String , Object>> selectBbssoldList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectBbssoldDetail(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectBbssoldReplyList(Map<String, Object> mapParam);

	List<Map<String, Object>> insertCounselor(Map<String, Object> mapParam);
	
	Map<String, Object> saveBbssoldReply(HttpServletRequest request, DataRequest dataRequest);
	
	void deleteBbssold(HttpServletRequest request, DataRequest dataRequest);
	
	void bbssoldDtlCnt(Map<String, Object> mapParam);

	void insertCrisis(HttpServletRequest request, DataRequest dataRequest);
	
	int getTotalCount(Map<String, Object> mapParam);
	
//--------------------------------------------------------
	void insertMemo(Map<String, Object> mapParam);
	
	void saveCounselor(Map<String, Object> mapParam);
	
	void updateProbmStts(Map<String, Object> mapParam);

}

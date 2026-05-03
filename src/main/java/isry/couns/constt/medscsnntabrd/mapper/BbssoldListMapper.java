/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.medscsnntabrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : BbsonmMapper.java
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

@Mapper("bbssoldListMapper")
public interface BbssoldListMapper {
	
	List<Map<String , Object>> selectBbssoldList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectBbssoldDetail(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectBbssoldReplyList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> insertCounselor(Map<String, Object> mapParam);
	
	int selectCrisBrdCnt(Map<String, Object> mapParam);
	
	int selectCrisPrsCnt(Map<String, Object> mapParam);

	void insertReply(Map<String, String> mapIns);
	
	void updateReply(Map<String, String> mapUpd);
	
	void deleteReply(Map<String, String> mapDel);
	
	void deleteBbssold(Map<String, String> mapDel);

	void bbssoldDtlCnt(Map<String, Object> mapParam);
	
	void insertCrisis(Map<String, Object> mapParam);
	
	void insertCrisisBoard(Map<String, Object> mapParam);
	
	void insertCrisisPerson(Map<String, Object> mapParam);
	
	void updateCrisisBoard(Map<String, Object> mapParam);
	
	void updateCrisisPerson(Map<String, Object> mapParam);
	
	int getTotalCount(Map<String, Object> mapParam);
	
//	-----------------------------------------------------
	void insertMemo(Map<String, Object> mapParam);

	List<Map<String, Object>> selectMemo(Map<String, Object> mapParam);
	
	void updateMemo(Map<String, Object> mapParam);
	
	void updateProbmStts(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectCounselor(Map<String, Object> mapParam);
	
	void insertCounselor2(Map<String, Object> mapParam);
	
	void updateCounselor(Map<String, Object> mapParam);

}

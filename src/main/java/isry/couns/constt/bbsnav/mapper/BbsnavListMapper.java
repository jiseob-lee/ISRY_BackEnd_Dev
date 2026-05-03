/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbsnav.mapper;

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

@Mapper("bbsnavListMapper")
public interface BbsnavListMapper {
	
	List<Map<String , Object>> selectBbsnavList(Map<String, Object> mapParam);

	List<Map<String , Object>> nonRepSelectBbsnavList(Map<String, Object> mapParam);
	
	List<Map<String , Object>> repSelectBbsnavList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectBbsnavDetail(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectSulmun(Map<String, Object> mapParam);
	
	int selectCrisBrdCnt(Map<String, Object> mapParam);
	
	int selectCrisPrsCnt(Map<String, Object> mapParam);

	int selectBrdMaxCnt(Map<String, Object> mapParam);

	void insertBbsnav(Map<String, String> mapIns);
	
	void updateBbsnav(Map<String, String> mapUpd);
	
	void deleteBbsnav(Map<String, String> mapDel);

	void insertRespod(Map<String, String> mapIns);
	
	void updateRespod(Map<String, String> mapUpd);
	
	void deleteRespod(Map<String, String> mapDel);
	
	int getTotalCount(Map<String, Object> mapParam);

	void bbsnavDtlCnt(Map<String, Object> mapParam);
	
	void insertCrisis(Map<String, Object> mapParam);
	
	void insertCrisisBoard(Map<String, Object> mapParam);
	
	void insertCrisisPerson(Map<String, Object> mapParam);
	
	void updateCrisisPerson(Map<String, Object> mapParam);
	
	void updateCrisisBoard(Map<String, Object> mapParam);
	
	void updateCase(Map<String, Object> mapParam);
	
	void updateBbsnavRespod(Map<String, Object> mapAns);

	void RespodDtlCnt(Map<String, Object> mapParam);
	
	void updateBbssolRespod(Map<String, Object> mapAns);
	
	List<Map<String, Object>> selectCounselorList(Map<String, String> mapParam);
	
	void processCounselor(Map<String, String> mapParam);

	void updateReteDlivCmptn(Map<String, String> mapParam);
}

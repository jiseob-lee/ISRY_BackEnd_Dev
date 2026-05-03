/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.chttmng.mapper;

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

@Mapper("InqChacttListMapper")
public interface InqChacttListMapper {
	
	List<Map<String , Object>> selectInqchacttList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectInqchacttDetail(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectSulmun(Map<String, Object> mapParam);
	
	int selectCrisBrdCnt(Map<String, Object> mapParam);
	
	int selectCrisPrsCnt(Map<String, Object> mapParam);
	
	void updateChactt(Map<String, String> mapUpd);
	
	void deleteChactt(Map<String, String> mapDel);
	
	void updateChacttMemo(Map<String, Object> mapParam);
	
	void insertCrisisBoard(Map<String, Object> mapParam);
	
	void insertCrisisPerson(Map<String, Object> mapParam);
	
	void updateCrisisBoard(Map<String, Object> mapParam);
	
	void updateCrisisPerson(Map<String, Object> mapParam);
	
	void updateCase(Map<String, Object> mapParam);
	
	int getTotalCount(Map<String, Object> mapParam);
	
	void insertSpcla(Map<String, String> mapUpd);
	
}

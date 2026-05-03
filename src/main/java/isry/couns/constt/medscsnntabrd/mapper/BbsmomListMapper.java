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

@Mapper("bbsmomListMapper")
public interface BbsmomListMapper {
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	//본인상담
	List<Map<String, Object>> selectBbsmomList(Map<String, Object> mapParam) throws Exception;
	
	//답변완료
	List<Map<String, Object>> selectBbsmomListY(Map<String, Object> mapParam) throws Exception;
	
	//미답변
	List<Map<String, Object>> selectBbsmomListN(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsmomDetail(Map<String, Object> mapParam) throws Exception;
	
	void updateRdcntDtlList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> counselorList(Map<String, Object> mapParam) throws Exception;
	
//	void insertBbsmom(Map<String, String> mapIns);

	void updateBbsmom(Map<String, String> mapUpd);
	
	void deleteBbsmom(Map<String, String> mapDel);
	
//	---------------------------------------답글
	List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) throws Exception;
	
	void insertRespod(Map<String, String> mapIns);

	void updateRespod(Map<String, String> mapUpd);
	
	void deleteRespod(Map<String, String> mapDel);
	
//	--------------------------------------	
	void insertCounselor(Map<String, String> mapIns);
	
	void updateCounselor(Map<String, String> mapUpd);

	List<Map<String, Object>> counselorBoardList(Map<String, Object> mapParam) throws Exception;
	
//	--------------------------------------	
	List<Map<String, Object>> selectMemo(Map<String, Object> mapParam);
	
	void insertMemo(Map<String, Object> mapParam);
	
	void updateMemo(Map<String, Object> mapParam);
}












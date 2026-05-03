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

@Mapper("bbssimListMapper")
public interface BbssimListMapper {
	
	List<Map<String , Object>> selectBbssimList(Map<String, Object> mapParam);
	
	List<Map<String , Object>> selectBbssimList1(Map<String, Object> mapParam);
	
	List<Map<String , Object>> selectBbssimList2(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectBbssimDetail(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectBbssimDetailEum(Map<String, Object> mapParam);
	
	/**
	 * @Method명   : selectEumMailDetail
	 * @param 	   : mapParam
	 * @return	   : Map
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 1. 31. 
	 * @Method설명 : 발송할 메일 정보 조회(이음-e 마지막 댓글 + 이음-e 정보)
	 */
	Map<String, Object> selectEumMailDetail(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectBbssimReplyList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> insertCounselor(Map<String, Object> mapParam);
	
	int selectCrisBrdCnt(Map<String, Object> mapParam);
	
	int selectCrisPrsCnt(Map<String, Object> mapParam);

	int insertReply(Map<String, String> mapIns);
	
	void updateReply(Map<String, String> mapUpd);
	
	void deleteReply(Map<String, String> mapDel);
	
	void deleteBbssim(Map<String, String> mapDel);

	void bbssimDtlCnt(Map<String, Object> mapParam);
	
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
	
	public List<Map<String, Object>> selectBbscttTypeSeCd(String codeId) throws Exception;
	
	public void updateBbssim(Map<String, String> mapParam) throws Exception;
	
	public void updateEmailSndng(Map<String, String> mapParam) throws Exception;
}

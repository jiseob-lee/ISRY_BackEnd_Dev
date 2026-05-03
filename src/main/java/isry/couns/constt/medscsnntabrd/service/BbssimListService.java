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
public interface BbssimListService {
	
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
	
	Map<String, Object> saveBbssimReply(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	void deleteBbssim(HttpServletRequest request, DataRequest dataRequest);
	
	void bbssimDtlCnt(Map<String, Object> mapParam);

	void insertCrisis(HttpServletRequest request, DataRequest dataRequest);
	
	int getTotalCount(Map<String, Object> mapParam);
	
//--------------------------------------------------------
	void insertMemo(Map<String, Object> mapParam);
	
	void updateProbmStts(Map<String, Object> mapParam);
	
	void saveCounselor(Map<String, Object> mapParam);
	
	public List<Map<String, Object>> selectBbscttTypeSeCd(String codeId) throws Exception;
	
	/**
	 * @Method명   : updateBbssim
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 3. 
	 * @Method설명 : 기타 댓글상담 상세 내역 UPDATE
	 */
	public void updateBbssim(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : updateEmailSndng
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 : 기타 댓글상담 메일발송여부 or 이음-e 메일발송일자 UPDATE
	 */
	public void updateEmailSndng(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}

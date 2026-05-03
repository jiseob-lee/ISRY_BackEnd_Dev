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
public interface BbscttListService {
	
	List<Map<String , Object>> selectBbscttList(Map<String, Object> mapParam);
	
	List<Map<String , Object>> nonRepSelectBbscttList(Map<String, Object> mapParam);
	
	List<Map<String , Object>> repSelectBbscttList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectBbscttDetail(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam);

	List<Map<String, Object>> insertCounselor(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectSulmun(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectMemo(Map<String, Object> mapParam);
	
	Map<String, Object> saveBbscttList(HttpServletRequest request, DataRequest dataRequest);
	
	Map<String, Object> saveRespod(HttpServletRequest request, DataRequest dataRequest);
	
	void updateBbscttTitle(Map<String, Object> mapParam);
	
	void bbscttDtlCnt(Map<String, Object> mapParam);

	void insertCrisis(HttpServletRequest request, DataRequest dataRequest);
	
	void updateCase(Map<String, Object> mapParam);
	
	int getTotalCount(Map<String, Object> mapParam);

	void RespodDtlCnt(Map<String, Object> mapParam);
	
	void insertVoc(Map<String, Object> mapParam);
	
	void insertMemo(Map<String, Object> mapParam);
	
	void saveCounselor(Map<String, Object> mapParam);

	List<Map<String, String>> selectSrvyResultList(Map<String, String> mapParam);
	
	/**
	 * @Method명   : processSecreNtabrdDtl
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 4. 7. 
	 * @Method설명 : 비밀게시판 게시글 및 답글 Insert/Update/Delete
	 */
	public void processSecreNtabrdDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}

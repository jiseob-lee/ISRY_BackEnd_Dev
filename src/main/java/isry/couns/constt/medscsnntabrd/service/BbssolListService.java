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
public interface BbssolListService {
	
	List<Map<String , Object>> nonRepSelectBbssolList(Map<String, Object> mapParam);
	
	List<Map<String , Object>> repSelectBbssolList(Map<String, Object> mapParam);
	
	List<Map<String , Object>> selectBbssolList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectBbssolDetail(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam);

	List<Map<String, Object>> insertCounselor(Map<String, Object> mapParam);
	
//	void saveBbssolListDetail(HttpServletRequest request, DataRequest dataRequest);
	
	Map<String, Object> saveBbssolList(HttpServletRequest request, DataRequest dataRequest);
	
	Map<String, Object> saveRespod(HttpServletRequest request, DataRequest dataRequest);
	
	void bbssolDtlCnt(Map<String, Object> mapParam);

	void respodDtlCnt(Map<String, Object> mapParam);

	void insertCrisis(HttpServletRequest request, DataRequest dataRequest);
	
	int getTotalCount(Map<String, Object> mapParam);

	
//--------------------------------------------------------
	void insertMemo(Map<String, Object> mapParam);
	
	void updateSupvSlctnCaseYn(Map<String, Object> mapParam);
	
	void saveCounselor(Map<String, Object> mapParam);
	
	List<Map<String, String>> ssolContentList(DataRequest dataRequest);
	
	void ssolContentInsert(HttpServletRequest request, DataRequest dataRequest);
	
	List<Map<String , Object>> selectMemo(Map<String, Object> mapParam);

	Map<String, Object> saveBbssol(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : updateCase
	 * @param 	   : mapParam
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 4. 12. 
	 * @Method설명 : 슈퍼비전 사례 선정 처리
	 */
	public void updateCase(Map<String, Object> mapParam);
}

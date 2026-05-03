/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbsnav.service;

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
public interface BbsnavListService {
	
	List<Map<String , Object>> selectBbsnavList(Map<String, Object> mapParam);
	
	List<Map<String , Object>> nonRepSelectBbsnavList(Map<String, Object> mapParam);
	
	List<Map<String , Object>> repSelectBbsnavList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectBbsnavDetail(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam);

	List<Map<String, Object>> selectSulmun(Map<String, Object> mapParam);
	
	Map<String, Object> saveBbsnavList(HttpServletRequest request, DataRequest dataRequest);
	
	Map<String, Object> saveRespod(HttpServletRequest request, DataRequest dataRequest);
	
	void insertCrisis(HttpServletRequest request, DataRequest dataRequest);
	
	void bbsnavDtlCnt(Map<String, Object> mapParam);
	
	void updateCase(Map<String, Object> mapParam);
	
	int getTotalCount(Map<String, Object> mapParam);

	void RespodDtlCnt(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectCounselorList
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 : 선택한 일자에 대한 근무자 리스트 출력
	 */
	List<Map<String, Object>> selectCounselorList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : insertCounselor
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 : 상담원 할당 / AYA150(상담자배정) INSERT
	 */
	void insertCounselor(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : updateReteDlivCmptn
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 : 기타상담게시판 - 카카오톡 오픈채팅 : 내담자에게 답글전달 확인 여부 UPDATE
	 */
	void updateReteDlivCmptn(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}

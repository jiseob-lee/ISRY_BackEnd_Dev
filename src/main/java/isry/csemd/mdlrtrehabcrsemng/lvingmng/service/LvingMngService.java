/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mdlrtrehabcrsemng.lvingmng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : LvingMngService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Seoung.Jae
 * @작성일        : 2022. 9. 16. 
 * @수정자        : Lee.Seoung.Jae
 * @수정일        : 2022. 9. 16.
 * @수정내용      : 
 * -                
 * -                
 */
public interface LvingMngService {

	/**
	 * @param request 
	 * @Method명   : selectPic
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 27. 
	 * @Method설명 : 담당자 콤보데이터 조회
	 */
	List<Map<String, String>> selectPic(HttpServletRequest request) throws Exception;
	
	/**
	 * @Method명   : selectSrvcExcnBiz
	 * @param request
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 서비스실행사업 콤보조회
	 */
	List<Map<String, String>> selectSrvcExcnBiz(HttpServletRequest request) throws Exception;
	
	/**
	 * @Method명   : selectBizYr
	 * @param request
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 서비스사업년도 콤보조회
	 */
	List<Map<String, String>> selectBizYr(HttpServletRequest request) throws Exception;
	
	/**
	 * @Method명   : selectDormitForSearch
	 * @param request
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 10. 17. 
	 * @Method설명 : 생활동 콤보데이터 조회
	 */
	List<Map<String, String>> selectDormitForSearch(HttpServletRequest request) throws Exception;
	
	/**
	 * @Method명   : selectTakingEra
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 29. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectTakingEra() throws Exception;
	
	/**
	 * @Method명   : selectEnfsn
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 10. 5. 
	 * @Method설명 : 생활동 담당자를 지정하기 위한 종사자 콤보데이터 조회
	 */
	List<Map<String, String>> selectEnfsn(HttpServletRequest request) throws Exception;
	
	/**
	 * @Method명   : selectInst
	 * @param request
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 10. 12. 
	 * @Method설명 : 기관 목록 조회
	 */
	List<Map<String, String>> selectInst(HttpServletRequest request) throws Exception;
	
	/**
	 * @Method명   : selectWorkDiaryList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 16. 
	 * @Method설명 : 근무일지 목록조회
	 */
	void selectWorkDiaryList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectWorkDiary
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 23. 
	 * @Method설명 : 근무일지 상세 조회
	 */
	void selectWorkDiary(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : saveWorkDiary
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 23. 
	 * @Method설명 : 근무일지 수정/저장
	 */
	void saveWorkDiary(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectDayChckList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 :
	 */
	void selectDayChckList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectDayChck
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 :
	 */
	void selectDayChck(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : saveDayChck
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 24. 
	 * @Method설명 :
	 */
	void saveDayChck(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectDormitList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 26. 
	 * @Method설명 :
	 */
	void selectDormitList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectDormitNowStrdcList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 26. 
	 * @Method설명 :
	 */
	void selectDormitNowStrdcList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : saveDormit
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 26. 
	 * @Method설명 :
	 */
	void saveDormit(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectRenuNo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 9. 28. 
	 * @Method설명 :
	 */
	String selectRenuNo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectDayChckExist
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 10. 1. 
	 * @Method설명 :
	 */
	void selectDayChckExist(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectYngbgsObservRcord
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 12. 5. 
	 * @Method설명 :
	 */
	void selectYngbgsObservRcord(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : updateWorkDiaryAprv
	 * @param request
	 * @param dataRequest
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 12. 5. 
	 * @Method설명 :
	 */
	void updateWorkDiaryAprv(HttpServletRequest request, DataRequest dataRequest);

	/**
	 * @Method명   : selectDormitListForCheck
	 * @param request
	 * @param dataRequest
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 1. 27. 
	 * @Method설명 :
	 */
	void selectDormitAllList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}

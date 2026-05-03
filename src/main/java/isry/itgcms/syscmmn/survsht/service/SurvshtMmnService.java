/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.syscmmn.survsht.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

/**
 * @파일명 : SurvshtService.java
 * @프로그램 설명 : 설문지 작성을 관리하는 Service
 * @작성자 : kim.seong.gyu
 * @작성일 : 2022. 5. 04
 * @수정자 :
 * @수정일 :
 * @수정내용 : - -
 */
public interface SurvshtMmnService {

	/**
	 * @Method명 : selectSurvshtListTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문지 목록 totalCount조회
	 */
	public Integer selectSurvshtListTotalCount(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectSurvshtList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문지 목록 조회
	 */
	public List<Map<String, Object>> selectSurvshtList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : saveSurvsht
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서 수발신함 그리드 컨트롤(CUD)
	 */
	public void saveSurvsht(DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : insertSurvsht
	 * @param dmSaveMap
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문서쓰기 발송
	 */
	public void insertSurvsht(Map<String, Object> dmSaveMap) throws Exception;

	/**
	 * @Method명 : updateSurvsht
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 :
	 */
	public void updateSurvsht(Map<String, Object> dmUpdateMap) throws Exception;


	/**
	 * @Method명 : selectQesitmListTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문지 문항 목록 totalCount조회
	 */
	public Integer selectQesitmListTotalCount(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectQesitmList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 설문지 문항 목록 조회
	 */
	public List<Map<String, Object>> selectQesitmList(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectQesitm
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문항내용 조회
	 */
	public List<Map<String, Object>> selectQesitm(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : selectQesitmExmplList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : kim.seong.gyu
	 * @작성일 : 2022. 4. 28.
	 * @Method설명 : 문항보기 목록 조회
	 */
	public List<Map<String, Object>> selectQesitmExmplList(Map<String, String> map) throws Exception;

	public void saveQesitmMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> deleteQesitmMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> saveQustnbMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectQesitmQustnbMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectQuestnbRelmList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> saveQuestnbRelmList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectResultCrtrList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectResultQustnbRelm(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> saveResultCrtrList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectPreSurvshtInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectPreSurvshtList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public List<Map<String, Object>> selectSrvyTrprList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> processStatusQustnbMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Integer selectSurvshtTmptListTotalCount(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectSurvshtTmptList(Map<String, Object> map) throws Exception;

	public Map<String, Object> selectQesitmQustnbTmptMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> saveQustnbTmptMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectQuestnbRelmTmptList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> saveQuestnbRelmTmptList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectResultCrtrTmptList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> saveResultCrtrTmptList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectResultQustnbRelmTmpt(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectPreSurvshtTmptInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectPreSurvshtTmptList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectSurvshtCpTmptList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> processSurvshtCpTmpt(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> processQustnbTmptMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectMySurvshtList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> savePreSurvshtList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	//append for n survey
	public Map<String, Object> savePreSurvshtBySurvsht(HttpServletRequest request, ParameterGroup dmBase, ParameterGroup ds2) throws Exception;

	public Map<String, Object> selectSurvshtTmptRelmMarkList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectDidimGrdngRelmList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> saveDidimGrdngRelmList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * 설문지 복사 공통 넘겨주는 정보는 QUSTNB_TMPT_MNG_NO 설문지 템플릿 번호
	 * RETURN 정보는 설문지템플릿번호, 설문지 번호
	 * @Method명   : copySurvshtTmptData
	 * @param request
	 * @param dataRequest
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : TaesooSong
	 * @작성일     : 2022. 10. 14.
	 * @Method설명 :
	 */
	public Map<String, Object> processSurvshtTmptData(HttpServletRequest request, DataRequest dataRequest, Map<String, Object> map) throws Exception;

	public Map<String, Object> sendSrvyMsg(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public Map<String, Object> selectPreSurvshtResponseList(String QUSTNB_MNG_NO) throws Exception;
}

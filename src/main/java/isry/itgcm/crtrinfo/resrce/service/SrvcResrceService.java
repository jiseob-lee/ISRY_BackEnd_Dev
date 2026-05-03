/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.crtrinfo.resrce.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;


/**
* @Class Name  : SrvcResrceService.java
* @Description : 자원정보 Service Class
*
* @author  : Kwon.Min.Seo
* @since   : 2022. 06. 24.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 06. 24.  Kwon.Min.Seo    최초작성
* </pre>
*/
public interface SrvcResrceService {

	/**
	 * @Method     : selectResrceList
	 * @Method설명 : 자원 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public List<Map<String, Object>> selectResrceList(DataRequest dataRequest, HttpServletRequest request) throws Exception;

	
	public Map<String, Object> selectResrcePagingList(DataRequest dataRequest, HttpServletRequest request) throws Exception;
	/**
	 * @Method     : selectResrceDetail
	 * @Method설명 : 자원 상세조회
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public Map<String, Object> selectResrceDetail(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectResrceHistory
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 16. 
	 * @Method설명 : 자원 이력조회
	 */
	public Map<String, Object> selectResrceHistory(DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectResrceProgrmList
	 * @Method설명 : 자원 프로그램조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public List<Map<String, Object>> selectResrceProgrmList(DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectResrceProgrmSchdlList
	 * @Method설명 : 자원 프로그램상세일정
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public List<Map<String, Object>> selectResrceProgrmSchdlList(DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectResrceProgrmInstrList
	 * @Method설명 : 자원 프로그램강사
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public List<Map<String, Object>> selectResrceProgrmInstrList(DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectResrcePicList
	 * @Method설명 : 자원 담당자조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public List<Map<String, Object>> selectResrcePicList(DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectResrceChgHstrList
	 * @Method설명 : 자원 변경이력
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public List<Map<String, Object>> selectResrceChgHstrList(DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : processDscsnOutrcDetail
	 * @Method설명 : 자원 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public Map<String, Object> processResrceDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : processAprvPrcs
	 * @Method설명 : 자원 승인(반려)처리
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 24. 
 	 */	
	public void processAprvPrcs(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectBizYrCombo
	 * @Method설명 : 사업연도 콤보 데이터 조회
	 * @param      : dataRequest
	 * @return     : ListMap
	 * @exception  : Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 07. 28. 
 	 */
	public List<Map<String, Object>> selectBizYrCombo(HttpServletRequest request) throws Exception;
	
	/**
	 * @Method     : selectResrceNmCombo
	 * @Method설명 : 교육과정 콤보 데이터 조회
	 * @param      : dataRequest
	 * @return     : ListMap
	 * @exception  : Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public List<Map<String, Object>> selectResrceNmCombo(HttpServletRequest request) throws Exception;
	
	/**
	 * @Method     : selectInstNmCombo
	 * @Method설명 : 교육기관 콤보 데이터 조회
	 * @param      : dataRequest
	 * @return     : ListMap
	 * @exception  : Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public List<Map<String, Object>> selectInstNmCombo(HttpServletRequest request) throws Exception;
	
	public List<Map<String, Object>> selectInstNmCombo1(HttpServletRequest request) throws Exception;
	
	public List<Map<String, Object>> selectInstNmCombo2(HttpServletRequest request) throws Exception;
	
	public List<Map<String, Object>> selectInstNmCombo3(HttpServletRequest request) throws Exception;
	
	/**
	 * @Method     : selectEduSchdlDtlList
	 * @Method설명 : 교육시간표 상세 목록 조회
	 * @param      : dataRequest
	 * @return     : ListMap
	 * @exception  : Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public List<Map<String, Object>> selectEduSchdlDtlList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectEduHrDtList
	 * @Method설명 : 교육시간표 상세 일괄등록 조회
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : 
	 * @작성일     : 2022. 07. 26. 
 	 */	
	public List<Map<String, String>> selectEduHrDt(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : processAprvPrcs
	 * @Method설명 : 교육시간표상세 일괄등록 엑셀업로드
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public List<Map<String, String>> processEduHrDtlRegExcelUpload(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : processAllDel
	 * @Method설명 : 교육시간표상세 일괄등록 전체삭제
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public void processAllDel(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : processAplcn
	 * @Method설명 : 스케쥴 적용
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 25. 
 	 */	
	public List<Map<String, String>> processAplcn(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectInstrInfo
	 * @Method설명 : 강사조회
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Choi.Doo.Il.
	 * @작성일     : 2022. 07. 28. 
 	 */	
	public List<Map<String, String>> selectInstr(DataRequest dataRequest) throws Exception;	
	
	/**
	 * @Method     : processAplcnDel
	 * @Method설명 : 스케쥴 적용 삭제
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 07. 28. 
 	 */	
	public List<Map<String, String>> processAplcnDel(HttpServletRequest request, DataRequest dataRequest) throws Exception;
		
	public List<Map<String, Object>> selectCommonCodeUnit(String codeId, String unitCode) throws Exception;
	
	/**
	 * @Method     : selectResrceNmChk
	 * @Method설명 : 자원명 중복조회
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Choi.Doo.Il
	 * @작성일     : 2022. 11. 08. 
		 */	
	public Map<String, Object> selectResrceNmChk(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectRsfrMbyInstChk
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 28. 
	 * @Method설명 : 자원제공주체 확인
	 */
	public Map<String, Object> selectRsfrMbyInstChk(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectEduCrseChk
	 * @Method설명 : 교육과정확인 조회
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 11. 18. 
	*/		
	Map<String, Object> selectEduCrseChk(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	Map<String, Object> selectEduCrseChk1(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	Map<String, Object> selectEduCrseChk2(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectInstNmCombo4
	 * @param request
	 * @return
	 * @throws Exception 
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectInstNmCombo4(HttpServletRequest request) throws Exception;

	/**
	 * @Method     : selectResrceHistoryDetail
	 * @Method설명 : 자원 이력상세조회
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Choi.Doo.Il
	 * @작성일     : 2022. 11. 29. 
 	 */	
//	public Map<String, Object> selectResrceHistoryDetail(DataRequest dataRequest) throws Exception;
	
}

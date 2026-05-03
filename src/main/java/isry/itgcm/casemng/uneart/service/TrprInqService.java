/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.uneart.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;


/**
* @Class Name  : TrprInqService.java
* @Description : 대상자정보 Service Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 18.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 18.  Seo.Hae.Seok    최초작성
* </pre>
*/
public interface TrprInqService {

	/**
	 * @Method     : selectTrprInqList
	 * @Method설명 : 대상자 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 18. 
 	 */	
	public Map<String, Object> selectTrprInqList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectTrprInqDetail
	 * @Method설명 : 대상자 상세조회, 본인인증정보조회, 개인식별목록조회
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 18. 
 	 */	
	public Map<String, Object> selectTrprInqDetail(DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectPersonalInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 2. 1. 
	 * @Method설명 : 발굴대상자 개인정보조회
	 */
	public List<Map<String, Object>> selectPersonalInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectPersonalInfoHistory
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 3. 17. 
	 * @Method설명 : 발굴대상자 개인정보이력조회
	 */
	public List<Map<String, Object>> selectPersonalInfoHistory(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	/**
	 * @Method     : processTrprInqDetail
	 * @Method설명 : 대상자 상세저장(등록,수정)
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 18. 
 	 */	
	public Map<String, Object> processTrprInqDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : deleteTrprInqDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 16. 
	 * @Method설명 : 대상자 삭제,이력
	 */
	public Map<String, Object> deleteTrprInqDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectCaseTrprFamList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 22. 
	 * @Method설명 : 사례가족대상 목록 조회
	 */
	public List<Map<String, Object>> selectCaseTrprFamList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectAcbgSttsList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학력상태 목록 조회
	 */
	public List<Map<String, Object>> selectAcbgSttsList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectSchulwDscntcList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학업중단 목록 조회
	 */
	public List<Map<String, Object>> selectSchulwDscntcList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectEmpymnInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 취업정보 목록 조회
	 */
	public List<Map<String, Object>> selectEmpymnInfoList(DataRequest dataRequest) throws Exception;
	
	
	/**
	 * @Method명   : selectTrprQlfcInfoList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 : 대상자자격정보 목록 조회
	 */
	public List<Map<String, Object>> selectTrprQlfcInfoList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectPrvcHistoryList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 : 개인정보이력 목록 조회
	 */
	public List<Map<String, Object>> selectPrvcHistoryList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectTrprCaseDpcnInq
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 3. 22. 
	 * @Method설명 : 대상자 사례진행여부 확인( 사례관리구분코드가 미선정이나 사례대상자신청(대기상태)로 들어오면 저장전 확인)
	 */
	public Map<String, Object> selectTrprCaseDpcnInq(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectTrprRegCnt
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 16. 
	 * @Method설명 : 대대상자등록 확인(발굴상담, 아웃리치, 긴급구조, 복지부연계, 1338상담)
	 */
	public Map<String, Object> selectTrprRegCnt(DataRequest dataRequest) throws Exception;	
	
	/**
	 * @Method명   : selectAEB100List
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 16. 
	 * @Method설명 : 발굴대상자 등록(청소년자립지원관 면접심사 조회)
	 */
	public List<Map<String, Object>> selectAEB100List(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : setPersonal
	 * @param request
	 * @param infoMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 1. 
	 * @Method설명 : 개인식별
	 */
	public String setPersonal(HttpServletRequest request, Map<String, Object> infoMap) throws Exception;
	
	/**
	 * @Method명   : selectPicList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoon.Hee.Sung
	 * @작성일     : 2023. 04. 12. 
	 * @Method설명 : 총괄 담당자, 기관 담당자 조회
	 */
	public List<Map<String, Object>> selectPicList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectCaseCnt
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoon.Hee.Sung
	 * @작성일     : 2023.0 5. 17. 
	 * @Method설명 : 대상자 진행중인 사례건수 조회
	 */
	public Map<String, Object> selectCaseCnt(DataRequest dataRequest) throws Exception;
}

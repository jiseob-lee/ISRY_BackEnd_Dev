/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.uneart.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


/**
* @Class Name  : TrprInqMapper.java
* @Description : 대상자정보 Mapper Class
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
* 2022. 05. 09.  Seo.Hae.Seok    최초작성
* </pre>
*/
@Mapper("trprInqMapper")
public interface TrprInqMapper {

	/**
	 * @Method명   : selectTrprInqListCount
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 8. 
	 * @Method설명 : 대상자 건수조회
	 */
	public Integer selectTrprInqListCount(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectTrprInqList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 8. 
	 * @Method설명 : 대상자 목록조회
	 */
	public List<Map<String, Object>> selectTrprInqList(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method     : selectTrprInqDetail
	 * @Method설명 : 대상자 상세조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 18. 
 	 */	
	public Map<String, Object> selectTrprInqDetail(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectPersonalInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 2. 1. 
	 * @Method설명 : 발굴대상자 개인정보 조회
	 */
	public List<Map<String, Object>> selectPersonalInfo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectPersonalInfoHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 3. 17. 
	 * @Method설명 : 발굴대상자 개인정보 이력변경 상세조회
	 */
	public List<Map<String, Object>> selectPersonalInfoHistory(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectTrprInqDetailModChk
	 * @Method설명 : 대상자 상세 변경체크 
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public String selectTrprInqDetailModChk(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectPesnalInfolModChk
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 30. 
	 * @Method설명 : 개인정보 변경체크
	 */
	public String selectPesnalInfolModChk(Map<String, Object> paramMap) throws Exception;
	
	public int selectUneartDscsnTrpr(Map<String, String> paramMap) throws Exception;
	public int selectOutrcTrpr(Map<String, String> paramMap) throws Exception;
	public int selectEmrgIntrvnTrpr(Map<String, String> paramMap) throws Exception;
	public int selectLinkMohwSrvcRqstTrpr(Map<String, String> paramMap) throws Exception;
	public int selectLinkTrprRqst(Map<String, String> paramMap) throws Exception;
	public int select1388TlphonDscsn(Map<String, String> paramMap) throws Exception;
	public int selectCaseRegTrpr(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertTrprInqDetail
	 * @Method설명 : 대상자 상세등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 18. 
 	 */	
	public int insertTrprInqDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateTrprInqDetail
	 * @Method설명 : 대상자 상세수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 18. 
 	 */	
	public int updateTrprInqDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : deleteTrprInqDetail
	 * @Method설명 : 대상자 상세삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 18. 
 	 */	
	public int deleteTrprInqDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertTrprInqHistory
	 * @Method설명 : 대상자 이력등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 18. 
 	 */	
	public int insertTrprInqHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertTrprInqHistory2
	 * @param trpr
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 20. 
	 * @Method설명 : 대상자 이력등록 (다건)
	 */
	public int insertTrprInqHistory2(List<Map<String, String>> trpr) throws Exception;

	/**
	 * @Method     : updateTrprInqDetail
	 * @Method설명 : 대상자 개인식별번호 저장
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 18. 
 	 */	
	public int updateIndvIdntfcNo(Map<String, String> paramMap) throws Exception;

	/**
	 * 
	 * @Method명   : selectCaseFamInfoList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 22. 
	 * @Method설명 : 사례대상자가족 조회
	 */
	public List<Map<String, Object>> selectCaseTrprFamList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCaseTrprFamMngSn
	 * @param sTrprInfoNo
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 13. 
	 * @Method설명 : 사례대상자가족 관리일련번호 채번
	 */
	public String selectCaseTrprFamMngSn(String sTrprInfoNo) throws Exception;	
	
	/**
	 * @Method명   : insertCaseTrprFam
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 22. 
	 * @Method설명 : 사례대상자가족 등록
	 */
	public int insertCaseTrprFam(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertCaseFamHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 22. 
	 * @Method설명 : 사례대상자가족이력 등록
	 */
	public int insertCaseFamHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateCaseTrprFam
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 22. 
	 * @Method설명 : 사례대상자가족 수정
	 */
	public int updateCaseTrprFam(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteCaseTrprFam
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 22. 
	 * @Method설명 : 사례대상자가족 삭제
	 */
	public int deleteCaseTrprFam(Map<String, String> paramMap) throws Exception;
	
	
	/**
	 * @Method명   : selectAcbgSttsList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학력상태 목록 조회
	 */
	public List<Map<String, Object>> selectAcbgSttsList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectAcbgSttsMngSn
	 * @param String
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학력상태일련번호 채번조회
	 */
	public String selectAcbgSttsMngSn(String sTrprInfoNo) throws Exception;
	
	/**
	 * 
	 * @Method명   : insertAcbgStts
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학력상태 등록
	 */
	public int insertAcbgStts(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 
	 * @Method명   : insertAcbgSttsHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학력상태 등록
	 */
	public int insertAcbgSttsHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateAcbgStts
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학력상태이력 등록
	 */
	public int updateAcbgStts(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteAcbgStts
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학력상태 삭제
	 */
	public int deleteAcbgStts(Map<String, String> paramMap) throws Exception;
	
	
	/**
	 * @Method명   : selectSchulwDscntcList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학업중단 목록 조회
	 */
	public List<Map<String, Object>> selectSchulwDscntcList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectSchulwDscntcMngSn
	 * @param String
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학력중단일련번호 채번조회
	 */			  
	public String selectSchulwDscntcMngSn(String sTrprInfoNo) throws Exception;	
	
	
	/**
	 * @Method명   : insertSchulwDscnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학업중단 등록
	 */
	public int insertSchulwDscnt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertSchulwDscntHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학업중단이력 등록
	 */
	public int insertSchulwDscntHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateSchulwDscnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학업중단 수정
	 */
	public int updateSchulwDscnt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteSchulwDscnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 학업중단 삭제
	 */
	public int deleteSchulwDscnt(Map<String, String> paramMap) throws Exception;

	
	/**
	 * @Method명   : selectEmpymnInfoList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 : 취업정보 목록 조회
	 */
	public List<Map<String, Object>> selectEmpymnInfoList(Map<String, String> paramMap) throws Exception;
	
	
	/**
	 * @Method명   : selectEmpymnInfoMngSn
	 * @param sTrprInfoNo
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 : 취업정보 일련번호 채번
	 */
	public String selectEmpymnInfoMngSn(String sTrprInfoNo) throws Exception;
	
	/**
	 * @Method명   : insertEmpymnInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 : 취업정보 등록
	 */
	public int insertEmpymnInfo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertEmpymnInfoHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 : 취업정보이력 등록
	 */
	public int insertEmpymnInfoHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateEmpymnInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 : 취업정보 수정
	 */
	public int updateEmpymnInfo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteEmpymnInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 : 취업정보 삭제
	 */
	public int deleteEmpymnInfo(Map<String, String> paramMap) throws Exception;
	
	
	/**
	 * @Method명   : selectTrprQlfcInfoList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 : 대상자자격정보 목록 조회
	 */
	public List<Map<String, Object>> selectTrprQlfcInfoList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectTrprQlfcInfoMngSn
	 * @param sTrprInfoNo
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 : 대상자자격정보 관리일련번호 조회
	 */
	public String selectTrprQlfcInfoMngSn(String sTrprInfoNo) throws Exception;
	
	/**
	 * @Method명   : insertTrprQlfcInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 : 대상자자격정보 등록
	 */
	public int insertTrprQlfcInfo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertTrprQlfcInfoHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 : 대상자자격정보이력 등록
	 */
	public int insertTrprQlfcInfoHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateTrprQlfcInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 : 대상자자격정보 수정
	 */
	public int updateTrprQlfcInfo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteTrprQlfcInfo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 : 대상자자격정보 삭제
	 */
	public int deleteTrprQlfcInfo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectPrvcHistoryList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 : 개인정보이력 목록 조회
	 */
	public List<Map<String, Object>> selectPrvcHistoryList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateCaseTrprFamIndvIdntfcNo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 : 사례가족대상자 개인식별번호 수정
	 */
	public int updateCaseTrprFamIndvIdntfcNo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateAcbgSttsIndvIdntfcNo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 : 학력상태 개인식별번호 수정
	 */
	public int updateAcbgSttsIndvIdntfcNo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateSchulwDscntcIndvIdntfcNo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 : 학업중단 개인식별번호 수정
 	 */
	public int updateSchulwDscntcIndvIdntfcNo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateEmpymnInfoIndvIdntfcNo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 : 취업정보 개인식별번호 수정
	 */
	public int updateEmpymnInfoIndvIdntfcNo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateQlfcInfoDtlIndvIdntfcNo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 : 자격정보 개인식별번호 수정
	 */
	public int updateQlfcInfoDtlIndvIdntfcNo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectUnitChkCnt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 : 사용자단위업구분 개인정보 건수조회
	 */
	public Integer selectUnitChkCnt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectUnitChkList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 : 사용자단위업구분외 대상자 정보조회
	 */
	public List<Map<String, Object>> selectUnitChkList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectTrprCaseBgngYmd
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 5. 
	 * @Method설명 : 대상자 사례시작일자 조회 (사례대상자일경우만)
	 */
	public Map<String, String> selectTrprCaseBgngYmd(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectTrprCaseDpcnInq
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 3. 22. 
	 * @Method설명 : 대상자 사례진행여부 확인( 사례관리구분코드가 미선정이나 사례대상자신청(대기상태)로 들어오면 저장전 확인)
	 */
	public Map<String, Object> selectTrprCaseDpcnInq(Map<String, String> paramMap) throws Exception;
	/**
	 * @Method명   : selectTrprNmBrdtDpcnInq
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 2. 
	 * @Method설명 : 대상자명, 생년월일 중복 조회
	 */
	public Integer selectTrprNmBrdtDpcnInq(Map<String, String> paramMap) throws Exception;
	/**
	 * @Method명   : selectRRnoDpcnInq
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 : 주민번호 중복 조회
	 */
	public Integer selectRRnoDpcnInq(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : getSCA300IndvIdntfcNo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 12. 9. 
	 * @Method설명 : 주민등록번호로 개인식별번호 return
	 */
	public Map<String, String> getSCA300IndvIdntfcNo(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateSCA300
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 30. 
	 * @Method설명 : 대상자정보 수정시 개인정보 수정
	 */
	public Integer updateSCA300(Map<String, Object> paramMap) throws Exception;
	/**
	 * @Method명   : insertSCA301
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 30. 
	 * @Method설명 : 대상자정보 수정시 개인정보 이력등록
	 */
	public Integer insertSCA301(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectAEB100List
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 16. 
	 * @Method설명 : 발굴대상자 등록(청소년자립지원관 면접심사 조회)
	 */
	public List<Map<String, Object>> selectAEB100List(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : insertAEB100
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 16. 
	 * @Method설명 : 발굴대상자 등록(청소년자립지원관 면접심사 등록)
	 */
	public Integer insertAEB100(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateAEB100
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 16. 
	 * @Method설명 : 발굴대상자 등록(청소년자립지원관 면접심사 수정)
	 */
	public Integer updateAEB100(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteAEB100
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 16. 
	 * @Method설명 : 발굴대상자 등록(청소년자립지원관 면접심사 삭제)
	 */
	public Integer deleteAEB100(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : updateTrprIndvIdntfcNo
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 : 주민번호입력 후 대상자 등록시 개인식별번호 발급된 개인식별번호로 대상자 테이블 수정
	 */
	public int updateTrprIndvIdntfcNo(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectInstNoNow
	 * @Method설명 : 화면에서 받아온 종사자번호로 현재 기관번호 구하기
	 * @param      : 
	 * @return     : String 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 01. 
 	 */	
	public String selectInstNoNow(String sEnfsnNo) throws Exception;
	
	/**
	 * @Method     : selectEnfsnRoleSeCd
	 * @Method설명 : 화면에서 받아온 종사자번호로 종사자역활구분코드 구하기
	 * @param      : 
	 * @return     : String 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 01. 
 	 */	
	public String selectEnfsnRoleSeCd(String sEnfsnNo) throws Exception;
	
	/**
	 * @Method     : selectInstTypeSeCd
	 * @Method설명 : 현재 기관번호로 기관유형구분코드 구하기
	 * @param      : 
	 * @return     : String 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 01. 
 	 */	
	public String selectInstTypeSeCd(String sEnfsnNo) throws Exception;
	
	/**
	 * @Method     : selectOgdpInstNo
	 * @Method설명 : 화면에서 받아온 종사자번호로 소속기관번호 구하기
	 * @param      : 
	 * @return     : String 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 01. 
 	 */	
	public String selectOgdpInstNo(String sEnfsnNo) throws Exception;
	
	/**
	 * @Method     : selectRgnCd
	 * @Method설명 : 화면에서 받아온 기관번호로 지역코드 구하기
	 * @param      : 
	 * @return     : String 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 01. 
 	 */	
	public String selectRgnCd(String sEnfsnNo) throws Exception;

	/**
	 * @Method명   : selectSggCd
	 * @param sRgnCd
	 * @return
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 1. 
	 * @Method설명 : 지역코드로 시군구코드 구하기
	 */
	public List<Map<String, Object>> selectSggCd(String sRgnCd) throws Exception;
	
	/**
	 * @Method     : selectRgnCdDs
	 * @Method설명 : 전화면에서 받아온 기관번호로 지역코드 구하기
	 * @param      : 
	 * @return     : String 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 02. 
 	 */	
	public String selectRgnCdDs(String sEnfsnNo) throws Exception;
	
	/**
	 * @Method     : selectSggCdDs
	 * @Method설명 : 전화면에서 받아온 지역코드로 시군구 코드 구하기
	 * @param      : 
	 * @return     : String 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 02. 
 	 */	
	public String selectSggCdDs(String sEnfsnNo) throws Exception;
	
	/**
	 * @Method     : selectCasePinNo
	 * @Method설명 : 화면에서 받아온 종사자번호로 사례담당자번호 구하기
	 * @param      : 
	 * @return     : String 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 04. 
 	 */	
	public String selectCasePinNo(String sEnfsnNo) throws Exception;
	
	/**
	 * @Method명   : selectPicList
	 * @param sRgnCd
	 * @return
	 * @작성자     : Yoon.Hee.Sung
	 * @작성일     : 2023. 04. 12. 
	 * @Method설명 : 총괄 담당자, 기관 담당자 조회
	 */
	public List<Map<String, Object>> selectPicList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCaseCnt
	 * @param sRgnCd
	 * @return
	 * @작성자     : Yoon.Hee.Sung
	 * @작성일     : 2023. 05. 17. 
	 * @Method설명 : 대상자 진행중인 사례건수 조회
	 */
	public Map<String, Object> selectCaseCnt(Map<String, String> paramMap) throws Exception;
	
}

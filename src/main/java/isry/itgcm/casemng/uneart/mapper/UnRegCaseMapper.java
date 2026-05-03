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
* @Class Name  : UnRegCaseMapper.java
* @Description : 미등록사례지원 mapper Class
*
* @author  : Hee Sung Yoon
* @since   : 2023. 01. 10.
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2023. 01. 10.  Hee Sung Yoon   최초작성
*/

@Mapper("unRegCaseMapper")
public interface UnRegCaseMapper {

	/**
	 * @Method     : selectUnRegCaseList
	 * @Method설명 : 미등록사례지원 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public List<Map<String, Object>> selectUnRegCaseList(Map<String, Object> paramMap) throws Exception;
	public Integer unRegCaseListCount(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method     : newUnregiCaseNo
	 * @Method설명 : 신규 미등록사례지원 번호 조회
	 * @param      : dataRequest
	 * @return     : String 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public String newUnregiCaseNo() throws Exception;
	
	/**
	 * @Method     : updategeUnRegCase
	 * @Method설명 : 미등록사례지원 저장
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public void updategeUnRegCase(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertSrvcExcnBiz
	 * @Method설명 : 미등록사례별 서비스사업 저장
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public void insertSrvcExcnBiz(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : updateSrvcExcnBiz
	 * @Method설명 : 미등록사례별 서비스사업 수정
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public void updateSrvcExcnBiz(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : deleteSrvcExcnBiz
	 * @Method설명 : 미등록사례별 서비스사업 삭제
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public void deleteSrvcExcnBiz(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectBizList
	 * @Method설명 : 사업등록 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public List<Map<String, Object>> selectBizList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectCnterSprtListCount
	 * @Method설명 : 시군구센터지원 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public Integer selectCnterSprtListCount(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method     : selectCnterSprtList
	 * @Method설명 : 시군구센터지원 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public List<Map<String, Object>> selectCnterSprtList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method     : selectInstList
	 * @Method설명 : 시군구 기관 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public List<Map<String, Object>> selectInstList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : newSsgCnterSprtNo
	 * @Method설명 : 신규 시군구센터지원 번호 조회
	 * @param      : dataRequest
	 * @return     : String 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public String newSsgCnterSprtNo() throws Exception;
	
	/**
	 * @Method     : updategeCnterSprt
	 * @Method설명 : 시군구센터지원 저장
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public void updategeCnterSprt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertCnterSrvcExcnBiz
	 * @Method설명 : 시군구센터지원 서비스사업 저장
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public void insertCnterSrvcExcnBiz(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : updateCnterSrvcExcnBiz
	 * @Method설명 : 시군구센터지원 서비스사업 수정
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public void updateCnterSrvcExcnBiz(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : deleteCnterSrvcExcnBiz
	 * @Method설명 : 시군구센터지원 서비스사업 삭제
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public void deleteCnterSrvcExcnBiz(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertCnterSprt
	 * @Method설명 : 시군구센터지원 실적컨설팅센터 저장
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public void insertCnterSprt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : updateCnterSprt
	 * @Method설명 : 시군구센터지원 실적컨설팅센터 수정
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public void updateCnterSprt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : deleteCnterSprt
	 * @Method설명 : 시군구센터지원 실적컨설팅센터 삭제
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public void deleteCnterSprt(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectInstList
	 * @Method설명 : 실적컨설팅센터 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public List<Map<String, Object>> selectCnstnCnterInst(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectCnterBizList
	 * @Method설명 : 시군구센터지원 사업등록 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */
	public List<Map<String, Object>> selectCnterBizList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectExcnDetaiaBizList
	 * @Method설명 : 미등록사례지원 실행서비스 사업분류 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */	
	public List<Map<String, Object>> selectExcnDetaiaBizList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertExcnDetaiaBiz
	 * @Method설명 : 미등록사례지원 실행서비스 사업분류 목록 저장
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */
	public void insertUneartExcnDetaiaBiz(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : deleteUneartExcnDetaiaBiz
	 * @Method설명 : 미등록사례지원 실행서비스 사업분류 목록 삭제
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */
	public void deleteUneartExcnDetaiaBiz(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectCnterExcnDetaiaBizList
	 * @Method설명 : 미등록사례지원 실행서비스 사업분류 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */	
	public List<Map<String, Object>> selectCnterExcnDetaiaBizList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertCnterExcnDetaiaBiz
	 * @Method설명 : 시군구센터지원 실행서비스 사업분류 목록 저장
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */
	public void insertCnterExcnDetaiaBiz(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : deleteCnterExcnDetaiaBiz
	 * @Method설명 : 시군구센터지원 실행서비스 사업분류 목록 삭제
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */
	public void deleteCnterExcnDetaiaBiz(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertUnRegCasePic
	 * @Method설명 : 미등록사례지원 담당자 목록 등록
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */
	public void insertUnRegCasePic(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : updateUnRegCasePic
	 * @Method설명 : 미등록사례지원 담당자 목록 수정
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */
	public void updateUnRegCasePic(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : deleteUnRegCasePic
	 * @Method설명 : 미등록사례지원 담당자 목록 삭제
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */
	public void deleteUnRegCasePic(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectUnRegCasePic
	 * @Method설명 : 미등록사례지원 담당자 목록 조회
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */
	public List<Map<String, Object>> selectUnRegCasePic(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertUnRegCaseHis
	 * @Method설명 : 미등록사례지원 이력 저장
	 * @param      : Map
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 16. 
 	 */
	public void insertUnRegCaseHis(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertCnterSprtHis
	 * @Method설명 : 시군구센터지원 이력 저장
	 * @param      : Map
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 16. 
 	 */
	public void insertCnterSprtHis(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectUnRegCaseTrpr
	 * @Method설명 : 미등록사례지원 사례대상자 조회
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 18. 
 	 */
	public List<Map<String, Object>> selectUnRegCaseTrpr(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertUnRegCaseTrpr
	 * @Method설명 : 미등록사례지원 사례대상자 등록
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 18. 
 	 */
	public void insertUnRegCaseTrpr(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : updateUnRegCaseTrpr
	 * @Method설명 : 미등록사례지원 사례대상자 수정
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 18. 
 	 */
	public void updateUnRegCaseTrpr(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : deleteUnRegCaseTrpr
	 * @Method설명 : 미등록사례지원 사례대상자 삭제
	 * @param      : dataRequest
	 * @return     :  
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 18. 
 	 */
	public void deleteUnRegCaseTrpr(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 미등록사례지원 SRVC_PVSN_NO update
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/
	public void updateSED100SrvcPvsnNo(Map<String, Object> paramMap) throws Exception;
}

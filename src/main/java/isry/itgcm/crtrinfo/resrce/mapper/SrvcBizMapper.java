/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.crtrinfo.resrce.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
* @Class Name  : TrprIdntfcMapper.java
* @Description : 서비스사업식별조회(팝업) Mapper Class
*
* @author  : Kwon.Min.Seo
* @since   : 2022. 05. 18.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 07. 18. Kwon.Min.Seo    최초작성
* </pre>
*/
@Mapper("srvcBizMapper")
public interface SrvcBizMapper {
	
	/**
	 * @Method     : selectSrvcBizList
	 * @Method설명 : 서비스사업 목록조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public List<Map<String, Object>> selectSrvcBizList(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method     : selectSrvcBizDetail
	 * @Method설명 : 서비스사업 상세조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public List<Map<String, Object>> selectSrvcBizDetail(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectSrvcBizDetailModChk
	 * @Method설명 : 서비스사업 상세 변경체크 
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public String selectSrvcBizDetailModChk(Map<String, String> paramMap) throws Exception;

	
	/**
	 * @Method     : insertSrvcBizDetail
	 * @Method설명 : 서비스사업 상세등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public int insertSrvcBizDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateSrvcBizDetail
	 * @Method설명 : 서비스사업 상세수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public int updateSrvcBizDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : deleteSrvcBizDetail
	 * @Method설명 : 서비스사업 상세삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public int deleteSrvcBizDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertSrvcBizHistory
	 * @Method설명 : 서비스사업 이력등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public int insertSrvcBizHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectSrvcExcnBizDetailModChk
	 * @Method설명 : 서비스사업 상세 변경체크 
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public String selectSrvcExcnBizDetailModChk(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectSrvcExcnBizList
	 * @Method설명 : 서비스실행사업 목록조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public List<Map<String, Object>> selectSrvcExcnBizList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertSrvcBizDetail
	 * @Method설명 : 서비스실행사업 상세등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public int insertSrvcExcnBizDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateSrvcBizDetail
	 * @Method설명 : 서비스실행사업 상세수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public int updateSrvcExcnBizDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : deleteSrvcBizDetail
	 * @Method설명 : 서비스실행사업 상세삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public int deleteSrvcExcnBizDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertSrvcExcnBizHistory
	 * @Method설명 : 서비스실행사업 이력등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public int insertSrvcExcnBizHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectSrvcExcnBizNoCnt
	 * @Method설명 : 서비스실행사업 사용여부체크
	 * @param      : sSrvcExcnBizNo
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Choi.Doo.Il
	 * @작성일     : 2022. 10. 13.
 	 */	
	public int selectSrvcExcnBizNoCnt(String sSrvcExcnBizNo) throws Exception;
	
	/**
	 * @Method     : selectExcnSrvcBizList
	 * @Method설명 : 실행서비스사업 목록조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03.
 	 */	
	public List<Map<String, Object>> selectExcnSrvcBizList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectExcnSrvcDetList
	 * @Method설명 : 실행서비스세부사업 목록조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03.
 	 */	
	public List<Map<String, Object>> selectExcnSrvcDetList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : saveExcnSrvcBiz
	 * @Method설명 : 실행서비스사업 저장
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03.
 	 */
	public void saveExcnSrvcBiz(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : saveExcnSrvcBizHis
	 * @Method설명 : 실행서비스사업이력 저장
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03.
 	 */
	public void saveExcnSrvcBizHis(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertExecSrvcDetaiaBiz
	 * @Method설명 : 실행서비스세부사업 저장
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03.
 	 */
	public void insertExecSrvcDetaiaBiz(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : updateExecSrvcDetaiaBiz
	 * @Method설명 : 실행서비스세부사업 수정
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03.
 	 */
	public void updateExecSrvcDetaiaBiz(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : deleteExecSrvcDetaiaBiz
	 * @Method설명 : 실행서비스세부사업 삭제
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03.
 	 */
	public void deleteExecSrvcDetaiaBiz(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : insertExecSrvcDetaiaBizHis
	 * @Method설명 : 실행서비스세부사업이력 저장
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03.
 	 */
	public void insertExecSrvcDetaiaBizHis(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method     : selectExcnDetaiaList
	 * @Method설명 : 실행서비스 세부사업 목록 팝업 조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03.
 	 */
	public List<Map<String, Object>> selectExcnDetaiaList(Map<String, String> paramMap) throws Exception;
	
	public String selectSrvcBizCnt(Map<String, Object> map) throws Exception;
	
	public void deleteExcnSrvcBiz(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : selectExcnSrvcDetBizNoCnt
	 * @Method설명 : 실행서비스 세부사업 사용여부조회
	 * @param      : selectExcnSrvcDetBizNoCnt
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 08. 08.
 	 */	
	public int selectExcnSrvcDetBizNoCnt(String sExcnSrvcDetBizNo) throws Exception;
}

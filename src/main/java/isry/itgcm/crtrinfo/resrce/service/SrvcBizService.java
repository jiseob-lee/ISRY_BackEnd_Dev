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
* @Class Name  : SrvcBizService.java
* @Description : 서비스사업 Service Class
*
* @author  : Kwon.Min.Seo
* @since   : 2022. 07. 18.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
*
* </pre>
*/
public interface SrvcBizService {
	/**
	 * @Method     : selectSrvcBizList
	 * @Method설명 : 서비스사업 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public Map<String, Object> selectSrvcBizList(HttpServletRequest request ,DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectSrvcBizDetail
	 * @Method설명 : 서비스사업 상세조회
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public List<Map<String, Object>> selectSrvcBizDetail(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectSrvcExcnBizList
	 * @Method설명 : 서비스실행사업 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public List<Map<String, Object>> selectSrvcExcnBizList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : processSrvcBizDetail
	 * @Method설명 : 서비스사업 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 07. 18.
 	 */	
	public Map<String, Object> processSrvcBizDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectExcnSrvcBizList
	 * @Method설명 : 실행서비스사업 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03. 
 	 */	
	public List<Map<String, Object>> selectExcnSrvcBizList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectExcnSrvcDetList
	 * @Method설명 : 실행서비스세부사업 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03. 
 	 */	
	public List<Map<String, Object>> selectExcnSrvcDetList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : saveExcnSrvcBiz
	 * @Method설명 : 실행서비스세부사업 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03. 
 	 */		
	public Map<String, Object> saveExcnSrvcBiz(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectExcnDetaiaList
	 * @Method설명 : 실행서비스 세부사업 목록 팝업 조회
	 * @param      : request
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 03. 
 	 */	
	public List<Map<String, Object>> selectExcnDetaiaList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : deleteExcnSrvcBiz
	 * @Method설명 : 실행서비스세부사업 삭제(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : void 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 08. 08. 
 	 */		
	public void deleteExcnSrvcBiz(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}

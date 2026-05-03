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
* @Class Name  : UnRegCaseService.java
* @Description : 미등록사례지원 service Class
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
public interface UnRegCaseService {
	
	/**
	 * @Method     : selectUnRegCaseList
	 * @Method설명 : 미등록사례지원 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public Map<String, Object> selectUnRegCaseList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : insertUnRegCase
	 * @Method설명 : 미등록사례지원 등록
	 * @param      : request
	 * @param      : response
	 * @return     : void 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public Map<String, Object> insertUnRegCase(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectBizList
	 * @Method설명 : 사업목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public List<Map<String, Object>> selectBizList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectCnterSprtList
	 * @Method설명 : 사업목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public Map<String, Object> selectCnterSprtList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectInstList
	 * @Method설명 : 시군구 기관목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public List<Map<String, Object>> selectInstList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : insertCnterSprt
	 * @Method설명 : 미등록사례지원 등록
	 * @param      : request
	 * @param      : response
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public Map<String, Object> insertCnterSprt(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectCnstnCnterInst
	 * @Method설명 : 실적컨설팅센터 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public List<Map<String, Object>> selectCnstnCnterInst(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectCnterBizList
	 * @Method설명 : 시군구센터지원 사업목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 01. 10. 
 	 */	
	public List<Map<String, Object>> selectCnterBizList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectExcnDetaiaBizList
	 * @Method설명 : 미등록사례지원 실행서비스 사업분류 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */	
	public List<Map<String, Object>> selectExcnDetaiaBizList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectCnterExcnDetaiaBizList
	 * @Method설명 : 시군구센터지원 실행서비스 사업분류 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 11. 
 	 */	
	public List<Map<String, Object>> selectCnterExcnDetaiaBizList(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectunRegCasePicList
	 * @Method설명 : 미등록사례지원 담당자 목록 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 12. 
 	 */	
	public List<Map<String, Object>> selectUnRegCasePic(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method     : selectUnRegCaseTrpr
	 * @Method설명 : 미등록사례지원 사례대상자 조회
	 * @param      : dataRequest
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 05. 18. 
 	 */	
	public List<Map<String, Object>> selectUnRegCaseTrpr(DataRequest dataRequest) throws Exception;
}

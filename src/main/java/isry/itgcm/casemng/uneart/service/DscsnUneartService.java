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
* @Class Name  : DscsnUneartService.java
* @Description : 발굴정보 Service Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 23.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 23.  Seo.Hae.Seok    최초작성
* </pre>
*/
public interface DscsnUneartService {

	/**
	 * @Method     : selectDscsnUneartList
	 * @Method설명 : 발굴 목록조회(01:초기상담,02:아웃리치,03.긴급개입)
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public List<Map<String, Object>> selectUneartDscsnList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectDscsnUneartDetail
	 * @Method설명 : 발굴(초기상담) 상세조회, 조치내역(대상자)조회
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public Map<String, Object> selectDscsnUneartDetail(DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectDscsnUneartList
	 * @Method설명 : 발굴(초기상담) 이력조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public List<Map<String, Object>> selectDscsnUneartHstrList(DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : processDscsnUneartDetail
	 * @Method설명 : 발굴(초기상담) 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public Map<String, Object> processDscsnUneartDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}

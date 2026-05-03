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
* @Class Name  : DscsnOutrcService.java
* @Description : 아웃리치정보 Service Class
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
public interface DscsnOutrcService {

	/**
	 * @Method     : selectDscsnOutrcDetail
	 * @Method설명 : 발굴(아웃리치) 상세조회, 연합거리상담조회, 쉼터자체활동조회, 지원서비스실적, 조치현황
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public Map<String, Object> selectDscsnOutrcDetail(DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectDscsnOutrcUniteList
	 * @Method설명 : 발굴(아웃리치) 연합거리상담조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public List<Map<String, Object>> selectDscsnOutrcUniteList(DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectDscsnOutrcSheltrList
	 * @Method설명 : 발굴(아웃리치) 쉼터자체활동조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public List<Map<String, Object>> selectDscsnOutrcSheltrList(DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectDscsnOutrcPrfmncDetail
	 * @Method설명 : 발굴(아웃리치) 지원서비스실적
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public Map<String, Object> selectDscsnOutrcPrfmncDetail(DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : selectDscsnOutrcActnList
	 * @Method설명 : 발굴(아웃리치) 조치현황
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public List<Map<String, Object>> selectDscsnOutrcActnList(DataRequest dataRequest) throws Exception;

	/**
	 * @Method     : processDscsnOutrcDetail
	 * @Method설명 : 발굴(아웃리치) 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public Map<String, Object> processDscsnOutrcDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}

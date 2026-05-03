/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;


/**
* @Class Name  : RqstCoursService.java
* @Description : 의뢰경로조회(팝업) Service Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 17.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 17.  Seo.Hae.Seok    최초작성
* </pre>
*/
public interface RqstCoursService {

	/**
	 * @Method     : selectRqstCoursList
	 * @Method설명 : 의뢰경로 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 17. 
 	 */	
	public List<Map<String, Object>> selectRqstCoursList(DataRequest dataRequest) throws Exception;
	
}

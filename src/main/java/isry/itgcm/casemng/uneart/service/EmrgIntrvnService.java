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
* @Class Name  : EmrgIntrvnService.java
* @Description : 긴급개입 Service Class
*
* @author  : Kwon.Min.Seo
* @since   : 2022. 06. 14.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 06. 14.  Kwon.Min.Seo    최초작성
* </pre>
*/
public interface EmrgIntrvnService {
	
	/**
	 * @Method     : selectEmrgIntrvnDetail
	 * @Method설명 : 긴급개입 상세조회
	 * @param      : dataRequest
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 14. 
 	 */	
	public List<Map<String, Object>> selectEmrgIntrvnDetail(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : processEmrgIntrvn
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 8. 
	 * @Method설명 : 긴급개입 CRUD
	 */
	public Map<String, Object> processEmrgIntrvn(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectEmrgIntrvnActnMatter
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 4. 
	 * @Method설명 : 긴급개입조치사항 조회
	 */
	public List<Map<String, Object>> selectEmrgIntrvnActnMatter(DataRequest dataRequest) throws Exception;
	
}

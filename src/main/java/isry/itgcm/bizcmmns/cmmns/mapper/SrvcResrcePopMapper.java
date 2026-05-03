/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


/**
* @Class Name  : SrvcResrcePopMapper.java
* @Description : 자원제공서비스목록(팝업) Mapper Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 11.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 11.  Seo.Hae.Seok    최초작성
* </pre>
*/
@Mapper("srvcResrcePopMapper")
public interface SrvcResrcePopMapper {

	/**
	 * @Method     : selectSrvcResrceList
	 * @Method설명 : 자원제공서비스 목록조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 11. 
 	 */	
	public List<Map<String, Object>> selectSrvcResrceList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectSrvcResrcePagingList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Song.Tae.Soo
	 * @작성일     : 2023. 6. 9. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectSrvcResrcePagingList(Map<String, Object> paramMap) throws Exception;
	
	public String selectSrvcResrceCount(Map<String, Object> paramMap) throws Exception;
}

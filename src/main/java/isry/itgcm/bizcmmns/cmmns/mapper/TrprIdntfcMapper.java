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
* @Class Name  : TrprIdntfcMapper.java
* @Description : 대상자식별조회(팝업) Mapper Class
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
* 2022. 05. 18.  Seo.Hae.Seok    최초작성
* </pre>
*/
@Mapper("trprIdntfcMapper")
public interface TrprIdntfcMapper {

	/**
	 * @Method     : selectTrprInqList
	 * @Method설명 : 대상자식별 목록조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 18. 
 	 */	
	public List<Map<String, Object>> selectTrprIdntfcList(Map<String, String> paramMap) throws Exception;

}

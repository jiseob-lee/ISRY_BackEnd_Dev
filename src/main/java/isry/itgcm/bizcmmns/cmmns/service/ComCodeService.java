/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;


/**
* @Class Name  : ComCodeService.java
* @Description : 공통코드조회 Service Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 12.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 12.  Seo.Hae.Seok    최초작성
* </pre>
*/
public interface ComCodeService {

	/**
	 * @Method     : selectComCodeList
	 * @Method설명 : 공통코드조회 목록조회
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 12. 
	 * ******************************
	 * 공통코드 조회 조건 (dsCodeParam)
	 * 1.CMMNS_CD_ID       : 공통코드아이디 (필수)  - ex) SRVC_RESRCE_LCLAS_SE_CD
	 * 2.DS_SET_NM         : RETURN 데이터셋 (필수) - ex) dsSrvcResrceLclasSeCd
	 * 3.CMMNS_CD_VALUE    : 공통코드값
	 * 4.CMMNS_CD_VALUE_NM : 공통코드값명
	 * 5.ADDTNG_MNG_VALUE1 : 추가관리값1
	 * 6.ADDTNG_MNG_VALUE2 : 추가관리값2
	 * 7.ADDTNG_MNG_VALUE3 : 추가관리값3
	 * 8.ADDTNG_MNG_VALUE4 : 추가관리값4
	 * 9.ADDTNG_MNG_VALUE5 : 추가관리값5
	 *10.USE_YN            : 사용여부
	 */	
	public List<Map<String, Object>> selectComCodeList(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectComCodeList
	 * @Method설명 : 공통코드조회 목록조회
	 * @param      : paramCode
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 12. 
	 */	
	public List<Map<String, Object>> selectComCodeList(String paramCode) throws Exception;
	
	public List<Map<String, Object>> selectCommonCodeUnit(Map<String, String> paramMap) throws Exception;
	
}

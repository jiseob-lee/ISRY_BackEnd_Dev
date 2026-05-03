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
* @Class Name  : TrprInfoMapper.java
* @Description : 대상자정보조회 팝업 Mapper Class
*
* @author  : Yoo.Chi.Hoon
* @since   : 2022. 05. 11.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 11.  Yoo.Chi.Hoon    최초작성
* </pre>
*/

@Mapper("trprInfoMapper")
public interface TrprInfoMapper {
	
	/**
	* 대상자정보 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectTrprInfoList(Map<String, Object> mapParam) throws Exception;
	public Integer getTrprInfoList(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectTrprDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 8. 26. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectTrprDetail(Map<String, String> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectTrprInfoInqPagingList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 7. 13. 
	 * @Method설명 : 발굴대상자 - 대상자(선정)대상자 조회(사례) 
	 */
	public List<Map<String, Object>> selectTrprInfoInqPagingList(Map<String, Object> mapParam) throws Exception;	
	public String selectTrprInfoInqCount(Map<String, Object> map) throws Exception;
	
	
}

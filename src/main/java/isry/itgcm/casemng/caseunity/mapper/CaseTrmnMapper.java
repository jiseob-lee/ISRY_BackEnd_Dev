/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


/**
* @Class Name  : CaseTrmnMapper.java
* @Description : 사례종결 Mapper Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 09.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 09.  Seo.Hae.Seok    최초작성
* </pre>
*/
@Mapper("caseTrmnMapper")
public interface CaseTrmnMapper {

	/**
	* @Method    : 사례종결 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseTrmnList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCaseTrmnAprvList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 22. 
	 * @Method설명 : 종결승인 목록
	 */
	public List<Map<String, Object>> selectCaseTrmnAprvList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : getCaseTrmnAprvList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 23. 
	 * @Method설명 : 종결승인 목록 건수
	 */
	public Integer getCaseTrmnAprvList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCaseTrmnAply
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 23. 
	 * @Method설명 : 종결신청 정보 조회
	 */
	public List<Map<String, Object>> selectCaseTrmnAply(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectUpperInst
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 7. 25. 
	 * @Method설명 : 종결신청 정보
	 */
	public List<Map<String, Object>> selectUpperInst(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사례종결 상세등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int insertCaseTrmnDetail(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사례종결 상세수정
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int updateCaseTrmnDetail(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사례기본 사후관리여부 수정
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int updateAftfctMngYn(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 사례기본 사례진행상태구분 수정
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int updateCasePrgrsSttsSeCd(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사례종결 상세삭제
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int deleteCaseTrmnDetail(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사례종결 이력등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int insertCaseTrmnHistory(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사례종결 심사담당자 등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int insertCaseTrmnPic(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사례종결 심사담당자 이력등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int insertCaseTrmnPicHistory(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사례종결 심사결과
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public String selectTrmnSrngResultSeCd(Map<String, String> paramMap) throws Exception;
	
	public Map<String, Object> selectCaseTrmnMmaInfo(Map<String, String> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectCaseTrmnYmd(Map<String, Object> paramMap) throws Exception;
	
	public void updateCaseTrmnYmd(Map<String, String> paramMap) throws Exception;
}

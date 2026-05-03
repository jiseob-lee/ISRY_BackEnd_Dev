/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;


/**
* @Class Name  : CaseTrmnServic.java
* @Description : 사례종결 Servic Class
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
public interface CaseTrmnService {

	/**
	* @Method    : 사례종결 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseTrmnList(DataRequest dataRequest) throws Exception;
	

	/**
	* @Method    : 사례종결 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public Map<String, Object> processCaseTrmnDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectCaseTrmnAprvList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 22. 
	 * @Method설명 : 종결승인 목록
	 */
	public Map<String, Object> selectCaseTrmnAprvList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectCaseTrmnAply
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 23. 
	 * @Method설명 : 종결신청 정보
	 */
	public Map<String, Object> selectCaseTrmnAply(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectUpperInst
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 7. 25. 
	 * @Method설명 : 종결수정 접속한종사자 상위기관조회
	 */
	public List<Map<String, Object>> selectUpperInst(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	
	/**
	 * @Method명   : updateCaseTrmnAprv
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 6. 26. 
	 * @Method설명 : 사례종결 수정
	 */
	public Map<String, Object> updateCaseTrmnAprv(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}

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
* @Class Name  : CaseTrprInfoService.java
* @Description : 사례대상자정보조회 팝업 Service Class
*
* @author  : Lee.Jun.Yeong
* @since   : 2022. 06. 29.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 06. 29.  Lee.Jun.Yeong    최초작성
* </pre>
*/
public interface CaseTrprInfoService {
	
	/**
	* 사례대상자정보 목록조회
	* @param     : Map  : TRPR_NM_ENCPT(대상자명암호화), CASE_TRPR_SLCTN_YMD(사례대상자선정일자)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseTrprInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}

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
* @Class Name  : TrprInfoService.java
* @Description : 대상자정보조회 팝업 Service Class
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
public interface TrprInfoService {
	
	/**
	* 대상자정보조회 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public Map<String, Object> selectTrprInfoList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Map<String, Object> selectTrprInfoInqList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mngrpage.aplcnttrprmng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : RsvtSmsService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 10. 14. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 10. 14.
 * @수정내용      : 
 * -                
 * -                
 */
public interface RsvtSmsService {

	/**
	 * @Method명   : selectRcptnTrprList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 14. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectRcptnTrprList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : insertRcptnTrpr
	 * @param request
	 * @param dataRequest
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 14. 
	 * @Method설명 :
	 */
	void insertRcptnTrpr(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}

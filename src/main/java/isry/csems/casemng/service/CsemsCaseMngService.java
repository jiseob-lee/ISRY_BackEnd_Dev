/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.casemng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명      	: CsemdCaseMngService.java
 * @프로그램 설명	:
 * - 
 * - 
 * @작성자      	: Lee.Seung.Yeon
 * @작성일      	: 2022. 10. 4.
 * @수정자      	: Lee.Seung.Yeon
 * @수정일      	: 2022. 10. 4.
 * @수정내용    	: 
 * -                
 * -                
 */
public interface CsemsCaseMngService {

	/**
	 * 사례관리_등록 상세정보 저장
	 * @Method명   : saveCaseMngRegDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 10. 4. 
	 * @Method설명 :
	 */
	public Map<String, String> saveCaseMngRegDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	



	/**
	 * @Method명   : chkCreateQustnbMngNoYn
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 2. 
	 * @Method설명 :
	 */
	public List<Map<String, String>> chkCreateQustnbMngNoYn(HttpServletRequest request, DataRequest dataRequest) throws Exception;





	/**
	 * @Method명   : createQustnb
	 * @param request
	 * @param dataRequest
	 * @param resultMap 
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 2. 
	 * @Method설명 :
	 */
	//public void createQustnb(HttpServletRequest request, DataRequest dataRequest, Map<String, Object> resultMap) throws Exception;





	/**
	 * @Method명   : srvyWrtStts
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 2. 
	 * @Method설명 :
	 */
	public Map<String, String> srvyWrtStts(HttpServletRequest request, DataRequest dataRequest) throws Exception;





	/**
	 * @Method명   : chkQustnbTmptUseYn
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 9. 
	 * @Method설명 :
	 */
	public Map<String, Object> chkQustnbTmptUseYn(HttpServletRequest request, DataRequest dataRequest) throws Exception;



}	

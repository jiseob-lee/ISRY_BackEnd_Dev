/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.survsht.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : SurvshtCmmnsInqService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 12. 7. 
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 12. 7.
 * @수정내용      : 
 * -                
 * -                
 */
public interface SurvshtCmmnsInqService {

	/**
	 * @Method명   : searchQustnbTmptUseYn
	 * @param resultMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 7. 
	 * @Method설명 : 사용할 설문지 템플릿관리번호가 사용중인지 미사용인지 여부 조회
	 */
	Map<String, Object> searchQustnbTmptUseYn(Map<String, Object> resultMap) throws Exception;

	/**
	 * @param dataRequest 
	 * @param request 
	 * @Method명   : getQustnbMngNo
	 * @param searchMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 8. 
	 * @Method설명 :
	 */
	Map<String, Object> getQustnbMngNo(HttpServletRequest request, DataRequest dataRequest, Map<String, Object> searchMap) throws Exception;

	
	
}

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
 * @파일명        : IdntfcTrprService.java
 * @프로그램 설명 : 개인식별등록 팝업
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 8. 22. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 8. 22.
 * @수정내용      : 
 * -                
 * -                
 */
public interface IdntfcTrprService {
	
	/**
	 * @Method명   : selectIdntfcTrprList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 22. 
	 * @Method설명 : 개인식별등록 목록 조회
	 */
	public List<Map<String, Object>> selectIdntfcTrprList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : processIndvIdntfcReg
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 23. 
	 * @Method설명 : 개인식별등록 
	 */
	public Map<String, Object> processIndvIdntfcReg(HttpServletRequest request, DataRequest dataRequest) throws Exception; 

	/**
	 * @Method명   : processIndvIdntfcDel
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoon.Hee.Sung
	 * @작성일     : 2023. 8. 17. 
	 * @Method설명 : 개인식별 해제
	 */
	public void processIndvIdntfcDel(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}

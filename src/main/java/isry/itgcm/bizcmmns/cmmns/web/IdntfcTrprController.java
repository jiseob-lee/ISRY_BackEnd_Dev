/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcm.bizcmmns.cmmns.service.IdntfcTrprService;

/**
 * @파일명        : IdntfcTrprController.java
 * @프로그램 설명 :
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
@Controller
@RequestMapping(value = "/isry/itgcm/bizcmmns/cmmns")
public class IdntfcTrprController {
	
	@Resource(name = "idntfcTrprService")
	private IdntfcTrprService idntfcTrprService;
	
	
	/**
	 * @Method명   : selectIdntfcTrprList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 22. 
	 * @Method설명 : 개인식별 목록 조회
	 */
	@RequestMapping(value = "/selectIdntfcTrprList.do")
	public View selectIdntfcTrprList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String,Object>> retList = idntfcTrprService.selectIdntfcTrprList(request, dataRequest);
		
		dataRequest.setResponse("dsIdntfcList", retList);
		
		
		return new JSONDataView();
	};
	
	
	/**
	 * @Method명   : processIndvIdntfcReg
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 23. 
	 * @Method설명 : 개인식별 등록
	 */
	@RequestMapping(value = "/processIndvIdntfcReg.do")
	public View processIndvIdntfcReg(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = idntfcTrprService.processIndvIdntfcReg(request, dataRequest);
		
		// 식별한 식별번호로 화면 재조회 위해 식별번호 화면으로 
//		Map<String, Object> message = new HashMap<String, Object>();
//		message.put("INDV_IDNTFC_NO", retMap.get("INDV_IDNTFC_NO"));		
//		dataRequest.setMetadata(true, message);
		
		dataRequest.setResponse("dmPersonalInfo", retMap);
		
		return new JSONDataView();
	};

	/**
	 * @Method명   : processIndvIdntfcDel
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoon.Hee.Sung
	 * @작성일     : 2023. 8. 17. 
	 * @Method설명 : 개인식별 해제
	 */
	@RequestMapping(value = "/processIndvIdntfcDel.do")
	public View processIndvIdntfcDel(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		idntfcTrprService.processIndvIdntfcDel(request, dataRequest);
		
		return new JSONDataView();
	};
}

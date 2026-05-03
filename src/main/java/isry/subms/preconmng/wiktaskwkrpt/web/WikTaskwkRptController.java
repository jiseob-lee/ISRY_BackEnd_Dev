/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.wiktaskwkrpt.web;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.subms.cmmn.service.SubmsService;
import isry.subms.preconmng.wiktaskwkrpt.service.WikTaskwkRptService;

/**
 * @파일명        : WikTaskWorkRptController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Seoung.Jae
 * @작성일        : 2022. 6. 10. 
 * @수정자        : Lee.Seoung.Jae
 * @수정일        : 2022. 6. 10.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/subms/preconmng/wiktaskwkrpt")
public class WikTaskwkRptController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//주간업무보고 서비스
	@Resource(name="wikTaskwkRptService")
	private WikTaskwkRptService wikTaskwkRptService;
	
	//이주배경 공통 서비스
	@Resource(name="submsService")
	private SubmsService submsService;
	
	/**
	 * @Method명   : selectWikTaskwkCombo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 1. 
	 * @Method설명 : 콤보데이터 조회
	 */
	@RequestMapping("/selectWikTaskwkCombo.do")
	public View selectWikTaskwkCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception{
		dataRequest.setResponse("dsBizYr", submsService.selectBizYrCombo(request));
		dataRequest.setResponse("dsSrvcExcnBiz", submsService.selectSrvcExcnBizCombo(request));
		dataRequest.setResponse("dsResrce", submsService.selectResrceNmCombo(request));
		dataRequest.setResponse("dsOperInst", submsService.selectInstNmCombo(request));
		
		return new JSONDataView();
		
	}
	
	/**
	 * @Method명   : selectWikTaskwkList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 1. 
	 * @Method설명 : 주간업무보고 목록조회
	 */
	@RequestMapping("/selectWikTaskwkList.do")
	public View selectWikTaskwkList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception{
		
		List<Map<String, String>> resultList = wikTaskwkRptService.selectWikTaskwkList(dataRequest);
		dataRequest.setResponse("dsList", resultList);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectWikTaskwk
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 1. 
	 * @Method설명 : 주간업무보고 상세조회
	 */
	@RequestMapping("/selectWikTaskwk.do")
	public View selectWikTaskwk(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		Map<String,List<Map<String, Object>>> responseMap = wikTaskwkRptService.selectWikTaskwk(request, dataRequest);
		dataRequest.setResponse("dsWikOperRpt", responseMap.get("dsWikOperRpt"));
		dataRequest.setResponse("dsWeekMng", responseMap.get("dsWeekMng"));
		dataRequest.setResponse("dsPgmInfo", responseMap.get("dsPgmInfo"));
		dataRequest.setResponse("dsLinkData", responseMap.get("dsLinkData"));
		
		return new JSONDataView();

	}
	
	/**
	 * @Method명   : selectWikTaskwkSearch
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 1. 
	 * @Method설명 : 주간업무보고 상세에서 조건으로 검색
	 */
	@RequestMapping("/selectWikTaskwkSearch.do")
	public View selectWikTaskwkSearch(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		Map<String,List<Map<String, Object>>> responseMap = wikTaskwkRptService.selectWikTaskwkSearch(request, dataRequest);
		dataRequest.setResponse("dsWikOperRpt", responseMap.get("dsWikOperRpt"));
		dataRequest.setResponse("dsWeekMng", responseMap.get("dsWeekMng"));
		dataRequest.setResponse("dsPgmInfo", responseMap.get("dsPgmInfo"));
		dataRequest.setResponse("dsLinkData", responseMap.get("dsLinkData"));
		
		return new JSONDataView();

	}
	
	/**
	 * @Method명   : saveWikTaskwkRpt
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 1. 
	 * @Method설명 : 주간업무보고 등록/수정/삭제
	 */
	@RequestMapping("/saveWikTaskwkRpt.do")
	public View saveWikTaskwkRpt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception{
		
		Map<String, Object> returnMap = wikTaskwkRptService.saveWikTaskwkRpt(request, dataRequest);
		
		dataRequest.setResponse("dmDtlParam", returnMap);
		
		return new JSONDataView();
	}
}









/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.atendcomplprecon.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.subms.preconmng.atendcomplprecon.service.AtendComplPreconService;

/**
 * @파일명        : AtendComplPreconController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2023. 6. 26. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2023. 6. 26.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/subms/preconmng/atendcomplprecon")
public class AtendComplPreconController {
	
	@Resource(name = "atendComplPreconService")
	private AtendComplPreconService atendComplPreconService;
	
	
	/**
	 * 
	 * @Method명   : selectAtendComplPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 6. 26. 
	 * @Method설명 : 출석 및 이수현황 목록 조회
	 */
	@RequestMapping(value = "/selectAtendComplPreconList.do")
	public View selectAtendComplPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", atendComplPreconService.selectAtendComplPreconList(dataRequest, request));
		
		return new JSONDataView();
	}
	
	
	/**
	 * 
	 * @Method명   : saveAtendComplPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 7. 3. 
	 * @Method설명 : 출석 및 이수현황 목록 수정
	 */
	@RequestMapping(value = "/saveAtendComplPreconList.do")
	public View saveAtendComplPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		atendComplPreconService.saveAtendComplPreconList(dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectAtendComplPreconMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 6. 29. 
	 * @Method설명 : 출석 및 이수현황 상세 조회
	 */
	@RequestMapping(value = "/selectAtendComplPreconMngList.do")
	public View selectAtendComplPreconMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", atendComplPreconService.selectAtendComplPreconMngList(dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : saveAtendComplPreconMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 7. 3. 
	 * @Method설명 : 출석 및 이수현황 상세 수정
	 */
	@RequestMapping(value = "/saveAtendComplPreconMng.do")
	public View saveAtendComplPreconMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception	{
		
		atendComplPreconService.saveAtendComplPreconMng(dataRequest);
		
		return new JSONDataView();
	}
}

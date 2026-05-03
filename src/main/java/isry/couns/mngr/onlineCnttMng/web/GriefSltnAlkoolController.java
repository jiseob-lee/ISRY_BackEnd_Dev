/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.onlineCnttMng.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.couns.mngr.onlineCnttMng.service.GriefSltnAlkoolService;

@Controller
@RequestMapping("/isry/couns/mngr/onlineCnttMng")
public class GriefSltnAlkoolController {
			
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
    @Autowired
    private GriefSltnAlkoolService griefSltnAlkoolService;
    
    /**
	 * 고민해결백과 주제 조회
	 * @Method명   : selectGriefSltnAlkoolThemaList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 21. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subGriefSltnAlkoolThemaList.do")
	public View selectGriefSltnAlkoolThemaList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
				
		dataRequest.setResponse("dsGriefSltnAlkoolThemaList", griefSltnAlkoolService.selectGriefSltnAlkoolThemaList(dataRequest));
	
	return new JSONDataView();
	
	}
	
	/**
	 * 고민해결백과 주제 등록
	 * @Method명   : griefSltnAlkoolThemaInsert
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 21. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subGriefSltnAlkoolThemaInsert.do")
	@ResponseBody
	public View griefSltnAlkoolThemaInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = griefSltnAlkoolService.griefSltnAlkoolThemaInsert(request, dataRequest);		
		log.debug("griefSltnAlkoolThemaInsert retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 고민해결백과 등록
	 * @Method명   : griefSltnAlkoolInsert
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subGriefSltnAlkoolInsert.do")						    
	@ResponseBody
	public View griefSltnAlkoolInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = griefSltnAlkoolService.griefSltnAlkoolInsert(request, dataRequest);		
		log.debug("griefSltnAlkoolInsert retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 고민해결백과 주제 수정 조회
	 * @Method명   : selectGriefSltnAlkoolThemaUpdate
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 21. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subGriefSltnAlkoolThemaUpdateInq.do")
	public View selectGriefSltnAlkoolThemaUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dmGriefSltnAlkoolThemaReg" , griefSltnAlkoolService.selectGriefSltnAlkoolThemaUpdate(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 고민해결백과 수정 조회
	 * @Method명   : selectGriefSltnAlkoolUpdate
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subGriefSltnAlkoolUpdateInq.do")
	public View selectGriefSltnAlkoolUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dmGriefSltnAlkoolReg" , griefSltnAlkoolService.selectGriefSltnAlkoolUpdate(dataRequest));
		
		return new JSONDataView();
		
	}
	
	/**
	 * 고민해결백과 주제 삭제
	 * @Method명   : griefSltnAlkoolThemaDelete
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subGriefSltnAlkoolThemaDelete.do")
	@ResponseBody
	public View griefSltnAlkoolThemaDelete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = griefSltnAlkoolService.griefSltnAlkoolThemaDelete(request, dataRequest);		
		log.debug("griefSltnAlkoolThemaDelete retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 고민해결백과 주제 수정
	 * @Method명   : griefSltnAlkoolThemaUpdate
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subGriefSltnAlkoolThemaUpdate.do")
	@ResponseBody
	public View griefSltnAlkoolThemaUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = griefSltnAlkoolService.griefSltnAlkoolThemaUpdate(request, dataRequest);		
		log.debug("griefSltnAlkoolThemaUpdate retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 고민해결백과 주제 수정
	 * @Method명   : griefSltnAlkoolUpdate
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subGriefSltnAlkoolUpdate.do")
	@ResponseBody
	public View griefSltnAlkoolUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = griefSltnAlkoolService.griefSltnAlkoolUpdate(request, dataRequest);		
		log.debug("griefSltnAlkoolUpdate retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 고민해결백과 onload 조회
	 * @Method명   : selectGriefSltnAlkool
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subGriefSltnAlkool.do")
	public View selectGriefSltnAlkool(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
				
		dataRequest.setResponse("daGriefSltnAlkool", griefSltnAlkoolService.selectGriefSltnAlkool(request,dataRequest));
	
	return new JSONDataView();
	
	}
	
	/**
	 * 고민해결백과 조회
	 * @Method명   : selectGriefSltnAlkoolList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 21. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subGriefSltnAlkoolList.do")
	public View selectGriefSltnAlkoolList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
				
		dataRequest.setResponse("dsGriefSltnAlkoolList", griefSltnAlkoolService.selectGriefSltnAlkoolList(dataRequest));
	
	return new JSONDataView();
	
	}
	
	/**
	 * 고민해결백과 삭제
	 * @Method명   : griefSltnAlkoolDelete
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 24. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subGriefSltnAlkoolDelete.do")
	@ResponseBody
	public View griefSltnAlkoolDelete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = griefSltnAlkoolService.griefSltnAlkoolDelete(request, dataRequest);		
		log.debug("griefSltnAlkoolDelete retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
    
    
}
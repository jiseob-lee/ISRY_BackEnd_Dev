/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.sndng.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.couns.constt.sndng.service.SndngService;
import isry.couns.mngr.mntrngReprtsMng.service.MntrngReprtsMngService;

/**
 * @파일명        : SndngController.java
 * @프로그램 설명 : 이음-e 발송
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 10. 05. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 10. 05. 
 * @수정내용      : 이음-e 발송
 * -                
 * -                
 */

@Controller
@RequestMapping("/isry/couns/constt/sndng")
public class SndngController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
    
	@Resource(name = "sndngService")
	private SndngService sndngService;
	
	/**
	 * 독려문자발송 조회
	 * @Method명   : selectChrctrSndngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 05. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subChrctrSndngList.do")
	public View selectChrctrSndngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 독려문자발송 조회
		dataRequest.setResponse("dsChrctrSndngList", sndngService.selectChrctrSndngList(dataRequest));
	
		return new JSONDataView();
	}
	
	/**
	 * 발송내역 조회
	 * @Method명   : selectSndngHistbList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 05. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subSndngHistbList.do")
	public View selectSndngHistbList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 발송내역 조회
		dataRequest.setResponse("dsSndngHistbList", sndngService.selectSndngHistbList(dataRequest));
	
		return new JSONDataView();
	}
	
	/**
	 * SMS 보내기
	 * @Method명   : saveSms
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 07. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subSaveSms.do")
	public View saveSms(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		sndngService.saveSms(request, dataRequest);
		
		return new JSONDataView();
	}
	
}

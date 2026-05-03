/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.slfrlsprtpensn.splymtenblr.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.pubms.slfrlsprtpensn.splymtenblr.service.SplymtEnblrService;

/**
 * @파일명        : SplymtEnblrController.java
 * @프로그램 설명 : 자립지원수당 - 2. 수급자현황
 * @작성자        : Baek.Gyu.Ha
 * @작성일        : 2023.07.26
 * @수정자        : Baek.Gyu.Ha
 * @수정일        : 2023.07.26
 * @수정내용      : 
 * - [2023-08-30, Gyu.Ha.Baek] PRE 반영
 * -                
 */

@Controller
@RequestMapping("/isry/pubms/slfrlsprtpensn/splymtenblr")
public class SplymtEnblrController {
	
//	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "splymtEnblrService")
	private SplymtEnblrService splymtEnblrService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value = "/selectSplymtEnblrMtchngList.do")
	public View selectSplymtEnblrMtchngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//목록 조회
		Map<String, Object> result = splymtEnblrService.selectSplymtEnblrMtchngList(request, dataRequest);
		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectSplymtEnblrDtlList.do")
	public View selectSplymtEnblrDtlList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//목록 조회
		Map<String, Object> result = splymtEnblrService.selectSplymtEnblrDtlList(request, dataRequest);
		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dsSebList", result.get("dsSebList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));
		dataRequest.setResponse("dmPage2", result.get("dmPage2"));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectMtchngList.do")
	public View selectMtchngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
				
		//목록 조회
		Map<String, Object> result = splymtEnblrService.selectMtchngList(request, dataRequest);
		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/updateMtchngReg.do")
	public View updateMtchngReg(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		splymtEnblrService.updateMtchngReg(request, dataRequest);
		return new JSONDataView();
	}	

}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.gitple.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;
import com.google.gson.JsonObject;

import egovframework.com.cmm.service.EgovProperties;
import io.swagger.annotations.ResponseHeader;
import isry.base.IsryBaseController;
import isry.gitple.service.GitpleEventService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.DisconnectUserService;

/**
 * @파일명        : GitpleEventController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 5. 26. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 5. 31.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/gitple")
public class GitpleEventController extends IsryBaseController {

	@Resource(name = "gitpleEventService")
	private GitpleEventService gitpleEventService;
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);	
	
	@RequestMapping(value="/gitpleSave.do")
	@ResponseBody
	public View gitpleEvent(@RequestBody JSONObject body) throws Exception {
		LOGGER.info("gitple에서 이벤트를 받았습니다. gitpleSave 실행");  
		gitpleEventService.insertAYBData(body);
		return new JSONDataView();
	}
	
	@RequestMapping(value="/existsGitpleID.do")
	public View existsGitpleID(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		String gitpleId = gitpleEventService.existsGitpleID(request);
		Map<String, String> map = new HashMap<String, String>();
		map.put("GITPLE_ID", gitpleId);
		
		//System.out.println("DDD : "+ map.toString());        
		
		dataRequest.setResponse("gitpleID", map);
		return new JSONDataView();
	}
	
	@RequestMapping(value="/gitpleManager.do")
	public View gitpleManager(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		String sUrl = gitpleEventService.gitpleManager(request);
		Map<String, String> map = new HashMap<String, String>();
		map.put("GITPLE_URL", sUrl);
		dataRequest.setResponse("dmUrl",     map);
		return new JSONDataView();
	}
	
	@RequestMapping(value="/gitpleLogin.do")
	public void gitpleLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// http://116.67.91.151/isry/gitple/gitpleLogin.do
		String qToken = request.getHeader("qtoken");
		String secret = request.getHeader("Secret");
		String strJson = "";
		
		if("gitplechat".equals(secret)) {
			String gitpleId = gitpleEventService.gitpleId(qToken);
			strJson = "{\"loginId\": \"" + gitpleId + "\"}";
		} else {
			strJson = "유효한 Secret이 아닙니다.";
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(strJson);
	}
	
	@RequestMapping(value="/gitpleLogout.do")
	public View gitpleLogout(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		String msg = gitpleEventService.gitpleLogout(request);
		Map<String, String> map = new HashMap<String, String>();
		map.put("LOGOUT_MSG", msg);
		dataRequest.setResponse("dmLogout", map);
		return new JSONDataView();
	}
}

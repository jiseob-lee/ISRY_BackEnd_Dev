/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.util.StringUtil;

/**
 * @파일명        : UserInstAuthController.java
 * @프로그램 설명 : 사용자별 기관 권한
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2023. 2. 21. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2023. 2. 21.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class UserInstAuthController {
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService service;
	
	/**
	 * 사용자별 기관 권한 체크
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkInstAuth.do", method = { RequestMethod.POST, RequestMethod.GET })
	public View checkInstAuth(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		// 결과 메시지 Data
    	Map<String, Object> message = new LinkedHashMap<String, Object>();
    	
    	// 사용자별 기관 권한 체크 및 결과 모델 설정 
    	Map<String, Object> resultMap = service.checkInstAuth(request, dataRequest, "dmInstAuthParam");
    	resultMap.forEach(message::put);
    	
    	dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}
	
	/**
	 * 기관 권한 조회
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectInstAuthList.do", method = { RequestMethod.POST, RequestMethod.GET })
	public View selectInstAuthList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmInstAuthParam");
		
		String dsName = searchParam.getValue("DS_NAME");
		if (StringUtil.isEmpty(dsName)) {
			throw new AppWorksException("필수 조건인 데이터셋명이 없습니다.", Alert.ERROR);
		}
		
		// 2023-02-21 (Myeong.Jae.Cheol) : 사용자별 기관 권한 목록 (SAB230) 적용
		List<Map<String, Object>> results = service.getUserInstAuthItems(request, dataRequest, "dmInstAuthParam");
		dataRequest.setResponse(dsName, results);
		
		return new JSONDataView();
	}
	
	/**
	 * 기관 권한 세션 업데이트
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateInstAuthSession.do", method = { RequestMethod.POST, RequestMethod.GET })
	public View updateInstAuthSession(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		// 결과 메시지 Data
    	Map<String, Object> message = new LinkedHashMap<String, Object>();
		
		// 권한 세션 업데이트 및 결과 모델 설정 
    	Map<String, Object> resultMap = service.updateInstAuthSession(request, dataRequest, "dmInstAuthInfo");
    	resultMap.forEach(message::put);
    	
    	dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}

}

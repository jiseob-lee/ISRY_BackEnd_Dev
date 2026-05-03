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

import isry.itgcms.sysmgmt.userauth.service.UserAuthAplyService;
import isry.itgcms.util.StringUtil;

/**
 * @파일명        : UserAuthAplyController.java
 * @프로그램 설명 : 사용자 권한 신청
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2023. 2. 20. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2023. 2. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class UserAuthAplyController {
	
	@Resource(name = "userAuthAplyService")
	private UserAuthAplyService service;
	
	/**
	 * 신청 기관 목록 조회 (콤보박스)
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectAplyInstList.do", method = { RequestMethod.POST, RequestMethod.GET })
	public View selectAplyInstList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		String dsName = searchParam.getValue("DS_NAME");
		if (StringUtil.isEmpty(dsName)) {
			throw new AppWorksException("필수 조건인 데이터셋명이 없습니다.", Alert.ERROR);
		}
		
		List<Map<String, Object>> results = service.selectAplyInstList(request, dataRequest);
		dataRequest.setResponse(dsName, results);
		
		return new JSONDataView();
	}
	
	/**
	 * 콤보박스 데이터 조회
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectRgnComboDataList.do", method = { RequestMethod.POST, RequestMethod.GET })
	public View selectRgnComboDataList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmCmbSrch");
		
		// 콤보박스 데이터 조회
		List<Map<String, Object>> results = service.selectComboDataList(request, dataRequest);
		dataRequest.setResponse(searchParam.getValue("DS_NAME"), results);
		
		return new JSONDataView();
	}
	
	/**
	 * 사용자별 권한 신청 저장
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveUserAuthAply.do", method = RequestMethod.POST)
	public View saveUserAuthAply(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		// 결과 메시지 Data
    	Map<String, Object> message = new LinkedHashMap<String, Object>();
		
    	// 권한 신청 저장 및 결과 모델 설정 
    	Map<String, Object> resultMap = service.saveUserAuthAply(request, dataRequest);
    	resultMap.forEach(message::put);
    	
    	dataRequest.setMetadata(true, message);
    	
		return new JSONDataView();
	}
	
}

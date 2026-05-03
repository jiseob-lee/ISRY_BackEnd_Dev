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
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.userauth.service.MgmtUserAuthAprvService;

/**
 * @파일명        : MgmtUserAuthAprvController.java
 * @프로그램 설명 : 사용자 권한 승인 관리
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
public class MgmtUserAuthAprvController {
	
	@Resource(name = "mgmtUserAuthAprvService")
	private MgmtUserAuthAprvService service;
	
	/**
	 * 사용자 권한 승인 관리 OnLoad
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 */
	@RequestMapping(value = "/onLoadMgmtUserAuthAprv.do", method = { RequestMethod.POST, RequestMethod.GET })
	public View onLoadMgmtUserAuthAprv(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		// 승인관리자 기관 권한 목록 조회 및 응답 결과 설정
		List<Map<String, Object>> results = service.selectAprvAdminInstList(request, dataRequest);
		
		dataRequest.setResponse("dsCmbAprvInst", results);
		
		return new JSONDataView();
	}
	
	/**
	 * 사용자 권한 승인 목록 조회
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectUserAuthAprvList.do", method = { RequestMethod.POST, RequestMethod.GET })
	public View selectUserAuthAprvList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		// 권한 승인 목록 조회 및 응답 결과 설정
		List<Map<String, Object>> results = service.selectUserAuthAprvList(request, dataRequest);
		
		dataRequest.setResponse("dsList", results);
		
		return new JSONDataView();
	}
	
	/**
	 * 사용자 권한 승인 요청 상세 조회
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selectUserAuthAprvDetails.do", method = { RequestMethod.POST, RequestMethod.GET })
	public View selectUserAuthAprvDetails(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		// 권한 승인 요청 상세 조회 및 응답 결과 설정
		List<Map<String, Object>> results = service.selectUserAuthAprvDetails(request, dataRequest);
		
		dataRequest.setResponse("dsDtlAuthAply", results);
		
		return new JSONDataView();
	}
	
	/**
	 * 사용자 권한 요청 반려 처리
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/processUserAuthAplyByReject.do", method = RequestMethod.POST)
	public View processUserAuthAplyByReject(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		// 결과 메시지 Data
    	Map<String, Object> message = new LinkedHashMap<String, Object>();
    	
    	// 권한 요청 반려 처리 및 결과 모델 설정 
    	Map<String, Object> resultMap = service.processUserAuthAplyByReject(request, dataRequest);
    	resultMap.forEach(message::put);
    	
    	dataRequest.setMetadata(true, message);
    	
		return new JSONDataView();
	}
	
	/**
	 * 사용자 권한 요청 승인 처리
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/processUserAuthAplyByApproval.do", method = RequestMethod.POST)
	public View processUserAuthAplyByApproval(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		// 결과 메시지 Data
    	Map<String, Object> message = new LinkedHashMap<String, Object>();
    	
    	// 권한 요청 승인 처리 및 결과 모델 설정 
    	Map<String, Object> resultMap = service.processUserAuthAplyByApproval(request, dataRequest);
    	resultMap.forEach(message::put);
    	
    	dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}
	
	/**
	 * 사용자 권한 신청 취소 처리
	 * 
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/processUserAuthAplyByCancel.do", method = RequestMethod.POST)
	public View processUserAuthAplyByCancel(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		// 결과 메시지 Data
    	Map<String, Object> message = new LinkedHashMap<String, Object>();
    	
    	// 권한 신청 취소 처리 및 결과 모델 설정 
    	Map<String, Object> resultMap = service.processUserAuthAplyByCancel(request, dataRequest);
    	resultMap.forEach(message::put);
    	
    	dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.link.web;

import java.math.BigDecimal;
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

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseController;
import isry.drmgs.link.service.OfcdcLinkAplyService;

/**
 * @파일명        : OfcdcLinkAplyController.java
 * @프로그램 설명 : 교육청 연계신청
 * - 
 * - OfcdcLinkAplyController
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 8. 09. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 8. 09. 
 * @수정내용      : 교육청 연계신청
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/drmgs/link")
public class OfcdcLinkAplyController extends IsryBaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "ofcdcLinkAplyService")
	private OfcdcLinkAplyService ofcdcLinkAplyService;
	
	/**
	 * @Method     : processExcelUpload
	 * @Method설명 : 연계의뢰서 업로드(집단) 엑셀업로드
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 09. 
 	 */	
	@RequestMapping(value = "/processLinkRqstdoExcelUpload.do")
	public View processEduHrDtlRegExcelUpload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		LOGGER.debug("연계의뢰서 업로드(집단) 엑셀업로드 시작 ==>> ");
		
		ofcdcLinkAplyService.processLinkRqstdoExcelUpload(request, dataRequest); // 연계의뢰서 업로드(집단) + 교육청집단연계(AKA220) update
		
//		ofcdcLinkAplyService.saveLinkRqst(iMngSn); // 교육청집단연계(AKA220) update
		
		return new JSONDataView();
	}
	
	/**
	 * 연계의뢰서 업로드(집단) 조회
	 * @Method명   : selectLinkRqstdoList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 10. 
	 * @Method설명 : 연계의뢰서 업로드(집단) 조회
	 */	
	@RequestMapping(value = "/selectLinkRqstList.do")
	public View selectLinkRqstList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 조회
		dataRequest.setResponse("dsList", ofcdcLinkAplyService.selectLinkRqstList(request, dataRequest)); 
		
		return new JSONDataView();
	}
	
	/**
	 * 특별관리리스트 조회
	 * @Method명   : selectOfcdcSpclaMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 16. 
	 * @Method설명 : 특별관리리스트 조회
	 */	
	@RequestMapping(value = "/selectOfcdcSpclaMngList.do")
	public View selectOfcdcSpclaMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 조회
		dataRequest.setResponse("dsOfcdcSpclaMngList", ofcdcLinkAplyService.selectOfcdcSpclaMngList(request, dataRequest)); 
		
		return new JSONDataView();
	}
	
	/**
	 * 오류정보리스트 조회
	 * @Method명   : selectOfcdcErrorInfoList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 16. 
	 * @Method설명 : 오류정보리스트 조회
	 */	
	@RequestMapping(value = "/selectOfcdcErrorInfoList.do")
	public View selectOfcdcErrorInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 조회
		dataRequest.setResponse("dsOfcdcErrorInfoList", ofcdcLinkAplyService.selectOfcdcErrorInfoList(request, dataRequest)); 
		
		return new JSONDataView();
	}
	
	/**
	 * 연계의뢰서 업로드(집단) 상세 조회
	 * @Method명   : selectLinkRqstDetList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 04. 18.
	 * @Method설명 : 연계의뢰서 업로드(집단) 상세 조회
	 */	
	@RequestMapping(value = "/selectLinkRqstDetList.do")
	public View selectLinkRqstDetList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 조회
		dataRequest.setResponse("dsList", ofcdcLinkAplyService.selectLinkRqstDetList(request, dataRequest)); 
		
		return new JSONDataView();
	}
}

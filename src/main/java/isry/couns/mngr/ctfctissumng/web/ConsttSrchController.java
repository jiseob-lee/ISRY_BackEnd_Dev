/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.ctfctissumng.web;

import java.util.HashMap;
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
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.couns.cmmn.service.CounsService;
import isry.couns.mngr.ctfctissumng.service.ConsttSrchService;

/**
 * @파일명        : ConsttSrchController.java
 * @프로그램 설명 : 상담사 검색 Controller
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2022. 10. 31. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2022. 10. 31.
 * @수정내용      : 
 * -                
 * -                
 */
@Api(value = "상담사 검색 Controller")
@Controller
@RequestMapping(value = "/isry/couns/mngr/ctfctissumng")
public class ConsttSrchController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "counsService")
	private CounsService cmmnService;
	
	@Resource(name = "consttSrchService")
	private ConsttSrchService service;
	
	/**
	 * 
	 * @Method명   : onLoadConsttSrch
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 10. 31. 
	 * @Method설명 : 상담사 검색 팝업 호출시 초기 데이터
	 */
	@RequestMapping("/onLoadConsttSrch.do")
	public View onLoadConsttSrch(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		
		// 화면에서 넘어온 파라미터
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		// 부서코드에서 단위업무구분코드 조회
		String unitTaskWkCd = cmmnService.selectUnitTaskWorkSeCode(searchParam.getValue("OGDP_DEPT_CD"));
		
		// 결과 데이터 설정
		searchParam.setValue(0, "UNT_TASKWK_SE_CD", unitTaskWkCd);
		
		dataRequest.setResponse("dmSearch", searchParam.getSingleValueMap());
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectConsttList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 10. 31. 
	 * @Method설명 : 상담사 목록 조회
	 */
	@RequestMapping("/selectConsttList.do")
	public View selectConsttList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		// 목록 조회
		List<Map<String, Object>> results = service.selectConsttList(dataRequest, resPage);
		
		// 결과 데이터 설정
		dataRequest.setResponse("dsList", results);
		dataRequest.setResponse("dmPage", resPage);
		
		return new JSONDataView();
	}
}

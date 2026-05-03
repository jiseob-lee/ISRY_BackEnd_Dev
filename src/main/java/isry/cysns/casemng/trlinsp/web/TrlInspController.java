/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.casemng.trlinsp.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.cysns.casemng.trlinsp.servcie.TrlInspService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;

/**
 * @파일명 : TrlInspController.java
 * @프로그램 설명 : 심리검사 컨트롤러 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 11. 15.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 11. 15.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/cysns/casemng/trlinsp")
public class TrlInspController {

	// 심리검사 관련 서비스
	@Resource(name = "trlInspService")
	TrlInspService trlInspService;

	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	MgmtCmmnCodeService mgmtCmmnCodeService;

	/**
	 * @Method명 : onLoad
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 15.
	 * @Method설명 : 심리검사 관련 공통코드 조회
	 */
	@RequestMapping(value = "/onLoad.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		String trlInspResultMlsfcSeCd = dataRequest.getParameter("trlInspResultMlsfcSeCd");

		List<Map<String, Object>> dsTrlInspResultSclasSeCd = mgmtCmmnCodeService
				.selectCommonCode("TRL_INSP_RESULT_SCLAS_SE_CD", trlInspResultMlsfcSeCd);

		dataRequest.setResponse("dsTrlInspResultSclasSeCd", dsTrlInspResultSclasSeCd);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectTrlInsp
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 15.
	 * @Method설명 : 심리검사 조회
	 */
	@RequestMapping(value = "/selectTrlInsp.do")
	public View selectTrlInsp(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> requestMap = dmSearch.getSingleValueMap();

		List<Map<String, Object>> dsList = trlInspService.selectTrlInsp(requestMap);
		
		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : saveTrlInsp
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 15.
	 * @Method설명 : 심리검사 저장/수정/삭제
	 */
	@RequestMapping(value = "/saveTrlInsp.do")
	public View saveTrlInsp(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		trlInspService.saveTrlInsp(request, dataRequest);

		return new JSONDataView();
	}

}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.sample.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
//import com.penta.scpdb.ScpDbAgent;
//import com.penta.scpdb.ScpDbAgentException;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

import isry.base.IsryBaseController;
import isry.sample.service.CmnCodeService;
import isry.sample.service.SampleService;

/**
 * 
 * @파일명 : SampleController.java
 * @프로그램 설명 : - Sample서비스를 위한 웹 컨트롤러 입니다 -
 * @작성자 : Song.Young.Il
 * @작성일 : 2021. 11. 11.
 * @수정자 : Song.Young.Il
 * @수정일 : 2021. 11. 11.
 * @수정내용 : - -
 */
@Controller
@RequestMapping("/sample")
public class SampleController extends IsryBaseController {

	@Resource
	private SampleService sampleService;

	@Resource
	private CmnCodeService cmnCodeService;

	@RequestMapping("/loadSample.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		// 성별코드([TMP001])
		dataRequest.setResponse("dsGenderRcd", cmnCodeService.selectCmnCodeList("TMP001"));

		// 학생구분코드([TMP002])
		dataRequest.setResponse("dsStudDivRcd", cmnCodeService.selectCmnCodeList("TMP002"));

		// 주야간코드([TMP003])
		dataRequest.setResponse("dsDayNightDivRcd", cmnCodeService.selectCmnCodeList("TMP003"));

		// 국가코드([TMP004])
		dataRequest.setResponse("dsNatRcd", cmnCodeService.selectCmnCodeList("TMP004"));

		// 은행코드([TMP005])
		dataRequest.setResponse("dsBankRcd", cmnCodeService.selectCmnCodeList("TMP005"));
		
		return new JSONDataView();
	}

	@RequestMapping("/listSample.do")
	public View listSample(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmParam");

		Map<String, String> mapParam = new HashMap<>();
		mapParam.put("STUD_NO", param.getValue("strStudNo"));
		
		List<Map<String, Object>> listCmnTmpReg = sampleService.selectSample(mapParam);

		dataRequest.setResponse("dsCmnTmpReg", listCmnTmpReg);
		
		return new JSONDataView();

	}

	@RequestMapping("/saveSample.do")
	public View saveSample(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		sampleService.saveSample(dataRequest);
		return new JSONDataView();
	}
	
    @RequestMapping(value = "/transactionTest.do")
    public ModelAndView TransactionTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	ModelAndView modelAndView = new ModelAndView();
    	
    	sampleService.updateService();

    	modelAndView.addObject("flag", "1");
    	
    	modelAndView.setViewName("jsonView");
    	
    	return modelAndView;
    }

}

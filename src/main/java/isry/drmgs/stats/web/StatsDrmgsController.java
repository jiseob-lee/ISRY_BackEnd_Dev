/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.stats.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.drmgs.cnter.service.CnterPreconEnfsnService;
import isry.drmgs.stats.service.StatsDrmgsService;

/**
 * @파일명        : StatsDrmgsController.java
 * @프로그램 설명 : 학교밖지원센터 통계
 * @작성자        : Hee Sung Yoon
 * @작성일        : 2022. 12. 23. 
 * @수정자        : Hee Sung Yoon
 * @수정일        : 2022. 12. 23. 
 * @수정내용      : 학교밖청소년지원센터 통계
*/

@Controller
@RequestMapping(value = "/isry/drmgs/stats")
public class StatsDrmgsController extends IsryBaseController {
	
	@Resource(name = "statsDrmgsService")
	private StatsDrmgsService statsDrmgsService;

	@RequestMapping(value="/selectOccpAbilitStats.do")
	public View selectOccpAbilitStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", statsDrmgsService.selectOccpAbilitStats(request, dataRequest));		// 전문인력양성교육
		
		return new JSONDataView();
	}
	
	@RequestMapping(value="/selectMeditationStats.do")
	public View selectMeditationStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", statsDrmgsService.selectMeditationStats(request, dataRequest));		// 전문인력양성교육
		
		return new JSONDataView();
	}
	
	@RequestMapping(value="/selectGgBizStats.do")
	public View selectGgBizStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", statsDrmgsService.selectGgBizStats(request, dataRequest));		// 전문인력양성교육
		
		return new JSONDataView();
	}
}

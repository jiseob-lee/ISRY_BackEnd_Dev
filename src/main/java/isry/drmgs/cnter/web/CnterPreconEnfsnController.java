/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.cnter.web;

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

/**
 * @파일명        : CnterPreconEnfsnController.java
 * @프로그램 설명 : 센터별 종사자 현황
 * - 
 * - CnterPreconEnfsnController
 * @작성자        : Hee Sung Yoon
 * @작성일        : 2022. 8. 3o. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 8. 3o. 
 * @수정내용      : 센터별 종사자 현황
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/drmgs/cnter")
public class CnterPreconEnfsnController extends IsryBaseController {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "cnterPreconEnfsnService")
	private CnterPreconEnfsnService cnterPreconEnfsnService;

	@RequestMapping(value="/selectEnfsnInfo.do")
	public View selectRegion(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsEnfsnList", cnterPreconEnfsnService.selectEnfsnInfo(request, dataRequest));			// 종사자
		return new JSONDataView();
	}
	
	@RequestMapping(value="/selectEnfsnNtnCerti.do")
	public View selectEnfsnNtnCerti(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsEnfsnNtnCerti", cnterPreconEnfsnService.selectEnfsnCerti(request, dataRequest));		// 국가자격증
		return new JSONDataView();
	}
	@RequestMapping(value="/selectPrvateCerti.do")
	public View selectPrvateCerti(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsPrvateCerti", cnterPreconEnfsnService.selectEnfsnPrvateCerti(request, dataRequest));		// 청소년민간자격증
		return new JSONDataView();
	}
	@RequestMapping(value="/selectTrnngEdu.do")
	public View selectTrnngEdu(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsTrnngEdu", cnterPreconEnfsnService.selectTrnngEdu(request, dataRequest));		// 전문인력양성교육
		return new JSONDataView();
	}
}

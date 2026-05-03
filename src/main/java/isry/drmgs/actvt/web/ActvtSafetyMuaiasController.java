/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.actvt.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

//import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseController;
import isry.drmgs.actvt.service.ActvtSafetyMuaiasService;
import isry.drmgs.stdnt.service.YouthLifeRecodeService;

/**
 * @파일명        : ActvtSafetyMuaiasController.java
 * @프로그램 설명 : 활동안전공제회현황
 * - 
 * - 
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2022. 7. 13. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2022. 7. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/drmgs/actvt")
public class ActvtSafetyMuaiasController extends IsryBaseController {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "actvtSafetyMuaiasService")
	private ActvtSafetyMuaiasService actvtSafetyMuaiasService;
	
	@RequestMapping(value = "/selectActvtSafetyMuaiasList.do")
	public View selectActvtSafetyMuaiasList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		//활동안전공제회현황 목록 조회
		List<Map<String, Object>> list = actvtSafetyMuaiasService.selectActvtSafetyMuaiasList(request, dataRequest);
		dataRequest.setResponse("dsList", list);

		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectActvtSafetyMuaiasPopupList.do")
	public View selectActvtSafetyMuaiasPopupList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		//활동안전공제회현황 목록 조회
		List<Map<String, Object>> list = actvtSafetyMuaiasService.selectActvtSafetyMuaiasPopupList(request, dataRequest);
		dataRequest.setResponse("dsList", list);

		return new JSONDataView();
	}

}

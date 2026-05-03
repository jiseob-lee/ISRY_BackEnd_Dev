/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.config.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.config.service.MgmtCmmnConfigService;

/**
 * @파일명        : MgmtCmmnConfigController.java
 * @프로그램 설명 : 환경설정 관리
 * - 
 * - 
 * @작성자        : Hee Sung Yoon
 * @작성일        : 2023.01.16. 
 * @수정자        : Hee Sung Yoon
 * @수정일        : 2023.01.16.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/config")
public class MgmtCmmnConfigController extends IsryBaseController {

	@Resource(name="mgmtCmmnConfigService")
	private MgmtCmmnConfigService mgmtCmmnConfigService;
	
	@RequestMapping(value = "/selectConfigList.do")
	public View selectConfigList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		/*
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmConfigParam");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		String stngId = paramMap.get("STNG_ID");
		dataRequest.setResponse("dsConfigList", mgmtCmmnConfigService.selectConfigList(stngId));
		*/
		return new JSONDataView();
	}

}

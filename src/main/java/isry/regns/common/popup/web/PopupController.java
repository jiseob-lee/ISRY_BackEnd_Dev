/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.regns.common.popup.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.regns.common.popup.service.PopupService;

/**
 * @파일명        : PopupController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 6. 3. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 6. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/regns/common/popup")
public class PopupController {
	
	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "popupService")
	private PopupService popupService;
	
	@RequestMapping("/selectCmitMtgList.do")
	public View selectCmitMtgList(DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", popupService.selectCmitMtgList(dataRequest));
		
		return new JSONDataView();
	}

	@RequestMapping("/selectLinkInstList.do")
	public View selectLinkInstList(DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", popupService.selectLinkInstList(dataRequest));
		
		return new JSONDataView();
	}

	@RequestMapping("/selectEmrgRptList.do")
	public View selectEmrgRptList(DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");

		if (param.getValue("SRCH_SE").equals("RPT")) {
			dataRequest.setResponse("dsList", popupService.selectEmrgRptList(dataRequest));
		} else {
			dataRequest.setResponse("dsList", popupService.selectEmrgActnList(dataRequest));
		}
		
		return new JSONDataView();
	}

}

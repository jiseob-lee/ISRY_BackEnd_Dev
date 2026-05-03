/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.regns.casemng.casereg.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.regns.casemng.casereg.service.RegnsRegService;

/**
 * @파일명        : RegnsRegController.java
 * @프로그램 설명 :
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2023. 1. 5. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2023. 1. 5.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/regns/casemng/casereg")
public class RegnsRegController {
	
	@Resource(name = "regnsRegService")
	private RegnsRegService regnsRegService;
	
	
	@RequestMapping("/selectReqById.do")
	public View selectReqById(DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", regnsRegService.selectReqById(dataRequest));
		dataRequest.setResponse("dsEfectnList", regnsRegService.selectEfectnById(dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping("/saveData.do")
	public View saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, String> dmParam = regnsRegService.saveData(request, dataRequest);

		dataRequest.setResponse("dmParam", dmParam);

		return new JSONDataView();
	}
	
}

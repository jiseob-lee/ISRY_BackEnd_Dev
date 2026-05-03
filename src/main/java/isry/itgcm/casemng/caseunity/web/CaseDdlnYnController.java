/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcm.casemng.caseunity.service.CaseDdlnYnService;

/**
 * @파일명        : CaseDdlnYnController.java
 * @프로그램 설명 : 마감여부 Controller Class
 * 
 * @작성자        : Choi.Doo.Il
 * @작성일        : 2022. 9. 05. 
 * @수정자        : 
 * @수정일        : 
 * @수정내용      : 
 * 
 */
@Controller
@RequestMapping(value = "/isry/itgcm/casemng/caseunity")
public class CaseDdlnYnController {

	@Resource(name = "caseDdlnYnService")
	private CaseDdlnYnService caseDdlnYnService;


	/**
	* @Method    : 마감여부
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCaseDdlnYn.do")
	public View caseDdlnYn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//사례마감여부
		
		String sDdlnYm 	      = "";
		String sUntTaskwkSeCd = "";
		
		Map<String, Object> infoMap = caseDdlnYnService.caseDdlnYn(sUntTaskwkSeCd, sDdlnYm);
		
		return new JSONDataView();
	}

}
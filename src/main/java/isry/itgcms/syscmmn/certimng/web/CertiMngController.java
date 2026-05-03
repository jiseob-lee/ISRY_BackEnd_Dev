/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.certimng.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.syscmmn.certimng.service.CertiMngService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;

/**
 * @파일명      	: CertiMngController.java
 * @프로그램 설명	: 자격증에 대한 내역을 관리한다.
 * - 
 * - 
 * @작성자      	: Lee.Seung.Yeon
 * @작성일      	: 2022. 9. 15.
 * @수정자      	: Lee.Seung.Yeon
 * @수정일      	: 2022. 9. 15.
 * @수정내용    	: 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/itgcms/syscmmn/certiMng")
public class CertiMngController {
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name = "certiMngService")
	private CertiMngService certiMngService;

	@RequestMapping(value = "/onLoad.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsQlfcAcqsKnd", mgmtCmmnCodeService.selectCommonCode("OUTC_CN_MLSFC_SE_CD", "06"));
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectCertiList.do")
	public View selectCertiList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", certiMngService.selectCertiList(dataRequest));
		
		return new JSONDataView();
	}

}

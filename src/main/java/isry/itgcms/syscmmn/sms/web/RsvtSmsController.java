/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.sms.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.syscmmn.sms.service.RsvtSmsService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;

/**
 * @파일명        : RsvtSmsController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 10. 13. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 10. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller()
@RequestMapping("/isry/itgcms/syscmmn/sms")
public class RsvtSmsController {
	
	@Resource(name = "rsvtSmsService")
	private RsvtSmsService rsvtSmsService;
	
	// 공통코드 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value = "/selectSmsCombo.do")
	public View selectSmsCombo(DataRequest dataRequest, HttpServletRequest request) throws Exception{
		
		dataRequest.setResponse("dsSndng", mgmtCmmnCodeService.selectCommonCode("SNDNG_STTS_SE_CD"));
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectSmsRcptnTrprList.do")
	public View selectSmsRcptnTrprList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception{
		
		List<Map<String, String>> info = rsvtSmsService.selectSmsRcptnTrprList(request, dataRequest);
		
		dataRequest.setResponse("dsList", info);
		
		return new JSONDataView();
	}
	
}

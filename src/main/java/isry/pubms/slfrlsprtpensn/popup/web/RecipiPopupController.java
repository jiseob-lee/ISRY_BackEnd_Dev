/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.slfrlsprtpensn.popup.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.pubms.slfrlsprtpensn.popup.serivce.RecipiPopupService;

/**
 * @파일명        : RecipiPopupController.java
 * @프로그램 설명 : 자립지원수당 - 각 팝업
 * @작성자        : Baek.Gyu.Ha
 * @작성일        : 2023.08.31
 * @수정자        : Baek.Gyu.Ha
 * @수정일        : 2023.08.31
 * @수정내용      : 
 * - [2023-08-31, Gyu.Ha.Baek] PRE 반영
 * -                
 */

@Controller
@RequestMapping("/isry/pubms/slfrlsprtpensn/popup")
public class RecipiPopupController {
	
//	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "recipiPopupService")
	private RecipiPopupService recipiPopupService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value = "/selectOrganizationViewList.do")
	public View selectOrganizationViewList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//목록 조회
		Map<String, Object> result = recipiPopupService.selectOrganizationViewList(request, dataRequest);
		dataRequest.setResponse("dsOrganization", result.get("dsOrganization"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));
		
		return new JSONDataView();
	}

}

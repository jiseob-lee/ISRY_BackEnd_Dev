/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userauth.service.RsfrInstAprvService;
import isry.itgcms.sysmgmt.userjoin.service.SrchAddrService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : RsfrInstAprvController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2023. 1. 9. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2023. 1. 9.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class RsfrInstAprvController {
	
	@Resource(name="srchAddrService")
    private SrchAddrService srchAddrService;	
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;	
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;	
	
	@Resource(name = "rsfrInstAprvService")
	private RsfrInstAprvService rsfrInstAprvService;
	
	
	/**
	 * @Method명   : selectRsfrInstMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공기관 승인목록/공통코드
	 */
	@RequestMapping(value = {"/onloadtRsfrInstAprvList.do", "/selectRsfrInstAprvList.do"})
	public View selectRsfrInstMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String UntTaskwk = "";
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}		
		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		if(requestUrl.endsWith("/selectRsfrInstAprvList.do")) {
			dataRequest.setResponse("dsRsfrAprvMngList", rsfrInstAprvService.selectRsfrInstAprvList(request, dataRequest));
		}
		
		if (requestUrl.endsWith("/onloadtRsfrInstAprvList.do")) {
			dataRequest.setResponse("dsInstType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", UntTaskwk));
			dataRequest.setResponse("dsAprvSttsSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("APRV_STTS_SE_CD", UntTaskwk));
			dataRequest.setResponse("dsSigungu", srchAddrService.selectSgg());		
		}		
		
		return new JSONDataView();
	}	
	
	/**
	 * @Method명   : processRsfrInstAprvRjct
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 10. 
	 * @Method설명 : 순수자원제공기관 승인/반려
	 */
	@RequestMapping(value = {"/updateRsfrInstAprv.do", "/updateRsfrInstRjct.do"})
	public void processRsfrInstAprvRjct(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		if(requestUrl.endsWith("/updateRsfrInstAprv.do")) {
			rsfrInstAprvService.saveRsfrInstAprv(request, dataRequest);
		}
		
		if (requestUrl.endsWith("/updateRsfrInstRjct.do")) {
			rsfrInstAprvService.saveRsfrInstRjct(request, dataRequest);
		}		
	}		

}

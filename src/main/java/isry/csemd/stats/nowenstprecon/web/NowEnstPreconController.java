/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.stats.nowenstprecon.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.csemd.cmmn.service.CsemdService;
import isry.csemd.stats.nowenstprecon.service.NowEnstPreconService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : NowEnstPreconController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2023. 2. 13. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2023. 2. 13.
 * @수정내용      : 
 * -                
 * -                
 */

@Controller
@RequestMapping(value = "/isry/csemd/stats/nowenstprecon")
public class NowEnstPreconController {
	
	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	// 세션정보호출 관련 서비스
	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;
	
	// 현재입교생현황 관련 서비스
	@Resource(name = "nowEnstPreconService")
	private NowEnstPreconService nowEnstPreconService;
	
	//디딤센터 공통 서비스
	@Resource(name = "csemdService")
	private CsemdService csemdService;
	
	/**
	 * @Method명   : onLoadNowEnstPrecon
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 2. 6. 
	 * @Method설명 : 현재입교생현황 onLoad
	 */
	@RequestMapping(value = "/onLoadNowEnstPrecon.do")
	public View onLoadNowEnstPrecon(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		requestMap.put("AUTHRT_SE_CD", loginVO.getAuthrtSeCd());

		
		// 공통코드 대표주호소문제구분코드
		dataRequest.setResponse("dsRprsMaapProbmSeCd",
				mgmtCmmnCodeService.selectCommonCodeUnit("RPRS_MAAP_PROBM_SE_CD", loginVO.getUntTaskwk()));
		
		// 기관
		dataRequest.setResponse("dsInst", csemdService.selectInstCmb(requestMap));
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectNowEnstPrecon
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 현재입교생현황 통계
	 */
	@RequestMapping(value = "/selectNowEnstPrecon.do")
	public View selectNowEnstPrecon(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		nowEnstPreconService.selectNowEnstPrecon(request, dataRequest);

		return new JSONDataView();
	}

}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.stats.entscpreconunityreprts.web;

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
import isry.csemd.stats.entscpreconunityreprts.servcie.EntscPreconUnityReprtsService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : EntscPreconUnityReprtsController.java
 * @프로그램 설명 : 입교현황통합보고서 컨트롤러 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 2. 7.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 2. 7.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/csemd/stats/entscpreconunityreprts")
public class EntscPreconUnityReprtsController {

	// 세션정보호출 관련 서비스
	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	// 디딤센터 공통 서비스
	@Resource(name = "csemdService")
	private CsemdService csemdService;

	// 입교현황통합보고서 관련 서비스
	@Resource(name = "entscPreconUnityReprtsService")
	private EntscPreconUnityReprtsService entscPreconUnityReprtsService;

	/**
	 * @Method명 : onLoadEntscPreconUnityReprts
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 7.
	 * @Method설명 : 입교현황통합보고서 onLoad
	 */
	@RequestMapping(value = "/onLoadEntscPreconUnityReprts.do")
	public View onLoadEntscPreconUnityReprts(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		requestMap.put("AUTHRT_SE_CD", loginVO.getAuthrtSeCd());

		// 사업연도
		dataRequest.setResponse("dsBizYr", csemdService.selectBizYrCmb(requestMap));
		// 기관
		dataRequest.setResponse("dsInst", csemdService.selectInstCmb(requestMap));
		// 과정 - 서비스실행사업
		dataRequest.setResponse("dsCrse", csemdService.selectSrvcExcnBizCmb(requestMap));

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectEntscPreconUnityReprts
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 7.
	 * @Method설명 : 입교현황통합보고서 조회
	 */
	@RequestMapping(value = { "/selectEntscPreconUnityReprts.do", "/selectTotTrprList.do" })
	public View selectEntscPreconUnityReprts(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		entscPreconUnityReprtsService.selectEntscPreconUnityReprts(request, dataRequest);

		return new JSONDataView();
	}
}

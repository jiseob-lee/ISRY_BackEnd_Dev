/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.stats.ensttrlinsp.web;

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
import isry.csemd.stats.ensttrlinsp.service.EnstTrlInspService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : EnstTrlInspController.java
 * @프로그램 설명 : 입교생심리검사 컨트롤러 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 2. 7.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 2. 7.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/csemd/stats/ensttrlinsp")
public class EnstTrlInspController {

	// 세션정보호출 관련 서비스
	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	// 디딤센터 공통 서비스
	@Resource(name = "csemdService")
	private CsemdService csemdService;

	@Resource(name = "enstTrlInspService")
	private EnstTrlInspService enstTrlInspService;

	/**
	 * @Method명 : onLoadEnstTrlInsp
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 7.
	 * @Method설명 : 입교생심리검사 onLoad
	 */
	@RequestMapping(value = "/onLoadEnstTrlInsp.do")
	public View onLoadEnstTrlInsp(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		requestMap.put("AUTHRT_SE_CD", loginVO.getAuthrtSeCd());

		// 기관
		dataRequest.setResponse("dsInst", csemdService.selectInstCmb(requestMap));
		// 과정 - 서비스실행사업
		dataRequest.setResponse("dsCrse", csemdService.selectSrvcExcnBizCmb(requestMap));
		// 문항정보
		dataRequest.setResponse("dsQesitmInfo", enstTrlInspService.selectQesitm(dataRequest));

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectPopulStatsInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 7.
	 * @Method설명 : 인구통계학적정보 조회
	 */
	@RequestMapping(value = "/selectPopulStatsInfo.do")
	public View selectPopulStatsInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		enstTrlInspService.selectPopulStatsInfo(request, dataRequest);
		
		return new JSONDataView();
	}

	/**
	 * @Method명 : selectAwarExmn
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 7.
	 * @Method설명 : 인지도조사 조회
	 */
	@RequestMapping(value = "/selectAwarExmn.do")
	public View selectAwarExmn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		enstTrlInspService.selectAwarExmn(dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectEmtGhvr
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 2. 8. 
	 * @Method설명 : 정서행동검사통계
	 */
	@RequestMapping(value = "/selectEmtGhvr.do")
	public View selectEmtGhvr(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		enstTrlInspService.selectEmtGhvr(dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectTrlEmtInsp
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 2. 15. 
	 * @Method설명 : 심리정서검사통계
	 */
	@RequestMapping(value = "/selectTrlEmtInsp.do")
	public View selectTrlEmtInsp(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		enstTrlInspService.selectTrlEmtInsp(dataRequest);
		
		return new JSONDataView();
	}
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.stats.srvctot.web;

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
import isry.csemd.stats.srvctot.service.SrvcTotService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : SrvcTotController.java
 * @프로그램 설명 : 서비스별집계 Controller - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 2. 13.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 2. 13.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/csemd/stats/srvctot")
public class SrvcTotController {

	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	// 세션정보호출 관련 서비스
	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	// 디딤센터 공통 서비스
	@Resource(name = "csemdService")
	private CsemdService csemdService;

	// 서비스별집계 관련 서비스
	@Resource(name = "srvcTotService")
	private SrvcTotService srvcTotService;

	/**
	 * @Method명 : OnloadYrStats
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 13.
	 * @Method설명 : 연도별통계 Onload
	 */
	@RequestMapping(value = "/onLoadYrStats.do")
	public View onLoadYrStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		requestMap.put("AUTHRT_SE_CD", loginVO.getAuthrtSeCd());

		// 기관
		dataRequest.setResponse("dsInst", csemdService.selectInstCmb(requestMap));
		// 사업연도
		dataRequest.setResponse("dsBizYr", csemdService.selectBizYrCmb(requestMap));
		// 서비스실행사업
		dataRequest.setResponse("dsSrvcExcnBiz", csemdService.selectSrvcExcnBizCmb(requestMap));

		// 공통코드 : 성별구분코드
		dataRequest.setResponse("dsSxdcSeCd",
				mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", loginVO.getUntTaskwk()));
		// 공통코드 : 학력구분코드
		dataRequest.setResponse("dsAcbgSeCd",
				mgmtCmmnCodeService.selectCommonCodeUnit("ACBG_SE_CD", loginVO.getUntTaskwk()));
		// 공통코드 : 주거형태구분코드
		dataRequest.setResponse("dsResideShapeSeCd",
				mgmtCmmnCodeService.selectCommonCodeUnit("RESIDE_SHAPE_SE_CD", loginVO.getUntTaskwk()));
		// 공통코드 : 공적부조구분코드
		dataRequest.setResponse("dsPblastSeCd",
				mgmtCmmnCodeService.selectCommonCodeUnit("PBLAST_SE_CD", loginVO.getUntTaskwk()));

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectYrStats
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 13.
	 * @Method설명 : 연도별통계 조회
	 */
	@RequestMapping(value = "/selectYrStats.do")
	public View selectYrStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		srvcTotService.selectYrStats(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : onLoadPrdStats
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 13.
	 * @Method설명 : 기간별통계 onLoad
	 */
	@RequestMapping(value = "/onLoadPrdStats.do")
	public View onLoadPrdStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		requestMap.put("AUTHRT_SE_CD", loginVO.getAuthrtSeCd());

		// 기관
		dataRequest.setResponse("dsInst", csemdService.selectInstCmb(requestMap));

		return new JSONDataView();
	}

	/**
	 * @Method명   : onLoadCrseEnfsnStats
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 2. 14. 
	 * @Method설명 : 과정별통계 & 종사자별통계 onLoad
	 */
	@RequestMapping(value = "/onLoadCrseEnfsnStats.do")
	public View onLoadCrseEnfsnStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		requestMap.put("AUTHRT_SE_CD", loginVO.getAuthrtSeCd());

		// 기관
		dataRequest.setResponse("dsInst", csemdService.selectInstCmb(requestMap));
		// 사업연도
		dataRequest.setResponse("dsBizYr", csemdService.selectBizYrCmb(requestMap));
		// 서비스실행사업
		dataRequest.setResponse("dsSrvcExcnBiz", csemdService.selectSrvcExcnBizCmb(requestMap));

		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectPrdCrseEnfsnStats
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 2. 14. 
	 * @Method설명 : 기간별통계 & 과정별통계 & 종사자별통계 조회
	 */
	@RequestMapping(value = "/selectPrdCrseEnfsnStats.do")
	public View selectPrdCrseEnfsnStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		srvcTotService.selectPrdCrseEnfsnStats(request, dataRequest);

		return new JSONDataView();
	}
}

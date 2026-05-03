/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.sysmng.srvymng.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.XBConfig;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.cleopatra.spring.UIView;

import isry.csemd.cmmn.service.CsemdService;
import isry.csemd.sysmng.srvymng.service.SrvyMngService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.StringUtil;

/**
 * @파일명 : SrvyMngController.java
 * @프로그램 설명 : 설문관리 컨트롤러 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 10. 25.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 10. 25.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/csemd/sysmng/srvymng")
public class SrvyMngController {

	// 설문관리 서비스
	@Resource(name = "srvyMngService")
	SrvyMngService srvyMngService;

	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	MgmtCmmnCodeService mgmtCmmnCodeService;

	// 드림&디딤 콤보데이터 조회 서비스
	@Resource(name = "csemdService")
	CsemdService csemdService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectSrvyMngCmb
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 10. 25.
	 * @Method설명 : 설문관리 콤보데이터 조회
	 */
	@RequestMapping(value = "/selectSrvyMngCmb.do")
	public View selectSrvyMngCmb(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		requestMap.put("AUTHRT_SE_CD", loginVO.getAuthrtSeCd());
		
		// 사업연도
		List<Map<String, Object>> dsBizYr = csemdService.selectBizYrCmb(requestMap);

		// 기관정보
		List<Map<String, Object>> dsInstCmb = csemdService.selectInstCmb(requestMap);

		// 과정정보(서비스실행사업)
		List<Map<String, Object>> dsSrvcExcnBizCmb = csemdService.selectSrvcExcnBizCmb(requestMap);

		// 공통코드
		// 배정그룹소분류구분코드
		List<Map<String, Object>> dsAltmntGroupSclasSeCd = mgmtCmmnCodeService
				.selectCommonCodeUnit("ALTMNT_GROUP_SCLAS_SE_CD", loginVO.getUntTaskwk());
		// 성별구분코드
		List<Map<String, Object>> dsSxdcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD",loginVO.getUntTaskwk());
		// 대상자관계구분코드
		List<Map<String, Object>> dsTrprRelSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("TRPR_REL_SE_CD",
				loginVO.getUntTaskwk());
		// 설문지종류구분코드
		List<Map<String, Object>> dsQustnbKndSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("QUSTNB_KND_SE_CD",
				loginVO.getUntTaskwk());
		// 설문시기구분코드
		List<Map<String, Object>> dsSrvyEraSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SRVY_ERA_SE_CD",loginVO.getUntTaskwk());

		dataRequest.setResponse("dsBizYr", dsBizYr);
		dataRequest.setResponse("dsInstCmb", dsInstCmb);
		dataRequest.setResponse("dsSrvcExcnBizCmb", dsSrvcExcnBizCmb);
		dataRequest.setResponse("dsAltmntGroupSclasSeCd", dsAltmntGroupSclasSeCd);
		dataRequest.setResponse("dsSxdcSeCd", dsSxdcSeCd);
		dataRequest.setResponse("dsTrprRelSeCd", dsTrprRelSeCd);
		dataRequest.setResponse("dsQustnbKndSeCd", dsQustnbKndSeCd);
		dataRequest.setResponse("dsSrvyEraSeCd", dsSrvyEraSeCd);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectSrvyCn
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 10. 25.
	 * @Method설명 : 설문내용 조회
	 */
	@RequestMapping(value = "/selectSrvyCn.do")
	public View selectSrvyCn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		ParameterGroup dmParam = dataRequest.getParameterGroup("dmParam");

		Map<String, String> requestMap = dmParam.getSingleValueMap();

		// 대상자정보 조회
		List<Map<String, Object>> dsTrprInfo = srvyMngService.selectTrprInfo(requestMap);
		// 설문 응답 정보 조회
		List<Map<String, Object>> dsSrvyRspnsInfo = srvyMngService.selectSrvyRspnsInfo(requestMap);

		dataRequest.setResponse("dsTrprInfo", dsTrprInfo);
		dataRequest.setResponse("dsSrvyRspnsInfo", dsSrvyRspnsInfo);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectSrvyChart
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 25.
	 * @Method설명 : 설문차트 조회
	 */
	@RequestMapping(value = "/selectSrvyChart.do")
	public View selectSrvyChart(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		srvyMngService.selectSrvyChart(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectSrvyAnlsCn
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 25.
	 * @Method설명 : 설문내용 분석내용 조회
	 */
	@RequestMapping(value = "/selectSrvyAnlsCn.do")
	public View selectSrvyAnlsCn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		srvyMngService.selectSrvyAnlsCn(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectSrvyRecodeCmb
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 10. 26.
	 * @Method설명 : 설문이력 목록 콤보
	 */
	@RequestMapping(value = "/selectSrvyRecodeCmb.do")
	public View selectSrvyRecodeCmb(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
		requestMap.put("AUTHRT_SE_CD", loginVO.getAuthrtSeCd());

		// 사업연도
		List<Map<String, Object>> dsBizYr = csemdService.selectBizYrCmb(requestMap);

		// 기관정보
		List<Map<String, Object>> dsInstCmb = csemdService.selectInstCmb(requestMap);

		// 과정정보(서비스실행사업)
		List<Map<String, Object>> dsSrvcExcnBizCmb = csemdService.selectSrvcExcnBizCmb(requestMap);

		// 공통코드
		// 성별구분코드
		List<Map<String, Object>> dsSxdcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD",loginVO.getUntTaskwk());

		// 배정그룹소분류구분코드(생활동)
		List<Map<String, Object>> dsAltmntGroupSclasSeCd = mgmtCmmnCodeService
				.selectCommonCodeUnit("ALTMNT_GROUP_SCLAS_SE_CD", loginVO.getUntTaskwk());

		// 설문지 종류
		List<Map<String, Object>> dsQustnbKndSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("QUSTNB_KND_SE_CD",
				loginVO.getUntTaskwk());

		// 설문진행상태
		List<Map<String, Object>> dsSrvyPrgrsSttsSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SRVY_PRGRS_STTS_SE_CD",loginVO.getUntTaskwk());

		// 설문시기
		List<Map<String, Object>> dsSrvyEraSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SRVY_ERA_SE_CD",
				loginVO.getUntTaskwk());

		// 대상자와의관계
		List<Map<String, Object>> dsTrprRelSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("TRPR_REL_SE_CD",
				loginVO.getUntTaskwk());

		// 설문수신방법(재확인 필요)
		List<Map<String, Object>> dsSrvyRcptnMthdSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SRVY_RCPTN_MTHD_SE_CD",loginVO.getUntTaskwk());

		dataRequest.setResponse("dsBizYrCmb", dsBizYr);
		dataRequest.setResponse("dsInstCmb", dsInstCmb);
		dataRequest.setResponse("dsSrvcExcnBizCmb", dsSrvcExcnBizCmb);
		dataRequest.setResponse("dsSxdcSeCd", dsSxdcSeCd);
		dataRequest.setResponse("dsAltmntGroupCmb", dsAltmntGroupSclasSeCd);
		dataRequest.setResponse("dsSuryListCmb", dsQustnbKndSeCd);
		dataRequest.setResponse("dsSrvyPrgrsSttsSeCdCmb", dsSrvyPrgrsSttsSeCd);
		dataRequest.setResponse("dsSrvyEraSeCdCmb", dsSrvyEraSeCd);
		dataRequest.setResponse("dsTrprRelSeCdCmb", dsTrprRelSeCd);
		dataRequest.setResponse("dsSrvyRcptnMthdCmb", dsSrvyRcptnMthdSeCd);

		return new JSONDataView();

	}

	/**
	 * @Method명 : selectSrvyRecodeList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 10. 27.
	 * @Method설명 : 설문이력 목록 조회
	 */
	@RequestMapping(value = "/selectSrvyRecodeList.do")
	public View selectSrvyRecodeList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> requestMap = dmSearch.getSingleValueMap();

		List<Map<String, Object>> list = srvyMngService.selectSrvyRecodeList(request, dataRequest);

		dataRequest.setResponse("dsList", list);
		dataRequest.setResponse("dsQustnbList", srvyMngService.selectQustnbList(requestMap));
		dataRequest.setResponse("dsQustnbQesitm", srvyMngService.selectQustnbQesitm(requestMap));

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : selectSrvySndngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 31.
	 * @Method설명 : 설문지발송목록대상자 리스트 조회
	 */
	@RequestMapping(value = "/selectSrvySndngList.do")
	public View selectSrvySndngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		List<Map<String, String>> list = srvyMngService.selectSrvySndngList(request, dataRequest);
		dataRequest.setResponse("dsList", list);

		return new JSONDataView();
	}
	
	
	/**
	 * 
	 * @Method명   : createQustnb
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 12. 13. 
	 * @Method설명 : 설문지생성
	 */
	@RequestMapping(value = "/createQustnb.do")
	public View createQustnb(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		Map<String, Object> message = srvyMngService.chkQustnbTmptUseYn(request, dataRequest);
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}
	
	
	/**
	 * 
	 * @Method명 : updateQustnbCompno
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 31.
	 * @Method설명 : 설문지 발송(복수)
	 */
	@RequestMapping(value = "/updateQustnbCompno.do")
	public View updateQustnbCompno(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest, Map<String, Object> resultMap)
			throws Exception {

		srvyMngService.updateQustnbCompno(request, dataRequest, resultMap);

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : updateQustnbSingle
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 11. 3.
	 * @Method설명 : 설문지 발송(단일)
	 */
	@RequestMapping(value = "/updateQustnbSingle.do")
	public View updateQustnbSingl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest, Map<String, Object> resultMap)
			throws Exception {

		srvyMngService.updateQustnbSingle(request, dataRequest, resultMap);

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : mobileQustnbWrit
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 11. 17.
	 * @Method설명 : 모바일버전 설문작성화면
	 */
	@RequestMapping(value = "/mblaSrvyPtcptn.do")
	public View mobileQustnbWrit(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		String qustnbMngNo = StringUtil.nullConvert(request.getParameter("qustnbMngNo"));

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("QUSTNB_MNG_NO", qustnbMngNo);

		List<String> pathList = XBConfig.getInstance().getDeployPath(); // eXbuilder6 deploy path
		String deployPath = pathList.get(0);
		String pageUrl = deployPath + "/";
		
		LocalDateTime now = LocalDateTime.now();
		String currentTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String refreshParam = "?p=" + currentTime;
		
		//pageUrl += "app/csemd/aplcntpage/aplcnttrprmng/SurvshtDtlPopup2.clx";
		pageUrl += "app/itgcm/outsdsrvyptcptn/CmmnsSurvshtDtlPopup.clx";

		return new UIView(pageUrl, paramMap);

	}

	/**
	 * 
	 * @Method명   : selectAddtng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 12. 14. 
	 * @Method설명 : 설문지 템플릿 관리번호 조회
	 */
	@RequestMapping(value = "/selectAddtng.do")
	public View selectAddtng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {

		ParameterGroup dmAddtng = dataRequest.getParameterGroup("dmAddtng");
		Map<String, String> dmMap = dmAddtng.getSingleValueMap();

		dataRequest.setResponse("dmAddtng", srvyMngService.selectAddtng(dmMap));
		
		return new JSONDataView();
	}
	
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.history.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.history.service.PersonalHistoryService;
import isry.itgcms.sysmgmt.userjoin.service.SrchAddrService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : PersonalHistoryController.java
 * @프로그램 설명 : 사용자의 이력 조회 및 상세조회 Controller
 * @작성자 : Ji-Seob.Lee
 * @작성일 : 2022. 10. 8.
 * @수정자 : Ji-Seob.Lee
 * @수정일 : 2022. 10. 8.
 * @수정내용 : - -
 */

@Controller
@RequestMapping("/isry/itgcm/sysmgmt/history")
public class PersonalHistoryController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "personalHistoryService")
	private PersonalHistoryService personalHistoryService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="srchAddrService")
    private SrchAddrService srchAddrService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	// 종사자 이력
	@RequestMapping(value = {"/onLoadWorkerHistory.do", "/listWorkerHistory.do"})
	public View workerHistory(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		log.debug("test");
		
		//ScpDb scpDb = new ScpDb();
		
		Map<String, Object> dmSearchMap = new HashMap<>(dmSearch.getSingleValueMap());

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = personalHistoryService.selectWorkerHistoryCount(dmSearchMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		dmSearchMap.put("OFFSET_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = personalHistoryService.selectWorkerHistory(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		//log.debug("#### requestUrl : " + requestUrl);
		
		if (requestUrl.endsWith("/onLoadWorkerHistory.do")) {
			dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
			dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));
			dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk()));  // 단위 시스템
		}
		
		return new JSONDataView();
	}

	// 종사자 이력 상세
	@RequestMapping(value = "/onLoadWorkerHistoryDetail.do")
	public View workerHistoryDetail(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk()));  // 단위 시스템
		dataRequest.setResponse("dsQlfcSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("QLFC_SE_CD", userVo.getUntTaskwk()));  // 자격구분코드
		dataRequest.setResponse("dsSnsSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SNS_SE_CD", userVo.getUntTaskwk()));  // SNS구분코드
		
		return new JSONDataView();
	}

	// 청소년 보호자 이력
	@RequestMapping(value = {"/onLoadYouthGuardianHistory.do", "/listYouthGuardianHistory.do"})
	public View youthGuardianHistory(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//ScpDb scpDb = new ScpDb();
		
		Map<String, Object> dmSearchMap = new HashMap<>(dmSearch.getSingleValueMap());

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = personalHistoryService.selectYouthGuardianHistoryCount(dmSearchMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		dmSearchMap.put("OFFSET_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = personalHistoryService.selectYouthGuardianHistory(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		//log.debug("#### requestUrl : " + requestUrl);
		
		if (requestUrl.endsWith("/onLoadYouthGuardianHistory.do")) {
			dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
			dataRequest.setResponse("dsSgg", srchAddrService.selectSgg());  // 시군구
			dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));
		}
		
		return new JSONDataView();
	}

	// 청소년 보호자 이력 상세
	@RequestMapping(value = "/onLoadYouthGuardianHistoryDetail.do")
	public View youthGuardianHistoryDetail(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
		dataRequest.setResponse("dsSgg", srchAddrService.selectSgg());  // 시군구
		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsTrprBcrnTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("TRPR_BCRN_TYPE_SE_CD", userVo.getUntTaskwk()));  // 대상자배경유형구분코드
		dataRequest.setResponse("dsNltyNtnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("NLTY_NTN_SE_CD", userVo.getUntTaskwk()));  // 국적국가구분코드
		dataRequest.setResponse("dsBrthNtnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("BRTH_NTN_SE_CD", userVo.getUntTaskwk()));  // 출생국가구분코드
		dataRequest.setResponse("dsGrowthNtnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("GROWTH_NTN_SE_CD", userVo.getUntTaskwk()));  // 성장국가구분코드
		dataRequest.setResponse("dsVisaTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("VISA_TYPE_SE_CD", userVo.getUntTaskwk()));  // 비자유형구분코드
		dataRequest.setResponse("dsRelgnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RELGN_SE_CD", userVo.getUntTaskwk()));  // 종교구분코드
		
		return new JSONDataView();
	}

	// 개인정보 이력
	@RequestMapping(value = {"/onLoadPersonalInfoHistory.do", "/listPersonalInfoHistory.do"})
	public View personalInfoHistory(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//ScpDb scpDb = new ScpDb();
		
		Map<String, Object> dmSearchMap = new HashMap<>(dmSearch.getSingleValueMap());

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = personalHistoryService.selectPersonalInfoHistoryCount(dmSearchMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		dmSearchMap.put("OFFSET_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = personalHistoryService.selectPersonalInfoHistory(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		//log.debug("#### requestUrl : " + requestUrl);
		
		if (requestUrl.endsWith("/onLoadPersonalInfoHistory.do")) {
			dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
			dataRequest.setResponse("dsLastAcbgSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("LAST_ACBG_SE_CD", userVo.getUntTaskwk()));  // 최종학력구분코드
			dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));
		}
		
		return new JSONDataView();
	}


	// 개인정보 이력 상세
	@RequestMapping(value = "/onLoadPersonalInfoHistoryDetail.do")
	public View personalInfoHistoryDetail(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
		dataRequest.setResponse("dsLastAcbgSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("LAST_ACBG_SE_CD", userVo.getUntTaskwk()));  // 최종학력구분코드
		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsSnsSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SNS_SE_CD", userVo.getUntTaskwk()));  // SNS구분코드
		dataRequest.setResponse("dsSgg", srchAddrService.selectSgg());  // 시군구
		
		return new JSONDataView();
	}

	// 로그인 사용자 이력
	@RequestMapping(value = {"/onLoadLoginUserHistory.do", "/listLoginUserHistory.do"})
	public View loginUserHistory(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//ScpDb scpDb = new ScpDb();
		
		Map<String, Object> dmSearchMap = new HashMap<>(dmSearch.getSingleValueMap());

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = personalHistoryService.selectLoginUserHistoryCount(dmSearchMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		dmSearchMap.put("OFFSET_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = personalHistoryService.selectLoginUserHistory(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		//log.debug("#### requestUrl : " + requestUrl);
		
		if (requestUrl.endsWith("/onLoadLoginUserHistory.do")) {
			dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
			dataRequest.setResponse("dsUserState", mgmtCmmnCodeService.selectCommonCodeUnit("USER_ID_USE_SE_CD", userVo.getUntTaskwk()));  // 회원 가입 상태 : 신청, 승인, 반려, 사용중지, 삭제
		}
		
		return new JSONDataView();
	}

	// 로그인 사용자 이력 상세
	@RequestMapping(value = "/onLoadLoginUserHistoryDetail.do")
	public View loginUserHistoryDetail(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
		dataRequest.setResponse("dsUserState", mgmtCmmnCodeService.selectCommonCodeUnit("USER_ID_USE_SE_CD", userVo.getUntTaskwk()));  // 회원 가입 상태 : 신청, 승인, 반려, 사용중지, 삭제
		dataRequest.setResponse("dsUserCntnIntrcpSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("USER_CNTN_INTRCP_SE_CD", userVo.getUntTaskwk()));
		
		return new JSONDataView();
	}

}

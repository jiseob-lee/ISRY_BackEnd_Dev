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
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.history.service.HistoryService;
import isry.itgcms.sysmgmt.pgmemu.service.MgmtMenuService;
import isry.itgcms.sysmgmt.userjoin.service.SrchAddrService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;

/**
 * @파일명 : HistoryController.java
 * @프로그램 설명 : 이력 조회 및 상세조회 Controller
 * @작성자 : Park.Kyu.Young
 * @작성일 : 2022. 4. 7.
 * @수정자 : Park.Kyu.Young
 * @수정일 : 2022. 4. 7.
 * @수정내용 : - -
 */
@Controller
@RequestMapping("/isry/itgcm/sysmgmt/history")
public class HistoryController extends IsryBaseController {

	@Resource(name = "mgmtMenuService")
	private MgmtMenuService mgmtMenuService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name = "srchAddrService")
	private SrchAddrService srchAddrService;

	@Resource(name = "historyService")
	private HistoryService historyService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명   : onLoadProgramHistory
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 4. 7.
	 * @Method설명 : 프로그램이력 조회 조건 데이터 세팅
	 */
	@RequestMapping(value = "/onLoadProgramHistory.do")
	public View onLoadProgramHistory(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		dataRequest.setResponse("dsProgramUseCode", mgmtCmmnCodeService.selectCommonCodeUnit("PROGRM_USE_SE_CD", userVo.getUntTaskwk())); // 프로그램사용 구분
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
		dataRequest.setResponse("dsRootMenuList", mgmtMenuService.selectRootMenu()); // 상위메뉴 구분
		return new JSONDataView();
	}

	/**
	 * @Method명   : onLoadProgramHistoryDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 4. 7.
	 * @Method설명 : 프로그램이력 상세 팝업
	 */
	@RequestMapping(value = "/onLoadProgramHistoryDetail.do")
	public View onLoadProgramHistoryDetail(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsProgramUseCode", mgmtCmmnCodeService.selectCommonCodeUnit("PROGRM_USE_SE_CD", userVo.getUntTaskwk())); // 프로그램사용 구분
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
		dataRequest.setResponse("dsRootMenuList", mgmtMenuService.selectRootMenu()); // 상위메뉴 구분
		return new JSONDataView();
	}

	/**
	 * @Method명   : selectProgramHistory
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2.
	 * @Method설명 : 프로그램이력 조회
	 */
	@RequestMapping(value = "/selectProgramHistory.do")
	public View selectProgramHistory(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, Object> dmSearchMap = new HashMap<>();
		dmSearchMap.put("PROGRM_NM", dmSearch.getValue("PROGRM_NM"));
		dmSearchMap.put("UNT_SYS_SE_CD", dmSearch.getValue("UNT_SYS_SE_CD"));
		dmSearchMap.put("URL_ADDR", dmSearch.getValue("URL_ADDR"));
		dmSearchMap.put("PROGRM_USE_SE_CD", dmSearch.getValue("PROGRM_USE_SE_CD"));
		dmSearchMap.put("DATAA_CHG_SE_CD", dmSearch.getValue("DATAA_CHG_SE_CD"));
		dmSearchMap.put("START_DATE", dmSearch.getValue("START_DATE"));
		dmSearchMap.put("END_DATE", dmSearch.getValue("END_DATE"));

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = historyService.selectProgramHistoryTotalCount(dmSearchMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = historyService.selectProgramHistory(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		return new JSONDataView();
	}

	/**
	 * @Method명   : onLoadMenuHistory
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2.
	 * @Method설명 : 메뉴이력 조회 조건 세팅
	 */
	@RequestMapping(value = "/onLoadMenuHistory.do")
	public View onLoadMenuHistory(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
		dataRequest.setResponse("dsRootMenuList", mgmtMenuService.selectRootMenu()); // 상위메뉴 구분
		// 단위업무구분코드 추가
		dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk()));
		return new JSONDataView();
	}

	/**
	 * @Method명   : onLoadMenuHistoryDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2.
	 * @Method설명 : 메뉴이력 상세 팝업
	 */
	@RequestMapping(value = "/onLoadMenuHistoryDetail.do")
	public View onLoadMenuHistoryDetail(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
		dataRequest.setResponse("dsRootMenuList", mgmtMenuService.selectRootMenu()); // 상위메뉴 구분
		return new JSONDataView();
	}

	/**
	 * @Method명   : selectMenuHistory
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2.
	 * @Method설명 : 메뉴이력 조회
	 */
	@RequestMapping(value = "/selectMenuHistory.do")
	public View selectMenuHistory(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, Object> dmSearchMap = new HashMap<>();
		dmSearchMap.put("MENU_NM", dmSearch.getValue("MENU_NM"));
		dmSearchMap.put("UP_MENU_ID", dmSearch.getValue("UP_MENU_ID"));
		dmSearchMap.put("URL_ADDR", dmSearch.getValue("URL_ADDR"));
		dmSearchMap.put("RM_CN", dmSearch.getValue("RM_CN"));
		dmSearchMap.put("DATAA_CHG_SE_CD", dmSearch.getValue("DATAA_CHG_SE_CD"));
		dmSearchMap.put("START_DATE", dmSearch.getValue("START_DATE"));
		dmSearchMap.put("END_DATE", dmSearch.getValue("END_DATE"));
		// 단위업무 및 작업자
		dmSearchMap.put("UNT_TASKWK_SE_CD", dmSearch.getValue("UNT_TASKWK_SE_CD"));
		dmSearchMap.put("WORKER", dmSearch.getValue("WORKER"));

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = historyService.selectMenuHistoryTotalCount(dmSearchMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = historyService.selectMenuHistory(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		return new JSONDataView();
	}

	/**
	 * @Method명   : onLoadDeptHistory
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2.
	 * @Method설명 : 부서이력 조회 조건 세팅
	 */
	@RequestMapping(value = "/onLoadDeptHistory.do")
	public View onLoadDeptHistory(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
		// 단위업무구분 추가
		dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk())); // 단위업무구분
		return new JSONDataView();
	}

	/**
	 * @Method명   : onLoadDeptHistoryDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2.
	 * @Method설명 : 부서이력 상세 팝업
	 */
	@RequestMapping(value = "/onLoadDeptHistoryDetail.do")
	public View onLoadDeptHistoryDetail(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
		return new JSONDataView();
	}

	/**
	 * @Method명   : selectDeptHistory
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2.
	 * @Method설명 : 부서이력 조회
	 */
	@RequestMapping(value = "/selectDeptHistory.do")
	public View selectDeptHistory(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, Object> dmSearchMap = new HashMap<>();
		dmSearchMap.put("INST_NM", dmSearch.getValue("INST_NM"));
		dmSearchMap.put("DEPT_NM", dmSearch.getValue("DEPT_NM"));
		dmSearchMap.put("MAIN_TASKWK_CN", dmSearch.getValue("MAIN_TASKWK_CN"));
		dmSearchMap.put("DEPT_TELNO", dmSearch.getValue("DEPT_TELNO"));
		dmSearchMap.put("DATAA_CHG_SE_CD", dmSearch.getValue("DATAA_CHG_SE_CD"));
		dmSearchMap.put("START_DATE", dmSearch.getValue("START_DATE"));
		dmSearchMap.put("END_DATE", dmSearch.getValue("END_DATE"));
		// 단위업무 및 작업자 추가
		dmSearchMap.put("UNT_TASKWK_SE_CD", dmSearch.getValue("UNT_TASKWK_SE_CD"));
		dmSearchMap.put("WORKER", dmSearch.getValue("WORKER"));

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = historyService.selectDeptHistoryTotalCount(dmSearchMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = historyService.selectDeptHistory(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		return new JSONDataView();
	}

	/**
	 * @Method명   : onLoadInstituteHistory
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2.
	 * @Method설명 : 통합기관이력 조회 조건 세팅
	 */
	@RequestMapping(value = "/onLoadInstituteHistory.do")
	public View onLoadInstituteHistory(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
		dataRequest.setResponse("dsInstTypeSeCode", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk())); // 기관유형 구분
		dataRequest.setResponse("dsAddrArea", srchAddrService.selectAddrArea()); // 지역구분
		// 단위업무구분 추가
		dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk())); // 단위업무구분

		return new JSONDataView();
	}

	/**
	 * @Method명   : onLoadInstituteHistoryDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2.
	 * @Method설명 : 통합기관이력 상세 팝업
	 */
	@RequestMapping(value = "/onLoadInstituteHistoryDetail.do")
	public View onLoadInstituteHistoryDetail(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력)구분코드
		dataRequest.setResponse("dsInstTypeSeCode", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk())); // 기관유형구분코드
		dataRequest.setResponse("dsAddrArea", srchAddrService.selectAddrArea()); // 지역구분코드
		//dataRequest.setResponse("dsFcltyInstSeCode", mgmtCmmnCodeService.selectCommonCodeUnit("FCLTY_INST_SE_CD")); // 시설기관구분코드
		//dataRequest.setResponse("dsSoctyWlfarFcltySeCode", mgmtCmmnCodeService.selectCommonCodeUnit("SOCTY_WLFAR_FCLTY_SE_CD")); // 사회복지기관구분코드
		//dataRequest.setResponse("dsSrvcCnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_CN_SE_CD")); // 서비스내용구분코드
		dataRequest.setResponse("dsMainEnfcInstSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("MAIN_ENFC_INST_SE_CD", userVo.getUntTaskwk())); // 주요시행기관구분코드
		//dataRequest.setResponse("dsRprsOccpSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RPRS_OCCP_SE_CD")); // 대표직업구분코드
		//dataRequest.setResponse("dsOrganization", historyService.selectOrg()); // 상위기관
		dataRequest.setResponse("dsSido", srchAddrService.selectSido());  // 시도 코드
		dataRequest.setResponse("dsSgg", srchAddrService.selectSgg());  // 시군구 코드

		return new JSONDataView();
	}

	/**
	 * @Method명   : selectInstituteHistory
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2.
	 * @Method설명 : 통합기관이력 조회
	 */
	@RequestMapping(value = "/selectInstituteHistory.do")
	public View selectInstituteHistory(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, Object> dmSearchMap = new HashMap<>();
		dmSearchMap.put("INST_NM", dmSearch.getValue("INST_NM"));
		dmSearchMap.put("INST_TYPE_SE_CD", dmSearch.getValue("INST_TYPE_SE_CD"));
		dmSearchMap.put("RGN_CD", dmSearch.getValue("RGN_CD"));
		dmSearchMap.put("RPRSV_NM_ENCPT", dmSearch.getValue("RPRSV_NM_ENCPT"));
		dmSearchMap.put("DATAA_CHG_SE_CD", dmSearch.getValue("DATAA_CHG_SE_CD"));
		dmSearchMap.put("START_DATE", dmSearch.getValue("START_DATE"));
		dmSearchMap.put("END_DATE", dmSearch.getValue("END_DATE"));
		dmSearchMap.put("UNT_TASKWK_SE_CD", dmSearch.getValue("UNT_TASKWK_SE_CD"));
		dmSearchMap.put("WORKER", dmSearch.getValue("WORKER"));

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = historyService.selectInstituteHistoryTotalCount(dmSearchMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = historyService.selectInstituteHistory(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsList", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		return new JSONDataView();
	}

	@RequestMapping(value = {"/onLoadRightsHistory.do", "/selectRightsHistory.do"})
	public View rightsHistory(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//ScpDb scpDb = new ScpDb();

		Map<String, Object> dmSearchMap = new HashMap<>(dmSearch.getSingleValueMap());

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = historyService.selectRightsHistoryCount(dmSearchMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		dmSearchMap.put("OFFSET_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = historyService.selectRightsHistory(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);


		dataRequest.setResponse("dmPage", resPage);

		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		log.debug("#### requestUrl : " + requestUrl);

		if (requestUrl.endsWith("/onLoadRightsHistory.do")) {
			dataRequest.setResponse("dsInstituteType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk()));
			dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
			dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk())); // 단위업무구분
		}

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadRightsHistoryDetail.do")
	public View onLoadRightsHistoryDetail(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력)구분코드
		dataRequest.setResponse("dsInstTypeSeCode", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk())); // 기관유형구분코드

		return new JSONDataView();
	}


	@RequestMapping(value = {"/onLoadRightsHistoryGroup.do", "/selectRightsHistoryGroup.do"})
	public View rightsHistoryGroup(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//ScpDb scpDb = new ScpDb();

		Map<String, Object> dmSearchMap = new HashMap<>(dmSearch.getSingleValueMap());

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = historyService.selectRightsHistoryGroupCount(dmSearchMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		dmSearchMap.put("OFFSET_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = historyService.selectRightsHistoryGroup(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);


		dataRequest.setResponse("dmPage", resPage);

		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		log.debug("#### requestUrl : " + requestUrl);

		if (requestUrl.endsWith("/onLoadRightsHistoryGroup.do")) {
			UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
			dataRequest.setResponse("dsInstituteType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk()));
			dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
			// 단위업무구분 추가
			dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk())); // 단위업무구분
		}

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadRightsHistoryGroupDetail.do")
	public View onLoadRightsHistoryGroupDetail(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력)구분코드
		dataRequest.setResponse("dsInstTypeSeCode", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk())); // 기관유형구분코드

		return new JSONDataView();
	}


	@RequestMapping(value = {"/onLoadRightsHistoryUserGroup.do", "/selectRightsHistoryUserGroup.do"})
	public View rightsHistoryUserGroup(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//ScpDb scpDb = new ScpDb();

		Map<String, Object> dmSearchMap = new HashMap<>(dmSearch.getSingleValueMap());

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = 0;

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		dmSearchMap.put("OFFSET_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);


		dataRequest.setResponse("dmPage", resPage);

		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		log.debug("#### requestUrl : " + requestUrl);

		if (requestUrl.endsWith("/onLoadRightsHistoryUserGroup.do")) {
			UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
			dataRequest.setResponse("dsInstituteType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk()));
			dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
			// 단위업무구분 추가
			dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk()));
		}

		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadRightsHistoryUserGroupDetail.do")
	public View onLoadRightsHistoryUserGroupDetail(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력)구분코드
		dataRequest.setResponse("dsInstTypeSeCode", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk())); // 기관유형구분코드

		return new JSONDataView();
	}


	@RequestMapping(value = {"/onLoadRightsHistoryUserMenu.do", "/selectRightsHistoryUserMenu.do"})
	public View rightsHistoryUserMenu(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		//ScpDb scpDb = new ScpDb();

		Map<String, Object> dmSearchMap = new HashMap<>(dmSearch.getSingleValueMap());

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = historyService.selectRightsHistoryUserMenuCount(dmSearchMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		dmSearchMap.put("OFFSET_IDX", startIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = historyService.selectRightsHistoryUserMenu(dmSearchMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);


		dataRequest.setResponse("dmPage", resPage);

		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		log.debug("#### requestUrl : " + requestUrl);

		if (requestUrl.endsWith("/onLoadRightsHistoryUserMenu.do")) {
			UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
			dataRequest.setResponse("dsInstituteType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk()));
			dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력) 구분
			// 단위업무구분 추가
			dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk())); // 단위업무구분
		}

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadRightsHistoryUserMenuDetail.do")
	public View onLoadRightsHistoryUserMenuDetail(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsDataChgCode", mgmtCmmnCodeService.selectCommonCodeUnit("DATAA_CHG_SE_CD", userVo.getUntTaskwk())); // 데이터변경(이력)구분코드
		dataRequest.setResponse("dsInstTypeSeCode", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk())); // 기관유형구분코드

		return new JSONDataView();
	}


}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.web;

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

import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userauth.service.InqMenuAuthService;
import isry.itgcms.sysmgmt.userauth.service.InqOrgListService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userjoin.service.SrchAddrService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : InqOrgListController.java
 * @프로그램 설명 : 기관 조회
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 1. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 1.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
//@Api(value = "InqOrgList web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class InqOrgListController extends IsryBaseController {

	private final Logger LOGGER = LoggerFactory.getLogger(InqOrgListController.class);
	
	@Resource(name = "inqOrgListService")
	private InqOrgListService inqOrgListService;

	@Resource(name="srchAddrService")
    private SrchAddrService srchAddrService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@Resource(name = "inqMenuAuthService")
	private InqMenuAuthService inqMenuAuthService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;	
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	//@ApiOperation(value = "/selectOrg.do", notes = "기관 정보 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectOrg.do")
	public View selectOrg(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String UntTaskwk = "";
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}
		dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		dataRequest.setResponse("dsSido", srchAddrService.selectSido());
		dataRequest.setResponse("dsSigungu", srchAddrService.selectSgg());
		
		dataRequest.setResponse("dsOrgType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", UntTaskwk));
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", UntTaskwk));
		
		dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();

	}
	
	/*
	 * 기관 검색
	 * */
	@RequestMapping(value = "/selectOrgPaging.do")
	public View selectOrgPaging(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String UntTaskwk = "";
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}
		
		String six = param.getValue("six");
		
		Integer orgType = null;
		String unitSystem = null;
		String unitTaskWork = null;
		String orgName = null;
		String engCtpvNm = null;
		String rgnCd = null;
		String linkYn = null;
		String linkTypeSeCd = null;
		
		if (param != null) {
			if (param.getValue("orgType") != null && !"".equals(param.getValue("orgType"))) {
				orgType = Integer.valueOf(param.getValue("orgType"));
			}
			if (param.getValue("unitSystem") != null && !"".equals(param.getValue("unitSystem"))) {
				unitSystem = param.getValue("unitSystem");
			}
			if (param.getValue("unitTaskWork") != null && !"".equals(param.getValue("unitTaskWork"))) {
				unitTaskWork = param.getValue("unitTaskWork");
			}
			if (param.getValue("orgName") != null && !"".equals(param.getValue("orgName"))) {
				orgName = param.getValue("orgName");
			}
			if (param.getValue("engCtpvNm") != null && !"".equals(param.getValue("engCtpvNm"))) {
				engCtpvNm = param.getValue("engCtpvNm");
			}
			if (param.getValue("rgnCd") != null && !"".equals(param.getValue("rgnCd"))) {
				rgnCd = param.getValue("rgnCd");
			}
			if (param.getValue("LINK_YN") != null && !"".equals(param.getValue("LINK_YN"))) {
				linkYn = param.getValue("LINK_YN");
			}
			if (param.getValue("LINK_TYPE_SE_CD") != null && !"".equals(param.getValue("LINK_TYPE_SE_CD"))) {
				linkTypeSeCd = param.getValue("LINK_TYPE_SE_CD");
			}
		}
		
		Map<String, Object> paramMap = new HashMap<>();
		
		paramMap.put("ORG_TYPE", orgType);
		paramMap.put("UNIT_SYSTEM", unitSystem);
		paramMap.put("UNIT_TASKWORK", unitTaskWork);
		paramMap.put("ORG_NAME", orgName);
		paramMap.put("ENG_CTPV_NM", engCtpvNm);
		paramMap.put("RGN_CD", rgnCd);
		paramMap.put("SIX", six);
		paramMap.put("LINK_YN", linkYn);
		paramMap.put("LINK_TYPE_SE_CD", linkTypeSeCd);
		
		String authAppId = dataRequest.getParameter("_AUTH_APP_ID");
		
		if ("app/itgcms/sysmgmt/02_institute/OrganizationManage.clx".equals(authAppId)) {
			paramMap.put("DEL_YN", "Y");
		} else {
			paramMap.put("DEL_YN", "N");
		}
		
		/* [문서수발신] 수신기관 검색팝업 일감-결함관리#434*/
		if("app/itgcms/wrksupt/docsr/DocDsptchList.clx".equals(authAppId)) {
			paramMap.put("LINK_YN", "Y");
		}
		
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, userVo.getUntTaskwk());
		
		Map<String, Object> paramMap2 = new HashMap<>();
		
		paramMap.forEach((StrKey, StrValue) ->{ paramMap2.put(StrKey, StrValue); }); /* 형변환*/
		
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", userVo.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", userVo.getGroupAuthrtSeCd());
		System.out.println("paramMap2 = " + paramMap2);

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = inqOrgListService.selectOrgCount(paramMap2);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		paramMap2.put("START_IDX", startIndex);
		paramMap2.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = inqOrgListService.selectOrgPaging(paramMap2);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsOrganization", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		//dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		dataRequest.setResponse("dsSido", srchAddrService.selectSido());
		dataRequest.setResponse("dsSigungu", srchAddrService.selectSgg());
		
		dataRequest.setResponse("dsOrgType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", UntTaskwk));
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", UntTaskwk));
		
		dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();

	}
	
	@RequestMapping(value = "/onloadOrg.do")
	public View onloadOrg(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String UntTaskwk = "";
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}
		//dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		dataRequest.setResponse("dsSido", srchAddrService.selectSido());
		dataRequest.setResponse("dsSigungu", srchAddrService.selectSgg());
		
		dataRequest.setResponse("dsOrgType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", UntTaskwk));
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", UntTaskwk));
		dataRequest.setResponse("dsAprvSttsSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("APRV_STTS_SE_CD", UntTaskwk));
		dataRequest.setResponse("dsFcltyInstSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("FCLTY_INST_SE_CD", UntTaskwk));
		dataRequest.setResponse("dsRsfrMbvSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RSFR_MBY_SE_CD", UntTaskwk));
		
		dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();

	}

	@RequestMapping(value = "/selectOrgInit.do")
	public View selectOrgInit(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String UntTaskwk = "";
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}
		//dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		dataRequest.setResponse("dsSido", srchAddrService.selectSido());
		dataRequest.setResponse("dsSigungu", srchAddrService.selectSgg());
		
		dataRequest.setResponse("dsOrgType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", UntTaskwk));
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", UntTaskwk));
		
		dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();

	}

	//@ApiOperation(value = "/selectOrgDtl.do", notes = "기관 상세 정보 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectOrgDtl.do")
	public View selectOrgDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String UntTaskwk = "";
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}

		dataRequest.setResponse("dmOrgDetail", inqOrgListService.selectOrgDetail(dataRequest));
		
		dataRequest.setResponse("dmRestArea", inqOrgListService.selectOrgRestArea(dataRequest));

		dataRequest.setResponse("dsOrgDetailHistory", inqOrgListService.selectOrgDetailHistory(dataRequest));
		
		//dataRequest.setResponse("dsInstituteType", inqOrgListService.selectInstituteType());
		dataRequest.setResponse("dsOrgType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", UntTaskwk));
		
		//dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		
		dataRequest.setResponse("dsAddrArea", srchAddrService.selectAddrArea());
		dataRequest.setResponse("dsSido", srchAddrService.selectSido());
		dataRequest.setResponse("dsSgg", srchAddrService.selectSgg());
		
		dataRequest.setResponse("dsSrvcCnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_CN_SE_CD", UntTaskwk));  // 기관 서비스내용구분코드
		dataRequest.setResponse("dsMainEnfcInstSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("MAIN_ENFC_INST_SE_CD", UntTaskwk));  // 기관 주요시행기관구분코드
		dataRequest.setResponse("dsFcltyInstSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("FCLTY_INST_SE_CD", UntTaskwk));  // 기관 시설기관구분코드
		dataRequest.setResponse("dsSoctyWlfarFcltySeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SOCTY_WLFAR_FCLTY_SE_CD", UntTaskwk));  // 기관 사회복지시설구분코드
		dataRequest.setResponse("dsRprsOccpSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RPRS_OCCP_SE_CD", UntTaskwk));  // 기관 대표직업구분코드
		dataRequest.setResponse("dsAprvSttsSeCd", mgmtCmmnCodeService.selectCommonCode("APRV_STTS_SE_CD"));  // 승인상태 구분코드
		
		dataRequest.setResponse("dsUnitSystemList", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", UntTaskwk));  // 단위 업무 시스템 목록
		
		dataRequest.setResponse("dsHanteoMemberYN", mgmtCmmnCodeService.selectCommonCodeUnit("HANTEO_MEMBER_YN", UntTaskwk));  // 한터협 회원 여부
		dataRequest.setResponse("dsSxdcSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", UntTaskwk));  //성별
		dataRequest.setResponse("dsProtectionPeriod", mgmtCmmnCodeService.selectCommonCodeUnit("PROTECTION_PERIOD", UntTaskwk));  // 보호 기간
		
		return new JSONDataView();

	}

	//@ApiOperation(value = "/selectInstituteType.do", notes = "기관 유형 정보 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectInstituteType.do")
	public View selectInstituteType(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String UntTaskwk = "";
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}
		//dataRequest.setResponse("dsInstituteType", inqOrgListService.selectInstituteType());
		dataRequest.setResponse("dsOrgType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", UntTaskwk));

		return new JSONDataView();

	}

	@RequestMapping(value = "/selectOrgName.do")
	public View selectOrgName(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrgName(dataRequest));
		
		return new JSONDataView();

	}
	
	/**
	 * 화면컨트롤 공통코드 조회
	 * @Method명   : selectScrinControCmmnsCd
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 8. 18. 
	 * @Method설명 : 화면컨트롤 공통코드 조회
	 */	
	@RequestMapping(value = "/selectScrinControCmmnsCd.do")
	public View selectScrinControCmmnsCd(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmScrinControCmmnsCd");
		Map<String,String> paramMap = paramGroup.getSingleValueMap();
		String sUnitCode = paramMap.get("sUnitCode");// 단위업무구분코드
		LOGGER.debug("paramMap ===>>>> " + paramMap.toString());
		LOGGER.debug("단위업무구분코드 ===>>>> " + sUnitCode);
		
		dataRequest.setResponse("dsFcltyTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("FCLTY_TYPE_SE_CD" , sUnitCode));  // 시설유형구분코드
		dataRequest.setResponse("dsPrtctnYngbgsTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("PRTCTN_YNGBGS_TYPE_SE_CD", sUnitCode));  // 보호청소년유형구분코드
		dataRequest.setResponse("dsCnterTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("CNTER_TYPE_SE_CD", sUnitCode));  // 센터유형구분코드
		dataRequest.setResponse("dsInstlOperMthdSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("INSTL_OPER_MTHD_SE_CD", sUnitCode));  // 설치운영방법구분코드
		dataRequest.setResponse("dsOperMthdSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("OPER_MTHD_SE_CD", sUnitCode));  // 운영방법구분코드
		dataRequest.setResponse("dsOPerInstTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("OPER_INST_TYPE_SE_CD", sUnitCode));  // 운영기관유형구분코드
		dataRequest.setResponse("dsDsgnMthdSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("DSGN_MTHD_SE_CD", sUnitCode));  // 지정방법구분코드
		dataRequest.setResponse("dsDaofthSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("DAOFTH_SE_CD", sUnitCode));  // 요일구분코드
		dataRequest.setResponse("dsTpriRcvrWorkTmzonSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("TPRI_RCVR_WORK_TMZON_SE_CD", sUnitCode));  // 1차수신자근무시간대구분코드
		dataRequest.setResponse("dsTpriRcptnStaffSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("TPRI_RCPTN_STAFF_SE_CD", sUnitCode));  // 1차수신요원구분코드
		dataRequest.setResponse("dsOperHnfLclasSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("OPER_HNF_LCLAS_SE_CD", sUnitCode));  // 운영인력대분류구분코드
		dataRequest.setResponse("dsOperHnfSclasSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("OPER_HNF_SCLAS_SE_CD", sUnitCode));  // 운영인력소분류구분코드
		dataRequest.setResponse("dsBldgPosesnShapeLclasSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("BLDG_POSESN_SHAPE_LCLAS_SE_CD", sUnitCode));  // 건물소유형태대분류구분코드
		dataRequest.setResponse("dsBldgPosesnBhviorSclasSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("BLDG_POSESN_BHVIOR_SCLAS_SE_CD", sUnitCode));  // 건물소유행태소분류구분코드
		dataRequest.setResponse("dsInstlMvnShapeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("INSTL_MVN_SHAPE_SE_CD", sUnitCode));  // 설치입주형태구분코드
		dataRequest.setResponse("dsBldgMngRegstrMapuSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("BLDG_MNG_REGSTR_MAPU_SE_CD", sUnitCode));  // 건물관리대장주용도구분코드
		dataRequest.setResponse("dsSamensInofabYngbgsFcltySeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SAMENS_INOFAB_YNGBGS_FCLTY_SE_CD", sUnitCode));  // 청소년시설구분코드
		dataRequest.setResponse("dsUseSpceInfoSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("USE_SPCE_INFO_SE_CD", sUnitCode));  // 사용공간정보구분코드
		dataRequest.setResponse("dsCarInfoSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("CAR_INFO_SE_CD", sUnitCode));  // 차량정보구분코드
		dataRequest.setResponse("dsDscsnTlphonRcordgSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("DSCSN_TLPHON_RCORDG_SE_CD", sUnitCode));  // 상담전화녹음구분코드
		dataRequest.setResponse("dsTlphonCscOperSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("TLPHON_CSC_OPER_SE_CD", sUnitCode));  // 전화상담실운영구분코드
		dataRequest.setResponse("dsCmmnsLingoUseSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("CMMNS_LINGO_USE_SE_CD", sUnitCode));  // 공통링고사용구분코드
		dataRequest.setResponse("dsPrvuseSpceInstlLcSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("PRVUSE_SPCE_INSTL_LC_SE_CD", sUnitCode));  // 전용공간설치위치구분코드
		dataRequest.setResponse("dsPrvuseSpceSprtSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("PRVUSE_SPCE_SPRT_SE_CD", sUnitCode));  // 전용공간지원구분코드
		
		return new JSONDataView();
	}


	@RequestMapping(value = {"/onloadNewInstituteList.do", "/selectNewInstituteList.do"})
	public View selectNewInstituteList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String UntTaskwk = "";
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}
		dataRequest.setResponse("dsList", inqOrgListService.selectNewInstituteList(request, dataRequest));

		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		if (requestUrl.endsWith("/onloadNewInstituteList.do")) {
			//dataRequest.setResponse("dsAddrArea", srchAddrService.selectAddrArea());
			dataRequest.setResponse("dsSigungu", srchAddrService.selectSgg());
			dataRequest.setResponse("dsInstituteType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", UntTaskwk));
		}
		
		return new JSONDataView();

	}

	//자원제공서비스등록 전용(value = "/resrceSelectOrg.do", notes = "기관 정보 조회")
	@RequestMapping(value = "/resrceSelectOrg.do")
	public View resrceSelectOrg(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String UntTaskwk = "";
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}
		dataRequest.setResponse("dsOrganization", inqOrgListService.resrceSelectOrg(dataRequest));
	
		dataRequest.setResponse("dsOrgType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", UntTaskwk));
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", UntTaskwk));
		
		return new JSONDataView();

	}
	
	/*
	 * 권한별 기관 선택
	 * */
	@RequestMapping(value = "/selectOrgAuthryPaging.do")
	public View selectOrgAuthryPaging(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String UntTaskwk = "";
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}
		
		String six = param.getValue("six");
		
		Integer orgType = null;
		String unitSystem = null;
		String unitTaskWork = null;
		String orgName = null;
		String engCtpvNm = null;
		String rgnCd = null;
		String ctpvSeCd = null;
		String multiYN = null;
		String multiOrgType = null;
		
		if (param != null) {
			if (param.getValue("orgType") != null && !"".equals(param.getValue("orgType"))) {
				orgType = Integer.valueOf(param.getValue("orgType"));
			}
			if (param.getValue("unitSystem") != null && !"".equals(param.getValue("unitSystem"))) {
				unitSystem = param.getValue("unitSystem");
			}
			if (param.getValue("unitTaskWork") != null && !"".equals(param.getValue("unitTaskWork"))) {
				unitTaskWork = param.getValue("unitTaskWork");
			}
			if (param.getValue("orgName") != null && !"".equals(param.getValue("orgName"))) {
				orgName = param.getValue("orgName");
			}
			if (param.getValue("engCtpvNm") != null && !"".equals(param.getValue("engCtpvNm"))) {
				engCtpvNm = param.getValue("engCtpvNm");
			}
			if (param.getValue("rgnCd") != null && !"".equals(param.getValue("rgnCd"))) {
				rgnCd = param.getValue("rgnCd");
			}
			if (param.getValue("CTPV_SE_CD") != null && !"".equals(param.getValue("CTPV_SE_CD"))) {
				ctpvSeCd = param.getValue("CTPV_SE_CD");
			}
			if (param.getValue("MULTI_ORG_TYPE") != null && !"".equals(param.getValue("MULTI_ORG_TYPE"))) {
				multiOrgType = param.getValue("MULTI_ORG_TYPE");
			}
			if (param.getValue("MULTI_EXCN_YN") != null && !"".equals(param.getValue("MULTI_EXCN_YN"))) {
				multiYN = param.getValue("MULTI_EXCN_YN");
			}
		}
		
		Map<String, Object> paramMap = new HashMap<>();
		
		paramMap.put("ORG_TYPE", orgType);
		paramMap.put("UNIT_SYSTEM", unitSystem);
		paramMap.put("UNIT_TASKWORK", unitTaskWork);
		paramMap.put("ORG_NAME", orgName);
		paramMap.put("ENG_CTPV_NM", engCtpvNm);
		paramMap.put("RGN_CD", rgnCd);
		paramMap.put("CTPV_SE_CD", ctpvSeCd);
		paramMap.put("MULTI_ORG_TYPE", multiOrgType);
		paramMap.put("MULTI_EXCN_YN", multiYN);
		paramMap.put("SIX", six);		
		paramMap.put("DEL_YN", "N");
		/*20230126_강화영_권한 적용_시작*/
		paramMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());
		Map<String, String> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{
			paramMap2.put(StrKey, String.valueOf(StrValue));
		});		 
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, paramMap2);
		paramMap.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap.put("GROUP_AUTHRT_SE_CD", (userVo.getGroupAuthrtSeCd() == null || "".equals(userVo.getGroupAuthrtSeCd())?0:Integer.parseInt(userVo.getGroupAuthrtSeCd())));
		/*20230126_강화영_권한 적용_종료*/

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = inqOrgListService.selectOrgAuthryCount(paramMap);

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;

		// Map<String, Object> mapParam = new HashMap<String, Object>();
		paramMap.put("START_IDX", startIndex);
		paramMap.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = inqOrgListService.selectOrgAuthryPaging(paramMap);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsOrganization", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		//dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		dataRequest.setResponse("dsSido", srchAddrService.selectSido());
		dataRequest.setResponse("dsSigungu", srchAddrService.selectSgg());
		
		dataRequest.setResponse("dsOrgType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", UntTaskwk));
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", UntTaskwk));
		
		dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();

	}
	
	/*
	 * 권한별기관검색팝업 초기 데이터 로딩
	 * */
	@RequestMapping(value = "/selectOrgAuthryInit.do")
	public View selectOrgAuthryInit(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String UntTaskwk = "";
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}		
		dataRequest.setResponse("dsOrgType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", UntTaskwk));
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", UntTaskwk));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectOrgDtlHistory.do")
	public View selectOrgDtlHistory(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String UntTaskwk = "";
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}

		dataRequest.setResponse("dmOrgDetail", inqOrgListService.selectOrgDetailHistoryData(dataRequest));
		
		dataRequest.setResponse("dmRestArea", inqOrgListService.selectOrgRestArea(dataRequest));

		dataRequest.setResponse("dsOrgType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", UntTaskwk));
		
		//dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		
		dataRequest.setResponse("dsAddrArea", srchAddrService.selectAddrArea());
		dataRequest.setResponse("dsSido", srchAddrService.selectSido());
		dataRequest.setResponse("dsSgg", srchAddrService.selectSgg());
		dataRequest.setResponse("dsUnitSystemList", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", UntTaskwk));  // 단위 업무 시스템 목록
		dataRequest.setResponse("dsSxdcSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", UntTaskwk));  //성별
		
		/*dataRequest.setResponse("dsSrvcCnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_CN_SE_CD", UntTaskwk));  // 기관 서비스내용구분코드
		dataRequest.setResponse("dsMainEnfcInstSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("MAIN_ENFC_INST_SE_CD", UntTaskwk));  // 기관 주요시행기관구분코드
		dataRequest.setResponse("dsFcltyInstSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("FCLTY_INST_SE_CD", UntTaskwk));  // 기관 시설기관구분코드
		dataRequest.setResponse("dsSoctyWlfarFcltySeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SOCTY_WLFAR_FCLTY_SE_CD", UntTaskwk));  // 기관 사회복지시설구분코드
		dataRequest.setResponse("dsRprsOccpSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RPRS_OCCP_SE_CD", UntTaskwk));  // 기관 대표직업구분코드
		
		
		dataRequest.setResponse("dsHanteoMemberYN", mgmtCmmnCodeService.selectCommonCodeUnit("HANTEO_MEMBER_YN", UntTaskwk));  // 한터협 회원 여부
		dataRequest.setResponse("dsProtectionPeriod", mgmtCmmnCodeService.selectCommonCodeUnit("PROTECTION_PERIOD", UntTaskwk));*/  // 보호 기간
		
		return new JSONDataView();

	}
	
	@RequestMapping(value = "/selectOrgRenameOnload.do")
	public View selectOrgRenameOnload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String UntTaskwk = "";
		if(userVo != null) {
			UntTaskwk = userVo.getUntTaskwk();
		}
		
		dataRequest.setResponse("dsSido", srchAddrService.selectSido());
		dataRequest.setResponse("dsSgg", srchAddrService.selectSgg());
		dataRequest.setResponse("dsUnitSystemList", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", UntTaskwk));  // 단위 업무 시스템 목록
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectOrgRenameList.do")
	public View selectOrgRenameList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> map = inqOrgListService.selectOrgRenameDetail(request, dataRequest);
		
		dataRequest.setResponse("dmDetail", map.get("dmDetail"));
		dataRequest.setResponse("dsList", map.get("dsList"));
		
		return new JSONDataView();
	}
	
	/*
	 * 기존 선택된 기관 리스트 로딩
	 * */
	@RequestMapping(value = "/selectOrgList.do")
	public View selectOrgList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> list = inqOrgListService.selectOrgList(dataRequest);
		dataRequest.setResponse("dsAddress2", list);
		return new JSONDataView();
	}
}

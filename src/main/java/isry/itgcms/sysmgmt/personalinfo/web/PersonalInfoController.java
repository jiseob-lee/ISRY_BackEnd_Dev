package isry.itgcms.sysmgmt.personalinfo.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseController;
import isry.itgcms.syscmmn.sms.service.SmsService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.personalinfo.service.PersonalInfoService;
import isry.itgcms.sysmgmt.pgmemu.service.MgmtMenuService;
import isry.itgcms.sysmgmt.userauth.service.UserAuthAplyService;
//import isry.itgcms.sysmgmt.userauth.service.InqOrgListService;
import isry.itgcms.sysmgmt.userjoin.service.ReqUserJoinService;
import isry.itgcms.sysmgmt.userjoin.service.SrchAddrService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.RandomString;

@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/personalinfo")
public class PersonalInfoController extends IsryBaseController {
	
	@Resource(name = "personalInfoService")
	private PersonalInfoService personalInfoService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name = "mgmtMenuService")
	private MgmtMenuService mgmtMenuService;

	//@Resource(name = "inqOrgListService")
	//private InqOrgListService inqOrgListService;

	@Resource(name="srchAddrService")
    private SrchAddrService srchAddrService;

	@Resource(name = "reqUserJoinService")
	private ReqUserJoinService reqUserJoinService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "smsService")
	private SmsService smsService;
	
	@Resource(name = "userAuthAplyService")
	private UserAuthAplyService userAuthAplyService;
	
	@RequestMapping(value = "/prepareDownload.do")
	public View prepareDownload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmDownload");
		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
		String exportTitle = paramMap.get("exportTitle");
		
		String writeDownloadReason = paramMap.get("writeDownloadReason");
		
		String menuUrl = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		Integer menuNo = request.getParameter("_AUTH_MENU_NO") == null || "".equals(request.getParameter("_AUTH_MENU_NO"))
				? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));
		
		log.info("#### menuUrl : " + menuUrl);
		log.info("#### menuNo : " + menuNo);
		
		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
		
		String generatedString = "123456";
		
		//if ("pre".equals(profile) || "real2".equals(profile) || "real1".equals(profile)) {
		if ("real2".equals(profile) || "real1".equals(profile)) {
			generatedString = RandomString.generateNumeric(6);
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("downloadCount", 0);
		session.setAttribute("downloadReason", "");
		session.setAttribute("smsToken1", generatedString);
		session.setAttribute("menuNo", menuNo);
		session.setAttribute("menuUrl", menuUrl);

		if ("false".equals(writeDownloadReason)) {
			session.setAttribute("smsToken2", generatedString);
			session.setAttribute("downloadReason", "1");
		}
		
		//boolean isPersonalInfo = personalInfoService.isPersonalInfo(exportTitle, menuNo, menuUrl);
		//Integer registeredCount = personalInfoService.isExcelDownloadRegistered(menuNo, exportTitle, menuUrl);
		
		Map<String, String> map = new HashMap<>();
		map.put("exportTitle", exportTitle);
		//map.put("personalInfo", isPersonalInfo ? "1" : "0");
		//map.put("registeredDownload", registeredCount > 0 ? "1" : "0");
		
		map.put("smsToken2", "");


		
		//if (isPersonalInfo) {
		if ("true".equals(writeDownloadReason)) {
		//if ("pre".equals(profile) || "real2".equals(profile) || "real1".equals(profile)) {
		if ("real2".equals(profile) || "real1".equals(profile)) {
		
			UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
			String mblTelno = userVo.getMobile();
			mblTelno = mblTelno.replaceAll("[^\\d]", "");
	
			String contents = "[청소년 안전망 시스템] 인증 토큰은 " + generatedString + " 입니다.";
	
			Map<String, String> phoneMap = new HashMap<>();
			phoneMap.put("contents", contents);
			phoneMap.put("sender", "0516623229");
			ParameterGroup phoneParam = new ParameterGroup("dmParam", phoneMap);
			dataRequest.putParameterGroup(phoneParam);
			
			List<Map<String, String>> receiverList = new ArrayList<>();
			Map<String, String> receiverMap = new HashMap<>();
			receiverMap.put("MBL_TELNO", mblTelno);
			receiverList.add(receiverMap);
			ParameterGroup receiverParam = new ParameterGroup("dsReceiver2", receiverList);
			dataRequest.putParameterGroup(receiverParam);
			
			smsService.insertSMS(request, dataRequest);
		}
		}
		
		
		dataRequest.setResponse("dmDownload", map);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/excelDownload.do")
	public View excelDownload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		List<Map<String, Object>> list = personalInfoService.selectExcelDownload(request, dataRequest);
		
		dataRequest.setResponse("dsList", list);
		
		dataRequest.setResponse("dsPersonalInfo", mgmtCmmnCodeService.selectCommonCodeUnit("IS_PERSONAL_INFO", userVo.getUntTaskwk()));
		
		dataRequest.setResponse("dsRootMenuList", mgmtMenuService.selectRootMenu());
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/excelDownloadList.do")
	public View excelDownloadList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = personalInfoService.selectExcelDownloadListCount(dmSearchMap);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = totalCount;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
				
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		//Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("OFFSET_IDX", startIndex - 1);
		dmSearchMap.put("LAST_IDX", lastIndex);
		dmSearchMap.put("ROW_COUNT", rowSize);
		
		List<Map<String, Object>> list = personalInfoService.selectExcelDownloadList(dmSearchMap);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		dataRequest.setResponse("dsList", list);
		dataRequest.setResponse("dmPage", resPage);
		
		///////////////////////////////////////////////////
		
		//List<Map<String, Object>> list = personalInfoService.selectExcelDownloadList(request, dataRequest);
		
		//dataRequest.setResponse("dsList", list);
		
		dataRequest.setResponse("dsPersonalInfo", mgmtCmmnCodeService.selectCommonCodeUnit("IS_PERSONAL_INFO", userVo.getUntTaskwk()));
		
		dataRequest.setResponse("dsRootMenuList", mgmtMenuService.selectRootMenu());
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/onloadExcelDownloadReason.do")
	public View onloadExcelDownloadReason(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		dataRequest.setResponse("dsDownloadCs", mgmtCmmnCodeService.selectCommonCodeUnit("DWNLD_CS_SE_CD", userVo.getUntTaskwk()));
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/insertExcelDownload.do")
	public View insertExcelDownload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = personalInfoService.selectExcelDownload(request, dataRequest);
		
		dataRequest.setResponse("dsList", list);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectMenuNm.do")
	public View selectMenuNm(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsMenuUrl", personalInfoService.selectMenuNm(dataRequest));

		List<Map<String, Object>> list = personalInfoService.selectExcelDownload(request, dataRequest);
		
		dataRequest.setResponse("dsList", list);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/saveExcelDownload.do")
	public View saveExcelDownload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		personalInfoService.saveExcelDownload(request, dataRequest);

		List<Map<String, Object>> list = personalInfoService.selectExcelDownload(request, dataRequest);
		
		dataRequest.setResponse("dsList", list);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/saveExcelDownloadReason.do")
	public View saveExcelDownloadReason(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		boolean checkResult = personalInfoService.saveExcelDownloadReason(request, dataRequest);
		
		Map<String, String> map = new HashMap<>();
		map.put("checkResult", checkResult ? "1" : "0");
		
		dataRequest.setResponse("dmCheckResult", map);
		
		return new JSONDataView();
	}


	@RequestMapping(value = "/loadLongTermNotConnect.do")
	public View loadLongTermNotConnect(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		List<Map<String, Object>> list = personalInfoService.selectLongTermNotConnect(request, dataRequest);
		
		dataRequest.setResponse("dsList", list);
		
		// 장기간 미접속 기간 종류
		dataRequest.setResponse("dsTerm", mgmtCmmnCodeService.selectCommonCodeUnit("PRD_KND_SE_CD", userVo.getUntTaskwk()));
		
		// 장기간 미접속 구분 종류
		dataRequest.setResponse("dsType", mgmtCmmnCodeService.selectCommonCodeUnit("197", userVo.getUntTaskwk()));
		
		// 세션 시간, 비밀번호 실패 허용회수, 비밀번호 변경 알림주기 
		dataRequest.setResponse("dmValue", personalInfoService.selectSystemEnv());
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/saveSystemEnv.do")
	public View saveSystemEnv(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		personalInfoService.saveSystemEnv(request, dataRequest);
		
		return new JSONDataView();
	}
	

	@RequestMapping(value = "/onLoadReconfirmPassword1.do")
	public View onLoadReconfirmPassword1(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginMap.put("USER_ID", loginVO.getId());
			loginMap.put("USER_NM", loginVO.getUserName());
		}
		
		String personalInfoRights = (String)session.getAttribute("personalInfoRights");
		loginMap.put("PERSONAL_INFO_RIGHTS", personalInfoRights);
		
		dataRequest.setResponse("dmUserInfo", loginMap);

		return new JSONDataView();
	}
	
	@RequestMapping(value = "/onLoadReconfirmPassword.do")
	public View onLoadReconfirmPassword(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (userVo != null && userVo.getId() != null && !"".equals(userVo.getId())) {
			loginMap.put("USER_ID", userVo.getId());
			loginMap.put("USER_NM", userVo.getUserName());
		}
		
		String personalInfoRights = (String)session.getAttribute("personalInfoRights");
		loginMap.put("PERSONAL_INFO_RIGHTS", personalInfoRights);
		
		dataRequest.setResponse("dmUserInfo", loginMap);

		dataRequest.setResponse("dsEmailDomains", mgmtCmmnCodeService.selectCommonCodeUnit("EMAIL_DOMAINS", userVo.getUntTaskwk()));

		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));

		dataRequest.setResponse("dsQualifyClass", mgmtCmmnCodeService.selectCommonCodeUnit("QLFC_SE_CD", userVo.getUntTaskwk()));
		
		dataRequest.setResponse("dsSnsDivision", mgmtCmmnCodeService.selectCommonCodeUnit("SNS_SE_CD", userVo.getUntTaskwk()));  // 종사자 SNS 구분
		
		dataRequest.setResponse("dsAddrArea", srchAddrService.selectAddrArea());

		//dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk()));  // 단위 시스템
		dataRequest.setResponse("dsOrgBossYn", mgmtCmmnCodeService.selectCommonCodeUnit("ISTDR_YN", userVo.getUntTaskwk()));  // 기관장 여부
		dataRequest.setResponse("dsOrgRegion", reqUserJoinService.selectOrgRegion(dataRequest));
		
		dataRequest.setResponse("dsGroupAuthrtSeCd", mgmtCmmnCodeService.selectCommonCode("GROUP_AUTHRT_SE_CD"));  // 그룹권한구분코		
		
		// 자격 정보
		dataRequest.setResponse("dsOfapPrvateQlfcSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("OFAP_PRVATE_QLFC_SE_CD", userVo.getUntTaskwk()));  // 공인민간자격구분코드
		dataRequest.setResponse("dsCertiSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("CERTI_SE_CD", userVo.getUntTaskwk()));  // 자격증구분코드
		dataRequest.setResponse("dsQlfcGradSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("QLFC_GRAD_SE_CD", userVo.getUntTaskwk()));  // 자격증 등급
		
		// 학력 정보
		dataRequest.setResponse("dsLastAcbgSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("LAST_ACBG_SE_CD", userVo.getUntTaskwk()));  // 최종학력구분코드
		dataRequest.setResponse("dsGrdtnSttsSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("GRDTN_STTS_SE_CD", userVo.getUntTaskwk()));  // 졸업상태구분코드
		dataRequest.setResponse("dsSchlLctnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SCHL_LCTN_SE_CD", userVo.getUntTaskwk()));  // 학교소재지구분코드
		
		// 근무 이력
		dataRequest.setResponse("dsJbgdSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("JBGD_SE_CD", userVo.getUntTaskwk()));  // 직급구분코드
		dataRequest.setResponse("dsWorkHstrInfoYn", mgmtCmmnCodeService.selectCommonCodeUnit("WORK_HSTR_INFO_YN", userVo.getUntTaskwk()));  // 근무이력정보여부
		
		// 추가 정보
		dataRequest.setResponse("dsEnfsnNtnCertiSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("ENFSN_NTN_CERTI_SE_CD", userVo.getUntTaskwk()));  // 종사자국가자격증구분코드
		dataRequest.setResponse("dsSpcltyHnfTrnngEduSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SPCLTY_HNF_TRNNG_EDU_SE_CD", userVo.getUntTaskwk()));  // 전문인력양성교육구분코드
		dataRequest.setResponse("dsJbpsSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("JBPS_SE_CD", userVo.getUntTaskwk()));  // 직위구분코드
		dataRequest.setResponse("dsRtrmCsSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RTRM_CS_SE_CD", userVo.getUntTaskwk()));  // 퇴직사유구분코드
		dataRequest.setResponse("dsYngbgsCmpnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("YNGBGS_CMPN_SE_CD", userVo.getUntTaskwk()));  // 청소년동반자구분코드
		dataRequest.setResponse("dsMajorSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("EMPMN_INFO_MAJOR_SE_CD", userVo.getUntTaskwk()));  // 채용정보전공구분코드
		
		// 코드 사용 단위업무 조회
		dataRequest.setResponse("dsUseUnit", personalInfoService.selectCommonuUseUnit("JBPS_SE_CD"));
		
		// 회원 가입 상태 : 신청, 승인, 반려, 사용중지, 삭제
		dataRequest.setResponse("dsUserState", mgmtCmmnCodeService.selectCommonCodeUnit("USER_ID_USE_SE_CD", userVo.getUntTaskwk()));
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/checkReconfirmPassword.do")
	public View checkReconfirmPassword(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> message = new HashMap<String, Object>();
		
		boolean result = personalInfoService.checkReconfirmPassword(request, dataRequest);
		
		if (result) {
			message.put("uri", "app/itgcms/sysmgmt/MemberInfoModify");
		} else {
			message.put("msg", "비밀번호가 일치하지 않습니다.");
		}
		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/changeUserPassword.do")
	public View changeUserPassword(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> message = new HashMap<String, Object>();
		
		boolean result = personalInfoService.checkReconfirmPassword(request, dataRequest);
		
		if (result) {
			message.put("uri", "app/itgcms/sysmgmt/MemberInfoModify");
		} else {
			message.put("msg", "비밀번호가 일치하지 않습니다.");
		}
		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadPersonalInfo.do")
	public View onLoadPersonalInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (userVo != null && userVo.getId() != null && !"".equals(userVo.getId())) {
			loginMap.put("USER_ID", userVo.getId());
			loginMap.put("USER_NM", userVo.getUserName());
		}
		
		String personalInfoRights = (String)session.getAttribute("personalInfoRights");
		loginMap.put("PERSONAL_INFO_RIGHTS", personalInfoRights);
		
		dataRequest.setResponse("dmUserInfo", loginMap);

		dataRequest.setResponse("dsEmailDomains", mgmtCmmnCodeService.selectCommonCodeUnit("EMAIL_DOMAINS", userVo.getUntTaskwk()));  // 이메일 도메인

		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));  // 성별

		dataRequest.setResponse("dsMerriageYN", mgmtCmmnCodeService.selectCommonCodeUnit("MRG_YN_SE_CD", userVo.getUntTaskwk()));  // 결혼 여부

		dataRequest.setResponse("dsEducationLevel", mgmtCmmnCodeService.selectCommonCodeUnit("ACBG_SE_CD", userVo.getUntTaskwk()));  // 최종 학력
		
		dataRequest.setResponse("dsSnsDivision", mgmtCmmnCodeService.selectCommonCodeUnit("SNS_SE_CD", userVo.getUntTaskwk()));  // SNS 구분

		dataRequest.setResponse("dsSgg", srchAddrService.selectSgg());  // 시군구
		
		dataRequest.setResponse("dsUserType", mgmtCmmnCodeService.selectCommonCodeUnit("USER_TYPE", userVo.getUntTaskwk()));  // 사용자 유형
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectPersonalInfo.do")
	public View selectPersonalInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginMap.put("USER_ID", loginVO.getId());
			loginMap.put("USER_NM", loginVO.getUserName());
		}
		
		String personalInfoRights = (String)session.getAttribute("personalInfoRights");
		loginMap.put("PERSONAL_INFO_RIGHTS", personalInfoRights);
		
		dataRequest.setResponse("dmUserInfo", loginMap);

		//dataRequest.setResponse("dsEmailDomains", mgmtCmmnCodeService.selectCommonCodeUnit("EMAIL_DOMAINS"));  // 이메일 도메인
		//dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));  // 성별
		//dataRequest.setResponse("dsMerriageYN", mgmtCmmnCodeService.selectCommonCodeUnit("MRG_YN_SE_CD", userVo.getUntTaskwk()));  // 결혼 여부
		//dataRequest.setResponse("dsEducationLevel", mgmtCmmnCodeService.selectCommonCodeUnit("ACBG_SE_CD", userVo.getUntTaskwk()));  // 최종 학력

		if ("1".equals(personalInfoRights)) {
			dataRequest.setResponse("dsList", personalInfoService.selectPersonalInfo(request, dataRequest));  // 개인 정보 조회
		}
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/deletePersonalInfo.do")
	public View deletePersonalInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		
		String personalInfoRights = (String)session.getAttribute("personalInfoRights");

		if ("1".equals(personalInfoRights)) {
			dataRequest.setResponse("dmResult", personalInfoService.deletePersonalInfo(request, dataRequest));  // 개인 정보 삭제
		}
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/savePersonalInfo.do")
	public View savePersonalInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginMap.put("USER_ID", loginVO.getId());
			loginMap.put("USER_NM", loginVO.getUserName());
		}
		
		String personalInfoRights = (String)session.getAttribute("personalInfoRights");
		loginMap.put("PERSONAL_INFO_RIGHTS", personalInfoRights);
		
		dataRequest.setResponse("dmUserInfo", loginMap);

		//dataRequest.setResponse("dsEmailDomains", mgmtCmmnCodeService.selectCommonCodeUnit("EMAIL_DOMAINS"));  // 이메일 도메인
		//dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));  // 성별
		//dataRequest.setResponse("dsMerriageYN", mgmtCmmnCodeService.selectCommonCodeUnit("MRG_YN_SE_CD", userVo.getUntTaskwk()));  // 결혼 여부
		//dataRequest.setResponse("dsEducationLevel", mgmtCmmnCodeService.selectCommonCodeUnit("ACBG_SE_CD", userVo.getUntTaskwk()));  // 최종 학력

		personalInfoService.savePersonalInfo(request, dataRequest);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectWorkerInfo.do")
	public View selectWorkerInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		
		ParameterGroup dmEnfsnNo = dataRequest.getParameterGroup("dmEnfsnNo");
		String enfsnNo = dmEnfsnNo.getValue("enfsnNo");
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginMap.put("USER_ID", loginVO.getId());
			loginMap.put("USER_NM", loginVO.getUserName());
		}
		
		String personalInfoRights = (String)session.getAttribute("personalInfoRights");

		if (!"1".equals(personalInfoRights)) {
			return new JSONDataView();
		}
		
		Map<String, String> workerMap = null;
		
		if (enfsnNo == null || "".equals(enfsnNo)) {
			
			log.debug("#### selectWorkerInfo.do : 세션 로그인 아이디로 종사자 정보를 구함.");
			
			// 세션 로그인 아이디로 종사자 정보를 구함.
			workerMap = personalInfoService.selectWorkerInfo(loginMap);
		
		} else {
			
			if (!"0".equals(enfsnNo)) {
				
				log.debug("#### selectWorkerInfo.do : 넘겨진 종사자 번호에 해당하는 종사자 정보를 구함.");
				
				// 넘겨진 종사자 번호에 해당하는 종사자 정보를 구함.
				Map<String, String> noMap = new HashMap<>();
				noMap.put("ENFSN_NO", enfsnNo);
				workerMap = personalInfoService.selectWorkerInfoNo(noMap);
			}
		}
		
		//List<Map<String, String>> unitSystemList = personalInfoService.selectUnitSystemList(workerMap);
		
		if (workerMap != null) {
			
			dataRequest.setResponse("dmWorker", workerMap);
			//dataRequest.setResponse("dsWorkerUnitSystem", unitSystemList);
	
			
			// 학력 정보
			dataRequest.setResponse("dsEducation", personalInfoService.selectEducation(workerMap.get("ENFSN_NO")));
			
			if (workerMap.get("QLFC_INFO_MNG_NO") != null && !"".equals(workerMap.get("QLFC_INFO_MNG_NO"))) {
				// 자격 정보
				dataRequest.setResponse("dsQualification", personalInfoService.selectQualification(workerMap.get("QLFC_INFO_MNG_NO")));
			}
			
			// 근무 이력
			dataRequest.setResponse("dsWork", personalInfoService.selectWork(workerMap.get("ENFSN_NO")));
		
		}
		
		return new JSONDataView();
	}

	
	@RequestMapping(value = "/saveWorkerInfo.do")
	public View saveWorkerInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginMap.put("USER_ID", loginVO.getId());
			loginMap.put("USER_NM", loginVO.getUserName());
		}
		
		String personalInfoRights = (String)session.getAttribute("personalInfoRights");

		if (!"1".equals(personalInfoRights)) {
			return new JSONDataView();
		}
		
		Map<String, String> map = personalInfoService.saveWorkerInfo(request, dataRequest);
		String resultStr = map.get("resultStr");
		log.debug("#### resultStr 1 : " + map.get("resultStr"));		
		
		/* 2023-02-10 YOO.CHI.HOON 화면조회를 위해 userId, enfsnNo 전달위해 추가*/
		if(resultStr != null) {
			dataRequest.setResponse("dmMemberInfoCheck", map);
		}else {
			Map<String, Object> msgMap = new HashMap<>();
			msgMap.put("USER_ID"       , map.get("USER_ID"));
			msgMap.put("ENFSN_NO"	   , map.get("ENFSN_NO"));
			msgMap.put("INDV_IDNTFC_NO", map.get("INDV_IDNTFC_NO"));
			
			dataRequest.setMetadata(true, msgMap);
		}		
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/deleteWorkerInfo.do")
	public View deleteWorkerInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginMap.put("USER_ID", loginVO.getId());
			loginMap.put("USER_NM", loginVO.getUserName());
		}
		
		String personalInfoRights = (String)session.getAttribute("personalInfoRights");

		if (!"1".equals(personalInfoRights)) {
			return new JSONDataView();
		}
		
		dataRequest.setResponse("dmMemberInfoCheck", personalInfoService.deleteWorkerInfo(request, dataRequest));
		
		return new JSONDataView();
	}


	@RequestMapping(value = "/selectInstituteInfo.do")
	public View selectInstituteInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginMap.put("USER_ID", loginVO.getId());
			loginMap.put("USER_NM", loginVO.getUserName());
		}
		
		String personalInfoRights = (String)session.getAttribute("personalInfoRights");

		if (!"1".equals(personalInfoRights)) {
			return new JSONDataView();
		}
		
		Map<String, String> instituteMap = personalInfoService.selectInstituteInfo(loginMap);
		
		//List<Map<String, String>> unitSystemList = personalInfoService.selectUnitSystemList(workerMap);
		
		dataRequest.setResponse("dmOrgDetail", instituteMap);
		//dataRequest.setResponse("dsWorkerUnitSystem", unitSystemList);
		
		return new JSONDataView();
	}


	@RequestMapping(value = "/selectYouthInfo.do")
	public View selectYouthInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginMap.put("USER_ID", loginVO.getId());
			loginMap.put("USER_NM", loginVO.getUserName());
		}
		
		String personalInfoRights = (String)session.getAttribute("personalInfoRights");

		if (!"1".equals(personalInfoRights)) {
			return new JSONDataView();
		}
		
		Map<String, String> youthMap = personalInfoService.selectYouthInfo(loginMap);
		
		//List<Map<String, String>> unitSystemList = personalInfoService.selectUnitSystemList(workerMap);
		
		dataRequest.setResponse("dmYouth", youthMap);
		//dataRequest.setResponse("dsWorkerUnitSystem", unitSystemList);
		
		return new JSONDataView();
	}


	@RequestMapping(value = "/selectGuardianInfo.do")
	public View selectGuardianInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginMap.put("USER_ID", loginVO.getId());
			loginMap.put("USER_NM", loginVO.getUserName());
		}
		
		String personalInfoRights = (String)session.getAttribute("personalInfoRights");

		if (!"1".equals(personalInfoRights)) {
			return new JSONDataView();
		}
		
		Map<String, String> guardianMap = personalInfoService.selectGuardianInfo(loginMap);
		
		//List<Map<String, String>> unitSystemList = personalInfoService.selectUnitSystemList(workerMap);
		
		dataRequest.setResponse("dmGuardian", guardianMap);
		//dataRequest.setResponse("dsWorkerUnitSystem", unitSystemList);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadUserView.do")
	public View onLoadUserView(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		ParameterGroup userInfo = dataRequest.getParameterGroup("dmUserInfo");
		Map<String, String> paramMap = userInfo.getSingleValueMap();
		String userType = paramMap.get("USER_TYPE");
		String userId = paramMap.get("USER_ID");

		log.debug("#### userType : " + userType);
		log.debug("#### userId : " + userId);
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		Map<String, Object> loginMap = new HashMap<>();
		loginMap.put("USER_ID", "");
		
		if (userVo != null && userVo.getId() != null && !"".equals(userVo.getId())) {
			loginMap.put("USER_ID", userId);
			loginMap.put("USER_ID2", userVo.getId());
			loginMap.put("USER_NM", userVo.getUserName());
		}
				
		if ("WORKER".equals(userType)) {
			Map<String, String> workerMap = personalInfoService.selectWorkerInfo(loginMap);
			dataRequest.setResponse("dmWorker", workerMap);

			// 학력 정보
			dataRequest.setResponse("dsEducation", personalInfoService.selectEducation(workerMap.get("ENFSN_NO")));
			
			if (workerMap.get("QLFC_INFO_MNG_NO") != null && !"".equals(workerMap.get("QLFC_INFO_MNG_NO"))) {
				// 자격 정보
				dataRequest.setResponse("dsQualification", personalInfoService.selectQualification(workerMap.get("QLFC_INFO_MNG_NO")));
			}
			
			// 근무 이력
			dataRequest.setResponse("dsWork", personalInfoService.selectWork(workerMap.get("ENFSN_NO")));
			
		} else if ("INSTITUTE".equals(userType)) {
			Map<String, String> instituteMap = personalInfoService.selectInstituteInfo(loginMap);
			dataRequest.setResponse("dmOrgDetail", instituteMap);
			
		} else if ("YOUTH".equals(userType)) {
			Map<String, String> youthMap = personalInfoService.selectYouthInfo(loginMap);
			dataRequest.setResponse("dmMemberInfo", youthMap);
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, -14);
			Date result = cal.getTime();
			DateFormat df = new SimpleDateFormat("YYYYMMdd", Locale.KOREAN);
			
			Map<String, String> map = new HashMap<>();
			map.put("fullFourteen", df.format(result));
			
			//log.debug("#### fullFourteen : " + df.format(result));
			
			dataRequest.setResponse("dmFullFourteen", map);
			
		} else if ("GUARDIAN".equals(userType)) {
			Map<String, String> guardianMap = personalInfoService.selectGuardianInfo(loginMap);
			dataRequest.setResponse("dmMemberInfo", guardianMap);
		}

		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsAgreeYN", mgmtCmmnCodeService.selectCommonCodeUnit("AGREE_YN", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsQualifyClass", mgmtCmmnCodeService.selectCommonCodeUnit("QLFC_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsSnsDivision", mgmtCmmnCodeService.selectCommonCodeUnit("SNS_SE_CD", userVo.getUntTaskwk()));  // 종사자 SNS 구분
		dataRequest.setResponse("dsAddrArea", srchAddrService.selectAddrArea());
		//dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk()));  // 단위 시스템
		dataRequest.setResponse("dsOrgBossYn", mgmtCmmnCodeService.selectCommonCodeUnit("ISTDR_YN", userVo.getUntTaskwk()));  // 기관장 여부
		//dataRequest.setResponse("dsOrgRegion", reqUserJoinService.selectOrgRegion(dataRequest));
		dataRequest.setResponse("dsUserState", mgmtCmmnCodeService.selectCommonCodeUnit("USER_ID_USE_SE_CD", userVo.getUntTaskwk()));  // 회원 가입 상태 : 신청, 승인, 반려, 사용중지, 삭제
		dataRequest.setResponse("dsYN", mgmtCmmnCodeService.selectCommonCodeUnit("YES_OR_NO", userVo.getUntTaskwk()));  // 예/아니오 응답
		dataRequest.setResponse("dsSido", srchAddrService.selectSido());
		dataRequest.setResponse("dsSgg", srchAddrService.selectSgg());
		dataRequest.setResponse("dsMemberState", mgmtCmmnCodeService.selectCommonCodeUnit("USER_ID_USE_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsOrgType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk()));  // 기관 유형
		dataRequest.setResponse("dsMainAgency", mgmtCmmnCodeService.selectCommonCodeUnit("MAIN_ENFC_INST_SE_CD", userVo.getUntTaskwk()));  // 기관 주요시행기관구분코드
		
		// 담당업무 추가(권한)
		dataRequest.setResponse("dsGroupAuthrtSeCd", mgmtCmmnCodeService.selectCommonCode("GROUP_AUTHRT_SE_CD"));  // 그룹권한구분코드
		
		// 자격 정보
		dataRequest.setResponse("dsOfapPrvateQlfcSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("OFAP_PRVATE_QLFC_SE_CD", userVo.getUntTaskwk()));  // 공인민간자격구분코드
		dataRequest.setResponse("dsCertiSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("CERTI_SE_CD", userVo.getUntTaskwk()));  // 자격증구분코드
		dataRequest.setResponse("dsQlfcGradSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("QLFC_GRAD_SE_CD", userVo.getUntTaskwk()));  // 자격증 등급
		
		// 학력 정보
		dataRequest.setResponse("dsLastAcbgSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("LAST_ACBG_SE_CD", userVo.getUntTaskwk()));  // 최종학력구분코드
		dataRequest.setResponse("dsGrdtnSttsSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("GRDTN_STTS_SE_CD", userVo.getUntTaskwk()));  // 졸업상태구분코드
		dataRequest.setResponse("dsSchlLctnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SCHL_LCTN_SE_CD", userVo.getUntTaskwk()));  // 학교소재지구분코드
		
		// 근무 이력
		dataRequest.setResponse("dsJbgdSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("JBGD_SE_CD", userVo.getUntTaskwk()));  // 직급구분코드
		dataRequest.setResponse("dsWorkHstrInfoYn", mgmtCmmnCodeService.selectCommonCodeUnit("WORK_HSTR_INFO_YN", userVo.getUntTaskwk()));  // 근무이력정보여부
		
		// 추가 정보
		dataRequest.setResponse("dsEnfsnNtnCertiSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("ENFSN_NTN_CERTI_SE_CD", userVo.getUntTaskwk()));  // 종사자국가자격증구분코드
		dataRequest.setResponse("dsSpcltyHnfTrnngEduSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SPCLTY_HNF_TRNNG_EDU_SE_CD", userVo.getUntTaskwk()));  // 전문인력양성교육구분코드
		dataRequest.setResponse("dsJbpsSeCd2", mgmtCmmnCodeService.selectCommonCodeUnit("JBPS_SE_CD", userVo.getUntTaskwk()));  // 직위구분코드
		dataRequest.setResponse("dsRtrmCsSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RTRM_CS_SE_CD", userVo.getUntTaskwk()));  // 퇴직사유구분코드
		dataRequest.setResponse("dsYngbgsCmpnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("YNGBGS_CMPN_SE_CD", userVo.getUntTaskwk()));  // 청소년동반자구분코드
		dataRequest.setResponse("dsMajorSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("EMPMN_INFO_MAJOR_SE_CD", userVo.getUntTaskwk()));  // 채용정보전공구분코드
		
		// 청소년 보호자 추가 정보
		dataRequest.setResponse("dsTrprBcrnTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("TRPR_BCRN_TYPE_SE_CD", userVo.getUntTaskwk()));  // 대상자배경유형구분코드
		dataRequest.setResponse("dsBrthNtnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("BRTH_NTN_SE_CD", userVo.getUntTaskwk()));  // 출생국가구분코드
		dataRequest.setResponse("dsNltyNtnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("NLTY_NTN_SE_CD", userVo.getUntTaskwk()));  // 국적국가구분코드
		dataRequest.setResponse("dsGrowthNtnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("GROWTH_NTN_SE_CD", userVo.getUntTaskwk()));  // 성장국가구분코드
		dataRequest.setResponse("dsVisaTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("VISA_TYPE_SE_CD", userVo.getUntTaskwk()));  // 비자유형구분코드
		dataRequest.setResponse("dsRelgnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RELGN_SE_CD", userVo.getUntTaskwk()));  // 종교구분코드
		
		return new JSONDataView();
	}


	@RequestMapping(value = "/onLoadUserViewNo.do")
	public View onLoadUserViewNo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		ParameterGroup userInfo = dataRequest.getParameterGroup("dmUserInfo");
		Map<String, String> paramMap = userInfo.getSingleValueMap();
		String userType = paramMap.get("USER_TYPE");
		String userNo = paramMap.get("USER_NO");

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		Map<String, String> noMap = new HashMap<>();
		
		if (userVo != null && userVo.getId() != null && !"".equals(userVo.getId())) {
			noMap.put("USER_ID2", userVo.getId());
			noMap.put("USER_NM", userVo.getUserName());
		}
				
		if ("WORKER".equals(userType)) {
			noMap.put("ENFSN_NO", userNo);
			Map<String, String> workerMap = personalInfoService.selectWorkerInfoNo(noMap);
			dataRequest.setResponse("dmWorker", workerMap);

			// 학력 정보
			dataRequest.setResponse("dsEducation", personalInfoService.selectEducation(workerMap.get("ENFSN_NO")));
			
			if (workerMap.get("QLFC_INFO_MNG_NO") != null && !"".equals(workerMap.get("QLFC_INFO_MNG_NO"))) {
				// 자격 정보
				dataRequest.setResponse("dsQualification", personalInfoService.selectQualification(workerMap.get("QLFC_INFO_MNG_NO")));
			}
			
			// 근무 이력
			dataRequest.setResponse("dsWork", personalInfoService.selectWork(workerMap.get("ENFSN_NO")));
			
		} else if ("INSTITUTE".equals(userType)) {
			noMap.put("INST_NO", userNo);
			Map<String, String> instituteMap = personalInfoService.selectInstituteInfoNo(noMap);
			dataRequest.setResponse("dmOrgDetail", instituteMap);
			
		} else if ("YOUTH".equals(userType)) {
			noMap.put("YNGBGS_PRTCR_NO", userNo);
			Map<String, String> youthMap = personalInfoService.selectYouthInfoNo(noMap);
			dataRequest.setResponse("dmMemberInfo", youthMap);
			
		} else if ("GUARDIAN".equals(userType)) {
			noMap.put("YNGBGS_PRTCR_NO", userNo);
			Map<String, String> guardianMap = personalInfoService.selectGuardianInfoNo(noMap);
			dataRequest.setResponse("dmMemberInfo", guardianMap);
		}

		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsAgreeYN", mgmtCmmnCodeService.selectCommonCodeUnit("AGREE_YN", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsQualifyClass", mgmtCmmnCodeService.selectCommonCodeUnit("QLFC_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsSnsDivision", mgmtCmmnCodeService.selectCommonCodeUnit("SNS_SE_CD", userVo.getUntTaskwk()));  // 종사자 SNS 구분
		dataRequest.setResponse("dsAddrArea", srchAddrService.selectAddrArea());
		//dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk()));  // 단위 시스템
		dataRequest.setResponse("dsOrgBossYn", mgmtCmmnCodeService.selectCommonCodeUnit("ISTDR_YN", userVo.getUntTaskwk()));  // 기관장 여부
		//dataRequest.setResponse("dsOrgRegion", reqUserJoinService.selectOrgRegion(dataRequest));
		dataRequest.setResponse("dsUserState", mgmtCmmnCodeService.selectCommonCodeUnit("USER_ID_USE_SE_CD", userVo.getUntTaskwk()));  // 회원 가입 상태 : 신청, 승인, 반려, 사용중지, 삭제
		dataRequest.setResponse("dsYN", mgmtCmmnCodeService.selectCommonCodeUnit("YES_OR_NO", userVo.getUntTaskwk()));  // 예/아니오 응답
		dataRequest.setResponse("dsSido", srchAddrService.selectSido());
		dataRequest.setResponse("dsSgg", srchAddrService.selectSgg());
		dataRequest.setResponse("dsMemberState", mgmtCmmnCodeService.selectCommonCodeUnit("USER_ID_USE_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsOrgType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk()));  // 기관 유형
		dataRequest.setResponse("dsMainAgency", mgmtCmmnCodeService.selectCommonCodeUnit("MAIN_ENFC_INST_SE_CD", userVo.getUntTaskwk()));  // 기관 주요시행기관구분코드
		
		// 자격 정보
		dataRequest.setResponse("dsOfapPrvateQlfcSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("OFAP_PRVATE_QLFC_SE_CD", userVo.getUntTaskwk()));  // 공인민간자격구분코드
		dataRequest.setResponse("dsCertiSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("CERTI_SE_CD", userVo.getUntTaskwk()));  // 자격증구분코드
		dataRequest.setResponse("dsQlfcGradSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("QLFC_GRAD_SE_CD", userVo.getUntTaskwk()));  // 자격증 등급
		
		// 학력 정보
		dataRequest.setResponse("dsLastAcbgSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("LAST_ACBG_SE_CD", userVo.getUntTaskwk()));  // 최종학력구분코드
		dataRequest.setResponse("dsGrdtnSttsSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("GRDTN_STTS_SE_CD", userVo.getUntTaskwk()));  // 졸업상태구분코드
		dataRequest.setResponse("dsSchlLctnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SCHL_LCTN_SE_CD", userVo.getUntTaskwk()));  // 학교소재지구분코드
		
		// 근무 이력
		dataRequest.setResponse("dsJbgdSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("JBGD_SE_CD", userVo.getUntTaskwk()));  // 직급구분코드
		dataRequest.setResponse("dsWorkHstrInfoYn", mgmtCmmnCodeService.selectCommonCodeUnit("WORK_HSTR_INFO_YN", userVo.getUntTaskwk()));  // 근무이력정보여부
		
		// 추가 정보
		dataRequest.setResponse("dsEnfsnNtnCertiSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("ENFSN_NTN_CERTI_SE_CD", userVo.getUntTaskwk()));  // 종사자국가자격증구분코드
		dataRequest.setResponse("dsSpcltyHnfTrnngEduSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("SPCLTY_HNF_TRNNG_EDU_SE_CD", userVo.getUntTaskwk()));  // 전문인력양성교육구분코드
		dataRequest.setResponse("dsJbpsSeCd2", mgmtCmmnCodeService.selectCommonCodeUnit("JBPS_SE_CD", userVo.getUntTaskwk()));  // 직위구분코드
		dataRequest.setResponse("dsRtrmCsSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RTRM_CS_SE_CD", userVo.getUntTaskwk()));  // 퇴직사유구분코드
		dataRequest.setResponse("dsYngbgsCmpnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("YNGBGS_CMPN_SE_CD", userVo.getUntTaskwk()));  // 청소년동반자구분코드
		dataRequest.setResponse("dsMajorSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("EMPMN_INFO_MAJOR_SE_CD", userVo.getUntTaskwk()));  // 채용정보전공구분코드
		return new JSONDataView();
	}

	// 자격정보관리번호 채번
	@RequestMapping(value = "/selectQualificationNo.do")
	public View selectQualificationNo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		Map<String, String> map = new HashMap<>();
		map.put("USER_ID", userId2 == null || "".equals(userId2) ? "nobody" : userId2);
		String qualificationNo = personalInfoService.selectQualificationNo(map);
		map.put("QLFC_INFO_MNG_NO", qualificationNo);
		dataRequest.setResponse("dmQualificationNo", map);  // 자격정보관리번호
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectEnfsnInfo.do")
	public View selectEnfsnInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		ParameterGroup userInfo = dataRequest.getParameterGroup("dmEnfsnNo");
		String enfsnNo = ""; // 종사자번호
		String userId = "";
		if(userInfo != null ) { // 종사자관리 > 상세팝업 추가정보
			Map<String, String> paramMap = userInfo.getSingleValueMap();
			enfsnNo = paramMap.get("enfsnNo");
			userId  = paramMap.get("userId");
		}
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		int instNo; // 기관 번호
		
		if (loginVO != null && loginVO.getEnfsnNo() != null && !"".equals(loginVO.getEnfsnNo())) {
			if("".equals(enfsnNo) || userInfo == null) {
				enfsnNo = loginVO.getEnfsnNo();
			}
			if("".equals(userId) || userInfo == null) {
				userId = loginVO.getId();
			}
			instNo = loginVO.getInstNo() == null ? 0 : loginVO.getInstNo();
			
			dataRequest.setResponse("dsCnterEnfsnInfo", personalInfoService.selectEnfsnInfo(userId));
			dataRequest.setResponse("dsCnterEnfsnCerti", personalInfoService.selectEnfsnCerti(enfsnNo));
			dataRequest.setResponse("dsCnterEnfsnTrnngEdu", personalInfoService.selectEnfsnTrnngEdu(enfsnNo));
			dataRequest.setResponse("dsCnterEnfsnYngbgsPrvateCerti", personalInfoService.selectEnfsnYngbgsPrvateCerti(enfsnNo));
			//dataRequest.setResponse("dsAplyInstList", userAuthAplyService.selectAplyInstList(request, dataRequest));
			dataRequest.setResponse("dsAplyInstList", personalInfoService.selectAplyInstList(userId));
			// 기관유형
			dataRequest.setResponse("dmInstType", personalInfoService.selectInstType(instNo));
		}
		return new JSONDataView();
	}

	
	@RequestMapping(value = "/saveWithdrawal.do")
	public View saveWithdrawal(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 회원 탈퇴 처리
		personalInfoService.saveWithdrawal(request);
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/processAuthrtReset.do")
	public View processAuthrtReset(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 권한 초기화 처리
		personalInfoService.processAuthrtReset(request, dataRequest);
		
		return new JSONDataView();
	}

}

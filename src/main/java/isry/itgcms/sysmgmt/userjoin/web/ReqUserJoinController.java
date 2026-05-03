/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userjoin.web;

import java.net.URLEncoder;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.cleopatra.XBConfig;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.cleopatra.spring.UIView;

import egovframework.com.cmm.service.EgovProperties;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userauth.service.InqOrgListService;
import isry.itgcms.sysmgmt.userjoin.service.ReqUserJoinService;
import isry.itgcms.sysmgmt.userjoin.service.SrchAddrService;

import com.dreamsecurity.crypt.MsgCrypto;

/**
 * @파일명        : ReqUserJoinController.java
 * @프로그램 설명 : 회원 가입
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 2. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 2.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
//@Api(value = "ReqUserJoin web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/userjoin")
public class ReqUserJoinController extends IsryBaseController {

	//@Resource(name = "inqOrgTypeListService")
	//private InqOrgTypeListService inqOrgTypeListService;

	@Resource(name = "reqUserJoinService")
	private ReqUserJoinService reqUserJoinService;

	@Resource(name = "inqOrgListService")
	private InqOrgListService inqOrgListService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="srchAddrService")
    private SrchAddrService srchAddrService;
	

	@RequestMapping("/memberJoin.do")
	public View memberJoin(HttpServletRequest request, HttpServletResponse resp, DataRequest dataRequest) throws Exception {
		
		List<String> pathList = XBConfig.getInstance().getDeployPath(); //eXbuilder6 deploy path
		
		String deployPath = pathList.get(0);
		
		String mainPageUrl = deployPath+"/";   //메인 페이지 URL
		
		LocalDateTime now = LocalDateTime.now();
		String currentTime = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
		String refreshParam = "?p=" + currentTime;
		
//		mainPageUrl += "app/itgcms/sysmgmt/05_user/MemberJoin7.clx";
		mainPageUrl += "app/itgcms/sysmgmt/05_user/MemberJoin8.clx";

		
		
		HttpSession session = request.getSession();
		
		String root = session.getServletContext().getRealPath("/");
		
		root += root.endsWith("/") || root.endsWith("\\") ? "" : "/";
		
		log.debug("#### root : " + root);
		
	    // 1. 거래요청번호  생성 
	    //   - 최대 40byte 이내 사용 가능, 중복되지 않은 유일한값으로 설정
	    //   - 예시 : 회원사 PREFIX + 날짜 + 랜덤6자리
	    // 1.1 날짜 생성
	    Calendar today = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss", Locale.KOREA);
	    String reqTime = sdf.format(today.getTime());
	    // 1.2 거래요청ID 유일한값 생성을 위한 랜덤 생성
	    SecureRandom rand = null;
	    String randomStr = "";
	    try {
	        rand = SecureRandom.getInstance("SHA1PRNG");
	        for(int i=0; i < 6; i++)  {
	            randomStr += rand.nextInt(10);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    // 1.3 거래요청번호  생성
	    String reqNum  = "MOK" + reqTime + randomStr;
	    
	    // 1.4 거래요청번호  세션 저장
	    //     - 응답결과에서 세션정보 확인 권고
	    session.setAttribute("sessionReqNum ", reqNum );
	    
	    // 2. 회원사 등록 정보 설정
	    String urlCode  = "01005";         // 회원사 등록 코드
	    String cpId     = "youthsafe";     // 회원사ID
	    String reqdate  = reqTime;         // 요청일시
	    
	    /*
	    # url code(서비스 코드)
	    - 01001 회원가입
	    - 01002 정보변경
	    - 01003 아이디 분실시 조회페이지
	    - 01004 패스워드 분실시 조회페이지
	    - 01005 본인확인용
	    - 01999 기타
	    */
	    
	    // 3. 거래요청정보 암호화
	    MsgCrypto mscr = new MsgCrypto();
	    // 3.1 거래요청정보 : 서비스코드/거래요청번호/요청일시
	    //     -  /'로 구분
	    String reqInfo = urlCode + "/" + reqNum + "/" + reqdate;
	    // 3.2 거래요청정보 암호화
	    
	    //     - 암호문자열 = msgEncrypt(암호화 시킬 값, 인증서 경로(개발  서버 경로));
	    //String encReqInfo = mscr.msgEncrypt(reqInfo,"/app/ISRY_BackEnd/MOK/cert/youthsafeCert.der");
	    
	    //     - 암호문자열 = msgEncrypt(암호화 시킬 값, 인증서 경로(운영 서버 경로));
	    //String encReqInfo = mscr.msgEncrypt(reqInfo, "/app/ISRY_BackEnd.war/WEB-INF/mok/cert/youthsafeCert.der");
		String encReqInfo = mscr.msgEncrypt(reqInfo, root + "WEB-INF/mok/cert/youthsafeCert.der");
	    
	    // 3.3 암호화된 거래요청정보 URL 인코딩
	    encReqInfo = URLEncoder.encode(encReqInfo, "UTF-8");
	    
	    // 4. 휴대폰본인확인 요청정보 
	    // 4.1 본인인증 결과수신 받을 회원사 URL 설정
	    String rtn_url = getBaseUrl(request) + "/MOK/mok_webauth_result2.jsp";      // 본인인증 결과수신 받을 URL
	    
	    log.debug("#### rtn_url : " + rtn_url);
	    
	    // 4.2 휴대폰본인확인 요청 URL 생성
	    //     - https://휴대폰본인확인 URL?cpid=<회원사ID>&rtn_url=<회원사결과수신URL>&req_info=<암호화된 거래요청정보1>
	    //     - 운영 휴대폰본인확인 URL : https://www.mobile-ok.com/popup/common/hscert.jsp
	    String request_url = "https://www.mobile-ok.com/popup/common/hscert.jsp?cpid=" + cpId +"&rtn_url=" + rtn_url +"&req_info=" + encReqInfo;      // 본인인증 요청 URL
	    
	    log.info("#### request_url : " + request_url);
	    
	    // 5. 휴대폰본인확인 요청
	    //    - function openDRMOKWindow 함수 실행


		Map<String, String> initParam = new HashMap<String, String>();

		initParam.put("request_url", request_url);  // 휴대폰 본인인증
		
		initParam.put("SERVER", System.getProperty("SERVER"));
		
		initParam.put("PROFILE", EgovProperties.getProperty("globals", "isry.globals.profile"));
	    
		return new UIView(mainPageUrl, initParam); 
	}

	public String getBaseUrl(HttpServletRequest request) {
		String scheme = request.getScheme() + "://";
		String serverName = request.getServerName();
		String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
		String contextPath = request.getContextPath();
		return scheme + serverName + serverPort + contextPath;
	}
	
	//@ApiOperation(value = "/onLoadUserJoin.do", notes = "회원 가입 [공통] 이지섭")
	@RequestMapping(value = "/onLoadUserJoin.do")
	public View onLoadUserJoin(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//log.debug("#### onLoadUserJoin start.");
		
		//ParameterGroup param = dataRequest.getParameterGroup("dmKeyword");
		//String search = param.getValue("keyword");
		
		//dataRequest.setResponse("dsSearchResult", search == null || "".equals(search) ? null : srchAddrService.selectAddr(search));
		
		dataRequest.setResponse("dsInstituteType", mgmtCmmnCodeService.selectCommonCode("INST_TYPE_SE_CD"));
		dataRequest.setResponse("dsDividingRoles", mgmtCmmnCodeService.selectCommonCode("ENFSN_ROLE_SE_CD"));
		//dataRequest.setResponse("dsQualifyClass", reqUserJoinService.selectQualifyClass());
		dataRequest.setResponse("dsQualifyClass", mgmtCmmnCodeService.selectCommonCode("QLFC_SE_CD"));

		//dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		
		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCode("SXDC_SE_CD"));
		dataRequest.setResponse("dsMarriedYN", mgmtCmmnCodeService.selectCommonCode("MRG_YN_SE_CD"));
		dataRequest.setResponse("dsFinalEducation", mgmtCmmnCodeService.selectCommonCode("ACBG_SE_CD"));
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCode("UNT_TASKWK_SE_CD"));
		dataRequest.setResponse("dsEmailDomains", mgmtCmmnCodeService.selectCommonCode("EMAIL_DOMAINS"));
		
		dataRequest.setResponse("dsOrgRegion", reqUserJoinService.selectOrgRegion(dataRequest));

		dataRequest.setResponse("dsAgreeYN", mgmtCmmnCodeService.selectCommonCode("AGREE_YN"));  // 동의 여부
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -14);
		Date result = cal.getTime();
		DateFormat df = new SimpleDateFormat("YYYYMMdd", Locale.KOREAN);
		
		Map<String, String> map = new HashMap<>();
		map.put("fullFourteen", df.format(result));
		
		//log.debug("#### fullFourteen : " + df.format(result));
		
		dataRequest.setResponse("dmFullFourteen", map);
		
		dataRequest.setResponse("dsYN", mgmtCmmnCodeService.selectCommonCode("YES_OR_NO"));  // 예/아니오 응답

		dataRequest.setResponse("dsOrgType", mgmtCmmnCodeService.selectCommonCode("INST_TYPE_SE_CD"));  // 기관 유형
		
		dataRequest.setResponse("dsAddrArea", srchAddrService.selectAddrArea());
		dataRequest.setResponse("dsSido", srchAddrService.selectSido());
		dataRequest.setResponse("dsSgg", srchAddrService.selectSgg());
		
		dataRequest.setResponse("dsServiceContents", mgmtCmmnCodeService.selectCommonCode("SRVC_CN_SE_CD"));  // 기관 서비스내용구분코드
		dataRequest.setResponse("dsMainAgency", mgmtCmmnCodeService.selectCommonCode("MAIN_ENFC_INST_SE_CD"));  // 기관 주요시행기관구분코드
		dataRequest.setResponse("dsFacilities", mgmtCmmnCodeService.selectCommonCode("FCLTY_INST_SE_CD"));  // 기관 시설기관구분코드
		dataRequest.setResponse("dsWelfare", mgmtCmmnCodeService.selectCommonCode("SOCTY_WLFAR_FCLTY_SE_CD"));  // 기관 사회복지시설구분코드
		
		dataRequest.setResponse("dsJobDivision", mgmtCmmnCodeService.selectCommonCode("RPRS_OCCP_SE_CD"));  // 기관 대표직업구분코드
		
		dataRequest.setResponse("dsSnsDivision", mgmtCmmnCodeService.selectCommonCode("SNS_SE_CD"));  // 종사자 SNS 구분
		
		dataRequest.setResponse("dsOrgBossYn", mgmtCmmnCodeService.selectCommonCode("ISTDR_YN"));  // 기관장 여부
		dataRequest.setResponse("dsJbgdSeCd", mgmtCmmnCodeService.selectCommonCode("JBGD_SE_CD"));  // 직급 구분
		
		dataRequest.setResponse("dsGroupAuthrtSeCd", mgmtCmmnCodeService.selectCommonCode("GROUP_AUTHRT_SE_CD"));  // 그룹권한구분코

		// 자격 정보
		dataRequest.setResponse("dsOfapPrvateQlfcSeCd", mgmtCmmnCodeService.selectCommonCode("OFAP_PRVATE_QLFC_SE_CD"));  // 공인민간자격구분코드
		dataRequest.setResponse("dsCertiSeCd", mgmtCmmnCodeService.selectCommonCode("CERTI_SE_CD"));  // 자격증구분코드
		dataRequest.setResponse("dsQlfcGradSeCd", mgmtCmmnCodeService.selectCommonCode("QLFC_GRAD_SE_CD"));  // 자격증 등급
		
		// 학력 정보
		dataRequest.setResponse("dsLastAcbgSeCd", mgmtCmmnCodeService.selectCommonCode("LAST_ACBG_SE_CD"));  // 최종학력구분코드
		dataRequest.setResponse("dsGrdtnSttsSeCd", mgmtCmmnCodeService.selectCommonCode("GRDTN_STTS_SE_CD"));  // 졸업상태구분코드
		dataRequest.setResponse("dsSchlLctnSeCd", mgmtCmmnCodeService.selectCommonCode("SCHL_LCTN_SE_CD"));  // 학교소재지구분코드
		
		// 근무 이력
		dataRequest.setResponse("dsJbgdSeCd", mgmtCmmnCodeService.selectCommonCode("JBGD_SE_CD"));  // 직급구분코드
		dataRequest.setResponse("dsWorkHstrInfoYn", mgmtCmmnCodeService.selectCommonCode("WORK_HSTR_INFO_YN"));  // 근무이력정보여부
		
		// 추가 정보
		dataRequest.setResponse("dsEnfsnNtnCertiSeCd", mgmtCmmnCodeService.selectCommonCode("ENFSN_NTN_CERTI_SE_CD"));  // 종사자국가자격증구분코드
		dataRequest.setResponse("dsSpcltyHnfTrnngEduSeCd", mgmtCmmnCodeService.selectCommonCode("SPCLTY_HNF_TRNNG_EDU_SE_CD"));  // 전문인력양성교육구분코드
		dataRequest.setResponse("dsRtrmCsSeCd", mgmtCmmnCodeService.selectCommonCode("RTRM_CS_SE_CD"));  // 퇴직사유구분코드
		dataRequest.setResponse("dsYngbgsCmpnSeCd", mgmtCmmnCodeService.selectCommonCode("YNGBGS_CMPN_SE_CD"));  // 청소년동반자구분코드
		dataRequest.setResponse("dsMajorSeCd", mgmtCmmnCodeService.selectCommonCode("EMPMN_INFO_MAJOR_SE_CD"));  // 채용정보전공구분코드
		dataRequest.setResponse("dsLastAcbgSeCd", mgmtCmmnCodeService.selectCommonCode("LAST_ACBG_SE_CD"));  // 최종학력구분코드
		
		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
		//log.debug("#### profile : " + profile);
		Map<String, String> profileMap = new HashMap<>();
		profileMap.put("PROFILE", profile);
		dataRequest.setResponse("dmProfile", profileMap);  // 서버 PROFILE
				
		return new JSONDataView();
	}

	@RequestMapping(value = "/onLoadUserUnitSystem.do")
	public View onLoadUserUnitSystem(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCode("UNT_SYS_SE_CD"));  // 단위 시스템
		dataRequest.setResponse("dsOrgBossYn", mgmtCmmnCodeService.selectCommonCode("ISTDR_YN"));  // 기관장 여부
		dataRequest.setResponse("dsOrgRegion", reqUserJoinService.selectOrgRegion(dataRequest));
		
		return new JSONDataView();
	}

	//@ApiOperation(value = "/checkIdDuplicate.do", notes = "아이디 중복 여부 확인 [공통] 이지섭")
	@RequestMapping(value = "/checkIdDuplicate.do")
	public View checkIdDuplicate(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dmCheckId", reqUserJoinService.checkIdDuplicate(dataRequest));
		
		return new JSONDataView();
	}
	
	//@ApiOperation(value = "/saveMember.do", notes = "아이디 중복 여부 확인 [공통] 이지섭")
	//@RequestMapping(value = "/saveMember.do")
	//public View saveMember(HttpServletRequest request, HttpServletResponse response, 
			//DataRequest dataRequest) throws Exception {
		
		//dataRequest.setResponse("dmMemberInfoCheck", reqUserJoinService.saveMember(request, dataRequest));
		
		//return new JSONDataView();
	//}

	@RequestMapping(value = "/saveWorker.do")
	public View saveWorker(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {

		ParameterGroup param1 = dataRequest.getParameterGroup("dmWorkerDetail");
		if (param1 != null) {
			Map<String, String> workerMap = param1.getSingleValueMap();
			String userId = workerMap.get("USER_ID");
			HttpSession session = request.getSession();
			session.setAttribute("userId", userId);
		}
		
		Map<String, String> map = reqUserJoinService.saveWorker(request, dataRequest);
		String resultStr = map.get("resultStr");
		log.debug("#### resultStr 2 : " + map.get("resultStr"));
//		dataRequest.setResponse("dmMemberInfoCheck", map);
		
		/* 2023-02-09 YOO.CHI.HOON 화면조회를 위해 userId, enfsnNo 전달위해 추가*/
		if(resultStr != null) {
			dataRequest.setResponse("dmMemberInfoCheck", map);
		}else {
			Map<String, Object> msgMap = new HashMap<>();
			msgMap.put("USER_ID"       , map.get("USER_ID"));
			msgMap.put("ENFSN_NO"	   , map.get("ENFSN_NO"));
			msgMap.put("INDV_IDNTFC_NO", map.get("INDV_IDNTFC_NO"));
			
			dataRequest.setMetadata(true, msgMap);
		}
		
		if (resultStr == null || "".equals(resultStr)) {
			reqUserJoinService.saveReconsent(request, dataRequest);
		}
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/saveInstitute.do")
	public View saveInstitute(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dmMemberInfoCheck", reqUserJoinService.saveInstitute(request, dataRequest));
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectOrgRegion.do")
	public View selectOrgRegion(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsOrgRegion", reqUserJoinService.selectOrgRegion(dataRequest));
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectUnitSystemOrganization.do")
	public View selectUnitSystemOrganization(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		
		String menuUrl = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		
		log.debug("#### menuUrl : " + menuUrl);
		
		//app/itgcms/sysmgmt/05_user/MemberListWorker.clx
		//app/itgcms/sysmgmt/05_user/MemberJoin8.clx
		
		if (menuUrl.equals("app/itgcms/sysmgmt/05_user/MemberListWorker.clx") 
				|| menuUrl.equals("app/itgcms/sysmgmt/05_user/MemberJoin8.clx")
				|| (param != null && "1".equals(param.getValue("isJoin")))) {
			//if (param != null && param.getValue("rgnCd") != null && !"".equals(param.getValue("rgnCd"))) {
			if (param != null && ((param.getValue("rgnCd") != null && !"".equals(param.getValue("rgnCd"))) || "UPDATE".equals(param.getValue("msCrudType")))) {
				dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
			} else {
				dataRequest.setResponse("dsOrganization", new ArrayList<HashMap<String, String>>());
			}
		} else {
			dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		}
		
		dataRequest.setResponse("dsOrgRegion", reqUserJoinService.selectOrgRegion(dataRequest));
		
		//ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		if (param != null && param.getValue("groupAuthrtSeCd") != null && !"".equals(param.getValue("groupAuthrtSeCd"))) {
			dataRequest.setResponse("dsAuthrtSeCd", reqUserJoinService.selectAuthSeCd(dataRequest));
		}
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectUnitSystemOrganization1.do")
	public View selectUnitSystemOrganization1(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		dataRequest.setResponse("dsOrgRegion", reqUserJoinService.selectOrgRegion(dataRequest));
		
		ParameterGroup param = dataRequest.getParameterGroup("dmListParam");
		if (param.getValue("groupAuthrtSeCd") != null && !"".equals(param.getValue("groupAuthrtSeCd"))) {
				dataRequest.setResponse("dsAuthrtSeCd", reqUserJoinService.selectAuthSeCd(dataRequest));
		}
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectSiGunGu.do")
	public View selectSiGunGu(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsList", reqUserJoinService.selectSiGunGu(dataRequest));
		return new JSONDataView();
	}

	// 개인정보 처리방침 재동의 처리
	@RequestMapping(value = "/saveReconsent.do")
	public View saveReconsent(HttpServletRequest request, HttpServletResponse response, 
			DataRequest dataRequest) throws Exception {
		reqUserJoinService.saveReconsent(request, dataRequest);
		return new JSONDataView();
	}

	// 휴대전화 인증 기등록 여부 체크
	@RequestMapping(value = "/checkPhoneDuplicate.do")
	public View checkPhoneDuplicate(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dmPhoneDuplicate", reqUserJoinService.checkPhoneDuplicate(request));
		return new JSONDataView();
	}

	// 공동인증서 인증 기등록 여부 체크
	@RequestMapping(value = "/checkCertificateDuplicate.do")
	public View checkCertificateDuplicate(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dmCertificateDuplicate", reqUserJoinService.checkCertificateDuplicate(request));
		return new JSONDataView();
	}

	// 휴대전화 인증 기등록 여부 체크
	@RequestMapping(value = "/checkSimpleDuplicate.do")
	public View checkSimpleDuplicate(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dmSimpleDuplicate", reqUserJoinService.checkSimpleDuplicate(request, dataRequest));
		return new JSONDataView();
	}

}

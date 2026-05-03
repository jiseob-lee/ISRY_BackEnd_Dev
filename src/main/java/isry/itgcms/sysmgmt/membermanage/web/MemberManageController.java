/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.membermanage.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.membermanage.service.MemberManageService;
import isry.itgcms.sysmgmt.personalinfo.service.PersonalInfoService;
import isry.itgcms.sysmgmt.userjoin.service.ReqUserJoinService;
import isry.itgcms.sysmgmt.userjoin.service.SrchAddrService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : MemberManageController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 4. 20. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 4. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/membermanage")
public class MemberManageController {

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name = "memberManageService")
	private MemberManageService memberManageService;

	@Resource(name = "personalInfoService")
	private PersonalInfoService personalInfoService;

	@Resource(name="srchAddrService")
    private SrchAddrService srchAddrService;

	//@Resource(name = "inqOrgListService")
	//private InqOrgListService inqOrgListService;

	@Resource(name = "reqUserJoinService")
	private ReqUserJoinService reqUserJoinService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value = "/onLoadMemberManage.do")
	public View onLoadMemberManage(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		return new JSONDataView();
	}
	
	@RequestMapping(value = "/onLoadWorkerManage.do")
	public View onLoadWorkerManage(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		String userNo = "";
		if (dmSearch != null) {
			userNo = dmSearch.getValue("ENFSN_NO");
		}
		
		if (userNo != null && !"".equals(userNo)) {
			Map<String, String> noMap = new HashMap<>();
			noMap.put("ENFSN_NO", userNo);
			Map<String, String> workerMap = personalInfoService.selectWorkerInfoNo(noMap);
			dataRequest.setResponse("dmWorker", workerMap);
		}
		

		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsAgreeYN", mgmtCmmnCodeService.selectCommonCodeUnit("AGREE_YN", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsQualifyClass", mgmtCmmnCodeService.selectCommonCodeUnit("QLFC_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsSnsDivision", mgmtCmmnCodeService.selectCommonCodeUnit("SNS_SE_CD", userVo.getUntTaskwk()));  // 종사자 SNS 구분
		dataRequest.setResponse("dsAddrArea", srchAddrService.selectAddrArea());
		//dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk()));  // 단위 시스템
		dataRequest.setResponse("dsOrgBossYn", mgmtCmmnCodeService.selectCommonCodeUnit("ISTDR_YN", userVo.getUntTaskwk()));  // 기관장 여부
		dataRequest.setResponse("dsOrgRegion", reqUserJoinService.selectOrgRegion(dataRequest));
		dataRequest.setResponse("dsJbgdSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("JBGD_SE_CD", userVo.getUntTaskwk()));  // 직급 구분
		dataRequest.setResponse("dsEmailDomains", mgmtCmmnCodeService.selectCommonCodeUnit("EMAIL_DOMAINS", userVo.getUntTaskwk()));  // 이메일 도메인
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectWorker.do")
	public View selectWorker(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		String personalInfoRights = (String)session.getAttribute("personalInfoRights");
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		if (!"1".equals(personalInfoRights)) {
			return new JSONDataView();
		}
		
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = memberManageService.selectWorkerCount(dmSearchMap);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.
		int totCnt  = totalCount;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
				
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		//Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("LAST_IDX", lastIndex);
		
		dataRequest.setResponse("dsList", memberManageService.selectWorker(dmSearchMap));

		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		dataRequest.setResponse("dmPage", resPage);

		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));  // 성별
		dataRequest.setResponse("dsUserState", mgmtCmmnCodeService.selectCommonCodeUnit("USER_ID_USE_SE_CD", userVo.getUntTaskwk()));  // 회원 가입 상태 : 신청, 승인, 반려, 사용중지, 삭제
		// 기관유형 및 단위업무구분코드 추가
		dataRequest.setResponse("dsInstTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getInstTypeSeCd())); // 기관유형
		dataRequest.setResponse("dsUntTaskwkSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk())); // 단위업무구분코드
		
		dataRequest.setResponse("dsGroupAuthrtSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("GROUP_AUTHRT_SE_CD", null)); // 그룹권한구분코드
		dataRequest.setResponse("dsAuthrtSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("AUTHRT_SE_CD", null)); // 권한구분코드
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/onloadSaveYouthGuardian.do")
	public View onloadSaveYouthGuardian(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -14);
		Date result = cal.getTime();
		DateFormat df = new SimpleDateFormat("YYYYMMdd", Locale.KOREAN);
		
		Map<String, String> map = new HashMap<>();
		map.put("fullFourteen", df.format(result));
		
		//log.debug("#### fullFourteen : " + df.format(result));
		
		dataRequest.setResponse("dmFullFourteen", map);
		
		dataRequest.setResponse("dsYN", mgmtCmmnCodeService.selectCommonCodeUnit("YES_OR_NO", userVo.getUntTaskwk()));  // 예/아니오 응답
		dataRequest.setResponse("dsEmailDomains", mgmtCmmnCodeService.selectCommonCodeUnit("EMAIL_DOMAINS", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsSido", srchAddrService.selectSido());
		dataRequest.setResponse("dsSgg", srchAddrService.selectSgg());
		dataRequest.setResponse("dsMemberState", mgmtCmmnCodeService.selectCommonCodeUnit("USER_ID_USE_SE_CD", userVo.getUntTaskwk()));
		
		dataRequest.setResponse("dsTrprBcrnTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("TRPR_BCRN_TYPE_SE_CD", userVo.getUntTaskwk()));  // 대상자배경유형구분코드
		dataRequest.setResponse("dsBrthNtnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("BRTH_NTN_SE_CD", userVo.getUntTaskwk()));  // 출생국가구분코드
		dataRequest.setResponse("dsNltyNtnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("NLTY_NTN_SE_CD", userVo.getUntTaskwk()));  // 국적국가구분코드
		dataRequest.setResponse("dsGrowthNtnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("GROWTH_NTN_SE_CD", userVo.getUntTaskwk()));  // 성장국가구분코드
		dataRequest.setResponse("dsVisaTypeSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("VISA_TYPE_SE_CD", userVo.getUntTaskwk()));  // 비자유형구분코드
		dataRequest.setResponse("dsRelgnSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("RELGN_SE_CD", userVo.getUntTaskwk()));  // 종교구분코드
		
		return new JSONDataView();
	}


	@RequestMapping(value = {"/onloadManageYouthGuardian.do", "/selectManageYouthGuardian.do"})
	public View onloadManageYouthGuardian(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		String personalInfoRights = (String)session.getAttribute("personalInfoRights");

		if (!"1".equals(personalInfoRights)) {
			return new JSONDataView();
		}
		
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> dmSearchMap = dmSearch == null ? new HashMap<>() : new HashMap<>(dmSearch.getSingleValueMap());
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		Integer totalCount = memberManageService.selectYouthGuardianCount(dmSearchMap);
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.
		int totCnt  = totalCount;
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
				
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx - 1) * rowSize + 1;
		int lastIndex = startIndex + rowSize - 1;
		
		//Map<String, Object> mapParam = new HashMap<String, Object>();
		dmSearchMap.put("START_IDX", startIndex);
		dmSearchMap.put("LAST_IDX", lastIndex);
		dmSearchMap.put("OFFSET_IDX", startIndex - 1);
		dmSearchMap.put("ROW_COUNT", rowSize);
		
		dataRequest.setResponse("dsList", memberManageService.selectYouthGuardian(dmSearchMap));

		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dmPage", resPage);

		String requestUrl = (String)request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		
		if (requestUrl.endsWith("/onloadManageYouthGuardian.do")) {	
			UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
			dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));  // 성별
			dataRequest.setResponse("dsSgg", srchAddrService.selectSgg());  // 시군구 코드
			dataRequest.setResponse("dsYngbgsPrtcrSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("YNGBGS_PRTCR_SE_CD", userVo.getUntTaskwk()));  // 청소년/보호자 구분 코드
			dataRequest.setResponse("dsUserState", mgmtCmmnCodeService.selectCommonCodeUnit("USER_ID_USE_SE_CD", userVo.getUntTaskwk()));  // 회원 가입 상태 : 신청, 승인, 반려, 사용중지, 삭제
		}
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/saveYouthGuardian.do")
	public View saveYouthGuardian(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		memberManageService.saveYouthGuardian(request, dataRequest);
		return new JSONDataView();
	}

	@RequestMapping(value = "/deleteYouthGuardian.do")
	public View deleteYouthGuardian(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

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
		
		dataRequest.setResponse("dmResult", memberManageService.deleteYouthGuardian(request, dataRequest));
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/saveInstitute.do")
	public View saveInstitute(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		memberManageService.saveInstitute(request, dataRequest);
		return new JSONDataView();
	}

	// 14세 미만 청소년 보호자 휴대폰 인증 SMS 인증 토큰 보내기
	@RequestMapping(value = "/saveYouthAuthSms.do")
	public View saveYouthAuthSms(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, String> map = memberManageService.saveYouthAuthSms(request, dataRequest);
		dataRequest.setResponse("dmAuthSms", map);  // 휴대폰 인증 SMS 인증 토큰
		return new JSONDataView();
	}

	// 14세 미만 청소년 보호자 휴대폰 인증 토큰 일치여부 확인
	@RequestMapping(value = "/selectYouthAuthSmsToken.do")
	public View selectYouthAuthSmsToken(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, String> map = memberManageService.selectYouthAuthSmsToken(request, dataRequest);
		dataRequest.setResponse("dmResult", map);  // 휴대폰 인증 결과
		return new JSONDataView();
	}

	// 14세 미만 청소년 정보 조회시 보호자 휴대폰 인증 했음을 세팅함.
	@RequestMapping(value = "/setGuardianPhoneAuth.do")
	public View setGuardianPhoneAuth(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		HttpSession session = request.getSession();
		
		session.setAttribute("YOUTH_GUARDIAN_AUTH", "1");
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmMemberInfo");
		Map<String, String> map = parameterGroup.getSingleValueMap();
		
		session.setAttribute("YOUTH_GUARDIAN_PHONE", map.get("STTY_AGT_CTTPC_TELNO"));
		
		return new JSONDataView();
	}

}

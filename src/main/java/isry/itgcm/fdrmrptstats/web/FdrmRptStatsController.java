/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2023 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcm.fdrmrptstats.web;

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

import isry.itgcm.fdrmrptstats.service.FdrmRptStatsService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : FdrmRptStatsController.java
 * @프로그램 설명 : 정기보고통계 컨트롤러 - -
 * @작성자 : Lee.SangHoon
 * @작성일 : 2023. 7. 27.
 * @수정자 : Lee.SangHoon
 * @수정일 : 2023. 7. 27.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/itgcm/fdrmrptstats")
public class FdrmRptStatsController {

	@Resource(name = "fdrmRptStatsService")
	FdrmRptStatsService fdrmRptStatsService;

	@Resource(name = "userLoginService")
	UserLoginService userLoginService;

	@Resource(name = "userInstAuthService")
	UserInstAuthService userInstAuthService;

	/**
	 * @Method명 : selectFdrmRptStatsRegns
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 27.
	 * @Method설명 : 정기보고통계 조회 - 지자체청소년안전망
	 */
	@RequestMapping(value = "/selectFdrmRptStatsRegns.do")
	public View selectFdrmRptStatsRegns(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		Map<String, Object> paramMap = new HashMap<String, Object>(dmSearch); /* 형변환 */
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk()); // 단위업무
		paramMap.put("USER_INST_NO", loginVO.getInstNo()); // 사용자기관번호
		
//		paramMap.put("INST_NOS", fdrmRptStatsService.getLwprtInstList(loginVO.getInstNo())); // 본인기관 및 하위기관번호
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		paramMap.put("INST_NOS", comMap.get("INST_NOS")); // 공통 권한 적용 (20230825)

		dataRequest.setResponse("dsUneartReg", fdrmRptStatsService.selectUneartRegStats(paramMap)); // 발굴등록
		dataRequest.setResponse("dsCaseMng", fdrmRptStatsService.selectCaseMngStats(paramMap)); // 사례관리
		dataRequest.setResponse("dsSprtSrvc", fdrmRptStatsService.selectSprtSrvcStats(paramMap)); // 지원서비스
		dataRequest.setResponse("dsCrisisLevel", fdrmRptStatsService.selectCrisisLevelPrecon(paramMap)); // 위기수준에 따른 지원현황

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectFdrmRptStatsCysns
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 27.
	 * @Method설명 : 정기보고통계 조회 - 청소년상담복지센터
	 */
	@RequestMapping(value = "/selectFdrmRptStatsCysns.do")
	public View selectFdrmRptStatsCysns(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		Map<String, Object> paramMap = new HashMap<String, Object>(dmSearch); /* 형변환 */
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk()); // 단위업무
		paramMap.put("USER_INST_NO", loginVO.getInstNo()); // 사용자기관번호
		
//		paramMap.put("INST_NOS", fdrmRptStatsService.getLwprtInstList(loginVO.getInstNo())); // 본인기관 및 하위기관번호
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		paramMap.put("INST_NOS", comMap.get("INST_NOS")); // 공통 권한 적용 (20230825)
		
		paramMap.put("MODE", "00");

		dataRequest.setResponse("dsCaseMng", fdrmRptStatsService.selectCaseMngStats(paramMap)); // 사례관리
		dataRequest.setResponse("dsSprtSrvc", fdrmRptStatsService.selectSprtSrvcStats(paramMap)); // 지원서비스
		dataRequest.setResponse("ds1388", fdrmRptStatsService.select1388TlphonDscsn(paramMap)); // 1388전화상담
		dataRequest.setResponse("dsEfectn", fdrmRptStatsService.selectEfectnEvlSrvcDgstfn(paramMap)); // 효과성 평가 및 서비스 만족도

		// 고위기청소년 지원
		paramMap.put("MODE", "01");
		dataRequest.setResponse("dsCaseMng1", fdrmRptStatsService.selectCaseMngStats(paramMap)); // 사례관리
		dataRequest.setResponse("dsSprtSrvc1", fdrmRptStatsService.selectSprtSrvcStats(paramMap)); // 지원서비스

		// 청소년동반자 사업
		paramMap.put("MODE", "02");
		dataRequest.setResponse("dsCaseMng2", fdrmRptStatsService.selectCaseMngStats(paramMap)); // 사례관리
		dataRequest.setResponse("dsSprtSrvc2", fdrmRptStatsService.selectSprtSrvcStats(paramMap)); // 지원서비스

		// 고위기청소년맞춤형프로그램(안전망팀사업)
		paramMap.put("MODE", "03");
		dataRequest.setResponse("dsCaseMng3", fdrmRptStatsService.selectCaseMngStats(paramMap)); //사례관리
		dataRequest.setResponse("dsSprtSrvc3", fdrmRptStatsService.selectSprtSrvcStats(paramMap)); // 지원서비스

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectFdrmRptStatsDrmgs
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 27.
	 * @Method설명 : 정기보고통계 조회 - 학교밖청소년지원센터
	 */
	@RequestMapping(value = "/selectFdrmRptStatsDrmgs.do")
	public View selectFdrmRptStatsDrmgs(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		Map<String, Object> paramMap = new HashMap<String, Object>(dmSearch); /* 형변환 */
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk()); // 단위업무
		paramMap.put("USER_INST_NO", loginVO.getInstNo()); // 사용자기관번호
		
//		paramMap.put("INST_NOS", fdrmRptStatsService.getLwprtInstList(loginVO.getInstNo())); // 본인기관 및 하위기관번호
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		paramMap.put("INST_NOS", comMap.get("INST_NOS")); // 공통 권한 적용 (20230825)

		dataRequest.setResponse("dsRegStats", fdrmRptStatsService.selectUneartRegStats(paramMap)); // 발굴등록
		dataRequest.setResponse("dsCaseMng", fdrmRptStatsService.selectCaseMngStats(paramMap)); // 사례관리
		dataRequest.setResponse("dsOutc", fdrmRptStatsService.selectOutcStats(paramMap)); // 성과
		dataRequest.setResponse("dsSprtSrvc", fdrmRptStatsService.selectSprtSrvcStats(paramMap)); // 지원서비스

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectFdrmRptStatsPubms
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 27.
	 * @Method설명 : 정기보고통계 조회 - 청소년쉼터
	 */
	@RequestMapping(value = "/selectFdrmRptStatsPubms.do")
	public View selectFdrmRptStatsPubms(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		Map<String, Object> paramMap = new HashMap<String, Object>(dmSearch); /* 형변환 */
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk()); // 단위업무
		paramMap.put("USER_INST_NO", loginVO.getInstNo()); // 사용자기관번호

//		paramMap.put("INST_NOS", fdrmRptStatsService.getLwprtInstList(loginVO.getInstNo())); // 본인기관 및 하위기관번호
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		paramMap.put("INST_NOS", comMap.get("INST_NOS")); // 공통 권한 적용 (20230825)
		
		paramMap.put("MODE", "00");

		dataRequest.setResponse("dsOutrc", fdrmRptStatsService.selectOutrcActvt(paramMap)); // 아웃리치 활동
		dataRequest.setResponse("dsOuthfa", fdrmRptStatsService.selectOuthfaYngbgsActnPrecon(paramMap)); // 가정밖청소년 조치현황
		dataRequest.setResponse("dsEntrcLvngCaseMng", fdrmRptStatsService.selectEntrncLvngCaseMng(paramMap)); // 입소퇴소사례관리
		dataRequest.setResponse("dsSprt_Srvc1", fdrmRptStatsService.selectSprtSrvcStats(paramMap)); // 지원서비스
		dataRequest.setResponse("dsLvngSlfrl", fdrmRptStatsService.selectPstrtrSrlst(paramMap)); // 퇴소후 자립현황
		dataRequest.setResponse("dsAftfct", fdrmRptStatsService.selectAftfctSprtSrvc(paramMap)); // 사후지원서비스
		dataRequest.setResponse("dsSrvcDgstfn", fdrmRptStatsService.selectSrvcDgstfn(paramMap)); // 서비스만족도

		paramMap.put("MODE", "04");
		dataRequest.setResponse("dsSprt_Srvc2", fdrmRptStatsService.selectSprtSrvcStats(paramMap)); // 지원서비스(사례미등록 별도)

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectFdrmRptStatsPubmr
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 27.
	 * @Method설명 : 정기보고통계 조회 - 청소년자립지원관
	 */
	@RequestMapping(value = "/selectFdrmRptStatsPubmr.do")
	public View selectFdrmRptStatsPubmr(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		Map<String, Object> paramMap = new HashMap<String, Object>(dmSearch); /* 형변환 */
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk()); // 단위업무
		paramMap.put("USER_INST_NO", loginVO.getInstNo()); // 사용자기관번호

//		paramMap.put("INST_NOS", fdrmRptStatsService.getLwprtInstList(loginVO.getInstNo())); // 본인기관 및 하위기관번호
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		paramMap.put("INST_NOS", comMap.get("INST_NOS")); // 공통 권한 적용 (20230825)

		dataRequest.setResponse("dsCaseMngStats", fdrmRptStatsService.selectEntrncLvngCaseMng(paramMap)); // 입소퇴소 사례관리
		dataRequest.setResponse("dsSrvcStats", fdrmRptStatsService.selectSprtSrvcStats(paramMap)); // 지원서비스
		dataRequest.setResponse("dsLvngSlfrl", fdrmRptStatsService.selectPstrtrSrlst(paramMap)); // 퇴소 후 자립현황
		dataRequest.setResponse("dsAftfct", fdrmRptStatsService.selectAftfctSprtSrvc(paramMap)); // 사후지원서비스
		dataRequest.setResponse("dsSrvcDgstfn", fdrmRptStatsService.selectSrvcDgstfn(paramMap)); // 서비스 만족도

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectFdrmRptStatsPubmt
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 7. 27.
	 * @Method설명 : 정기보고통계 조회 - 청소년회복지원시설
	 */
	@RequestMapping(value = "/selectFdrmRptStatsPubmt.do")
	public View selectFdrmRptStatsPubmt(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();

		Map<String, Object> paramMap = new HashMap<String, Object>(dmSearch); /* 형변환 */
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk()); // 단위업무
		paramMap.put("USER_INST_NO", loginVO.getInstNo()); // 사용자기관번호
		
//		paramMap.put("INST_NOS", fdrmRptStatsService.getLwprtInstList(loginVO.getInstNo())); // 본인기관 및 하위기관번호
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		paramMap.put("INST_NOS", comMap.get("INST_NOS")); // 공통 권한 적용 (20230825)

		dataRequest.setResponse("dsEntrcLvngCaseMng", fdrmRptStatsService.selectEntrncLvngCaseMng(paramMap)); // 입소퇴소 사례관리
		dataRequest.setResponse("dsSprtSrvc", fdrmRptStatsService.selectSprtSrvcStats(paramMap)); // 지원서비스
		dataRequest.setResponse("dsLvngCsPrecon", fdrmRptStatsService.selectLvngCsPrecon(paramMap)); // 퇴소사유별 현황
		dataRequest.setResponse("dsAftfct", fdrmRptStatsService.selectAftfctSprtSrvc(paramMap)); // 사후지원서비스

		return new JSONDataView();
	}
}

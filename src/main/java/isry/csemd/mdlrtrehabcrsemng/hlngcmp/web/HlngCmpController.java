/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mdlrtrehabcrsemng.hlngcmp.web;

import java.util.HashMap;
import java.util.List;
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
import isry.csemd.mdlrtrehabcrsemng.hlngcmp.service.HlngCmpService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : HlngCmpController.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 9. 16.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 9. 16.
 * @수정내용 : - -
 */
@Controller
@RequestMapping("/isry/csemd/mdlrtrehabcrsemng/hlngcmp")
public class HlngCmpController {

	// 힐링캠프 서비스
	@Resource(name = "hlngCmpService")
	private HlngCmpService hlngCmpService;

	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	// 드림&디딤 콤보데이터 조회 서비스
	@Resource(name = "csemdService")
	private CsemdService csemdService;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명 : selectMentorMenteeMatchingCombo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 10. 19.
	 * @Method설명 : 멘토멘티매칭 콤보데이터 조회
	 */
	@RequestMapping("/selectMentorMenteeMatchingCombo.do")
	public View selectMentorMenteeMatchingCombo(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());

		// 사업연도
		List<Map<String, Object>> bizYrList = csemdService.selectBizYrCmb(requestMap);

		// 서비스실행사업(과정)
		List<Map<String, Object>> srvcExcnBizList = csemdService.selectSrvcExcnBizCmb(requestMap);

		// 공통코드
		// 배정그룹소분류구분코드
		List<Map<String, Object>> dsAltmntGroupSclasSeCd = mgmtCmmnCodeService
				.selectCommonCodeUnit("ALTMNT_GROUP_SCLAS_SE_CD", loginVO.getUntTaskwk());

		dataRequest.setResponse("dsBizYr", bizYrList);
		dataRequest.setResponse("dsSrvcExcnBizCmb", srvcExcnBizList);
		dataRequest.setResponse("dsAltmntGroupSclasSeCd", dsAltmntGroupSclasSeCd);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectMentorMenteeMatchingList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 : 멘토 목록,멘티 목록,멘토멘티매칭 목록조회
	 */
	@RequestMapping(value = "/selectMentorMenteeMatchingList.do")
	public View selectMentorMenteeMatchingList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		UserDetailsVO userVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> paramMap = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		paramMap.put("INST_NO", userVO.getInstNo().toString());
		paramMap.put("UNT_TASKWK_SE_CD", userVO.getUntTaskwk());
		paramMap.put("INST_TYPE_SE_CD", userVO.getInstTypeSeCd());

		dataRequest.setResponse("dsMentor", hlngCmpService.selectMentorList(paramMap));
		dataRequest.setResponse("dsMentee", hlngCmpService.selectMenteeList(paramMap));
		dataRequest.setResponse("dsList", hlngCmpService.selectMentorMenteeMatchingList(paramMap));

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : mentorMenteeMatching
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 16.
	 * @Method설명 : 멘토 멘티 매칭 저장
	 */
	@RequestMapping("/saveMentorMenteeMatching.do")
	public View mentorMenteeMatching(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		hlngCmpService.saveMmMatchingList(request, dataRequest);
		return null;
	}

	/**
	 * @Method명 : selectDayObservDiaryListCombo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 : 일일관찰일지 목록 콤보데이터 조회
	 */
	@RequestMapping("/selectDayObservDiaryListCombo.do")
	public View selectDayObservDiaryListCombo(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());

		// 사업연도
		dataRequest.setResponse("dsBizYr", csemdService.selectBizYrCmb(requestMap));
		// 과정(서비스실행사업)
		dataRequest.setResponse("dsSrvcExcnBizCmb", csemdService.selectSrvcExcnBizCmb(requestMap));
		// 작성자[멘토] (종사자)
		dataRequest.setResponse("dsMentor", hlngCmpService.selectMentorList(requestMap));
		// 대상자[멘티] (사례대상자)
		dataRequest.setResponse("dsMentee", hlngCmpService.selectMentorMenteeMatchingList(requestMap));
		// 공통코드
		// 배정그룹소분류구분코드
		dataRequest.setResponse("dsAltmntGroupSclasSeCd",
				mgmtCmmnCodeService.selectCommonCodeUnit("ALTMNT_GROUP_SCLAS_SE_CD", loginVO.getUntTaskwk()));

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : selectDayObservDiaryList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 16.
	 * @Method설명 : 일일관찰일지 목록 조회
	 */
	@RequestMapping("/selectDayObservDiaryList.do")
	public View selectDayObservDiaryList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		hlngCmpService.selectDayObservDiaryList(request, dataRequest);
		return new JSONDataView();
	}

	/**
	 * @Method명 : selectDayObservDiaryMngCombo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 9. 29.
	 * @Method설명 : 일일관찰일지 상세 콤보데이터 조회
	 */
	@RequestMapping("/selectDayObservDiaryMngCombo.do")
	public View selectDayObservDiaryMngCombo(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());

		// 사업연도
		List<Map<String, Object>> bizYrList = csemdService.selectBizYrCmb(requestMap);

		// 과정(서비스실행사업)
		List<Map<String, Object>> srvcExcnBizList = csemdService.selectSrvcExcnBizCmb(requestMap);

		// 작성자[멘토] (종사자)
		List<Map<String, Object>> mentorList = hlngCmpService.selectMentorList(requestMap);

		// 대상자[멘티] (사례대상자)
		List<Map<String, Object>> menteeList = hlngCmpService.selectMentorMenteeMatchingList(requestMap);

		// 공통코드
		// 배정그룹소분류구분코드
		List<Map<String, Object>> dsAltmntGroupSclasSeCd = mgmtCmmnCodeService
				.selectCommonCodeUnit("ALTMNT_GROUP_SCLAS_SE_CD", loginVO.getUntTaskwk());
		// 성별구분코드
		List<Map<String, Object>> dsSxdcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD",
				loginVO.getUntTaskwk());
		// 위생상태구분코드
		List<Map<String, Object>> dsSnitat = mgmtCmmnCodeService.selectCommonCodeUnit("SNITAT_STTS_SE_CD",
				loginVO.getUntTaskwk());
		// 건강구분코드
		List<Map<String, Object>> dsHealth = mgmtCmmnCodeService.selectCommonCodeUnit("HEALTH_SE_CD",
				loginVO.getUntTaskwk());
		// 참여태도구분코드
		List<Map<String, Object>> dsPtcptnAtitd = mgmtCmmnCodeService.selectCommonCodeUnit("PTCPTN_ATITD_SE_CD",
				loginVO.getUntTaskwk());
		// 행동특성구분코드
		List<Map<String, Object>> dsGhvrChar = mgmtCmmnCodeService.selectCommonCodeUnit("GHVR_CHAR_SE_CD",
				loginVO.getUntTaskwk());
		// 지도자대인관계구분코드
		List<Map<String, Object>> dsLeaderTwdpsnRel = mgmtCmmnCodeService
				.selectCommonCodeUnit("LEADER_TWDPSN_REL_SE_CD", loginVO.getUntTaskwk());
		// 또래대인관계구분코드
		List<Map<String, Object>> dsFridaTwdpsnRel = mgmtCmmnCodeService.selectCommonCodeUnit("FRIDA_TWDPSN_REL_SE_CD",
				loginVO.getUntTaskwk());
		// 목표달성정도구분코드
		List<Map<String, Object>> dsIndivGoalAchivDgree = mgmtCmmnCodeService
				.selectCommonCodeUnit("GOAL_ACHIV_DGREE_SE_CD", loginVO.getUntTaskwk());

		dataRequest.setResponse("dsBizYr", bizYrList);
		dataRequest.setResponse("dsSrvcExcnBizCmb", srvcExcnBizList);
		dataRequest.setResponse("dsMentor", mentorList);
		dataRequest.setResponse("dsMentee", menteeList);
		dataRequest.setResponse("dsAltmntGroupSclasSeCd", dsAltmntGroupSclasSeCd);
		dataRequest.setResponse("dsSxdcSeCd", dsSxdcSeCd);
		dataRequest.setResponse("dsSnitat", dsSnitat);
		dataRequest.setResponse("dsHealth", dsHealth);
		dataRequest.setResponse("dsPtcptnAtitd", dsPtcptnAtitd);
		dataRequest.setResponse("dsGhvrChar", dsGhvrChar);
		dataRequest.setResponse("dsLeaderTwdpsnRel", dsLeaderTwdpsnRel);
		dataRequest.setResponse("dsFridaTwdpsnRel", dsFridaTwdpsnRel);
		dataRequest.setResponse("dsIndivGoalAchivDgree", dsIndivGoalAchivDgree);

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : selectDayObservDiary
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 16.
	 * @Method설명 : 일일관찰일지 상세조회
	 */
	@RequestMapping("/selectDayObservDiary.do")
	public View selectDayObservDiary(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		hlngCmpService.selectDayObservDiary(dataRequest);
		return new JSONDataView();
	}

	/**
	 * @Method명 : saveDayObservDiary
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 16.
	 * @Method설명 : 일일관찰일지 등록/수정
	 */
	@RequestMapping("/saveDayObservDiary.do")
	public View saveDayObservDiary(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		hlngCmpService.saveDayObservDiary(request, dataRequest);
		return new JSONDataView();
	}

	/**
	 * @Method명 : selectDayObservDiaryCheck
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 10. 5.
	 * @Method설명 : 일일관찰일지 등록여부 확인
	 */
	@RequestMapping("/selectDayObservDiaryCheck.do")
	public View selectDayObservDiaryCheck(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		hlngCmpService.selectDayObservDiaryCheck(dataRequest);
		return new JSONDataView();
	}
}

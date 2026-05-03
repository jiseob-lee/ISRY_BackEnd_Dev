/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.sysmng.srvymng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.csemd.mngrpage.aplcnttrprmng.mapper.RsvtSmsMapper;
import isry.csemd.sysmng.srvymng.mapper.SrvyMngMapper;
import isry.csemd.sysmng.srvymng.service.SrvyMngService;
import isry.itgcm.outsdsrvyptcptn.service.OutsdSrvyPtcptnService;
import isry.itgcms.syscmmn.rsvtmng.mapper.RsvtMngMapper;
import isry.itgcms.syscmmn.survsht.service.SurvshtCmmnsInqService;
import isry.itgcms.syscmmn.survsht.service.SurvshtMmnService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry2.csemd.mngrpage.aplcnttrprmng.mapper.RsvtSms2Mapper;

/**
 * @파일명 : SrvyMngServiceImpl.java
 * @프로그램 설명 : 설문관리 서비스 임플리먼트 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 10. 25.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 10. 25.
 * @수정내용 : - -
 */
@Service(value = "srvyMngService")
public class SrvyMngServiceImpl implements SrvyMngService {

	@Resource(name = "srvyMngMapper")
	SrvyMngMapper srvyMngMapper;

	// 설문지 문자 SHOT CUT URL 생성 및 문자 내용 조합 Service Class
	@Resource(name = "outsdSrvyPtcptnService")
	private OutsdSrvyPtcptnService outsdSrvyPtcptnService;

	// 대상자정보 Service Class
//	@Resource(name = "trprInqService")
//	private TrprInqService trprInqService;

	// 설문지 관리번호 생성 Service Class
	@Resource(name = "survshtMmnService")
	private SurvshtMmnService survshtMmnService;

	// 설문지템플릿관리번호 사용여부
	@Resource(name = "survshtCmmnsInqService")
	private SurvshtCmmnsInqService survshtCmmnsInqService;

	@Resource(name = "csemdRsvtSmsMapper")
	private RsvtSmsMapper rsvtSmsMapper;

	@Resource(name = "csemdRsvtSms2Mapper")
	private RsvtSms2Mapper rsvtSms2Mapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "rsvtMngMapper")
	private RsvtMngMapper rsvtMngMapper;

	/**
	 * @Method명 : selectTrprInfo
	 * @param requestMap
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 10. 25.
	 * @Method설명 : 대상자 정보 조회
	 */
	@Override
	public List<Map<String, Object>> selectTrprInfo(Map<String, String> requestMap) {

		List<Map<String, Object>> resultMap = srvyMngMapper.selectTrprInfo(requestMap);

		for (Map<String, Object> map : resultMap) {

			// 교급/학년
			if (map.get("ACBG") != null && map.get("GRADE") != null)
				map.put("ACBG_GRADE", map.get("ACBG") + "/" + map.get("GRADE"));

			// 생년월일(만나이)
			if (map.get("BRDT") != null && map.get("AGEA") != null) {
				String brdt = (String) map.get("BRDT");
				map.put("BRDT_AGEA", brdt.substring(0, 4) + "-" + brdt.substring(4, 6) + "-" + brdt.substring(6, 8)
						+ "(만 " + map.get("AGEA") + "세)");
			}
		}
		return resultMap;
	}

	/**
	 * @Method명 : selectSrvyRspnsInfo
	 * @param requestMap
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 10. 25.
	 * @Method설명 : 설문 응답 정보 조회
	 */
	@Override
	public List<Map<String, Object>> selectSrvyRspnsInfo(Map<String, String> requestMap) {

		List<Map<String, Object>> resultMap = srvyMngMapper.selectSrvyRspnsInfo(requestMap);

		for (Map<String, Object> map : resultMap) {

			// 생년월일(만나이)
			if (map.get("BRDT") != null && map.get("AGEA") != null) {
				String brdt = String.valueOf(map.get("BRDT"));
				map.put("BRDT_AGEA", brdt.substring(0, 4) + "-" + brdt.substring(4, 6) + "-" + brdt.substring(6, 8)
						+ "(만 " + map.get("AGEA") + "세)");
			}
		}
		return resultMap;
	}

	/**
	 * @Method명 : selectSrvyChart
	 * @param request
	 * @param dataRequest
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 26.
	 * @Method설명 : 설문차트 조회
	 */
	@Override
	public void selectSrvyChart(HttpServletRequest request, DataRequest dataRequest) {
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ParameterGroup dmParam = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> requestMap = dmParam.getSingleValueMap();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());

		List<Map<String, Object>> resultMap = srvyMngMapper.selectSrvyChart(requestMap);
		Map<String, Object> countMap = srvyMngMapper.selectSrvyTrprCount(requestMap);

		for (int i = 0; i < resultMap.size(); i++) {
			resultMap.get(i).replace("QUSTNB_GRDNG_RELM_ESNTAL_NM",
					(String.valueOf(resultMap.get(i).get("QUSTNB_GRDNG_RELM_ESNTAL_NM")).trim()));
			if (String.valueOf(resultMap.get(i).get("QUSTNB_GRDNG_RELM_ESNTAL_NM")).equals("재활의지(동기, 신뢰, 협력)")) {
				resultMap.remove(i);
			}
		}

		for (Map<String, Object> map : resultMap) {
			map.replace("QUSTNB_GRDNG_RELM_ESNTAL_NM", (String.valueOf(map.get("QUSTNB_GRDNG_RELM_ESNTAL_NM")).trim()));
			if (String.valueOf(map.get("QUSTNB_GRDNG_RELM_ESNTAL_NM")).length() > 6) {
				String a = String.valueOf(map.get("QUSTNB_GRDNG_RELM_ESNTAL_NM"));
				String b = "";
				for (int i = 0; i < a.length(); i++) {
					if (i % 6 == 0 && i != 0) {
						b += "\n" + a.charAt(i);
					} else {
						b += a.charAt(i);
					}
				}
				map.replace("QUSTNB_GRDNG_RELM_ESNTAL_NM", b);
			}
		}

		dataRequest.setResponse("dsChart1", resultMap);
		dataRequest.setMetadata(true, countMap);

	}

	/**
	 * @Method명 : selectSrvyAnlsCn
	 * @param request
	 * @param dataRequest
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 28.
	 * @Method설명 : 설문이력 분석내용 조회
	 */
	@Override
	public void selectSrvyAnlsCn(HttpServletRequest request, DataRequest dataRequest) {
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ParameterGroup dmParam = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> requestMap = dmParam.getSingleValueMap();
		requestMap.put("UNT_TASKWK_SE_CD_", loginVO.getUntTaskwk());

		List<Map<String, Object>> resultMap = srvyMngMapper.selectSrvyAnlsCn(requestMap);

		for (int i = 0; i < resultMap.size(); i++) {
			resultMap.get(i).replace("QUSTNB_GRDNG_RELM_ESNTAL_NM",
					(String.valueOf(resultMap.get(i).get("QUSTNB_GRDNG_RELM_ESNTAL_NM")).trim()));
			if (String.valueOf(resultMap.get(i).get("QUSTNB_GRDNG_RELM_ESNTAL_NM")).equals("재활의지(동기, 신뢰, 협력)")) {
				resultMap.remove(i);
			}
		}

		int avgIndex = 0;
		Map<String, Object> avgMap = new HashMap<String, Object>();
		int index = 0;
		for (Map<String, Object> map : resultMap) {
			map.replace("QUSTNB_GRDNG_RELM_ESNTAL_NM", (String.valueOf(map.get("QUSTNB_GRDNG_RELM_ESNTAL_NM")).trim()));
			if (String.valueOf(map.get("QUSTNB_GRDNG_RELM_ESNTAL_NM")).length() > 6) {
				String a = String.valueOf(map.get("QUSTNB_GRDNG_RELM_ESNTAL_NM"));
				String b = "";
				for (int i = 0; i < a.length(); i++) {
					if (i % 6 == 0 && i != 0) {
						b += "\n" + a.charAt(i);
					} else {
						b += a.charAt(i);
					}
				}
				map.replace("QUSTNB_GRDNG_RELM_ESNTAL_NM", b);
			}
			if (map.get("QUSTNB_GRDNG_RELM_ESNTAL_NM").equals("평균")) {
				avgIndex = index;
				avgMap = map;
			}
			index++;
		}
		resultMap.remove(avgIndex);
		resultMap.add(0, avgMap);

		Map<String, Object> qustnbKndSeCd = srvyMngMapper.selectQustnbKndSeCd(requestMap);

		dataRequest.setMetadata(true, qustnbKndSeCd);
		dataRequest.setResponse("dsChart1", resultMap);
	}

	/**
	 * @Method명 : selectSrvyRecodeList
	 * @param request
	 * @param dataRequest
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 10. 26.
	 * @Method설명 : 설문 이력 조회
	 */
	@Override
	public List<Map<String, Object>> selectSrvyRecodeList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> paramMap = dmSearch.getSingleValueMap();
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());

		result = srvyMngMapper.selectSrvyRecodeList(paramMap);

		// 대상자 및 담당자 복호화
		for (int i = 0; i < result.size(); i++) {

			// 설문응답자
			String sFlnmEncpt = String.valueOf(result.get(i).get("SRVY_RSPDNT_NM_ENCPT"));
			result.get(i).put("SRVY_RSPDNT_NM", Masking.nameMasking(sFlnmEncpt));

			// 대상자명
			String sTrprNmEncpt = String.valueOf(result.get(i).get("TRPR_NM_ENCPT"));
			result.get(i).put("TRPR_NM", Masking.nameMasking(sTrprNmEncpt));

			// 설문응답자 핸드폰
			String SrvyRspnsRcptnMblTelno = String.valueOf(result.get(i).get("SRVY_RSPNS_RCPTN_MBL_TELNO_ENCPT"));
			result.get(i).put("SRVY_RSPNS_RCPTN_MBL_TELNO", Masking.phoneMasking(SrvyRspnsRcptnMblTelno));

		}

		for (Map<String, Object> map : result) {

			// 대상자 생년월일 및 나이
			if (map.get("TRPR_BRTH_YMD") != null && map.get("AGE") != null) {
				String brdt = (String) map.get("TRPR_BRTH_YMD");
				map.put("BRTH_DT_AGE", brdt.substring(0, 4) + "-" + brdt.substring(4, 6) + "-" + brdt.substring(6, 8)
						+ "(만" + map.get("AGE") + "세)");
			}
			// 설문응답자 생년월일 및 나이
			if (map.get("SRVY_RSPDNT_BRTH_YMD") != null && map.get("SRVY_RSPDNT_AGE") != null) {
				String brdt = (String) map.get("SRVY_RSPDNT_BRTH_YMD");
				map.put("SRVY_RSPDNT_BRTH_AGE", brdt.substring(0, 4) + "-" + brdt.substring(4, 6) + "-"
						+ brdt.substring(6, 8) + "(만" + map.get("SRVY_RSPDNT_AGE") + "세)");
			}

		}
		return result;
	}

	/**
	 * @Method명 : selectSrvySndngList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 31.
	 * @Method설명 : 설문발송목록
	 */
	@Override
	public List<Map<String, String>> selectSrvySndngList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> mapParam = dmSearch.getAllRowList().get(0);

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String untTaskwkSeCd = loginVO.getUntTaskwk(); // 단위업무구분코드

		mapParam.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);

		List<Map<String, String>> result = srvyMngMapper.selectSrvySndngList(mapParam);

		for (Map<String, String> map : result) {
			map.replace("RSPDNT", Masking.nameMasking(map.get("RSPDNT")));
			map.replace("RSPDNT_RCPTN", Masking.phoneMasking(map.get("RSPDNT_RCPTN")));
			map.replace("TRPR_NM_ENCPT", Masking.nameMasking(map.get("TRPR_NM_ENCPT")));
		}

		return result;
	}

	/**
	 * @Method명 : chkQustnbTmptUseYn
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 12. 13.
	 * @Method설명 : 설문지생성
	 */
	@Override
	public Map<String, Object> chkQustnbTmptUseYn(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSrvy = dataRequest.getParameterGroup("dmSrvy");
		ParameterGroup dsListParam = dataRequest.getParameterGroup("dsList");
		ParameterGroup dsListParam2 = dataRequest.getParameterGroup("dsList2");

		// insert 할 대상자(복수)
		List<Map<String, String>> getAllRowList = dsListParam.getAllRowList();
		List<Map<String, String>> dsList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < getAllRowList.size(); i++) {
			if (!getAllRowList.get(i).get("SRVY_RCPTN_MTHD_SE_CD").equals("null")) {
				dsList.add(getAllRowList.get(i));
			}
		}

		// insert 할 대상자(단일)
		List<Map<String, String>> getAllRowList2 = dsListParam2.getAllRowList();
		List<Map<String, String>> dsList2 = new ArrayList<Map<String, String>>();
		for (int i = 0; i < getAllRowList2.size(); i++) {
			dsList2.add(getAllRowList2.get(i));
		}

		// 로그인 한 유저 정보
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String untTaskwk = loginVO.getUntTaskwk(); // 단위업무구분코드

		Map<String, Object> message = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> searchMap = new HashMap<>();

		searchMap.put("QUSTNB_TMPT_MNG_NO", dmSrvy.getValue("QUSTNB_TMPT_MNG_NO")); // 설문지템플릿관리번호
		searchMap.put("UNT_TASKWK_SE_CD", untTaskwk);

		// 사용할 설문지 템플릿관리번호가 사용중인지 미사용인지 여부 조회
		resultMap = survshtCmmnsInqService.searchQustnbTmptUseYn(searchMap);

		if (resultMap.get("USE_YN") == null) {
			message.put("USE_YN", "X");
		}

		String useYn = resultMap.get("USE_YN").toString();

		if (useYn.equals("N")) { // 사용중지 -> 설문지 발송불가
			message.put("USE_YN", "N");

		} else { // 사용중 -> 설문지 발송가능

			searchMap.put("REUS_YN", dmSrvy.getValue("REUS_YN"));

			String reusYn = dmSrvy.getValue("REUS_YN");

			if (reusYn.equals("N")) { // 재사용여부 : N -> 설문지관리번호 채번

				if (dmSrvy.getValue("NO_INPT_SNDNG_YN").equals("Y")) { // 복수발송 채번

					resultMap.put("QUSTNB_TMPT_MNG_NO", searchMap.get("QUSTNB_TMPT_MNG_NO"));
					resultMap.put("UNT_TASKWK_SE_CD", searchMap.get("UNT_TASKWK_SE_CD"));
					resultMap.put("MNGNO_RENU", "Y"); // 설문지관리번호 채번 여부

					updateQustnbCompno(request, dataRequest, resultMap);

				} else {
					// 신규생성
					resultMap = survshtMmnService.processSurvshtTmptData(request, dataRequest, searchMap);
					updateQustnbSingle(request, dataRequest, resultMap);

				}
				// 신규생성
				resultMap = survshtMmnService.processSurvshtTmptData(request, dataRequest, searchMap);

			} else if (reusYn.equals("Y")) { // 재사용여부 : Y -> 설문지관리번호 조회

				// 설문지관리번호조회 후 리턴
			}

//			//설문지 생성 정보 저장
//			if(dmSrvy.getValue("NO_INPT_SNDNG_YN").equals("Y")) {
//				updateQustnbCompno(request, dataRequest, resultMap);
//			}else {
//				
//				updateQustnbSingle(request, dataRequest, resultMap);
//			}

			message.put("USE_YN", "Y");
		}

		return message;
	}

	/**
	 * @Method명 : updateQustnbCompno
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 31.
	 * @Method설명 : 설문지 발송(복수)
	 */

	// ### 문자발송 정보
	String sendMsg = "";

	@Override
	public void updateQustnbCompno(HttpServletRequest request, DataRequest dataRequest, Map<String, Object> resultMap)
			throws Exception {

		ParameterGroup dsListParam = dataRequest.getParameterGroup("dsList");
		ParameterGroup dmSrvy = dataRequest.getParameterGroup("dmSrvy");
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		List<Map<String, String>> getAllRowList = dsListParam.getAllRowList();
		List<Map<String, String>> dsList = new ArrayList<Map<String, String>>();

		// insert 할 대상자(복수)
		for (int i = 0; i < getAllRowList.size(); i++) {
			if (!getAllRowList.get(i).get("SRVY_RCPTN_MTHD_SE_CD").equals("null")) {
				dsList.add(getAllRowList.get(i));
			}
		}

		// 로그인 한 유저 정보
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String untTaskwkSeCd = loginVO.getUntTaskwk(); // 단위업무구분코드
		String sUserId = loginVO.getId(); // 로그인 한 유저
		String sInstno = String.valueOf(loginVO.getInstNo()); // 발신기관번호
//		String sWrdTelno = loginVO.getWrdTelno(); // 유선전화번호
		String sWrdTelno = "";

		for (Map<String, String> map : dsList) {

			if (resultMap.get("MNGNO_RENU").equals("Y")) {
				// 2. 설문지 관리번호 생성
				Map<String, Object> result = survshtMmnService.processSurvshtTmptData(request, dataRequest, resultMap);
				map.put("QUSTNB_MNG_NO", (String) result.get("QUSTNB_MNG_NO").toString()); // 설문지관리번호
			} else {
				map.put("QUSTNB_MNG_NO", (String) resultMap.get("QUSTNB_MNG_NO").toString()); // 설문지관리번호
			}

			map.put("FRST_RGTR_ID", sUserId);
			map.put("LAST_MDFR_ID", sUserId);

			// 3. SBB110 설문대상자 insert
			srvyMngMapper.insertQustnbTrprInfo(map);

			// 발신대표번호 조회
			map.put("INST_NO", sInstno);
			sWrdTelno = rsvtMngMapper.selectRprsTelno(map);

			untTaskwkSeCd = srvyMngMapper.selectUntTaskwkSeCd(map);

			// 4. 문자발송
			String telnoReplace = sWrdTelno.replace("-", "");
			String callTo = map.get("RSPDNT_RCPTN").replace("-", "");

			if (map.get("SRVY_RCPTN_MTHD_SE_CD").equals("3")) {

				map.put("MSG_TEMP", dmSrvy.getValue("CHRCTR_CN") + "\n[설문지작성]\n설문지 참여를 해주세요.\n");
				map.put("PATH", "/isry/itgcm/outsdsrvyptcptn/outsdSrvyPtcptnWrite.do");
				map.put("USER_ID", sUserId);
				map.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);
				map.put("TRPR_REL_SE_CD", map.get("TRPR_REL_CD"));
				map.put("SXDC_SE_CD", map.get("SXDC_SE_CD").toString());
				// outsdSrvyPtcptnParam.put("MNGR_YN", userParam.get("MNGR_YN").toString());

				sendMsg = outsdSrvyPtcptnService.getSendMsg(map);

				// System.err.println("로그로그 " + sendMsg);

				map.put("CONT_SEQ", map.get("CONT_SEQ"));
				map.put("RSVT_CHRCTR_CN", sendMsg);
				map.put("FRST_RGTR_ID", sUserId);
				map.put("LAST_MDFR_ID", sUserId);
				map.put("CALL_FROM", telnoReplace); // 발신휴대전화번호
				map.put("RSVT_MMDD", dmSrvy.getValue("RSVT_MMDD"));
				map.put("RSVT_HHSS", dmSrvy.getValue("RSVT_HHSS"));
				map.put("CALL_TO", callTo); // 응답자 수신번호

				// MMS 컨텐츠 정보 insert
				rsvtSms2Mapper.insertMmsContentsInfo(map);
				// MMS 메세지 데이터 insert
				rsvtSms2Mapper.insertMsgData(map);

			}

			// 번호입력여부
			if (dmSrvy.getValue("NO_INPT_SNDNG_YN").equals("N")) {
				map.put("NO_INPT_SNDNG_YN", dmSrvy.getValue("NO_INPT_SNDNG_YN"));
				map.put("SRVY_RSPDNT_NM_ENCPT", dmSrvy.getValue("SRVY_RSPDNT_NM_ENCPT")); // 설문응답자명암호화
				map.put("SRVY_RSPNS_RCPTN_MBL_TELNO_ENCPT", dmSrvy.getValue("SRVY_RSPNS_RCPTN_MBL_TELNO_ENCPT"));

			} else if (dmSrvy.getValue("NO_INPT_SNDNG_YN").equals("Y")) {
				map.put("NO_INPT_SNDNG_YN", dmSrvy.getValue("NO_INPT_SNDNG_YN"));
				map.put("SRVY_RSPDNT_NM_ENCPT", map.get("RSPDNT")); // 설문응답자명암호화
				map.put("SRVY_RSPNS_RCPTN_MBL_TELNO_ENCPT", map.get("RSPDNT_RCPTN"));

			}

			// 예약발송여부
			if (dmSrvy.getValue("RSVT_SNDNG_YN").equals("Y")) {
				map.put("RSVT_SNDNG_YN", dmSrvy.getValue("RSVT_SNDNG_YN"));
				map.put("SNDNG_DT", dmSrvy.getValue("RSVT_MMDD") + dmSrvy.getValue("RSVT_HHSS"));

			} else if (dmSrvy.getValue("RSVT_SNDNG_YN").equals("N")) {
				map.put("RSVT_SNDNG_YN", dmSrvy.getValue("RSVT_SNDNG_YN"));
			}

			map.put("CHRCTR_CN", sendMsg);
			map.put("MSSAGE_ESNTAL_NO", map.get("MSG_SEQ"));
			map.put("SRVY_ERA_SE_CD", dmSrvy.getValue("SRVY_ERA_SE_CD"));

			// SBB600 설문발송이력 추가
			map.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);
//			map.put("INST_NO", sInstno);
			map.put("INST_NO", dmSearch.getValue("INST_NO"));

			// 5. SBB600 설문발송이력 insert
			srvyMngMapper.insertQustnbSndngHstr(map);

		}

	}

	/**
	 * @Method명 : updateQustnbSingle
	 * @param request
	 * @param dataRequest
	 * @param resultMap
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 11. 3.
	 * @Method설명 : 설문지 발송(단일)
	 */
	@Override
	public void updateQustnbSingle(HttpServletRequest request, DataRequest dataRequest, Map<String, Object> resultMap)
			throws Exception {

		ParameterGroup dsListParam2 = dataRequest.getParameterGroup("dsList2");
		ParameterGroup dmSrvy = dataRequest.getParameterGroup("dmSrvy");
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		List<Map<String, String>> getAllRowList2 = dsListParam2.getAllRowList();
		List<Map<String, String>> dsList = new ArrayList<Map<String, String>>();

		// insert 할 대상자(단일)
		for (int i = 0; i < getAllRowList2.size(); i++) {
			dsList.add(getAllRowList2.get(i));
		}

		// 로그인 한 유저 정보
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String untTaskwkSeCd = loginVO.getUntTaskwk(); // 단위업무구분코드
		String sUserId = loginVO.getId(); // 로그인 한 유저
		String sInstno = String.valueOf(loginVO.getInstNo()); // 발신기관번호
//		String sWrdTelno = loginVO.getWrdTelno(); // 유선전화번호
		String sWrdTelno = "";

		for (Map<String, String> map : dsList) {

			// 발신대표번호 조회
			map.put("INST_NO", sInstno);
			sWrdTelno = rsvtMngMapper.selectRprsTelno(map);

			map.put("QUSTNB_MNG_NO", (String) resultMap.get("QUSTNB_MNG_NO")); // 설문지관리번호
			map.put("FRST_RGTR_ID", sUserId);
			map.put("LAST_MDFR_ID", sUserId);

			// 3. SBB110 설문대상자 insert
			srvyMngMapper.insertQustnbTrprInfo(map);

			untTaskwkSeCd = srvyMngMapper.selectUntTaskwkSeCd(map);

			// 4. 문자발송
			String telnoReplace = sWrdTelno.replace("-", "");

			if (map.get("SRVY_RCPTN_MTHD_SE_CD").equals("3")) {

				map.put("MSG_TEMP", dmSrvy.getValue("CHRCTR_CN") + "\n[설문지작성]\n설문지 참여를 해주세요.\n");
				map.put("PATH", "/isry/itgcm/outsdsrvyptcptn/outsdSrvyPtcptnWrite.do");
				map.put("QUSTNB_MNG_NO", resultMap.get("QUSTNB_MNG_NO").toString());
				map.put("USER_ID", sUserId);
				map.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);
				map.put("TRPR_REL_SE_CD", map.get("TRPR_REL_CD"));
				map.put("SXDC_SE_CD", "X"); // 미확인

				sendMsg = outsdSrvyPtcptnService.getSendMsg(map);

				// System.err.println("로그로그 " + sendMsg);

				map.put("CONT_SEQ", map.get("CONT_SEQ"));
				map.put("RSVT_CHRCTR_CN", sendMsg);
				map.put("FRST_RGTR_ID", sUserId);
				map.put("LAST_MDFR_ID", sUserId);
				map.put("CALL_FROM", telnoReplace); // 발신휴대전화번호
				map.put("RSVT_MMDD", dmSrvy.getValue("RSVT_MMDD"));
				map.put("RSVT_HHSS", dmSrvy.getValue("RSVT_HHSS"));
				map.put("CALL_TO", dmSrvy.getValue("SRVY_RSPNS_RCPTN_MBL_TELNO_ENCPT")); // 응답자 수신번호

				// MMS 컨텐츠 정보 insert
				rsvtSmsMapper.insertMmsContentsInfo(map);
				// MMS 메세지 데이터 insert
				rsvtSms2Mapper.insertMsgData(map);
			}

			// 번호입력여부
			if (dmSrvy.getValue("NO_INPT_SNDNG_YN").equals("N")) {
				map.put("NO_INPT_SNDNG_YN", dmSrvy.getValue("NO_INPT_SNDNG_YN"));
				map.put("SRVY_RSPDNT_NM_ENCPT", dmSrvy.getValue("SRVY_RSPDNT_NM_ENCPT")); // 설문응답자명암호화
				map.put("SRVY_RSPNS_RCPTN_MBL_TELNO_ENCPT", dmSrvy.getValue("SRVY_RSPNS_RCPTN_MBL_TELNO_ENCPT"));

			} else if (dmSrvy.getValue("NO_INPT_SNDNG_YN").equals("Y")) {
				map.put("NO_INPT_SNDNG_YN", dmSrvy.getValue("NO_INPT_SNDNG_YN"));
				map.put("SRVY_RSPDNT_NM_ENCPT", map.get("RSPDNT")); // 설문응답자명암호화
				map.put("SRVY_RSPNS_RCPTN_MBL_TELNO_ENCPT", map.get("RSPDNT_RCPTN"));

			}

			// 예약발송여부
			if (dmSrvy.getValue("RSVT_SNDNG_YN").equals("Y")) {
				map.put("RSVT_SNDNG_YN", dmSrvy.getValue("RSVT_SNDNG_YN"));
				map.put("SNDNG_DT", dmSrvy.getValue("RSVT_MMDD") + dmSrvy.getValue("RSVT_HHSS"));

			} else if (dmSrvy.getValue("RSVT_SNDNG_YN").equals("N")) {
				map.put("RSVT_SNDNG_YN", dmSrvy.getValue("RSVT_SNDNG_YN"));
			}

			map.put("CHRCTR_CN", sendMsg);
			map.put("MSSAGE_ESNTAL_NO", map.get("MSG_SEQ"));
			map.put("SRVY_ERA_SE_CD", dmSrvy.getValue("SRVY_ERA_SE_CD"));

			// SBB600 설문발송이력 추가
			map.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);
//			map.put("INST_NO", sInstno);
			map.put("INST_NO", dmSearch.getValue("INST_NO"));

			// 5. SBB600 설문발송이력 insert
			srvyMngMapper.insertQustnbSndngHstr(map);

		}

	}

	/**
	 * @Method명 : selectQustnbList
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 24.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectQustnbList(Map<String, String> requestMap) throws Exception {
		
		return srvyMngMapper.selectQustnbList(requestMap);
	}

	/**
	 * @Method명 : selectQustnbQesitm
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 24.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectQustnbQesitm(Map<String, String> requestMap) throws Exception {

		return srvyMngMapper.selectQustnbQesitm(requestMap);
	}

	/**
	 * @Method명 : selectAddtng
	 * @param dmMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 12. 14.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> selectAddtng(Map<String, String> dmMap) {
		// TODO Auto-generated method stub
		return srvyMngMapper.selectAddtng(dmMap);
	}

}

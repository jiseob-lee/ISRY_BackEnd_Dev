/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mngrpage.aplcnttrprmng.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.google.common.collect.Maps;

import isry.csemd.mngrpage.aplcnttrprmng.mapper.AplcntTrprMngMapper;
import isry.csemd.mngrpage.aplcnttrprmng.service.AplcntTrprMngService;
import isry.itgcm.casemng.uneart.service.TrprInqService;
import isry.itgcm.outsdsrvyptcptn.service.OutsdSrvyPtcptnService;
import isry.itgcms.syscmmn.survsht.service.SurvshtMmnService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry2.csemd.mngrpage.aplcnttrprmng.mapper.AplcntTrprMng2Mapper;

/**
 * @파일명 : AplcntTrprMngServiceImpl.java
 * @프로그램 설명 : 신청대상자 관리[관리자페이지] Service Implement - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 9. 16.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 9. 16.
 * @수정내용 : - -
 */
@Service("aplcntTrprMngService__admin")
public class AplcntTrprMngServiceImpl implements AplcntTrprMngService {

	// 설문지 문자 SHOT CUT URL 생성 및 문자 내용 조합 Service Class
	@Resource(name = "outsdSrvyPtcptnService")
	private OutsdSrvyPtcptnService outsdSrvyPtcptnService;

	// 대상자정보 Service Class
	@Resource(name = "trprInqService")
	private TrprInqService trprInqService;

	// 신청대상자관리[관리자페이지] 관련 매퍼
	@Resource(name = "aplcntTrprMngMapper__admin")
	private AplcntTrprMngMapper aplcntTrprMngMapper;

	// 설문지 관리번호 생성 Service Class
	@Resource(name = "survshtMmnService")
	private SurvshtMmnService survshtMmnService;

	@Resource(name = "aplcntTrprMng2Mapper__admin")
	private AplcntTrprMng2Mapper aplcntTrprMng2Mapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	// ### 문자발송 정보
	String sendMsg = "테스트 문자발송 내용 입니다...... link 정보 필요.";

	/**
	 * @Method명 : selectEntscAplyList
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 9. 22.
	 * @Method설명 : 신청 대상자 목록조회
	 */
	@Override
	public void selectAplyList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = dmSearch.getAllRowList().get(0);

		// 파라미터맵에 단위업무구분코드 추가
		paramMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		// 입교신청목록조회
		dataRequest.setResponse("dsList", aplcntTrprMngMapper.selectAplyList(paramMap));
	}

	/**
	 * @Method명 : savePrgrsSttsListUpdate
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 9. 28.
	 * @Method설명 : 진행상태 저장(복수건)
	 */
	@Override
	public void savePrgrsSttsListUpdate(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		List<Map<String, String>> UpdatedRowList = dsList.getUpdatedRowList();
		for (Map<String, String> map : UpdatedRowList) {
			map.put("APLY_RCPT_SRNG_SE_CD", dataRequest.getParameter("param").substring(0, 2));
			map.put("APLY_RCPT_SRNG_PRGRS_STTS_SE_CD", dataRequest.getParameter("param"));
			map.put("FRST_RGTR_ID", userVo.getId());
			map.put("LAST_MDFR_ID", userVo.getId());
			if (dataRequest.getParameter("param").equals("0401"))
				map.put("INTRVW_PTCPTN_SE_CD", "01");

			String caseMngSeCd = dataRequest.getParameter("CASE_MNG_SE_CD");
			String caseTrprNoapCsSeCd = dataRequest.getParameter("CASE_TRPR_NOAP_CS_SE_CD");

			if (caseMngSeCd != null || caseTrprNoapCsSeCd != null) {
				map.put("CASE_MNG_SE_CD", caseMngSeCd);
				map.put("CASE_TRPR_NOAP_CS_SE_CD", caseTrprNoapCsSeCd);
				aplcntTrprMngMapper.updateCaseStts(map); // 진행단계에 따른 사례관리구분코드/사례대상자미신청사유구분코드 업데이트
				map.put("DATAA_CHG_SE_CD", "U");
				aplcntTrprMngMapper.insertTrprInqHistory(map); // 대상자정보이력테이블 insert
			}

			// AFA100(신청접수) 신청접수심사진행상태구분코드 수정
			aplcntTrprMngMapper.updatePrgrsStts(map);

			// ### 신청접수심사상태정보이력(AFA150) : insert/update
			updatePrgrsStts(map);

			// AFA100(신청접수) 조회
			map = aplcntTrprMngMapper.selectAplyTrprDtlInfo(map);

			// ### 신청접수이력(AFA101) : insert
			aplcntTrprMngMapper.insertAplyRcptHstr(map);
		}

	}

	/**
	 * @Method명 : saveAplyTrprDtlInfo
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :신청 대상자 정보 등록, 수정
	 */
	@Override
	public void saveAplyTrprDtlInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		// 대상자정보번호
		// String trprInfoNo = "";

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearch");
		ParameterGroup dmDetailParam = dataRequest.getParameterGroup("dmDetail");
		ParameterGroup dmAplCntDtlParam = dataRequest.getParameterGroup("dmAplCntDtl");

		// 재조회시 신청자 정보, 대상자정보번호(TR) 매핑을 위해 화면에 내려준다
		// Map<String, Object> message = new HashMap<String, Object>();

		// Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, String> saveMap = dmAplCntDtlParam.getSingleValueMap();

		// 1. 대상자, 보호자, 가족정보, 학력상태, 학력중단 정보 insert / 대상자 공통 로직 사용(대상자 상세저장(등록,수정,삭제,이력))
		// 2. 대상자정보번호 생성 리턴
		// Map<String, Object> retMap = trprInqService.processTrprInqDetail(request,
		// dataRequest);

		// trprInfoNo = retMap.get("TRPR_INFO_NO").toString();

		// saveMap.put("TRPR_INFO_NO", trprInfoNo); // 대상자정보번호

		saveMap.put("FRST_RGTR_ID", dmSearchParam.getValue("USER_ID")); // 최초등록자아이디
		saveMap.put("LAST_MDFR_ID", dmSearchParam.getValue("USER_ID")); // 최종수정자아이디

		// String chkRqstPicYn = saveMap.get("RQST_PIC_YN").toString();

		// 의뢰담당자 여부가 "N" 인경우 빈값으로 처리 혹은 수정시 Y > N 으로 변경했을시도 빈값처리
//		if (chkRqstPicYn.equals("N")) {
//			saveMap.put("RQST_INST_NO", null); // 의뢰기관번호
//			saveMap.put("RQST_PIC_NO", ""); // 의뢰담당자번호
//		}

		String sStatus = dmDetailParam.getValue("TYPE").toString();

		if (sStatus.equals("u") || sStatus.equals("U")) {

			// 수정처리
			if (saveMap.get("APLY_RCPT_SRNG_PRGRS_STTS_SE_CD").equals("0203"))
				saveMap.put("APLY_CN_MDFCN_PSBLTY_YN", "Y");
			// ### 신청자 정보(AFA100) : update

			// 대상자, 보호자, 가족정보, 학력상태, 학력중단 정보 insert / 대상자 공통 로직 사용(대상자 상세저장(등록,수정,삭제,이력))
			trprInqService.processTrprInqDetail(request, dataRequest);

			String chkRqstPicYn = saveMap.get("RQST_PIC_YN").toString();
			// 의뢰담당자 여부가 "N" 인경우 빈값으로 처리 혹은 수정시 Y > N 으로 변경했을시도 빈값처리
			if (chkRqstPicYn.equals("N")) {
				saveMap.put("RQST_INST_NM", ""); // 의뢰기관명
				saveMap.put("PIC_NM", ""); // 담당자명암호화
				saveMap.put("PIC_TELNO", ""); // 담당자전화번호암호화
				saveMap.put("PIC_MBL_TELNO", ""); // 담당자휴대전화번호암호화
				saveMap.put("PIC_EML_ADDR", ""); // 담당자이메일주소암호화

			}

			aplcntTrprMngMapper.updateAplyTrprDtlInfo(saveMap);

			// ### 신청접수심사상태정보이력(AFA150) : insert/update
			updatePrgrsStts(saveMap);

			// message.put("TRPR_INFO_NO", retMap.get("TRPR_INFO_NO"));
			// message.put("APLY_RCPT_SN", saveMap.get("APLY_RCPT_SN"));
			// dataRequest.setMetadata(true, message);

		} else if (sStatus.equals("d") || sStatus.equals("D")) {

			// 삭제처리

		}

		// ### 신청접수이력(AFA101) : insert
		aplcntTrprMngMapper.insertAplyRcptHstr(saveMap);

	}

	/**
	 * @Method명 : selectAplyTrprDtlInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :신청 대상자 정보 상세
	 */
	@Override
	public Map<String, String> selectAplyTrprDtlInfo(DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> reqMap = dmSearchParam.getSingleValueMap();

		Map<String, String> aplyTrprDtlInfo = aplcntTrprMngMapper.selectAplyTrprDtlInfo(reqMap);

		aplyTrprDtlInfo.put("APLY_RCPT_SRNG_SE_CD",
				aplyTrprDtlInfo.get("APLY_RCPT_SRNG_PRGRS_STTS_SE_CD").substring(0, 2));

		reqMap.put("CHK_APLY_RCPT_SRNG_SE_CD", "02");
		// ### 입교 신청, 접수 설문지 점수 입력 폼 > 평가 > 라디오 버튼 활성/비활성 용 선정, 미선정, 반송 상태 값
		Map<String, Object> selectQustnbScoreEvlSttsInfo = aplcntTrprMngMapper.selectQustnbScoreEvlSttsInfo(reqMap);

		if (selectQustnbScoreEvlSttsInfo != null) {

			String chkStr = selectQustnbScoreEvlSttsInfo.get("APLY_RCPT_SRNG_PRGRS_STTS_SE_CD").toString();

			if (chkStr.equals("0201")) {
				aplyTrprDtlInfo.put("QUSTNB_SCORE_EVL_STTS", "");
			} else if (chkStr.equals("0202")) {
				aplyTrprDtlInfo.put("QUSTNB_SCORE_EVL_STTS", "01");
			} else if (chkStr.equals("0203")) {
				aplyTrprDtlInfo.put("QUSTNB_SCORE_EVL_STTS", "02");
			}

		} else {
			aplyTrprDtlInfo.put("QUSTNB_SCORE_EVL_STTS", "");
		}

		// 첨부파일 갯수를 가져올 때 신청자 데이터 맵을 String/String 형식으로 바꿔주기위한 hashMap
		Map<String, String> countMap = new HashMap<String, String>();
		countMap.put("APLY_PAPERS_ATFINO", (String) aplyTrprDtlInfo.get("APLY_PAPERS_ATFINO"));
		dataRequest.setResponse("dmAtcmfl", aplcntTrprMngMapper.selectAplyPapersCount(countMap));

		String caseHstrCnt = aplcntTrprMngMapper.selectCaseHstrCnt(reqMap);
		if (caseHstrCnt != null) {
			aplyTrprDtlInfo.put("CASE_HSTR_CNT", caseHstrCnt);

		}

		return aplyTrprDtlInfo;
	}

	/**
	 * @Method명 : updateAplyCnMdfcnPsbltyYn
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :관리자(입교신청) > 수정권한(부여,회수)
	 */
	@Override
	public void updateAplyCnMdfcnPsbltyYn(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearch");
		ParameterGroup dmAplCntDtlParam = dataRequest.getParameterGroup("dmAplCntDtl");

		Map<String, String> updateMap = dmAplCntDtlParam.getSingleValueMap();

		updateMap.put("LAST_MDFR_ID", dmSearchParam.getValue("USER_ID")); // 최종수정자아이디

		// // ### 신청자 정보(AFA100) : update 수정권한(부여,회수) 업데이트
		aplcntTrprMngMapper.updateAplyCnMdfcnPsbltyYn(updateMap);

		// ### 신청접수이력(AFA101) : insert
		aplcntTrprMngMapper.insertAplyRcptHstr(updateMap);
	}

	/**
	 * @Method명 : prgrsSttsStageUpdate
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 14.
	 * @Method설명 :단계별 진행 상태 업데이트
	 */
	@Override
	public void prgrsSttsStageUpdate(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// ### 신청자 : 신청 취소, 신청 제출 2단계 제출
		// ### 관리자(입교신청) : 신청서 반송, 접수승인, 접수미승인, 접수포기
		// ### 관리자(입교접수) : 최종선정, 선정 예비자, 최종 미선정, 결정보류, 심사포기

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearch");
		ParameterGroup dmAplCntDtlParam = dataRequest.getParameterGroup("dmAplCntDtl");
		ParameterGroup dmDetail = dataRequest.getParameterGroup("dmDetail");

		// ### 최종승인/접수 미승인 등일 경우 대상자정보테이블도 update 해야함.
		if (dmDetail != null) {
			Map<String, String> updateMap = dmDetail.getSingleValueMap();
			updateMap.put("FRST_RGTR_ID", dmSearchParam.getValue("USER_ID"));
			updateMap.put("LAST_MDFR_ID", dmSearchParam.getValue("USER_ID"));
			aplcntTrprMngMapper.updateCaseStts(updateMap); // 진행단계에 따른 사례관리구분코드/사례대상자미신청사유구분코드 업데이트
			updateMap.put("DATAA_CHG_SE_CD", "U");
			aplcntTrprMngMapper.insertTrprInqHistory(updateMap); // 대상자정보이력테이블 insert
		}

		Map<String, String> updateMap = dmAplCntDtlParam.getSingleValueMap();

		updateMap.put("FRST_RGTR_ID", dmSearchParam.getValue("USER_ID")); // 최초등록자아이디
		updateMap.put("LAST_MDFR_ID", dmSearchParam.getValue("USER_ID")); // 최종수정자아이디
		if (updateMap.get("APLY_RCPT_SRNG_PRGRS_STTS_SE_CD").equals("0401"))
			updateMap.put("INTRVW_PTCPTN_SE_CD", "01");

		// aplcntTrprMngMapper.updateAplyTrprDtlInfo(updateMap);
		aplcntTrprMngMapper.updatePrgrsStts(updateMap); // AFA100 > 진행단계상태 업데이트

		// ### 신청접수심사상태정보이력(AFA150) : insert/update
		updatePrgrsStts(updateMap);

		// ### 신청접수이력(AFA101) : insert
		aplcntTrprMngMapper.insertAplyRcptHstr(updateMap);
	}

	/**
	 * @param saveMap
	 * @Method명 : updatePrgrsStts
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 11.
	 * @Method설명 : 단계별 진행 상태 업데이트
	 */
	public void updatePrgrsStts(Map<String, String> saveMap) {

		// APLY_RCPT_SRNG_SE_CD(신청접수심사구분코드) Key > 존재 여부 조회
		// 상태 대분류 코드가 없으면 대분류, 소분류 insert / 있으면 소분류 update

		int chkCount = aplcntTrprMngMapper.chkAplyRcptSrngPrgrsStts(saveMap);
		if (chkCount == 0) {
			aplcntTrprMngMapper.insertAplyRcptSrngPrgrsSttsInfoHstr(saveMap);
		} else if (chkCount > 0) {
			aplcntTrprMngMapper.updateAplyRcptSrngPrgrsSttsInfoHstr(saveMap);
		}

	}

	/**
	 * @Method명 : selectTrprProbmSttsHistb
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 20.
	 * @Method설명 :대상자문제상태내역 > AFA120 체크
	 */
	@Override
	public int selectTrprProbmSttsHistb(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = dmSearch.getAllRowList().get(0);

		return aplcntTrprMngMapper.selectTrprProbmSttsHistb(paramMap);
	}

	/**
	 * @Method명 : selectSrvyRspns
	 * @param request
	 * @param dataRequest
	 * @param selectQustnbSndngHstrList
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 25.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSrvyRspns(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearch");

		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("USER_ID", dmSearchParam.getSingleValueMap().get("USER_ID"));
		mapParam.put("TRPR_INFO_NO", dmSearchParam.getSingleValueMap().get("TRPR_INFO_NO"));
		mapParam.put("APLY_RCPT_SN", dmSearchParam.getSingleValueMap().get("APLY_RCPT_SN"));
		mapParam.put("INST_NO", dmSearchParam.getSingleValueMap().get("INST_NO"));
		mapParam.put("UNT_TASKWK_SE_CD", dmSearchParam.getSingleValueMap().get("UNT_TASKWK_SE_CD"));

		List<Map<String, Object>> selectSrvyRspnsList = aplcntTrprMngMapper.selectSrvyRspnsList(mapParam);

		if (selectSrvyRspnsList == null) {
			return null;
		}

		List<String> mssageEsntalNoList = new ArrayList<>();

		Map<String, Map<String, String>> smsMap = new HashMap<>();

		for (int i = 0; i < selectSrvyRspnsList.size(); i++) {

			if (i > 0 && i % 1000 == 0) {

				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("mssageEsntalNoList", mssageEsntalNoList);
				List<Map<String, String>> smsData = aplcntTrprMng2Mapper.selectSmsList(paramMap);

				if (smsData != null && smsData.size() > 0) {
					for (int j = 0; j < smsData.size(); j++) {
						Map<String, String> smsMap2 = smsData.get(j);
						smsMap.put(smsMap2.get("MSG_SEQ"), smsMap2);
					}
				}

				mssageEsntalNoList.clear();
			}

			if (selectSrvyRspnsList.get(i).get("MSSAGE_ESNTAL_NO") != null
					&& !"".equals(selectSrvyRspnsList.get(i).get("MSSAGE_ESNTAL_NO"))) {
				mssageEsntalNoList.add(String.valueOf(selectSrvyRspnsList.get(i).get("MSSAGE_ESNTAL_NO")));
			}
		}

		if (mssageEsntalNoList.size() > 0) {
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("mssageEsntalNoList", mssageEsntalNoList);
			List<Map<String, String>> smsData = aplcntTrprMng2Mapper.selectSmsList(paramMap);

			if (smsData != null && smsData.size() > 0) {
				for (int j = 0; j < smsData.size(); j++) {
					Map<String, String> smsMap2 = smsData.get(j);
					smsMap.put(String.valueOf(smsMap2.get("MSG_SEQ")), smsMap2);
				}
			}
		}

		for (int i = 0; i < selectSrvyRspnsList.size(); i++) {

			String msgSeq = String.valueOf(selectSrvyRspnsList.get(i).get("MSSAGE_ESNTAL_NO"));
			String sndngSttsSeCd = "";
			if (smsMap.get(msgSeq) != null) {
				switch (String.valueOf(smsMap.get(msgSeq).get("CUR_STATE"))) {
				case "0":
					sndngSttsSeCd = "00";
					break;
				case "1":
					sndngSttsSeCd = "01";
					break;
				case "2":
					sndngSttsSeCd = "01";
					break;
				case "3":
					if ("100".equals(String.valueOf(smsMap.get(msgSeq).get("RSLT_CODE")))) {
						sndngSttsSeCd = "03";
					} else {
						sndngSttsSeCd = "02";
					}
					break;
				default:
					sndngSttsSeCd = "";
					break;
				}
			}
			/*
			 * CASE C.CUR_STATE WHEN 0 THEN '00' WHEN 1 THEN '01' WHEN 2 THEN '01' WHEN 3
			 * THEN CASE C.RSLT_CODE WHEN 100 THEN '03' ELSE '02' END END AS
			 * SNDNG_STTS_SE_CD
			 */
			selectSrvyRspnsList.get(i).put("SNDNG_STTS_SE_CD", sndngSttsSeCd);
			selectSrvyRspnsList.get(i).put("SENT_DATE",
					smsMap.get(msgSeq) == null ? "" : smsMap.get(msgSeq).get("SENT_DATE"));
		}

		return selectSrvyRspnsList;
	}

	/**
	 * @Method명 : reSndngQustnb
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 10. 26.
	 * @Method설명 :설문지 재발송
	 */
	@Override
	public void reSndngQustnb(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearch");
		ParameterGroup dmAplCntDtlParam = dataRequest.getParameterGroup("dmAplCntDtl");

		Map<String, Object> userParam = new HashMap<String, Object>();
		userParam.put("USER_ID", dmSearchParam.getSingleValueMap().get("USER_ID"));
		userParam.put("TRPR_INFO_NO", dmSearchParam.getSingleValueMap().get("TRPR_INFO_NO"));
		userParam.put("APLY_RCPT_SN", dmSearchParam.getSingleValueMap().get("APLY_RCPT_SN"));
		userParam.put("INST_NO", dmSearchParam.getSingleValueMap().get("INST_NO"));
		userParam.put("UNT_TASKWK_SE_CD", dmSearchParam.getSingleValueMap().get("UNT_TASKWK_SE_CD"));
		userParam.put("TRPR_REL_SE_CD", dmSearchParam.getSingleValueMap().get("TRPR_REL_SE_CD")); // 대상자관계구분코드
		userParam.put("SXDC_SE_CD", dmSearchParam.getSingleValueMap().get("SXDC_SE_CD"));
		// userParam.put("MNGR_YN", dmSearchParam.getSingleValueMap().get("MNGR_YN"));

		// userParam = Maps.newHashMap(dmSearchParam.getSingleValueMap());

		// ### 로그인 유저 정보
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String sUserId = loginVO.getId(); // 아이디
		String sUserNm = loginVO.getUserName(); // 이름
		String sInstNo = userParam.get("INST_NO").toString(); // 기관번호
		String sRprsTelno = ""; // 기관대표전화번호

		Map<String, String> param = new HashMap<String, String>();
		Map<String, Object> param2 = new HashMap<String, Object>();

		param2.put("QUSTNB_TMPT_MNG_NO", dmSearchParam.getSingleValueMap().get("QUSTNB_TMPT_MNG_NO").toString()); // 설문지템플릿관리번호

		String qustnbUntTaskwkSeCd = aplcntTrprMngMapper.selectQustnbUntTaskwkSeCd(param2);
		userParam.replace("UNT_TASKWK_SE_CD", qustnbUntTaskwkSeCd);

		// 20230619 이승재 - 재발송 시 설문지를 새로 만들어서 전송.
		Map<String, Object> searchMap = new HashMap<>();
		searchMap.put("QUSTNB_TMPT_MNG_NO", dmSearchParam.getSingleValueMap().get("QUSTNB_TMPT_MNG_NO")); // 설문지템플릿관리번호
		searchMap.put("UNT_TASKWK_SE_CD", dmSearchParam.getSingleValueMap().get("UNT_TASKWK_SE_CD")); // 설문지템플릿관리번호

		param.put("QUSTNB_MNG_NO", String
				.valueOf(survshtMmnService.processSurvshtTmptData(request, dataRequest, searchMap).get("QUSTNB_MNG_NO")));

//		param.put("QUSTNB_MNG_NO", dmSearchParam.getSingleValueMap().get("QUSTNB_MNG_NO").toString()); // 설문지관리번호
		// 위의 내용이 재발송 설문지 새로 만들어 전송 부분임.
		param.put("QUSTNB_TMPT_MNG_NO", dmSearchParam.getSingleValueMap().get("QUSTNB_TMPT_MNG_NO").toString()); // 설문지템플릿관리번호

		// 사례관리번호
		// 사례관리차수
		param.put("TRPR_INFO_NO", userParam.get("TRPR_INFO_NO").toString()); // 대상자정보번호
		param.put("APLY_RCPT_SN", userParam.get("APLY_RCPT_SN").toString()); // 신청접수일련번호
		// 사례대상자명암호화
		// 종사자번호
		param.put("TRPR_REL_SE_CD", userParam.get("TRPR_REL_SE_CD").toString()); // 대상자관계구분코드
		param.put("FRST_RGTR_ID", userParam.get("USER_ID").toString()); // 최초등록자아이디
		param.put("LAST_MDFR_ID", userParam.get("USER_ID").toString()); // 최종수정자아이디

		// 3. SBB110 설문대상자 insert 20230619 - 이승재 설문발송 안됐을 경우 sbb110에도 데이터 입력위해 추가
		aplcntTrprMngMapper.insertQustnbTrprInfo(param);

		String sendMsg = "";
		param.put("SRVY_RCPTN_MTHD_SE_CD", dmSearchParam.getValue("SRVY_RCPTN_MTHD_SE_CD"));

		// 문자발송
		if (dmSearchParam.getValue("SRVY_RCPTN_MTHD_SE_CD").equals("3")) {
			Map<String, String> outsdSrvyPtcptnParam = new HashMap<String, String>();
			outsdSrvyPtcptnParam.put("MSG_TEMP", "[설문지작성]\n설문지 참여를 해주세요.\n");
			outsdSrvyPtcptnParam.put("PATH", "/isry/itgcm/outsdsrvyptcptn/outsdSrvyPtcptnWrite.do");
			outsdSrvyPtcptnParam.put("QUSTNB_MNG_NO", param.get("QUSTNB_MNG_NO").toString());
			outsdSrvyPtcptnParam.put("USER_ID", userParam.get("USER_ID").toString());
			outsdSrvyPtcptnParam.put("UNT_TASKWK_SE_CD", userParam.get("UNT_TASKWK_SE_CD").toString());
			outsdSrvyPtcptnParam.put("SXDC_SE_CD", userParam.get("SXDC_SE_CD").toString());
			// outsdSrvyPtcptnParam.put("MNGR_YN", userParam.get("MNGR_YN").toString());

			Map<String, String> dmSearchParamMap = dmSearchParam.getSingleValueMap();
			sRprsTelno = aplcntTrprMngMapper.selectRprsTelno(dmSearchParamMap);

			sendMsg = outsdSrvyPtcptnService.getSendMsg(outsdSrvyPtcptnParam);
			log.debug("### 설문재발송문자 = " + sendMsg);

			param.put("RSVT_CHRCTR_CN", sendMsg); // 문자발송 내용

			param.put("TRPR_REL_SE_CD", dmSearchParam.getValue("TRPR_REL_SE_CD"));
			param.put("RCPTN_MBL_TELNO", dmSearchParam.getValue("RCPTN_MBL_TELNO"));

			aplcntTrprMng2Mapper.insertQustnbMmsContentsInfo(param);

			param.put("CONT_SEQ", param.get("CONT_SEQ")); // MMS 컨텐츠 키
			param.put("FRST_RGTR_ID", sUserId);
			param.put("LAST_MDFR_ID", sUserId);
			param.put("CALL_FROM", sRprsTelno.replace("-", "")); // 발신휴대전화번호
			param.put("TRNSMI_INST_NO", String.valueOf(sInstNo)); // 송신기관번호

			aplcntTrprMng2Mapper.insertQustnbMsgData(param);

			param.put("MSG_SEQ", param.get("MSG_SEQ")); // 메세지 고유번호
			param.put("RSVT_SNDNG_YN", "N"); // 예약발송여부
		} else {
			sendMsg = "";
		}

		// 설문발송에서는 SAB980 사용 안함
		// aplcntTrprMngMapper.insertQustnbNtcnSnsSndng(param);

		// ### SBB600 설문발송이력 param 추가
		param.put("INST_NO", userParam.get("INST_NO").toString()); // 기관번호
		param.put("UNT_TASKWK_SE_CD", userParam.get("UNT_TASKWK_SE_CD").toString()); // 단위업무구분코드
		param.put("SRVC_EXCN_BIZ_NO", dmAplCntDtlParam.getSingleValueMap().get("SRVC_EXCN_BIZ_NO").toString()); // 서비스실행사업번호
		param.put("SRVY_ERA_SE_CD", "01"); // 설문시기구분코드(사전:01, 사후:02)

		if (dmAplCntDtlParam.getSingleValueMap().get("RQST_PIC_YN").equals("Y")) {
			// 담당자
			param.put("NO_INPT_SNDNG_YN", "N"); // 번호입력발송여부
		} else {
			param.put("NO_INPT_SNDNG_YN", "Y"); // 번호입력발송여부
		}

		param.put("SRVY_RSPDNT_NM_ENCPT", dmSearchParam.getSingleValueMap().get("RCPTN_TRPR").toString()); // 설문응답자명암호화
		param.put("SRVY_RSPNS_RCPTN_MBL_TELNO_ENCPT",
				dmSearchParam.getSingleValueMap().get("RCPTN_MBL_TELNO").toString()); // 설문응답수신휴대전화번호암호화
		param.put("RSVT_SNDNG_YN", "N"); // 예약발송여부
		param.put("CHRCTR_CN", sendMsg); // 문자내용
		param.put("MSSAGE_ESNTAL_NO", param.get("MSG_SEQ")); // 메세지고유번호

		// 5. SBB600 설문발송이력 insert
		Map<String, Object> newParam = Maps.newHashMap(param);
		aplcntTrprMngMapper.insertQustnbSndngHstr(newParam);

	}

	/**
	 * @Method명 : updateMultiFileUpload
	 * @param request
	 * @param dataRequest
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 28.
	 * @Method설명 : 멀티파일 업로드
	 */
	@Override
	public void updateMultiFileUpload(HttpServletRequest request, DataRequest dataRequest) {
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmAplCntDtl");
		Map<String, String> paramMap = parameterGroup.getSingleValueMap();

		paramMap.put("LAST_MDFR_ID", loginVO.getId());

		aplcntTrprMngMapper.updateMultiFileUpload(paramMap);
	}

	/**
	 * @Method명 : selectAplyPapersCount
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 10. 21.
	 * @Method설명 : 첨부파일 갯수 조회
	 */
	@Override
	public void selectAplyPapersCount(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmAplCntDtl");
		Map<String, String> paramMap = parameterGroup.getAllRowList().get(0);

		Map<String, Object> fileCnt = aplcntTrprMngMapper.selectAplyPapersCount(paramMap);
		dataRequest.setResponse("dmAtcmfl", fileCnt);
	}

	/**
	 * @Method명 : selectPapersScrennList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 2.
	 * @Method설명 : 서류스크리닝 조회
	 */
	@Override
	public List<Map<String, Object>> selectPapersScrennList(HttpServletRequest request, DataRequest dataRequest) {
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paraMap = dmSearch.getSingleValueMap();

		List<Map<String, Object>> returnMap = aplcntTrprMngMapper.selectPapersScrennList(paraMap);

		for (Map<String, Object> map : returnMap) {
			// 법률 문제
			String lgsltnProbm = "";
			// 보호관찰
			if (map.get("PRTCTN_OBSERV_YN").equals("Y")) {
				lgsltnProbm = lgsltnProbm + "보호관찰";
			}
			// 재판진행
			if (map.get("TRIAL_PRGRS_YN").equals("Y")) {
				if (lgsltnProbm.equals(""))
					lgsltnProbm = lgsltnProbm + "재판진행";
				else
					lgsltnProbm = lgsltnProbm + "\n재판진행";
			}
			// 학폭위회부
			if (map.get("SCHL_VIOLNC_YN").equals("Y")) {
				if (lgsltnProbm.equals(""))
					lgsltnProbm = lgsltnProbm + "학폭위회부";
				else
					lgsltnProbm = lgsltnProbm + "\n학폭위회부";
			}
			// 경찰조사중
			if (map.get("POLC_EXMN_YN").equals("Y")) {
				if (lgsltnProbm.equals(""))
					lgsltnProbm = lgsltnProbm + "경찰조사중";
				else
					lgsltnProbm = lgsltnProbm + "\n경찰조사중";
			}
			// 해당없음
			if (map.get("RLVT_NAPC_YN").equals("Y")) {
				if (lgsltnProbm.equals(""))
					lgsltnProbm = lgsltnProbm + "해당없음";
				else
					lgsltnProbm = lgsltnProbm + "\n해당없음";
			}

			map.put("LGSLTN_PROBM", lgsltnProbm);

			// 공동체 문제
			String collabGrpProbm = "";
			// 알러지
			if (map.get("ALLERGY_YN").equals("Y")) {
				collabGrpProbm = collabGrpProbm + "알러지";
			}
			// 약물복용
			if (map.get("DRFSTF_TAKNG_YN").equals("Y")) {
				if (collabGrpProbm.equals(""))
					collabGrpProbm = collabGrpProbm + "약물복용";
				else
					collabGrpProbm = collabGrpProbm + "\n약물복용";
			}
			// 도움필요
			if (map.get("HELP_NEED_YN").equals("N")) {
				if (collabGrpProbm.equals(""))
					collabGrpProbm = collabGrpProbm + "도움필요";
				else
					collabGrpProbm = collabGrpProbm + "\n도움필요";
			}

			map.put("COLLAB_GRP_PROBM", collabGrpProbm);
		}

		return returnMap;
	}

	/**
	 * @Method명 : updateTrprPhotoAtfino
	 * @param requestMap
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 28.
	 * @Method설명 : 대상자사진첨부파일번호 저장
	 */
	@Override
	public void updateTrprPhotoAtfino(Map<String, String> requestMap) {

		aplcntTrprMngMapper.updateTrprPhotoAtfino(requestMap);
	}

	/**
	 * @Method명 : getIntrvwInfo
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 11. 29.
	 * @Method설명 :
	 */
	@Override
	public Map<String, String> getIntrvwInfo(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearch");

		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("USER_ID", dmSearchParam.getSingleValueMap().get("USER_ID"));
		mapParam.put("TRPR_INFO_NO", dmSearchParam.getSingleValueMap().get("TRPR_INFO_NO"));
		mapParam.put("APLY_RCPT_SN", dmSearchParam.getSingleValueMap().get("APLY_RCPT_SN"));
		mapParam.put("INST_NO", dmSearchParam.getSingleValueMap().get("INST_NO"));
		mapParam.put("UNT_TASKWK_SE_CD", dmSearchParam.getSingleValueMap().get("UNT_TASKWK_SE_CD"));

		Map<String, String> map = aplcntTrprMngMapper.getIntrvwInfo(mapParam);

		Map<String, String> resultMap = new HashMap<String, String>();

		if (map != null && !map.isEmpty()) {

			resultMap.put("INTRVW_YMD", map.get("RSVT_BGNG_YMD"));
			resultMap.put("INTRVW_HR", map.get("RSVT_BGNG_HR"));

		} else {
			resultMap.put("INTRVW_YMD", "");
			resultMap.put("INTRVW_HR", "");

		}

		return resultMap;
	}

}

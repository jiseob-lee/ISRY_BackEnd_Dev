/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mngrpage.aplcnttrprmng.web;

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
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.csemd.cmmn.service.CsemdService;
import isry.csemd.mngrpage.aplcnttrprmng.service.AplcntTrprMngService;
import isry.csems.mngrpage.aplcnttrprdtlinfomng.service.AplcntTrprDtlInfoMngService;
import isry.itgcm.casemng.uneart.service.TrprInqService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : AplcntTrprMngController.java
 * @프로그램 설명 : 신청대상자 관리[관리자페이지] Controller - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 9. 16.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 9. 16.
 * @수정내용 : - -
 */
@Controller("aplcntTrprMngController__admin")
@RequestMapping(value = "/isry/csemd/mngrpage/aplcnttrprmng")
public class AplcntTrprMngController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	// 신청대상자관리[관리자페이지] 관련 서비스
	@Resource(name = "aplcntTrprMngService__admin")
	private AplcntTrprMngService aplcntTrprMngService;

	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	// 대상자정보 Service Class
	@Resource(name = "trprInqService")
	private TrprInqService trprInqService;

	// 드림&디딤 콤보데이터 조회 서비스
	@Resource(name = "csemdService")
	private CsemdService csemdService;

	// 드림마을(관리자페이지) 관련 서비스
	@Resource(name = "csemsMngrPageAplcntTrprDtlInfoMngService")
	private AplcntTrprDtlInfoMngService csemsAplcntTrprDtlInfoMngService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectEntscAplyCmb
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 드림/디딤 신청목록 콤보데이터 조회
	 */
	@RequestMapping(value = "/selectAplyCmb.do")
	public View selectAplyCmb(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		// 신청접수심사구분코드
		List<Map<String, Object>> dsAplyRctpSrngSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("APLY_RCPT_SRNG_SE_CD",
				loginVO.getUntTaskwk());
		// 신청접수심사진행상태구분코드
		List<Map<String, Object>> dsAplyRctpSrngPrgrsSttsSeCd = mgmtCmmnCodeService
				.selectCommonCodeUnit("APLY_RCPT_SRNG_PRGRS_STTS_SE_CD", loginVO.getUntTaskwk());
		// 성별구분코드
		List<Map<String, Object>> dsSxdcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD",
				loginVO.getUntTaskwk());
		// 면접참여방법구분코드
		List<Map<String, Object>> dsIntrvwPtcptnMthdSeCd = mgmtCmmnCodeService
				.selectCommonCodeUnit("INTRVW_PTCPTN_MTHD_SE_CD", loginVO.getUntTaskwk());
		// 면접참여구분코드
		List<Map<String, Object>> dsIntrvwPtcptnSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("INTRVW_PTCPTN_SE_CD",
				loginVO.getUntTaskwk());
		// 점수범위조회조건구분코드
		List<Map<String, Object>> dsScoreScpInqCndSeCd = mgmtCmmnCodeService
				.selectCommonCodeUnit("SCORE_SCP_INQ_CND_SE_CD", loginVO.getUntTaskwk());
		List<Map<String, Object>> dsAcbgSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("ACBG_SE_CD",
				loginVO.getUntTaskwk());

		dataRequest.setResponse("dsAplyRctpSrngSeCd", dsAplyRctpSrngSeCd);
		dataRequest.setResponse("dsAplyRctpSrngPrgrsSttsSeCd", dsAplyRctpSrngPrgrsSttsSeCd);
		dataRequest.setResponse("dsSxdcSeCd", dsSxdcSeCd);
		dataRequest.setResponse("dsIntrvwPtcptnMthdSeCd", dsIntrvwPtcptnMthdSeCd);
		dataRequest.setResponse("dsIntrvwPtcptnSeCd", dsIntrvwPtcptnSeCd);
		dataRequest.setResponse("dsScoreScpInqCndSeCd", dsScoreScpInqCndSeCd);
		dataRequest.setResponse("dsAcbgSeCd", dsAcbgSeCd);

		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		requestMap.put("INST_NO", loginVO.getInstNo().toString());
		requestMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());

		// 사업연도
		List<Map<String, Object>> bizYrList = csemdService.selectBizYrCmb(requestMap);
		// 기관정보
		List<Map<String, Object>> instList = csemdService.selectInstCmb(requestMap);
		// 서비스실행사업정보(과정)
		List<Map<String, Object>> srvcExcnBizList = csemdService.selectSrvcExcnBizCmb(requestMap);

		dataRequest.setResponse("dsBizYr", bizYrList);
		dataRequest.setResponse("dsEduInstCmb", instList);
		dataRequest.setResponse("dsSrvcExcnBizCmb", srvcExcnBizList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectAplyList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 9. 24.
	 * @Method설명 : 접수 대상자 목록조회
	 */
	@RequestMapping(value = "/selectAplyList.do")
	public View selectAplyList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		aplcntTrprMngService.selectAplyList(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : savePrgrsSttsListUpdate
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 9. 27.
	 * @Method설명 : 진행상태 저장(복수건)
	 */
	@RequestMapping(value = "/savePrgrsSttsListUpdate.do")
	public View saveLastResult(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		aplcntTrprMngService.savePrgrsSttsListUpdate(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method : onLoadAplCntDtlInitInfo
	 * @Method설명 : 신청자 등록/상세 OnLoad
	 * @param : request
	 * @param : response
	 * @return : dataRequest
	 * @exception : Exception
	 * @작성자 :
	 * @작성일 : ****************************** 공통코드 조회 조건 (dsCodeParam) 1.CMMNS_CD_ID
	 *      : 공통코드아이디 (필수) - ex) SRVC_RESRCE_LCLAS_SE_CD 2.DS_SET_NM : RETURN 데이터셋
	 *      (필수) - ex) dsSrvcResrceLclasSeCd 3.CMMNS_CD_VALUE : 공통코드값
	 *      4.CMMNS_CD_VALUE_NM : 공통코드값명 5.ADDTNG_MNG_VALUE1 : 추가관리값1
	 *      6.ADDTNG_MNG_VALUE2 : 추가관리값2 7.ADDTNG_MNG_VALUE3 : 추가관리값3
	 *      8.ADDTNG_MNG_VALUE4 : 추가관리값4 9.ADDTNG_MNG_VALUE5 : 추가관리값5 10.USE_YN :
	 *      사용여부
	 */
	@RequestMapping(value = "/onLoadAplCntDtlInitInfo.do")
	public View onLoadAplCntDtlInitInfo(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		String codeId = ""; // 공통코드 아이디
		String dataSetNm = ""; // 데이터셋
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		log.debug("selectAplCntInfoOnLoad.paraGroup=[" + paramGroup + "]");

		if (paramGroup != null) {

			List<Map<String, String>> paramList = paramGroup.getAllRowList();

			for (Map<String, String> rowMap : paramList) {

				dataSetNm = String.valueOf(rowMap.get("DS_SET_NM")); // 응답 데이터셋
				codeId = String.valueOf(rowMap.get("CMMNS_CD_ID")); // 요청 공통코드 아이디
				log.debug("selectAplCntInfoOnLoad.dataSetNm=[" + dataSetNm + "]");
				log.debug("selectAplCntInfoOnLoad.codeId=[" + codeId + "]");
				// 시스템 공통코드 조회 서비스 요청
				List<Map<String, Object>> list = mgmtCmmnCodeService.selectCommonCodeUnit(codeId,
						userVo.getUntTaskwk());
				// 응답객체 셋팅
				dataRequest.setResponse(dataSetNm, list);
			}
		}

		ParameterGroup dmSearchParam = dataRequest.getParameterGroup("dmSearch");

		// 요청
		Map<String, String> requestMap = new HashMap<String, String>();

		requestMap.put("USER_ID", dmSearchParam.getValue("USER_ID"));
		requestMap.put("INST_NO", dmSearchParam.getValue("INST_NO"));
		requestMap.put("UNT_TASKWK_SE_CD", dmSearchParam.getValue("UNT_TASKWK_SE_CD"));
		requestMap.put("ENFSN_NO", dmSearchParam.getValue("ENFSN_NO"));
		requestMap.put("INST_TYPE_SE_CD", userVo.getInstTypeSeCd());

		// 자원제공주체(교육기관) select box list
		List<Map<String, Object>> selectEduInstCmbList = csemdService.selectInstCmb(requestMap);

		dataRequest.setResponse("dsEduInstCmb", selectEduInstCmbList);

		// 과정(서비스실행사업) select box list
		List<Map<String, Object>> selectRsfrSrvcCrseCmbList = csemdService.selectSrvcExcnBizCmb(requestMap);

		dataRequest.setResponse("dsRsfrSrvcCrseCmb", selectRsfrSrvcCrseCmbList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : saveAplyTrprDtlInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 9. 22.
	 * @Method설명 : 신청 대상자 정보 등록, 수정
	 */
	@RequestMapping(value = "/saveAplyTrprDtlInfo.do")
	public View saveAplyTrprDtlInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		aplcntTrprMngService.saveAplyTrprDtlInfo(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectAplyTrprDtlInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 9. 22.
	 * @Method설명 : 신청 대상자 정보 상세
	 */
	@RequestMapping(value = "/selectAplyTrprDtlInfo.do")
	public View selectAplyTrprDtlInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		// 대상자 상세조회
		Map<String, Object> retMap = trprInqService.selectTrprInqDetail(dataRequest);
		dataRequest.setResponse("dmDetail", retMap.get("dmDetail"));

		// 신청자 정보 상세
		Map<String, String> aplCntDtlMap = aplcntTrprMngService.selectAplyTrprDtlInfo(dataRequest);
		dataRequest.setResponse("dmAplCntDtl", aplCntDtlMap);

		// 대상자문제상태내역 작성여부 체크
		Map<String, Object> docWrtSttsYnMap = new HashMap<String, Object>();
		int chkCount = aplcntTrprMngService.selectTrprProbmSttsHistb(request, dataRequest);
		if (chkCount == 0) {
			docWrtSttsYnMap.put("DOC_WRT_STTS_SE_CD", "01"); // 미작성
		} else if (chkCount > 0) {
			docWrtSttsYnMap.put("DOC_WRT_STTS_SE_CD", "02"); // 작성
		}

		// 드림마을 참가자동의서 작성여부 체크
		chkCount = csemsAplcntTrprDtlInfoMngService.selectAdhrncWrtcnsChck(request, dataRequest);

		if (chkCount == 0) {
			docWrtSttsYnMap.put("ADHRNC_WRTCNS_YN", "01"); // 미작성
		} else if (chkCount > 0) {
			docWrtSttsYnMap.put("ADHRNC_WRTCNS_YN", "02"); // 작성
		}

		dataRequest.setResponse("dmDocWrtSttsSeCd", docWrtSttsYnMap);

		// 설문발송이력 조회
		// 설문이력, 설문응답 조회리스트
		List<Map<String, Object>> selectQustnbMngNoList = aplcntTrprMngService.selectSrvyRspns(request, dataRequest);

		dataRequest.setResponse("dsQustnbMngNoList", selectQustnbMngNoList);

		Map<String, Object> srvyRspnsMap = new HashMap<String, Object>();

		for (int i = 0; i < selectQustnbMngNoList.size(); i++) {

			if (selectQustnbMngNoList.get(i).get("QUSTNB_TMPT_MNG_NO").equals("TM2022100600001")) {

				srvyRspnsMap.put("TRPR_QUSTNB_KND_SE_CD", selectQustnbMngNoList.get(i).get("QUSTNB_KND_SE_CD"));
				srvyRspnsMap.put("TRPR_QUSTNB_KND_SE_CD_NM", selectQustnbMngNoList.get(i).get("QUSTNB_KND_SE_CD_NM"));
				srvyRspnsMap.put("TRPR_SRVY_WRT_STTS_SE_CD", selectQustnbMngNoList.get(i).get("SRVY_WRT_STTS_SE_CD"));
				srvyRspnsMap.put("TRPR_QUSTNB_SMS_SNDNG_STTS_SE_CD",
						selectQustnbMngNoList.get(i).get("SNDNG_STTS_SE_CD"));
				srvyRspnsMap.put("TRPR_QUSTNB_SMS_SENT_DATE", selectQustnbMngNoList.get(i).get("SENT_DATE"));
				srvyRspnsMap.put("TRPR_SRVY_WRT_DT", selectQustnbMngNoList.get(i).get("RSPNS_DT"));

			} else if (selectQustnbMngNoList.get(i).get("QUSTNB_TMPT_MNG_NO").equals("TM2022100400002")) {

				srvyRspnsMap.put("PRTCR_QUSTNB_KND_SE_CD", selectQustnbMngNoList.get(i).get("QUSTNB_KND_SE_CD"));
				srvyRspnsMap.put("PRTCR_QUSTNB_KND_SE_CD_NM", selectQustnbMngNoList.get(i).get("QUSTNB_KND_SE_CD_NM"));
				srvyRspnsMap.put("PRTCR_SRVY_WRT_STTS_SE_CD", selectQustnbMngNoList.get(i).get("SRVY_WRT_STTS_SE_CD"));
				srvyRspnsMap.put("PRTCR_QUSTNB_SMS_SNDNG_STTS_SE_CD",
						selectQustnbMngNoList.get(i).get("SNDNG_STTS_SE_CD"));
				srvyRspnsMap.put("PRTCR_QUSTNB_SMS_SENT_DATE", selectQustnbMngNoList.get(i).get("SENT_DATE"));
				srvyRspnsMap.put("PRTCR_SRVY_WRT_DT", selectQustnbMngNoList.get(i).get("RSPNS_DT"));

			} else if (selectQustnbMngNoList.get(i).get("QUSTNB_TMPT_MNG_NO").equals("TM2022100400007")) {

				srvyRspnsMap.put("PRTCR_EVL_QUSTNB_KND_SE_CD", selectQustnbMngNoList.get(i).get("QUSTNB_KND_SE_CD"));
				srvyRspnsMap.put("PRTCR_EVL_QUSTNB_KND_SE_CD_NM",
						selectQustnbMngNoList.get(i).get("QUSTNB_KND_SE_CD_NM"));
				srvyRspnsMap.put("PRTCR_EVL_SRVY_WRT_STTS_SE_CD",
						selectQustnbMngNoList.get(i).get("SRVY_WRT_STTS_SE_CD"));
				srvyRspnsMap.put("PRTCR_EVL_QUSTNB_SMS_SNDNG_STTS_SE_CD",
						selectQustnbMngNoList.get(i).get("SNDNG_STTS_SE_CD"));
				srvyRspnsMap.put("PRTCR_EVL_QUSTNB_SMS_SENT_DATE", selectQustnbMngNoList.get(i).get("SENT_DATE"));
				srvyRspnsMap.put("PRTCR_EVL_SRVY_WRT_DT", selectQustnbMngNoList.get(i).get("RSPNS_DT"));

			} else if (selectQustnbMngNoList.get(i).get("QUSTNB_TMPT_MNG_NO").equals("TM2022100500001")) {

				srvyRspnsMap.put("PIC_QUSTNB_KND_SE_CD", selectQustnbMngNoList.get(i).get("QUSTNB_KND_SE_CD"));
				srvyRspnsMap.put("PIC_QUSTNB_KND_SE_CD_NM", selectQustnbMngNoList.get(i).get("QUSTNB_KND_SE_CD_NM"));
				srvyRspnsMap.put("PIC_SRVY_WRT_STTS_SE_CD", selectQustnbMngNoList.get(i).get("SRVY_WRT_STTS_SE_CD"));
				srvyRspnsMap.put("PIC_QUSTNB_SMS_SNDNG_STTS_SE_CD",
						selectQustnbMngNoList.get(i).get("SNDNG_STTS_SE_CD"));
				srvyRspnsMap.put("PIC_QUSTNB_SMS_SENT_DATE", selectQustnbMngNoList.get(i).get("SENT_DATE"));
				srvyRspnsMap.put("PIC_SRVY_WRT_DT", selectQustnbMngNoList.get(i).get("RSPNS_DT"));

			} else if (selectQustnbMngNoList.get(i).get("QUSTNB_TMPT_MNG_NO").equals("TM2022100400001")) {
				// 이 아래는 드림. 위에서부터 청소년설문지, 학부모설문지, 문장완성검사
				srvyRspnsMap.put("TRPR_QUSTNB_KND_SE_CD", selectQustnbMngNoList.get(i).get("QUSTNB_KND_SE_CD"));
				srvyRspnsMap.put("TRPR_QUSTNB_KND_SE_CD_NM", selectQustnbMngNoList.get(i).get("QUSTNB_KND_SE_CD_NM"));
				srvyRspnsMap.put("TRPR_SRVY_WRT_STTS_SE_CD", selectQustnbMngNoList.get(i).get("SRVY_WRT_STTS_SE_CD"));
				srvyRspnsMap.put("TRPR_QUSTNB_SMS_SNDNG_STTS_SE_CD",
						selectQustnbMngNoList.get(i).get("SNDNG_STTS_SE_CD"));
				srvyRspnsMap.put("TRPR_QUSTNB_SMS_SENT_DATE", selectQustnbMngNoList.get(i).get("SENT_DATE"));
				srvyRspnsMap.put("TRPR_SRVY_WRT_DT", selectQustnbMngNoList.get(i).get("RSPNS_DT"));

			} else if (selectQustnbMngNoList.get(i).get("QUSTNB_TMPT_MNG_NO").equals("TM2022100400004")) {

				srvyRspnsMap.put("PRTCR_QUSTNB_KND_SE_CD", selectQustnbMngNoList.get(i).get("QUSTNB_KND_SE_CD"));
				srvyRspnsMap.put("PRTCR_QUSTNB_KND_SE_CD_NM", selectQustnbMngNoList.get(i).get("QUSTNB_KND_SE_CD_NM"));
				srvyRspnsMap.put("PRTCR_SRVY_WRT_STTS_SE_CD", selectQustnbMngNoList.get(i).get("SRVY_WRT_STTS_SE_CD"));
				srvyRspnsMap.put("PRTCR_QUSTNB_SMS_SNDNG_STTS_SE_CD",
						selectQustnbMngNoList.get(i).get("SNDNG_STTS_SE_CD"));
				srvyRspnsMap.put("PRTCR_QUSTNB_SMS_SENT_DATE", selectQustnbMngNoList.get(i).get("SENT_DATE"));
				srvyRspnsMap.put("PRTCR_SRVY_WRT_DT", selectQustnbMngNoList.get(i).get("RSPNS_DT"));

			} else if (selectQustnbMngNoList.get(i).get("QUSTNB_TMPT_MNG_NO").equals("TM2022100400005")) {

				srvyRspnsMap.put("PRTCR_EVL_QUSTNB_KND_SE_CD", selectQustnbMngNoList.get(i).get("QUSTNB_KND_SE_CD"));
				srvyRspnsMap.put("PRTCR_EVL_QUSTNB_KND_SE_CD_NM",
						selectQustnbMngNoList.get(i).get("QUSTNB_KND_SE_CD_NM"));
				srvyRspnsMap.put("PRTCR_EVL_SRVY_WRT_STTS_SE_CD",
						selectQustnbMngNoList.get(i).get("SRVY_WRT_STTS_SE_CD"));
				srvyRspnsMap.put("PRTCR_EVL_QUSTNB_SMS_SNDNG_STTS_SE_CD",
						selectQustnbMngNoList.get(i).get("SNDNG_STTS_SE_CD"));
				srvyRspnsMap.put("PRTCR_EVL_QUSTNB_SMS_SENT_DATE", selectQustnbMngNoList.get(i).get("SENT_DATE"));
				srvyRspnsMap.put("PRTCR_EVL_SRVY_WRT_DT", selectQustnbMngNoList.get(i).get("RSPNS_DT"));

			}
		}

		// 설문 응답 map
		dataRequest.setResponse("dmSrvyRspns", srvyRspnsMap);

		// 서류스크리닝
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO.getUntTaskwk().equals("U07")) {
			List<Map<String, Object>> dsPapersScrennList = aplcntTrprMngService.selectPapersScrennList(request,
					dataRequest);

			dataRequest.setResponse("dsPapersScrennList", dsPapersScrennList);
		}

		return new JSONDataView();
	}

	/**
	 * @Method명 : reSndngQustnb
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception551ㅣ
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 9. 22.
	 * @Method설명 : 설문지 재발송
	 */
	@RequestMapping(value = "/reSndngQustnb.do")
	public View reSndngQustnb(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		// ## 설문지 발송 > 설문발송이력 테이블, 설문응답 테이블(작성 여부 체크)
		aplcntTrprMngService.reSndngQustnb(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : prgrsSttsStageUpdate
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 9. 22.
	 * @Method설명 : 단계별 진행 상태 업데이트
	 */
	@RequestMapping(value = "/prgrsSttsStageUpdate.do")
	public View prgrsSttsStageUpdate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		// ### 신청자 : 신청 취소, 신청 제출 2단계 제출
		// ### 관리자(입교신청) : 신청서 반송, 접수승인, 접수미승인, 접수포기
		// ### 관리자(입교접수) : 최종선정, 선정 예비자, 최종 미선정, 결정보류, 심사포기
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmAplCntDtl");
		Map<String, String> paramMap = paramGroup.getAllRowList().get(0);
		log.debug("admin 단계별 진행 상태 업데이트 - selectAplCntInfoOnLoad.paraGroup = "
				+ paramMap.get("APLY_RCPT_SRNG_SE_CD").toString() + " / "
				+ paramMap.get("APLY_RCPT_SRNG_PRGRS_STTS_SE_CD").toString());
		// ## 단계별 진행 상태 업데이트
		aplcntTrprMngService.prgrsSttsStageUpdate(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : updateAplyCnMdfcnPsbltyYn
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Kim.Seong.Ok
	 * @작성일 : 2022. 9. 22.
	 * @Method설명 : 디딤/드림 관리자(입교신청) > 수정권한(부여,회수)
	 */
	@RequestMapping(value = "/updateAplyCnMdfcnPsbltyYn.do")
	public View updateAplyCnMdfcnPsbltyYn(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		// ParameterGroup paramGroup = dataRequest.getParameterGroup("dmAplCntDtl");
		// Map<String, String> paramMap = paramGroup.getAllRowList().get(0);
		// log.debug("admin 관리자(입교신청) > 수정권한(부여,회수) - selectAplCntInfoOnLoad.paraGroup =
		// " + paramMap.get("APLY_CN_MDFCN_PSBLTY_YN").toString());
		// ## 관리자(입교신청) > 수정권한(부여,회수)
		aplcntTrprMngService.updateAplyCnMdfcnPsbltyYn(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : updateMultiFileUpload
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 22.
	 * @Method설명 : 멀티파일 업로드
	 */
	@RequestMapping(value = "/updateMultiFileUpload.do")
	public View updateMultiFileUpload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		aplcntTrprMngService.updateMultiFileUpload(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectAplyPapersCount
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 9. 22.
	 * @Method설명 : 신청서류 첨부파일 갯수
	 */
	@RequestMapping(value = "/selectAplyPapersCount.do")
	public View selectAplyPapersCount(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		aplcntTrprMngService.selectAplyPapersCount(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : updateTrprPhotoAtfino
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 28.
	 * @Method설명 : 대상자사진첨부파일번호 저장
	 */
	@RequestMapping(value = "/updateTrprPhotoAtfino.do")
	public View updateTrprPhotoAtfino(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmAplCntDtl = dataRequest.getParameterGroup("dmAplCntDtl");
		Map<String, String> requestMap = dmAplCntDtl.getSingleValueMap();
		requestMap.put("LAST_MDFR_ID", loginVO.getId());

		aplcntTrprMngService.updateTrprPhotoAtfino(requestMap);

		return new JSONDataView();
	}

	/**
	 * @Method명 : getIntrvwInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : KIM.SEONG.OK
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 : 면접정보(예약정보 테이블 조회 SBD301, SBD320 - TRPR_INFO_NO KEY )
	 */
	@RequestMapping(value = "/getIntrvwInfo.do")
	public View getIntrvwInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, String> mapDate = aplcntTrprMngService.getIntrvwInfo(request, dataRequest);
		dataRequest.setResponse("dmIntrvwInfo", mapDate);

		return new JSONDataView();
	}

}

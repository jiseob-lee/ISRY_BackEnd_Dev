/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.medscsnntabrd.web;

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
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.couns.cmmn.service.CounsService;
import isry.couns.constt.medscsnntabrd.service.BbscttListService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;

/**
 * @파일명        : BbsonmController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Song.Young.Il
 * @작성일        : 2022. 5. 13. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2022. 5. 13.
 * @수정내용      : 
 * -                
 * -                
 */

@Controller
//@RequestMapping("/medscsnntabrd")
@RequestMapping("/bbscttList")
public class BbscttController extends IsryBaseController{
	
	@Resource(name = "bbscttListService")
	private BbscttListService bbscttListService;
	
	@Resource(name = "counsService")
	private CounsService counsService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/selectBbscttList.do")
	public View selectBbscttList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		int totalCount = 0;

		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		
		mapParam.put("BBSCTT_ESNTAL_NO", searchParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("PROBM_STTS_REG", searchParam.getValue("PROBM_STTS_REG"));					// 문제상태미등록
		mapParam.put("CONSTT_NM_ENCPT", searchParam.getValue("CONSTT_NM_ENCPT"));
		
		mapParam.put("SUPV_SLCTN_CASE_YN", searchParam.getValue("SUPV_SLCTN_CASE_YN"));				//슈퍼비전선정사례여부
		mapParam.put("WRTR_NM_ENCPT", searchParam.getValue("WRTR_NM_ENCPT"));		//작성자명
		mapParam.put("BBSCTT_TTL_NM", searchParam.getValue("BBSCTT_TTL_NM"));						//게시글제목
		mapParam.put("AVRG_DGSTFN_SCORE_MIN", searchParam.getValue("AVRG_DGSTFN_SCORE_MIN"));		//최소 상담만족도
		mapParam.put("AVRG_DGSTFN_SCORE_MAX", searchParam.getValue("AVRG_DGSTFN_SCORE_MAX"));		//최대 상담만족도
		mapParam.put("BBSCTT_ESNTAL_NO", searchParam.getValue("BBSCTT_ESNTAL_NO"));					//게시글번호
		mapParam.put("CRISIS_TYPE_SE_CD", searchParam.getValue("CRISIS_TYPE_SE_CD"));				//위기유형구분코드
		mapParam.put("PROBM_STTS_LCLAS_SE_CD", searchParam.getValue("PROBM_STTS_LCLAS_SE_CD"));		//문제상태대분류
		mapParam.put("PROBM_STTS_MLSFC_SE_CD", searchParam.getValue("PROBM_STTS_MLSFC_SE_CD"));		//문제상태중분류
		mapParam.put("PROBM_STTS_SCLAS_SE_CD", searchParam.getValue("PROBM_STTS_SCLAS_SE_CD"));		//문제상태소분류
		mapParam.put("PROBM_CAS_LCLAS_SE_CD", searchParam.getValue("PROBM_CAS_LCLAS_SE_CD"));		//문제원인대분류
		mapParam.put("PROBM_CAS_SCLAS_SE_CD", searchParam.getValue("PROBM_CAS_SCLAS_SE_CD"));		//문제원인소분류
		mapParam.put("ETC_CN", searchParam.getValue("ETC_CN"));										//문제상세
		mapParam.put("SRVC_PVSN_RQST_YN", searchParam.getValue("SRVC_PVSN_RQST_YN"));				// 서비스제공의뢰여부
		
		mapParam.put("MSRMT_SCORE1", searchParam.getValue("MSRMT_SCORE1"));		//설문만족도1
		mapParam.put("MSRMT_SCORE2", searchParam.getValue("MSRMT_SCORE2"));		//설문만족도2
		
		ParameterGroup boardMenu = dataRequest.getParameterGroup("dmBoardMenu");			//게시판
		mapParam.put("BOARD_RESYN", boardMenu.getValue("brdReYn"));							//미답변,답변,본인상담
		
		ParameterGroup searchTime = dataRequest.getParameterGroup("dmTime");
		mapParam.put("START_DATE", searchTime.getValue("startDate"));						//조회시작날짜
		mapParam.put("END_DATE", searchTime.getValue("endDate"));							//조회끝날짜
		
		List<Map<String , Object>> dsBoardList = null;
		// 게시판 메뉴 comboBox
		String brdReYn = boardMenu.getValue("brdReYn");
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginRoleCd = loginVO.getEnfsnRoleSeCd();
//		System.out.println("loginRoleCd sec list ::"+loginRoleCd);
		
//		Map<String, Object> oUserID = new HashMap<String, Object>();
//		oUserID.put("oUserID", loginRoleCd);
//		oUserID.put("oUserNM", loginVO.getUserName());
		
//		System.out.println("brdReYn ::::::::::" + brdReYn);
		
//		System.out.println("mapParam 00000000 ::::::::::" + mapParam.toString());
		
		// 선택한 게시판 메뉴에 따른 조회
		if(brdReYn.equals("0")) {
			dsBoardList = bbscttListService.selectBbscttList(mapParam);	//본인상담
		}else if(brdReYn.equals("1")) {
			dsBoardList = bbscttListService.nonRepSelectBbscttList(mapParam); //미답변
		}else if(brdReYn.equals("2")) {
			dsBoardList = bbscttListService.repSelectBbscttList(mapParam); //답변
		}
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		//전체 게시글 수
		if (dsBoardList == null || dsBoardList.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", dsBoardList.get(0).get("TOTAL_COUNT"));
		}		

		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsBoardList", dsBoardList);
		dataRequest.setResponse("dmPage", resPage);
//		dataRequest.setResponse("dmUser", oUserID);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectBbscttDetail.do")
	public View selectBbscttDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		Map<String, Object> autoSndngInfo = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", dmDtlParam.getValue("BBSCTT_TYPE_SE_CD"));
		
//		System.out.println("dmDtlParam 00000 ::::::::::" + dmDtlParam.toString());
		
		// 조회수추가
		bbscttListService.bbscttDtlCnt(mapParam);
		
		// 게시글 상세 조회
		List<Map<String, Object>> dsBoardList = bbscttListService.selectBbscttDetail(mapParam);
		for (Map<String, Object> map : dsBoardList) {
	        try {
				autoSndngInfo.put("BBSCTT_ESNTAL_NO", map.get("BBSCTT_ESNTAL_NO"));
				autoSndngInfo.put("BBSCTT_TYPE_SE_CD", map.get("BBSCTT_TYPE_SE_CD"));
				autoSndngInfo.put("RECEIVER_NM", map.get("WRTR_NM_ENCPT"));
				autoSndngInfo.put("RECEIVER_EML", map.get("EML_ADDR_ENCPT"));
				autoSndngInfo.put("RECEIVER_TELNO", map.get("MBL_TELNO_ENCPT"));
				autoSndngInfo.put("CHRCTR_YN", map.get("CHRCTR_YN"));
				
			} catch (Exception e) {

			}
        }
		
		dataRequest.setResponse("dsBoardList", dsBoardList);
		dataRequest.setResponse("dmAutoSndngInfo", autoSndngInfo);
		
		mapParam.put("RETE_ESNTAL_NO", dmDtlParam.getValue("RETE_ESNTAL_NO"));
		mapParam.put("NUM", dmDtlParam.getValue("NUM"));
		
		// 조회수추가
		bbscttListService.RespodDtlCnt(mapParam);
		
		// 답글 상세 조회
		List<Map<String, Object>> dsDtlReply = bbscttListService.selectRespodDetail(mapParam);
		dataRequest.setResponse("dsDtlReply", dsDtlReply);
		
		return new JSONDataView();
	}

	@RequestMapping("/saveBbscttList.do")
	public View saveBbscttList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = bbscttListService.saveBbscttList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/onLoadBbsctt.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		//위기유형구분코드
		List<Map<String, Object>> dsCriTySeCdCmb = mgmtCmmnCodeService.selectCommonCodeUnit("CRISIS_TYPE_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCriTySeCdCmb", dsCriTySeCdCmb);
		
		//문제상태대분류코드
		List<Map<String, Object>> dsSttsLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsLclas", dsSttsLclas);
		
		//문제상태중분류코드
		List<Map<String, Object>> dsSttsMlsfc = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_MLSFC_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsMlsfc", dsSttsMlsfc);
		
		//문제상태소분류코드
		List<Map<String, Object>> dsSttsSclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_SCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsSclas", dsSttsSclas);
		
		//문제원인대분류코드
		List<Map<String, Object>> dsCasLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasLclas", dsCasLclas);
		
		//문제원인소분류코드
		List<Map<String, Object>> dsCasSclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_SCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasSclas", dsCasSclas);
		
		//신고유형구분코드
		List<Map<String, Object>> dsDclrTySeCd = mgmtCmmnCodeService.selectCommonCodeUnit("DCLR_TYPE_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsDclrTySeCd", dsDclrTySeCd);
		
		//신고자구분코드
		List<Map<String, Object>> dsDclSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("DCL_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsDclSeCd", dsDclSeCd);
		
		//처리유형대분류코드
		List<Map<String, Object>> dsPrcsLclasSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("PRCS_TYPE_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsPrcsLclasSeCd", dsPrcsLclasSeCd);
		
		//처리유형소분류코드
		List<Map<String, Object>> dsPrcsSclasSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("PRCS_TYPE_SCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsPrcsSclasSeCd", dsPrcsSclasSeCd);
		
		//상담처리내역구분코드
		List<Map<String, Object>> dsDcPrHiSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("DSCSN_PRCS_HISTB_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsDcPrHiSeCd", dsDcPrHiSeCd);
		
		//게시판상담실직업구분코드
		List<Map<String, Object>> dsNtCsOcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("NTABRD_CSC_OCCP_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsNtCsOcSeCd", dsNtCsOcSeCd);
		
		//상담영역구분코드
		List<Map<String, Object>> dsReSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("DSCSN_RELM_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsReSeCd", dsReSeCd);
		
		//성별구분코드
		List<Map<String, Object>> dsSxdcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSxdcSeCd", dsSxdcSeCd);
		
		//학력구분코드
		List<Map<String, Object>> dsAcbgSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("ACBG_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsAcbgSeCd", dsAcbgSeCd);
		
		//학년구분코드
		List<Map<String, Object>> dsGradeSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("GRADE_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsGradeSeCd", dsGradeSeCd);
		
		//이슈문제구분코드
		List<Map<String, Object>> dsIssProSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("ISSUE_PROBM_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsIssProSeCd", dsIssProSeCd);
		
		//주요시행기관구분코드
		List<Map<String, Object>> dsPrcsInstCd = mgmtCmmnCodeService.selectCommonCodeUnit("MAIN_ENFC_INST_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsPrcsInstCd", dsPrcsInstCd);
		
		// 연계처리구분코드
		List<Map<String, Object>> dsLinkPrcsSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("LINK_PRCS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsLinkPrcsSeCd", dsLinkPrcsSeCd);
				
//		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginRoleCd = loginVO.getEnfsnRoleSeCd();
//		System.out.println("loginRoleCd 000000000 ::::::::::::::"+loginRoleCd);
		
		Map<String, Object> oUserID = new HashMap<String, Object>();
		oUserID.put("oUserRoleCd", loginRoleCd);
		oUserID.put("oUserNM", loginVO.getUserName());
		oUserID.put("oUserId", loginVO.getId());
		dataRequest.setResponse("dmUser", oUserID);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/insertCounselor.do")
	public View insertCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmCouns = dataRequest.getParameterGroup("dmCouns");
		mapParam.put("WORK_YMD", dmCouns.getValue("WORK_YMD"));
		List<Map<String, Object>> dsCouns = bbscttListService.insertCounselor(mapParam);

		dataRequest.setResponse("dsCouns", dsCouns);
		
		return new JSONDataView();
	}
//	/bbscttList/saveCounselor.do
	
	@RequestMapping("/insertCrisis.do")
	public View insertCrisis(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {

		bbscttListService.insertCrisis(request, dataRequest);		
		return new JSONDataView();
	}
	
	@RequestMapping("/updateCase.do")
	public View updateCase(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");

		mapParam.put("BBSCTT_ESNTAL_NO", dsBoardList.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", dsBoardList.getValue("BBSCTT_TYPE_SE_CD"));
		mapParam.put("SUPV_SLCTN_CASE_YN", dsBoardList.getValue("SUPV_SLCTN_CASE_YN"));
		bbscttListService.updateCase(mapParam);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/selectRespodDetail.do")
	public View selectRespodDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("RETE_ESNTAL_NO", dmDtlParam.getValue("RETE_ESNTAL_NO"));
		mapParam.put("NUM", dmDtlParam.getValue("NUM"));
		//조회수추가
		bbscttListService.RespodDtlCnt(mapParam);
		//게시글 상세 조회
		List<Map<String, Object>> dsDtlReply = bbscttListService.selectRespodDetail(mapParam);
		dataRequest.setResponse("dsDtlReply", dsDtlReply);
		return new JSONDataView();
	}
	
	@RequestMapping("/saveRespod.do")
	public View saveRespod(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> returnParam = bbscttListService.saveRespod(request, dataRequest);
		bbscttListService.saveBbscttList(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectSulmun.do")
	public View selectSulmun(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
//		System.out.println("DDD bbsctt dmSearch ===================== : "+dmSearch.toString());
		
		mapParam.put("BBSCTT_ESNTAL_NO", dmSearch.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", dmSearch.getValue("BBSCTT_TYPE_SE_CD"));
		List<Map<String, Object>> dsList = bbscttListService.selectSulmun(mapParam);
		
		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	
	@RequestMapping("/insertVoc.do")
	public View insertVoc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmInsertVoc = dataRequest.getParameterGroup("dmInsertVoc");
//		System.out.println("DDD : "+dmInsertVoc.toString());

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		mapParam.put("BBSCTT_ESNTAL_NO", dmInsertVoc.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TTL_NM", dmInsertVoc.getValue("BBSCTT_TTL_NM"));
		mapParam.put("BBSCTT_CN", dmInsertVoc.getValue("BBSCTT_CN"));
		mapParam.put("RETE_TTL_NM", dmInsertVoc.getValue("RETE_TTL_NM"));
		mapParam.put("RETE_CN", dmInsertVoc.getValue("RETE_CN"));
		mapParam.put("EML_ADDR_ENCPT", dmInsertVoc.getValue("EML_ADDR_ENCPT"));
		mapParam.put("BOARD_TYPE", dmInsertVoc.getValue("BOARD_TYPE"));
		mapParam.put("FRST_RGTR_ID", userId);
		mapParam.put("LAST_MDFR_ID", userId);
		bbscttListService.insertVoc(mapParam);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectBbscttMemo.do")
	public View selectBbscttMemo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		List<Map<String, Object>> dsMemo = bbscttListService.selectMemo(mapParam);
		dataRequest.setResponse("dsMemo", dsMemo);
		return new JSONDataView();
	}
	
	@RequestMapping("/saveCounselor.do")
	public View saveCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmCounselor = dataRequest.getParameterGroup("dmSaveCounselor");
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		if(dmCounselor.getValue("INDEX_SN") != null && dmCounselor.getValue("INDEX_SN") != "") {
			mapParam.put("INDEX_SN", dmCounselor.getValue("INDEX_SN"));
		}
		mapParam.put("BBSCTT_ESNTAL_NO", dmCounselor.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", dmCounselor.getValue("BBSCTT_TYPE_SE_CD"));
		mapParam.put("REG_DT", dmCounselor.getValue("REG_DT"));
		mapParam.put("CONSTT_ID", dmCounselor.getValue("CONSTT_ID"));
		mapParam.put("FRST_RGTR_ID", userId);
		mapParam.put("LAST_MDFR_ID", userId);
		
		bbscttListService.saveCounselor(mapParam);
				
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBbscttMemo.do")
	public View insertBbscttMemo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		String loginId = "";
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsBoardList");
		List<Map<String, String>> paramList = paramGroup.getUpdatedRowList();
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		mapParam.put("BBSCTT_ESNTAL_NO", paramList.get(0).get("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", paramList.get(0).get("BBSCTT_TYPE_SE_CD"));
		mapParam.put("CONSTT_ID", loginId);
		mapParam.put("MEMO_NM", paramList.get(0).get("MEMO_NM"));
		mapParam.put("FRST_RGTR_ID", loginId);
		mapParam.put("LAST_MDFR_ID", loginId);
		
		bbscttListService.insertMemo(mapParam);
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명		: selectSrvyResult
	 * @param		: request
	 * @return		: View
	 * @throws		: Exception
	 * @작성자			: Sin.Hyun.Jin
	 * @작성일			: 2023. 01. 11. 
	 * @Method설명	: 설문결과를 조회한다.
	 */
	@RequestMapping("/selectSrvyResultList.do")
	public View selectSrvyResultList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		Map<String, String> mapParam	= new HashMap<String, String>();
		ParameterGroup dmSearch			= dataRequest.getParameterGroup("dmSearch");
		
		mapParam.put("BBSCTT_ESNTAL_NO", 	dmSearch.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", 	dmSearch.getValue("BBSCTT_TYPE_SE_CD"));
		List<Map<String, String>> dsList = bbscttListService.selectSrvyResultList(mapParam);
		
		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : processSecreNtabrdDtl
	 * @param 	   : request
	 * @param	   : response
	 * @param 	   : dataRequest
	 * @return	   : View
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 4. 7. 
	 * @Method설명 : 비밀게시판 게시글 및 답글 Insert/Update/Delete
	 */
	@RequestMapping("/processSecreNtabrdDtl.do")
	public View processSecreNtabrdDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		bbscttListService.processSecreNtabrdDtl(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : deleteCnsltntAsgn
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return	   : View
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 6. 2. 
	 * @Method설명 : 비밀게시판 상담자 할당 Delete
	 */
	@RequestMapping("/deleteCnsltntAsgn.do")
	public View deleteCnsltntAsgn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		counsService.deleteCnsltntAsgn(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : updateBbscttTitle
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return	   : View
	 * @throws 	   : Exception
	 * @작성자     : Park.Chan.Hyeop
	 * @작성일     : 2023. 8. 30. 
	 * @Method설명 : 비밀게시판 게시글 제목 수정
	 */
	@RequestMapping("/updateBbscttTitle.do")
	public View updateBbscttTitle(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", dmDtlParam.getValue("BBSCTT_TYPE_SE_CD"));
		mapParam.put("BBSCTT_TTL_NM", dmDtlParam.getValue("BBSCTT_TTL_NM"));
		
		
		bbscttListService.updateBbscttTitle(mapParam);
		
		return new JSONDataView();
	}

}
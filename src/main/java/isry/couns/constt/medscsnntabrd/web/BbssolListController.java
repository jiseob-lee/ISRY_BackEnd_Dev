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
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.couns.cmmn.service.CounsService;
import isry.couns.constt.medscsnntabrd.service.BbssolListService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.redis.service.RedisService;

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
@RequestMapping("/bbssolList")
public class BbssolListController extends IsryBaseController{
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "bbssolListService")
	private BbssolListService bbssolListService;
	
	@Resource(name = "counsService")
	private CounsService counsService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/selectBbssolList.do")
	public View selectBbssolList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		// int totalCount = 0;

		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
		
        // ParameterGroup dmRoleCd = dataRequest.getParameterGroup("dmRoleCd");
		
		//if(dmRoleCd.getValue("loginRoleCd").equals("3")) {
		//	System.out.println("상담원~~~~");
		//	HttpSession session = request.getSession();
		//	UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
		//	String loginId = loginVO.getId();			
			
		//	mapParam.put("LOGIN_ID", loginId);
		// }
				
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
									
		mapParam.put("WRTR_NM_ENCPT"				, searchParam.getValue("WRTR_NM_ENCPT"));		// 작성자명
		mapParam.put("BBSCTT_TTL_NM"				, searchParam.getValue("BBSCTT_TTL_NM"));						// 게시글제목
		mapParam.put("BBSCTT_ESNTAL_NO"				, searchParam.getValue("BBSCTT_ESNTAL_NO"));					// 게시글번호
		mapParam.put("CRISIS_TYPE_SE_CD"			, searchParam.getValue("CRISIS_TYPE_SE_CD"));					// 위기유형구분코드
		mapParam.put("PROBM_STTS_LCLAS_SE_CD"		, searchParam.getValue("PROBM_STTS_LCLAS_SE_CD"));				// 문제상태대분류
		mapParam.put("PROBM_STTS_MLSFC_SE_CD"		, searchParam.getValue("PROBM_STTS_MLSFC_SE_CD"));				// 문제상태중분류
		mapParam.put("PROBM_STTS_SCLAS_SE_CD"		, searchParam.getValue("PROBM_STTS_SCLAS_SE_CD"));				// 문제상태소분류
		mapParam.put("PROBM_CAS_LCLAS_SE_CD"		, searchParam.getValue("PROBM_CAS_LCLAS_SE_CD"));				// 문제원인대분류
		mapParam.put("PROBM_CAS_SCLAS_SE_CD"		, searchParam.getValue("PROBM_CAS_SCLAS_SE_CD"));				// 문제원인소분류
		mapParam.put("ETC_CN"						, searchParam.getValue("ETC_CN"));								// 문제상세		
		mapParam.put("PROBM_STTS_LCLAS_SE_CD_EMPTY"	, searchParam.getValue("PROBM_STTS_LCLAS_SE_CD_EMPTY"));		// 문제상태미등록
		mapParam.put("AVRG_DGSTFN_SCORE_MIN"		, searchParam.getValue("AVRG_DGSTFN_SCORE_MIN"));				// 최소상담만족도
		mapParam.put("AVRG_DGSTFN_SCORE_MAX"		, searchParam.getValue("AVRG_DGSTFN_SCORE_MAX"));				// 최대상담만족도
		mapParam.put("CNSLTNT_NM_ENCPT"				, searchParam.getValue("CNSLTNT_NM_ENCPT"));	// 상담원명
		mapParam.put("SRVC_PVSN_RQST_YN"			, searchParam.getValue("SRVC_PVSN_RQST_YN"));					// 서비스제공의뢰여부
		
		ParameterGroup boardMenu = dataRequest.getParameterGroup("dmBoardMenu");			//게시판
		mapParam.put("BOARD_RESYN", boardMenu.getValue("brdReYn"));							//미답변,답변,본인상담
		
		ParameterGroup searchtime = dataRequest.getParameterGroup("dmTime");
		
		mapParam.put("START_DATE", searchtime.getValue("startDate"));						//조회시작날짜
		mapParam.put("END_DATE", searchtime.getValue("endDate"));							//조회끝날짜
		List<Map<String , Object>> dsBoardList = null;
		String brdReYn = boardMenu.getValue("brdReYn");
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginRoleCd = loginVO.getEnfsnRoleSeCd();
//		System.out.println("loginRoleCd::"+loginRoleCd);

//		if(loginRoleCd.equals("3")) {                        
		if("3".equals(loginRoleCd)) {                        // 상담원 접속시 상담원 본인 게시글만 조회 
//		System.out.println("상담원~~~~");
			//String loginId = loginVO.getId();			
			
			//mapParam.put("LOGIN_ID_DD", loginId);
		}
		
		if(brdReYn.equals("0")) {
			dsBoardList = bbssolListService.selectBbssolList(mapParam);	//본인상담 (전체)
		}else if(brdReYn.equals("1")) {
			dsBoardList = bbssolListService.nonRepSelectBbssolList(mapParam); //미답변
		}else if(brdReYn.equals("2")) {
			dsBoardList = bbssolListService.repSelectBbssolList(mapParam); //답변
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
		return new JSONDataView();
	}
	
	@RequestMapping("/selectBbssolDetail.do")
	public View selectBbssolDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		Map<String, Object> autoSndngInfo = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", dmDtlParam.getValue("BBSCTT_TYPE_SE_CD"));
		
		//조회수추가
		bbssolListService.bbssolDtlCnt(mapParam);
		
		//게시글 메인 조회
		List<Map<String, Object>> dsBoardList = bbssolListService.selectBbssolDetail(mapParam);
		for (Map<String, Object> map : dsBoardList) {
	        try {
				autoSndngInfo.put("BBSCTT_ESNTAL_NO", map.get("BBSCTT_ESNTAL_NO"));
				autoSndngInfo.put("BBSCTT_TYPE_SE_CD", map.get("BBSCTT_TYPE_SE_CD"));
				autoSndngInfo.put("RECEIVER_NM", map.get("WRTR_NM_ENCPT"));
				autoSndngInfo.put("RECEIVER_EML", map.get("EML_ADDR_ENCPT"));
				autoSndngInfo.put("RECEIVER_TELNO", map.get("MBL_TELNO_ENCPT"));
				autoSndngInfo.put("CHRCTR_YN", map.get("CHRCTR_YN"));
				
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
		
		dataRequest.setResponse("dsBoardList", dsBoardList);
		dataRequest.setResponse("dmAutoSndngInfo", autoSndngInfo);
		
		mapParam.put("RETE_ESNTAL_NO", dmDtlParam.getValue("RETE_ESNTAL_NO"));
		mapParam.put("NUM", dmDtlParam.getValue("NUM"));
		
		//조회수추가
		bbssolListService.respodDtlCnt(mapParam);
		
		//게시글 답글 조회
		List<Map<String, Object>> dsDtlReply = bbssolListService.selectRespodDetail(mapParam);
		dataRequest.setResponse("dsDtlReply", dsDtlReply);
		
		return new JSONDataView();
	}

//	@RequestMapping("/saveBbssolListDetail.do")   ////////////////
//	public View saveBbssolListDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
//			throws Exception {
//
//		bbssolListService.saveBbssolListDetail(request, dataRequest);
//
//		return new JSONDataView();
//	}

	@RequestMapping("/saveBbssolList.do")
	public View saveBbssolList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = bbssolListService.saveBbssolList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/onLoadBbssol.do")
	public View onLoadBbssol(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		//역할구분코드
		Map<String, Object> mapRoleCd = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		String loginRoleCd = userVo.getEnfsnRoleSeCd();
		//System.out.println("loginRoleCd::"+loginRoleCd);
		mapRoleCd.put("loginRoleCd", loginRoleCd);
		
		dataRequest.setResponse("dmRoleCd", mapRoleCd);
		
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
		
		//처리내역구분코드
		List<Map<String, Object>> dsDcPrHiSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("DSCSN_PRCS_HISTB_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsDcPrHiSeCd", dsDcPrHiSeCd);
		
		// 연계처리구분코드
		List<Map<String, Object>> dsLinkPrcsSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("LINK_PRCS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsLinkPrcsSeCd", dsLinkPrcsSeCd);
		
		
		
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBbssolCounselor.do")
	public View insertBbssolCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmCouns = dataRequest.getParameterGroup("dmCouns");
		mapParam.put("WORK_YMD", dmCouns.getValue("WORK_YMD"));
		List<Map<String, Object>> dsCouns = bbssolListService.insertCounselor(mapParam);
		//System.out.println("dsCouns DDD : "+ dsCouns.toString());
		dataRequest.setResponse("dsCouns", dsCouns);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBbssolCrisis.do")
	public View insertBbssolCrisis(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		bbssolListService.insertCrisis(request, dataRequest);
		
		return new JSONDataView();
	}

	@RequestMapping("/selectBbssolRespodDetail.do")
	public View selectBbssolRespodDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("RETE_ESNTAL_NO", dmDtlParam.getValue("RETE_ESNTAL_NO"));
		mapParam.put("NUM", dmDtlParam.getValue("NUM"));
		//조회수추가
		bbssolListService.respodDtlCnt(mapParam);
		//게시글 상세 조회
		List<Map<String, Object>> dsDtlReply = bbssolListService.selectRespodDetail(mapParam);

		dataRequest.setResponse("dsDtlReply", dsDtlReply);
		return new JSONDataView();
	}
	
	@RequestMapping("/saveBbssolRespod.do")
	public View saveBbssolRespod(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> returnParam = bbssolListService.saveRespod(request, dataRequest);
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/saveBbbSsoCounselor.do")
	public View saveBbbSsoCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmCounselor = dataRequest.getParameterGroup("dmSaveCounselor");
		
		HttpSession session = request.getSession();
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
		mapParam.put("CONSTT_ID", dmCounselor.getValue("CONSTT_ID"));
		mapParam.put("REG_DT", dmCounselor.getValue("REG_DT"));
		mapParam.put("FRST_RGTR_ID", userId);
		mapParam.put("LAST_MDFR_ID", userId);
		
		bbssolListService.saveCounselor(mapParam);
				
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBbsSsoMemo.do")
	public View insertBbsSsoMemo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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
		
		bbssolListService.insertMemo(mapParam);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/ssolContentList.do")
	public View ssolContentList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		List<Map<String, String>> dsList = bbssolListService.ssolContentList(dataRequest);

		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	
	@RequestMapping("/ssolContentInsert.do")
	public View ssolContentInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		bbssolListService.ssolContentInsert(request, dataRequest);

		return null;
	}
	
	@RequestMapping("/saveBbssol.do")
	public View saveBbssol(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = bbssolListService.saveBbssol(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/updateCase.do")
	public View updateCase(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");

		mapParam.put("BBSCTT_ESNTAL_NO", dsBoardList.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", dsBoardList.getValue("BBSCTT_TYPE_SE_CD"));
		mapParam.put("SUPV_SLCTN_CASE_YN", dsBoardList.getValue("SUPV_SLCTN_CASE_YN"));
		
		bbssolListService.updateCase(mapParam);
		
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
	 * @Method설명 : 솔로봇게시판 상담자 할당 Delete
	 */
	@RequestMapping("/deleteCnsltntAsgn.do")
	public View deleteCnsltntAsgn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		counsService.deleteCnsltntAsgn(request, dataRequest);
		
		return new JSONDataView();
	}
}
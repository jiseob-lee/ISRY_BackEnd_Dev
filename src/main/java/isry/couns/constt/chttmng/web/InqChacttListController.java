/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.chttmng.web;

import java.util.ArrayList;
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
import isry.couns.constt.chttmng.service.InqChacttListService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
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
@RequestMapping("/chttmng")
public class InqChacttListController extends IsryBaseController{
	
	@Resource(name = "InqChacttListService")
	private InqChacttListService inqChacttListService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/selectInqchacttList.do")
	public View selectInqchacttList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		//int totalCount = 0;

		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		
		// 슈퍼비전선정사례여부, 문제상태 미등록 조회여부 null인 경우 N으로 수정
		String supvSlctnCaseYn = searchParam.getValue("SUPV_SLCTN_CASE_YN") == null && "".equals(searchParam.getValue("SUPV_SLCTN_CASE_YN")) ? "N" : searchParam.getValue("SUPV_SLCTN_CASE_YN");
		String probmSttsLclasSeCdEmpty = searchParam.getValue("PROBM_STTS_LCLAS_SE_CD_EMPTY") == null && "".equals(searchParam.getValue("PROBM_STTS_LCLAS_SE_CD_EMPTY")) ? "N" : searchParam.getValue("PROBM_STTS_LCLAS_SE_CD_EMPTY");
		
		String clienaNmEncpt	= searchParam.getValue("CLIENA_NM_ENCPT")	!= null ? searchParam.getValue("CLIENA_NM_ENCPT")	: "";
		String cnsltntNmEncpt	= searchParam.getValue("CNSLTNT_NM_ENCPT")	!= null ? searchParam.getValue("CNSLTNT_NM_ENCPT")	: "";
				
		// 조회 input set
		mapParam.put("CHTT_TYPE_SE_CD"				, searchParam.getValue("CHTT_TYPE_SE_CD"));						// 채팅유형구분
		mapParam.put("CNTN_IP_ADDR"					, searchParam.getValue("CNTN_IP_ADDR"));						// 아이피
		mapParam.put("CLIENA_NM_ENCPT"				, clienaNmEncpt);												// 내담자명
		mapParam.put("CNSLTNT_NM_ENCPT"				, cnsltntNmEncpt);												// 상담원명
		mapParam.put("AVRG_DGSTFN_SCORE_MIN"		, searchParam.getValue("AVRG_DGSTFN_SCORE_MIN"));				// 최소 상담만족도
		mapParam.put("AVRG_DGSTFN_SCORE_MAX"		, searchParam.getValue("AVRG_DGSTFN_SCORE_MAX"));				// 최대 상담만족도
		mapParam.put("SUPV_SLCTN_CASE_YN"			, supvSlctnCaseYn);												// 슈퍼비전선정사례여부
		mapParam.put("PROBM_STTS_LCLAS_SE_CD"		, searchParam.getValue("PROBM_STTS_LCLAS_SE_CD"));				// 문제상태대분류
		mapParam.put("PROBM_STTS_MLSFC_SE_CD"		, searchParam.getValue("PROBM_STTS_MLSFC_SE_CD"));				// 문제상태중분류
		mapParam.put("PROBM_STTS_SCLAS_SE_CD"		, searchParam.getValue("PROBM_STTS_SCLAS_SE_CD"));				// 문제상태소분류
		mapParam.put("PROBM_CAS_LCLAS_SE_CD"		, searchParam.getValue("PROBM_CAS_LCLAS_SE_CD"));				// 문제원인대분류
		mapParam.put("PROBM_CAS_SCLAS_SE_CD"		, searchParam.getValue("PROBM_CAS_SCLAS_SE_CD"));				// 문제원인소분류
		mapParam.put("ETC_CN"						, searchParam.getValue("ETC_CN"));								// 문제상세
		mapParam.put("PROBM_STTS_LCLAS_SE_CD_EMPTY"	, probmSttsLclasSeCdEmpty);										// 문제상태 미등록 조회여부
		mapParam.put("SPCLA_MNG_TRPR_YN"			, searchParam.getValue("SPCLA_MNG_TRPR_YN"));					// 특별관리여부
		mapParam.put("CRISIS_TYPE_SE_CD"			, searchParam.getValue("CRISIS_TYPE_SE_CD"));					// 위기유형구분코드
		mapParam.put("CNSLTNT_ID"					, searchParam.getValue("CNSLTNT_ID"));							// 상담원아이디
		mapParam.put("SRVC_PVSN_RQST_YN"			, searchParam.getValue("SRVC_PVSN_RQST_YN"));					// 연계의뢰
		
		
		ParameterGroup searchtime = dataRequest.getParameterGroup("dmTime");
		
		mapParam.put("START_DATE", searchtime.getValue("startDate"));							//조회시작날짜
		mapParam.put("END_DATE", searchtime.getValue("endDate"));								//조회끝날짜
		List<Map<String , Object>> dsBoardList = inqChacttListService.selectInqchacttList(mapParam);	//본인상담
//		System.out.println("dsBoardList : "+dsBoardList.toString());
		for (Map<String, Object> map : dsBoardList) {
	        try {
	        	String clienaNmDec	= map.get("CLIENA_NM_ENCPT")	!= null ? map.get("CLIENA_NM_ENCPT").toString()	: "";
				String cnsltntNmDec	= map.get("CNSLTNT_NM_ENCPT")	!= null ? map.get("CNSLTNT_NM_ENCPT").toString()	: "";
	        	
        		map.replace("CLIENA_NM_ENCPT", clienaNmDec);
	        	map.replace("CNSLTNT_NM_ENCPT", cnsltntNmDec);
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		//전체 게시글 수
		if(dsBoardList.size() == 0) {
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
	
	@RequestMapping("/selectInqchacttDetail.do")
	public View selectInqchacttDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("CHRO_NO", dmDtlParam.getValue("CHRO_NO"));
		//게시글 상세 조회
		List<Map<String, Object>> dsBoardList = inqChacttListService.selectInqchacttDetail(mapParam);
		
		dataRequest.setResponse("dsBoardList", dsBoardList);
		return new JSONDataView();
	}

	@RequestMapping("/saveChacttList.do")
	public View saveChacttList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = inqChacttListService.saveChacttList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("CHRO_NO", returnParam.get("CHRO_NO"));
		message.put("strFindRowKey", "CHRO_NO == '" + returnParam.get("CHRO_NO") + "'");

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/onLoadInqchactt.do")
	public View onLoadInqchactt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		//위기유형구분코드
		List<Map<String, Object>> dsCriTySeCdCmb = mgmtCmmnCodeService.selectCommonCodeUnit("CRISIS_TYPE_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCriTySeCdCmb", dsCriTySeCdCmb);
		
		//문제상태대분류
		List<Map<String, Object>> dsSttsLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsLclas", dsSttsLclas);
		
		//문제상태중분류
		List<Map<String, Object>> dsSttsMlsfc = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_MLSFC_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsMlsfc", dsSttsMlsfc);
		
		//문제상태소분류
		List<Map<String, Object>> dsSttsSclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_SCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsSclas", dsSttsSclas);
		
		//문제원인대분류
		List<Map<String, Object>> dsCasLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasLclas", dsCasLclas);
		
		//문제원인소분류
		List<Map<String, Object>> dsCasSclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_SCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasSclas", dsCasSclas);
		
		//채팅유형구분
		List<Map<String, Object>> dsChttTySeCd = mgmtCmmnCodeService.selectCommonCodeUnit("CHTT_TYPE_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsChttTySeCd", dsChttTySeCd);
		
		//게시판상담실직업구분코드
		List<Map<String, Object>> dsNtCsOcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("NTABRD_CSC_OCCP_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsNtCsOcSeCd", dsNtCsOcSeCd);

		//상담처리내역구분코드
		List<Map<String, Object>> dsDcPrHiSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("DSCSN_PRCS_HISTB_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsDcPrHiSeCd", dsDcPrHiSeCd);		
		
		// 학력구분코드
		List<Map<String, Object>> dsAcbgSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("ACBG_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsAcbgSeCd", dsAcbgSeCd);
		
		// 입소당시학년구분코드
		List<Map<String, Object>> dsGradeSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("GRADE_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsGradeSeCd", dsGradeSeCd);
		
		//성별구분코드
		List<Map<String, Object>> dsSxdcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSxdcSeCd", dsSxdcSeCd);
		
		// 연계처리구분코드
		List<Map<String, Object>> dsLinkPrcsSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("LINK_PRCS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsLinkPrcsSeCd", dsLinkPrcsSeCd);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/updateChacttMemo.do")
	public View updateChacttMemo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
		mapParam.put("CHRO_NO", dsBoardList.getValue("CHRO_NO"));
		mapParam.put("CHTT_MEMO_CN", dsBoardList.getValue("CHTT_MEMO_CN"));
		inqChacttListService.updateChacttMemo(mapParam);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/insertCrisis.do")
	public View insertCrisis(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
//		Map<String, Object> mapParam = new HashMap<String, Object>();
//		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
//		mapParam.put("CHRO_NO", dsBoardList.getValue("CHRO_NO"));
//		mapParam.put("CRISIS_TYPE_SE_CD", dsBoardList.getValue("CRISIS_TYPE_SE_CD"));
//		
//		mapParam.put("CNSLTNT_ID", dsBoardList.getValue("CNSLTNT_ID"));
		inqChacttListService.insertCrisis(request, dataRequest);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/updateCase.do")
	public View updateCase(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");

		mapParam.put("CHRO_NO", dsBoardList.getValue("CHRO_NO"));
		mapParam.put("SUPV_SLCTN_CASE_YN", dsBoardList.getValue("SUPV_SLCTN_CASE_YN"));
		inqChacttListService.updateCase(mapParam);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/selectSulmun.do")
	public View selectSulmun(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
//		System.out.println("DDD inqChat dmSearch ===================== : "+dmSearch.toString());
		
		mapParam.put("CHRO_NO", dmSearch.getValue("CHRO_NO"));
		List<Map<String, Object>> dsList = inqChacttListService.selectSulmun(mapParam);
		
		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	
	@RequestMapping("/getChtt.do")
	public View getChtt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		String rtnMsg = inqChacttListService.getChtt(dataRequest);
		Map<String, String> map = new HashMap<String, String>();
		map.put("RETURN_MSG", rtnMsg);
		dataRequest.setResponse("dmRtnMsg", map);
		return new JSONDataView();
	}
}

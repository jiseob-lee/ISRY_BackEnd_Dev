/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbsout.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseController;
import isry.couns.constt.bbsout.service.BbsoutListService;
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
@RequestMapping("/constt")
public class BbsoutListController extends IsryBaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "bbsoutListService")
	private BbsoutListService bbsoutListService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/selectBbsoutList.do")
	public View selectBbsoutList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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
		
		mapParam.put("CRISIS_TYPE_SE_CD", searchParam.getValue("CRISIS_TYPE_SE_CD"));		//위기관리
		mapParam.put("WRTR_NM_ENCPT", searchParam.getValue("WRTR_NM_ENCPT"));		//작성자명
		mapParam.put("OUTRC_DSCSN_MTHD_SE_CD", searchParam.getValue("OUTRC_DSCSN_MTHD_SE_CD"));		//아웃리치상담방법
		mapParam.put("SNS_SE_CD", searchParam.getValue("SNS_SE_CD"));								//SNS구분
		mapParam.put("PROBM_STTS_LCLAS_SE_CD", searchParam.getValue("PROBM_STTS_LCLAS_SE_CD"));		//문제상태대분류
		mapParam.put("PROBM_STTS_MLSFC_SE_CD", searchParam.getValue("PROBM_STTS_MLSFC_SE_CD"));		//문제상태중분류
		mapParam.put("PROBM_STTS_SCLAS_SE_CD", searchParam.getValue("PROBM_STTS_SCLAS_SE_CD"));		//문제상태소분류
		mapParam.put("PROBM_CAS_LCLAS_SE_CD", searchParam.getValue("PROBM_CAS_LCLAS_SE_CD"));		//문제원인대분류
		mapParam.put("PROBM_CAS_SCLAS_SE_CD", searchParam.getValue("PROBM_CAS_SCLAS_SE_CD"));		//문제원인소분류
		mapParam.put("ETC_CN", searchParam.getValue("ETC_CN"));										//문제상세
		mapParam.put("ATFINO", searchParam.getValue("ATFINO"));										//첨부
		
		mapParam.put("PROBM_STTS_REG", searchParam.getValue("PROBM_STTS_REG"));					// 문제상태미등록
		mapParam.put("BBSCTT_TTL_NM", searchParam.getValue("BBSCTT_TTL_NM"));					//게시글제목
		mapParam.put("BBSCTT_ESNTAL_NO", searchParam.getValue("BBSCTT_ESNTAL_NO"));				//게시글번호
		mapParam.put("SRVC_PVSN_RQST_YN", searchParam.getValue("SRVC_PVSN_RQST_YN"));			//서비스제공의뢰여부
		
		ParameterGroup searchtime = dataRequest.getParameterGroup("dmTime");
		mapParam.put("START_DATE", searchtime.getValue("startDate"));						//조회시작날짜
		mapParam.put("END_DATE", searchtime.getValue("endDate"));							//조회끝날짜
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginRoleCd = loginVO.getEnfsnRoleSeCd();
//		System.out.println("loginRoleCd sec list ::"+loginRoleCd);
		
		Map<String, Object> oUserID = new HashMap<String, Object>();
		oUserID.put("oUserID", loginRoleCd);
		oUserID.put("oUserNM", loginVO.getUserName());
		
		List<Map<String , Object>> dsBoardList = bbsoutListService.selectBbsoutList(mapParam);
		
		totalCount = bbsoutListService.getTotalCount(mapParam);
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();

		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		dataRequest.setResponse("dsBoardList", dsBoardList);
		dataRequest.setResponse("dmPage", resPage);
		dataRequest.setResponse("dmUser", oUserID);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectBbsoutDetail.do")
	public View selectBbsoutDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", dmDtlParam.getValue("BBSCTT_TYPE_SE_CD"));
		
		//조회수추가
		bbsoutListService.bbsoutDtlCnt(mapParam);
		
		//게시글 상세 조회
		List<Map<String, Object>> dsBoardList = bbsoutListService.selectBbsoutDetail(mapParam);
		
		dataRequest.setResponse("dsBoardList", dsBoardList);
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBbsoutCrisis.do")
	public View insertBbsoutCrisis(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {

		bbsoutListService.insertCrisis(request, dataRequest);		
		return new JSONDataView();
	}

	@RequestMapping("/saveBbsoutList.do")
	public View saveBbsoutList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = bbsoutListService.saveBbsoutList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("BBSCTT_TYPE_SE_CD", returnParam.get("BBSCTT_TYPE_SE_CD"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/onLoadBbsout.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		HttpSession session = request.getSession();
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
		
		//근무형태코드
		List<Map<String, Object>> dsWorkShapeCd = mgmtCmmnCodeService.selectCommonCodeUnit("WORK_SHAPE_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsWorkShapeCd", dsWorkShapeCd);
		
		//아웃리치상담방법코드
		List<Map<String, Object>> dsOutrcDscsnCd = mgmtCmmnCodeService.selectCommonCodeUnit("OUTRC_DSCSN_MTHD_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsOutrcDscsnCd", dsOutrcDscsnCd);
		
		//SNS코드
		List<Map<String, Object>> dsSnsCd = mgmtCmmnCodeService.selectCommonCodeUnit("SNS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSnsCd", dsSnsCd);

		//직업구분코드
		List<Map<String, Object>> dsNtabrdCscOccpSeCd = mgmtCmmnCodeService.selectCommonCode("NTABRD_CSC_OCCP_SE_CD");
		dataRequest.setResponse("dsNtabrdCscOccpSeCd", dsNtabrdCscOccpSeCd);

		//학력구분코드
		List<Map<String, Object>> dsAcbgSeCd = mgmtCmmnCodeService.selectCommonCode("ACBG_SE_CD");
		dataRequest.setResponse("dsAcbgSeCd", dsAcbgSeCd);

		//학년구분코드
		List<Map<String, Object>> dsGradeSeCd = mgmtCmmnCodeService.selectCommonCode("GRADE_SE_CD");
		dataRequest.setResponse("dsGradeSeCd", dsGradeSeCd);

		// 성별구분코드
		List<Map<String, Object>> dsSxdcSeCd = mgmtCmmnCodeService.selectCommonCode("SXDC_SE_CD");
		dataRequest.setResponse("dsSxdcSeCd", dsSxdcSeCd);
		
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
	
	

}

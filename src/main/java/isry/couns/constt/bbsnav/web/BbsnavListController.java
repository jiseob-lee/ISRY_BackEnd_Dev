/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbsnav.web;

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
import isry.couns.constt.bbsnav.service.BbsnavListService;
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
public class BbsnavListController extends IsryBaseController{
	
	@Resource(name = "bbsnavListService")
	private BbsnavListService bbsnavListService;
	
	@Resource(name = "counsService")
	private CounsService counsService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/selectBbsnavList.do")
	public View selectBbsnavList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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

		mapParam.put("WRTR_NM_ENCPT", searchParam.getValue("WRTR_NM_ENCPT"));		//작성자명
		mapParam.put("BBSCTT_TTL_NM", searchParam.getValue("BBSCTT_TTL_NM"));					//게시글제목
		mapParam.put("BBSCTT_ESNTAL_NO", searchParam.getValue("BBSCTT_ESNTAL_NO"));				//게시글번호
		mapParam.put("BBSCTT_CN", searchParam.getValue("BBSCTT_CN"));							//개시글내용
		mapParam.put("CRISIS_TYPE_SE_CD", searchParam.getValue("CRISIS_TYPE_SE_CD"));			//위기유형구분코드
		mapParam.put("PROBM_STTS_LCLAS_SE_CD", searchParam.getValue("PROBM_STTS_LCLAS_SE_CD"));	//문제상태대분류
		mapParam.put("PROBM_STTS_MLSFC_SE_CD", searchParam.getValue("PROBM_STTS_MLSFC_SE_CD"));	//문제상태중분류
		mapParam.put("PROBM_STTS_SCLAS_SE_CD", searchParam.getValue("PROBM_STTS_SCLAS_SE_CD"));	//문제상태소분류
		mapParam.put("PROBM_CAS_LCLAS_SE_CD", searchParam.getValue("PROBM_CAS_LCLAS_SE_CD"));	//문제원인대분류
		mapParam.put("PROBM_CAS_SCLAS_SE_CD", searchParam.getValue("PROBM_CAS_SCLAS_SE_CD"));	//문제원인소분류
		mapParam.put("ETC_CN", searchParam.getValue("ETC_CN"));									//문제상세
		
		mapParam.put("BBSCTT_TYPE_SE_CD", searchParam.getValue("BBSCTT_TYPE_SE_CD"));			// 상담실적구분
		mapParam.put("PROBM_STTS_REG", searchParam.getValue("PROBM_STTS_REG"));					// 문제상태미등록
		mapParam.put("RETE_DLIV_CMPTN_YN", searchParam.getValue("RETE_DLIV_CMPTN_YN"));			// 답글전달완료여부
		
		ParameterGroup searchTime = dataRequest.getParameterGroup("dmTime");
		mapParam.put("START_DATE", searchTime.getValue("startDate"));							//조회시작날짜
		mapParam.put("END_DATE", searchTime.getValue("endDate"));								//조회끝날짜
//		System.out.println("DDD : "+mapParam.toString());
		
		List<Map<String , Object>> dsBoardList = bbsnavListService.selectBbsnavList(mapParam);	
		totalCount = bbsnavListService.getTotalCount(mapParam);
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();

		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		dataRequest.setResponse("dsBoardList", dsBoardList);
		dataRequest.setResponse("dmPage", resPage);
		return new JSONDataView();
	}
	
	@RequestMapping("/selecBbsnavDetail.do")
	public View selecBbsnavDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", dmDtlParam.getValue("BBSCTT_TYPE_SE_CD"));
		
		//조회수추가
		bbsnavListService.bbsnavDtlCnt(mapParam);
		
		// 원글(AYE100) 상세 조회
		List<Map<String, Object>> dsBoardList = bbsnavListService.selectBbsnavDetail(mapParam);
		dataRequest.setResponse("dsBoardList", dsBoardList);
		
		// 답글(AYE120) 상세 조회
		if (!"31".equals(dmDtlParam.getValue("BBSCTT_TYPE_SE_CD"))) {
			List<Map<String, Object>> dsDtlReply = bbsnavListService.selectRespodDetail(mapParam);
			
			dataRequest.setResponse("dsDtlReply", dsDtlReply);
		}
		
		return new JSONDataView();
	}

	@RequestMapping("/saveBbsnavList.do")
	public View saveBbsnavList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = bbsnavListService.saveBbsnavList(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("BBSCTT_TYPE_SE_CD", returnParam.get("BBSCTT_TYPE_SE_CD"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/onLoadBbsnav.do")
	public View onLoadBbsnav(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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
		dataRequest.setResponse("dsNtabrdCscOccpSeCd", dsNtCsOcSeCd);
		
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
	
	@RequestMapping("/selectBbsnavRespodDetail.do")
	public View selectBbsnavRespodDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("RETE_ESNTAL_NO", dmDtlParam.getValue("RETE_ESNTAL_NO"));
		mapParam.put("NUM", dmDtlParam.getValue("NUM"));
		//조회수추가
		bbsnavListService.RespodDtlCnt(mapParam);
		//게시글 상세 조회
		List<Map<String, Object>> dsDtlReply = bbsnavListService.selectRespodDetail(mapParam);

		dataRequest.setResponse("dsDtlReply", dsDtlReply);
		return new JSONDataView();
	}
	
	@RequestMapping("/saveBbsnavRespod.do")
	public View saveBbsnavRespod(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> returnParam = bbsnavListService.saveRespod(request, dataRequest);
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("RETE_ESNTAL_NO", returnParam.get("RETE_ESNTAL_NO"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBbsnavCrisis.do")
	public View insertBbsnavCrisis(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {

		bbsnavListService.insertCrisis(request, dataRequest);		
		return new JSONDataView();
	}
	
	@RequestMapping("/selectSulmun.do")
	public View selectSulmun(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
//		System.out.println("DDD bbsnav dmSearch ===================== : "+dmSearch.toString());
		
		mapParam.put("BBSCTT_ESNTAL_NO", dmSearch.getValue("BBSCTT_ESNTAL_NO"));
		List<Map<String, Object>> dsList = bbsnavListService.selectSulmun(mapParam);
		
		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectCounselorList.do")
	public View selectCounselorList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsCouns", bbsnavListService.selectCounselorList(request, dataRequest));
		
		return new JSONDataView();
	}
	
	@RequestMapping("/insertCounselor.do")
	public View insertCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		bbsnavListService.insertCounselor(request, dataRequest);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/updateReteDlivCmptn.do")
	public View updateReteDlivCmptn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		bbsnavListService.updateReteDlivCmptn(request, dataRequest);
		
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
	 * @Method설명 : 기타상담게시판 상담자 할당 Delete
	 */
	@RequestMapping("/deleteCnsltntAsgn.do")
	public View deleteCnsltntAsgn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		counsService.deleteCnsltntAsgn(request, dataRequest);
		
		return new JSONDataView();
	}
}

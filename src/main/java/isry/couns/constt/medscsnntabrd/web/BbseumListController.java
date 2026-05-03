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
import isry.couns.constt.medscsnntabrd.service.BbseumListService;
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
//@RequestMapping("/medscsnntabrd")
@RequestMapping("/bbseumList")
public class BbseumListController extends IsryBaseController{
	
	@Resource(name = "bbseumListService")
	private BbseumListService bbseumListService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/selectBbseumList.do")
	public View selectBbseumList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
//		int totalCount = 0;

		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		mapParam.put("WRTR_NM_ENCPT", searchParam.getValue("WRTR_NM_ENCPT"));				//작성자명
		mapParam.put("BBSCTT_TTL_NM", searchParam.getValue("BBSCTT_TTL_NM"));				//게시글제목
		mapParam.put("BBSCTT_ESNTAL_NO", searchParam.getValue("BBSCTT_ESNTAL_NO"));			//게시글번호
		mapParam.put("CRISIS_TYPE_SE_CD", searchParam.getValue("CRISIS_TYPE_SE_CD"));		//위기유형구분코드
		mapParam.put("PROBM_STTS_LCLAS_SE_CD", searchParam.getValue("PROBM_STTS_LCLAS_SE_CD"));	//문제상태대분류
		mapParam.put("PROBM_STTS_MLSFC_SE_CD", searchParam.getValue("PROBM_STTS_MLSFC_SE_CD"));	//문제상태중분류
		mapParam.put("PROBM_STTS_SCLAS_SE_CD", searchParam.getValue("PROBM_STTS_SCLAS_SE_CD"));	//문제상태소분류
		mapParam.put("PROBM_CAS_LCLAS_SE_CD", searchParam.getValue("PROBM_CAS_LCLAS_SE_CD"));	//문제원인대분류
		mapParam.put("PROBM_CAS_SCLAS_SE_CD", searchParam.getValue("PROBM_CAS_SCLAS_SE_CD"));	//문제원인소분류
		mapParam.put("ETC_CN", searchParam.getValue("ETC_CN"));									//문제상세

		ParameterGroup boardMenu = dataRequest.getParameterGroup("dmBoardMenu");			//게시판
		mapParam.put("BOARD_RESYN", boardMenu.getValue("brdReYn"));							//미답변,답변,본인상담
		
		mapParam.put("PROBM_STTS_REG", searchParam.getValue("PROBM_STTS_REG"));		// 문제상태미등록
		
		ParameterGroup searchtime = dataRequest.getParameterGroup("dmTime");
		mapParam.put("START_DATE", searchtime.getValue("startDate"));						//조회시작날짜
		mapParam.put("END_DATE", searchtime.getValue("endDate"));							//조회끝날짜

		List<Map<String , Object>> dsBoardList = null;
		String brdReYn = boardMenu.getValue("brdReYn");
		if(brdReYn.equals("0")) {
			dsBoardList = bbseumListService.selectBbseumList(mapParam);	//본인상담
		}else if(brdReYn.equals("1")) {
			dsBoardList = bbseumListService.nonRepSelectBbseumList(mapParam); //미답변
		}else if(brdReYn.equals("2")) {
			dsBoardList = bbseumListService.repSelectBbseumList(mapParam); //답변
		}
		// 카운터 오류로 주석처리 
		//totalCount = bbseumListService.getTotalCount(mapParam);
		
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
	
	@RequestMapping("/selectBbseumDetail.do")
	public View selectBbseumDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		//조회수추가
		bbseumListService.bbseumDtlCnt(mapParam);
		//게시글 상세 조회
		List<Map<String, Object>> dsBoardList = bbseumListService.selectBbseumDetail(mapParam);
		dataRequest.setResponse("dsBoardList", dsBoardList);
		
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("RETE_ESNTAL_NO", dmDtlParam.getValue("RETE_ESNTAL_NO"));
		mapParam.put("NNO", dmDtlParam.getValue("NNO"));
		mapParam.put("NUM", dmDtlParam.getValue("NUM"));
		//조회수추가
		bbseumListService.respodDtlCnt(mapParam);
		//게시글 상세 조회
		List<Map<String, Object>> dsDtlReply = bbseumListService.selectRespodDetail(mapParam);
		
		dataRequest.setResponse("dsDtlReply", dsDtlReply);
		
		//역할구분코드
		Map<String, Object> mapRoleCd = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
		String loginRoleCd = loginVO.getEnfsnRoleSeCd();
//		System.out.println("loginRoleCd::"+loginRoleCd);
		
		mapRoleCd.put("loginRoleCd", loginRoleCd);
		
		dataRequest.setResponse("dmRoleCd", mapRoleCd);
		
		return new JSONDataView();
	}

	@RequestMapping("/saveBbseumList.do")
	public View saveBbseumList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = bbseumListService.saveBbseumList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/onLoadBbseum.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		//위기유형구분코드
		List<Map<String, Object>> dsCriTySeCdCmb = mgmtCmmnCodeService.selectCommonCodeUnit("CRISIS_TYPE_SE_CD", userVo.getUntTaskwk());
		//문제상태대분류코드
		List<Map<String, Object>> dsSttsLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_LCLAS_SE_CD", userVo.getUntTaskwk());
		//문제상태중분류코드
		List<Map<String, Object>> dsSttsMlsfc = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_MLSFC_SE_CD", userVo.getUntTaskwk());
		//문제상태소분류코드
		List<Map<String, Object>> dsSttsSclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_SCLAS_SE_CD", userVo.getUntTaskwk());
		//문제원인대분류코드
		List<Map<String, Object>> dsCasLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_LCLAS_SE_CD", userVo.getUntTaskwk());
		//문제원인소분류코드
		List<Map<String, Object>> dsCasSclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_SCLAS_SE_CD", userVo.getUntTaskwk());
		//게시판상담실직업구분코드
		List<Map<String, Object>> dsNtCsOcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("NTABRD_CSC_OCCP_SE_CD", userVo.getUntTaskwk());
		//상담영역구분코드
		List<Map<String, Object>> dsReSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("DSCSN_RELM_SE_CD", userVo.getUntTaskwk());
		//성별구분코드
		List<Map<String, Object>> dsSxdcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk());
		//학력구분코드
		List<Map<String, Object>> dsAcbgSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("ACBG_SE_CD", userVo.getUntTaskwk());
		//학년구분코드
		List<Map<String, Object>> dsGradeSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("GRADE_SE_CD", userVo.getUntTaskwk());
		//이슈문제구분코드
		List<Map<String, Object>> dsIssProSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("ISSUE_PROBM_SE_CD", userVo.getUntTaskwk());
		String userIp = request.getRemoteAddr();
		Map<String, Object> dmIp = new HashMap<String, Object>();
		dmIp.put("CNTN_IP_ADDR", userIp);
		
		dataRequest.setResponse("dmIp", dmIp);
		dataRequest.setResponse("dsCriTySeCdCmb", dsCriTySeCdCmb);
		dataRequest.setResponse("dsSttsLclas", dsSttsLclas);
		dataRequest.setResponse("dsSttsMlsfc", dsSttsMlsfc);
		dataRequest.setResponse("dsSttsSclas", dsSttsSclas);
		dataRequest.setResponse("dsCasLclas", dsCasLclas);
		dataRequest.setResponse("dsCasSclas", dsCasSclas);
		dataRequest.setResponse("dsNtCsOcSeCd", dsNtCsOcSeCd);
		dataRequest.setResponse("dsReSeCd", dsReSeCd);
		dataRequest.setResponse("dsSxdcSeCd", dsSxdcSeCd);
		dataRequest.setResponse("dsAcbgSeCd", dsAcbgSeCd);
		dataRequest.setResponse("dsGradeSeCd", dsGradeSeCd);
		dataRequest.setResponse("dsIssProSeCd", dsIssProSeCd);
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBbseumCounselor.do")
	public View insertBbseumCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
//		Map<String, Object> mapParam = new HashMap<String, Object>();
//		ParameterGroup dmCouns = dataRequest.getParameterGroup("dmCouns");
//		mapParam.put("WORK_YMD", dmCouns.getValue("WORK_YMD"));
//		List<Map<String, Object>> dsCouns = bbseumListService.insertCounselor(mapParam);
//		dataRequest.setResponse("dsCouns", dsCouns);
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmCouns = dataRequest.getParameterGroup("dmCouns");
		mapParam.put("WORK_YMD", dmCouns.getValue("WORK_YMD"));
		List<Map<String, Object>> dsCouns = bbseumListService.insertCounselor(mapParam);
		
		dataRequest.setResponse("dsCouns", dsCouns);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBbseumCrisis.do")
	public View insertBbseumCrisis(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		bbseumListService.insertCrisis(request, dataRequest);
		
		return new JSONDataView();
	}

	@RequestMapping("/selectBbseumRespodDetail.do")
	public View selectBbseumRespodDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("RETE_ESNTAL_NO", dmDtlParam.getValue("RETE_ESNTAL_NO"));
		mapParam.put("NNO", dmDtlParam.getValue("NNO"));
		mapParam.put("NUM", dmDtlParam.getValue("NUM"));
		//조회수추가
		bbseumListService.respodDtlCnt(mapParam);
		//게시글 상세 조회
		List<Map<String, Object>> dsDtlReply = bbseumListService.selectRespodDetail(mapParam);
		
		dataRequest.setResponse("dsDtlReply", dsDtlReply);
		return new JSONDataView();
	}
	
	@RequestMapping("/saveBbseumRespod.do")
	public View saveBbseumRespod(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {

		Map<String, Object> returnParam = bbseumListService.saveRespod(request, dataRequest);
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");
//		System.out.println("SSsssss3");
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/saveBbbEumCounselor.do")
	public View saveBbbEumCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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
		mapParam.put("CONSTT_ID", dmCounselor.getValue("CONSTT_ID"));
		mapParam.put("REG_DT", dmCounselor.getValue("REG_DT"));
		mapParam.put("FRST_RGTR_ID", userId);
		mapParam.put("LAST_MDFR_ID", userId);
		
		
		bbseumListService.saveCounselor(mapParam);
				
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBbsEumMemo.do")
	public View insertBbsEumMemo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmMemo = dataRequest.getParameterGroup("dmMemo");
//		System.out.println("dsdsdsdsmemoemo"+dmMemo.toString());
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		mapParam.put("BBSCTT_ESNTAL_NO", dmMemo.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("CONSTT_ID", dmMemo.getValue("CONSTT_ID"));
		mapParam.put("MEMO_NM", dmMemo.getValue("MEMO_NM"));
		mapParam.put("FRST_RGTR_ID", userId);
		mapParam.put("LAST_MDFR_ID", userId);
		bbseumListService.insertMemo(mapParam);
		return new JSONDataView();
	}
	
	
	@RequestMapping("/eumContentList.do")
	public View eumContentList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		List<Map<String, String>> dsList = bbseumListService.eumContentList(dataRequest);

		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	
	@RequestMapping("/eumContentInsert.do")
	public View eumContentInsert(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		bbseumListService.eumContentInsert(request, dataRequest);

		return null;
	}
	
	@RequestMapping("/saveMail.do")
	public View saveMail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		bbseumListService.saveMail(request, dataRequest);

		return null;
	}

}

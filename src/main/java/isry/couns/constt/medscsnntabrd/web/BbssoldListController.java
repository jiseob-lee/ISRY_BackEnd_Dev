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
import isry.couns.constt.medscsnntabrd.service.BbssoldListService;
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
@RequestMapping("/bbssoldList")
public class BbssoldListController extends IsryBaseController{
	
	@Resource(name = "bbssoldListService")
	private BbssoldListService bbssoldListService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/selectBbssoldList.do")
	public View selectBbssoldList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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
		mapParam.put("WRTR_NM_ENCPT", searchParam.getValue("WRTR_NM_ENCPT"));		//작성자명
		mapParam.put("BBSCTT_TTL_NM", searchParam.getValue("BBSCTT_TTL_NM"));						//게시글제목
		mapParam.put("BBSCTT_ESNTAL_NO", searchParam.getValue("BBSCTT_ESNTAL_NO"));					//게시글번호
		mapParam.put("CRISIS_TYPE_SE_CD", searchParam.getValue("CRISIS_TYPE_SE_CD"));				//위기유형구분코드
		
		mapParam.put("PROBM_STTS_LCLAS_SE_CD", searchParam.getValue("PROBM_STTS_LCLAS_SE_CD"));		//문제상태대분류
		mapParam.put("PROBM_CAS_LCLAS_SE_CD", searchParam.getValue("PROBM_CAS_LCLAS_SE_CD"));		//문제원인대분류
		
		mapParam.put("PROBM_STTS_REG", searchParam.getValue("PROBM_STTS_REG"));		// 문제상태미등록
		
		ParameterGroup searchtime = dataRequest.getParameterGroup("dmTime");
		mapParam.put("START_DATE", searchtime.getValue("startDate"));						//조회시작날짜
		mapParam.put("END_DATE", searchtime.getValue("endDate"));							//조회끝날짜
		
		List<Map<String , Object>> dsBoardList = bbssoldListService.selectBbssoldList(mapParam);
		
		//totalCount = bbssoldListService.getTotalCount(mapParam);
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		//전체 게시글 수
		if(dsBoardList.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", dsBoardList.get(0).get("TOTAL_COUNT"));
		}

		//resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		dataRequest.setResponse("dsBoardList", dsBoardList);
		dataRequest.setResponse("dmPage", resPage);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectBbssoldDetail.do")
	public View selectBbssoldDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		// 문제상태 대분류
		List<Map<String, Object>> dsSttsLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsLclas", dsSttsLclas);
		// 문제상태 중분류
		List<Map<String, Object>> dsSttsMlsfc = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_MLSFC_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsMlsfc", dsSttsMlsfc);
		// 문제상대 소분류
		List<Map<String, Object>> dsSttsSclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_SCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsSclas", dsSttsSclas);
		// 문제원인 대분류
		List<Map<String, Object>> dsCasLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasLclas", dsCasLclas);
		// 문제원인 소분류
		List<Map<String, Object>> dsCasSclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_SCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasSclas", dsCasSclas);
				
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		//조회수추가
		bbssoldListService.bbssoldDtlCnt(mapParam);
		
		//게시글 상세 조회
		List<Map<String, Object>> dsBoardList = bbssoldListService.selectBbssoldDetail(mapParam);
		dataRequest.setResponse("dsBoardList", dsBoardList);
		
		//역할구분코드
		Map<String, Object> mapRoleCd = new HashMap<String, Object>();

		String loginRoleCd = userVo.getEnfsnRoleSeCd();
		//System.out.println("loginRoleCd sec detail !! ::"+loginRoleCd);
		mapRoleCd.put("loginRoleCd", loginRoleCd);

		dataRequest.setResponse("dmRoleCd", mapRoleCd);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/selectBbssoldReplyList.do")
    public View selectBbssoldReplyList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		String loginId = loginVO.getId();
		//System.out.println("loginId :::::::::::: "+loginId);
		Map<String, Object> mapParam = new HashMap<String, Object>();

		ParameterGroup rplyParam = dataRequest.getParameterGroup("dmRplyParam");
		mapParam.put("BBSCTT_ESNTAL_NO", rplyParam.getValue("BBSCTT_ESNTAL_NO"));
		
		mapParam.put("loginId", loginId);
		
		//댓글리스트
		List<Map<String, Object>> dsReply = bbssoldListService.selectBbssoldReplyList(mapParam);
		
        dataRequest.setResponse("dsReply", dsReply);      
        
      //역할구분코드
		Map<String, Object> mapRoleCd = new HashMap<String, Object>();
		
//		HttpSession session = request.getSession();
//		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginRoleCd = loginVO.getEnfsnRoleSeCd();
		//System.out.println("loginRoleCd sec detail !! ::"+loginRoleCd);
		mapRoleCd.put("loginRoleCd", loginRoleCd);
	
		dataRequest.setResponse("dmRoleCd", mapRoleCd);
		
		
		return new JSONDataView();
    }

	@RequestMapping("/saveBbssolReply.do")
	public View saveBbssolReply(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> returnParam = bbssoldListService.saveBbssoldReply(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("CMNT_ESNTAL_NO", returnParam.get("CMNT_ESNTAL_NO"));

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/deleteBbssold.do")
	public View deleteBbssold(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		bbssoldListService.deleteBbssold(request, dataRequest);

		return new JSONDataView();
	}
	
	@RequestMapping("/onLoadBbssold.do")
	public View onLoadBbssold(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		List<Map<String, Object>> dsCriTySeCdCmb = mgmtCmmnCodeService.selectCommonCodeUnit("CRISIS_TYPE_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsSxdcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk());
		
		//문제상태대분류코드
		List<Map<String, Object>> dsSttsLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsLclas", dsSttsLclas);

		//문제원인대분류코드
		List<Map<String, Object>> dsCasLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasLclas", dsCasLclas);
				
		dataRequest.setResponse("dsCriTySeCdCmb", dsCriTySeCdCmb);
		dataRequest.setResponse("dsSxdcSeCd", dsSxdcSeCd);
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBbssoldCounselor.do")
	public View insertBbssoldCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
//		Map<String, Object> mapParam = new HashMap<String, Object>();
//		ParameterGroup dmCouns = dataRequest.getParameterGroup("dmCouns");
//		mapParam.put("WORK_YMD", dmCouns.getValue("WORK_YMD"));
//		List<Map<String, Object>> dsCouns = bbssoldListService.insertCounselor(mapParam);
//		dataRequest.setResponse("dsCouns", dsCouns);
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmCouns = dataRequest.getParameterGroup("dmCouns");
		mapParam.put("WORK_YMD", dmCouns.getValue("WORK_YMD"));
		List<Map<String, Object>> dsCouns = bbssoldListService.insertCounselor(mapParam);

		dataRequest.setResponse("dsCouns", dsCouns);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBbssoldCrisis.do")
	public View insertBbssoldCrisis(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		bbssoldListService.insertCrisis(request, dataRequest);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/saveBbbSsoldCounselor.do")
	public View saveBbbSsoldCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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
		
		bbssoldListService.saveCounselor(mapParam);
				
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBbsSsoldMemo.do")
	public View insertBbsSsoldMemo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmMemo = dataRequest.getParameterGroup("dmMemo");
		//System.out.println("dsdsdsdsmemoemo"+dmMemo.toString());
		
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
		bbssoldListService.insertMemo(mapParam); // 메모저장
		
		mapParam.put("PROBM_STTS_LCLAS_SE_CD", dmMemo.getValue("PROBM_STTS_LCLAS_SE_CD")); 
		mapParam.put("PROBM_STTS_MLSFC_SE_CD", dmMemo.getValue("PROBM_STTS_MLSFC_SE_CD"));
		mapParam.put("PROBM_STTS_SCLAS_SE_CD", dmMemo.getValue("PROBM_STTS_SCLAS_SE_CD"));
		mapParam.put("PROBM_CAS_LCLAS_SE_CD", dmMemo.getValue("PROBM_CAS_LCLAS_SE_CD"));
		mapParam.put("PROBM_CAS_SCLAS_SE_CD", dmMemo.getValue("PROBM_CAS_SCLAS_SE_CD"));
		mapParam.put("ETC_CN", dmMemo.getValue("ETC_CN"));
		bbssoldListService.updateProbmStts(mapParam); // 문제상태 저장
		
		return new JSONDataView();
	}
}

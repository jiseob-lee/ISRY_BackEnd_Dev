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

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.constt.medscsnntabrd.service.BbsmomListService;
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
@Api(value = "bbsmomListController Controller")
//@RequestMapping("/constt/medscsnntabrd")
@RequestMapping("/constt/bbsmomList")
public class BbsmomListController extends IsryBaseController{
	
	@Resource
	private BbsmomListService bbsmomListService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/bbsMomOnLoad.do")
    public View bbsMomOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		//문제상태대분류코드
		List<Map<String, Object>> dsSttsLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsLclas", dsSttsLclas);

		//문제원인대분류코드
		List<Map<String, Object>> dsCasLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasLclas", dsCasLclas);
		
		//역할구분코드
		Map<String, Object> mapRoleCd = new HashMap<String, Object>();
		
		String loginRoleCd = userVo.getEnfsnRoleSeCd();
//		System.out.println("loginRoleCd::"+loginRoleCd);
		mapRoleCd.put("loginRoleCd", loginRoleCd);
		
		List<Map<String, Object>> dsCriTySeCdCmb = mgmtCmmnCodeService.selectCommonCodeUnit("CRISIS_TYPE_SE_CD", userVo.getUntTaskwk());

		dataRequest.setResponse("dsCriTySeCdCmb", dsCriTySeCdCmb);
		
		dataRequest.setResponse("dmRoleCd", mapRoleCd);
		
		return new JSONDataView();
    }
	
	@RequestMapping("/selectBbsmomList.do")//게시글목록 조회
    public View selectBbsmomList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
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
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmDtlParam");
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
		
		List<Map<String , Object>> dsBoardList = bbsmomListService.selectBbsmomList(mapParam);
		
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
		dataRequest.setResponse("dsList", dsBoardList);
		dataRequest.setResponse("dmPage", resPage);
		return new JSONDataView();

    }	
	
	@RequestMapping("/counselorBbsMom.do")
    public View counselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup workParam = dataRequest.getParameterGroup("dmWorkYmd");
		
		mapParam.put("WORKYMD", workParam.getValue("REG_DT"));
		
		List<Map<String, Object>> dsCounselor = bbsmomListService.counselorList(mapParam);
		dataRequest.setResponse("dsCounselor", dsCounselor);
		
		return new JSONDataView();
    }
	
	@RequestMapping("/selectBbsmomDetail.do")//게시글상세 조회
    public View selectBbsmomDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup Param = dataRequest.getParameterGroup("dmDtlParam");//상세
		
		mapParam.put("BBSCTT_ESNTAL_NO", Param.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("RETE_ESNTAL_NO", Param.getValue("RETE_ESNTAL_NO"));
		mapParam.put("INDEX_SN", Param.getValue("INDEX_SN"));
		mapParam.put("CREATE_YN", Param.getValue("strCreateYn"));
//		indexSn != null && !indexSn.isEmpty()
		
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> dsBoardList = bbsmomListService.selectBbsmomDetail(mapParam);   
		List<Map<String, Object>> counselorBoardList = bbsmomListService.counselorBoardList(mapParam);
		
		//역할구분코드
		Map<String, Object> mapRoleCd = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
		String loginRoleCd = loginVO.getEnfsnRoleSeCd();
//		System.out.println("loginRoleCd::"+loginRoleCd);
		
		mapRoleCd.put("loginRoleCd", loginRoleCd);
		
		dataRequest.setResponse("dmRoleCd", mapRoleCd);
		dataRequest.setResponse("dsBoardList", dsBoardList);
		dataRequest.setResponse("dsCounselorList", counselorBoardList);
		
		return new JSONDataView();
    }
		
	@RequestMapping("/insertMomMemo.do")
	public View insertMomMemo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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
		bbsmomListService.insertMemo(mapParam);
		return new JSONDataView();
	}
	
	
	@RequestMapping("/saveBbsmom.do")//게시글(추가(insertBbsmom), 수정(updateBbsmom), 삭제(deleteBbsmom))
    public View saveBbsmom(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
//		Map<String, Object> returnParam = noticeBoardService.saveNoticeBoardList(request, dataRequest);
		Map<String, Object> returnParam = bbsmomListService.saveBbsmom(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		//bbsRplyListService.updateBbsRplyProc(request, dataRequest);   
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
        
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }
	
//	----------------------------------------------------------답글
	
	@RequestMapping("/saveBbsmomRply.do")//답글(추가(insertRespod), 수정(updateRespod), 삭제(deleteRespod))
    public View saveBbsmomRply(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
//		Map<String, Object> returnParam = noticeBoardService.saveNoticeBoardList(request, dataRequest);
		Map<String, Object> returnParam = bbsmomListService.saveBbsmomRply(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		//bbsRplyListService.updateBbsRplyProc(request, dataRequest);   
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("RETE_ESNTAL_NO", returnParam.get("RETE_ESNTAL_NO"));
        
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }

	
	@RequestMapping("/selectRespodDetail.do")//답글 상세 조회
    public View selectRespodDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup Param = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("RETE_ESNTAL_NO", Param.getValue("RETE_ESNTAL_NO"));
		mapParam.put("BBSCTT_ESNTAL_NO", Param.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("CREATE_YN", "Y");
		// 조회수 추가
//		noticeBoardService.updateNoticeBoardDtlList(mapParam);
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> dsRplyList = bbsmomListService.selectRespodDetail(mapParam);   
		
		dataRequest.setResponse("dsRplyList", dsRplyList);  
        // 게시판 기본 데이터 호출
 		List<Map<String, Object>> dsBoardList = bbsmomListService.selectBbsmomDetail(mapParam);   
 		
 		dataRequest.setResponse("dsBoardList", dsBoardList);
		return new JSONDataView();
    }
	
	@RequestMapping("/insertCounselor.do")
    public View insertCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
		Map<String, Object> returnParam = bbsmomListService.saveCounselor(request, dataRequest);
		
		Map<String, Object> message = new HashMap<String, Object>();
		   
		message.put("INDEX_SN", returnParam.get("INDEX_SN"));
        
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
    }
	
	@RequestMapping("/onLoadBbsmom.do")
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
		

		return new JSONDataView();
	}

}

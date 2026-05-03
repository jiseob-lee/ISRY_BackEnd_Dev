/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.etcntabrd.web;

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
import isry.couns.cmmn.service.CounsService;
import isry.couns.constt.etcntabrd.service.BbsEpilogoListService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.redis.service.RedisService;


@Controller
@Api(value = "bbsEpilogoListController Controller")
@RequestMapping("/constt/etcntabrd")
public class BbsEpilogoListController extends IsryBaseController {

	@Autowired
    private BbsEpilogoListService bbsEpilogoListService;
	
	@Resource(name = "counsService")
	private CounsService counsService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
//	@Autowired
//	private NoticeBoardService noticeBoardService;
	

	@RequestMapping("/onLoadBbsnav.do")
	public View onLoadBbsnav(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		//문제상태대분류코드
		List<Map<String, Object>> dsSttsLclas = mgmtCmmnCodeService.selectCommonCode("PROBM_STTS_LCLAS_SE_CD");
		dataRequest.setResponse("dsSttsLclas", dsSttsLclas);
		
		//문제원인대분류코드
		List<Map<String, Object>> dsCasLclas = mgmtCmmnCodeService.selectCommonCode("PROBM_CAS_LCLAS_SE_CD");
		dataRequest.setResponse("dsCasLclas", dsCasLclas);
		
		HttpSession session = request.getSession();
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
	
	@RequestMapping("/selectBbsEpilogoList.do")
    public View bbsEpilogoOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
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
		
//		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
//		Map<String, Object> resPage = new HashMap<String, Object>();
		
		ParameterGroup dtlParam = dataRequest.getParameterGroup("dmSearch");

		String wrtrNmEncpt		= dtlParam.getValue("WRTR_NM_ENCPT")	!= null ? dtlParam.getValue("WRTR_NM_ENCPT")	: "";
		
		mapParam.put("WRTR_NM_ENCPT"	, wrtrNmEncpt);
		mapParam.put("BBSCTT_TTL_NM"	, dtlParam.getValue("BBSCTT_TTL_NM"));
		mapParam.put("BBSCTT_ESNTAL_NO"	, dtlParam.getValue("BBSCTT_ESNTAL_NO"));
		
		mapParam.put("PROBM_STTS_LCLAS_SE_CD", dtlParam.getValue("PROBM_STTS_LCLAS_SE_CD"));	//문제상태대분류
		mapParam.put("PROBM_CAS_LCLAS_SE_CD", dtlParam.getValue("PROBM_CAS_LCLAS_SE_CD"));		//문제원인대분류
		mapParam.put("PROBM_STTS_REG", dtlParam.getValue("PROBM_STTS_REG"));					// 문제상태미등록
		
		ParameterGroup boardMenu = dataRequest.getParameterGroup("dmBoardMenu");			//게시판
		mapParam.put("BOARD_RESYN", boardMenu.getValue("brdReYn"));							//미답변,답변,본인상담
		
		ParameterGroup searchTime = dataRequest.getParameterGroup("dmTime");
		mapParam.put("START_DATE", searchTime.getValue("startDate"));						//조회시작날짜
		mapParam.put("END_DATE", searchTime.getValue("endDate"));							//조회끝날짜
		
//		ParameterGroup searchUser = dataRequest.getParameterGroup("dmUser");
//		System.out.println("dmUser ::::::::: " + searchUser.getValue("oUserId") );
		
		List<Map<String , Object>> dsBoardList = null;
		// 게시판 메뉴 comboBox
		String brdReYn = boardMenu.getValue("brdReYn");
		
//		System.out.println("brdReYn ::::::::: " + brdReYn );
		
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginRoleCd = loginVO.getEnfsnRoleSeCd();
//		System.out.println("loginRoleCd sec list ::"+loginRoleCd);
		
		if("3".equals(loginRoleCd)) {
//			System.out.println("상담원~~~~");
			String loginId = loginVO.getId();			
			
			mapParam.put("LOGIN_ID_DD", loginId);
		}
		
		// 선택한 게시판 메뉴에 따른 조회
		if(brdReYn.equals("0")) {
			dsBoardList = bbsEpilogoListService.selectBbsEpilogoList(mapParam);	// 전체
		}else if(brdReYn.equals("1")) {
			dsBoardList = bbsEpilogoListService.selectBbsEpilogoList1(mapParam); // 미답변
		}else if(brdReYn.equals("2")) {
			dsBoardList = bbsEpilogoListService.selectBbsEpilogoList2(mapParam); // 답변
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
	
	@RequestMapping("/selectBbsEpilogoDetail.do")
    public View selectInqBbsRplyListDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		Map<String, Object> autoSndngInfo = new HashMap<String, Object>();
        
		ParameterGroup Param = dataRequest.getParameterGroup("dmDtlParam");
		
		mapParam.put("BBSCTT_ESNTAL_NO", Param.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("CREATE_YN", Param.getValue("strCreateYn"));

		List<Map<String, Object>> boardDetail = bbsEpilogoListService.selectBbsEpilogoDetail(mapParam);    
		for (Map<String, Object> map : boardDetail) {
	        try {
					map.replace("WRTR_NM_ENCPT", map.get("WRTR_NM_ENCPT").toString());
					map.replace("EML_ADDR_ENCPT", map.get("EML_ADDR_ENCPT").toString());
					
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
		
		dataRequest.setResponse("dsBoardList", boardDetail);
		dataRequest.setResponse("dmAutoSndngInfo", autoSndngInfo);
		
		// 답글 기본 데이터 호출
		List<Map<String, Object>> boardReDetail = bbsEpilogoListService.selectBbsEpilogoRplyDetail(mapParam);   
		dataRequest.setResponse("dsRplyList", boardReDetail);
		
		return new JSONDataView();
    }

	@RequestMapping("/insertCounselor.do")
	public View insertCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmCouns = dataRequest.getParameterGroup("dmCouns");
		mapParam.put("WORK_YMD", dmCouns.getValue("WORK_YMD"));
		List<Map<String, Object>> dsCouns = bbsEpilogoListService.insertCounselor(mapParam);
				
		dataRequest.setResponse("dsCouns", dsCouns);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/saveCounselor.do")
	public View saveCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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
		
		bbsEpilogoListService.saveCounselor(mapParam);
				
		return new JSONDataView();
	}

	@RequestMapping("/insertEpilgMemo.do")
	public View insertEpilgMemo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		bbsEpilogoListService.insertEpilgMemo(request, dataRequest);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/saveBbsEpilogoAll.do")
	public View saveBbssol(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = bbsEpilogoListService.saveBbsEpilogoAll(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	// 2023.04.27 : 사용안함
	@RequestMapping("/selectBbsEpilogoReDetail.do")
    public View selectBbsEpilogoReDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
     
		Map<String, Object> mapParam = new HashMap<String, Object>();
        
		ParameterGroup Param = dataRequest.getParameterGroup("dmDtlParam");
		
		mapParam.put("BBSCTT_ESNTAL_NO", Param.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("RETE_ESNTAL_NO", Param.getValue("RETE_ESNTAL_NO"));
		mapParam.put("CREATE_YN", "Y");
//		rplyIndex != null && !rplyIndex.isEmpty()
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> boardDetail = bbsEpilogoListService.selectBbsEpilogoDetail(mapParam);    
		dataRequest.setResponse("dsBoardList", boardDetail);                
				
//		System.out.println("RETE_ESNTAL_NO ::::::::::::::::::::"+Param.getValue("RETE_ESNTAL_NO").toString());
		
		// 답글 기본 데이터 호출
		List<Map<String, Object>> boardReDetail = bbsEpilogoListService.selectBbsEpilogoRplyDetail(mapParam);   
		dataRequest.setResponse("dsRplyList", boardReDetail);
		
		// 메모 조회
		List<Map<String, Object>> dsMemoCn = bbsEpilogoListService.selectMemo(mapParam);		
		dataRequest.setResponse("dsMemoCn", dsMemoCn);
		
		return new JSONDataView();
    }
	
	// 2023.04.27 : 사용안함
	@RequestMapping("/saveBbsEpilogoProc.do")
    public View saveBbsEpilogoProc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
                 
		Map<String, Object> returnParam = bbsEpilogoListService.saveBbsEpilogoProc(request, dataRequest); 
		
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		
		dataRequest.setMetadata(true, message);
        
		return new JSONDataView();
    }
	
	// 2023.04.27 : 사용안함
	@RequestMapping("/saveBbsEpilogoReProc.do")
    public View saveBbsEpilogoReProc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		
		Map<String, Object> returnParam = bbsEpilogoListService.saveBbsEpilogoReProc(request, dataRequest); 
		
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("RETE_ESNTAL_NO", returnParam.get("RETE_ESNTAL_NO"));
		
		dataRequest.setMetadata(true, message);
        
		return new JSONDataView();
    }
	
	// 2023.04.27 : 사용안함
	@RequestMapping("/selectOnLoadBbsEpilogo.do")
    public View selectOnLoadBbsEpilogo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {		
		HttpSession session1 = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
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
		
		//학력구분코드
		List<Map<String, Object>> dsAcbgSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("ACBG_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsAcbgSeCd", dsAcbgSeCd);
		
		//학년구분코드
		List<Map<String, Object>> dsGradeSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("GRADE_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsGradeSeCd", dsGradeSeCd);
		
		//성별구분코드
		List<Map<String, Object>> dsSxdcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSxdcSeCd", dsSxdcSeCd);
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
	 * @Method설명 : 사이버상담후기 상담자 할당 Delete
	 */
	@RequestMapping("/deleteCnsltntAsgn.do")
	public View deleteCnsltntAsgn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		counsService.deleteCnsltntAsgn(request, dataRequest);
		
		return new JSONDataView();
	}
}

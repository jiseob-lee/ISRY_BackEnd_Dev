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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.clipsoft.org.jsoup.helper.DataUtil;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseController;
import isry.couns.cmmn.service.CounsService;
import isry.couns.constt.medscsnntabrd.service.BbssimListService;
import isry.couns.taskwksprt.taskwkandatdmng.web.TaskwkReprtsController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
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
@RequestMapping("/bbssimList")

public class BbssimListController extends IsryBaseController{
	
	@Resource(name = "bbssimListService")
	private BbssimListService bbssimListService;
	
	@Resource(name = "counsService")
	private CounsService counsService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskwkReprtsController.class);
	
	@RequestMapping("/selectBbssimList.do")
	public View selectBbssimList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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
									
		mapParam.put("WRTR_NM_ENCPT", searchParam.getValue("WRTR_NM_ENCPT"));				//작성자명
		mapParam.put("BBSCTT_TTL_NM", searchParam.getValue("BBSCTT_TTL_NM"));				//게시글제목
		mapParam.put("BBSCTT_ESNTAL_NO", searchParam.getValue("BBSCTT_ESNTAL_NO"));			//게시글번호
		mapParam.put("CRISIS_TYPE_SE_CD", searchParam.getValue("CRISIS_TYPE_SE_CD"));		//위기유형구분코드
		
		mapParam.put("CONSTT_NM_ENCPT", searchParam.getValue("CONSTT_NM_ENCPT")); //상담자명
		
		
		mapParam.put("PROBM_STTS_LCLAS_SE_CD", searchParam.getValue("PROBM_STTS_LCLAS_SE_CD"));		//문제상태대분류
		mapParam.put("PROBM_CAS_LCLAS_SE_CD", searchParam.getValue("PROBM_CAS_LCLAS_SE_CD"));		//문제원인대분류
		
		mapParam.put("PROBM_STTS_REG", searchParam.getValue("PROBM_STTS_REG"));		// 문제상태미등록
		
		mapParam.put("BBSCTT_TYPE_SE_CD", searchParam.getValue("BBSCTT_TYPE_SE_CD"));		// 댓글구분
		
		ParameterGroup searchtime = dataRequest.getParameterGroup("dmTime");		
		mapParam.put("START_DATE", searchtime.getValue("startDate"));						//조회시작날짜
		mapParam.put("END_DATE", searchtime.getValue("endDate"));							//조회끝날짜
		
//		ParameterGroup boardMenu = dataRequest.getParameterGroup("dmBoardMenu");			//게시판
//		mapParam.put("BOARD_RESYN", boardMenu.getValue("brdReYn"));							//미답변,답변,본인상담
		String brdReYn = searchParam.getValue("brdReYn");		
//		System.out.println("brdReYn ::::::::: " + brdReYn );
		
		List<Map<String , Object>> dsBoardList = null;
		
		if(brdReYn.equals("0")) { // 전체
			dsBoardList = bbssimListService.selectBbssimList(mapParam);
		}else if(brdReYn.equals("1")) { // 미답변
			dsBoardList = bbssimListService.selectBbssimList1(mapParam);
		}else if(brdReYn.equals("2")) { // 답변
			dsBoardList = bbssimListService.selectBbssimList2(mapParam);
		}
		
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
	
	@RequestMapping("/selectBbssimDetail.do")
	public View selectBbssimDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		Map<String, Object> autoSndngInfo = new HashMap<String, Object>();
		
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", dmDtlParam.getValue("BBSCTT_TYPE_SE_CD"));
		
		//조회수추가
		bbssimListService.bbssimDtlCnt(mapParam);
		
		//게시글 상세 조회
		List<Map<String, Object>> dsBoardList = bbssimListService.selectBbssimDetail(mapParam);

		for (Map<String, Object> map : dsBoardList) {
			
			autoSndngInfo.put("BBSCTT_ESNTAL_NO", map.get("BBSCTT_ESNTAL_NO"));
			autoSndngInfo.put("BBSCTT_TYPE_SE_CD", map.get("BBSCTT_TYPE_SE_CD"));
			autoSndngInfo.put("RECEIVER_NM", map.get("WRTR_NM_ENCPT"));
			autoSndngInfo.put("RECEIVER_EML", map.get("EML_ADDR_ENCPT"));
			autoSndngInfo.put("RECEIVER_TELNO", map.get("MBL_TELNO_ENCPT"));
			autoSndngInfo.put("CHRCTR_YN", map.get("CHRCTR_YN"));
			
        }
		
		if("03".equals(mapParam.get("BBSCTT_TYPE_SE_CD"))) { // 03:	이음-e(댓글)
			//이음-e 답변내용
			List<Map<String, Object>> dsBoardListEum = bbssimListService.selectBbssimDetailEum(mapParam);
			
			String eumContent = "";
			
//			System.out.println("dsBoardListEum.size() :::::::::::" + dsBoardListEum.size());
			
			if(dsBoardListEum.size() != 0) {
				for(int z=0; z<dsBoardListEum.size(); z++) {
					
					if (z == 0) {
						eumContent += "------------------------------------------------------- " + dsBoardListEum.get(z).get("CNCT_PAGE_SN") + " page" + " -------------------------------------------------------" + "<br>";
					} else if (!dsBoardListEum.get(z).get("CNCT_PAGE_SN").equals(dsBoardListEum.get(z-1).get("CNCT_PAGE_SN"))) {
						eumContent += "------------------------------------------------------- " + dsBoardListEum.get(z).get("CNCT_PAGE_SN") + " page" + " -------------------------------------------------------" + "<br>";
					} else {
						
					}
					
					if (dsBoardListEum.get(z).get("QESITM_ANS_CN") == null || "".equals(dsBoardListEum.get(z).get("QESITM_ANS_CN"))) {
						continue;
					}
					eumContent += dsBoardListEum.get(z).get("QESITM_ANS_CN") + "<br>";
					
				}
				dsBoardListEum.get(0).put("QESITM_ANS_CN", eumContent);
				dataRequest.setResponse("dsBoardListEum", dsBoardListEum);
			}			
		}
		
		dataRequest.setResponse("dsBoardList", dsBoardList);
		dataRequest.setResponse("dmAutoSndngInfo", autoSndngInfo);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/selectBbssimReplyList.do")
    public View selectBbssimReplyList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
		
		String loginId = "";			// 세션 정보의 ID
		
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		if (userVo != null && userVo.getId() != null && !"".equals(userVo.getId())) {
			loginId = userVo.getId();
		} else {
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
		}
		
		Map<String, Object> mapParam = new HashMap<String, Object>();

		ParameterGroup rplyParam = dataRequest.getParameterGroup("dmRplyParam");
		mapParam.put("BBSCTT_ESNTAL_NO", rplyParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", rplyParam.getValue("BBSCTT_TYPE_SE_CD"));
		mapParam.put("loginId", loginId);
		
		if("03".equals(mapParam.get("BBSCTT_TYPE_SE_CD"))) { // 03:	이음-e(댓글)
			
			// 이음-E 메일발송 내역 조회
			Map<String, Object> dmEumMailMap = bbssimListService.selectEumMailDetail(mapParam);
			LOGGER.debug("resultMap ::: " + dmEumMailMap);
			
			dataRequest.setResponse("dmEumMailDtl", dmEumMailMap);
		}
		
		//댓글리스트
		List<Map<String, Object>> dsReply = bbssimListService.selectBbssimReplyList(mapParam);
		
        dataRequest.setResponse("dsReply", dsReply);      
        
		return new JSONDataView();
    }

	@RequestMapping("/saveBbssimReply.do")
	public View saveBbssimReply(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		Map<String, Object> returnParam = bbssimListService.saveBbssimReply(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
//		message.put("BBSCTT_TYPE_SE_CD", returnParam.get("BBSCTT_TYPE_SE_CD"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/deleteBbssim.do")
	public View deleteBbssim(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		bbssimListService.deleteBbssim(request, dataRequest);

		return new JSONDataView();
	}
	
	@RequestMapping("/onLoadBbssim.do")
	public View onLoadBbssim(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
//		System.out.println("onLoadBbssim...........................................");
		List<Map<String, Object>> dsCriTySeCdCmb = mgmtCmmnCodeService.selectCommonCodeUnit("CRISIS_TYPE_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsSxdcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk());
		
		//문제상태대분류코드
		List<Map<String, Object>> dsSttsLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsLclas", dsSttsLclas);

		//문제원인대분류코드
		List<Map<String, Object>> dsCasLclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_LCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasLclas", dsCasLclas);
		
		// 문제상태 중분류
		List<Map<String, Object>> dsSttsMlsfc = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_MLSFC_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsMlsfc", dsSttsMlsfc);
		// 문제상대 소분류
		List<Map<String, Object>> dsSttsSclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_STTS_SCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsSttsSclas", dsSttsSclas);
		// 문제원인 소분류
		List<Map<String, Object>> dsCasSclas = mgmtCmmnCodeService.selectCommonCodeUnit("PROBM_CAS_SCLAS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsCasSclas", dsCasSclas);
		
		
		//게시글유형구분코드
		List<Map<String, Object>> dsBbscttTypeSeCd = bbssimListService.selectBbscttTypeSeCd("BBSCTT_TYPE_SE_CD");
		dataRequest.setResponse("dsBbscttTypeSeCd", dsBbscttTypeSeCd);

		
		dataRequest.setResponse("dsCriTySeCdCmb", dsCriTySeCdCmb);
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
	
	@RequestMapping("/insertBbssimCounselor.do")
	public View insertBbssimCounselor(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dmCouns = dataRequest.getParameterGroup("dmCouns");
		mapParam.put("WORK_YMD", dmCouns.getValue("WORK_YMD"));

		List<Map<String, Object>> dsCouns = bbssimListService.insertCounselor(mapParam);
		
		dataRequest.setResponse("dsCouns", dsCouns);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBbssimCrisis.do")
	public View insertBbssimCrisis(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		bbssimListService.insertCrisis(request, dataRequest);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/saveBbbSsimCounselor.do")
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
		
		bbssimListService.saveCounselor(mapParam);
				
		return new JSONDataView();
	}
	
	@RequestMapping("/insertBbsSsimMemo.do")
	public View insertBbsSsoMemo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsBoardList");
		List<Map<String, String>> paramList = paramGroup.getUpdatedRowList();
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		mapParam.put("BBSCTT_TYPE_SE_CD", paramList.get(0).get("BBSCTT_TYPE_SE_CD"));
		mapParam.put("BBSCTT_ESNTAL_NO", paramList.get(0).get("BBSCTT_ESNTAL_NO"));
		mapParam.put("CONSTT_ID", userId);
		mapParam.put("MEMO_NM", paramList.get(0).get("MEMO_NM"));
		mapParam.put("FRST_RGTR_ID", userId);
		mapParam.put("LAST_MDFR_ID", userId);
		bbssimListService.insertMemo(mapParam); // 메모저장
		
		return new JSONDataView();
	}
	
	@RequestMapping("/updateBbssim.do")
	public View updateBbssim(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		bbssimListService.updateBbssim(request, dataRequest);
		
		return new JSONDataView();
	}
	
	@RequestMapping("/updateEmailSndng.do")
	public View updateEmailSndng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		bbssimListService.updateEmailSndng(request, dataRequest);
		
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
	 * @Method설명 : 기타 댓글상담 상담자 할당 Delete
	 */
	@RequestMapping("/deleteCnsltntAsgn.do")
	public View deleteCnsltntAsgn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		counsService.deleteCnsltntAsgn(request, dataRequest);
		
		return new JSONDataView();
	}
}

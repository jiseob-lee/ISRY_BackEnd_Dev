/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbsmcr.web;

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
import isry.couns.constt.bbscrs.service.BbscrsListService;
import isry.couns.constt.bbsmcr.service.BbsmcrListService;
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
public class BbsmcrListController extends IsryBaseController{
	
	@Resource(name = "BbsmcrListService")
	private BbsmcrListService bbsmcrListService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/selectBbsmcrList.do")
	public View selectBbsmcrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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
		mapParam.put("CONSTT_NM_ENCPT", searchParam.getValue("CONSTT_NM_ENCPT"));		//상담자명
		mapParam.put("CLIENA_NM_ENCPT", searchParam.getValue("CLIENA_NM_ENCPT"));		//내담자명
		mapParam.put("CRISIS_TYPE_SE_CD", searchParam.getValue("CRISIS_TYPE_SE_CD"));	//위기유형구분
		mapParam.put("DGDGR_SE_CD", searchParam.getValue("DGDGR_SE_CD"));				//위험도구분
		

		ParameterGroup searchtime = dataRequest.getParameterGroup("dmTime");
		
		mapParam.put("START_DATE", searchtime.getValue("startDate"));						//조회시작날짜
		mapParam.put("END_DATE", searchtime.getValue("endDate"));							//조회끝날짜
		List<Map<String , Object>> dsBoardList1 = null;
		List<Map<String , Object>> dsBoardList2 = null;

		ParameterGroup tab = dataRequest.getParameterGroup("dmTab");
		if(tab.getValue("TAB").equals("1")) {			//모바일
			dsBoardList1 = bbsmcrListService.selectBbsmcrList1(mapParam);
			for (Map<String, Object> map : dsBoardList1) {
		        try {
		        	if ("2".equals(map.get("NUM").toString())) {
		        		map.replace("CLIENA", "re : "+Masking.nameMasking(map.get("CLIENA").toString()));
		        	} else {
		        		map.replace("CLIENA", Masking.nameMasking(map.get("CLIENA").toString()));
		        	}
					
				} catch (Exception e) {
					// TODO: handle exception
				}
	        }
//			System.out.println("selectBbsmcrList dsBoardList1 :::::::::::::::: \n"+dsBoardList1.toString());
		}else if(tab.getValue("TAB").equals("2")) {		//오픈채팅
			dsBoardList2 = bbsmcrListService.selectBbsmcrList2(mapParam);
		}
		mapParam.put("TAB", tab.getValue("TAB"));
		totalCount = bbsmcrListService.getTotalCount(mapParam);
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();

		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dsBoardList1", dsBoardList1);
		dataRequest.setResponse("dsBoardList2", dsBoardList2);
		dataRequest.setResponse("dmPage", resPage);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectBbsmcrDetail.do")
	public View selectBbsmcrDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");

		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", dmDtlParam.getValue("BBSCTT_TYPE_SE_CD"));
		//게시글 상세 조회
		List<Map<String, Object>> dsList = bbsmcrListService.selectBbsmcrDetail(mapParam);
		
		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}

	@RequestMapping("/saveBbsmcrList.do")
	public View saveBbsmcrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = bbsmcrListService.saveBbsmcrList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectBbsmcrAftDetail.do")
	public View selectBbsmcrAftDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");

		mapParam.put("INDEX_SN", dmDtlParam.getValue("INDEX_SN"));
		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", dmDtlParam.getValue("BBSCTT_TYPE_SE_CD"));
		mapParam.put("AFTFCT_MNG_ESNTAL_NO", dmDtlParam.getValue("AFTFCT_MNG_ESNTAL_NO"));
		//게시글 상세 조회
		List<Map<String, Object>> dsList = bbsmcrListService.selectBbsmcrAftDetail(mapParam);
		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	
	@RequestMapping("/saveBbsmcrAftList.do")
	public View saveBbsmcrAftList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = bbsmcrListService.saveBbsmcrAftList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("BBSCTT_TYPE_SE_CD", returnParam.get("BBSCTT_TYPE_SE_CD"));
		message.put("INDEX_SN", returnParam.get("INDEX_SN"));
		message.put("AFTFCT_MNG_ESNTAL_NO", returnParam.get("AFTFCT_MNG_ESNTAL_NO"));

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/onLoadBbsmcr.do")
	public View onLoadBbsmcr(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		List<Map<String, Object>> dsCriTySeCdCmb = mgmtCmmnCodeService.selectCommonCodeUnit("CRISIS_TYPE_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsDgdgrSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("DGDGR_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsSxdcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsYngbgsSecd = mgmtCmmnCodeService.selectCommonCodeUnit("YNGBGS_DSCSN_WLFAR_CNTER_RQST_MTHD_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsFamSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("FAM_RQST_MTHD_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsCaceSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("DT_CACE_SHELTR_RQST_MTHD_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsEmrgSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("EMRG_RESC_RQST_MTHD_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsLeaderSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("LEADER_RQST_MTHD_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsEtcSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("ETC_SPCLTY_INST_RQST_MTHD_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsMblaSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("MBLA_PVSN_PURPS_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsNoraCsSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("NORA_CS_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsAftfctSeCd = mgmtCmmnCodeService.selectCommonCodeUnit("AFTFCT_MNG_TRGT_SE_CD", userVo.getUntTaskwk());
		
		dataRequest.setResponse("dsCriTySeCdCmb", dsCriTySeCdCmb);
		dataRequest.setResponse("dsDgdgrSeCd", dsDgdgrSeCd);
		dataRequest.setResponse("dsSxdcSeCd", dsSxdcSeCd);
		dataRequest.setResponse("dsYngbgsSecd", dsYngbgsSecd);
		dataRequest.setResponse("dsFamSeCd", dsFamSeCd);
		dataRequest.setResponse("dsCaceSeCd", dsCaceSeCd);
		dataRequest.setResponse("dsEmrgSeCd", dsEmrgSeCd);
		dataRequest.setResponse("dsLeaderSeCd", dsLeaderSeCd);
		dataRequest.setResponse("dsEtcSeCd", dsEtcSeCd);
		dataRequest.setResponse("dsMblaSeCd", dsMblaSeCd);
		dataRequest.setResponse("dsNoraCsSeCd", dsNoraCsSeCd);
		dataRequest.setResponse("dsAftfctSeCd", dsAftfctSeCd);
		return new JSONDataView();
	}


}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbscrs.web;

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
import isry.couns.constt.bbscrs.service.BbscrsListService;
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
public class BbscrsListController extends IsryBaseController{
	
	@Resource(name = "BbscrsListService")
	private BbscrsListService bbscrsListService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping("/selectBbscrsList.do")
	public View selectBbscrsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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
		mapParam.put("BBSCTT_TTL_NM", searchParam.getValue("BBSCTT_TTL_NM"));			//게시글 제목
		mapParam.put("CNSLTNT_NM_ENCPT", searchParam.getValue("CNSLTNT_NM_ENCPT"));		//상담자명
		mapParam.put("DGDGR_SE_CD", searchParam.getValue("DGDGR_SE_CD"));				//위험도구분
		mapParam.put("CRISIS_TYPE_SE_CD", searchParam.getValue("CRISIS_TYPE_SE_CD"));	//위기유형구분
		
		ParameterGroup tab = dataRequest.getParameterGroup("dmTab");
		mapParam.put("TAB", tab.getValue("TAB"));										//게시글 탭번호

		ParameterGroup searchtime = dataRequest.getParameterGroup("dmTime");
		mapParam.put("START_DATE", searchtime.getValue("startDate"));					//조회시작날짜
		mapParam.put("END_DATE", searchtime.getValue("endDate"));						//조회끝날짜
		List<Map<String , Object>> dsBoardList1 = null;
		List<Map<String , Object>> dsBoardList2 = null;
		List<Map<String , Object>> dsBoardList3 = null;
		List<Map<String , Object>> dsBoardList4 = null;
		List<Map<String , Object>> dsBoardList5 = null;
		List<Map<String , Object>> dsBoardList6 = null;
		List<Map<String , Object>> dsBoardList7 = null;	// (모바일)연계 상담 게시판 목록의 오픈채팅을 연계상담으로 이동
		
		/*
		if(tab.getValue("TAB").equals("1")) {			//비밀
			dsBoardList1 = bbscrsListService.selectBbscrsList1(mapParam);
			if (dsBoardList1.size() > 0) totalCount = Integer.parseInt(dsBoardList1.get(0).get("TOTAL_COUNT").toString());
		}else if(tab.getValue("TAB").equals("2")) {		//공개
			dsBoardList2 = bbscrsListService.selectBbscrsList2(mapParam);
			if (dsBoardList2.size() > 0) totalCount = Integer.parseInt(dsBoardList2.get(0).get("TOTAL_COUNT").toString());
		}else if(tab.getValue("TAB").equals("3")) {		//네이버
			dsBoardList3 = bbscrsListService.selectBbscrsList3(mapParam);
			if (dsBoardList3.size() > 0) totalCount = Integer.parseInt(dsBoardList3.get(0).get("TOTAL_COUNT").toString());
		}else if(tab.getValue("TAB").equals("4")) {		//채팅
			dsBoardList4 = bbscrsListService.selectBbscrsList4(mapParam);
			if (dsBoardList4.size() > 0) totalCount = Integer.parseInt(dsBoardList4.get(0).get("TOTAL_COUNT").toString());
		}else if(tab.getValue("TAB").equals("5")) {		//솔로봇
			dsBoardList5 = bbscrsListService.selectBbscrsList5(mapParam);
			if (dsBoardList5.size() > 0) totalCount = Integer.parseInt(dsBoardList5.get(0).get("TOTAL_COUNT").toString());
		}else if(tab.getValue("TAB").equals("6")) {		//아웃리치
			dsBoardList6 = bbscrsListService.selectBbscrsList6(mapParam);
			if (dsBoardList6.size() > 0) totalCount = Integer.parseInt(dsBoardList6.get(0).get("TOTAL_COUNT").toString());
		}else if(tab.getValue("TAB").equals("7")) {		//오픈채팅
			dsBoardList7 = bbscrsListService.selectBbscrsList7(mapParam);
			if (dsBoardList7.size() > 0) totalCount = Integer.parseInt(dsBoardList7.get(0).get("TOTAL_COUNT").toString());
		}
		*/
		// 탭 순서 변경으로 전체 수정
		if(tab.getValue("TAB").equals("3")) {			//비밀
			dsBoardList1 = bbscrsListService.selectBbscrsList1(mapParam);
			if (dsBoardList1.size() > 0) totalCount = Integer.parseInt(dsBoardList1.get(0).get("TOTAL_COUNT").toString());
		}else if(tab.getValue("TAB").equals("7")) {		//공개
			dsBoardList2 = bbscrsListService.selectBbscrsList2(mapParam);
			if (dsBoardList2.size() > 0) totalCount = Integer.parseInt(dsBoardList2.get(0).get("TOTAL_COUNT").toString());
		}else if(tab.getValue("TAB").equals("5")) {		//네이버
			dsBoardList3 = bbscrsListService.selectBbscrsList3(mapParam);
			if (dsBoardList3.size() > 0) totalCount = Integer.parseInt(dsBoardList3.get(0).get("TOTAL_COUNT").toString());
		}else if(tab.getValue("TAB").equals("1")) {		//채팅
			dsBoardList4 = bbscrsListService.selectBbscrsList4(mapParam);
			if (dsBoardList4.size() > 0) totalCount = Integer.parseInt(dsBoardList4.get(0).get("TOTAL_COUNT").toString());
		}else if(tab.getValue("TAB").equals("4")) {		//솔로봇
			dsBoardList5 = bbscrsListService.selectBbscrsList5(mapParam);
			if (dsBoardList5.size() > 0) totalCount = Integer.parseInt(dsBoardList5.get(0).get("TOTAL_COUNT").toString());
		}else if(tab.getValue("TAB").equals("2")) {		//아웃리치
			dsBoardList6 = bbscrsListService.selectBbscrsList6(mapParam);
			if (dsBoardList6.size() > 0) totalCount = Integer.parseInt(dsBoardList6.get(0).get("TOTAL_COUNT").toString());
		}else if(tab.getValue("TAB").equals("6")) {		//오픈채팅
			dsBoardList7 = bbscrsListService.selectBbscrsList7(mapParam);
			if (dsBoardList7.size() > 0) totalCount = Integer.parseInt(dsBoardList7.get(0).get("TOTAL_COUNT").toString());
		}
		
		
//		totalCount = bbscrsListService.getTotalCount(mapParam);
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();

		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dsBoardList1", dsBoardList1);
		dataRequest.setResponse("dsBoardList2", dsBoardList2);
		dataRequest.setResponse("dsBoardList3", dsBoardList3);
		dataRequest.setResponse("dsBoardList4", dsBoardList4);
		dataRequest.setResponse("dsBoardList5", dsBoardList5);
		dataRequest.setResponse("dsBoardList6", dsBoardList6);
		dataRequest.setResponse("dsBoardList7", dsBoardList7);
		dataRequest.setResponse("dmPage", resPage);
		return new JSONDataView();
	}
	
	
	@RequestMapping("/selecBbscrsIndvList.do")
	public View selecBbscrsIndvList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
//		System.out.println("DDD selecBbscrsIndvList ............ "+dmDtlParam.toString());
		mapParam.put("TAB", dmDtlParam.getValue("TAB"));				//탭번호
		mapParam.put("CLIENA_ID", dmDtlParam.getValue("CLIENA_ID")); 	//내담자아이디
		mapParam.put("CONSTT_ID", dmDtlParam.getValue("CONSTT_ID")); 	//상담원아이디
		List<Map<String, Object>> dsList = bbscrsListService.selecBbscrsIndvList(mapParam);

		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	
	@RequestMapping("/selecBbscrsIndvDetail.do")
	public View selecBbscrsIndvDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
//		System.out.println("DDD : selecBbscrsIndvDetail : \n"+dmDtlParam.toString());

		mapParam.put("BBSCTT_ESNTAL_NO", dmDtlParam.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("BBSCTT_TYPE_SE_CD", dmDtlParam.getValue("BBSCTT_TYPE_SE_CD"));
		mapParam.put("CHRO_NO", dmDtlParam.getValue("CHRO_NO"));
		//게시글 상세 조회
		List<Map<String, Object>> dsList = bbscrsListService.selecBbscrsIndvDetail(mapParam);
		//System.out.println("dsList :::::::::::::::: \n"+dsList.toString());
		for (Map<String, Object> map : dsList) {
	    	String clienaNm			= map.get("CLIENA_NM_ENCPT")	!= null ? map.get("CLIENA_NM_ENCPT").toString()	: "";
	    	String consttNm			= map.get("CONSTT_NM")			!= null ? map.get("CONSTT_NM").toString()			: "";
	    	String mblTelNoEncpt	= map.get("MBL_TELNO_ENCPT")	!= null ? map.get("MBL_TELNO_ENCPT").toString()	: "";
	    	String trprEmlAddrEncpt	= map.get("TRPR_EML_ADDR_ENCPT")!= null ? map.get("TRPR_EML_ADDR_ENCPT").toString(): "";
	    	String picNmEncpt		= map.get("PIC_NM_ENCPT")		!= null ? map.get("PIC_NM_ENCPT").toString()		: "";

	    	map.replace("CLIENA_NM_ENCPT"		, clienaNm);			// 내담자명
	    	map.replace("CONSTT_NM"				, consttNm);			// 상담원명
	    	map.replace("MBL_TELNO_ENCPT"		, mblTelNoEncpt);		// 휴대전화번호암호화
	    	map.replace("TRPR_EML_ADDR_ENCPT"	, trprEmlAddrEncpt);	// 대상자이메일주소암호화
	    	map.replace("PIC_NM_ENCPT"			, picNmEncpt);			// 담당자명암호화
        }
		//System.out.println("dsList 1234 :::::::::::::::: \n"+dsList.toString());
		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}

	@RequestMapping("/saveBbscrsList.do")
	public View saveBbscrsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = bbscrsListService.saveBbscrsIndv(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/onLoadBbscrs.do")
	public View onLoadBbscrs(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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
		return new JSONDataView();
	}

	@RequestMapping("/selecClienaInfoList.do")
	public View selecClienaInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, String> mapParam = new HashMap<String, String>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		
		String bbscttEsntalNo	=	dmDtlParam.getValue("BBSCTT_ESNTAL_NO");
		String bbscttTypeSecd	=	dmDtlParam.getValue("BBSCTT_TYPE_SE_CD");
		String chroNo			=	dmDtlParam.getValue("CHRO_NO");

		mapParam.put("BBSCTT_ESNTAL_NO",	bbscttEsntalNo);
		mapParam.put("BBSCTT_TYPE_SE_CD",	bbscttTypeSecd);
		mapParam.put("CHRO_NO",				chroNo);
		
		List<Map<String, String>> dsClienaInfoList = new ArrayList<Map<String,String>>();
		
		if ("24".equals(bbscttTypeSecd)) {
			// 연계상담게시판  내담자 정보 조회_채팅
			dsClienaInfoList = bbscrsListService.selecClienaInfoChttList(mapParam);
		}else {
			//연계상담게시판  내담자 정보 조회
			dsClienaInfoList = bbscrsListService.selecClienaInfoList(mapParam);
		}
		
		for (Map<String, String> map : dsClienaInfoList) {						
	    	String clienaNm			= map.get("CLIENA_NM_ENCPT")	!= null	? map.get("CLIENA_NM_ENCPT")		: "";
	    	String mblTelnoEncpt	= map.get("MBL_TELNO_ENCPT")	!= null	? map.get("MBL_TELNO_ENCPT")		: "";
	    	String trprEmlAddrEncpt	= map.get("TRPR_EML_ADDR_ENCPT")!= null	? map.get("TRPR_EML_ADDR_ENCPT")	: "";

	    	map.replace("CLIENA_NM_ENCPT"		, clienaNm);			// 내담자명
	    	map.replace("MBL_TELNO_ENCPT"		, mblTelnoEncpt);		// 휴대번호
	    	map.replace("TRPR_EML_ADDR_ENCPT"	, trprEmlAddrEncpt);	// 이메일
        }

		dataRequest.setResponse("dsClienaInfoList", dsClienaInfoList);
		return new JSONDataView();
	}
}

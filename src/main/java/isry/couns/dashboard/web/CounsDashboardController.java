/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.dashboard.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.couns.dashboard.service.CounsDashboardService;
import isry.itgcms.util.ScpDb;

/**
 * @파일명        : CounsDashboardController.java
 * @프로그램 설명 	: 청소년상담 메인화면
 * - 
 * - 
 * @작성자        : Sin.Hyun.Jin
 * @작성일        : 2022. 12. 20. 
 * @수정자        : Sin.Hyun.Jin
 * @수정일        : 2022. 12. 20.
 * @수정내용       : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/couns/counsDashboard")
public class CounsDashboardController {	
	
	@Autowired
	private CounsDashboardService counsDashboardService;
	
	ScpDb scpDb = new ScpDb();
	
	@RequestMapping(value = "/selectCounsDashboardList.do")
	public View selectCounsDashboardList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, String> mapParam = new HashMap<String, String>();
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
			
		mapParam.put("P_TODAY"			, searchParam.getValue("P_TODAY"));				// 당일날짜 20220101
		mapParam.put("CURR_MONTH"		, searchParam.getValue("CURR_MONTH"));			// 당월 202201
		mapParam.put("CURR_YEAR"		, searchParam.getValue("CURR_YEAR"));			// 해당년도 2022
		mapParam.put("CONSTT_ID"		, searchParam.getValue("CONSTT_ID"));			// 상담원아이디
		mapParam.put("NTABRD_ESNTAL_NO"	, searchParam.getValue("NTABRD_ESNTAL_NO"));	// 게시판고유번호
		
//		// 상담원 근무시간 조회
//		Map<String, Object> workInfoMap = counsDashboardService.selectTodayWorkInfoByCnsltnt(mapParam);
//		mapParam.put("WORK_YMD", workInfoMap.get("WORK_YMD").toString());		
//		mapParam.put("WK_START_DT", workInfoMap.get("WK_START_DT").toString());		
//		mapParam.put("WK_END_DT", workInfoMap.get("WK_END_DT").toString());
		
		// 상담실적 : 
		List<Map<String, String>> dscsnPrfmncMainList = counsDashboardService.selectDscsnPrfmncMainList(mapParam);
		dataRequest.setResponse("dsDscsnPrfmncMain", dscsnPrfmncMainList);
		
		// 상담실적
		List<Map<String, String>> dscsnPrfmncDetailList = counsDashboardService.selectDscsnPrfmncDetailList(mapParam);
		dataRequest.setResponse("dsDscsnPrfmncDetail", dscsnPrfmncDetailList);
		
		// 오늘의일정 사이버상담
		List<Map<String, String>> mnthngSchdlcyberDscsnList = counsDashboardService.selectMnthngSchdlcyberDscsnList(mapParam);				
		dataRequest.setResponse("dsSchdlcyberDscs", mnthngSchdlcyberDscsnList);
		
		// 오늘의일정 사이버아웃리치
		List<Map<String, String>> mnthngSchdlcyberOutList = counsDashboardService.selectMnthngSchdlcyberOutList(mapParam);		
		dataRequest.setResponse("dsSchdlcyberOut", mnthngSchdlcyberOutList);
		
		// 오늘의일정 모니터링 담당자 조회
		List<Map<String, String>> mngrMntrgSchdlList = counsDashboardService.selectMngrMntrgSchdlList(mapParam);				
		dataRequest.setResponse("dsMngrMntrgSchd", mngrMntrgSchdlList);
		
		// 실시간게시판
		List<Map<String, String>> bbsonmList = counsDashboardService.selectBbsonmList(mapParam);		
		dataRequest.setResponse("dsBbsonmL", bbsonmList);
		
		// 특별관리대상
		List<Map<String, String>> spclaList = counsDashboardService.selectSpclaList(mapParam);
		dataRequest.setResponse("dsSpcla", spclaList);
		
		// 퇴근처리 기본정보 조회
		dataRequest.setResponse("dmLvffcPrcsBassInfo" , counsDashboardService.selectLvffcPrcsBassInfo(request, dataRequest));
		// 다음출근시간 조회
		dataRequest.setResponse("dmNextAtendb" , counsDashboardService.selectNextAtndb(request, dataRequest));
		// 오늘출근시간 조회
		dataRequest.setResponse("dmAtendb" , counsDashboardService.selectAtndb(request, dataRequest));
		// 공지사항
		List<Map<String, String>> noticeList = counsDashboardService.selectNoticeList(mapParam);
		dataRequest.setResponse("dsNotice", noticeList);
		
		return new JSONDataView();
	}	
	
	/**
	 * 퇴근처리 저장
	 * @Method명   : lvffcPrcsSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Sin.Hyun.Jin
	 * @작성일     : 2023. 01. 02. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subLvffcPrcsSave.do")
	@ResponseBody
	public View lvffcPrcsSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {				
		counsDashboardService.updatelvffcPrcs(request, dataRequest);
		return new JSONDataView();
	}
	
	/**
	 * 퇴근처리 저장
	 * @Method명   : lvffcPrcsDelete
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2023. 01. 02. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subLvffcPrcsDelete.do")
	@ResponseBody
	public View lvffcPrcsDelete(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {				
		counsDashboardService.deleteLvffcPrcs(request, dataRequest);
		return new JSONDataView();
	}
}

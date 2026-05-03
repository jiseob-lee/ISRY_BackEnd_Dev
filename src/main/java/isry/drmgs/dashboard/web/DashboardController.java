/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.dashboard.web;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

//import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseController;
import isry.drmgs.dashboard.service.DashboardService;
import isry.drmgs.link.service.OfcdcLinkAplyService;
import isry.itgcms.itgBrd.service.ItgBrdCmnService;
import isry.itgcms.itgBrd.service.ItgQnaBrdService;

/**
 * @파일명        : ChupPreconController.java
 * @프로그램 설명 : 건강검진현황
 * - 
 * - 
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2022. 7. 13. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2022. 7. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/drmgs/dashboard")
public class DashboardController extends IsryBaseController {
	
	@Autowired
	private ItgBrdCmnService itgBrdCmnService;
	
	@Autowired
	private ItgQnaBrdService itgQnaBrdService;
	
	@Autowired
	private DashboardService dashboardService;
	
	@Autowired
	private OfcdcLinkAplyService ofcdcLinkAplyService;
	
	@RequestMapping(value = "/selectDashboardList.do")
	public View selectDashboardList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("UNT_TASKWK_SE_CD", "U03");
		mapParam.put("START_IDX", 0);
		mapParam.put("ROW_COUNT", 5);
		mapParam.put("UNITY_BBSCTT_CTGRYB_SE_CD", "");
		
		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", "001"); // 공지사항
		List<Map<String, Object>> noticeList = itgBrdCmnService.selectItgCmnBrdImprtnList(mapParam);
		dataRequest.setResponse("dsNotice", noticeList);

		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", "002"); // 자료실
		List<Map<String, Object>> referenceList = itgBrdCmnService.selectItgCmnBrdList(mapParam);
		dataRequest.setResponse("dsReference", referenceList);
		
		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", "003"); // 지역별 정보공유
		List<Map<String, Object>> areaNoticeList = itgBrdCmnService.selectItgCmnBrdList(mapParam);
		dataRequest.setResponse("dsAreaNotice", areaNoticeList);
		
		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", "004"); // 지역별 자료공유
		List<Map<String, Object>> areaReferenceList = itgBrdCmnService.selectItgCmnBrdList(mapParam);
		dataRequest.setResponse("dsAreaReference", areaReferenceList);
		
		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", "005"); // FAQ
		List<Map<String, Object>> faqList = itgBrdCmnService.selectItgCmnBrdList(mapParam);
		dataRequest.setResponse("dsFaq", faqList);
		
		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", "006"); // 문의사항
		List<Map<String, Object>> inquiryList = itgQnaBrdService.selectItgQnaBrdList(mapParam);
		dataRequest.setResponse("dsInquiry", inquiryList);
		
		Map<String, Object> mapLink = new HashMap<String, Object>();
		mapLink.put("RCPT_UNT_TASKWK_SE_CD", "U03");
		mapLink.put("RCPT_SE_CD", "11");
		
		mapLink.put("LINK_TYPE_SE_CD", "06"); // 교육청 연계
		List<Map<String, Object>> eduLinkcnt = dashboardService.selectEduLinkCnt(mapLink);
		dataRequest.setResponse("dsEduLink", eduLinkcnt);
		
		mapLink.put("LINK_TYPE_SE_CD", "05"); // 경찰청 연계
		List<Map<String, Object>> picLinkcnt = dashboardService.selectPicLinkCnt(mapLink);
		dataRequest.setResponse("dsPicLink", picLinkcnt);
		
		mapLink.put("LINK_TYPE_SE_CD", "00"); // 연계 접수
		List<Map<String, Object>> linkcnt = dashboardService.selectLinkCnt(mapLink);
		dataRequest.setResponse("dsLinkRcpt", linkcnt);
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectEduDashboardList.do")
	public View selectEduDashboardList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("UNT_TASKWK_SE_CD", "U03");
		mapParam.put("START_IDX", 0);
		mapParam.put("ROW_COUNT", 5);
		mapParam.put("UNITY_BBSCTT_CTGRYB_SE_CD", "");
		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", "001"); // 공지사항
		List<Map<String, Object>> noticeList = itgBrdCmnService.selectItgCmnBrdImprtnList(mapParam);
		dataRequest.setResponse("dsNotice", noticeList);
		
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> mapChartParam = paramGroup.getSingleValueMap();
		mapChartParam.put("LINK_TYPE_SE_CD", "06"); // 교육청
		List<Map<String, Object>> chartData = dashboardService.selectChartData(mapChartParam);
		List<Map<String, Object>> dsList = new ArrayList<Map<String,Object>>(); 
		for(Map<String, Object> forMap : chartData) {
			Map<String, Object> newMap = new HashMap<String, Object>();
			newMap.put("name", forMap.get("NAME"));
			newMap.put("value", forMap.get("VALUE"));
			dsList.add(newMap);
		}
		dataRequest.setResponse("dsPieData", dsList);
		
		List<Map<String, Object>> barChartData = dashboardService.selectBarChartData(mapChartParam);
		List<Map<String, Object>> dsBarChartList = new ArrayList<Map<String,Object>>();
		
		LocalDate localDate = LocalDate.now(); // YYYY-MM-DDTHH:MI:SS.MIS
		for(int i = -11; i <= 0; i++) {
			int mon = localDate.getMonth().plus(i).getValue();
			String month = String.valueOf(mon);
			Map<String, Object> newMap = new HashMap<String, Object>();
			newMap.put("month", month + " 월");
			if(month.length() < 2) {
				month = "0" + month;
			}
			for(Map<String, Object> forMap : barChartData) {
				if(month.equals(forMap.get("MON").toString())) {
					newMap.put("value1", forMap.get("VALUE"));
				}
			}
			dsBarChartList.add(newMap);
		}
		dataRequest.setResponse("dsBarData", dsBarChartList);
		
		List<Map<String, String>> latelyLink = ofcdcLinkAplyService.selectLinkRqstList(request, dataRequest);
		
		// 5건만 보여준다
		List<Map<String, String>> rtnLatelyLink = new ArrayList<Map<String,String>>();
		int latelyLinkCnt = 0;
		if(latelyLink.size() >= 5) {
			latelyLinkCnt = 5;
		} else {
			latelyLinkCnt = latelyLink.size();
		}
		for(int i = 0; i < latelyLinkCnt; i++) {
			Map<String, String> rtnMap = new HashMap<String, String>();
			rtnMap = latelyLink.get(i);
			rtnLatelyLink.add(rtnMap);
		}
		
		dataRequest.setResponse("dsLatelyLink", rtnLatelyLink);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectPicDashboardList.do")
	public View selectPicDashboardList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("UNT_TASKWK_SE_CD", "U03");
		mapParam.put("START_IDX", 0);
		mapParam.put("ROW_COUNT", 5);
		mapParam.put("UNITY_BBSCTT_CTGRYB_SE_CD", "");
		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", "001"); // 공지사항
		List<Map<String, Object>> noticeList = itgBrdCmnService.selectItgCmnBrdImprtnList(mapParam);
		dataRequest.setResponse("dsNotice", noticeList);
		
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> mapChartParam = paramGroup.getSingleValueMap();
		mapChartParam.put("LINK_TYPE_SE_CD", "05"); // 경찰청
		List<Map<String, Object>> chartData = dashboardService.selectChartData(mapChartParam);
		List<Map<String, Object>> dsList = new ArrayList<Map<String,Object>>(); 
		for(Map<String, Object> forMap : chartData) {
			Map<String, Object> newMap = new HashMap<String, Object>();
			newMap.put("name", forMap.get("NAME"));
			newMap.put("value", forMap.get("VALUE"));
			dsList.add(newMap);
		}
		dataRequest.setResponse("dsPieData", dsList);
		
		List<Map<String, Object>> barChartData = dashboardService.selectBarChartData(mapChartParam);
		List<Map<String, Object>> dsBarChartList = new ArrayList<Map<String,Object>>();
		
		LocalDate localDate = LocalDate.now(); // YYYY-MM-DDTHH:MI:SS.MIS
		for(int i = -11; i <= 0; i++) {
			int mon = localDate.getMonth().plus(i).getValue();
			String month = String.valueOf(mon);
			Map<String, Object> newMap = new HashMap<String, Object>();
			newMap.put("month", month + " 월");
			if(month.length() < 2) {
				month = "0" + month;
			}
			for(Map<String, Object> forMap : barChartData) {
				if(month.equals(forMap.get("MON").toString())) {
					newMap.put("value1", forMap.get("VALUE"));
				}
			}
			dsBarChartList.add(newMap);
		}
		dataRequest.setResponse("dsBarData", dsBarChartList);
		
		List<Map<String, Object>> latelyLink = dashboardService.selectLatelyLink(mapChartParam);
		dataRequest.setResponse("dsLatelyLink", latelyLink);
		
		dataRequest.setResponse("dsList", ofcdcLinkAplyService.selectLinkRqstList(request, dataRequest));
		
		return new JSONDataView();
	}
}

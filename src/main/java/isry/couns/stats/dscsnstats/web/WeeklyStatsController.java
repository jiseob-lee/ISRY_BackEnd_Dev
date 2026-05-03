/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.stats.dscsnstats.service.WeeklyStatsService;

@Controller
@Api(value = "WeeklyStatsController Controller")
@RequestMapping("/weeklyStats")
public class WeeklyStatsController extends IsryBaseController {

    @Autowired
    private WeeklyStatsService svc;

     @RequestMapping("/list.do")
     public View list(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
             throws Exception {
        
         Map<String, Object> mapParam = new HashMap<String, Object>();
        
         ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
         log.debug("DDD : "+ searchParam.toString());        
         String writeType = searchParam.getValue("writeType"); //글작성타입
         String startDate = searchParam.getValue("startDate"); //
         String endDate = searchParam.getValue("endDate"); //
         String sxdcCd = searchParam.getValue("sxdcCd"); // 성별
         String tabIndex  = searchParam.getValue("TABINDEX"); // TABINDEX		
         String chttPrfmncVal = searchParam.getValue("chttPrfmncVal"); // 채팅실적
         
         mapParam.put("writeType", writeType);
         mapParam.put("startDate", startDate);
         mapParam.put("endDate", endDate);
         mapParam.put("sxdcCd", sxdcCd);
         mapParam.put("chttPrfmncVal", chttPrfmncVal);
         
         log.debug("tabIndex ::::::::::::: "  + tabIndex); 
         log.debug("writeType ::::::::::::: " + writeType);
         log.debug("chttPrfmncVal ::::::::::::: " + chttPrfmncVal);
         
         if("1".equals(tabIndex)) { // 월별 			
        	 
        	 if("1".equals(writeType)) { // 1:내담자글
            	 Map<String, Object> result = svc.listMm(mapParam);
                 dataRequest.setResponse("dsListMm", result.get("dsListMm"));                
                 Map<String, Object> resultChtt = svc.listChttMm(mapParam);
                 dataRequest.setResponse("dsListChttMm", resultChtt.get("dsListChttMm"));                 
             }else if("2".equals(writeType)) { // 2:상담자글
            	 Map<String, Object> result = svc.listConsttMm(mapParam);
            	 dataRequest.setResponse("dsListMm", result.get("dsListMm"));
            	 Map<String, Object> resultChtt = svc.listChttMm(mapParam);
                 dataRequest.setResponse("dsListChttMm", resultChtt.get("dsListChttMm")); 
             }
        	 
 		 }else if("2".equals(tabIndex)) { // 요일별 
 			 
 			if("1".equals(writeType)) { // 1:내담자글
 	        	 List<Map<String, Object>> list = svc.list(mapParam);
 	        	 dataRequest.setResponse("dsList", list);
 	        	 List<Map<String, Object>> listChtt = svc.listChtt(mapParam);
	        	 dataRequest.setResponse("dsListChtt", listChtt);
 	         }else if("2".equals(writeType)) { // 2:상담자글
 	        	 List<Map<String, Object>> list = svc.listConstt(mapParam);
 	        	 dataRequest.setResponse("dsList", list);
 	        	 List<Map<String, Object>> listChtt = svc.listChtt(mapParam);
	        	 dataRequest.setResponse("dsListChtt", listChtt);
 	         }
 			
 		 }else if("3".equals(tabIndex)) { // 시간대별
 			 
 			if("1".equals(writeType)) { // 1:내담자글
 	        	 List<Map<String, Object>> list = svc.listHour(mapParam);
 	        	 dataRequest.setResponse("dsListHour", list);
 	        	 List<Map<String, Object>> listChtt = svc.listChttHour(mapParam);
	        	 dataRequest.setResponse("dsListChttHour", listChtt);
 	         }else if("2".equals(writeType)) { // 2:상담자글
 	        	 List<Map<String, Object>> list = svc.listConsttHour(mapParam);
 	        	 dataRequest.setResponse("dsListHour", list);
 	        	 List<Map<String, Object>> listChtt = svc.listChttHour(mapParam);
	        	 dataRequest.setResponse("dsListChttHour", listChtt);
 	         }
 			
 		 }else if("4".equals(tabIndex)) { // 문제상태별
 			 
 			 if("1".equals(writeType)) { // 1:내담자글
	 			List<Map<String, Object>> list = svc.listProblem(mapParam);
	        	dataRequest.setResponse("dsListProblem", list);
	        	List<Map<String, Object>> listChtt = svc.listChttProblem(mapParam);
	 			dataRequest.setResponse("dsListChttProblem", listChtt);
			 }else if("2".equals(writeType)) { // 2:상담자글
				List<Map<String, Object>> list = svc.listConsttProblem(mapParam);
	        	dataRequest.setResponse("dsListProblem", list);
	        	List<Map<String, Object>> listChtt = svc.listChttProblem(mapParam);
	 			dataRequest.setResponse("dsListChttProblem", listChtt);
			 }
 			
 		 }
         
         return new JSONDataView();
     }
     
     // 채팅상담실적 통계_2023.01.19 이태호 추가
     @RequestMapping("/listChttDscsn.do")
     public View listChttDscsn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
             throws Exception {
        
         Map<String, Object> mapParam = new HashMap<String, Object>();
        
         ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
         log.debug("searchParam  :::::: "+ searchParam.toString());        

         String chttPrfmncVal = searchParam.getValue("CHTT_PRFMNC_VAL"); 	// 채팅실적
         String inqYear       = searchParam.getValue("INQ_YEAR"); 			// 통계년도        
         
         mapParam.put("chttPrfmncVal", chttPrfmncVal);
         mapParam.put("inqYear", inqYear);
         
         log.debug("chttPrfmncVal ::::::::::::: "  + chttPrfmncVal); 
         log.debug("inqYear ::::::::::::: " + inqYear);
         
         List<Map<String, Object>> listChttDscsn = svc.listChttDscsn(mapParam);
     	 dataRequest.setResponse("dsListChttDscsn", listChttDscsn);
         
         return new JSONDataView();
     }

}
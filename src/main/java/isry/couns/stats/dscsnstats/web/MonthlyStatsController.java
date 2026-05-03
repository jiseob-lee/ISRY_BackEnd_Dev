/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.couns.stats.dscsnstats.service.MonthlyStatsService;

@Controller
@Api(value = "MonthlyStatsController Controller")
@RequestMapping("/monthlyStats")
public class MonthlyStatsController {

    @Resource(name = "monthlyStatsService")
    private MonthlyStatsService monthlyStatsService;
    
//    @RequestMapping("/selectMonthlyStats.do")
    @RequestMapping("/monthCount.do")
    public View selectMonthlyStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//System.out.println("DDD : "+ searchParam.toString()); 
        
        String writeType = searchParam.getValue("writeType"); //글작성타입
        String memType = searchParam.getValue("memType"); //회원여부
        String gender = searchParam.getValue("gender"); //성별
        String startDate = searchParam.getValue("startDate");
        String endDate = searchParam.getValue("endDate");
        
        mapParam.put("writeType", writeType);
        mapParam.put("memType", memType);
        mapParam.put("gender", gender);
        mapParam.put("startDate", startDate);
        mapParam.put("endDate", endDate);
        
//        log.debug("DDD 2 "); 
        Map<String, Object> result = monthlyStatsService.selectMonthlyStats(mapParam);

        dataRequest.setResponse("dsList", result.get("dsList"));
        dataRequest.setResponse("dsChartList", result.get("dsChartList"));
        
        return new JSONDataView();

    }
    
//    @RequestMapping("/selectMonthlyStatsDetail.do")
    @RequestMapping("/monthDetailCount.do")
    public View selectMonthlyStatsDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//System.out.println("DDD : "+ searchParam.toString()); 
        
        String writeType = searchParam.getValue("writeType"); //글작성타입
        String memType = searchParam.getValue("memType"); //회원여부
        String gender = searchParam.getValue("gender"); //성별
        String yearMonth = searchParam.getValue("yearMonth"); 
        
        mapParam.put("yearMonth", yearMonth);
        mapParam.put("writeType", writeType);
        mapParam.put("memType", memType);
        mapParam.put("gender", gender);

        List<Map<String, Object>> dsList = monthlyStatsService.selectMonthlyStatsDetail(mapParam);

        dataRequest.setResponse("dsList", dsList);
        
        return new JSONDataView();

    }
}
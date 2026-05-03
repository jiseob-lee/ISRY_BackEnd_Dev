/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.mbrstats.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.couns.stats.mbrstats.service.TotalSubscriberStatsService;


@Controller
@Api(value = "totalSubscriberController Controller")
@RequestMapping("/totalSubscriber")
public class TotalSubscriberStatsController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "totalSubscriberStatsService")
    private TotalSubscriberStatsService svc;
    
    @RequestMapping("/selectTotalSubscriberStats.do")
    public View selectTotalSubscriberStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        mapParam.put("statsYear", searchParam.getValue("statsYear"));

        List<Map<String, Object>> dsList = svc.selectTotalSubscriberStats(mapParam);
        dataRequest.setResponse("dsList"      , dsList);
        
        return new JSONDataView();
    }
    
    @RequestMapping("/selectTotalSubscriberStatsDetail.do")
    public View selectTotalSubscriberStatsDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        mapParam.put("statsYM", searchParam.getValue("statsYM"));

        List<Map<String, Object>> dsListDetail = svc.selectTotalSubscriberStatsDetail(mapParam);
        dataRequest.setResponse("dsListDetail", dsListDetail);
        
        return new JSONDataView();
    }
    
}
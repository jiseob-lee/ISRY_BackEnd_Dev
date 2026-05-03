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
import isry.couns.stats.dscsnstats.service.MobileCrisisStatsService;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;


@Controller
@Api(value = "mobileCrisisStatsController Controller")
@RequestMapping("/mobileCrisisStats")
public class MobileCrisisStatsController {

//	protected Logger log = LoggerFactory.getLogger(this.getClass());
    private final ScpDb scpDb = new ScpDb();

    @Resource(name = "mobileCrisisStatsService")
    private MobileCrisisStatsService mobileCrisisStatsService;
    
    @RequestMapping("/selectmobileCrisisStats.do")
    public View selectmobileCrisisStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
//        log.debug("monthCount.do===="); 
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//        log.debug("DDD : "+ searchParam.toString()); 
        
		String startDate = searchParam.getValue("startDate");
		String endDate   = searchParam.getValue("endDate");
        String cbxLinkMod = searchParam.getValue("cbxLinkMod"); //직접연계

		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	
        mapParam.put("cbxLinkMod", cbxLinkMod);
        
//        log.debug("DDD 2 "); 

//유형별 
        List<Map<String, Object>> dsList = mobileCrisisStatsService.selectmobileCrisisStats(mapParam);

        dataRequest.setResponse("dsList", dsList);
  
//상담사별        
        List<Map<String, Object>> dsList2 = mobileCrisisStatsService.selectmobileCrisisStatsCounselor(mapParam);
        
        dataRequest.setResponse("dsList2", dsList2);
         
        
        return new JSONDataView();

    }
   
}
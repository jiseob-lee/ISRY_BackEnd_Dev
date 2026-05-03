/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.couns.stats.dscsnstats.service.CrisisCounselingPeopleStatsService;



@Controller
@Api(value = "crisisCounselingPeopleStatsController Controller")
@RequestMapping("/crisisCounselingPeopleStats")
public class CrisisCounselingPeopleStatsController {

//	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "crisisCounselingPeopleStatsService")
    private CrisisCounselingPeopleStatsService  crisisCounselingPeopleStatsService;
    
    @RequestMapping("/selectcrisisCounselingPeopleStats.do")
    public View selectcrisisCounselingPeopleStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//System.out.println("DDD : "+ searchParam.toString()); 
        
		mapParam.put("AA_AA"		, searchParam.getValue("AA_AA"));
		mapParam.put("START_DATE"	, searchParam.getValue("START_DATE"));
		mapParam.put("END_DATE"		, searchParam.getValue("END_DATE"));
        
        List<Map<String, Object>> dsList = crisisCounselingPeopleStatsService.selectcrisisCounselingPeopleStats(mapParam);

        dataRequest.setResponse("dsList", dsList);
        
        return new JSONDataView();

    }
    
    @RequestMapping("/selectcrisisCounselingPeopleStats2.do")
    public View selectcrisisCounselingPeopleStats2(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//System.out.println("DDD : "+ searchParam.toString()); 
        
		mapParam.put("AA_AA"		, searchParam.getValue("AA_AA"));
		mapParam.put("START_DATE"	, searchParam.getValue("START_DATE"));
		mapParam.put("END_DATE"		, searchParam.getValue("END_DATE"));
        
        List<Map<String, Object>> dsList = crisisCounselingPeopleStatsService.selectcrisisCounselingPeopleStats2(mapParam);

        dataRequest.setResponse("dsList2", dsList);
        
        return new JSONDataView();

    }
    

   
}
/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.consttprfmnc.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.couns.cmmn.service.CounsService;
import isry.couns.stats.consttprfmnc.service.ConSurveyStatsService;

@Controller
@Api(value = "conSurveyStatsController Controller")
@RequestMapping("/conSurveyStats")
public class ConSurveyStatsController {

//	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "conSurveyStatsService")
    private ConSurveyStatsService conSurveyStatsService;
    
    @Resource(name = "counsService")
    private CounsService counsService;
    
    @RequestMapping(value = "/onLoadConSurveyStatsList.do", method = { RequestMethod.POST, RequestMethod.GET })
    public View onLoadConPerformanceStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	// 부서 목록 조회 (콤보박스)
    	List<Map<String, Object>> dsList = counsService.selectOrgDeptCombo(request);
    	
    	dataRequest.setResponse("dsCombDeptCd", dsList);
        
        return new JSONDataView();
    }
    
    @RequestMapping("/selectconSurveyStats.do")
    public View selectconSurveyStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
//        log.debug("monthCount.do===="); 
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//        log.debug("DDD : "+ searchParam.toString()); 
        
        String writeType = searchParam.getValue("writeType"); //글작성타입
        
        mapParam.put("writeType", writeType);
        
//        log.debug("DDD 2 "); 

        List<Map<String, Object>> dsList = conSurveyStatsService.selectconSurveyStats(mapParam);

        dataRequest.setResponse("dsList", dsList);
      
        
        return new JSONDataView();

    }
    

   
}
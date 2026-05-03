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
import isry.couns.stats.dscsnstats.service.KakaoStatusService;




@Controller
@Api(value = "kakaoStatusController Controller")
@RequestMapping("/kakaoStatus")
public class KakaoStatusController {

//	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "kakaoStatusService")
    private KakaoStatusService kakaoStatusService;
    
    /**
     * 
     * @Method명   : selectkakaoStatus
     * @param request
     * @param response
     * @param dataRequest
     * @return
     * @throws Exception
     * @작성자     : ???
     * @작성일     : ????.??.?? 
     * @Method설명 :
     * @사용안함 : 2023.07.26
     */
    @RequestMapping("/selectkakaoStatus.do")
    public View selectkakaoStatus(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
//        log.debug("monthCount.do===="); 
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//        log.debug("DDD : "+ searchParam.toString()); 
    	
		String startDate = searchParam.getValue("startDate");
		String endDate   = searchParam.getValue("endDate");

		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);	

//        log.debug("startDate2" + startDate); 
//성별실적
        List<Map<String, Object>> dsList = kakaoStatusService.selectkakaoStatus(mapParam);

        dataRequest.setResponse("dsList", dsList);
 
//연령별실적     
        List<Map<String, Object>> dsList2 = kakaoStatusService.selectkakaoStatusAge(mapParam);

        dataRequest.setResponse("dsList2", dsList2);

//상담사별실적             
        List<Map<String, Object>> dsList3 = kakaoStatusService.selectkakaoStatusCounselor(mapParam);

    	for (Map<String, Object> map : dsList3) {
    		if(map.get("COUNSELOR") != null && !map.get("COUNSELOR").toString().isEmpty() )
    			map.replace("COUNSELOR", map.get("COUNSELOR").toString() + map.get("ID").toString() );
    		else 
    			map.replace("COUNSELOR",  map.get("ID").toString()); 
   		}

        
        dataRequest.setResponse("dsList3", dsList3);
        
        return new JSONDataView();

    }
    

}

   

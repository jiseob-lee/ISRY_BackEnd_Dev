/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.couns.stats.dscsnstats.service.MobileManageStatsService;



@Controller
@Api(value = "mobileManageStatsController Controller")
@RequestMapping("/isry/couns/stats/dscsnstats")
public class MobileManageStatsController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "mobileManageStatsService")
    private MobileManageStatsService mobileManageStatsService;
    
    @RequestMapping("/selectAfterFactMngStatsList.do")
    public View selectAfterFactMngStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
    	
    	mobileManageStatsService.selectAfterFactMngStatsList(request, dataRequest);
    	
    	return new JSONDataView();
    }
   
}
/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.etcstats.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.stats.etcstats.service.ConnGraphStatsService;



@Controller
@Api(value = "ConnGraphStatsController Controller")
                            
@RequestMapping("/connGraphStats") 
public class ConnGraphStatsController  extends IsryBaseController {

	

	@Resource(name = "connGraphStatsService")
	private ConnGraphStatsService connGraphStatsService;

	
	@RequestMapping("/selectconnGraphStats.do")
	public View selectconnGraphStatsStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
	
		String startDate = searchParam.getValue("startDate");
		mapParam.put("STARTDATE", startDate);
	
		String endDate = searchParam.getValue("endDate");
		mapParam.put("ENDDATE", endDate);
 
    	
		List<Map<String, Object>> dsList1 = connGraphStatsService.selectconnGraphStats(mapParam);
		
		dataRequest.setResponse("dsList", dsList1);
		
		
        return new JSONDataView();

	}


	
	
	
	
}
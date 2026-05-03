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



@Controller
@Api(value = "hopeDownloadStatsController Controller")
                            
@RequestMapping("/hopeDownloadStats") 
public class hopeDownloadStatsController  extends IsryBaseController {

	

	@Autowired
//	private isry.couns.taskwkschmng.schprecon.service.workChgAplyService workChgAplyService;

	private isry.couns.stats.etcstats.service.hopeDownloadStatsService hopeDownloadStatsService;

	
	@RequestMapping("/selectHopeDownloadStats.do")
	public View selectHopeDownloadStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> mapParam = searchParam.getSingleValueMap();
	
//		String startDate = searchParam.getValue(0, "startDate");
//		mapParam.put("startDate", startDate);
//	
//		String endDate = searchParam.getValue(0, "endDate");
//		mapParam.put("endDate", endDate);
// 
		log.debug("@@@ : "+ searchParam.toString());
		log.debug("@@@222 : "+ mapParam);
		
		List<Map<String, Object>> dsList1 = hopeDownloadStatsService.hopeDownloadStats(mapParam);
		
		dataRequest.setResponse("dsList", dsList1);
		
		
        return new JSONDataView();

	}


	
	
	
	
}
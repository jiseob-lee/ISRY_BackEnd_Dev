/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.sample.web;

import java.util.ArrayList;
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
import isry.sample.service.AimnsTrainessCostService;


@Controller
@Api(value = "AimnsTrainessCost Controller")
@RequestMapping("/aimnsTrainessCost")
public class AimnsTrainessCostController extends IsryBaseController {

	@Autowired
	private AimnsTrainessCostService aimnsTrainessCostService;

	@RequestMapping("/listAimnsTrainessCost.do")
	public View list(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
		
		String strYear = searchParam.getValue("strYear");
		String strMonth = searchParam.getValue("strMonth");
		
		String[][] arrayMonth = { {"01", "JAN"}, {"02", "FEB"},
								  {"03", "MAR"}, {"04", "APR"}, 
								  {"05", "MAY"}, {"06", "JUN"}, 
								  {"07", "JUL"}, {"08", "AUG"},
								  {"09", "SEP"}, {"10", "OCT"},
								  {"11", "NOV"}, {"12", "DEC"}};
				
		List<Map<String, String>> searchDateList = new ArrayList<Map<String, String>>();
		
		// 년도 전체 조회했을경우
		if(strMonth.equals("total")){
			
			for (String[] month : arrayMonth) {
				Map<String, String> searchDateMap = new HashMap<String, String>();
				String strYearMonth = strYear + "-" +  month[0];
				searchDateMap.put("DATE", strYearMonth);
				searchDateMap.put("ALIAS", month[1]);
				
				searchDateList.add(searchDateMap);
			}			
		} else {
			Map<String, String> searchDateMap = new HashMap<String, String>();
			
			int monthIdx = Integer.parseInt(strMonth) - 1;
			String strYearMonth = strYear + "-" +  arrayMonth[monthIdx][0];
			
			searchDateMap.put("DATE", strYearMonth);
			searchDateMap.put("ALIAS", arrayMonth[monthIdx][1]);
				
			searchDateList.add(searchDateMap);
		}
				
		mapParam.put("SEARCH_DATE_ARRAY", searchDateList);
		
		// 자립 지원금 리스트 조회
		List<Map<String, Object>> listTrainessCostList = aimnsTrainessCostService.selectTrainessCostList(mapParam);

		dataRequest.setResponse("dsTrainessCostList", listTrainessCostList);

		return new JSONDataView();

	}
}

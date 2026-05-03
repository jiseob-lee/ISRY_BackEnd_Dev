/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwkschmng.schprecon.web;

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
import isry.couns.taskwkschmng.schprecon.service.dailyschAllService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;



@Controller
@Api(value = "dailyschAllController Controller")

@RequestMapping("/taskwkschmng/schprecon") 
public class dailyschAllController extends IsryBaseController {

	@Autowired
	private dailyschAllService dailyschAllService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	// @RequestMapping("/list.do")
	// public View list(HttpServletRequest request, HttpServletResponse response,
	// DataRequest dataRequest)
	// throws Exception {

	// Map<String, Object> mapParam = new HashMap<String, Object>();

	// ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");

	// String a1 = searchParam.getValue("a1"); //글작성타입
	// String a2 = searchParam.getValue("a2"); //회원여부
	// String a3 = searchParam.getValue("a3"); //성별
	// String startDate = searchParam.getValue("START_DATE"); //
	// String endDate = searchParam.getValue("END_DATE"); //

	// mapParam.put("START_DATE", startDate);
	// mapParam.put("END_DATE", endDate);

	// List<Map<String, Object>> list = monthlyStatsService.list(mapParam);

	// dataRequest.setResponse("dsTrainessCostList", listTrainessCostList);

	// return new JSONDataView();

	// }
	
	// 조회조건
//			ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
//
//			mapParam.put("SEARCH_KEY", searchParam.getValue("strSearchKey"));
//			mapParam.put("SEARCH_DATA", searchParam.getValue("strSearchData"));
//			mapParam.put("START_DATE", searchParam.getValue("strStartDate"));
//			mapParam.put("END_DATE", searchParam.getValue("strEndDate"));
//	/taskwkschmng/dailysch/selectDailySchList.do
	@RequestMapping("/selectDailySchAllOnload.do")
	public View monthDetailCount(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
  
	//사용자정보	
		
		HttpSession session = request.getSession();
 		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		String WORKYMD = searchParam.getValue("WORKYMD");
		mapParam.put("WORKYMD", WORKYMD);

		List<Map<String, Object>> dsCyberList1 = dailyschAllService.subOnLoadDsTime(mapParam);

		List<Map<String, Object>> dsCyberList2 = dailyschAllService.subOnLoadDsCyberTime(mapParam);
        for (Map<String, Object> map : dsCyberList2) {
	        try {
					map.replace("CONS_NAME", map.get("CONS_NAME"));
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
//System.out.println("dsCyberList2 : "+dsCyberList2.toString());
		List<Map<String, Object>> dsCyberList3 = dailyschAllService.subOnLoadDsMobileTime(mapParam);
        for (Map<String, Object> map : dsCyberList3) {
	        try {
					map.replace("CONS_NAME", map.get("CONS_NAME"));
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
//System.out.println("dsCyberList3 : "+dsCyberList3.toString());

		List<Map<String, Object>> dsCyberList4 = dailyschAllService.subOnLoadDsOutreachTime(mapParam);
        for (Map<String, Object> map : dsCyberList4) {
	        try {
					map.replace("CONS_NAME", map.get("CONS_NAME"));
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
//System.out.println("dsCyberList4 : "+dsCyberList4.toString());
		

		dataRequest.setResponse("dsTime", dsCyberList1);
		
		dataRequest.setResponse("dsCyberTime", dsCyberList2);

		dataRequest.setResponse("dsMobileTime", dsCyberList3);
		
		dataRequest.setResponse("dsOutReachTime", dsCyberList4);
		
		
		return new JSONDataView();

	}
//				
	@RequestMapping("/selectDailySchAllList.do")
	public View selectDailySchList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

	//사용자정보	
		
		HttpSession session = request.getSession();
 		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
 		

		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		String WORKYMD = searchParam.getValue("WORKYMD");
		mapParam.put("WORKYMD", WORKYMD);

 
		List<Map<String, Object>> dsCyberList1 = dailyschAllService.subOnLoadDsTime(mapParam);
 
		List<Map<String, Object>> dsCyberList2 = dailyschAllService.subOnLoadDsCyberTime(mapParam);
		
//System.out.println("dsCyberList2 : "+dsCyberList2.toString());

		List<Map<String, Object>> dsCyberList3 = dailyschAllService.subOnLoadDsMobileTime(mapParam);
        
//System.out.println("dsCyberList3 : "+dsCyberList3.toString());
		 
		List<Map<String, Object>> dsCyberList4 = dailyschAllService.subOnLoadDsOutreachTime(mapParam);
        
//System.out.println("dsCyberList4 : "+dsCyberList4.toString());

		
		dataRequest.setResponse("dsTime", dsCyberList1);
		
		dataRequest.setResponse("dsCyberTime", dsCyberList2);

		dataRequest.setResponse("dsMobileTime", dsCyberList3);
		
		dataRequest.setResponse("dsOutReachTime", dsCyberList4);

		return new JSONDataView();

	}
}
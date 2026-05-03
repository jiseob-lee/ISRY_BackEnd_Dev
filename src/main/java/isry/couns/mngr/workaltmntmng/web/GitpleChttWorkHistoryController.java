/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.workaltmntmng.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.couns.mngr.workaltmntmng.service.GitpleChttWorkHistoryService;

/**
 * @파일명        : GitpleChttWorkHistoryController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Hai.Ryong
 * @작성일        : 2023. 5. 2. 
 * @수정자        : Kim.Hai.Ryong
 * @수정일        : 2023. 5. 2.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/couns/mngr/workaltmntmng")
public class GitpleChttWorkHistoryController extends IsryBaseController{

	@Resource (name = "GitpleChttWorkHistoryServiceImpl")
	private GitpleChttWorkHistoryService GitChttWorkHistoryService;
	
	/**
	 * 
	 * @Method명   : GitpleChttWorkHistoryList
	 * @param request
	 * @param response
	 * @param datarequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Hai.Ryong
	 * @작성일     : 2023. 5. 3. 
	 * @Method설명 :
	 */
	
	@RequestMapping("GitpleChttWorkHistoryList.do")
	public View GitpleChttWorkHistoryList(HttpServletRequest request, HttpServletResponse response, DataRequest datarequest) throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = datarequest.getParameterGroup("dmTime");
		
		String startDate = searchParam.getValue("startDate");
		String endDate 	 = searchParam.getValue("endDate");
		
		mapParam.put("startDate", startDate);
		mapParam.put("endDate", endDate);
		
		List<Map<String, Object>> dsList = GitChttWorkHistoryService.GitpleChttWorkHistoryList(request, mapParam);
		datarequest.setResponse("dsList", dsList);
		
		return new JSONDataView();
	}
}

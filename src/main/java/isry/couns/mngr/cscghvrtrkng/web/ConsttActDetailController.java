/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.cscghvrtrkng.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.loading.PrivateClassLoader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.couns.mngr.cscghvrtrkng.service.ConsttActDetailService;
import net.bytebuddy.asm.Advice.Return;

/**
 * @파일명        : ConsttActDetailController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Hai.Ryong
 * @작성일        : 2023. 5. 11. 
 * @수정자        : Kim.Hai.Ryong
 * @수정일        : 2023. 5. 11.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/couns/mngr/cscghvrtrkng")
public class ConsttActDetailController extends IsryBaseController{
	
	@Resource (name = "ConsttActDetailServiceImpl")
	private ConsttActDetailService ConsttActDetailService;

	/**
	 * 
	 * @Method명   : ConsttActDetailList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Hai.Ryong
	 * @작성일     : 2023. 5. 11. 
	 * @Method설명 :
	 */
	
	@RequestMapping("/ConsttActDetailList.do")
	public View ConsttActDetailList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
	
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		String sdate = searchParam.getValue("startDate");
		String edate = searchParam.getValue("endDate");
		String nm 	 = searchParam.getValue("nm");
		
		mapParam.put("SDATE", sdate);
		mapParam.put("EDATE", edate);
		mapParam.put("NM", nm);
		
		List<Map<String, Object>> actList = ConsttActDetailService.consttActDetailList(request, mapParam);
		dataRequest.setResponse("consttActList", actList);
		
		return new JSONDataView();
	}
	
	@RequestMapping("ConsttActDetailInfo.do")
	public View ConsttActDetailInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
	
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		String AST = searchParam.getValue("AST");
		String sUserId 	 = searchParam.getValue("sUserId");
		
		mapParam.put("AST", AST);
		mapParam.put("sUserId", sUserId);
		
		System.out.println("DDD : "+ searchParam.toString());  
		
		List<Map<String, Object>> actInfo = ConsttActDetailService.consttActDetailInfo(request, mapParam);
		dataRequest.setResponse("consttActDetail", actInfo);
		
		return new JSONDataView();
	}
}

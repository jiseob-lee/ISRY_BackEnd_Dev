/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.cnnctchatconstt.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import isry.couns.cmmn.service.CounsService;
import isry.couns.mngr.cnnctchatconstt.service.CnnctChatConsttService;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;


@Controller
@Api(value = "CnnctChatConsttController Controller")
@RequestMapping("/cnnctchatconstt")
public class CnnctChatConsttController extends IsryBaseController {

    @Autowired
    private CnnctChatConsttService cnnctChatConsttService;
    
    @Resource(name = "counsService")
    private CounsService counsService;
    
    /**
	 * @Method명   : sampleSearchOption
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 12. 
	 * @Method설명 :
	 */ 
    @RequestMapping("/sampleSearchOption.do")
     public View sampleSearchOption(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
             throws Exception {
    	log.info("CnnctChatConsttController sampleSearchOption");
    	
    	///조회 조건 검색 (부서)
    	List<Map<String, Object>> searchComboList = counsService.selectOrgDeptCombo(request);
    	
    	dataRequest.setResponse("dsSearchCombo", searchComboList);
    	
    	return new JSONDataView();
    }
    
    /**
	 * @Method명   : selectCnnctChatConsttList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 13. 
	 * @Method설명 :
	 */ 
    @RequestMapping("/selectCnnctChatConsttList.do")
    public View selectCnnctChatConsttList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		log.info("CnnctChatConsttController selectCnnctChatConsttList");
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
				
		String deptCd = searchParam.getValue("DEPT_CD");
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("deptCd", deptCd);
		
		List<Map<String, Object>> dsList =	cnnctChatConsttService.selectCnnctChatConsttList(request, mapParam);
		
		dataRequest.setResponse("dsList", dsList);
		
		return new JSONDataView();
   }
    
    /**
	 * @Method명   : processCnnctChatConstt
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 16. 
	 * @Method설명 :
	 */ 
    @RequestMapping("/processCnnctChatConstt.do")
    public void processCnnctChatConstt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
    	log.info("CnnctChatConsttController processCnnctChatConstt");
    	
    	int ret = 0;
    	
    	ret = cnnctChatConsttService.processCnnctChatConstt(request,dataRequest);
    }
    
}
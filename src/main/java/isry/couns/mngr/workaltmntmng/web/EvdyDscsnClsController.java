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
import isry.couns.mngr.workaltmntmng.service.EvdyDscsnClsService;

@Controller
@Api(value = "EvdyDscsnClsController Controller")
@RequestMapping("/workaltmntmng")
public class EvdyDscsnClsController extends IsryBaseController {

    @Autowired
    private EvdyDscsnClsService evdyDscsnClsService;
    
    @Resource(name = "counsService")
    private CounsService counsService;
    
    /**
	 * @Method명   : sampleSearchOptionEvdy
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 23. 
	 * @Method설명 : 부서 콤보박스 조회
	 */
    @RequestMapping("/sampleSearchOptionEvdy.do")
    public View sampleSearchOption(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

	    // 조회 조건 검색 (부서)
	   	dataRequest.setResponse("dsSearchCombo", counsService.selectOrgDeptCombo(request));
	   	
	   	return new JSONDataView();
   }
    
    /**
	 * @Method명   : selectEvdyDscsnClsList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 30. 
	 * @Method설명 :
	 */
    @RequestMapping("/selectEvdyDscsnClsList.do")
    public View selectEvdyDscsnClsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
       	
       	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
       	//String instNo = searchParam.getValue("INST_NO").split("-")[1];
       	String instNo = searchParam.getValue("DEPT_CD");
       	String inqYmd = searchParam.getValue("INQ_YMD");
       	
       	mapParam.put("instNo", instNo);
       	mapParam.put("inqYmd", inqYmd);
       	
       	///일일상담배정 조회
       	List<Map<String, Object>> dsList2 = evdyDscsnClsService.selectEvdyDscsnClsList(mapParam);
       	
       	dataRequest.setResponse("dsDscsnAltmnt", dsList2);
    	
    	return new JSONDataView();
    }
    
    
    
    
}
/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.couns.cmmn.service.CounsService;
import isry.couns.mngr.service.ConsttSrvyExmnInptCdService;

@Controller
@Api(value = "ConsttSrvyExmnInptCdController Controller")
@RequestMapping("/consttSrvyExmnInptCd")
public class ConsttSrvyExmnInptCdController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "consttSrvyExmnInptCdService")
    private ConsttSrvyExmnInptCdService svc;
    
    @Resource(name = "counsService")
    private CounsService counsService;

    @RequestMapping("/selectCombo1List.do")
    public View selectCombo1List(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
//        Map<String, Object> mapParam = new HashMap<String, Object>();
       
//        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//log.debug("DDD : "+ searchParam.toString());        
//
//        List<Map<String, Object>> list = svc.selectCombo1List(mapParam);
    	
    	// 조회 조건 검색 (소속기관)
	   	List<Map<String, Object>> searchComboList = counsService.selectOrgDeptCombo(request);

        dataRequest.setResponse("dsSearchCombo", searchComboList);

        return new JSONDataView();

    }

    @RequestMapping("/selectConsttSrvyExmnInptCdList.do")
    public View selectConsttSrvyExmnInptCdList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
log.debug("DDD : "+ searchParam.toString());    

//        String instNo = searchParam.getValue("instNo");
		String deptCd = searchParam.getValue("DEPT_CD");
        String startDate = searchParam.getValue("startDate"); //
        String endDate = searchParam.getValue("endDate"); //
        String gubun = searchParam.getValue("gubun"); //
        String[] arrGuid = gubun.split(","); //
        String gbIdnt = ""; //
        String gbName = ""; //
        String name = ""; //
        String id = ""; //
        if (Arrays.asList(arrGuid).contains("GB_ID")) {
        	gbIdnt = "T"; //
        	id = searchParam.getValue("name"); //
        }
        if (Arrays.asList(arrGuid).contains("GB_NM")) {
        	gbName = "T"; //
        	name = searchParam.getValue("name"); //
        }

        
        //System.out.println("qweqwe::"+gbIdnt+"/qweqwe::"+gbName);
        
//        mapParam.put("instNo", instNo);
        mapParam.put("DEPT_CD", deptCd);
        mapParam.put("startDate", startDate);
        mapParam.put("endDate", endDate);
        mapParam.put("gbIdnt", gbIdnt);
        mapParam.put("gbName", gbName);
        mapParam.put("name", name);
        mapParam.put("id", id);

        List<Map<String, Object>> list = svc.selectConsttSrvyExmnInptCdList(request, mapParam);
        
        // 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		if(list.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", list.get(0).get("TOTAL_COUNT"));
		}
        
		dataRequest.setResponse("dmPage", resPage);
        dataRequest.setResponse("dsList", list);

        return new JSONDataView();

    }

}
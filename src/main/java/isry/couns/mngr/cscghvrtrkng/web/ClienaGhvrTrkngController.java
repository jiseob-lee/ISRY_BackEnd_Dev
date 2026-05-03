/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.cscghvrtrkng.web;

import java.util.ArrayList;
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
import isry.couns.mngr.cscghvrtrkng.service.ClienaGhvrTrkngService;

@Controller
@Api(value = "ClienaGhvrTrkngController Controller")
@RequestMapping("/clienaGhvrTrkng")
public class ClienaGhvrTrkngController {

//	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "clienaGhvrTrkngService")
    private ClienaGhvrTrkngService svc;

    @RequestMapping("/selectClienaGhvrTrkngList.do")
    public View selectClienaGhvrTrkngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//System.out.println("DDD : "+ searchParam.toString());        

        mapParam.put("START_DATE"	, searchParam.getValue("START_DATE"));
        mapParam.put("END_DATE"		, searchParam.getValue("END_DATE"));

        String gubun = searchParam.getValue("GB"); //
        String[] arrGuid = gubun.split(","); //
        String gbIdnt = ""; //
        String gbName = ""; //
        String name = ""; //
        String id = ""; //
        if (Arrays.asList(arrGuid).contains("GB_ID")) {
        	gbIdnt = "T"; //
        	id = searchParam.getValue("SEARCH_TXT"); 
        }
        if (Arrays.asList(arrGuid).contains("GB_NM")) {
        	gbName = "T"; //
        	name = searchParam.getValue("SEARCH_TXT"); //
        }
        mapParam.put("GB_ID"		, gbIdnt);
        mapParam.put("GB_NM"		, gbName);
        mapParam.put("name", name);
        mapParam.put("id", id);
        

        List<Map<String, Object>> list = svc.selectClienaGhvrTrkngList(mapParam);		
		for (Map<String, Object> map : list) {
			try {
				map.replace("FLNM", map.get("FLNM"));
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
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

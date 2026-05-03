/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.web;

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
import isry.couns.mngr.service.ConsttUnrcordSesnService;

@Controller
@Api(value = "ConsttUnrcordSesnController Controller")
@RequestMapping("/consttUnrcordSesn")
public class ConsttUnrcordSesnController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "consttUnrcordSesnService")
    private ConsttUnrcordSesnService svc;

    @RequestMapping("/selectCombo1List.do")
    public View selectCombo1List(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
log.debug("DDD : "+ searchParam.toString());        

        List<Map<String, Object>> list = svc.selectCombo1List(mapParam);

        dataRequest.setResponse("dsComb1", list);

        return new JSONDataView();

    }

    /**
     * @Method명   : selectUnrecordSesnList
     * @param request
     * @param response
     * @param dataRequest
     * @return
     * @throws Exception
     * @작성자     : ????
     * @작성일     : ????.??.?? 
     * @Method설명 :
     * @사용안함 : 2023.07.28
     */
    @RequestMapping("/selectUnrecordSesnList.do")
    public View selectUnrecordSesnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
log.debug("DDD : "+ searchParam.toString());    

        mapParam.put("instNo"	, searchParam.getValue("instNo"));
        mapParam.put("startDate", searchParam.getValue("startDate"));
        mapParam.put("endDate"	, searchParam.getValue("endDate"));

        List<Map<String, Object>> list = svc.selectUnrecordSesnList(mapParam);
        
        // 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		if(list.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", list.get(0).get("CCNT"));
		}
		dataRequest.setResponse("dmPage", resPage);
        dataRequest.setResponse("dsList", list);

        return new JSONDataView();

    }

}
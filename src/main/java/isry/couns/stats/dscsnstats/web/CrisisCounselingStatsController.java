/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.stats.dscsnstats.service.ActualUserStatsService;
import isry.couns.stats.dscsnstats.service.AgesStatsService;
import isry.couns.stats.dscsnstats.service.CrisisCounselingStatsService;

@Controller
@Api(value = "CrisisCounselingStatsController Controller")
@RequestMapping("/crisisCounselingStats")
public class CrisisCounselingStatsController extends IsryBaseController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "crisisCounselingStatsService")
    private CrisisCounselingStatsService svc;

    @RequestMapping("/list.do")
    public View list(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
             throws Exception {
    	log.debug("/crisisCounselingStats"+ "/list.do"+" START...");        
        
    	Map<String, Object> mapParam = new HashMap<String, Object>();
        
         ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
log.debug("DDD : "+ searchParam.toString());        
         String writeType = searchParam.getValue("writeType"); //글작성타입
         String startDate = searchParam.getValue("startDate"); //
         String endDate = searchParam.getValue("endDate"); //
        
         mapParam.put("writeType", writeType);
         mapParam.put("startDate", startDate);
         mapParam.put("endDate", endDate);

         List<Map<String, Object>> list = svc.list(mapParam);

         dataRequest.setResponse("dsList", list);

         return new JSONDataView();

    }

}
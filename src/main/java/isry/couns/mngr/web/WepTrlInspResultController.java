/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.web;

import java.util.ArrayList;
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
import isry.couns.mngr.service.WepTrlInspResultService;

@Controller
@Api(value = "WepTrlInspResultController Controller")
@RequestMapping("/wepTrlInspResult")
public class WepTrlInspResultController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "wepTrlInspResultService")
    private WepTrlInspResultService svc;

    @RequestMapping("/selectWebTrlInspResultList.do")
    public View selectWebTrlInspResultList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
log.debug("DDD : "+ searchParam.toString());        
//        String writeType = searchParam.getValue("writeType"); //글작성타입
//        String startDate = searchParam.getValue("startDate"); //
//        String endDate = searchParam.getValue("endDate"); //
//       
//        mapParam.put("writeType", writeType);
//        mapParam.put("startDate", startDate);
//        mapParam.put("endDate", endDate);

        List<Map<String, Object>> list = svc.selectWebTrlInspResultList(mapParam);

        dataRequest.setResponse("dsList", list);

        return new JSONDataView();

    }

    @RequestMapping("/selectWebTrlInspResultDetail.do")
    public View selectWebTrlInspResultDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
log.debug("DDD : "+ searchParam.toString());    

        String lclasTypeSeCd = searchParam.getValue("TRL_INSP_LCLAS_TYPE_SE_CD"); //글작성타입
        String mlsfcTypeSeCd = searchParam.getValue("TRL_INSP_MLSFC_TYPE_SE_CD"); //
        String sclasTypeSeCd = searchParam.getValue("TRL_INSP_SCLAS_TYPE_SE_CD"); //
       
        mapParam.put("TRL_INSP_LCLAS_TYPE_SE_CD", lclasTypeSeCd);
        mapParam.put("TRL_INSP_MLSFC_TYPE_SE_CD", mlsfcTypeSeCd);
        mapParam.put("TRL_INSP_SCLAS_TYPE_SE_CD", sclasTypeSeCd);
log.debug("AAA : "+ mapParam.toString());    

        List<Map<String, Object>> list = svc.selectWebTrlInspResultDetail(mapParam);

        dataRequest.setResponse("dsList", list);

        return new JSONDataView();

    }

}
/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.consultantabltymng.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import io.swagger.annotations.Api;
import isry.couns.mngr.consultantabltymng.service.AprslMngService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Controller
@Api(value = "AprslMngController Controller")
@RequestMapping("/aprslMng")
public class AprslMngController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "aprslMngService")
    private AprslMngService svc;

    @RequestMapping("/selectNgtmWorkEtxpyReqstdMngList.do")
    public View selectNgtmWorkEtxpyReqstdMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
log.debug("DDD : "+ searchParam.toString());        
       
        mapParam.put("yyyyMm", searchParam.getValue("yyyyMm"));

        List<Map<String, Object>> list = svc.selectNgtmWorkEtxpyReqstdMngList(mapParam);

        dataRequest.setResponse("dsList", list);

        return new JSONDataView();

    }
    
    @RequestMapping("/selectNgtmWorkEtxpyReqstdMngDetail.do")
    public View selectNgtmWorkEtxpyReqstdMngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
log.debug("DDD : "+ searchParam.toString());        
       
		mapParam.put("yyyyMm", searchParam.getValue("yyyyMm"));
		mapParam.put("userId", searchParam.getValue("userId"));
		

        List<Map<String, Object>> list = svc.selectNgtmWorkEtxpyReqstdMngDetail(mapParam);

        dataRequest.setResponse("dsList", list);

        return new JSONDataView();

    }
     

}
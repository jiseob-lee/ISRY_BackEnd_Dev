/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.dclrandsgstd.web;

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
import isry.couns.taskwksprt.dclrandsgstd.service.InvtnPrpslService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Controller
@Api(value = "InvtnPrpslController Controller")
@RequestMapping("/invtnPrpsl")
public class InvtnPrpslController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "invtnPrpslService")
    private InvtnPrpslService svc;

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
    
//    @RequestMapping("/mergeWorkEtxpyUntpcMng.do")
//    public View mergeWorkEtxpyUntpcMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
//            throws Exception {
//       
//		// 세션정보 가져오기
//		HttpSession session   = request.getSession();
//		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
//		String userId = "";
//		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
//			userId = loginVO.getId();
//		} else {
//			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
//		}
//
//		Map<String, Object> mapParam = new HashMap<String, Object>();
//       
//        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//log.debug("DDD : "+ searchParam.toString());        
//       
//        mapParam.put("CRTR_YR"					, searchParam.getValue("CRTR_YR"));
//        mapParam.put("NIGHT_WORK_UNTPC_AMT"		, searchParam.getValue("NIGHT_WORK_UNTPC_AMT"));
//        mapParam.put("WIK_OVTIME_UNTPC_AMT"		, searchParam.getValue("WIK_OVTIME_UNTPC_AMT"));
//        mapParam.put("NIGHT_OVTIME_UNTPC_AMT"	, searchParam.getValue("NIGHT_OVTIME_UNTPC_AMT"));
//        mapParam.put("USER_ID"					, userId);
//
//        
//        int ccnt = svc.mergeWorkEtxpyUntpcMng(mapParam);	// 저장
//        
//        List<Map<String, Object>> list = svc.selectWorkEtxpyUntpcMngList(mapParam);
//
//        dataRequest.setResponse("dsList", list);
//
//        return new JSONDataView();
//
//    }
//     
//    @RequestMapping("/deleteWorkEtxpyUntpcMng.do")
//    public View deleteeWorkEtxpyUntpcMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
//            throws Exception {
//       
//		Map<String, Object> mapParam = new HashMap<String, Object>();
//       
//        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
//log.debug("DDD : "+ searchParam.toString());        
//       
//        mapParam.put("CRTR_YR"					, searchParam.getValue("CRTR_YR"));
//        
//        int ccnt = svc.deleteWorkEtxpyUntpcMng(mapParam);	// 저장
//        
//        List<Map<String, Object>> list = svc.selectWorkEtxpyUntpcMngList(mapParam);
//
//        dataRequest.setResponse("dsList", list);
//
//        return new JSONDataView();
//
//    }
     

}
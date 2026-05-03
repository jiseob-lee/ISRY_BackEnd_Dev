/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.etxpyreqstdmng.web;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import io.swagger.annotations.Api;
import isry.couns.mngr.etxpyreqstdmng.service.WorkEtxpyUntpcMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Controller
@Api(value = "workEtxpyUntpcMngController Controller")
@RequestMapping("/workEtxpyUntpcMng")
public class WorkEtxpyUntpcMngController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "workEtxpyUntpcMngService")
    private WorkEtxpyUntpcMngService svc;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
    @RequestMapping("/selectWorkEtxpyUntpcMngList.do")
    public View selectWorkEtxpyUntpcMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
log.debug("DDD : "+ searchParam.toString());        
        String writeType = searchParam.getValue("writeType"); //글작성타입
        String startDate = searchParam.getValue("startDate"); //
        String endDate = searchParam.getValue("endDate"); //
       
        mapParam.put("writeType", writeType);
        mapParam.put("startDate", startDate);
        mapParam.put("endDate", endDate);

        List<Map<String, Object>> list = svc.selectWorkEtxpyUntpcMngList(mapParam);

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
    
    @RequestMapping("/mergeWorkEtxpyUntpcMng.do")
    public View mergeWorkEtxpyUntpcMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없읍니다.", Alert.ERROR);
		}

		Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
log.debug("DDD : "+ searchParam.toString());        
       
        mapParam.put("CRTR_YR"					, searchParam.getValue("CRTR_YR"));
        mapParam.put("NIGHT_WORK_UNTPC_AMT"		, searchParam.getValue("NIGHT_WORK_UNTPC_AMT"));
        mapParam.put("WIK_OVTIME_UNTPC_AMT"		, searchParam.getValue("WIK_OVTIME_UNTPC_AMT"));
        mapParam.put("NIGHT_OVTIME_UNTPC_AMT"	, searchParam.getValue("NIGHT_OVTIME_UNTPC_AMT"));
        mapParam.put("USER_ID"					, userId);

        
        int ccnt = svc.mergeWorkEtxpyUntpcMng(mapParam);	// 저장
        
        List<Map<String, Object>> list = svc.selectWorkEtxpyUntpcMngList(mapParam);

        dataRequest.setResponse("dsList", list);

        return new JSONDataView();

    }
     
    @RequestMapping("/deleteWorkEtxpyUntpcMng.do")
    public View deleteeWorkEtxpyUntpcMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
		Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
log.debug("DDD : "+ searchParam.toString());        
       
        mapParam.put("CRTR_YR"					, searchParam.getValue("CRTR_YR"));
        
        int ccnt = svc.deleteWorkEtxpyUntpcMng(mapParam);	// 저장
        
        List<Map<String, Object>> list = svc.selectWorkEtxpyUntpcMngList(mapParam);

        dataRequest.setResponse("dsList", list);

        return new JSONDataView();

    }
     

}
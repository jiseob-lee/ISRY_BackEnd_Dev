/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.consttprfmnc.web;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import io.swagger.annotations.Api;
import isry.couns.cmmn.service.CounsService;
import isry.couns.stats.consttprfmnc.service.ConPerformanceStatsService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.redis.service.RedisService;


@Controller
@Api(value = "conPerformanceStatsController Controller")
@RequestMapping("/conPerformanceStats")
public class ConPerformanceStatsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

    @Resource(name = "conPerformanceStatsService")
    private ConPerformanceStatsService conPerformanceStatsService;
    
    @Resource(name = "counsService")
    private CounsService counsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
    @RequestMapping(value = "/onLoadConPerformanceStatsList.do", method = { RequestMethod.POST, RequestMethod.GET })
    public View onLoadConPerformanceStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	String userGroupAuthrtSeCd = "";		// session의 그룹권한구분코드
    	
    	Map<String, Object> searchMap = new HashMap<String, Object>();
    	List<Map<String, Object>> dsList = new ArrayList<Map<String,Object>>();
    	
    	// 부서 목록 조회 (콤보박스)
    	dsList = counsService.selectOrgDeptCombo(request);

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		// session정보의 그룹권한구분코드 조회하기
		if (loginVO != null && !"".equals(loginVO.getGroupAuthrtSeCd())) {
			userGroupAuthrtSeCd = loginVO.getGroupAuthrtSeCd();
		}
		
		// 1xx : 여성가족부, 2xx : 중앙관리기관
        if (userGroupAuthrtSeCd.charAt(0) == '1' || userGroupAuthrtSeCd.charAt(0) == '2') {
        	searchMap.put("IS_ADMIN", "Y");
        } else if (userGroupAuthrtSeCd.charAt(0) == '3') {
        	// x10 : 총괄관리자, x20 : 기관관리자
        	if ("10".equals(userGroupAuthrtSeCd.substring(1)) || "20".equals(userGroupAuthrtSeCd.substring(1))) {
        		searchMap.put("IS_ADMIN", "Y");
        	
        	// x30 : 사업담당자, x40 : 담당자
        	} else {
        		searchMap.put("IS_ADMIN", "N");
        	}
        	
        } else {
        	throw new AppWorksException("접근 권한이 없습니다. 권한 신청을 해주세요.", Alert.ERROR);
        }
        
        LOGGER.debug("searchMap ::: " + searchMap);
    	
    	dataRequest.setResponse("dsCombDeptCd", dsList);
    	dataRequest.setResponse("dmSearch", searchMap);
        
        return new JSONDataView();
    }
    
    @RequestMapping(value = "/selectCnsltntPerformanceStatsList.do")
    public View selectCnsltntPerformanceStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
    	
    	conPerformanceStatsService.selectCnsltntPerformanceStatsList(request, dataRequest);
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/selectconPerformanceStats.do")
    public View selectconPerformanceStats(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
        
//        log.debug("monthCount.do===="); 
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");

        //System.out.println("DDD : "+ searchParam.toString()); 
        
        String deptCd = searchParam.getValue("deptCd");       //소속기관
        String startDate = searchParam.getValue("startDate"); //조회시작일
        String endDate = searchParam.getValue("endDate");     //조회종료일
        
        HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        String sUserId = loginVO.getId();
         
         mapParam.put("deptCd", deptCd);
         mapParam.put("startDate", startDate);
         mapParam.put("endDate", endDate);
         mapParam.put("sUserId", sUserId);
 
        List<Map<String, Object>> dsList = conPerformanceStatsService.selectconPerformanceStats(mapParam, request);

        dataRequest.setResponse("dsList", dsList);
        
        return new JSONDataView();
    }
    

   
}
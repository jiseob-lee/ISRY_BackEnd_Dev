/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.workaltmntmng.web;

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
import com.google.common.collect.Iterators;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.cmmn.service.CounsService;
import isry.couns.mngr.workaltmntmng.service.AttdneMngService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;

@Controller
@Api(value = "AttdneMngController Controller")
@RequestMapping("/workaltmntmng")
public class AttdneMngController extends IsryBaseController {

    @Autowired
    private AttdneMngService attdneMngService;
    
    @Resource(name = "counsService")
    private CounsService counsService;
    
    @RequestMapping(value = "/onLoadCounsDeptList.do")
    public View onLoadCounsDeptList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
    
    	// 기관별 부서 목록 조회 (콤보박스)
        List<Map<String, Object>> searchComboList = counsService.selectOrgDeptCombo(request);

        // 리턴 데이터 설정
        dataRequest.setResponse("dsCombDeptCd", searchComboList);
        
    	return new JSONDataView();
    }

    @RequestMapping("/selectAttdneMngList.do")
    public View selectAttdneMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	String crtrYm = searchParam.getValue("CRTR_YM");
    	String deptCd = searchParam.getValue("DEPT_CD");
    	
    	log.info("selectAttdneMngList crtrYm : " + crtrYm);
    	
    	String crtrYear = "";
    	String crtrMonth = "";
    	int tempMonth = 0;
    	try {
    		crtrYear = crtrYm.split("-")[0];
    		tempMonth = Integer.parseInt(crtrYm.split("-")[1]);
        	crtrMonth = tempMonth+"";
		} catch (Exception e) {
			// TODO: handle exception
			crtrYear = "";
	    	crtrMonth = "";
		}
    	if(crtrYm.contains("-"))
    		crtrYm = crtrYm.replace("-", "");
    	mapParam.put("crtrYm", crtrYm);
    	mapParam.put("crtrYear", crtrYear);
    	mapParam.put("crtrMonth", crtrMonth);
    	mapParam.put("deptCd", deptCd);
    	
    	List<Map<String, Object>> dsList = attdneMngService.selectAttdneMngList(mapParam);
    	
    	dataRequest.setResponse("dsList", dsList);
    	
    	return new JSONDataView();
   }
    
    @RequestMapping("/processAttdneMng.do")
    public View processAttdneMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	attdneMngService.processAttdneMng(request, dataRequest);
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/processAttdneMngMonth.do")
    public View processAttdneMngMonth(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	attdneMngService.processAttdneMngMonth(request, dataRequest);
    	
    	return new JSONDataView();
    }
    
    
}
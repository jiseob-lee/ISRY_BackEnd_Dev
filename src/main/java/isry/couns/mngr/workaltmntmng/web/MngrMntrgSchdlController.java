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
import isry.couns.mngr.workaltmntmng.service.EvdyDscsnClsService;
import isry.couns.mngr.workaltmntmng.service.MngrMntrgSchdlService;
//import isry.couns.mngr.taskwkaltmntmng.service.CnnctChatReqstdService;
import isry.couns.mngr.workaltmntmng.service.MnthySchdlRegInfoMngService;
import isry.couns.mngr.workaltmntmng.service.OfwkUpcsdClngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;

@Controller
@Api(value = "MngrMntrgSchdlController Controller")
@RequestMapping("/workaltmntmng")
public class MngrMntrgSchdlController extends IsryBaseController {

    @Autowired
    private MngrMntrgSchdlService mngrMntrgSchdlService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;    
    
   
    @RequestMapping("/selectMngrMntrgSchdlList.do")
    public View selectMngrMntrgSchdlList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	Map<String, Object> mapParam = new HashMap<String, Object>();
   	
    	ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
    	String crtrYm = searchParam.getValue("CRTR_YM");
    	if(crtrYm.length()>6)
    		crtrYm = crtrYm.substring(0, 6);
    	
    	mapParam.put("crtrYm", crtrYm);
    	
    	List<Map<String, Object>> dsList = mngrMntrgSchdlService.selectMngrMntrgSchdlList(mapParam);
    	
    	ScpDb scpDb = new ScpDb();
    	
    	for (Map<String, Object> map : dsList) {

    		// 휴대전화번호
    		String sMblTelnoEncpt = String.valueOf(map.get("MBL_TELNO_ENCPT"));
    		String sWrdTelno = String.valueOf(map.get("WRD_TELNO"));
			if(sMblTelnoEncpt == null || sMblTelnoEncpt.equals("") || sMblTelnoEncpt == "null") {
				if(sWrdTelno == null || sWrdTelno.equals("") || sWrdTelno == "null") {
					map.put("NUMBER", null);
				} else {
					map.put("NUMBER", "유선 : " + map.get("WRD_TELNO"));
				}
			} else {
				if(sMblTelnoEncpt != null && sMblTelnoEncpt != "") {
					map.put("NUMBER", "모바일 : " + map.get("MBL_TELNO_ENCPT"));
				} else {
					map.put("NUMBER", null);
				}
			}
		}
    	
    	dataRequest.setResponse("dsList", dsList);
    	
    	return new JSONDataView();
   }
    
    @RequestMapping("/insertMngrMntrgSchdl.do")
    public View insertMngrMntrgSchdl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	HttpSession session = request.getSession();
    	UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	ScpDb scpdb = new ScpDb();
    	String mngrInptYmd = searchParam.getValue("MNGR_INPT_YMD");
    	String cnsltntId = searchParam.getValue("CNSLTNT_ID");
    	String cnsltntNm = searchParam.getValue("CNSLTNT_NM");
//    	String indexSn = searchParam.getValue("INDEX_SN");
//    	System.out.println("indexSn ==== " + indexSn);
//    	String cnsltntNm = scpdb.scpEncB64(searchParam.getValue("CNSLTNT_NM_RAW"));
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	mapParam.put("mngrInptYmd", mngrInptYmd);
    	mapParam.put("cnsltntId", cnsltntId);
    	mapParam.put("cnsltntNm", cnsltntNm);
//    	mapParam.put("indexSn", indexSn);
    	mapParam.put("loginId", loginId);
    	
    	Map<String, Object> delInfo = mngrMntrgSchdlService.selectMngrMntrgSchdlDelInfo(mapParam);
    	
    	if(delInfo != null) {
    		//System.out.println("INDEX_SN ===== " + delInfo.get("INDEX_SN"));
    		delInfo.put("indexSn", delInfo.get("INDEX_SN"));
    		delInfo.put("loginId", loginId);
    		
    		mngrMntrgSchdlService.deleteMngrMntrgSchdl(delInfo);
    	} else {
    		//System.out.println("New Insert");
    	}
    	
    	try {
    		// NEW
    		mngrMntrgSchdlService.insertMngrMntrgSchdl(mapParam);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/updateMngrMntrgSchdl.do")
    public View updateMngrMntrgSchdl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	HttpSession session = request.getSession();
    	UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
		ScpDb scpdb = new ScpDb();
		
		String cnsltntId = searchParam.getValue("CNSLTNT_ID");
		String indexSn = searchParam.getValue("INDEX_SN");
		String cnsltntNm = searchParam.getValue("CNSLTNT_NM");
//    	String cnsltntNm = scpdb.scpEncB64(searchParam.getValue("CNSLTNT_NM_RAW"));
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	mapParam.put("cnsltntId", cnsltntId);
    	mapParam.put("cnsltntNm", cnsltntNm);
    	mapParam.put("indexSn", indexSn);
    	mapParam.put("loginId", loginId);
    	
    	try {
    		mngrMntrgSchdlService.updateMngrMntrgSchdl(mapParam);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/deleteMngrMntrgSchdl.do")
    public View deleteMngrMntrgSchdl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	HttpSession session = request.getSession();
    	UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String loginId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
    	
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		String indexSn = searchParam.getValue("INDEX_SN");
		
		mapParam.put("indexSn", indexSn);
		mapParam.put("loginId", loginId);
		
		try {
			mngrMntrgSchdlService.deleteMngrMntrgSchdl(mapParam);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	return new JSONDataView();
    }
}
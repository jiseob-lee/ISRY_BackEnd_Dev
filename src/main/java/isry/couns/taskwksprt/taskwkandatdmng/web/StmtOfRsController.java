/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.taskwkandatdmng.web;

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

import io.swagger.annotations.Api;
import isry.couns.taskwksprt.taskwkandatdmng.service.StmtOfRsService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Controller
@Api(value = "StmtOfRsController Controller")
@RequestMapping("/taskwkandatdmng")
public class StmtOfRsController {

//	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "stmtOfRsService")
    private StmtOfRsService stmtOfRsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
    @RequestMapping("/selectStmtOfRsList.do")
    public View selectStmtOfRsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	String loginId = "";		// session 정보의 ID
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
    	
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}else {
			return new JSONDataView();
		}
		
    	mapParam.put("loginId", loginId);
    	
    	Map<String, Object> dmSearch = stmtOfRsService.selectUserInfo(mapParam);
    	List<Map<String, Object>> dsList = stmtOfRsService.selectStmtOfRsList(mapParam);
    	
    	//System.out.println("dsList = [ " + dsList + " ]");

    	dataRequest.setResponse("dmSearch", dmSearch);
    	dataRequest.setResponse("dsList", dsList);
    	
    	return new JSONDataView();

    }
    
    @RequestMapping("/insertStmtOfRs.do")
    public View insertStmtOfRs(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {

    	String loginId = "";		// session 정보의 ID
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
    	
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}else {
			return new JSONDataView();
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		String wrtYmd = searchParam.getValue("WRT_YMD");
		String stofreTtlNm = searchParam.getValue("STOFRE_TTL_NM");
		String stofreCn = searchParam.getValue("STOFRE_CN");
		String bbscttAtfino = searchParam.getValue("BBSCTT_ATFINO");
		
    	mapParam.put("loginId", loginId);
    	mapParam.put("stofreTtlNm", stofreTtlNm);
    	mapParam.put("stofreCn", stofreCn);
    	mapParam.put("wrtYmd", wrtYmd);
    	mapParam.put("bbscttAtfino", bbscttAtfino);
    	
    	stmtOfRsService.insertStmtOfRs(mapParam);
    	
    	return new JSONDataView();
    }
    
    @RequestMapping("/deleteStmtOfRs.do")
    public View deleteStmtOfRs(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	String loginId = "";		// session 정보의 ID
    	
    	Map<String, Object> mapParam = new HashMap<String, Object>();
    	
    	UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
    	
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		}else {
			return new JSONDataView();
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmDeleteIdx");
		
		String stofreSn = searchParam.getValue("STOFRE_SN");
		
    	mapParam.put("loginId", loginId);
    	mapParam.put("stofreSn", stofreSn);
    	
    	stmtOfRsService.deleteStmtOfRs(mapParam);
    	
    	
    	return new JSONDataView();
    }
    
}
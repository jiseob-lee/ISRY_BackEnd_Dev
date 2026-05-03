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

import egovframework.com.cmm.service.EgovProperties;
import io.swagger.annotations.Api;
import isry.couns.taskwksprt.taskwkandatdmng.service.TlcmtChgReqstdService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Controller
@Api(value = "TlcmtChgReqstdController Controller")
@RequestMapping("/taskwkandatdmng")
public class TlcmtChgReqstdController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

    @Resource(name = "tlcmtChgReqstdService")
    private TlcmtChgReqstdService tlcmtChgReqstdService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
    @RequestMapping("/selectUserInfo.do")
    public View selectUserInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
    		throws Exception {
    	HttpSession session = request.getSession();
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        String loginId = "";
        if(!loginVO.getId().isEmpty())
        	loginId = loginVO.getId();
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("loginId", loginId);
    	
        Map<String, Object> dmSearch = tlcmtChgReqstdService.selectUserInfo(mapParam);
        
        dataRequest.setResponse("dmSearch", dmSearch);
        
    	return new JSONDataView();
    }
    
    
    @RequestMapping("/selectTlcmtChgReqstdList.do")
    public View selectTlcmtChgReqstdList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	
    	String loginId = "";		// session 정보의 ID
    	
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        if(!loginVO.getId().isEmpty()) {
        	loginId = loginVO.getId();
        }
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("loginId", loginId);
        
        Map<String, Object> dmSearch = tlcmtChgReqstdService.selectUserInfo(mapParam);
        List<Map<String, Object>> dsList = tlcmtChgReqstdService.selectTlcmtChgReqstdList(mapParam);
        
       	dataRequest.setResponse("dsList", dsList);
        dataRequest.setResponse("dmSearch", dmSearch);
        
    	return new JSONDataView();
    }
    
    @RequestMapping("/insertTlcmtChgReqstd.do")
    public View insertTlcmtChgReqstd(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	String loginId = "";		// session 정보의 ID
    	
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        
        if(!loginVO.getId().isEmpty()) {
        	loginId = loginVO.getId();
        }
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
        
        String cnsltntId = searchParam.getValue("USER_ID");
        String hmtrcWorkChgYmd = searchParam.getValue("HMTRC_WORK_CHG_YMD");
        String hmtrcWorkCsCn = searchParam.getValue("HMTRC_WORK_CS_CN");
        String zip = searchParam.getValue("ZIP");
        String trprAddr = searchParam.getValue("TRPR_ADDR");
        String trprLctnDaddr = searchParam.getValue("TRPR_LCTN_DADDR");
        
        mapParam.put("loginId", loginId);
        mapParam.put("cnsltntId", cnsltntId);
        mapParam.put("hmtrcWorkChgYmd", hmtrcWorkChgYmd);
        mapParam.put("hmtrcWorkCsCn", hmtrcWorkCsCn);
        mapParam.put("zip", zip);
        mapParam.put("trprAddr", trprAddr);
        mapParam.put("trprLctnDaddr", trprLctnDaddr);
        
        tlcmtChgReqstdService.insertTlcmtChgReqstd(mapParam);
        
    	return new JSONDataView();
    }
    
    @RequestMapping("/deleteTlcmtChgReqstd.do")
    public View deleteTlcmtChgReqstd(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
    	String loginId = "";		// session 정보의 ID
    	
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        
        if(!loginVO.getId().isEmpty()) {
        	loginId = loginVO.getId();
        }
        
        Map<String, Object> mapParam = new HashMap<String, Object>();
        
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmDeleteIdx");
        
        String indexSn = searchParam.getValue("HMTRC_SN");
        
        mapParam.put("loginId", loginId);
        mapParam.put("indexSn", indexSn);
        
        tlcmtChgReqstdService.deleteTlcmtChgReqstd(mapParam);
        
    	return new JSONDataView();
    }
    
}
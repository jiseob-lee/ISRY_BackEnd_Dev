/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.etxpyaplyandinq.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import isry.couns.taskwksprt.etxpyaplyandinq.service.NgtmWorkEtxpyAplyService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;

@Controller
@Api(value = "NgtmWorkEtxpyAplyController Controller")
@RequestMapping("/ngtmWorkEtxpyAply")
public class NgtmWorkEtxpyAplyController {

//	protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "ngtmWorkEtxpyAplyService")
    private NgtmWorkEtxpyAplyService ngtmWorkEtxpyAplyService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
    ScpDb scpDb = new ScpDb();
    
    @RequestMapping("/selectNgtmWorkEtxpyAplyList.do")
    public View selectNgtmWorkEtxpyAplyList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
        //System.out.println("DDD : "+ searchParam.toString());     
        
        mapParam.put("USER_ID", userId);
        mapParam.put("YYYYMM", searchParam.getValue("YYYYMM"));

        List<Map<String, Object>> list = ngtmWorkEtxpyAplyService.selectNgtmWorkEtxpyAplyList(mapParam);
        //System.out.println("DDD list : "+ list.toString());     

        List<Map<String, Object>> list2= ngtmWorkEtxpyAplyService.selectNgtmWorkEtxpyAplyList2(mapParam);
        List<Map<String, Object>> list3= ngtmWorkEtxpyAplyService.selectNgtmWorkEtxpyAplyList3(mapParam);

        dataRequest.setResponse("dsList" , list);
        dataRequest.setResponse("dsList2", list2);
        dataRequest.setResponse("dsList3", list3);

        return new JSONDataView();

    }
    
    @RequestMapping("/insertNgtmWorkEtxpyAply.do")
    public View insertNgtmWorkEtxpyAply(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
            throws Exception {
       
        Map<String, Object> mapParam = new HashMap<String, Object>();
       
        ParameterGroup searchParam = dataRequest.getParameterGroup("dmSave");
        //System.out.println("DDD : "+ searchParam.toString());        
       
        mapParam.put("GIVE_YM"			, searchParam.getValue("GIVE_YM"));
        mapParam.put("CONSTT_ID"		, searchParam.getValue("CONSTT_ID"));
        mapParam.put("NIGHT_WORK_HR"	, searchParam.getValue("NIGHT_WORK_HR"));
        mapParam.put("PENSN_SUM_AMT"	, searchParam.getValue("PENSN_SUM_AMT"));
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
        mapParam.put("USER_ID", userId);

        int ccnt = ngtmWorkEtxpyAplyService.insertNgtmWorkEtxpyAply(mapParam);

        return new JSONDataView();

    }
    
}
/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwkschmng.schprecon.web;

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

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.taskwkschmng.schprecon.service.MeMnthngSchService;
import isry.couns.taskwkschmng.schprecon.service.MnthngSchdlService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;



@Controller
@Api(value = "MeMnthngSchController Controller")
@RequestMapping("/schprecon") 
public class MeMnthngSchController {

    @Resource(name = "meMnthngSchService")
	private MeMnthngSchService svc;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
   

	@RequestMapping("/selectMeMnthngSchList.do")
	public View selectMeMnthngSchList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		String crtrYm = searchParam.getValue("CRTR_YM");
		String userDeptCd = searchParam.getValue("DEPT_CD");
		
		mapParam.put("crtrYm", crtrYm);
		mapParam.put("DEPT_CD", userDeptCd);
		
		//사용자정보	
		HttpSession session = request.getSession();
 		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

 		String loginId = loginVO.getId();
		mapParam.put("loginId", loginId);
		
		if(!crtrYm.isEmpty()) {
			List<Map<String, Object>> dsList = svc.selectMeMnthngSchList(mapParam);
			
			dataRequest.setResponse("dsList", dsList);
		}
		
		return new JSONDataView();
	}
	
}
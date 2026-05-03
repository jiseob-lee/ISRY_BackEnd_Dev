/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.ytosp.portalmng.menuandprogrm.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.ytosp.portalmng.menuandprogrm.service.YtospMgmtMenuService;

/**
 * @파일명        : YtospMgmtMenuController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2023. 9. 7. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2023. 9. 7.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/ytosp/portalmng/memuandprogrm")
public class YtospMgmtMenuController {

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "ytospMgmtMenuService")
	private YtospMgmtMenuService ytospMgmtMenuService;

	@RequestMapping(value = "/selectMenu.do")
	public View selectMenu(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		List<Map<String, Object>> list = ytospMgmtMenuService.selectMenu(dataRequest);
		
		//dataRequest.setResponse("header", map.get("header"));
		//dataRequest.setResponse("menuId", map.get("menuId"));
		//dataRequest.setResponse("dsMenuList", map.get("menuList"));
		
		dataRequest.setResponse("dsMenuList", list);
		
		
		//Map<String, Object> loginMap = selectUserMap(request);
		
		//dataRequest.setResponse("dmUserInfo", loginMap);
		
		//dataRequest.setResponse("dsTaskWorkDbList", mgmtCmmnCodeService.selectCommonCodeUnit("MAIN_DASHBOARDS_BREAKDOWN", userVo == null ? "" : userVo.getUntTaskwk()));
		//dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();
	}

}

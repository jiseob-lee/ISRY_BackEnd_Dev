/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.userauth.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.pgmemu.service.MgmtMenuService;
import isry.itgcms.sysmgmt.userauth.service.InqGrpAuthListService;
import isry.itgcms.sysmgmt.userauth.service.InqMenuAuthService;

/**
 * 
 * @파일명        : InqGrpAuthListController.java
 * @프로그램 설명 : 그룹별 권한 조회
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 11. 23. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 11. 23.
 * @수정내용      : 
 * -                
 * -
 */
@Controller
//@Api(value = "InqGrpAuthList web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class InqGrpAuthListController extends IsryBaseController {

	@Resource(name = "inqGrpAuthListService")
	private InqGrpAuthListService inqGrpAuthListService;

	@Resource(name = "inqMenuAuthService")
	private InqMenuAuthService inqMenuAuthService;

	@Resource(name = "mgmtMenuService")
	private MgmtMenuService mgmtMenuService;

	//@ApiOperation(value = "/selectGrpAuth.do", notes = "그룹 권한 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectGrpAuth.do")
	public View selectUserRights(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> map = inqGrpAuthListService.selectGrpAuth(request, dataRequest);
		dataRequest.setResponse("header", map.get("header"));
		dataRequest.setResponse("menuId", map.get("menuId"));
		dataRequest.setResponse("dsAllMenu", map.get("menuPivot"));
		dataRequest.setResponse("dsMenuList", map.get("menuList"));
		
		dataRequest.setResponse("dsRootMenuList", mgmtMenuService.selectRootMenu());
		
		dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();
	}

	//@ApiOperation(value = "/selectGrpAuth2.do", notes = "그룹 세부 권한 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectGrpAuth2.do")
	public View searchAddr(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmRightId");
		String rightId = null;
		
		if (param != null) {
			rightId = param.getValue("rightId");
		}
		
		log.debug("#### rightId : " + rightId);
		
		dataRequest.setResponse("dsMenuList", rightId == null ? null : inqGrpAuthListService.selectGrpAuth2(rightId));
		
		dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();
	}
}

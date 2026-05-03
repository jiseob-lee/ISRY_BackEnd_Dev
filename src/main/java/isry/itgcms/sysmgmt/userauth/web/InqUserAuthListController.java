/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.userauth.web;

import java.util.List;
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
import isry.itgcms.sysmgmt.userauth.service.InqMenuAuthService;
import isry.itgcms.sysmgmt.userauth.service.InqUserAuthListService;

/**
 * 
 * @파일명        : InqUserAuthListController.java
 * @프로그램 설명 : 사용자별 권한 조회
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
//@Api(value = "InqUserAuthList web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class InqUserAuthListController extends IsryBaseController {

	@Resource(name = "inqUserAuthListService")
	private InqUserAuthListService inqUserAuthListService;

	@Resource(name = "inqMenuAuthService")
	private InqMenuAuthService inqMenuAuthService;

	@Resource(name = "mgmtMenuService")
	private MgmtMenuService mgmtMenuService;

	//@ApiOperation(value = "/selectUserAuth.do", notes = "사용자 권한 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectUserAuth.do")
	public View selectUserRights(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> map = inqUserAuthListService.selectUserAuth(request,dataRequest);
		dataRequest.setResponse("header", map.get("header"));
		dataRequest.setResponse("menuId", map.get("menuId"));
		dataRequest.setResponse("dsAllMenu", map.get("menuPivot"));
		dataRequest.setResponse("dsMenuList", map.get("menuList"));
		
		dataRequest.setResponse("dsRootMenuList", mgmtMenuService.selectRootMenu());
		
		dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();
	}

	//@ApiOperation(value = "/selectUserAuth2.do", notes = "사용자 세부 권한 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectUserAuth2.do")
	public View selectUserAuth2(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmUserId");
		String userId = null;
		
		if (param != null) {
			userId = param.getValue("userId");
		}
		
		log.debug("#### userId : " + userId);
		
		dataRequest.setResponse("dsMenuList", userId == null ? null : inqUserAuthListService.selectUserAuth2(userId));
		
		dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectUserAuth22.do")
	public View selectUserAuth21(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmUserId");
		String userId = null;
		
		if (param != null) {
			userId = param.getValue("userId");
		}
		
		log.debug("#### userId : " + userId);
		
		dataRequest.setResponse("dsMenuList2", userId == null ? null : inqUserAuthListService.selectUserAuth2(userId));
		
		//dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();
	}
	
	
	/**
	 * 
	 * @Method명   : selectUserAuthList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2023. 2. 7. 
	 * @Method설명 : 사용자 목록 리스트 호출
	 */
	@RequestMapping(value = "/selectUserAuthList.do")
	public View selectUserAuthList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> dsList = inqUserAuthListService.selectUserAuthList(request, dataRequest);
		
		dataRequest.setResponse("dsList", dsList);
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectAuthrtList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 권한그룹 목록 리스트 호출
	 */
	@RequestMapping(value = "/selectAuthrtList.do")
	public View selectAuthrtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> dsList = inqUserAuthListService.selectAuthrtList(request, dataRequest);
		
		dataRequest.setResponse("dsList", dsList);
		
		return new JSONDataView();
	}
}

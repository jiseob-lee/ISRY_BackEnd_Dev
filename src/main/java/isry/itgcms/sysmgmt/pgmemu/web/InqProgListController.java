/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.pgmemu.web;

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
import com.cleopatra.spring.JSONDataView;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import isry.itgcms.sysmgmt.pgmemu.service.ProgramVO;
import isry.itgcms.sysmgmt.userauth.service.InqMenuAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.pgmemu.service.InqProgListService;
import isry.itgcms.sysmgmt.pgmemu.service.MgmtMenuService;
import isry.itgcms.sysmgmt.pgmemu.service.ProgramStatusVO;

/**
 * 
 * @파일명        : InqProgListController.java
 * @프로그램 설명 : 프로그램 관리
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
//@Api(value = "InqProgList web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/pgmemu")
public class InqProgListController extends IsryBaseController {

	@Resource(name = "inqProgListService")
	private InqProgListService inqProgListService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@Resource(name = "mgmtMenuService")
	private MgmtMenuService mgmtMenuService;

	@Resource(name = "inqMenuAuthService")
	private InqMenuAuthService inqMenuAuthService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	//@ApiOperation(value = "/selectProgram.do", notes = "프로그램 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectProgram.do")
	public View selectProgram(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		//ParameterGroup param = dataRequest.getParameterGroup("dmRightId");
		//String rightId = null;
		
		//if (param != null) {
			//rightId = param.getValue("rightId");
		//}
		
		//LOGGER.debug("#### rightId : " + rightId);
		
		List<Map<String, Object>> list = inqProgListService.selectProgram(dataRequest);
		//dataRequest.setResponse("header", map.get("header"));
		//dataRequest.setResponse("menuId", map.get("menuId"));
		//dataRequest.setResponse("dsAllMenu", map.get("menuPivot"));
		dataRequest.setResponse("dsEndPoints", list);
		
		//List<WorkUnitVO> listWorkUnit = inqProgListService.selectWorkUnit();
		//dataRequest.setResponse("dsWorkUnit", listWorkUnit);
		dataRequest.setResponse("dsWorkUnit", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_SYS_SE_CD", userVo.getUntTaskwk()));
		
		//List<ProgramStatusVO> listProgramStatus = inqProgListService.selectProgramStatus();
		//dataRequest.setResponse("dsProgramStatus", listProgramStatus);
		dataRequest.setResponse("dsProgramStatus", mgmtCmmnCodeService.selectCommonCodeUnit("PROGRM_USE_SE_CD", userVo.getUntTaskwk()));
		
		dataRequest.setResponse("dsExternal", mgmtCmmnCodeService.selectCommonCodeUnit("IS_EXTERNAL_PROGRAM", userVo.getUntTaskwk()));
		
		dataRequest.setResponse("dsRootMenuList", mgmtMenuService.selectRootMenu());

		dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();
	}

	//@ApiOperation(value = "/selectWorkUnit.do", notes = "단위 시스템 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectWorkUnit.do")
	public View selectWorkUnit(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		//List<WorkUnitVO> list = inqProgListService.selectWorkUnit();
		List<Map<String, Object>> list = mgmtCmmnCodeService.selectCommonCodeUnit("UNT_SYS_SE_CD", userVo.getUntTaskwk());
		dataRequest.setResponse("dsWorkUnit", list);
		return new JSONDataView();
	}

	//@ApiOperation(value = "/selectProgramStatus.do", notes = "프로그램 상태 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectProgramStatus.do")
	public View selectProgramStatus(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		List<Map<String, Object>> list = inqProgListService.selectProgramStatus();
		dataRequest.setResponse("dsProgramStatus", list);
		return new JSONDataView();
	}

	//@ApiOperation(value = "/saveProgram.do", notes = "프로그램 저장 [공통] 이지섭")
	@RequestMapping(value = "/saveProgram.do")
	public View saveProgram(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//ParameterGroup param = dataRequest.getParameterGroup("dmRightId");
		//String rightId = null;
		
		//if (param != null) {
			//rightId = param.getValue("rightId");
		//}
		
		//LOGGER.debug("#### rightId : " + rightId);
		
		inqProgListService.saveProgram(request, dataRequest);
		//dataRequest.setResponse("header", map.get("header"));
		//dataRequest.setResponse("menuId", map.get("menuId"));
		//dataRequest.setResponse("dsAllMenu", map.get("menuPivot"));
		//dataRequest.setResponse("dsEndPoints", list);
		
		return new JSONDataView();
	}
		
}

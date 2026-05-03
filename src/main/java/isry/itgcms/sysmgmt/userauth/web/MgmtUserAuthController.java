/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.userauth.web;

import java.util.HashMap;
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

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.userauth.service.InqUserAuthListService;
import isry.itgcms.sysmgmt.userauth.service.MgmtUserAuthService;

/**
 * 
 * @파일명        : MgmtUserAuthController.java
 * @프로그램 설명 : 사용자 권한 저장
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 11. 29. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 11. 29.
 * @수정내용      : 
 * -                
 * -
 */

@Controller
//@Api(value = "MgmtGrpAuth web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class MgmtUserAuthController extends IsryBaseController {

	@Resource(name = "mgmtUserAuthService")
	private MgmtUserAuthService mgmtUserAuthService;

	@Resource(name = "inqUserAuthListService")
	private InqUserAuthListService inqUserAuthListService;

	//@ApiOperation(value = "/saveUserAuth.do", notes = "사용자 권한 저장 [공통] 이지섭")
	@RequestMapping(value = "/saveUserAuth.do")
	public View saveUserAuth(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		mgmtUserAuthService.saveUserAuth(request, dataRequest);
		
		//log.debug("#### rightId : " + rightId);
		
		//dataRequest.setResponse("dsMenuList", rightId == null ? null : inqGrpAuthListService.selectGrpAuth2(rightId));

		return new JSONDataView();

	}

	//@ApiOperation(value = "/saveUserDetailAuths.do", notes = "사용자 상세 권한 저장 [공통] 이지섭")
	@RequestMapping(value = "/saveUserDetailAuths.do")
	public View saveUserDetailAuths(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		mgmtUserAuthService.saveUserDetailAuths(request, dataRequest);
		
		//log.debug("#### rightId : " + rightId);
		
		//dataRequest.setResponse("dsMenuList", rightId == null ? null : inqGrpAuthListService.selectGrpAuth2(rightId));

		return new JSONDataView();

	}
}

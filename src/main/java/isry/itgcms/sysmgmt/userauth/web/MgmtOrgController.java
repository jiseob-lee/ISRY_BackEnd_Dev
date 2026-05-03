/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.web;

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
import isry.itgcms.sysmgmt.userauth.service.MgmtOrgService;

/**
 * @파일명        : MgmtOrgController.java
 * @프로그램 설명 : 기관 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 1. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 1.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
//@Api(value = "MgmtOrg web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class MgmtOrgController extends IsryBaseController {

	@Resource(name = "mgmtOrgService")
	private MgmtOrgService mgmtOrgService;

	//@ApiOperation(value = "/saveOrg.do", notes = "기관 정보 저장 [공통] 이지섭")
	@RequestMapping(value = "/saveOrg.do")
	public View saveOrg(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		mgmtOrgService.saveOrg(request, dataRequest);
		
		//log.debug("#### rightId : " + rightId);
		
		//dataRequest.setResponse("dsMenuList", rightId == null ? null : inqGrpAuthListService.selectGrpAuth2(rightId));

		return new JSONDataView();

	}

	@RequestMapping(value = "/saveOrgUnitSystem.do")
	public View saveOrgUnitSystem(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		mgmtOrgService.saveOrgUnitSystem(request, dataRequest);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectMaxInstCd.do")
	public View selectMaxInstCd(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dmMaxInstCd", mgmtOrgService.selectMaxInstCd());
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/approveInstitute.do")
	public View approveInstitute(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		mgmtOrgService.saveApproveInstitute(request, dataRequest);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/rejectInstitute.do")
	public View rejectInstitute(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		mgmtOrgService.saveRejectInstitute(request, dataRequest);
		
		return new JSONDataView();
	}

}

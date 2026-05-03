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
import isry.itgcms.sysmgmt.userauth.service.MgmtOrgDeptService;

/**
 * @파일명        : MgmtOrgDeptController.java
 * @프로그램 설명 : 기관의 부서 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 2. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 2.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
//@Api(value = "MgmtOrgDept web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class MgmtOrgDeptController {

	@Resource(name = "mgmtOrgDeptService")
	private MgmtOrgDeptService mgmtOrgDeptService;

	//@ApiOperation(value = "/saveOrgDept.do", notes = "기관 부서 저장 [공통] 이지섭")
	@RequestMapping(value = "/saveOrgDept.do")
	public View saveOrgDept(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		//mgmtOrgTypeService.saveOrg(dataRequest);
		
		//log.debug("#### rightId : " + rightId);
		mgmtOrgDeptService.saveOrgDept(request, dataRequest);
		
		//dataRequest.setResponse("dsOrgDept", mgmtOrgDeptService.saveOrgDept());

		return new JSONDataView();

	}

}

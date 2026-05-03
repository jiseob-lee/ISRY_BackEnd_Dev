/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.web;

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
import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
//import isry.itgcms.sysmgmt.userauth.service.InqOrgTypeListService;
import isry.redis.service.RedisService;

/**
 * @파일명        : InqOrgTypeListController.java
 * @프로그램 설명 : 기관 유형 조회
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
//@Api(value = "InqOrgTypeList web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class InqOrgTypeListController extends IsryBaseController {

	//@Resource(name = "inqOrgTypeListService")
	//private InqOrgTypeListService inqOrgTypeListService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	//@ApiOperation(value = "/selectOrgType.do", notes = "기관 유형 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectOrgType.do")
	public View selectOrgType(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		//mgmtOrgTypeService.saveOrg(dataRequest);
		
		//log.debug("#### rightId : " + rightId);
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsInstituteType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsWoringAgencyYN", mgmtCmmnCodeService.selectCommonCodeUnit("84", userVo.getUntTaskwk()));

		return new JSONDataView();

	}

}

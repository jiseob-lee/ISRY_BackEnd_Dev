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
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userauth.service.InqAuthGrpListService;
import isry.itgcms.sysmgmt.userauth.service.InqMenuAuthService;
//import isry.itgcms.sysmgmt.userauth.service.InqOrgListService;
//import isry.itgcms.sysmgmt.userauth.service.InqOrgTypeListService;
//import isry.itgcms.sysmgmt.userjoin.service.ReqUserJoinService;
import isry.itgcms.sysmgmt.userjoin.service.SrchAddrService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : InqAuthGrpListController.java
 * @프로그램 설명 : 권한 그룹 조회
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 3. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
//@Api(value = "InqAuthGrpList web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class InqAuthGrpListController extends IsryBaseController {

	@Resource(name = "inqAuthGrpListService")
	private InqAuthGrpListService inqAuthGrpListService;

	//@Resource(name = "reqUserJoinService")
	//private ReqUserJoinService reqUserJoinService;

	//@Resource(name = "inqOrgTypeListService")
	//private InqOrgTypeListService inqOrgTypeListService;

	//@Resource(name = "inqOrgListService")
	//private InqOrgListService inqOrgListService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name = "inqMenuAuthService")
	private InqMenuAuthService inqMenuAuthService;
	
	@Resource(name="srchAddrService")
    private SrchAddrService srchAddrService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	//@ApiOperation(value = "/selectAuthGrp.do", notes = "권한 그룹 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectAuthGrp.do")
	public View selectAuthGrp(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		//dataRequest.setResponse("dsDividingRoles", reqUserJoinService.selectDividingRoles());
		dataRequest.setResponse("dsAuthGrp", inqAuthGrpListService.selectAuthGrp(request, dataRequest));
		dataRequest.setResponse("dsInstituteType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk()));
		//dataRequest.setResponse("dsOrganization", inqOrgListService.selectOrg(dataRequest));
		
		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectCommonCodeUnit("UNT_TASKWK_SE_CD", userVo.getUntTaskwk()));  // 단위 시스템
		dataRequest.setResponse("dsOrgBossYn", mgmtCmmnCodeService.selectCommonCodeUnit("ISTDR_YN", userVo.getUntTaskwk()));  // 기관장 여부
		
		dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		dataRequest.setResponse("dsSgg", srchAddrService.selectSgg());
		dataRequest.setResponse("dsOrgType", mgmtCmmnCodeService.selectCommonCodeUnit("INST_TYPE_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsGender", mgmtCmmnCodeService.selectCommonCodeUnit("SXDC_SE_CD", userVo.getUntTaskwk()));
		
		return new JSONDataView();
	}
	
	//@ApiOperation(value = "/selectAuthGrpList.do", notes = "권한 그룹 조회 [공통] 이지섭")
		@RequestMapping(value = "/selectAuthGrpList.do")
		public View selectAuthGrpList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
			HttpSession session = request.getSession();
			UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
			dataRequest.setResponse("dsAuthGrp", inqAuthGrpListService.selectAuthGrp(request, dataRequest));
			
			return new JSONDataView();
		}
	
	@RequestMapping(value = "/selectMaxAuthrtId.do")
	public View selectMaxAuthrtId(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dmMaxAuthrtId", inqAuthGrpListService.selectMaxAuthrtId());
		
		return new JSONDataView();
	}
	
}

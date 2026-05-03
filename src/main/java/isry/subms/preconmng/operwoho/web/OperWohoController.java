/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.operwoho.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.subms.cmmn.service.SubmsService;
import isry.subms.preconmng.operwoho.service.OperWohoService;

/**
 * @파일명 : OperWohoController.java
 * @프로그램 설명 : 운영시수 controller - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 6. 29.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 6. 29.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/subms/preconmng/operwoho")
public class OperWohoController {

	@Resource(name = "operWohoService")
	private OperWohoService operWohoService;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	@Resource(name = "submsService")
	private SubmsService submsService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	/**
	 * @Method명 : onLoadOperWohoList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 6. 29.
	 * @Method설명 : 운영시수 목록 콤보데이터 조회
	 */
	@RequestMapping(value = "/onLoadOperWohoList.do")
	public View onLoadOperWohoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		dataRequest.setResponse("dsBizYrCmb", submsService.selectBizYrCombo(request));
		dataRequest.setResponse("dsOperInstCmb", submsService.selectInstNmCombo(request));
		dataRequest.setResponse("dsExcnBizCmb", submsService.selectSrvcExcnBizCombo(request));
		dataRequest.setResponse("dsEduCrseCmb", submsService.selectResrceNmCombo(request));
		dataRequest.setResponse("dsEduProgrmCmb",
				mgmtCmmnCodeService.selectCommonCodeUnit("EDU_PROGRM_SE_CD", userVo.getUntTaskwk()));

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectOperWohoList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 6. 29.
	 * @Method설명 : 운영시수 목록 조회
	 */
	@RequestMapping(value = "/selectOperWohoList.do")
	public View selectOperWohoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		dataRequest.setResponse("dsList", operWohoService.selectOperWohoList(request, dataRequest));
		return new JSONDataView();
	}

	/**
	 * @Method명 : onLoadOperWohoMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 6. 29.
	 * @Method설명 : 운영시수 관리화면 콤보데이터 조회
	 */
	@RequestMapping(value = "/onLoadOperWohoMng.do")
	public View onLoadOperWohoMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		dataRequest.setResponse("dsSemstrCmb",
				mgmtCmmnCodeService.selectCommonCodeUnit("SEMSTR_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsEduProgrmCmb",
				mgmtCmmnCodeService.selectCommonCodeUnit("EDU_PROGRM_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsClroCmb",
				mgmtCmmnCodeService.selectCommonCodeUnit("CLRO_SE_CD", userVo.getUntTaskwk()));

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectOperWohoMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 6. 29.
	 * @Method설명 : 운영(목표)시수관리 조회
	 */
	@RequestMapping(value = "/selectOperWohoMng.do")
	public View selectOperWohoMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		dataRequest.setResponse("dsList", operWohoService.selectOperWohoMng(dataRequest));
		return new JSONDataView();
	}

	/**
	 * @Method명 : saveOperWohoMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 6. 29.
	 * @Method설명 : 운영(목표)시수 등록/수정/삭제
	 */
	@RequestMapping(value = "/saveOperWohoMng.do")
	public View saveOperWohoMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		operWohoService.saveOperWohoMng(request, dataRequest);
		return new JSONDataView();
	}

}

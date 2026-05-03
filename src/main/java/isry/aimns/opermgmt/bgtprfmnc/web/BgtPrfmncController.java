/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.opermgmt.bgtprfmnc.web;

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

import isry.aimns.opermgmt.bgtprfmnc.service.BgtPrfmncService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.subms.cmmn.service.SubmsService;

/**
 * @파일명 : BgtPrfmncController.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 6. 27.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 6. 27.
 * @수정내용 : - -
 */
@Controller
@RequestMapping("/isry/aimns/opermgmt/bgtprfmnc")
public class BgtPrfmncController {

	@Resource(name = "bgtPrfmncService")
	private BgtPrfmncService bgtPrfmncService;

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name = "submsService")
	private SubmsService aimnsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectBgtPrfmncCombo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 15.
	 * @Method설명 : 목록조회 시 콤보데이터 조회
	 */
	@RequestMapping("/selectBgtPrfmncCombo.do")
	public View selectBgtPrfmncCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		dataRequest.setResponse("dsBizYr", aimnsService.selectBizYrCombo(request));
		dataRequest.setResponse("dsOperInst", aimnsService.selectInstNmCombo(request));
		dataRequest.setResponse("dsAprvSttsSe", mgmtCmmnCodeService.selectCommonCodeUnit("APRV_STTS_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsBgtImplCl", mgmtCmmnCodeService.selectCommonCodeUnit("BGT_IMPL_CL_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsResrce", aimnsService.selectResrceNmCombo(request));

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectBgtPrfmncList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 15.
	 * @Method설명 : 예산실적 목록조회
	 */
	@RequestMapping("/selectBgtPrfmncList.do")
	public View selectBgtPrfmncList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		dataRequest.setResponse("dsList", bgtPrfmncService.selectBgtPrfmncList(dataRequest));
		return new JSONDataView();
	}

	/**
	 * @Method명 : selectBgtPrfmnc
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 15.
	 * @Method설명 : 예산실적 상세조회
	 */
	@RequestMapping("/selectBgtPrfmnc.do")
	public View selectBgtPrfmnc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		bgtPrfmncService.selectBgtPrfmnc(request, dataRequest);
		return new JSONDataView();
	}

	/**
	 * @Method명 : selectBgtPrfmncOnLoad
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 15.
	 * @Method설명 : 예산실적 등록/수정 시 필요한 콤보데이터 조회
	 */
	@RequestMapping("/selectBgtPrfmncOnLoad.do")
	public View selectBgtPrfmncOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		dataRequest.setResponse("dsAprvSttsSe", mgmtCmmnCodeService.selectCommonCodeUnit("APRV_STTS_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsBgtImplCl", mgmtCmmnCodeService.selectCommonCodeUnit("BGT_IMPL_CL_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsResrce", aimnsService.selectResrceNmCombo(request));
		dataRequest.setResponse("dsBizYr", aimnsService.selectBizYrCombo(request));
		dataRequest.setResponse("dsBgtImplList", bgtPrfmncService.selectBgtPrfmncOnLoad(request));
		return new JSONDataView();
	}

	/**
	 * @Method명 : saveBgtPrfmnc
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 15.
	 * @Method설명 : 예산실적 등록/수정/삭제
	 */
	@RequestMapping("/saveBgtPrfmnc.do")
	public View saveBgtPrfmnc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		bgtPrfmncService.saveBgtPrfmnc(request, dataRequest);
		return new JSONDataView();
	}

	/**
	 * @Method명 : selectBgtPrfmncStatusList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 15.
	 * @Method설명 : 예산실적 일괄조회
	 */
	@RequestMapping("/selectBgtPrfmncStatusList.do")
	public View selectBgtPrfmncStatusList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		bgtPrfmncService.selectBgtPrfmncStatusList(request, dataRequest);
		return new JSONDataView();
	}
	
	/**
	 * @Method명 : selectBgtPrfmncExist
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 7. 15.
	 * @Method설명 : 예산실적 중복 여부 체크
	 */
	@RequestMapping("/selectBgtPrfmncExist.do")
	public View selectBgtPrfmncExist(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		bgtPrfmncService.selectBgtPrfmncExist(request, dataRequest);
		return new JSONDataView();
	}
	
	
}

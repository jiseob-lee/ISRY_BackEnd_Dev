/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.casereg.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseController;
import isry.drmgs.casereg.service.DrmgsCaseRegService;
import isry.gitple.service.GitpleEventService;
import isry.itgcm.ddnl.monthDdln.service.MonthDdlnService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.DisconnectUserService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : DrmgsCaseRegController.java
 * @프로그램 설명 : 사례관리 내 학교밖 고유 영역, 성과관리
 * - 
 * - 
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2022. 5. 26. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2022. 5. 31.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/drmgs/cases")
public class DrmgsCaseRegController extends IsryBaseController {

	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@Resource(name = "drmgsCaseRegService")
	private DrmgsCaseRegService drmgsCaseRegService;
	
	@Resource(name = "monthDdlnService")
	private MonthDdlnService monthDdlnService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value="/onLoad.do")
	@ResponseBody
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		String untTaskwkSeCd  = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			untTaskwkSeCd = loginVO.getUntTaskwk();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		dataRequest.setResponse("dsInspKndSeCd"					   , mgmtCmmnCodeService.selectCommonCodeUnit("INSP_KND_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsInspSrvyTypeClCd"			   , mgmtCmmnCodeService.selectCommonCodeUnit("INSP_SRVY_TYPE_CL_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsIntnetSmrtpDgnssClSeCd"		   , mgmtCmmnCodeService.selectCommonCodeUnit("INTNET_SMRTP_DGNSS_CL_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsSpcltyProgrmDgstfnClCd"		   , mgmtCmmnCodeService.selectCommonCodeUnit("SPCLTY_PROGRM_DGSTFN_CL_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsBeffatAftfctSeCd"			   , mgmtCmmnCodeService.selectCommonCodeUnit("BEFFAT_AFTFCT_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsChupResrceNoSeCd"			   , mgmtCmmnCodeService.selectCommonCodeUnit("CHUP_RESRCE_NO_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsActvtSafetyMuaiasExclTrgtCsSeCd", mgmtCmmnCodeService.selectCommonCodeUnit("ACTVT_SAFETY_MUAIAS_EXCL_TRGT_CS_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsChilIltrtQustnbTypeSeCd"        , mgmtCmmnCodeService.selectCommonCodeUnit("CHIL_ILTRT_QUSTNB_TYPE_SE_CD", untTaskwkSeCd));

		dataRequest.setResponse("dsQusList"		, drmgsCaseRegService.selectQusList(dataRequest));
//		dataRequest.setResponse("dsInspSrvyList", drmgsCaseRegService.selectInspSrvyList(dataRequest));
		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명   : onOutcLoad
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 9. 
	 * @Method설명 :사례관리 내 성과등록 화면
	 */
	@RequestMapping(value="/onOutcLoad.do")
	@ResponseBody
	public View onOutcLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		String untTaskwkSeCd  = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			untTaskwkSeCd = loginVO.getUntTaskwk();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}

		dataRequest.setResponse("dsOutcCnL"								, mgmtCmmnCodeService.selectCommonCodeUnit("OUTC_CN_LCLAS_SE_CD"						   , untTaskwkSeCd));
		dataRequest.setResponse("dsOutcCnM"								, mgmtCmmnCodeService.selectCommonCodeUnit("OUTC_CN_MLSFC_SE_CD"						   , untTaskwkSeCd));
		dataRequest.setResponse("dsOutcCnS"								, mgmtCmmnCodeService.selectCommonCodeUnit("OUTC_CN_SCLAS_SE_CD"						   , untTaskwkSeCd));

		dataRequest.setResponse("dsDtySe"  								, mgmtCmmnCodeService.selectCommonCodeUnit("DTY_SE_CD"									   , untTaskwkSeCd));
		dataRequest.setResponse("dsOccpClSe"							, mgmtCmmnCodeService.selectCommonCodeUnit("OCCP_CL_SE_CD"								   , untTaskwkSeCd));
		dataRequest.setResponse("dsOccpAbilitAtrngt"					, mgmtCmmnCodeService.selectCommonCodeUnit("OCCP_ABILIT_STRNGT_MDSTRM_FAILR_CS_SE_CD"	   , untTaskwkSeCd));
		dataRequest.setResponse("dsOccpAbilitStrngtMdstrmFailrCsLclasSe", mgmtCmmnCodeService.selectCommonCodeUnit("OCCP_ABILIT_STRNGT_MDSTRM_FAILR_CS_LCLAS_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsOccpAbilitStrngtMdstrmFailrCsSclasSe", mgmtCmmnCodeService.selectCommonCodeUnit("OCCP_ABILIT_STRNGT_MDSTRM_FAILR_CS_SCLAS_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsIdntyResult"							, mgmtCmmnCodeService.selectCommonCodeUnit("IDNTY_RESULT_SE_CD"							   , untTaskwkSeCd));
		dataRequest.setResponse("dsStg1PtcptnStts"					   	, mgmtCmmnCodeService.selectCommonCodeUnit("STG1_PTCPTN_STTS_SE_CD"						   , untTaskwkSeCd));
		dataRequest.setResponse("dsSchulwDscntcCnsdraTrgtDscsnResult"  	, mgmtCmmnCodeService.selectCommonCodeUnit("SCHULW_DSCNTC_CNSDRA_TRGT_DSCSN_RESULT_SE_CD"  , untTaskwkSeCd));
		dataRequest.setResponse("dsSchulwDscntcCnsdraTrgtDscsnEndAfter"	, mgmtCmmnCodeService.selectCommonCodeUnit("SCHULW_DSCNTC_CNSDRA_TRGT_END_AFTR_SPRT_SE_CD" , untTaskwkSeCd));
		dataRequest.setResponse("dsHouseKndSe"					   		, mgmtCmmnCodeService.selectCommonCodeUnit("HOUSE_KND_SE_CD"							   , untTaskwkSeCd));
		dataRequest.setResponse("dsResideCstSprtCyclSe"					, mgmtCmmnCodeService.selectCommonCodeUnit("RESIDE_CST_SPRT_CYCL_SE_CD"					   , untTaskwkSeCd));
		dataRequest.setResponse("dsCertiSe"					   			, mgmtCmmnCodeService.selectCommonCodeUnit("CERTI_SE_CD"								   , untTaskwkSeCd));
		dataRequest.setResponse("dsQlfcGradSe"					   		, mgmtCmmnCodeService.selectCommonCodeUnit("QLFC_GRAD_SE_CD"							   , untTaskwkSeCd));
		dataRequest.setResponse("dsLrnExprnCrseSeCd"					, mgmtCmmnCodeService.selectCommonCodeUnit("LRN_EXPRN_CRSE_SE_CD"						   , untTaskwkSeCd));

		/* 2022.12.23
		 *  - TODO : mgmtCmmnCodeService.selectCommonCodeAllUnit 반영되면 아래 주석풀고 반영해야 함.
		 */
//		dataRequest.setResponse("dsOutcCnL"								, mgmtCmmnCodeService.selectCommonCodeAllUnit("OUTC_CN_LCLAS_SE_CD"						   	  , untTaskwkSeCd));
//		dataRequest.setResponse("dsOutcCnM"								, mgmtCmmnCodeService.selectCommonCodeAllUnit("OUTC_CN_MLSFC_SE_CD"						   	  , untTaskwkSeCd));
//		dataRequest.setResponse("dsOutcCnS"								, mgmtCmmnCodeService.selectCommonCodeAllUnit("OUTC_CN_SCLAS_SE_CD"						   	  , untTaskwkSeCd));
//
//		dataRequest.setResponse("dsDtySe"  								, mgmtCmmnCodeService.selectCommonCodeAllUnit("DTY_SE_CD"									  , untTaskwkSeCd));
//		dataRequest.setResponse("dsOccpClSe"							, mgmtCmmnCodeService.selectCommonCodeAllUnit("OCCP_CL_SE_CD"								  , untTaskwkSeCd));
//		dataRequest.setResponse("dsOccpAbilitAtrngt"					, mgmtCmmnCodeService.selectCommonCodeAllUnit("OCCP_ABILIT_STRNGT_MDSTRM_FAILR_CS_SE_CD"	  , untTaskwkSeCd));
//		dataRequest.setResponse("dsOccpAbilitStrngtMdstrmFailrCsLclasSe", mgmtCmmnCodeService.selectCommonCodeAllUnit("OCCP_ABILIT_STRNGT_MDSTRM_FAILR_CS_LCLAS_SE_CD", untTaskwkSeCd));
//		dataRequest.setResponse("dsOccpAbilitStrngtMdstrmFailrCsSclasSe", mgmtCmmnCodeService.selectCommonCodeAllUnit("OCCP_ABILIT_STRNGT_MDSTRM_FAILR_CS_SCLAS_SE_CD", untTaskwkSeCd));
//		dataRequest.setResponse("dsIdntyResult"							, mgmtCmmnCodeService.selectCommonCodeAllUnit("IDNTY_RESULT_SE_CD"							  , untTaskwkSeCd));
//		dataRequest.setResponse("dsStg1PtcptnStts"					   	, mgmtCmmnCodeService.selectCommonCodeAllUnit("STG1_PTCPTN_STTS_SE_CD"						  , untTaskwkSeCd));
//		dataRequest.setResponse("dsSchulwDscntcCnsdraTrgtDscsnResult"  	, mgmtCmmnCodeService.selectCommonCodeAllUnit("SCHULW_DSCNTC_CNSDRA_TRGT_DSCSN_RESULT_SE_CD"  , untTaskwkSeCd));
//		dataRequest.setResponse("dsSchulwDscntcCnsdraTrgtDscsnEndAfter"	, mgmtCmmnCodeService.selectCommonCodeAllUnit("SCHULW_DSCNTC_CNSDRA_TRGT_END_AFTR_SPRT_SE_CD" , untTaskwkSeCd));
//		dataRequest.setResponse("dsHouseKndSe"					   		, mgmtCmmnCodeService.selectCommonCodeAllUnit("HOUSE_KND_SE_CD"							   	  , untTaskwkSeCd));
//		dataRequest.setResponse("dsResideCstSprtCyclSe"					, mgmtCmmnCodeService.selectCommonCodeAllUnit("RESIDE_CST_SPRT_CYCL_SE_CD"					  , untTaskwkSeCd));
//		dataRequest.setResponse("dsCertiSe"					   			, mgmtCmmnCodeService.selectCommonCodeAllUnit("CERTI_SE_CD"								   	  , untTaskwkSeCd));
//		dataRequest.setResponse("dsQlfcGradSe"					   		, mgmtCmmnCodeService.selectCommonCodeAllUnit("QLFC_GRAD_SE_CD"							   	  , untTaskwkSeCd));

		Map<String, String> dmOutcomeDetail = drmgsCaseRegService.outcomeDetail(dataRequest);
		dataRequest.setResponse("dmOutcomeDetail", dmOutcomeDetail);

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(dmOutcomeDetail);
		dataRequest.setResponse("dsOutcomeDetail"	   , list);
		dataRequest.setResponse("dsOutcomeList"  	   , drmgsCaseRegService.outcomeList  (dataRequest));
		dataRequest.setResponse("dsOccpOutList" 	   , drmgsCaseRegService.dsOccpOutList(dataRequest));
		dataRequest.setResponse("dsProgrmList"	 	   , drmgsCaseRegService.dsProgrmList (dataRequest));
		dataRequest.setResponse("dsSchulwList"	 	   , drmgsCaseRegService.dsSchulwList (dataRequest));

		//20221008:강화영:직업역량강화 과거참여이력
		dataRequest.setResponse("dmStgHis"	 	 	   , drmgsCaseRegService.outStgHis (dataRequest));

		//20221124:사례관리 마감기준정보
		dataRequest.setResponse("dsCaseMngDdlnCrtrInfo", monthDdlnService.selectCaseMngDdlnCrtrInfo(dataRequest));

		return new JSONDataView();
	}
	
	/**
	 * 사례관리 내 성과등록 저장
	 * @Method명   : onOutcSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 10. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/onOutcSave.do")
	@ResponseBody
	public View onOutcSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap = drmgsCaseRegService.onOutcSave(request, dataRequest);

		return new JSONDataView();
	}
	
	@RequestMapping(value="/onOutcExcnSave.do")
	@ResponseBody
	public View onOutcExcnSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
				
		Map<String, String> retMap  = drmgsCaseRegService.onOutcExcnSave(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명		: onOutcAllLoad
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     	: Kang.Hwa.Young
	 * @작성일     	: 2022. 7. 9. 
	 * @Method설명	: 성과 일괄 등록 
	 */
	@RequestMapping(value="/onOutcAllLoad.do")
	@ResponseBody
	public View onOutcAllLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		String untTaskwkSeCd  = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			untTaskwkSeCd = loginVO.getUntTaskwk();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
				
		dataRequest.setResponse("dsOutcCnL"		 	   , mgmtCmmnCodeService.selectCommonCodeUnit("OUTC_CN_LCLAS_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsOutcCnM"		 	   , mgmtCmmnCodeService.selectCommonCodeUnit("OUTC_CN_MLSFC_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsOutcCnS"		 	   , mgmtCmmnCodeService.selectCommonCodeUnit("OUTC_CN_SCLAS_SE_CD", untTaskwkSeCd));

		dataRequest.setResponse("dsDtySe"		 	   , mgmtCmmnCodeService.selectCommonCodeUnit("DTY_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsOccpClSe"		   , mgmtCmmnCodeService.selectCommonCodeUnit("OCCP_CL_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsIdntyResult"	 	   , mgmtCmmnCodeService.selectCommonCodeUnit("IDNTY_RESULT_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsHouseKndSe"		   , mgmtCmmnCodeService.selectCommonCodeUnit("HOUSE_KND_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsResideCstSprtCyclSe", mgmtCmmnCodeService.selectCommonCodeUnit("RESIDE_CST_SPRT_CYCL_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsCertiSe"			   , mgmtCmmnCodeService.selectCommonCodeUnit("CERTI_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsQlfcGradSe"		   , mgmtCmmnCodeService.selectCommonCodeUnit("QLFC_GRAD_SE_CD", untTaskwkSeCd));
		dataRequest.setResponse("dsLrnExprnCrseSeCd"   , mgmtCmmnCodeService.selectCommonCodeUnit("LRN_EXPRN_CRSE_SE_CD", untTaskwkSeCd));

		dataRequest.setResponse("dsOutcomeList"	 	   , drmgsCaseRegService.outcomeAllList(dataRequest));
		dataRequest.setResponse("dmOutcomeDetail"	   , drmgsCaseRegService.outcomeAllDetail(dataRequest));

		dataRequest.setResponse("dsSEC330"	 	       , drmgsCaseRegService.dsSEC330(dataRequest));
		
		return new JSONDataView();
	}

	/**
	 * 일괄등록 성과 저장
	 * @Method명   : onOutcAllSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 9. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/onOutcAllSave.do")
	@ResponseBody
	public View onOutcAllSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = drmgsCaseRegService.onOutcAllSave(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 직업역량 강화 등록 팝업 
	 * @Method명   : onOccpAbilitLoad
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 10. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/onOccpAbilitLoad.do")
	@ResponseBody
	public View onOccpAbilitLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		dataRequest.setResponse("dsOccpAbilitStrngtMdstrmFailrCsLclasSe", mgmtCmmnCodeService.selectCommonCodeUnit("OCCP_ABILIT_STRNGT_MDSTRM_FAILR_CS_LCLAS_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsOccpAbilitStrngtMdstrmFailrCsSclasSe", mgmtCmmnCodeService.selectCommonCodeUnit("OCCP_ABILIT_STRNGT_MDSTRM_FAILR_CS_SCLAS_SE_CD", userVo.getUntTaskwk()));
		
		dataRequest.setResponse("dsStg1PtcptnStts", mgmtCmmnCodeService.selectCommonCodeUnit("STG1_PTCPTN_STTS_SE_CD", userVo.getUntTaskwk()));

		dataRequest.setResponse("dsProgrmList", drmgsCaseRegService.dsProgrmList(dataRequest));
		dataRequest.setResponse("dsOccpOutList", drmgsCaseRegService.dsOccpOutList(dataRequest));
		dataRequest.setResponse("dsOutcomeList", drmgsCaseRegService.outcomeList(dataRequest));
		dataRequest.setResponse("dmOutcomeDetail", drmgsCaseRegService.outcomeDetail(dataRequest));
		dataRequest.setResponse("dmStgHis", drmgsCaseRegService.outStgHis(dataRequest));
		
		
		return new JSONDataView();
	}
	
	/**
	 * 직업역량강화 저장
	 * @Method명   : onOccpAbilitSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 10. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/onOccpAbilitSave.do")
	@ResponseBody
	public View onOccpAbilitSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
				
		Map<String, String> retMap  = drmgsCaseRegService.onOccpAbilitSave(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 학업중단숙려제 목록
	 * @Method명   : onOccpAbilitLoad
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 10. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/onSchulwDscntcList.do")
	@ResponseBody
	public View onSchulwDscntcList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {

		dataRequest.setResponse("dsSchulwDscntcList", drmgsCaseRegService.onSchulwDscntcList(dataRequest));
		
		
		return new JSONDataView();
	}
	
	
	/**
	 * 학업중단숙려제 등록 팝업 
	 * @Method명   : onOccpAbilitLoad
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 10. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/onSchulwDscntcLoad.do")
	@ResponseBody
	public View onSchulwDscntcLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		dataRequest.setResponse("dsSchulwDscntcCnsdraTrgtDscsnResult", mgmtCmmnCodeService.selectCommonCodeUnit("SCHULW_DSCNTC_CNSDRA_TRGT_DSCSN_RESULT_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsSchulwDscntcCnsdraTrgtDscsnEndAfter", mgmtCmmnCodeService.selectCommonCodeUnit("SCHULW_DSCNTC_CNSDRA_TRGT_END_AFTR_SPRT_SE_CD", userVo.getUntTaskwk()));

		dataRequest.setResponse("dsSchulwList", drmgsCaseRegService.dsSchulwList(dataRequest));

		
		
		return new JSONDataView();
	}
	
	/**
	 * 학업중단숙려제 저장
	 * @Method명   : onOccpAbilitSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 10. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/onSchulwDscntcSave.do")
	@ResponseBody
	public View onSchulwDscntcSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
				
		Map<String, String> retMap  = drmgsCaseRegService.onSchulwDscntcSave(request, dataRequest);
		
		return new JSONDataView();
	}
		
	/**
	 * 직업역량강화 목록
	 * @Method명   : onOccpAbilitLoad
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 10. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/occpAbilitInsertList.do")
	@ResponseBody
	public View occpAbilitInsertList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {

		dataRequest.setResponse("daOccpAbilitInsertList", drmgsCaseRegService.selectOccpAbilitInsertList(request, dataRequest));
		
		
		return new JSONDataView();
	}
	
	/**
	 * 직업역량강화-설문지
	 * @Method명   : onOccpAbilitLoad
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 16. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/onOccpSurvshtDtl.do")
	@ResponseBody
	public View onOccpSurvshtDtl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> result = drmgsCaseRegService.onOccpSurvshtDtl(request, dataRequest); 
		
		// 세션정보 가져오기
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		String untTaskwkSeCd  = "";

		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			untTaskwkSeCd = loginVO.getUntTaskwk();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		dataRequest.setResponse("dmQustnbMngInfo", result.get("dmQustnbMngInfo"));
		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("ds3", result.get("ds3"));
		dataRequest.setResponse("qustnbList", result.get("qustnbList"));
		dataRequest.setResponse("dsAnswer", result.get("dsAnswer"));
		
		dataRequest.setResponse("dsIdntyQesitmList"   , mgmtCmmnCodeService.selectCommonCodeUnit("INSP_SRVY_TYPE_SCLAS_SE_CD", untTaskwkSeCd));
		return new JSONDataView();
	}
	
	/**
	 * 직업역량강화 - 설문지저장
	 * @Method명   : onOccpSurvshtSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 18. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/onOccpSurvshtSave.do")
	@ResponseBody
	public View onOccpSurvshtSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
				
		Map<String, String> dmSearch  = drmgsCaseRegService.onOccpSurvshtSave(request, dataRequest);
		
		dataRequest.setResponse("dmSearch", dmSearch);
		return new JSONDataView();
	}
	
	/**
	 * 직업역량강화 - 건강검진
	 * @Method명   : selectChupList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/selectChupList.do")
	@ResponseBody
	public View selectChupList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> result = drmgsCaseRegService.selectChupList(request, dataRequest); 
		
		dataRequest.setResponse("dsChupInfo", result.get("dsChupInfo"));
		dataRequest.setResponse("dsChupList", result.get("dsChupList"));
		
		return new JSONDataView();
	}
	
	/**
	 * 사례관리 상세조회
	 * @Method명   : selectCaseMngDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Seung.Yeon
	 * @작성일     : 2022. 8. 12. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/selectCaseMngDetail.do")
	@ResponseBody
	public View selectCaseMngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {

		Map<String, Object> result = drmgsCaseRegService.selectCaseMngDetail(request, dataRequest); 

//		dataRequest.setResponse("dsEmtGhvrCharInspList"   , result.get("dsEmtGhvrCharInspList"));
//		dataRequest.setResponse("dsIntnetSmrtpDgnssList"  , result.get("dsIntnetSmrtpDgnssList"));
//		dataRequest.setResponse("dsSpcltyProgrmDgstfnList", result.get("dsSpcltyProgrmDgstfnList"));
		dataRequest.setResponse("dsInspSrvyList"		  , result.get("dsInspSrvyList"));
		dataRequest.setResponse("dsCaseChupList"	   	  , result.get("dsCaseChupList"));
		dataRequest.setResponse("dsChupList"	   		  , result.get("dsChupList"));
		dataRequest.setResponse("dsActvtSafetyMuaiasList" , result.get("dsActvtSafetyMuaiasList"));
		dataRequest.setResponse("dsCrisisScrenn"          , result.get("dsCrisisScrenn"));
		dataRequest.setResponse("dsHpeSrvc"          	  , result.get("dsHpeSrvc"));
		return new JSONDataView();
	}

	/**
	 * 사례관리 상세정보 저장
	 * @Method명   : processDrmgsCaseRegDetailSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/processDrmgsCaseRegDetailSave.do")
	@ResponseBody
	public View processDrmgsCaseRegDetailSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
		throws Exception {

		Map<String, String> dmParam = drmgsCaseRegService.saveCaseRegDetail(request, dataRequest);

		dataRequest.setResponse("dmParam", dmParam);

		return new JSONDataView();
	}
	
	/**
	 * 사례관리 종결 고유화면
	 * @Method명   : onTrmnLoad
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/onTrmnLoad.do")
	@ResponseBody
	public View onTrmnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> dmOutcomeDetail = drmgsCaseRegService.outcomeDetail(dataRequest);
		dataRequest.setResponse("dmOutcomeDetail", dmOutcomeDetail);
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(dmOutcomeDetail);
		dataRequest.setResponse("dsOutcomeDetail", list);
		
		return new JSONDataView();
	}
	
	/**
	 * 사례관리 상세정보 저장
	 * @Method명   : drmgsCaseTrmnDetailSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/drmgsCaseTrmnDetailSave.do")
	@ResponseBody
	public View drmgsCaseTrmnDetailSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
		throws Exception {

		Map<String, String> dmSearch = drmgsCaseRegService.saveCaseTrmnDetail(request, dataRequest);

		dataRequest.setResponse("dmSearch", dmSearch);

		return new JSONDataView();
	}
	
	/**
	 * 사례관리 성과 대상자 목록
	 * @Method명   : selectOutcMainList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoon Hee Sung
	 * @작성일     : 2022. 11. 21. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/selectOutcMainList.do")
	@ResponseBody
	public View selectOutcMainList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {

		//사례 목록 조회
		//List<Map<String, Object>> list = drmgsCaseRegService.selectOutcMainList(request, dataRequest);
		//dataRequest.setResponse("dsCaseInqList", list);
		Map<String, Object> result =  drmgsCaseRegService.selectOutcPagingList(request, dataRequest);
		
		dataRequest.setResponse("dsCaseInqList", result.get("dsCaseInqList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));
		
		return new JSONDataView();
	}
	
	/**
	 * 사례관리 대상자 목록
	 * @Method명   : selectOutcTrprList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoon Hee Sung
	 * @작성일     : 2022. 11. 21. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/selectOutcTrprList.do")
	@ResponseBody
	public View selectOutcTrprList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {

		//사례 목록 조회
		//List<Map<String, Object>> list = drmgsCaseRegService.selectOutcMainList(request, dataRequest);
		//dataRequest.setResponse("dsCaseInqList", list);
		Map<String, Object> result =  drmgsCaseRegService.selectOutcTrprList(request, dataRequest);
		
		dataRequest.setResponse("dsAddress", result.get("dsAddress"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));
		
		return new JSONDataView();
	}
}

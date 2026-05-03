/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.operrpt.web;

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

import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.subms.cmmn.service.SubmsService;
import isry.subms.preconmng.operrpt.service.OperRptService;

/**
 * @파일명 : OperRptController.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 5. 16.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 5. 16.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/subms/preconmng/operrpt")
public class OperRptController {

	// 공통코드 사용하는 데이터 조회하기 위한 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	// 콤보데이터 조회하는 데이터를 위한 서비스
	@Resource(name = "submsService")
	private SubmsService submsService;
	// 비즈니스 처리 서비스
	@Resource(name = "operRptService")
	private OperRptService operRptService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectOperRptCombo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 5. 16.
	 * @Method설명 : 현황관리의 주간업무보고, 운영보고 화면 로딩
	 */
	@RequestMapping(value = "/selectOperRptCombo.do")
	public View selectOperRptCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsBizYr", submsService.selectBizYrCombo(request));
		dataRequest.setResponse("dsSrvcExcnBiz", submsService.selectSrvcExcnBizCombo(request));
		dataRequest.setResponse("dsResrce", submsService.selectResrceNmCombo(request));
		dataRequest.setResponse("dsOperInst", submsService.selectInstNmCombo(request));
		dataRequest.setResponse("dsRptSe", mgmtCmmnCodeService.selectCommonCodeUnit("RPT_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsDocStts", mgmtCmmnCodeService.selectCommonCodeUnit("APRV_STTS_SE_CD", userVo.getUntTaskwk()));
		dataRequest.setResponse("dsPrtpntLink", mgmtCmmnCodeService.selectCommonCodeUnit("PRTPNT_LINK_PRECON_SE_CD", userVo.getUntTaskwk()));
		
		return new JSONDataView();
	}

	/**
	 * @Method명 : selectOperRptList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2022. 5. 16.
	 * @Method설명 : 운영보고 조회버튼 선택 시
	 */
	@RequestMapping(value = "/selectOperRptList.do")
	public View selectOperRptList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		dataRequest.setResponse("dsList", operRptService.selectOperRptList(dataRequest));

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectOperRptDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 8.
	 * @Method설명 : 운영보고 상세페이지 조회
	 */
	@RequestMapping(value = "/selectOperRpt.do")
	public View selectOperRpt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, String>> dsList = operRptService.selectOperRpt(dataRequest);
		
		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * @Method명   : saveOperRpt
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 : 업무보고테이블 삽입/수정/삭제
	 */
	@RequestMapping(value = "/saveOperRpt.do")
	public View saveOperRpt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		Map<String, Object> returnMap = operRptService.saveOperRpt(request, dataRequest);
		
		dataRequest.setMetadata(true, returnMap);
		
		return new JSONDataView();
	}

}

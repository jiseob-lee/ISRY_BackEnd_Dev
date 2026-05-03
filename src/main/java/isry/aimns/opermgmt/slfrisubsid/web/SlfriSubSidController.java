/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.opermgmt.slfrisubsid.web;

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

import isry.aimns.opermgmt.slfrisubsid.service.SlfriSubSidService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.subms.cmmn.service.SubmsService;

/**
 * @파일명 : SlfriSubSidController.java
 * @프로그램 설명 : 자립장려금 컨트롤러 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 6. 24.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 6. 24.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/aimns/opermgmt/slfrisubsid")
public class SlfriSubSidController {

	// 자립장려금 관련 서비스
	@Resource(name = "slfriSubSidService")
	private SlfriSubSidService slfriSubSidService;
	// 공통 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	// 콤보데이터 관련 서비스
	@Resource(name = "submsService")
	private SubmsService aimnsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectSlfriSubSidCombo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 24.
	 * @Method설명 : 자립장려금페이지 콤보데이터 조회
	 */
	@RequestMapping(value = "/selectSlfriSubSidCombo.do")
	public View selectSlfriSubSidCombo(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		List<Map<String, Object>> dsStts = mgmtCmmnCodeService.selectCommonCodeUnit("APRV_STTS_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> dsBizYr = aimnsService.selectBizYrCombo(request);
		List<Map<String, Object>> dsInst = aimnsService.selectInstNmCombo(request);
		List<Map<String, Object>> dsResrce = aimnsService.selectResrceNmCombo(request);

		dataRequest.setResponse("dsStts", dsStts);
		dataRequest.setResponse("dsBizYr", dsBizYr);
		dataRequest.setResponse("dsInst", dsInst);
		dataRequest.setResponse("dsResrce", dsResrce);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectSlfrisubSidStatusList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 30.
	 * @Method설명 : 교육생 자립장려금 목록 조회
	 */
	@RequestMapping(value = "/selectSlfrisubSidStatusList.do")
	public View selectSlfrisubSidStatusList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		List<Map<String, String>> dsList = slfriSubSidService.selectSlfrisubSidStatusList(request, dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectSlfriSubSidList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 27.
	 * @Method설명 : 자립장려금 목록 조회
	 */
	@RequestMapping(value = "/selectSlfriSubSidList.do")
	public View selectSlfriSubSidList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, String>> dsList = slfriSubSidService.selectSlfriSubSidList(dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectSlfriSubSidInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 28.
	 * @Method설명 : 자립장려금 상세 조회
	 */
	@RequestMapping(value = "/selectSlfriSubSidInfo.do")
	public View selectSlfriSubSidInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, String>> dsList = slfriSubSidService.selectSlfriSubSidInfo(dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : saveSlfriSubSid
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 29.
	 * @Method설명 : 자립장려금 데이터 수정/삭제
	 */
	@RequestMapping(value = "/saveSlfriSubSid.do")
	public View saveSlfriSubSid(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		dataRequest.setParameter("Today", mgmtCmmnCodeService.getSysDate("YYYYMMDD"));

		Map<String, Object> returnMap = slfriSubSidService.saveSlfriSubSid(request, dataRequest);

		dataRequest.setMetadata(true, returnMap);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectSlfriSubSidCheck
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 7. 5.
	 * @Method설명 : 자립장려금 승인/신청중인지 여부 확인
	 */
	@RequestMapping(value = "/selectSlfriSubSidCheck.do")
	public View selectSlfriSubSidCheck(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		Map<String, Object> returnMap = slfriSubSidService.selectSlfriSubSidCheck(dataRequest);

		dataRequest.setMetadata(true, returnMap);

		return new JSONDataView();
	}
}

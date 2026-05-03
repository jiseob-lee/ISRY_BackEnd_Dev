/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.edumng.semstrmng.web;

import java.util.List;
import java.util.Map;

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

import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.subms.cmmn.service.SubmsService;
import isry.subms.edumng.semstrmng.service.SemstrMngService;

/**
 * @파일명 : SemstrMngController.java
 * @프로그램 설명 : 학기관리 컨트롤러 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 5. 6.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 5. 6.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/subms/edumng/semstrmng")
public class SemstrMngController {

	// 학기관리 관련 서비스
	@Resource(name = "semstrMngService")
	private SemstrMngService semstrMngService;
	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	// 이주배경 관련 서비스
	@Resource(name = "submsService")
	private SubmsService submsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectSemstrMngCombo
	 * @param request
	 * @param response
	 * @param dateRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 13.
	 * @Method설명 : 학기관리 콤보 데이터 조회
	 */
	@RequestMapping(value = "/selectSemstrMngCombo.do")
	public View selectSemstrMngCombo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		List<Map<String, Object>> listBizYrCombo = submsService.selectBizYrCombo(request);
		List<Map<String, Object>> listSrvcExcnBizCombo = submsService.selectSrvcExcnBizCombo(request);
		List<Map<String, Object>> listSemstr = mgmtCmmnCodeService.selectCommonCodeUnit("SEMSTR_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> listYesOrNo = mgmtCmmnCodeService.selectCommonCodeUnit("YES_OR_NO", userVo.getUntTaskwk());

		dataRequest.setResponse("dsBizYr", listBizYrCombo);
		dataRequest.setResponse("dsSrvcExcnBiz", listSrvcExcnBizCombo);
		dataRequest.setResponse("dsSemstrSeCd", listSemstr);
		dataRequest.setResponse("dsYesOrNo", listYesOrNo);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectSemstrMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 12.
	 * @Method설명 : 학기관리 목록 조회
	 */
	@RequestMapping(value = "/selectSemstrMngList.do")
	public View selectSemstrMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, String>> listBoard = semstrMngService.selectSemstrMngList(dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

	/**
	 * @Method명   : selectSemstrMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2022. 7. 14. 
	 * @Method설명 : 학기관리 상세 조회
	 */
	@RequestMapping(value = "/selectSemstrMng.do")
	public View selectSemstrMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, String>> listBoard = semstrMngService.selectSemstrMng(dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

	/**
	 * @Method명 : saveSemstrMng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 12.
	 * @Method설명 : 학기 등록/수정
	 */
	@RequestMapping(value = "/saveSemstrMng.do")
	public View saveSemstrMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> message = semstrMngService.saveSemstrMng(request, dataRequest);
		dataRequest.setMetadata(true, message);

		return new JSONDataView();
	}

}

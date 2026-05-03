/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.stats.operprfmnc.web;

import java.util.HashMap;
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
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.subms.cmmn.service.SubmsService;
import isry.subms.stats.operprfmnc.service.OperPrfmncService;

/**
 * @파일명 : OperPrfmncController.java
 * @프로그램 설명 : 운영실적 컨트롤러 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 5. 13.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 5. 13.
 * @수정내용 : - -
 */
@Controller
@RequestMapping("/isry/subms/stats/operprfmnc")
public class OperPrfmncController extends IsryBaseController {

	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	MgmtCmmnCodeService mgmtCmmnCodeService;
	// 이주배경 관련 서비스
	@Resource(name = "submsService")
	SubmsService submsService;
	// 운영실적 관련 서비스
	@Resource(name = "operPrfmncService")
	OperPrfmncService operPrfmncService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectOperPrfmncCombo
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 13.
	 * @Method설명 : 운영실적 콤보데이터 조회
	 */
	@RequestMapping("/selectOperPrfmncCombo.do")
	public View selectNmprPreconListCombo(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		List<Map<String, Object>> listBizYrCombo = submsService.selectBizYrCombo(request);
		List<Map<String, Object>> listSrvcExcnBizCombo = submsService.selectSrvcExcnBizCombo(request);
		List<Map<String, Object>> listInstNmCombo = submsService.selectInstNmCombo(request);
		List<Map<String, Object>> listSemstr = mgmtCmmnCodeService.selectCommonCodeUnit("SEMSTR_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> listProgrm = mgmtCmmnCodeService.selectCommonCodeUnit("EDU_PROGRM_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> listResrceNmCombo = submsService.selectResrceNmCombo(request);
		
		List<Map<String, Object>> semstrCombo = operPrfmncService.selectSemstrCombo(request);
		
		dataRequest.setResponse("dsBizYr", listBizYrCombo);
		dataRequest.setResponse("dsSrvcExcnBiz", listSrvcExcnBizCombo);
		dataRequest.setResponse("dsOperInst", listInstNmCombo);
		dataRequest.setResponse("dsSemstrSeCd", listSemstr);
		dataRequest.setResponse("dsProgrm", listProgrm);
		dataRequest.setResponse("dsSemstr", semstrCombo);
		dataRequest.setResponse("dsResrce", listResrceNmCombo);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectNmprPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 16.
	 * @Method설명 : 인원현황 목록 조회
	 */
	@RequestMapping("/selectNmprPreconList.do")
	public View selectNmprPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, Object> dmSearchMap = new HashMap<>();
		dmSearchMap.put("BIZ_YR", dmSearch.getValue("BIZ_YR"));
		dmSearchMap.put("SRVC_EXCN_BIZ_NO", dmSearch.getValue("SRVC_EXCN_BIZ_NO"));
		dmSearchMap.put("INST_NO", dmSearch.getValue("INST_NO"));
		dmSearchMap.put("SEMSTR_SE_CD", dmSearch.getValue("SEMSTR_SE_CD"));
		dmSearchMap.put("BGNG_YMD", dmSearch.getValue("BGNG_YMD"));
		dmSearchMap.put("END_YMD", dmSearch.getValue("END_YMD"));
		dmSearchMap.put("RESRCE_NO", dmSearch.getValue("RESRCE_NO"));

		List<Map<String, Object>> listBoard = operPrfmncService.selectNmprPreconList(request, dmSearchMap);
		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectNmprAchivRateList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 17.
	 * @Method설명 : 누적인원 및 달성률 목록 조회
	 */
	@RequestMapping("/selectNmprAchivRateList.do")
	public View selectNmprAchivRateList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, Object> dmSearchMap = new HashMap<>();
		dmSearchMap.put("BIZ_YR", dmSearch.getValue("BIZ_YR"));
		dmSearchMap.put("SRVC_EXCN_BIZ_NO", dmSearch.getValue("SRVC_EXCN_BIZ_NO"));
		dmSearchMap.put("INST_NO", dmSearch.getValue("INST_NO"));
		dmSearchMap.put("SEMSTR_SE_CD", dmSearch.getValue("SEMSTR_SE_CD"));
		dmSearchMap.put("RESRCE_NO", dmSearch.getValue("RESRCE_NO"));
		
		List<Map<String, Object>> listBoard = operPrfmncService.selectNmprAchivRateList(request, dmSearchMap);
		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectPrtpntTrgtPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 18.
	 * @Method설명 : 참여자대상별현황 목록 조회
	 */
	@RequestMapping("/selectPrtpntTrgtPreconList.do")
	public View selectPrtpntTrgtPreconList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, Object> dmSearchMap = new HashMap<String, Object>();
		dmSearchMap.put("BIZ_YR", dmSearch.getValue("BIZ_YR"));
		dmSearchMap.put("SRVC_EXCN_BIZ_NO", dmSearch.getValue("SRVC_EXCN_BIZ_NO"));
		dmSearchMap.put("INST_NO", dmSearch.getValue("INST_NO"));
		dmSearchMap.put("SEMSTR_SE_CD", dmSearch.getValue("SEMSTR_SE_CD"));
		dmSearchMap.put("BGNG_YMD", dmSearch.getValue("BGNG_YMD"));
		dmSearchMap.put("END_YMD", dmSearch.getValue("END_YMD"));
		dmSearchMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		dmSearchMap.put("RESRCE_NO", dmSearch.getValue("RESRCE_NO"));

		List<Map<String, Object>> listBoard = operPrfmncService.selectPrtpntTrgtPreconList(dmSearchMap);
		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectPrtpntTrprLinkPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 18.
	 * @Method설명 : 참여자대상자 연계 현황 목록 조회
	 */
	@RequestMapping("/selectPrtpntTrprLinkPreconList.do")
	public View selectPrtpntTrprLinkPreconList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, Object> dmSearchMap = new HashMap<String, Object>();
		dmSearchMap.put("BIZ_YR", dmSearch.getValue("BIZ_YR"));
		dmSearchMap.put("SRVC_EXCN_BIZ_NO", dmSearch.getValue("SRVC_EXCN_BIZ_NO"));
		dmSearchMap.put("INST_NO", dmSearch.getValue("INST_NO"));
		dmSearchMap.put("SEMSTR_SE_CD", dmSearch.getValue("SEMSTR_SE_CD"));
		dmSearchMap.put("BGNG_YMD", dmSearch.getValue("BGNG_YMD"));
		dmSearchMap.put("END_YMD", dmSearch.getValue("END_YMD"));

		List<Map<String, Object>> listBoard = operPrfmncService.selectPrtpntTrprLinkPreconList(dmSearchMap);
		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

}

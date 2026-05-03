/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.atendprecon.web;

import java.util.ArrayList;
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
import isry.subms.preconmng.atendprecon.service.AtendPreconService;

/**
 * @파일명 : AtendPreconController.java
 * @프로그램 설명 : 출석현황 컨트롤러 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 6. 13.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 6. 13.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/subms/preconmng/atendprecon")
public class AtendPreconController {

	// 출석현황 관련 서비스
	@Resource(name = "atendPreconService")
	private AtendPreconService atendPreconService;
	
	// 공통코드 관련 서비스
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	// 이주배경 콤보데이터 관련 서비스
	@Resource(name = "submsService")
	private SubmsService submsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectAtendPreconCombo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 13.
	 * @Method설명 : 출결현황 콤보데이터 조회
	 */
	@RequestMapping(value = "/selectAtendPreconCombo.do")
	public View selectAtendPreconCombo(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		List<Map<String, Object>> listAtnc = mgmtCmmnCodeService.selectCommonCodeUnit("ATNC_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> listAtncCs = mgmtCmmnCodeService.selectCommonCodeUnit("ATNC_CS_SE_CD", userVo.getUntTaskwk());
		List<Map<String, Object>> listSrvcExcnBiz = submsService.selectSrvcExcnBizCombo(request);
		List<Map<String, Object>> listResrce = submsService.selectResrceNmCombo(request);
		List<Map<String, Object>> listInst = submsService.selectInstNmCombo(request);
		List<Map<String, Object>> listBizYr = submsService.selectBizYrCombo(request);

		dataRequest.setResponse("dsAtnc", listAtnc);
		dataRequest.setResponse("dsAtncCs", listAtncCs);
		dataRequest.setResponse("dsSrvcExcnBiz", listSrvcExcnBiz);
		dataRequest.setResponse("dsResrce", listResrce);
		dataRequest.setResponse("dsInst", listInst);
		dataRequest.setResponse("dsBizYr", listBizYr);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectAtendPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 21.
	 * @Method설명 : 출결현황 조회
	 */
	@RequestMapping(value = "/selectAtendPreconList.do")
	public View selectAtendPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, String>> dsList = atendPreconService.selectAtendPreconList(dataRequest);

		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectAtendList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 13.
	 * @Method설명 : 출석조회(일자별)
	 */
	@RequestMapping(value = "/selectAtendList.do")
	public View selectAtendList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, String>> listAtend = atendPreconService.selectAtendList(request, dataRequest);

		dataRequest.setResponse("dsList", listAtend);

		return new JSONDataView();
	}

	/**
	 * @Method명 : saveAtend
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 14.
	 * @Method설명 : 출석 등록/수정/삭제
	 */
	@RequestMapping(value = "/saveAtend.do")
	public View saveAtend(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		atendPreconService.saveAtend(request, dataRequest);

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectAtend
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 8. 9.
	 * @Method설명 : 출결 상세 조회
	 */
	@RequestMapping(value = "/selectAtend.do")
	public View selectAtend(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, String>> dsList = new ArrayList<Map<String, String>>();
		dsList.add(atendPreconService.selectAtend(dataRequest));
		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectAtendByTrpr.do")
	public View selectAtendByTrpr(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, String>> dsList = new ArrayList<Map<String, String>>();
		dsList.add(atendPreconService.selectAtendByTrpr(dataRequest));
		dataRequest.setResponse("dsList", dsList);

		return new JSONDataView();
	}
}

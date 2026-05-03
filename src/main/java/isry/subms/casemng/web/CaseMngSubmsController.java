/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2023 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.subms.casemng.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.subms.casemng.service.CaseMngSubmsService;
import isry.subms.cmmn.service.SubmsService;

/**
 * @파일명 : CaseMngSubmsController.java
 * @프로그램 설명 : 이주배경 사례관리 관련 Controller - -
 * @작성자 : Lee.SangHoon
 * @작성일 : 2023. 8. 7.
 * @수정자 : Lee.SangHoon
 * @수정일 : 2023. 8. 7.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/subms/casemng")
public class CaseMngSubmsController {

	@Resource(name = "caseMngSubmsService")
	private CaseMngSubmsService caseMngSubmsService;

	@Resource(name = "submsService")
	private SubmsService submsService;

	/**
	 * @Method명 : selectMainList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 8. 7.
	 * @Method설명 : 사례목록조회
	 */
	@RequestMapping(value = "/selectMainList.do")
	public View selectMainList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		// 사례 목록 조회
		Map<String, Object> result = caseMngSubmsService.selectCaseinqPagingList(request, dataRequest);

		dataRequest.setResponse("dsCaseInqList", result.get("dsCaseInqList"));
		dataRequest.setResponse("dmPageInfo", result.get("dmPage"));

		return new JSONDataView();
	}

	/**
	 * @Method명 : selectCaseMngOnload
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 8. 7.
	 * @Method설명 : 사례목록 콤보데이터조회
	 */
	@RequestMapping(value = "/selectCaseMngOnload.do")
	public View selectCaseMngOnload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		dataRequest.setResponse("dsSrvcExcnBizCmb", submsService.selectSrvcExcnBizCombo(request));
		dataRequest.setResponse("dsBizYr", submsService.selectBizYrCombo(request));
		return new JSONDataView();
	}
}

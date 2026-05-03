/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.attendmgmt.atend.web;

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

import isry.aimns.attendmgmt.atend.service.AtboYrPreconService;
import isry.subms.cmmn.service.SubmsService;

/**
 * @파일명 : AtboYrPreconController.java
 * @프로그램 설명 : 출석부 연도별 현황 컨트롤러 - -
 * @작성자 : Park.Seong.Won
 * @작성일 : 2022. 7. 27.
 * @수정자 : Park.Seong.Won
 * @수정일 : 2022. 7. 27.
 * @수정내용 : - -
 */

@Controller
@RequestMapping(value = "/isry/aimns/attendmgmt/atend")
public class AtboYrPreconController {

	// 이주배경 관련 서비스
	@Resource(name = "submsService")
	private SubmsService submsService;
	
	// 출석부 관련 서비스
	@Resource(name = "atboYrPreconService")
	private AtboYrPreconService atboYrPreconService;
	
	/**
	 * @Method명 : selectAtboYrPreconCombo
	 * @param request
	 * @param response
	 * @param dateRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 7. 27.
	 * @Method설명 : 출석부 연도별 현황 콤보데이터 조회
	 */
	
	@RequestMapping(value = "/selectAtboYrPreconCombo.do")
	public View selectAtboYrPreconCombo(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception{
		
		List<Map<String, Object>> listBizYrCombo = submsService.selectBizYrCombo(request);
		List<Map<String, Object>> listSelectInstNmCombo = submsService.selectInstNmCombo(request);
		List<Map<String, Object>> listSelectResrceNmCombo = submsService.selectResrceNmCombo(request);
		
		dataRequest.setResponse("dsBizYr", listBizYrCombo);
		dataRequest.setResponse("dsInstNm", listSelectInstNmCombo);
		dataRequest.setResponse("dsResrceNm", listSelectResrceNmCombo);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명 : selectAtboYrPreconList
	 * @param request
	 * @param response
	 * @param dateRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Park.Seong.Won
	 * @작성일 : 2022. 7. 28.
	 * @Method설명 : 출석부 연도별 현황 조회
	 */
	
	@RequestMapping(value = "/selectAtboYrPreconList.do")
	public View selectAtboYrPreconList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		List<Map<String, Object>> listBoard = atboYrPreconService.selectAtboPcList(request, dataRequest);

		dataRequest.setResponse("dsList", listBoard);

		return new JSONDataView();
	}

}

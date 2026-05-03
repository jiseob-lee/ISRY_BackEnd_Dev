/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.sysmng.srvymng.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.csems.sysmng.srvymng.service.SrvyMngService;

/**
 * @파일명        : SrvyMngController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 11. 3. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 11. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller(value = "csemsSrvyMngController" )
@RequestMapping(value = "/isry/csems/sysmng/srvymng")
public class SrvyMngController {
	
	// 드림 설문관리 서비스
	@Resource(name = "csemsSrvyMngService")
	SrvyMngService srvyMngService;
	
	// 설문관리 서비스
	@Resource(name = "srvyMngService")
	isry.csemd.sysmng.srvymng.service.SrvyMngService csemdSrvyMngService;
		
	/**
	 * 
	 * @Method명   : selectSrvySndngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 11. 3. 
	 * @Method설명 : 설문지발송목록대상자 리스트 조회
	 */
	@RequestMapping(value = "/selectSrvySndngList.do")
	public View selectSrvySndngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		dataRequest.setResponse("dsList", srvyMngService.selectSrvySndngList(request, dataRequest));
		
		return new JSONDataView();
	}
	
	
	/**
	 * 
	 * @Method명 : updateQustnbCompno
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 31.
	 * @Method설명 : 설문지 발송(복수)
	 */
	@RequestMapping(value = "/updateQustnbCompno.do")
	public View updateQustnbCompno(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest, Map<String, Object> resultMap)
			throws Exception {

		csemdSrvyMngService.updateQustnbCompno(request, dataRequest, resultMap);

		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명 : updateQustnbSingl
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 11. 3.
	 * @Method설명 : 설문지 발송(단일)
	 */
	@RequestMapping(value = "/updateQustnbSingl.do")
	public View updateQustnbSingl(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest, Map<String, Object> resultMap)
			throws Exception {

		csemdSrvyMngService.updateQustnbSingle(request, dataRequest, resultMap);

		return new JSONDataView();
	}
	
}

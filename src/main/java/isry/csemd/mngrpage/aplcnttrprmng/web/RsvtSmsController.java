/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.mngrpage.aplcnttrprmng.web;

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

import isry.csemd.mngrpage.aplcnttrprmng.service.RsvtSmsService;

/**
 * @파일명        : RsvtSmsController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 10. 14. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 10. 14.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller("csemdRsvtSmsController")
@RequestMapping(value ="/isry/csemd/mngrpage/aplcnttrprmng")
public class RsvtSmsController {
	
	@Resource(name = "csemdRsvtSmsService")
	private RsvtSmsService rsvtSmsService;

	
	/**
	 * 
	 * @Method명   : selectRcptnTrprList
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 14. 
	 * @Method설명 : 문자수신대상자목록 조회
	 */
	@RequestMapping(value = "/selectRcptnTrprList.do")
	public View selectRcptnTrprList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		List<Map<String, String>> info = rsvtSmsService.selectRcptnTrprList(request, dataRequest);
		
		dataRequest.setResponse("dsList", info);
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : insertRcptnTrpr
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 18. 
	 * @Method설명 : 알림문자발송
	 */
	@RequestMapping(value = "/insertRcptnTrpr.do")
	public View insertRcptnTrpr (HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		rsvtSmsService.insertRcptnTrpr(request, dataRequest);
		
		return new JSONDataView();
	}
}

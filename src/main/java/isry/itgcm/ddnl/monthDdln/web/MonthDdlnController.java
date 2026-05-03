/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.ddnl.monthDdln.web;

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

import isry.itgcm.ddnl.monthDdln.service.MonthDdlnService;

/**
 * @파일명        : MonthDdlnController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 10. 25. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 10. 25.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/ddnl/monthDdln")
public class MonthDdlnController {
	
	@Resource(name = "monthDdlnService")
	private MonthDdlnService monthDdlnService; 
	
	/**
	 * @Method명   : selectUntTaskwkInstList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 12. 15. 
	 * @Method설명 : 마감기관별 시도수행기관 조회(콤보박스)
	 */
	@RequestMapping(value = "/selectUntTaskwkInstList.do")
	public View selectUntTaskwkInstList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 시도수행기관, 시군구수행기관 조회
		List<Map<String, Object>> retList = monthDdlnService.selectUntTaskwkInstList(request, dataRequest);
		dataRequest.setResponse("dsInstNm", retList);		 
		
		return new JSONDataView();
	}	
	
	/**
	 * @Method명   : selectMonthDdlnList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 월마감 조회
	 */
	@RequestMapping(value = "/selectMonthDdlnList.do")
	public View selectMonthDdlnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = monthDdlnService.selectMonthDdlnList(request, dataRequest);
		dataRequest.setResponse("dsSEC330", list);
//		Map<String, Object> retMap = monthDdlnService.selectMonthDdlnPrd(request, dataRequest);
//		dataRequest.setResponse("dmMonthDdlnChk", retMap);		

		return new JSONDataView();
	}	
	
	/**
	 * @Method명   : selectMonthDdlnList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 26. 
	 * @Method설명 : 전월마감 조회
	 */
	@RequestMapping(value = "/selectBfeMonthDdlnList.do")
	public View selectBfeMonthDdlnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = monthDdlnService.selectBfeMonthDdlnList(request, dataRequest);
		dataRequest.setResponse("dsSEC330", list);
//		Map<String, Object> retMap = monthDdlnService.selectMonthDdlnPrd(request, dataRequest);
//		dataRequest.setResponse("dmMonthDdlnChk", retMap);		
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectMonthDdlnPrd
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 17. 
	 * @Method설명 : 기간 조회
	 */
	@RequestMapping(value = "/selectMonthDdlnPrd.do")
	public View selectMonthDdlnPrd(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = monthDdlnService.selectMonthDdlnPrd(request, dataRequest);
		dataRequest.setResponse("dmMonthDdlnChk", retMap);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : processMonthDddln
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 10. 27. 
	 * @Method설명 : 월마감 처리
	 */
	@RequestMapping(value = "/processMonthDddln.do")
	public View processMonthDddln(HttpServletRequest request, HttpServletRequest response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> msgMap = monthDdlnService.processMonthDddln(request, dataRequest);
		
		dataRequest.setMetadata(true, msgMap);
		
		return new JSONDataView();
	}

}

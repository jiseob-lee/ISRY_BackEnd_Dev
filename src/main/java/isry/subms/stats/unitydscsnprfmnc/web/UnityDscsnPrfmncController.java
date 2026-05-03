/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.stats.unitydscsnprfmnc.web;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.subms.stats.unitydscsnprfmnc.service.UnityDscsnPrfmncService;

/**
 * @파일명        : UnityDscsnPrfmnc.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Seoung.Jae
 * @작성일        : 2022. 7. 7. 
 * @수정자        : Lee.Seoung.Jae
 * @수정일        : 2022. 7. 7.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/subms/stats/unitydscsnprfmnc")
public class UnityDscsnPrfmncController {
	
	@Resource(name = "unityDscsnPrfmncService")
	private UnityDscsnPrfmncService unityDscsnPrfmncService;
	
	@RequestMapping(value="/selectRegion.do")
	public View selectRegion(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsRegion", unityDscsnPrfmncService.selectRegion());			// 시도
		dataRequest.setResponse("dsRegion2", unityDscsnPrfmncService.selectRegion2());		// 시군구
		
		return new JSONDataView();
	}
	
	@RequestMapping("/selectSxdcDscsnStatsList.do")
	public View selectSxdcDscsnStatsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception{
		dataRequest.setResponse("dsList", unityDscsnPrfmncService.selectSxdcDscsnStatsList(request,dataRequest));
		return new JSONDataView();
	}
	
	@RequestMapping("/selectTrprTypeDscsnStatsList.do")
	public View selectprobmTypeSrvcMthdPrfmncList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception{
		dataRequest.setResponse("dsList", unityDscsnPrfmncService.selectTrprTypeDscsnStatsList(request,dataRequest));
		return new JSONDataView();
	}
	
	@RequestMapping("/selectPvsnMtdAndTrprTypeDscsnStatsList.do")
	public View selectsrvcMthdPrfmncList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception{
		dataRequest.setResponse("dsList", unityDscsnPrfmncService.selectPvsnMtdAndTrprTypeDscsnStatsList(request,dataRequest));
		return new JSONDataView();
	}
	
	@RequestMapping("/selectAgeDscsnStatsList.do")
	public View selecttrprBrthYearPrfmncList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception{
		dataRequest.setResponse("dsList", unityDscsnPrfmncService.selectAgeDscsnStatsList(request,dataRequest));
		return new JSONDataView();
	}
	
	@RequestMapping("/selectYngbgsSttsDscsnStatsList.do")
	public View selectTrprSxdcPrfmncList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception{
		dataRequest.setResponse("dsList", unityDscsnPrfmncService.selectYngbgsSttsDscsnStatsList(request,dataRequest));
		return new JSONDataView();
	}
	
	@RequestMapping("/selectPvsnMtdDscsnStatsList.do")
	public View selecttrprTypeSrvcMthdPrfmncList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception{
		dataRequest.setResponse("dsList", unityDscsnPrfmncService.selectPvsnMtdDscsnStatsList(request,dataRequest));
		return new JSONDataView();
	}
}








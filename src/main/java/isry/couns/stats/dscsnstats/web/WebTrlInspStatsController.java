/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.couns.stats.dscsnstats.service.WebTrlInspStatsService;

/**
 * @파일명        : WebTrlInspStatsController.java
 * @프로그램 설명 : 웹심리검사 통계 Controller Class
 * - 
 * - 
 * @작성자        : Jeong.Won.Je
 * @작성일        : 2023. 2. 10. 
 * @수정자        : Jeong.Won.Je
 * @수정일        : 2023. 2. 10.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/couns/stats/dscsnstats")
public class WebTrlInspStatsController {

	@Resource(name = "webTrlInspStatsService")
	private WebTrlInspStatsService webTrlInspStatsService;
	
	/**
	 * @Method명   : selectWebTrlInspKndList
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return	   : dataSet
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 14. 
	 * @Method설명 : 웹심리검사 통계_검사 종류 List 조회(검사종류/검사구분/실시건수/댓글건수)
	 */
	@RequestMapping(value = "/selectWebTrlInspKndList.do")
	public View selectWebTrlInspKndList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsWebTrlInspKndList", webTrlInspStatsService.selectWebTrlInspKndList(request, dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectWebTrlInspKndDetail
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return	   : dataSet
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 20. 
	 * @Method설명 : 선택한 웹심리검사에 대한 검사결과현황과 검사결과에 대한 내역 조회
	 */
	@RequestMapping(value = "/selectWebTrlInspKndDetail.do")
	public View selectWebTrlInspKndDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		webTrlInspStatsService.selectWebTrlInspKndDetail(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectWebTrlInspProbmSttsList
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return	   : dataSet
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 10. 
	 * @Method설명 : 웹심리검사 문제상태 통계 조회
	 */
	@RequestMapping(value = "/selectWebTrlInspProbmSttsList.do")
	public View selectWebTrlInspProbmSttsList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsWebTrlInspResultList", webTrlInspStatsService.selectWebTrlInspProbmSttsList(request, dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectWebTrlInspDgstfnKndList
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return	   : dataSet
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 웹심리검사 만족도 통계_검사 종류 List 조회(검사종류/응답건수)
	 */
	@RequestMapping(value = "/selectWebTrlInspDgstfnKndList.do")
	public View selectWebTrlInspDgstfnKndList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsWebTrlInspDgstfnKndList", webTrlInspStatsService.selectWebTrlInspDgstfnKndList(request, dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectWebTrlInspDgstfnKndDetail
	 * @param 	   : request
	 * @param 	   : response
	 * @param 	   : dataRequest
	 * @return	   : dataSet
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 웹심리검사 만족도 통계_선택한 검사에 대한 만족도 조사 결과 조회
	 */
	@RequestMapping(value = "/selectWebTrlInspDgstfnKndDetail.do")
	public View selectWebTrlInspDgstfnKndDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsWebTrlInspDgstfnKndDetail", webTrlInspStatsService.selectWebTrlInspDgstfnKndDetail(request, dataRequest));
		
		return new JSONDataView();
	}
}

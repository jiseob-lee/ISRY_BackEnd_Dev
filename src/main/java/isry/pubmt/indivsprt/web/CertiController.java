/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubmt.indivsprt.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;

import isry.pubmt.indivsprt.service.CertiService;

/**
 * 
 * @파일명 : CertiController.java
 * @프로그램 설명 : - -
 * @작성자 : Kim.seong.gyu
 * @작성일 : 2022. 02. 24.
 * @수정자 : 
 * @수정일 : 
 * @수정내용 : - 유연한 구조의 게시판을 개발 하기 위한 프로토 타입의 게시판입니다. -
 */
@Controller
@Api(value = "Certi Controller")
@RequestMapping("/pubmt/indivsprt/certimng")
public class CertiController extends IsryBaseController {

	@Autowired
	private CertiService certiService;
		
	@RequestMapping("/initCerti.do")
	public View init(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		// 게시판 ID(BOARD_ID)
		ParameterGroup mapInit = dataRequest.getParameterGroup("dmInit");
		
		Map<String, String> mapParam = new HashMap<String, String>();
		
		mapParam.put("BOARD_ID", mapInit.getValue("strBoardId"));
		
		List<Map<String, Object>> listCertiCol  = certiService.selectCertiColList(mapParam);
				
		dataRequest.setResponse("dsBoardColList", listCertiCol);
		
		return new JSONDataView();
		
	}	

	@RequestMapping("/onLoadCerti.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		Map<String, String> mapDate = new HashMap<String, String>();

		//현재 일자 조회
		mapDate.put("strToday", certiService.selectSysDate());
		
		dataRequest.setResponse("dmTime", mapDate);
		
		return new JSONDataView();
				
	}
	
	@RequestMapping("/listCerti.do")
	public View list(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		// 게시판 ID(BOARD_ID)
		ParameterGroup mapInit = dataRequest.getParameterGroup("dmCertiSearch");
		
		Map<String, String> mapParam = new HashMap<String, String>();

		mapParam.put("CASE_MNG_NO", mapInit.getValue("CASE_MNG_NO"));
		
		List<Map<String, Object>> listCerti = certiService.selectCertiList(mapParam);
				
		dataRequest.setResponse("dsCertiList", listCerti);
		
		return new JSONDataView();
				
	}
	
	
	// 동적 게시판을 위한 데이터 영역의 호출 URL  
	@RequestMapping("/listCertiCalData.do")	
	public View listCertiCalData(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		Map<String, String> mapParam = new HashMap<>();
		
		List<Map<String, Object>> listCertiColData = certiService.selectCertiColDataList(mapParam);
		
		dataRequest.setResponse("dsCertiColDataList", listCertiColData);
		
		return new JSONDataView();
		
	}
		
//	@RequestMapping("/listCerti_OLD.do")
//	public View list_OLD(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
//			throws Exception {
//		
//		Map<String, String> mapParam = new HashMap<>();
//		
//		List<Map<String, Object>> listCerti = certiService.selectCertiList(mapParam);
//		
//		List<Map<String, Object>> listCertiCol  = certiService.selectCertiColList(mapParam);
//		
//		List<Map<String, Object>> listCertiColData = certiService.selectCertiColDataList(mapParam);
//		
//		dataRequest.setResponse("dsCertiList", listCerti);
//
//		dataRequest.setResponse("dsCertiColList", listCertiCol);
//		
//		dataRequest.setResponse("dsCertiColDataList", listCertiColData);
//		
//		return new JSONDataView();
//		
//	}
		
	@RequestMapping("/saveCerti.do")
	public View save(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> message = certiService.saveCertiList(request, dataRequest);
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
}

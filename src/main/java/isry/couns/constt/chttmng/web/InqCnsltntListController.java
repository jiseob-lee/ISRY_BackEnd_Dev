/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.chttmng.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.constt.chttmng.service.InqCnsltntListService;


/**
 * @파일명        : SpclaController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Song.Young.Il
 * @작성일        : 2022. 5. 4. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2022. 5. 4.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@Api(value = "InqCnsltntListController Controller")
@RequestMapping("/Chttmng")
public class InqCnsltntListController extends IsryBaseController{
	
	@Resource(name = "InqCnsltntListService")
	private InqCnsltntListService inqCnsltntListService;
	
	@RequestMapping("/saveCnsltnt.do")
	public View saveCnsltnt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) {

		Map<String, Object> returnParam = inqCnsltntListService.saveInqCnsltntList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("INDEX_SN", returnParam.get("INDEX_SN"));
		message.put("strFindRowKey", "INDEX_SN == '" + returnParam.get("INDEX_SN") + "'");
		
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectInqCnsltntList.do")
	public View selectSpclaList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) {
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		mapParam.put("searchData", dmSearch.getValue("searchData"));

		List<Map<String , Object>> dsList = inqCnsltntListService.selectInqCnsltntList(mapParam);

		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectInqCnsltntDetail.do")
	public View selectSpclaDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) {
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");

		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("SPCLA_MNG_TRPR_SN", dmDtlParam.getValue("SPCLA_MNG_TRPR_SN"));
		List<Map<String , Object>> dsBoardList = inqCnsltntListService.selectInqCnsltntDetail(mapParam);
		
		dataRequest.setResponse("dsBoardList", dsBoardList);
		return new JSONDataView();
	}
	
	
}

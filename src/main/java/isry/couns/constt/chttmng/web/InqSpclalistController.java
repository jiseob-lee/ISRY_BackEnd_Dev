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
import isry.couns.constt.chttmng.service.InqSpclaService;
import isry.itgcms.util.Masking;

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
@Api(value = "InqSpclalistController Controller")
@RequestMapping("/Spcla")
public class InqSpclalistController extends IsryBaseController{
	
	@Resource(name = "InqSpclaService")
	private InqSpclaService inqSpclaService;

	@RequestMapping("/saveSpcla.do")
	public View saveSpcla(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) {

		Map<String, Object> returnParam = inqSpclaService.saveSpclaBoardList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("SPCLA_MNG_TRPR_SN", returnParam.get("SPCLA_MNG_TRPR_SN"));
		message.put("strFindRowKey", "SPCLA_MNG_TRPR_SN == '" + returnParam.get("SPCLA_MNG_TRPR_SN") + "'");
		
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectSpclaList.do")
	public View selectSpclaList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) {
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		Map<String, Object> mapParam = new HashMap<String, Object>();
		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		int totalCount = 0;		
		 		
		mapParam.put("CLIENA_ID", dmSearch.getValue("CLIENA_ID"));
		mapParam.put("CNSLTNT_ID", dmSearch.getValue("CNSLTNT_ID"));
		mapParam.put("CLIENA_NM_ENCPT", dmSearch.getValue("CLIENA_NM_ENCPT"));
		mapParam.put("CONSTT_NM", dmSearch.getValue("CONSTT_NM"));
		
		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);

		List<Map<String , Object>> dsBoardList = inqSpclaService.selectSpclaList(mapParam);
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();

		//전체 게시글 수
		if(dsBoardList.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", dsBoardList.get(0).get("TOTAL_COUNT"));
		}	
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		
		dataRequest.setResponse("dsBoardList", dsBoardList);
		dataRequest.setResponse("dmPage", resPage);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectSpclaDetail.do")
	public View selectSpclaDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) {
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");

		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("SPCLA_MNG_TRPR_SN", dmDtlParam.getValue("SPCLA_MNG_TRPR_SN"));
		mapParam.put("CLIENA_ID", dmDtlParam.getValue("CLIENA_ID"));
		List<Map<String , Object>> dsBoardList = inqSpclaService.selectSpclaDetail(mapParam);
		
		dataRequest.setResponse("dsBoardList", dsBoardList);
		return new JSONDataView();
	}
	
	/**
	* @Method명   : saveClientName
	* @param 	   : request
	* @param 	   : response
	* @param 	   : dataRequest
	* @return	   : dataSet
	* @throws 	   : Exception
	* @작성자     : Kim.Hai.Ryong
	* @작성일     : 2023. 3. 13. 
	* @Method설명 : 내담자 이름 수정
	*/
	
	@RequestMapping("/saveClientName.do")
	public View saveClientName(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) {

		inqSpclaService.saveClientName(request, dataRequest);
		
		return new JSONDataView();
	}
	
	
}

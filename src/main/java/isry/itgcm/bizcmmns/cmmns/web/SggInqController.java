/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.web;

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

import isry.itgcm.bizcmmns.cmmns.service.SggInqService;

/**
 * @파일명        : SsgInqController.java
 * @프로그램 설명 : 시군구 목록 조회
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 5. 25. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 5. 25.
 * @수정내용      : 
 * -                
 * -                
 */

@Controller
@RequestMapping(value = "/isry/itgcm/bizcmmns/cmmns")
public class SggInqController {
	
	@Resource(name = "ssgInqService")
	private SggInqService ssgInqService;
	
	/**
	 * @Method명   : selectSggInqList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return view
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 5. 25. 
	 * @Method설명 : 시군구 목록 조회
	 */
	
	@RequestMapping(value = "/selectSggInqList.do")
	public View selectSsgInqList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		List<Map<String, Object>> retList = ssgInqService.selectSggInqList(dataRequest);
		dataRequest.setResponse("dsSggInqList", retList);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectSggInqOnLoad
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return view
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 5. 25. 
	 * @Method설명 : 시군구 목록 공통코드 조회
	 */
	@RequestMapping(value = "/selectSggInqOnLoad.do")
	public View selectSsgInqOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		String sRetDsSet = ""; // RETURN 데이터셋
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		
		if (paramGroup != null) {
			
			List<Map<String, String>> paramList = paramGroup.getAllRowList();	// 모든 로우들을 리스트로 반환합니다.
			
			for (Map<String, String> rowMap : paramList) {
				
				sRetDsSet = String.valueOf(rowMap.get("DS_SET_NM"));
				// 공통코드 조회
				List<Map<String, Object>> list = ssgInqService.selectSggCodeList(rowMap);
				dataRequest.setResponse(sRetDsSet, list);
			}
		}
		return new JSONDataView();
	}
}

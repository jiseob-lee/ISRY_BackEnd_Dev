/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbsonm.web;

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

import isry.couns.constt.bbsonm.service.BbsonmListService;
import isry.itgcms.util.Masking;

/**
 * @파일명        : BbsonmController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Song.Young.Il
 * @작성일        : 2022. 5. 13. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2022. 5. 13.
 * @수정내용      : 
 * -                
 * -                
 */

@Controller
@RequestMapping("/constt/bbsonm")
public class BbsonmListController {
	
	@Resource(name = "BbsonmListService")
	private BbsonmListService bbsonmListService;
	
//	@Resource(name = "mgmtCmmnCodeService")
//	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@RequestMapping("/selectBbsonmList.do")
	public View selectBbsonmList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		int totalCount = 0;

		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearchParam");
//		System.out.println("AAAAAAAAAAAAA...."+searchParam.toString());
		
		if(searchParam.getValue("searchKey").equals("NM")) {
			mapParam.put("SEARCH_DATA", searchParam.getValue("searchData"));
		} else {
			mapParam.put("SEARCH_DATA", searchParam.getValue("searchData"));
//			System.out.println("DDDDDDDDDD22::"+searchParam.getValue("searchData"));
			
		}
		mapParam.put("SEARCH_KEY", searchParam.getValue("searchKey"));
		mapParam.put("BBSCTT_TYPE_SE_CD", searchParam.getValue("BBSCTT_TYPE_SE_CD"));
		
		totalCount = bbsonmListService.getTotalCount(mapParam);
		List<Map<String , Object>> dsList = bbsonmListService.selectBbsonmList(mapParam);
		
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		
		if(dsList.size() == 0) {
			resPage.put("totalCount", 0);
		} else {
			resPage.put("totalCount", dsList.get(0).get("TOTAL_COUNT"));
		}
		
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		dataRequest.setResponse("dsList", dsList);
		dataRequest.setResponse("dmPage", resPage);
		return new JSONDataView();
	}
	
	@RequestMapping("/saveBbsonmList.do")
	public View saveBbsonmList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) {

		Map<String, Object> returnParam = bbsonmListService.saveBbsonmList(request, dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("BBSCTT_ESNTAL_NO", returnParam.get("BBSCTT_ESNTAL_NO"));
		message.put("strFindRowKey", "BBSCTT_ESNTAL_NO == '" + returnParam.get("BBSCTT_ESNTAL_NO") + "'");
		
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}

    @RequestMapping("/getDangerLastArticleNo.do")
    public View getDangerLastArticleNo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
    		throws Exception {
        Map<String, Object> dmDangerLastArticle = bbsonmListService.selectU11DangerLastArticleNo();
        
        dataRequest.setResponse("dmDangerLastArticle", dmDangerLastArticle);
        
    	return new JSONDataView();
    }
	
}

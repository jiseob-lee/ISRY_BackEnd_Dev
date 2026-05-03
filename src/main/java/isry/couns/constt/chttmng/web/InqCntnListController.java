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

import isry.base.IsryBaseController;
import isry.couns.constt.chttmng.service.InqCntnListService;

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
@RequestMapping("/chttmng")
public class InqCntnListController extends IsryBaseController{
	
	@Resource(name = "InqCntnListService")
	private InqCntnListService inqCntnListService;
	
//	@Resource(name = "mgmtCmmnCodeService")
//	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@RequestMapping("/selectInqCntnList.do")
	public View selectInqCntnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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
		mapParam.put("CHTT_LOG_ESNTAL_NO", searchParam.getValue("CHTT_LOG_ESNTAL_NO"));			//채팅로그번호
		mapParam.put("SEARCH_KEY", searchParam.getValue("searchKey"));							//검색조건
		mapParam.put("SEARCH_DATA", searchParam.getValue("searchData"));						//검색어
									

		ParameterGroup searchtime = dataRequest.getParameterGroup("dmTime");
		
		mapParam.put("START_DATE", searchtime.getValue("startDate"));								//조회시작날짜
		mapParam.put("END_DATE", searchtime.getValue("endDate"));									//조회끝날짜
		List<Map<String , Object>> dsBoardList = inqCntnListService.selectInqCntnList(mapParam);
		
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
	
	@RequestMapping("/selectInqCntnDetailPopup.do")
	public View selectInqCntnDetailPopup(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("CHRO_NO", dmDtlParam.getValue("CHRO_ID"));
		//게시글 상세 조회
		List<Map<String, Object>> dsList = inqCntnListService.selectInqCntnDetailPopup(mapParam);
		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}

}

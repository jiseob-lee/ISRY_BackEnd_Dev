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
import isry.couns.constt.chttmng.service.InqLobbyListService;

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
public class InqLobbyListController extends IsryBaseController{
	
	@Resource(name = "InqLobbyListService")
	private InqLobbyListService inqLobbyListService;
	
	@RequestMapping("/selectInqLobbyList.do")
	public View selectInqLobbyList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
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
		mapParam.put("START_DATE", searchtime.getValue("startDate"));							//조회시작날짜
		mapParam.put("END_DATE", searchtime.getValue("endDate"));								//조회끝날짜
		List<Map<String , Object>> dsBoardList = inqLobbyListService.selectInqLobbyList(mapParam);	//본인상담
		
		totalCount = inqLobbyListService.getTotalCount(mapParam);
		// 페이징 데이터 맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();

		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);
		dataRequest.setResponse("dsBoardList", dsBoardList);
		dataRequest.setResponse("dmPage", resPage);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectInqLobbyDetail.do")
	public View selectInqLobbyDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup dmDtlParam = dataRequest.getParameterGroup("dmDtlParam");
		mapParam.put("CHTT_LOG_ESNTAL_NO", dmDtlParam.getValue("CHTT_LOG_ESNTAL_NO"));
		//게시글 상세 조회
		List<Map<String, Object>> dsBoardList = inqLobbyListService.selectInqLobbyDetail(mapParam);
		//게시글 조회수 증가
		inqLobbyListService.inqLobbyDtlCnt(mapParam);
		
		dataRequest.setResponse("dsBoardList", dsBoardList);
		return new JSONDataView();
	}

	@RequestMapping("/saveLobbyList.do")
	public View saveLobbyList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> returnParam = inqLobbyListService.saveLobbyList(request, dataRequest);

		// 필요가 없는거 같다?!
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("CHTT_LOG_ESNTAL_NO", returnParam.get("CHTT_LOG_ESNTAL_NO"));
		message.put("strFindRowKey", "CHTT_LOG_ESNTAL_NO == '" + returnParam.get("CHTT_LOG_ESNTAL_NO") + "'");

		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
	
	@RequestMapping("/selectIdCheck.do")
	public View selectIdCheck(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> message = new HashMap<String, Object>();
		int ii = inqLobbyListService.selectIdCheck(request, dataRequest);

		if(ii > 0) {
			message.put("CHECK", "99");
		} else {
			message.put("CHECK", "00");
		}

		dataRequest.setResponse("dmIdCheck", message);
		return new JSONDataView();
	}
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.sample.web;

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

import isry.sample.service.CmnCodeService;
import isry.sample.service.TstBoardDevService;
import isry.sample.service.TstGridDevService;
import isry.sample.service.TstGridGridDevService;

/**
 * @파일명 : TstBoardDevController.java
 * @프로그램 설명 : - -
 * @작성자 : You Minsang
 * @작성일 : 2021. 12. 20.
 * @수정자 : You Minsang
 * @수정일 : 2021. 12. 20.
 * @수정내용 : - -
 */
@Controller
@RequestMapping("/TstBoard")
public class TstBoardDevController {

	@Autowired
	private TstBoardDevService tstBoardDevService;

	@RequestMapping("/onLoad.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, String> mapDate = new HashMap<String, String>();

		mapDate.put("strToday", tstBoardDevService.selectSysDate());
		dataRequest.setResponse("dmTime", mapDate);

		return new JSONDataView();
	}

	@RequestMapping("/list.do")
	public View list(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		// 페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");

		// 페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		String totalCount = tstBoardDevService.getTotalCount();
						
		// 페이지 인덱싱에 필요한 정보를 정제합니다.
		int pageIdx = Integer.parseInt((String) reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String) reqPage.getValue("pageRowCount"));
		int startIndex = (pageIdx - 1) * rowSize;
		
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		mapParam.put("START_IDX", startIndex);
		mapParam.put("ROW_COUNT", rowSize);

		List<Map<String, Object>> listBoard = tstBoardDevService.selectBoardList(mapParam);

		// 데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totalCount);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);

		dataRequest.setResponse("dsBoardList", listBoard);
		dataRequest.setResponse("dmPage", resPage);

		return new JSONDataView();
	}

	@RequestMapping("/save.do")
	public View save(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		String strFindRowKey = tstBoardDevService.saveBoardList(dataRequest);

		Map<String, Object> message = new HashMap<String, Object>();
		message.put("strFindRowKey", "BRD_SEQ == '" + strFindRowKey + "'");
		dataRequest.setMetadata(true, message);
		return new JSONDataView();
	}
}

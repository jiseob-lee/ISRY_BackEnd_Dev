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

import isry.sample.service.TstBoardDevService2;

/**
 * @파일명        : TstBoardDevController2.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Jeong.Tae.Young
 * @작성일        : 2022. 3. 23. 
 * @수정자        : Jeong.Tae.Young
 * @수정일        : 2022. 3. 23.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/TstBoard2")
public class TstBoardDevController2 {

	@Autowired
	private TstBoardDevService2 tstBoardDevService2;
	
	@RequestMapping("/onLoad.do")
	public View onLoad(HttpServletRequest requset, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, String> mapDate = new HashMap<String, String>();
		
		mapDate.put("strToday", tstBoardDevService2.selectSysDate());
		dataRequest.setResponse("dmTime", mapDate);
		
		
		return new JSONDataView();
	}
	
	@RequestMapping("/list.do")
	public View list(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//페이지 인덱싱에 필요한 정보를 가진 데이터 맵을 가져옵니다.
		ParameterGroup reqPage = dataRequest.getParameterGroup("dmPage");
		
		//페이지 인덱싱에 필요한 전체 데이터 갯수를 가져옵니다.
		String totalCount = tstBoardDevService2.getTotalCount();
		
		//페이지 인덱싱에 필요한 정보를 정제합니다.		
		int totCnt  = Integer.parseInt(totalCount);
		int pageIdx = Integer.parseInt((String)reqPage.getValue("pageNo"));
		int rowSize = Integer.parseInt((String)reqPage.getValue("pageRowCount"));
				
		//쿼리에서 사용할 파라미터를 지정해줍니다.
		int startIndex = (pageIdx-1)*rowSize;
		int lastIndex = startIndex + rowSize;
				
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("START_IDX", startIndex);
		mapParam.put("LAST_IDX", lastIndex);
		
		List<Map<String, Object>> listBoard = tstBoardDevService2.selectBoardList(mapParam);
		
		//데이터맵에 저장할 데이터를 지정해줍니다.
		Map<String, Object> resPage = new HashMap<String, Object>();
		resPage.put("totalCount", totCnt);
		resPage.put("pageNo", pageIdx);
		resPage.put("pageRowCount", rowSize);		
		
		dataRequest.setResponse("dsBoardList", listBoard);
		dataRequest.setResponse("dmPage", resPage);	
				
		return new JSONDataView();
	}
}

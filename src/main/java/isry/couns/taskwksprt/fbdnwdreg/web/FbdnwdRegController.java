/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.fbdnwdreg.web;

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
import isry.couns.taskwksprt.fbdnwdreg.service.FbdnwdRegService;
import isry.itgcms.util.ScpDb;
import isry.sample.service.NoticeBoardService;

/**
 * @파일명        : FbdnwdRegController.java
 * @프로그램 설명 : 금칙어 등록
 * - 
 * - 
 * @작성자        : 박찬호
 * @작성일        : 2022. 5. 19. 
 * @수정자        : 박찬호
 * @수정일        : 2022. 5. 19.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@Api(value = "FbdnwdReg Controller")
@RequestMapping("/fbdnwdreg")
public class FbdnwdRegController {
	@Autowired
	private FbdnwdRegService fbdnwdRegService;
	
	@RequestMapping("/selectFbdnwdRegList.do") // 금칙어 목록 조회
	public View selectFbdnwdRegList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		//log.debug("들어왔다");
		Map<String, Object> mapParam = new HashMap<String, Object>();

		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		if(searchParam!=null) {
			mapParam.put("strId", searchParam.getValue("strId")); // 검색조건
		}
		
		// 게시판 기본 데이터 호출
		List<Map<String, Object>> board = fbdnwdRegService.selectFbdnwdRegList(mapParam);
		ScpDb scpDb = new ScpDb();
       	
		dataRequest.setResponse("dsList", board);

		return new JSONDataView();

	}

	@RequestMapping("/saveFbdnwdReg.do") // 금칙어 등록 및 삭제
	public View saveNoticeBoard(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		fbdnwdRegService.saveFbdnwdReg(request, dataRequest);

		return new JSONDataView();
	}
	
	
	
}

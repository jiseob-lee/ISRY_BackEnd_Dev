/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.itgBrd.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovProperties;
import io.swagger.annotations.Api;
import isry.itgcms.itgBrd.service.ItgBrdCmnService;

/**
 * @파일명 : itgNtcBrdController.java
 * @프로그램 설명 : 통합 공지 게시판 - -
 * @작성자 : You Minsang
 * @작성일 : 2022. 6. 30.
 * @수정자 : You Minsang
 * @수정일 : 2022. 6. 30.
 * @수정내용 : - -
 */
@Controller
@Api(value = "integrate Notice Board Controller")
@RequestMapping("/itgBrdCmn")
public class ItgBrdCmnController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "msg")
	protected EgovMessageSource msg;

	@Resource(name = "prop")
	protected EgovProperties prop;

	@Autowired
	private ItgBrdCmnService itgBrdCmnService;

	@RequestMapping("/onLoadItgBrdCmnPop.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();

		// 게시판 구분 코드
		ParameterGroup mapInit = dataRequest.getParameterGroup("dmParam");

		mapParam.put("UNITY_BBSCTT_TYPE_SE_CD", mapInit.getValue("strBbscttTypeSeCd"));

		// 업무단위 전체 카테고리 반환
		List<Map<String, Object>> listItgNtcCtgrybSeCd = itgBrdCmnService.selectAllCtgrySeCdList(mapParam);

		dataRequest.setResponse("dsCmbCtgrybSeCd", listItgNtcCtgrybSeCd);

		return new JSONDataView();

	}

	@RequestMapping("/listItgBrdCmnPop.do")
	public View list(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();

		ParameterGroup mapInit = dataRequest.getParameterGroup("dmParam");
		
		mapParam.put("UNITY_BBSCTT_CTGRYB_SE_CD", mapInit.getValue("strBbscttCtgrybSeCd"));

		// 게시판 기본 데이터 조회
		List<Map<String, Object>> listCtgrybInst = itgBrdCmnService.selectCtgtybInstList(mapParam);

		dataRequest.setResponse("dsCtgrybInstList", listCtgrybInst);

		return new JSONDataView();

	}

}

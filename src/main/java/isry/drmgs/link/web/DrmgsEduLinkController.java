/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.link.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.drmgs.link.service.DrmgsEduLinkService;

/**
 * @파일명        : EduLinkController.java
 * @프로그램 설명 : 교육청 연계신청 목록
 * @작성자        : Yoon.Hee.Sung
 * @작성일        : 2023. 8. 28. 
 * @수정자        : Yoon.Hee.Sung
 * @수정일        : 2023. 8. 28. 
 * @수정내용      : 교육청 연계신청 목록
 */

@Controller
@RequestMapping(value = "/isry/drmgs/eduLink")
public class DrmgsEduLinkController extends IsryBaseController {
	
	@Resource(name = "drmgsEduLinkService")
	private DrmgsEduLinkService drmgsEduLinkService;
	
	/**
	 * 교육부 연계 목록 조회
	 * @Method명   : selectLinkRqstDetList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 08. 28.
	 * @Method설명 : 교육부 연계 목록 조회
	 */	
	@RequestMapping(value = "/selectEduLinkList.do")
	public View selectLinkRqstDetList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 조회
		Map<String, Object> result =  drmgsEduLinkService.selectEduLinkList(request, dataRequest);
		
		dataRequest.setResponse("dsList", result.get("dsList"));
		dataRequest.setResponse("dmPage", result.get("dmPage"));
		
		return new JSONDataView();
	}
	
	/**
	 * 교육부 연계 대상자 상세 조회
	 * @Method명   : selectEduDetInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 08. 28.
	 * @Method설명 : 교육부 연계 대상자 상세 조회
	 */	
	@RequestMapping(value = "/selectEduDetInfo.do")
	public View selectEduDetInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 조회
		List<Map<String, Object>> result =  drmgsEduLinkService.selectEduDetInfo(request, dataRequest);
		
		dataRequest.setResponse("dsDetail", result);
		
		return new JSONDataView();
	}
}

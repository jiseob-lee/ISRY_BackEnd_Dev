/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.userjoin.web;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.userjoin.service.SrchAddrService;

/**
 * 
 * @파일명        : SrchAddrController.java
 * @프로그램 설명 : 주소 검색
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 11. 23. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 11. 23.
 * @수정내용      : 
 * -                
 * -
 */
@Controller
//@Api(value = "SrchAddr web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/userjoin")
public class SrchAddrController extends IsryBaseController {

	@Resource(name = "srchAddrService")
	private SrchAddrService srchAddrService;

	//@ApiOperation(value = "/searchAddr.do", notes = "주소검색 [공통] 이지섭")
	@RequestMapping(value = "/searchAddr.do")
	public View searchAddr(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		log.debug("addr search start.");
		
		ParameterGroup param = dataRequest.getParameterGroup("dmKeyword");
		String search = param.getValue("keyword");
		
		dataRequest.setResponse("dsSearchResult", search == null || "".equals(search) ? null : srchAddrService.selectAddr(search));
		
		log.debug("addr search end.");
		
		return new JSONDataView();
	}

	//@ApiOperation(value = "/selectAddrArea.do", notes = "주소 지역 구분 코드 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectAddrArea.do")
	public View selectAddrArea(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		//log.debug("addr search start.");
		
		//ParameterGroup param = dataRequest.getParameterGroup("dmKeyword");
		//String search = param.getValue("keyword");
		
		dataRequest.setResponse("dsAddrArea", srchAddrService.selectAddrArea());
		
		//log.debug("addr search end.");
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/searchAddrJob.do")
	public View searchAddrJob(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		try {
			String[] search = {"가", "나", "다", "라", "마", "바", "사", "아", "자", "차", "카", "타", "파", "하"};
			for (int i=0; i < search.length; i++) {
				log.debug("#### search : " + search[i]);
				srchAddrService.selectAddr(search[i]);
			}
		} catch (IOException e1) {
			log.debug(e1.getMessage());
		} catch (Exception e1) {
			log.debug(e1.getMessage());
		}
				
		return new JSONDataView();
	}
		
}

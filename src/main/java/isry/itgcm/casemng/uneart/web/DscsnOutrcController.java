/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.uneart.web;

import java.util.HashMap;
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
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcm.bizcmmns.cmmns.service.ComCodeService;
import isry.itgcm.casemng.uneart.service.DscsnOutrcService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;


/**
* @Class Name  : DscsnOutrcController.java
* @Description : 아웃리치정보 Controller Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 23.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 23.  Seo.Hae.Seok    최초작성
* </pre>
*/
@Controller
@RequestMapping(value = "/isry/itgcm/casemng/uneart")
public class DscsnOutrcController {
	
	@Resource(name = "dscsnOutrcService")
	private DscsnOutrcService dscsnOutrcService;
	

	/**
	 * @Method     : selectDscsnOutrcDetail
	 * @Method설명 : 발굴(아웃리치) 상세조회, 연합거리상담조회, 쉼터자체활동조회, 지원서비스실적, 조치현황
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@RequestMapping(value = "/selectDscsnOutrcDetail.do")
	public View selectDscsnOutrcDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = dscsnOutrcService.selectDscsnOutrcDetail(dataRequest);
		
		// 발굴(아웃리치) 상세조회
		dataRequest.setResponse("dmDetail",   retMap.get("dmDetail"));
		
		// 발굴(아웃리치) 연합거리상담조회
		dataRequest.setResponse("dsUnite", retMap.get("dsUnite"));
		
		// 발굴(아웃리치) 쉼터자체활동조회
		dataRequest.setResponse("dsSheltr", retMap.get("dsSheltr"));
		
		// 발굴(아웃리치) 지원서비스실적
		dataRequest.setResponse("dmPrfmnc",   retMap.get("dmPrfmnc"));
		
		// 발굴(아웃리치) 조치현황
		dataRequest.setResponse("dsActn", retMap.get("dsActn"));
		
		dataRequest.setResponse("dmUneartActbtCl", retMap.get("dmUneartActbtCl"));

		return new JSONDataView();
	}

	/**
	 * @Method     : selectDscsnOutrcUniteList
	 * @Method설명 : 발굴(초기상담) 연합거리상담조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@RequestMapping(value = "/selectDscsnOutrcUniteList.do")
	public View selectDscsnOutrcUniteList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = dscsnOutrcService.selectDscsnOutrcUniteList(dataRequest);
		dataRequest.setResponse("dsUnite", list);

		return new JSONDataView();
	}

	/**
	 * @Method     : selectDscsnOutrcSheltrList
	 * @Method설명 : 발굴(초기상담) 쉼터자체활동조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@RequestMapping(value = "/selectDscsnOutrcSheltrList.do")
	public View selectDscsnOutrcSheltrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = dscsnOutrcService.selectDscsnOutrcSheltrList(dataRequest);
		dataRequest.setResponse("dsSheltr", list);

		return new JSONDataView();
	}

	/**
	 * @Method     : selectDscsnOutrcPrfmncDetail
	 * @Method설명 : 발굴(초기상담) 지원서비스실적
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@RequestMapping(value = "/selectDscsnOutrcPrfmncDetail.do")
	public View selectDscsnOutrcPrfmncDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> map = dscsnOutrcService.selectDscsnOutrcPrfmncDetail(dataRequest);
		dataRequest.setResponse("dmPrfmnc", map);

		return new JSONDataView();
	}

	/**
	 * @Method     : selectDscsnOutrcActnList
	 * @Method설명 : 발굴(초기상담) 조치현황
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@RequestMapping(value = "/selectDscsnOutrcActnList.do")
	public View selectDscsnOutrcActnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = dscsnOutrcService.selectDscsnOutrcActnList(dataRequest);
		dataRequest.setResponse("dsActn", list);

		return new JSONDataView();
	}

	/**
	 * @Method     : processDscsnOutrcDetail
	 * @Method설명 : 발굴(아웃리치) 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@RequestMapping(value = "/processDscsnOutrcDetail.do")
	public View processDscsnOutrcDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap  = dscsnOutrcService.processDscsnOutrcDetail(request, dataRequest);

		// 재조회시 초기상담번호(ER) 매핑을 위해 화면에 내려준다
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("ERYY_GR_DSCSN_NO", retMap.get("ERYY_GR_DSCSN_NO"));		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}

}

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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcm.casemng.uneart.service.DscsnUneartService;


/**
* @Class Name  : DscsnUneartController.java
* @Description : 발굴정보 Controller Class
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
public class DscsnUneartController {
	
	@Resource(name = "dscsnUneartService")
	private DscsnUneartService dscsnUneartService;
	
	/**
	 * @Method     : selectDscsnUneartList
	 * @Method설명 : 발굴 목록조회(전체 or 01:초기상담,02:아웃리치,03.긴급개입)
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@RequestMapping(value = "/selectDscsnUneartList.do")
	public View selectDscsnUneartList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = dscsnUneartService.selectUneartDscsnList(request, dataRequest);
		dataRequest.setResponse("dsList", list);
		
		return new JSONDataView();
	}

	/**
	 * @Method     : selectDscsnUneartDetail
	 * @Method설명 : 발굴(초기상담) 상세조회, 조치내역(대상자)조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@RequestMapping(value = "/selectDscsnUneartDetail.do")
	public View selectDscsnUneartDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = dscsnUneartService.selectDscsnUneartDetail(dataRequest);
		
		// 발굴(초기상담) 상세조회
		dataRequest.setResponse("dmDetail",   retMap.get("dmDetail"));
		
		// 발굴(초기상담) 조치내역(대상자)조회
		dataRequest.setResponse("dsActnList", retMap.get("dsActnList"));

		return new JSONDataView();
	}

	/**
	 * @Method     : selectDscsnUneartHstrList
	 * @Method설명 : 발굴(초기상담) 이력조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	@RequestMapping(value = "/selectDscsnUneartHstrList.do")
	public View selectDscsnUneartHstrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = dscsnUneartService.selectDscsnUneartHstrList(dataRequest);
		dataRequest.setResponse("dsHstrList", list);

		return new JSONDataView();
	}

	/**
	 * @Method     : processDscsnUneartDetail
	 * @Method설명 : 발굴(초기상담) 상세저장(등록,수정,삭제,이력)
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 18. 
 	 */	
	@RequestMapping(value = "/processDscsnUneartDetail.do")
	public View processDscsnUneartDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap  = dscsnUneartService.processDscsnUneartDetail(request, dataRequest);

		// 재조회시 초기상담번호(ER) 매핑을 위해 화면에 내려준다
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("ERYY_DSCSN_NO", retMap.get("ERYY_DSCSN_NO"));		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}

}

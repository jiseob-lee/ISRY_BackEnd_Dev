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

import isry.itgcm.casemng.uneart.service.EmrgIntrvnService;

/**
* @Class Name  : EmrgIntrvnController.java
* @Description : 긴급개입 Controller Class
*
* @author  : Kwon.Min.Seo
* @since   : 2022. 06. 14.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 06. 14.  Kwon.Min.Seo    최초작성
* </pre>
*/
@Controller
@RequestMapping("/isry/itgcm/casemng/uneart")
public class EmrgIntrvnController {
	
	
	@Resource(name = "emrgIntrvnService")
	private EmrgIntrvnService emrgIntrvnService;
	
	/**
	 * @Method     : selectEmrgIntrvnDetail
	 * @Method설명 : 긴급개입 상세조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 06. 14. 
 	 */	
	@RequestMapping(value = "/selectEmrgIntrvnDetail.do")
	public View selectEmrgIntrvnDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> retList = emrgIntrvnService.selectEmrgIntrvnDetail(dataRequest);
		List<Map<String, Object>> retList2 = emrgIntrvnService.selectEmrgIntrvnActnMatter(dataRequest);
		
		// 긴급개입 상세조회
		dataRequest.setResponse("dsEmrgIntrvnDetail",   retList);
		// 긴급개입조치사항
		dataRequest.setResponse("dsEmrgIntrvnActnMatter",   retList2);

		return new JSONDataView();
	}
	
	/**
	 * @Method     : processEmrgIntrvnDetail
	 * @Method설명 : 긴급개입 상세저장(등록,수정,삭제)
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 05. 18. 
 	 */	
	@RequestMapping(value = "/processEmrgIntrvn.do")
	public View processEmrgIntrvnDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap  = emrgIntrvnService.processEmrgIntrvn(request, dataRequest);

		// 재조회시 초기상담번호(ER) 매핑을 위해 화면에 내려준다
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("EMRG_INTRVN_NO", retMap.get("EMRG_INTRVN_NO"));		
		dataRequest.setMetadata(true, message);
		
		return new JSONDataView();
	}



}

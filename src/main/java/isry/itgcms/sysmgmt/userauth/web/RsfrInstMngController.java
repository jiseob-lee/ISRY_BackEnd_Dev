/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.userauth.service.RsfrInstMngService;

/**
 * @파일명        : RsfrInstMngController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2023. 1. 5. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2023. 1. 5.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class RsfrInstMngController {
	
	@Resource(name = "rsfrInstMngService")
	private RsfrInstMngService rsfrInstMngService;
	
	/**
	 * @Method명   : selectRsfrInstMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 순수자원제공주체기관 목록
	 */
	@RequestMapping(value = "/selectRsfrInstMngList.do")
	public View selectRsfrInstMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsRsfrInstMngList", rsfrInstMngService.selectRsfrInstMngList(request, dataRequest));

		return new JSONDataView();
	}	
	
	/**
	 * @Method명   : selectRsfrInstDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 순수자원제공주체기관 상세정보
	 */
	@RequestMapping(value = "/selectRsfrInstDetail.do")
	public View selectRsfrInstDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = rsfrInstMngService.selectRsfrInstDetail(request, dataRequest);
		
		
		dataRequest.setResponse("dsRsfrInstMngDetail", retMap.get("detail"));		/* 상세정보*/
		dataRequest.setResponse("dsRsfrInstMngHistory", retMap.get("history"));	/* 변경이력*/
		
		return new JSONDataView();
	}	
	
	/**
	 * @Method명   : processRsfrInst
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공주체기관 처리
	 */
	@RequestMapping(value = "/processRsfrInst.do")
	public View processRsfrInst(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setMetadata(true, rsfrInstMngService.processRsfrInst(request, dataRequest));
		
		return new JSONDataView();
	}	
	

}

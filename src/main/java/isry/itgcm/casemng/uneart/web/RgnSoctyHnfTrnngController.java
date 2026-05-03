/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.uneart.web;

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

import isry.itgcm.casemng.uneart.service.RgnSoctyHnfTrnngService;

/**
 * @파일명        : RgnSoctyController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2023. 5. 19. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2023. 5. 19.
 * @수정내용      : 지역
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/casemng/uneart")
public class RgnSoctyHnfTrnngController {
	
	
	@Resource(name = "rgnSoctyHnfTrnngService")
	private RgnSoctyHnfTrnngService rgnSoctyHnfTrnngService;
	
	/**
	 * 
	 * @Method명   : selectTrprInqList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 25. 
	 * @Method설명 : 지역사회 인력양성 목록
	 */
	@RequestMapping(value = "/selectRgnSoctyHnfTrnngInqList.do")
	public View selectTrprInqList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = rgnSoctyHnfTrnngService.selectRgnSoctyHnfTrnngInqList(request, dataRequest);
		dataRequest.setResponse("dsList", retMap.get("dsList"));
		dataRequest.setResponse("dmPage", retMap.get("dmPage"));

		return new JSONDataView();
	}	
	
	/**
	 * @Method명   : selectRgnSoctyHnfTrnngDetail
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 25. 
	 * @Method설명 : 지역사회 인력양성 상세정보
	 */
	@RequestMapping(value = "/selectRgnSoctyHnfTrnngDetail.do")
	public View selectRgnSoctyHnfTrnngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = rgnSoctyHnfTrnngService.selectRgnSoctyHnfTrnngDetail(request, dataRequest);
		
		dataRequest.setResponse("dsList"             , retMap.get("dsList"));
		dataRequest.setResponse("dsBizReg"           , retMap.get("dsBizReg"));
		dataRequest.setResponse("dsExcnSrvcBizClList", retMap.get("dsExcnSrvcBizClList"));
		
		return new JSONDataView();
	}	
	
	/**
	 * @Method명   : processRgnSoctyHnfTrnng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 25. 
	 * @Method설명 : 지역사회 인력양성 등록,수정,삭제
	 */
	@RequestMapping(value = "/processRgnSoctyHnfTrnng.do")
	public View processRgnSoctyHnfTrnng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = rgnSoctyHnfTrnngService.processRgnSoctyHnfTrnng(request, dataRequest);
		
		dataRequest.setMetadata(true, retMap);
		
		return new JSONDataView();
	}	

}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.eduprecon.web;

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

import isry.subms.preconmng.eduprecon.service.EduPreconService;

/**
 * @파일명        : eduPreconController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2023. 7. 7. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2023. 7. 7.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/subms/preconmng/eduprecon")
public class EduPreconController {
	
	@Resource(name = "eduPreconService")
	private EduPreconService eduPreconService;
	
	/**
	 * 
	 * @Method명   : selectEduPreconList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 7. 7. 
	 * @Method설명 : 교육과정 현황 조회
	 */
	@RequestMapping(value = "/selectEduPreconList.do")
	public View selectEduPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, String>> list = eduPreconService.selectEduPreconList(dataRequest, request);
		
		dataRequest.setResponse("dsList", list);
		
		return new JSONDataView();
	}
}

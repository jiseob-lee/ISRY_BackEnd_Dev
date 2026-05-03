/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.stats.linkCaseMng.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.cysns.stats.linkCaseMng.service.LinkCaseMngService;

/**
 * @파일명        : LinkCaseMngController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2023. 5. 12. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2023. 5. 12.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/cysns/stats/linkCaseMng")
public class LinkCaseMngController {

	@Resource(name = "linkCaseMngService")
	private LinkCaseMngService linkCaseMngService;
	
	/**
	 * 
	 * @Method명   : selectLinkCaseMngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 5. 15. 
	 * @Method설명 : 연계 및 사례관리 통계 조회
	 */
	@RequestMapping("/selectLinkCaseMngList.do")
	public View selectLinkCaseMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
	throws Exception {
		
		dataRequest.setResponse("dsList", linkCaseMngService.selectLinkCaseMngList(request, dataRequest));
		
		return new JSONDataView();
	}
	
}

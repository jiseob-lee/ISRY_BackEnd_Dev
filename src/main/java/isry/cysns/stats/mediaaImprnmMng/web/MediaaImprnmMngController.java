/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.stats.mediaaImprnmMng.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.cysns.stats.mediaaImprnmMng.service.MediaaImprnmMngService;

/**
 * @파일명        : MediaaImprnmMngController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2023. 5. 17. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2023. 5. 17.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/cysns/stats/mediaaImprnmMng")
public class MediaaImprnmMngController {
	
	@Resource(name = "mediaaImprnmMngService")
	private MediaaImprnmMngService mediaaImprnmMngService;
	
	@RequestMapping("/selectMediaaImprnmMngList.do")
	public View selectMediaaImprnmMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
		throws Exception {
		
		dataRequest.setResponse("dsList", mediaaImprnmMngService.selectMediaaImprnmMngList(request, dataRequest));
		
		return new JSONDataView();
	}
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.offwork.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.couns.offwork.service.OffworkService;

/**
 * @파일명        : OffworkController.java
 * @프로그램 설명 : 퇴근처리
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 10. 05. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 10. 05. 
 * @수정내용      : 퇴근처리
 * -                
 * -                
 */

@Controller
@RequestMapping("/isry/couns/offwork")
public class OffworkController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
    
	@Resource(name = "offworkService")
	private OffworkService offworkService;

	/**
	 * 퇴근처리 기본정보 조회
	 * @Method명   : selectLvffcPrcsBassInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 05. 
	 * @Method설명 :
	 */
	@RequestMapping(value = "/subLvffcPrcsBassInfo.do")
	public View selectLvffcPrcsBassInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 퇴근처리 기본정보 조회
		dataRequest.setResponse("dmDetail" , offworkService.selectLvffcPrcsBassInfo(request, dataRequest));
	
		return new JSONDataView();
	}
	
	/**
	 * 퇴근처리 저장
	 * @Method명   : lvffcPrcsSave
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 05. 
	 * @Method설명 :
	 */
	@RequestMapping(value="/subLvffcPrcsSave.do")
	@ResponseBody
	public View lvffcPrcsSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
				
		Map<String, String> retMap  = offworkService.lvffcPrcsSave(request, dataRequest);		
		log.debug("lvffcPrcsSave retMap ==>> " + retMap);
		
		return new JSONDataView();
	}
	
}

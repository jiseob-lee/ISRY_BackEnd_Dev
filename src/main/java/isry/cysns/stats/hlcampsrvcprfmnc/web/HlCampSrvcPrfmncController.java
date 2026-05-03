/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.stats.hlcampsrvcprfmnc.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.cysns.stats.hlcampsrvcprfmnc.service.HlCampSrvcPrfmncService;

/**
 * @파일명 : HlCampSrvcPrfmncController.java
 * @프로그램 설명 : 치유캠프, 가족치유캠프 서비스실적 컨트롤러 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 5. 16.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 5. 16.
 * @수정내용 : - -
 */
@Controller
@RequestMapping("/isry/cysns/stats/hlcampsrvcprfmnc")
public class HlCampSrvcPrfmncController {

	@Resource(name = "hlCampSrvcPrfmncService")
	private HlCampSrvcPrfmncService hlCampSrvcPrfmncService;

	/**
	 * @Method명 : selectHlCampSrvcPrfmncList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 5. 16.
	 * @Method설명 : 치유캠프, 가족치유캠프 서비스실적통계조회
	 */
	@RequestMapping("/selectHlCampSrvcPrfmncList.do")
	public View selectHlCampSrvcPrfmncList(HttpServletRequest request, HttpServletResponse response,
			DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsList", hlCampSrvcPrfmncService.selectHlCampSrvcPrfmncList(request, dataRequest));
		return new JSONDataView();
	}
}

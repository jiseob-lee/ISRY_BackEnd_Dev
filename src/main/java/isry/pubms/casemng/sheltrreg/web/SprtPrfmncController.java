/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.casemng.sheltrreg.web;

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
import com.cleopatra.spring.JSONDataView;

import isry.pubms.casemng.sheltrreg.service.SprtPrfmncService;

/**
 * @파일명        : SprtPrfmncController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 6. 3. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 6. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/pubms/casemng/sprtprfmnc")
public class SprtPrfmncController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "sprtPrfmncService")
	private SprtPrfmncService sprtPrfmncService;

	@RequestMapping(value = "/selectsheltrsprtList.do")
	public View selectMainList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례 목록 조회
//		List<Map<String, String>> list = sprtPrfmncService.selectSprtPfrmncList(request, dataRequest);
		Map<String, Object> result = sprtPrfmncService.selectSprtPfrmncPagingList(request, dataRequest);

		dataRequest.setResponse("dsCaseInqList", result.get("list"));
		dataRequest.setResponse("dmPageInfo", result.get("dmPageInfo"));

		return new JSONDataView();
	}


	@RequestMapping("/saveData.do")
	public View saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		log.info("saveData_ controller");

		sprtPrfmncService.saveData(request, dataRequest);

		return new JSONDataView();
	}
	
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubmt.instinfo.slfrlinfo.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.pubmt.instinfo.slfrlinfo.service.SlfrlInfoService;

/**
 * @파일명        : SlfrlInfoController.java
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
@RequestMapping("/isry/pubmt/instinfo/slfrlinfo")
public class SlfrlInfoController {
	
	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "slfrlInfoService")
	private SlfrlInfoService slfrlInfoService;


	@RequestMapping("/selectReqList.do")
	public View selectReqList(DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", slfrlInfoService.selectReqList(dataRequest));
		
		return new JSONDataView();
	}

	@RequestMapping("/selectSlfrlCntList.do")
	public View selectSlfrlCntList(DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsCtpvList", slfrlInfoService.selectSlfrlCntList(dataRequest));
		
		return new JSONDataView();
	}
}






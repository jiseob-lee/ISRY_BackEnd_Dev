/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubmsr.casemng.recvrytrmn.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.pubms.casemng.sheltrtrmn.service.SheltrTrmnService;
import isry.pubmsr.casemng.recvrytrmn.service.RecvryTrmnService;

/**
 * @파일명        : RecvryTrmnController.java
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
@RequestMapping("/isry/pubmsr/casemng/recvrytrmn")
public class RecvryTrmnController {
	
	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "sheltrTrmnService")
	private SheltrTrmnService sheltrTrmnService;

	@Resource(name = "recvryTrmnService")
	private RecvryTrmnService recvryTrmnService;

	
	@RequestMapping("/selectReqById.do")
	public View selectReqById(DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", sheltrTrmnService.selectReqById(dataRequest));

		dataRequest.setResponse("dsEntrncXtnd", recvryTrmnService.selectReqById(dataRequest));
		
		return new JSONDataView();
	}

	@RequestMapping("/saveData.do")
	public View saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		recvryTrmnService.saveData(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping("/deleteData.do")
	public View deleteData(DataRequest dataRequest) throws Exception {
		
		recvryTrmnService.deleteData(dataRequest);
		
		return new JSONDataView();
	}

	@RequestMapping("/saveEntrncXtndData.do")
	public View saveEntrncXtndData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		recvryTrmnService.saveEntrncXtndData(request, dataRequest);
		
		return new JSONDataView();
	}
}

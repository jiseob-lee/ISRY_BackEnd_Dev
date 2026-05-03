/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.casemng.sheltrtrmn.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.pubms.casemng.sheltrtrmn.service.SheltrTrmnService;
import isry.pubmsr.casemng.recvrytrmn.service.RecvryTrmnService;
import isry.pubmt.casemng.slfrlreg.service.SlfrlRegService;

/**
 * @파일명        : EmrgActnController.java
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
@RequestMapping("/isry/pubms/casemng/sheltrtrmn")
public class SheltrTrmnController {
	
	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "sheltrTrmnService")
	private SheltrTrmnService sheltrTrmnService;

	@Resource(name = "slfrlRegService")
	private SlfrlRegService slfrlRegService;
	
	@RequestMapping("/selectReqById.do")
	public View selectReqById(DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", sheltrTrmnService.selectReqById(dataRequest));
		dataRequest.setResponse("dsEntrncXtnd", sheltrTrmnService.selectEntrncXtndById(dataRequest));

		dataRequest.setResponse("dsSlfrlPrpareList", slfrlRegService.selectSlfrlPrpareById(dataRequest, "04"));
	
		return new JSONDataView();
	}

	@RequestMapping("/saveData.do")
	public View saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		sheltrTrmnService.saveData(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping("/deleteData.do")
	public View deleteData(DataRequest dataRequest) throws Exception {
		
		sheltrTrmnService.deleteData(dataRequest);
		
		return new JSONDataView();
	}

	@RequestMapping("/selectEntrncXtndById.do")
	public View selectEntrncXtndById(DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsEntrncXtnd", sheltrTrmnService.selectEntrncXtndById(dataRequest));
		
		return new JSONDataView();
	}

	@RequestMapping("/saveEntrncXtndData.do")
	public View saveEntrncXtndData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		sheltrTrmnService.saveEntrncXtndData(request, dataRequest);

		return new JSONDataView();
	}

}

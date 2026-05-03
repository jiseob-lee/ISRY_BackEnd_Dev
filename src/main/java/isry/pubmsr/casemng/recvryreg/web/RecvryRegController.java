/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubmsr.casemng.recvryreg.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.cysns.casemng.casereg.service.CysnsRegService;
import isry.pubmsr.casemng.recvryreg.service.RecvryRegService;
import isry.pubmt.casemng.slfrlreg.service.SlfrlRegService;

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
@RequestMapping("/isry/pubmsr/casemng/recvryreg")
public class RecvryRegController {
	
	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "recvryRegService")
	private RecvryRegService recvryRegService;
	
	@Resource(name = "cysnsRegService")
	private CysnsRegService cysnsRegService;

	@Resource(name = "slfrlRegService")
	private	SlfrlRegService slfrlRegService;


	@RequestMapping("/selectReqById.do")
	public View selectReqById(DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", recvryRegService.selectReqById(dataRequest));

		dataRequest.setResponse("dsCrisisScoreList", cysnsRegService.selectReqById(dataRequest));
		dataRequest.setResponse("dsCrisisResultList", cysnsRegService.selectReqById2(dataRequest));

		dataRequest.setResponse("dsTrlEmtList", slfrlRegService.selectTrlEmtById(dataRequest));
		
		return new JSONDataView();
	}

	@RequestMapping("/saveData.do")
	public View saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		Map<String, String> dmParam = recvryRegService.saveData(request, dataRequest);

		dataRequest.setResponse("dmParam", dmParam);

		return new JSONDataView();
	}

	@RequestMapping("/deleteData.do")
	public View deleteData(DataRequest dataRequest) throws Exception {
		
		recvryRegService.deleteData(dataRequest);
		
		return new JSONDataView();
	}
}

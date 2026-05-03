/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.regns.emrgcntrms.emrgrpt.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.util.UserException;
import isry.regns.emrgcntrms.emrgrpt.service.EmrgRptService;

/**
 * @파일명        : LinkInstController.java
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
@RequestMapping("/isry/regns/emrgcntrms/emrgrpt")
public class EmrgRptController {
	
	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "emrgRptService")
	private EmrgRptService emrgRptService;

	@RequestMapping("/selectKeyValue.do")
	public View selectKeyValue(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dmKey", emrgRptService.selectKeyValue(request, dataRequest));
		
		return new JSONDataView();
	}

	
	@RequestMapping("/selectReqList.do")
	public View selectReqList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", emrgRptService.selectReqList(request, dataRequest));
		
		return new JSONDataView();
	}

	@RequestMapping("/selectReqById.do")
	public View selectReqById(DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsList", emrgRptService.selectReqById(dataRequest));
		dataRequest.setResponse("dsDtlList", emrgRptService.selectDtlById(dataRequest));
		
		return new JSONDataView();
	}

	@RequestMapping("/selectDtlById.do")
	public View selectDtlById(DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsDtlList", emrgRptService.selectDtlById(dataRequest));
		
		return new JSONDataView();
	}

	
	@RequestMapping("/saveData.do")
	public View saveData(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
//		throw new UserException("errors.prefix");
		
		emrgRptService.saveData(request, dataRequest);

	    return new JSONDataView();
	}

	@RequestMapping("/deleteData.do")
	public View deleteData(DataRequest dataRequest) throws Exception {
		
		emrgRptService.deleteData(dataRequest);
		
		return new JSONDataView();
	}
}

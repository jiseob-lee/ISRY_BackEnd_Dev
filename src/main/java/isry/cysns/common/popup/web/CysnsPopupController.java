/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.common.popup.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.cysns.common.popup.service.CysnsPopupService;
import lombok.extern.slf4j.Slf4j;

/**
 * @파일명        : PopupController.java
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
@RequestMapping("/isry/cysns/common/popup")
public class CysnsPopupController {

	
	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "cysnsPopupService")
	private CysnsPopupService cysnsPopupService;
	
	@RequestMapping("/selectTlphonDscsnList.do")
	public View selectTlphonDscsnList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		//20230629 이승재 - 권한 추가 위한 수정(request 추가)
		dataRequest.setResponse("dsList", cysnsPopupService.selectTlphonDscsnList(request, dataRequest));
		
		return new JSONDataView();
	}

}

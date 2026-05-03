/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.test.web;

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

import isry.itgcms.sysmgmt.test.service.TestService;

/**
 * @파일명        : TestController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 6. 20. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 6. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/itgcms/syscmmn/test")
public class Test1Controller {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "testService")
	private TestService testService;
	
	@RequestMapping(value = "/enc.do")
	public View onLoadSurvsht(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		log.debug("test");
		
		testService.encSAA000();
		testService.encSCA100();
		testService.encSCA300();
		
		return new JSONDataView();
	}
	
}

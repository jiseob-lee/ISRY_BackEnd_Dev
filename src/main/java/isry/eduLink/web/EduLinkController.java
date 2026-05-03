/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.eduLink.web;


import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseController;
import isry.eduLink.service.EduLinkService;

/**
 * @파일명        : GitpleEventController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 5. 26. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 5. 31.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/eduLink")
public class EduLinkController extends IsryBaseController {

	@Resource(name = "eduLinkService")
	private EduLinkService eduLinkService;
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);	
	
	@RequestMapping(value="/selectEduLink.do")
	public View gitpleEvent() throws Exception {
		eduLinkService.eduBatch();
		return new JSONDataView();
	}
	
}

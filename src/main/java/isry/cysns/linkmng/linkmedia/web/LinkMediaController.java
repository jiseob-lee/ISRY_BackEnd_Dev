/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.linkmng.linkmedia.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.cysns.linkmng.linkmedia.service.LinkMediaService;

/**
 * @파일명        : LinkMediaController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 8. 12. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 8. 12.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/cysns/linkmng/linkmedia")
public class LinkMediaController {
	
	//private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "linkMediaService")
	private LinkMediaService linkMediaService;
	
	@RequestMapping("/saveWlfarCnterData.do")
	public View saveWlfarCnterData(HttpServletRequest request) throws Exception {
		
		linkMediaService.saveWlfarCnterData();
		
		return new JSONDataView();
	}

	@RequestMapping("/saveSchlScoreData.do")
	public View saveSchlScoreData(HttpServletRequest request) throws Exception {
		
		linkMediaService.saveSchlScoreData();
		
		return new JSONDataView();
	}

	@RequestMapping("/saveInstScoreData.do")
	public View saveInstScoreData(HttpServletRequest request) throws Exception {
		
		linkMediaService.saveInstScoreData();
		
		return new JSONDataView();
	}

	@RequestMapping("/saveSchlDgnssData.do")
	public View saveSchlDgnssData(HttpServletRequest request) throws Exception {
		
		linkMediaService.saveSchlDgnssData();
		
		return new JSONDataView();
	}

	@RequestMapping("/saveInstDgnssData.do")
	public View saveinstDgnssData(HttpServletRequest request) throws Exception {
		
		linkMediaService.saveInstDgnssData();
		
		return new JSONDataView();
	}
	
}

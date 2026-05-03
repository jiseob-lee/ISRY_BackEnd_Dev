/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcm.bizcmmns.cmmns.service.RqstSrvcInqService;

/**
 * @파일명        : RqstSrvcInqController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kwon.Min.Seo
 * @작성일        : 2022. 10. 25. 
 * @수정자        : Kwon.Min.Seo
 * @수정일        : 2022. 10. 25.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/bizcmmns/cmmns")
public class RqstSrvcInqController {
	
	@Resource(name = "rqstSrvcInqService")
	private RqstSrvcInqService rqstSrvcInqService;
	
	/**
	 * @Method명		: selectRqstSrvcList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자    	: Kwon.Min.Seo
	 * @작성일		: 2022. 10. 25. 
	 * @Method설명	: 의뢰 서비스조회(복지부 연계)
	 */
	
	@RequestMapping(value = "/selectRqstSrvcList.do")
	public View selectRqstSrvcList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> resultMap = rqstSrvcInqService.selectRqstSrvcList(request, dataRequest);

		dataRequest.setResponse("dsCnrsResrceInqList", resultMap.get("cnrsResrceInqList"));	//공유자원 복지부 연계 정보

		return new JSONDataView();
	}

}

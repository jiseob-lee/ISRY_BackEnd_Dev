/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.userauth.service.OffcsSgnngMngService;

/**
 * @파일명 : OffcsSgnngMngController.java
 * @프로그램 설명 : 직인서명관리 컨트롤러 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 8. 11.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 8. 11.
 * @수정내용 : - -
 */
@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class OffcsSgnngMngController {

	@Resource(name = "offcsSgnngMngService")
	OffcsSgnngMngService offcsSgnngMngService;

	/**
	 * @Method명 : selectOffcsSgnngList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 8. 12.
	 * @Method설명 : 직인서명 목록 조회
	 */
	@RequestMapping(value = "/selectOffcsSgnngList.do")
	public View selectOffcsSgnngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		List<Map<String, Object>> dsList2 = offcsSgnngMngService.selectOffcsSgnngList(dataRequest);

		dataRequest.setResponse("dsList2", dsList2);

		return new JSONDataView();
	}

	/**
	 * @Method명 : saveOffcsSgnng
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 8. 12.
	 * @Method설명 : 직인서명 저장/수정/삭제
	 */
	@RequestMapping(value = "/saveOffcsSgnng.do")
	public View saveOffcsSgnng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {

		dataRequest.setMetadata(true, offcsSgnngMngService.saveOffcsSgnng(dataRequest));

		return new JSONDataView();
	}
}

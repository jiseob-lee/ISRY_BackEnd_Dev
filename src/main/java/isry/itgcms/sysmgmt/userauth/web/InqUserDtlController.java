/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.itgcms.sysmgmt.userauth.service.InqAuthGrpListService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import isry.itgcms.sysmgmt.userauth.service.InqUserAuthListService;
import isry.itgcms.sysmgmt.userauth.service.InqUserDtlService;

/**
 * @파일명        : InqUserDtlController.java
 * @프로그램 설명 : 사용자 상세 정보 조회
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 10. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 10.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
//@Api(value = "InqUserDtl web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class InqUserDtlController {

	@Resource(name = "inqUserDtlService")
	private InqUserDtlService inqUserDtlService;

	@Resource(name = "inqAuthGrpListService")
	private InqAuthGrpListService inqAuthGrpListService;
	
	//@ApiOperation(value = "/selectUserDetail.do", notes = "사용자 상세 정보 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectUserDetail.do")
	public View selectUserDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dmMemberDetail", inqUserDtlService.selectUserDetail(dataRequest));

		// 개인의 기관 권한 목록을 구한다.
		dataRequest.setResponse("dsInstituteAuthList", inqUserDtlService.selectUserInstituteAuthList(dataRequest));
		
		ParameterGroup param = dataRequest.getParameterGroup("dmMemberId");
		
		return new JSONDataView();
	}
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.web;

import java.util.ArrayList;
import java.util.HashMap;
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

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import isry.itgcms.sysmgmt.userauth.service.InqOrgDeptListService;

/**
 * @파일명        : InqOrgDeptListController.java
 * @프로그램 설명 : 기관 부서 조회
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 2. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 2.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
//@Api(value = "InqOrgDeptList web Controller")
@RequestMapping(value = "/isry/itgcm/sysmgmt/userauth")
public class InqOrgDeptListController {

	@Resource(name = "inqOrgDeptListService")
	private InqOrgDeptListService inqOrgDeptListService;

	//@ApiOperation(value = "/selectOrgDept.do", notes = "기관 정보 조회 [공통] 이지섭")
	@RequestMapping(value = "/selectOrgDept.do")
	public View selectOrgDept(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsOrgDept", inqOrgDeptListService.selectOrgDept(dataRequest));

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectSubOrgInfo.do")
	public View selectSubOrgInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> subOrgInfo = inqOrgDeptListService.selectSubOrgInfo(dataRequest);

		List<Map<String, Object>> subOrgInfo2 = new ArrayList<>();
		List<String> engCtpvNmList = new ArrayList<>();
		for (int i=0; i < subOrgInfo.size(); i++) {
			Map<String, Object> subOrg = subOrgInfo.get(i);
			String engCtpvNm = (String)subOrg.get("ENG_CTPV_NM");
			if (!engCtpvNmList.contains(engCtpvNm)) {
				Map<String, Object> subOrg2 = new HashMap<>();
				subOrg2.put("ORG_CODE", subOrg.get("ORG_CODE"));
				subOrg2.put("ORG_NAME", subOrg.get("ORG_NAME"));
				subOrg2.put("ENG_CTPV_NM", subOrg.get("ENG_CTPV_NM"));
				subOrg2.put("CTPV_NM", subOrg.get("CTPV_NM"));
				subOrgInfo2.add(subOrg2);
				engCtpvNmList.add(engCtpvNm);
			}
		}

		List<Map<String, Object>> subOrgInfo3 = new ArrayList<>();
		List<String> ctpvSggCdList = new ArrayList<>();
		for (int i=0; i < subOrgInfo.size(); i++) {
			Map<String, Object> subOrg = subOrgInfo.get(i);
			String ctpvSggCd = (String)subOrg.get("CTPV_SGG_CD");
			if (!ctpvSggCdList.contains(ctpvSggCd)) {
				Map<String, Object> subOrg3 = new HashMap<>();
				subOrg3.put("ORG_CODE", subOrg.get("ORG_CODE"));
				subOrg3.put("ORG_NAME", subOrg.get("ORG_NAME"));
				subOrg3.put("CTPV_SGG_CD", subOrg.get("CTPV_SGG_CD"));
				subOrg3.put("SGG_NM", subOrg.get("SGG_NM"));
				subOrg3.put("ENG_CTPV_NM", subOrg.get("ENG_CTPV_NM"));
				subOrgInfo3.add(subOrg3);
				ctpvSggCdList.add(ctpvSggCd);
			}
		}
		
		dataRequest.setResponse("dsSubOrg1", subOrgInfo2);
		dataRequest.setResponse("dsSubOrg2", subOrgInfo3);
		dataRequest.setResponse("dsSubOrg3", subOrgInfo);
		
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectMaxDeptCd.do")
	public View selectMaxDeptCd(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dmMaxDeptCd", inqOrgDeptListService.selectMaxDeptCd());

		return new JSONDataView();
	}

	
}

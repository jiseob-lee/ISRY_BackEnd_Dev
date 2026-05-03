/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.cas.gr.excn.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.drmgs.cas.gr.excn.service.DrmgsCasGrExcnService;
import isry.itgcm.casemng.caseunity.service.SrvcGrPvsnService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : DrmgsCasGrExcnController.java
 * @프로그램 설명 : 사례관리 내 서비스지원 > 서비스집단실행
 * - 
 * - 
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 7. 13. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 7. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/drmgs/cas/gr/excn")
public class DrmgsCasGrExcnController extends IsryBaseController {

	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	@Resource(name = "drmgsCasGrExcnService")
	private DrmgsCasGrExcnService drmgsCasGrExcnService;
		
	@Resource(name = "srvcGrPvsnService")
	private SrvcGrPvsnService srvcGrPvsnService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value="/onLoad.do")
	public View onLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		dataRequest.setResponse("dsServiceType", mgmtCmmnCodeService.selectCommonCodeUnit("SRVC_TYPE_SE_CD", userVo.getUntTaskwk()));
		return new JSONDataView();
	}
	
	@RequestMapping(value="/srvcSelect.do")
	public View srvcSelect(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = srvcGrPvsnService.selectSrvcGrPvsnList(request, dataRequest);
		
		dataRequest.setResponse("dsList", retMap.get("dsList"));
		dataRequest.setResponse("dmPage", retMap.get("dmPage"));
		return new JSONDataView();
	}
	
	@RequestMapping(value="/srvcSelectDet.do")
	public View srvcSelectDet(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsDetail", srvcGrPvsnService.selectCaseGrExcnDetail(dataRequest));
		return new JSONDataView();
	}
	
	@RequestMapping(value="/srvcSelectCase.do")
	public View srvcSelectCase(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("caseList", srvcGrPvsnService.selectExcnCaseTrprList(request, dataRequest));
		return new JSONDataView();
	}
	
	@RequestMapping(value="/srvcSelectChkList.do")
	public View srvcSelectChkList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsServiceChek", drmgsCasGrExcnService.selectChkList(dataRequest));
		return new JSONDataView();
	}
	
	@RequestMapping(value="/srvcSave.do")
	public View srvcSave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		drmgsCasGrExcnService.saveChkList(dataRequest);
		return new JSONDataView();
	}
	
	@RequestMapping(value="/srvcDaySave.do")
	public View srvcDaySave(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		drmgsCasGrExcnService.saveDayList(dataRequest);
		return new JSONDataView();
	}
	
	@RequestMapping(value="/srvcDayList.do")
	public View srvcDayList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsDays", drmgsCasGrExcnService.selectDayList(dataRequest));
		return new JSONDataView();
	}
}

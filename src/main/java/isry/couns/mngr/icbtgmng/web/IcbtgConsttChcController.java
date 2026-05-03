/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.icbtgmng.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import io.swagger.annotations.Api;
import isry.base.IsryBaseController;
import isry.couns.cmmn.service.CounsService;
import isry.couns.mngr.icbtgmng.service.IcbtgConsttChcService;
import isry.itgcms.util.ScpDb;

@Controller
@Api(value = "IcbtgConsttChcController Controller")
@RequestMapping("/icbtgmng")
public class IcbtgConsttChcController extends IsryBaseController {

	@Autowired
    private IcbtgConsttChcService icbtgConsttChcService;
	
	@Resource(name = "counsService")
    private CounsService counsService;
	
	@RequestMapping("/sampleSearchOptionIcbtg.do")
	public View sampleSearchOption(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
	        throws Exception {
		
	   	//Map<String, Object> mapParam = new HashMap<String, Object>();
	   	
	   	//ParameterGroup searchParam = dataRequest.getParameterGroup("dsSearchCombo");
	   	
	   	//조회 조건 검색 (부서)
	   	List<Map<String, Object>> searchComboList = counsService.selectOrgDeptCombo(request);

	   	dataRequest.setResponse("dsSearchCombo", searchComboList);
	   	return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectIcbtgConsttChcList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : ????
	 * @작성일     : ????.??.?? 
	 * @Method설명 :
	 * @사용안함 : 2023.07.26
	 */
	@RequestMapping("/selectIcbtgConsttChcList.do")
	public View selectIcbtgConsttChcList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
	        throws Exception {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		String deptCd = searchParam.getValue("DEPT_CD");
		String inqYmd = searchParam.getValue("INQ_YMD");
		
		mapParam.put("deptCd", deptCd);
		mapParam.put("inqYmd", inqYmd);
		
		List<Map<String, Object>> dsList = icbtgConsttChcService.selectIcbtgConsttChcList(mapParam); /* *소속기관 콤보박스 */
		
		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	
	@RequestMapping("/insertIcbtgConsttChc.do")
	public View insertIcbtgConsttChc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
	        throws Exception {

		Map<String, Object> mapParam = new HashMap<String, Object>();
		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");
		mapParam.put("INDEX_SN", dsList.getValue("INDEX_SN"));
		mapParam.put("CONSTT_ID", dsList.getValue("CONSTT_ID"));
		mapParam.put("NTABRD_EVL_YN", dsList.getValue("NTABRD_EVL_YN"));
		mapParam.put("CHTT_EVL_YN", dsList.getValue("CHTT_EVL_YN"));
		mapParam.put("INCUBA_WORK_YMD", dsList.getValue("INCUBA_WORK_YMD"));
		mapParam.put("START_DT", dsList.getValue("START_DT"));
		mapParam.put("END_DT", dsList.getValue("END_DT"));
		
		int cnt = icbtgConsttChcService.insertIcbtgConsttChc(mapParam);
		
		return new JSONDataView();
	}
   
    
}
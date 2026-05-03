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
import isry.couns.mngr.icbtgmng.service.IcbtgAltmntService;
import isry.itgcms.util.ScpDb;

@Controller
@Api(value = "IcbtgAltmntController Controller")
@RequestMapping("/icbtgmng")
public class IcbtgAltmntController extends IsryBaseController {

	@Autowired
    private IcbtgAltmntService icbtgAltmntService;
	
	@Resource(name = "counsService")
    private CounsService counsService;
	
	@RequestMapping("/sampleSearchOptionAltmnt.do")
	public View sampleSearchOption(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
	        throws Exception {
	   	
	   	//조회 조건 검색 (부서)
	   	dataRequest.setResponse("dsSearchCombo", counsService.selectOrgDeptCombo(request));
	   	
	   	return new JSONDataView();
	}
	
	@RequestMapping("/selectIcbtgAltmntList.do")
	public View selectIcbtgAltmntList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
	        throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		List<Map<String, Object>> dsList = icbtgAltmntService.selectIcbtgAltmntList(paramMap);
		
		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	
	/**
	 * @Method     : insertIcbtgAltmnt
	 * @Method설명 : 인큐베이팅 상담자배정표 등록
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 09. 01.
	 * @상세       : IcbtgConsttChcController(작성자 : 유영태) → IcbtgAltmntController로 복사
 	 */
	@RequestMapping(value = "/insertIcbtgAltmnt.do")
	public View insertIcbtgAltmnt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		int retVal = icbtgAltmntService.insertIcbtgAltmnt(request, dataRequest);
		//log.debug("controller retVal : " + retVal);
		Map<String, Object> mapParam = new HashMap<String, Object>();
		mapParam.put("retVal", retVal);
    	dataRequest.setMetadata(true, mapParam);
		return new JSONDataView();
	}
	
	/**
	 * @Method     : updateIcbtgAltmnt
	 * @Method설명 : 인큐베이팅 상담자배정표 상세 수정
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 09. 01. 
 	 */
	@RequestMapping(value = "/updateIcbtgAltmnt.do")
	public View updateIcbtgAltmnt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 인큐베이팅 상담자배정표 상세 삭제
		icbtgAltmntService.updateIcbtgAltmnt(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method     : deleteIcbtgAltmnt
	 * @Method설명 : 인큐베이팅 상담자배정표 상세 삭제
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 09. 01. 
 	 */
	@RequestMapping(value = "/deleteIcbtgAltmnt.do")
	public View deleteIcbtgAltmnt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		// 인큐베이팅 상담자배정표 상세 삭제
		icbtgAltmntService.deleteIcbtgAltmnt(request, dataRequest);
		
		return new JSONDataView();
	}
	
//	@RequestMapping("/insertIcbtgConsttChc.do")
//	public View insertIcbtgConsttChc(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
//	        throws Exception {
//		return new JSONDataView();
//	}
   
    
}
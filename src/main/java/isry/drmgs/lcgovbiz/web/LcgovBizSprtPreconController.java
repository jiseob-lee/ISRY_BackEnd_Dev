/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.lcgovbiz.web;

import java.util.ArrayList;
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

import isry.base.IsryBaseController;
import isry.drmgs.lcgovbiz.service.LcgovBizSprtPreconService;

/**
 * @파일명        : LcgovBizSprtPreconController.java
 * @프로그램 설명 : 지자체 사업 지원 현황
 * - 
 * - 
 * @작성자        : Jeong.Won.Je
 * @작성일        : 2022. 7. 20. 
 * @수정자        : Jeong.Won.Je
 * @수정일        : 2022. 7. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/drmgs/lcgovbiz")
public class LcgovBizSprtPreconController extends IsryBaseController{
	
	// 공통코드 사용하는 데이터 조회하기 위한 서비스
	//@Resource(name = "mgmtCmmnCodeService")
	//private MgmtCmmnCodeService mgmtCmmnCodeService;
	
	// 비즈니스 로직 처리 Service
	@Resource(name = "lcgovBizSprtPreconService")
	private LcgovBizSprtPreconService LcgovBizSprtPreconService;

	/**
	 * @Method     : selectLcgovBizSprtPreconList
	 * @Method설명 : 지자체 사업 지원 현황 목록 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 07. 20. 
 	 */
	@RequestMapping(value = "/selectLcgovBizSprtPreconList.do")
	public View selectLcgovBizSprtPreconList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest)
			throws Exception {
		
		// 지자체 사업 지원 현황 목록 조회
		dataRequest.setResponse("dsList", LcgovBizSprtPreconService.selectLcgovBizSprtPreconList(request, dataRequest));
		
		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectLcgovBizSprtPreconDetail
	 * @Method설명 : 지자체 사업 지원 현황 상세 조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 8. 9. 
 	 */
	@RequestMapping(value = "/selectLcgovBizSprtPreconDetail.do")
	public View selectLcgovBizSprtPreconDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) 
			throws Exception {
		
		List<Map<String, Object>> dsList = new ArrayList<Map<String,Object>>();
		dsList.add(LcgovBizSprtPreconService.selectClienaInfo(dataRequest));
		
		// 내담자 기본정보 및 경기도 사업 등록 유무 조회
		dataRequest.setResponse("dsClienaInfo", dsList);
		
		// 개별 서비스 목록 조회
		dataRequest.setResponse("dsIndivSprt", LcgovBizSprtPreconService.selectIndivSprvtList(dataRequest));
		// 집단 서비스 목록 조회
		dataRequest.setResponse("dsGrDscsn", LcgovBizSprtPreconService.selectGrDscsnList(dataRequest));
		
		return new JSONDataView();
	}
}

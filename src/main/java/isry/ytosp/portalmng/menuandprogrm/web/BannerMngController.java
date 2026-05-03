/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.ytosp.portalmng.menuandprogrm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseController;
import isry.ytosp.portalmng.menuandprogrm.mapper.BannerMngMapper;
import isry.ytosp.portalmng.menuandprogrm.service.BannerMngService;

/**
 * @파일명        : BannerMngController.java
 * @프로그램 설명 	: 배너관리 팝업
 * - 
 * - 
 * @작성자        : Kim.Hai.Ryong
 * @작성일        : 2023. 8. 24. 
 * @수정자        : Kim.Hai.Ryong
 * @수정일        : 2023. 8. 24.
 * @수정내용      : 
 * -                
 * -                
 */

@Controller
@RequestMapping("/isry/ytosp/portalmng/menuandprogrm")
public class BannerMngController extends IsryBaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
			
	@Resource(name = "bannerMngService")
	private BannerMngService bannerMngService;
	
	/**
	 * @Method     	: insertBannerMng
	 * @Method설명 	: 배너 등록
	 * @param      	: request
	 * @param      	: response
	 * @return     	: dataRequest 
	 * @exception  	: Exception
	 * @작성자     	: Kim.Hai.Ryong
	 * @작성일     	: 2023. 8. 24.
	 * @상세       	: 
 	 */
	
	@RequestMapping(value = "/insertBannerMng.do")
	public View insertBannerMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		bannerMngService.insertBannerMng(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method     	: selectBannerMngList
	 * @Method설명 	: 배너 목록조회
	 * @param      	: request
	 * @param      	: response
	 * @return     	: dataRequest 
	 * @exception  	: Exception
	 * @작성자     	: Kim.Hai.Ryong
	 * @작성일     	: 2023. 8. 24.
	 * @상세       	: 
 	 */
	
	@RequestMapping(value = "/selectBannerMngList.do")
	public View selectBannerMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		ParameterGroup ParamGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = ParamGroup.getSingleValueMap();
		
		List<Map<String, Object>> dsList = bannerMngService.selectBannerMngList(paramMap);
		
		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	
	/**
	 * @Method     	: selectBannerMngDetail
	 * @Method설명 	: 배너 상세조회
	 * @param      	: request
	 * @param      	: response
	 * @return     	: dataRequest 
	 * @exception  	: Exception
	 * @작성자     	: Kim.Hai.Ryong
	 * @작성일     	: 2023. 9. 1.
	 * @상세       	: 
 	 */
	
	@RequestMapping(value = "/selectBannerMngDetail.do")
	public View selectBannerMngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		ParameterGroup ParamGroup = dataRequest.getParameterGroup("dmDtlParam");
		Map<String, String> paramMap = ParamGroup.getSingleValueMap();
		
		LOGGER.debug("@@@ ::: " + paramMap);
		
		List<Map<String, Object>> dsList = bannerMngService.selectBannerMngDetail(paramMap);
		
		dataRequest.setResponse("dsList", dsList);
		return new JSONDataView();
	}
	
	/**
	 * @Method     	: deleteBannerMng
	 * @Method설명 	: 배너 상태(삭제) 업데이트
	 * @param      	: request
	 * @param      	: response
	 * @return     	: dataRequest 
	 * @exception  	: Exception
	 * @작성자     	: Kim.Hai.Ryong
	 * @작성일     	: 2023. 9. 04.
	 * @상세       	: 
 	 */
	@RequestMapping(value = "/deleteBannerMng.do")
	public View deleteIcbtgAltmnt(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		bannerMngService.deleteBannerMng(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method     	: updateBannerMng
	 * @Method설명 	: 배너 수정
	 * @param      	: request
	 * @param      	: response
	 * @return     	: dataRequest 
	 * @exception  	: Exception
	 * @작성자     	: Kim.Hai.Ryong
	 * @작성일     	: 2023. 9. 5.
	 * @상세       	: 
 	 */
	@RequestMapping(value = "/updateBannerMng.do")
	public View updateBannerMng(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		bannerMngService.updateBannerMng(request, dataRequest);
		
		return new JSONDataView();
	}
	
	
	
}

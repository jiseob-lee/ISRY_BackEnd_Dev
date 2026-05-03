/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.itgcm.bizcmmns.cmmns.service.ComCodeService;
import isry.itgcm.bizcmmns.cmmns.service.ProgrmInqService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : ProgrmInqController.java
 * @프로그램 설명 : 자원프로그램목록을 조회하는 팝업
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 6. 15. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 6. 15.
 * @수정내용      : 
 * -                
 * -                
 */

@Controller
@RequestMapping(value = "/isry/itgcm/bizcmmns/cmmns")
public class ProgrmInqController extends IsryBaseServiceImpl  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "progrmInqService")
	private ProgrmInqService progrmInqService;
	
	@Resource(name = "comCodeService")
	private ComCodeService comCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectProgrmInqList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return view
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 6. 15. 
	 * @Method설명 : 프로그램 목록 조회
	 */
	
	@RequestMapping(value = "/selectProgrmInqList.do")
	public View selectSsgInqList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		List<Map<String, String>> dsProgrm = progrmInqService.selectProgrmInqList(dataRequest);
		dataRequest.setResponse("dsProgrm", dsProgrm);
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectProgrmInqOnLoad.do
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return view
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 6. 15. 
	 * @Method설명 : 공통코드조회
	 */
	
	@RequestMapping(value = "/selectProgrmInqOnLoad.do")
	public View selectTrprIdntfcOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String sRetDsSet = "";		// RETURN 데이터셋 
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		HttpSession session = request.getSession();
        UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		LOGGER.debug("selectProgrmInqOnLoad.paramGroup=[" + paramGroup + "]");

		List<Map<String, String>> paramList = paramGroup.getAllRowList();
		
		for (Map<String, String> rowMap : paramList) {
			
			sRetDsSet = String.valueOf(rowMap.get("DS_SET_NM"));
			rowMap.put("unitCode", userVo.getUntTaskwk());
			
			List<Map<String, Object>> list = comCodeService.selectCommonCodeUnit(rowMap);
			dataRequest.setResponse(sRetDsSet, list);
			
		}

		return new JSONDataView();
	}

}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.web;

import java.util.HashMap;
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
import isry.itgcm.bizcmmns.cmmns.service.ComCodeService;
import isry.itgcm.bizcmmns.cmmns.service.DidimTrprInfoService;
import isry.itgcm.bizcmmns.cmmns.service.TrprInfoService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
* @Class Name  : DidimTrprInfoController.java
* @Description : (디딤,드림)대상자정보조회 팝업 Controller.java
*
* @author  : Kwon.Min.Seo
* @since   : 2022. 09. 15.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 09. 15. Kwon.Min.Seo    최초작성
* </pre>
*/
@Controller
@RequestMapping(value = "/isry/itgcm/bizcmmns/cmmns")
public class DidimTrprInfoController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "didimTrprInfoService")
	private DidimTrprInfoService didimTrprInfoService;
	
	@Resource(name = "comCodeService")
	private ComCodeService comCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectDidimTrprInfoOnload
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 09. 15. 
	 * @Method설명 : (디딤,드림) 대상자정보 화면 목록 구성시 업무단위콤보박스 조회
	 */
	@RequestMapping(value = "/selectDidimTrprInfoOnload.do")
	public View selectDidimTrprInfoOnload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		

		String sRetDsSet = "";		// RETURN 데이터셋 
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		HttpSession session = request.getSession();
        UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		LOGGER.debug("selectTrprInfoOnload.paramGroup = [" + paramGroup + "]");
		
		List<Map<String, String>> paramList = paramGroup.getAllRowList();
		
		for (Map<String, String> rowMap : paramList) {
			
			sRetDsSet = String.valueOf(rowMap.get("DS_SET_NM"));
			rowMap.put("unitCode", userVo.getUntTaskwk());
			
			// 공통코드 조회(업무단위구분코드)
			List<Map<String, Object>> list = comCodeService.selectCommonCodeUnit(rowMap);
			dataRequest.setResponse(sRetDsSet, list);
			
		}
		
		return new JSONDataView();
	}

	/**
	 * @Method명   : selectDidimTrprInfoList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 09. 15.
	 * @Method설명 : (디딤,드림)대상자정보 화면 목록 조회
	 */
	@RequestMapping(value = "/selectDidimTrprInfoList.do")
	public View selectDidimTrprInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> dsTrprInfo = didimTrprInfoService.selectDidimTrprInfoList(dataRequest);
		dataRequest.setResponse("dsTrprInfo", dsTrprInfo);
		
		return new JSONDataView();
	}
	
	
}

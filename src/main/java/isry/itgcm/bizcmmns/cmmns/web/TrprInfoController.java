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
import isry.itgcm.bizcmmns.cmmns.service.TrprInfoService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
* @Class Name  : TrprInfoController.java
* @Description : 대상자정보조회 팝업 ServiceImpl Class
*
* @author  : Yoo.Chi.Hoon
* @since   : 2022. 05. 11.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 11.  Yoo.Chi.Hoon    최초작성
* </pre>
*/
@Controller
@RequestMapping(value = "/isry/itgcm/bizcmmns/cmmns")
public class TrprInfoController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "trprInfoService")
	private TrprInfoService trprInfoService;
	
	@Resource(name = "comCodeService")
	private ComCodeService comCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectTrprInfoOnload
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 5. 12. 
	 * @Method설명 : 대상자정보 화면 목록 구성시 업무단위콤보박스 조회
	 */
	@RequestMapping(value = "/selectTrprInfoOnload.do")
	public View selectTrprInfoOnload(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		

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
	 * @Method명   : selectTrprInfoList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 5. 11. 
	 * @Method설명 : 대상자정보 화면 목록 조회
	 */
	@RequestMapping(value = "/selectTrprInfoList.do")
	public View selectTrprInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = trprInfoService.selectTrprInfoList(request, dataRequest);
		
		dataRequest.setResponse("dsTrprInfo"   , retMap.get("dsTrprInfo"));
		dataRequest.setResponse("dmPage"       , retMap.get("dmPage"));		
		
		return new JSONDataView();
	}
	
	/**
	 * @Method명   : selectTrprInfoInqList
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 11. 16. 
	 * @Method설명 : 대상자정보조회팝업 New
	 */
	@RequestMapping(value = "/selectTrprInfoInqList.do")
	public View selectTrprInfoInqList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> dsTrprInfo = trprInfoService.selectTrprInfoInqList(request, dataRequest);
		
		dataRequest.setResponse("dsTrprInfo", dsTrprInfo.get("list"));
		dataRequest.setResponse("dmPageInfo", dsTrprInfo.get("dmPageInfo"));
		
		return new JSONDataView();
	}	
}

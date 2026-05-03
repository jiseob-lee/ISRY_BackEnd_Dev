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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.itgcm.bizcmmns.cmmns.service.ComCodeService;
import isry.itgcm.bizcmmns.cmmns.service.SrvcExcnBizService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : SrvcExcnBizController.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 5. 24. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 5. 24.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/bizcmmns/cmmns")
public class SrvcExcnBizController {
	
	@Resource(name = "srvcExcnBizService")
	private SrvcExcnBizService srvcExcnBizService;
	
	@Resource(name = "comCodeService")
	private ComCodeService comCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@RequestMapping(value = "/srvcExcnBizOnLoad.do")
	public View srvcExcnBizOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String sRetDsSet = "";		// RETURN 데이터셋 
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		HttpSession session = request.getSession();
        UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		List<Map<String, String>> paramList = paramGroup.getAllRowList();
			
		for (Map<String, String> rowMap : paramList) {
			
			sRetDsSet = String.valueOf(rowMap.get("DS_SET_NM"));
			rowMap.put("unitCode", userVo.getUntTaskwk());
			
			// 공통코드 조회(자원제공서비스 대분류, 중분류, 소분류, 상세분류)
			List<Map<String, Object>> list = comCodeService.selectCommonCodeUnit(rowMap);
			dataRequest.setResponse(sRetDsSet, list);
			
		}

		return new JSONDataView();
	}
	
	/**
	* @Method    : 서비스실행사업 목록조회
	* @param     : Map  :
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectSrvcExcnBizList.do")
	public View selectSrvcResrceClList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = srvcExcnBizService.selectSrvcExcnBizList(dataRequest);
		dataRequest.setResponse("dsList", list);

		return new JSONDataView();
	}

}

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

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : ProbmSttsCasController.java
 * @프로그램 설명 : 문제상태분류코드, 문제원인분류코드 팝업
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 7. 7. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 7. 7.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping("/isry/itgcm/bizcmmns/cmmns")
public class ProbmSttsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;	
	
	/**
	 * @Method명   : selectProbmSttsCasOnload
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 7. 7. 
	 * @Method설명 : 문제상태분류, 문제원인분류 공통코드 조회
	 */
	@RequestMapping(value = "/selectProbmSttsOnload.do")
	public View selectInstInfoList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception{
		
		 String codeId = "";				// 공통코드 아이디
		 String dataSetNm = "";				// 데이터셋
		 ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		 HttpSession session = request.getSession();
		 UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		 
		 LOGGER.debug("selectProbmSttsCasOnload.paraGroup=[" + paramGroup + "]" );
		 
		 if (paramGroup != null) {
			 
			 List<Map<String, String>> paramList = paramGroup.getAllRowList();
			 
			 for (Map<String, String> rowMap : paramList) {
				 
				 dataSetNm = String.valueOf(rowMap.get("DS_SET_NM")); 		// 응답 데이터셋 
				 codeId = String.valueOf(rowMap.get("CMMNS_CD_ID"));		// 요청 공통코드 아이디
				 
				 // 시스템 공통코드 조회 서비스 요청
				 List<Map<String, Object>> list = mgmtCmmnCodeService.selectCommonCodeUnit(codeId, userVo.getUntTaskwk());
				 // 응답객체 셋팅
				 dataRequest.setResponse(dataSetNm, list);
			 }
		 }
		return new JSONDataView();
	}

}

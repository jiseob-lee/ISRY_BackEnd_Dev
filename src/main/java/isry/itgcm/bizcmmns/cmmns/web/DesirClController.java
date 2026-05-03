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
import isry.itgcm.bizcmmns.cmmns.service.DesirClService;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : DesirClController.java
 * @프로그램 설명 : (보건)복지부욕구분류(팝업) Controller Class
 * - 
 * - 
 * @작성자        : Kwon.Min.Seo
 * @작성일        : 2022. 10. 13. 
 * @수정자        : Kwon.Min.Seo
 * @수정일        : 2022. 10. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/bizcmmns/cmmns")
public class DesirClController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "desirClService")
	private DesirClService desirClService;
	
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method     : selectDesirClOnLoad
	 * @Method설명 : (보건)복지부욕구분류 OnLoad
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 10. 13.
	 * ******************************
	 * 공통코드 조회 조건 (dsCodeParam)
	 * 1.CMMNS_CD_ID       : 공통코드아이디 (필수)  - ex) MOHW_DESIR_LCLAS_SE_CD
	 * 2.DS_SET_NM         : RETURN 데이터셋 (필수) - ex) dsDesirLclasSeCd
	 * 3.CMMNS_CD_VALUE    : 공통코드값
	 * 4.CMMNS_CD_VALUE_NM : 공통코드값명
	 * 5.ADDTNG_MNG_VALUE1 : 추가관리값1
	 * 6.ADDTNG_MNG_VALUE2 : 추가관리값2
	 * 7.ADDTNG_MNG_VALUE3 : 추가관리값3
	 * 8.ADDTNG_MNG_VALUE4 : 추가관리값4
	 * 9.ADDTNG_MNG_VALUE5 : 추가관리값5
	 *10.USE_YN            : 사용여부
	 */	
	@RequestMapping(value = "/selectDesirClOnLoad.do")
	public View selectDesirClOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		 String codeId = "";				// 공통코드 아이디
		 String dataSetNm = "";				// 데이터셋
		 ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		 HttpSession session = request.getSession();
		 UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		 LOGGER.debug("selectDesirClOnLoad.paramGroup=[" + paramGroup + "]" );
		 
		 if (paramGroup != null) {
			 
			 List<Map<String, String>> paramList = paramGroup.getAllRowList();
			 
			 for (Map<String, String> rowMap : paramList) {
				 
				 dataSetNm = String.valueOf(rowMap.get("DS_SET_NM")); 		// 응답 데이터셋 
				 codeId = String.valueOf(rowMap.get("CMMNS_CD_ID"));		// 요청 공통코드 아이디
				 LOGGER.debug("selectDesirClOnLoad.dataSetNm=[" + dataSetNm + "]" );
				 LOGGER.debug("selectDesirClOnLoad.codeId=[" + codeId + "]" );
				 // 시스템 공통코드 조회 서비스 요청
				 List<Map<String, Object>> list = mgmtCmmnCodeService.selectCommonCodeUnit(codeId, userVo.getUntTaskwk());
				 // 응답객체 셋팅
				 dataRequest.setResponse(dataSetNm, list);
			 }
		 }

		return new JSONDataView();
	}
	
	/**
	 * @Method     : selectDesirClList
	 * @Method설명 : (보건)복지부욕구분류 목록조회
	 * @param      : request
	 * @param      : response
	 * @return     : dataRequest 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 10. 13.
 	 */	
	@RequestMapping(value = "/selectDesirClList.do")
	public View selectDesirClList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = desirClService.selectDesirClList(dataRequest);
		dataRequest.setResponse("dsDesirClList", list);

		return new JSONDataView();
	}

}

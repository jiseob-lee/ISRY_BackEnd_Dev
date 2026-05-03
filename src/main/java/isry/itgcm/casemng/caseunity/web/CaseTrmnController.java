/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.web;

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
import isry.itgcm.casemng.caseunity.service.CaseTrmnService;
import isry.itgcm.ddnl.monthDdln.service.MonthDdlnService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


/**
* @Class Name  : CaseTrmnController.java
* @Description : 사례종결 Controller Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 09.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 09.  Seo.Hae.Seok    최초작성
* </pre>
*/
@Controller
@RequestMapping(value = "/isry/itgcm/casemng/caseunity")
public class CaseTrmnController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "caseTrmnService")
	private CaseTrmnService caseTrmnService;	
	
	@Resource(name = "monthDdlnService")
	private MonthDdlnService monthDdlnService;
	
	@Resource(name = "comCodeService")
	private ComCodeService comCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	* @Method    : 자원제공서비스분류 OnLoad
	* @param     : void
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	* ******************************
	* 공통코드 조회 조건 (dsCodeParam)
	* 1.CMMNS_CD_ID       : 공통코드아이디 (필수)  - ex) SRVC_RESRCE_LCLAS_SE_CD
	* 2.DS_SET_NM         : RETURN 데이터셋 (필수) - ex) dsSrvcResrceLclasSeCd
	* 3.CMMNS_CD_VALUE    : 공통코드값
	* 4.CMMNS_CD_VALUE_NM : 공통코드값명
	* 5.ADDTNG_MNG_VALUE1 : 추가관리값1
	* 6.ADDTNG_MNG_VALUE2 : 추가관리값2
	* 7.ADDTNG_MNG_VALUE3 : 추가관리값3
	* 8.ADDTNG_MNG_VALUE4 : 추가관리값4
	* 9.ADDTNG_MNG_VALUE5 : 추가관리값5
	*10.USE_YN            : 사용여부
	*/	
	@RequestMapping(value = "/selectCaseTrmnOnLoad.do")
	public View selectCaseTrmnOnLoad(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String sRetDsSet = "";		// RETURN 데이터셋 
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("selectCaseTrmnOnLoad.paramGroup=[" + paramGroup + "]");
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
	* @Method    : 사례종결 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list : 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCaseTrmnList.do")
	public View selectCaseTrmnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = caseTrmnService.selectCaseTrmnList(dataRequest);
		dataRequest.setResponse("dsCaseTrmnList"       , list);
		dataRequest.setResponse("dsCaseMngDdlnCrtrInfo", monthDdlnService.selectCaseMngDdlnCrtrInfo(dataRequest));

		return new JSONDataView();
	}

	/**
	* @Method    : 사례종결 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/processCaseTrmnDetail.do")
	public View processCaseTrmnDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례종결 저장
		Map<String, Object> info = caseTrmnService.processCaseTrmnDetail(request, dataRequest);
		
		return new JSONDataView();
	}
	
	
	@RequestMapping(value = "/selectCaseTrmnAprvList.do")
	public View selectCaseTrmnAprvList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = caseTrmnService.selectCaseTrmnAprvList(request, dataRequest);
		dataRequest.setResponse("dsCaseTrmnList"       , retMap.get("dsCaseTrmnList"));
		dataRequest.setResponse("dmPage"       , retMap.get("dmPage"));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectCaseTrmnAply.do")
	public View selectCaseTrmnAply(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = caseTrmnService.selectCaseTrmnAply(request, dataRequest);
		dataRequest.setResponse("dsCaseTrmnList"      , retMap.get("dsCaseTrmnList"));
		dataRequest.setResponse("dsCaseTrmnYmd"       , retMap.get("dsCaseTrmnYmd"));
		
		return new JSONDataView();
	}
	
	@RequestMapping(value = "/selectUpperInst.do")
	public View selectUpperInst(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsUpperInstList"   , caseTrmnService.selectUpperInst(request, dataRequest));
		
		return new JSONDataView();
	}	
	
	@RequestMapping(value = "/updateCaseTrmnAprv.do")
	public View updateCaseTrmnAprv(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례종결 수정
		Map<String, Object> retMap = caseTrmnService.updateCaseTrmnAprv(request, dataRequest);
		
		dataRequest.setResponse("dmSearch"   , retMap);		
		dataRequest.setResponse("dmParam"    , retMap);		
		
		return new JSONDataView();
	}	

}

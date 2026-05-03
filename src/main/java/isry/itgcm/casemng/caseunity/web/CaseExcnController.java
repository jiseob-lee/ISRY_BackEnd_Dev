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
import isry.itgcm.casemng.caseunity.service.CaseExcnService;
import isry.itgcm.casemng.caseunity.service.CasePlanService;
import isry.itgcm.ddnl.monthDdln.service.MonthDdlnService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : CaseExcnController.java
 * @프로그램 설명 	: 사례계획 Controller Class
 * 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 6. 13. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 6. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/casemng/caseunity")
public class CaseExcnController {

	@Resource(name = "caseExcnService")
	private CaseExcnService caseExcnService;
	
	@Resource(name = "casePlanService")
	private CasePlanService casePlanService;
	
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
	@RequestMapping(value = "/onLoadCaseExcn.do")
	public View onLoadCaseExcn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String sRetDsSet = "";		// RETURN 데이터셋 
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
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
	* @Method    : 사례제공 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCaseExcnList.do")
	public View selectCaseExcnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsCaseExcnList"       , caseExcnService .selectCaseExcnList(dataRequest));
		dataRequest.setResponse("dsProgramList"    	   , caseExcnService .selectExcnProgramList2(dataRequest));
		dataRequest.setResponse("dsCaseMngDdlnCrtrInfo", monthDdlnService.selectCaseMngDdlnCrtrInfo(dataRequest));
		dataRequest.setResponse("dsGoalPlanCn"         , casePlanService.selectGoalPlanCn(dataRequest));
		dataRequest.setResponse("dsExcnSrvcBizClList"  , caseExcnService .selectCaseExcnSrvcBizClList(dataRequest));
		return new JSONDataView();
	}

	/**
	* @Method    : 프로그램 목록조회
	* @param     : Map  : RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectExcnProgramList.do")
	public View selectExcnProgramList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = caseExcnService.selectExcnProgramList(dataRequest);
		dataRequest.setResponse("dsExcnProgramList", list);

		return new JSONDataView();
	}
	
	/**
	* @Method    : 프로그램 목록조회2
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectExcnProgramList2.do")
	public View selectExcnProgramList2(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = caseExcnService.selectExcnProgramList2(dataRequest);
		dataRequest.setResponse("dsProgramList", list);

		return new JSONDataView();
	}

	/**
	* @Method    : 사례제공이력 목록조회
	* @param     : Map  : SRVC_PVSN_BGNG_YMD(서비스제공시작일자), SRVC_PVSN_END_YMD(서비스제공종료일자), RESRCE_NO(자원명)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCaseExcnHstrList.do")
	public View selectCaseExcnHstrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = caseExcnService.selectCaseExcnHstrList(dataRequest);
		dataRequest.setResponse("dsExcnHstrList", list);

		return new JSONDataView();
	}

	/**
	* @Method    : 사례제공 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/processCaseExcnDetail.do")
	public View processCaseExcnDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례제공 저장
		Map<String, Object> info = caseExcnService.processCaseExcnDetail(request, dataRequest);
		dataRequest.setResponse("dmParam", info);
		
		return new JSONDataView();
	}	

}

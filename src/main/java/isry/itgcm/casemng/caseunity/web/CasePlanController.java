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
import isry.itgcm.casemng.caseunity.service.CasePlanService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : CasePlanController.java
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
public class CasePlanController {

	@Resource(name = "casePlanService")
	private CasePlanService casePlanService;
	
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
	@RequestMapping(value = "/onLoadCasePlan.do")
	public View onLoadCasePlan(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

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
	* @Method    : 사례계획 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCasePlanList.do")
	public View selectCasePlanList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
//		List<Map<String, Object>> list = casePlanService.selectCasePlanList(dataRequest);
		
		dataRequest.setResponse("dsCasePlanList", casePlanService.selectCasePlanList   (dataRequest));
		dataRequest.setResponse("dsProgramList" , casePlanService.selectPlanProgramList(dataRequest));

		return new JSONDataView();
	}
	
	/**
	* @Method    : 사례계획 목표계획내용 조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectGoalPlanCn.do")
	public View selectGoalPlanCn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> list = casePlanService.selectGoalPlanCn(dataRequest);
		dataRequest.setResponse("dsGoalPlanCn", list);

		return new JSONDataView();
	}

	/**
	* @Method    : 프로그램 목록조회
	* @param     : Map  : RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectProgramList.do")
	public View selectProgramList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = casePlanService.selectProgramList(dataRequest);
		dataRequest.setResponse("dsPlanProgramList", list);

		return new JSONDataView();
	}
	
	/**
	* @Method    : 계획_프로그램 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectPlanProgramList.do")
	public View selectPlanProgramList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = casePlanService.selectPlanProgramList(dataRequest);
		dataRequest.setResponse("dsProgramList", list);

		return new JSONDataView();
	}
	
	/**
	* @Method    : 사례계획이력 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCasePlanHstrList.do")
	public View selectCasePlanHstrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = casePlanService.selectCasePlanHstrList(dataRequest);
		dataRequest.setResponse("dsPlanHstrList", list);

		return new JSONDataView();
	}

	/**
	* @Method    : 사례계획 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/processCasePlanDetail.do")
	public View processCasePlanDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//사례계획 저장
		Map<String, Object> info = casePlanService.processCasePlanDetail(request, dataRequest);
		dataRequest.setResponse("dmParam", info);
		
		return new JSONDataView();
	}
	
	/**
	* @Method    : 서비스실행사업대상 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectSrvcExcnBizTrgtList.do")
	public View selectSrvcExcnBizTrgtList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> list = casePlanService.selectSrvcExcnBizTrgtList(dataRequest);
		dataRequest.setResponse("dsSrvcExcnBizTrgtList", list);

		return new JSONDataView();
	}

}

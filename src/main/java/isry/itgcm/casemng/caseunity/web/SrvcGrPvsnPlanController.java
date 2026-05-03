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
import isry.itgcm.casemng.caseunity.service.SrvcGrPvsnPlanService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : SrvcGrPvsnPlanController.java
 * @프로그램 설명 	: 서비스집단제공계획 Controller Class
 * 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 6. 28. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 6. 28.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/casemng/caseunity")
public class SrvcGrPvsnPlanController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "srvcGrPvsnPlanService")
	private SrvcGrPvsnPlanService srvcGrPvsnPlanService;
	
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
	@RequestMapping(value = "/onLoadSrvcGrPvsnPlan.do")
	public View onLoadSrvcGrPvsnPlan(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

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
	* @Method    : 서비스집단제공계획 목록조회
	* @param     : Map  : PLAN_FNDNG_BGNG_YMD(계획수립일자 시작), PLAN_FNDNG_END_YMD(계획수립일자 종료), PVSN_RESRCE_NM(자원명), RSFR_INST_NO(자원제공주체번호), RSFR_INST_NM(자원제공주체명)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectSrvcGrPvsnPlanList.do")
	public View selectSrvcGrPvsnPlanList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = srvcGrPvsnPlanService.selectSrvcGrPvsnPlanList(request, dataRequest);
		dataRequest.setResponse("dsSrvcGrPvsnPlanList", list);

		return new JSONDataView();
	}

	/**
	* @Method    : 서비스집단제공계획 상세조회
	* @param     : Map  : SRVC_PVSN_PLAN_NO(서비스제공계획번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCaseGrPlanDetail.do")
	public View selectCaseGrPlanDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> info = srvcGrPvsnPlanService.selectCaseGrPlanDetail(dataRequest);
		dataRequest.setResponse("dsDetailInfo", info);

		return new JSONDataView();
	}

	/**
	* @Method    : 프로그램 목록조회
	* @param     : Map  : RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectGrPlanProgramList.do")
	public View selectGrPlanProgramList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = srvcGrPvsnPlanService.selectGrPlanProgramList(dataRequest);
		dataRequest.setResponse("dsProgramList", list);

		return new JSONDataView();
	}
	
	/**
	* @Method    : 서비스집단제공계획 사례대상자 목록조회
	* @param     : Map  : SRVC_PVSN_PLAN_NO(서비스제공계획번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCaseTrprList.do")
	public View selectCaseTrprList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = srvcGrPvsnPlanService.selectCaseTrprList(dataRequest);
		dataRequest.setResponse("dsCaseTrprList", list);

		return new JSONDataView();
	}

	/**
	* @Method    : 서비스집단제공계획 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/processSrvcGrPvsnPlanDetail.do")
	public View processSrvcGrPvsnPlanDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> info = srvcGrPvsnPlanService.processSrvcGrPvsnPlanDetail(request, dataRequest);
		LOGGER.info("info:"+info);
		dataRequest.setResponse("dmParam", info);
		
		return new JSONDataView();
	}

	/**
	* @Method    : 서비스집단제공계획 상세삭제
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/deleteSrvcGrPvsnPlanDetail.do")
	public View deleteSrvcGrPvsnPlanDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		srvcGrPvsnPlanService.deleteSrvcGrPvsnPlanDetail(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	* @Method    : 서비스집단제공계획 이력 불러오기
	* @param     : Map  : PVSN_PRNMNT_BGNG_YMD(제공예정시작일자), PVSN_PRNMNT_END_YMD(제공예정종료일자), UNT_TASKWK_SE_CD(단위업무구분코드), RESRCE_NM(자원멍)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectSrvcGrPvsnPlanHstrList.do")
	public View selectSrvcGrPvsnPlanHstrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> list = srvcGrPvsnPlanService.selectSrvcGrPvsnPlanHstrList(request, dataRequest);
		dataRequest.setResponse("dsSrvcGrPvsnPlanHstrList", list);

		return new JSONDataView();
	}
	
	/**
	* @Method    : 서비스집단제공계획 서비스실행사업번호 기준 사례대상자 목록조회
	* @param     : Map  : SRVC_EXCN_BIZ_NO(서비스실행사업번호), UNT_TASKWK_SE_CD(단위업무구분코드), RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectBizNoPlanCaseTrprList.do")
	public View selectBizNoPlanCaseTrprList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> list = srvcGrPvsnPlanService.selectBizNoPlanCaseTrprList(dataRequest);
		dataRequest.setResponse("dsBizNoCaseTrprList", list);

		return new JSONDataView();
	}
}

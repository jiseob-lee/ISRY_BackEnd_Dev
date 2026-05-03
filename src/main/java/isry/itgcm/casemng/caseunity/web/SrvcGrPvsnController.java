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
import isry.itgcm.casemng.caseunity.service.SrvcGrPvsnService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : SrvcGrPvsnController.java
 * @프로그램 설명 	: 서비스집단제공 Controller Class
 * 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 6. 30. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 6. 30.
 * @수정내용      : 
 * -                
 * -                
 */
@Controller
@RequestMapping(value = "/isry/itgcm/casemng/caseunity")
public class SrvcGrPvsnController {

	@Resource(name = "srvcGrPvsnService")
	private SrvcGrPvsnService srvcGrPvsnService;
	
	@Resource(name = "comCodeService")
	private ComCodeService comCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "caseExcnService")
	private CaseExcnService caseExcnService;
	
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
	@RequestMapping(value = "/onLoadSrvcGrPvsn.do")
	public View onLoadSrvcGrPvsn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String sRetDsSet = "";		// RETURN 데이터셋 
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsCodeParam");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		
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
	* @Method    : 서비스집단제공 목록조회
	* @param     : Map  : START_DATE(등록일자 시작), END_DATE(등록일자 종료), PVSN_RESRCE_NM(자원명), RSFR_INST_NO(자원제공주체번호), RSFR_INST_NM(자원제공주체명)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectSrvcGrPvsnList.do")
	public View selectSrvcGrPvsnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		Map<String, Object> retMap = srvcGrPvsnService.selectSrvcGrPvsnList(request, dataRequest);
		
		dataRequest.setResponse("dsSrvcGrPvsnList", retMap.get("dsList"));		
		dataRequest.setResponse("dmPage", retMap.get("dmPage"));		

		return new JSONDataView();
	}

	/**
	* @Method    : 서비스집단제공 상세조회
	* @param     : Map  : SRVC_PVSN_NO(서비스제공번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCaseGrExcnDetail.do")
	public View selectCaseGrExcnDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> info = srvcGrPvsnService.selectCaseGrExcnDetail(dataRequest);
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
	@RequestMapping(value = "/selectGrExcnProgramList.do")
	public View selectGrExcnProgramList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = srvcGrPvsnService.selectGrExcnProgramList(dataRequest);
		dataRequest.setResponse("dsProgramList", list);

		return new JSONDataView();
	}
	
	/**
	* @Method    : 서비스집단제공 사례대상자 목록조회
	* @param     : Map  : SRVC_PVSN_NO(서비스제공번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectExcnCaseTrprList.do")
	public View selectExcnCaseTrprList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsExcnSrvcBizClList"  , srvcGrPvsnService.selectGrExcnSrvcBizClList(dataRequest));
		
		dataRequest.setResponse("dsCaseTrprList"       , srvcGrPvsnService.selectExcnCaseTrprList(request, dataRequest));

		return new JSONDataView();
	}

	/**
	* @Method    : 서비스집단제공 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/processSrvcGrPvsnDetail.do")
	public View processSrvcGrPvsnDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> info = srvcGrPvsnService.processSrvcGrPvsnDetail(request, dataRequest);
		dataRequest.setResponse("dmParam", info);

		return new JSONDataView();
	}

	/**
	* @Method    : 서비스집단제공 상세삭제
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/deleteSrvcGrPvsnDetail.do")
	public View deleteSrvcGrPvsnDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		srvcGrPvsnService.deleteSrvcGrPvsnDetail(request, dataRequest);

		return new JSONDataView();
	}

	/**
	* @Method    : 서비스집단제공 이력 불러오기
	* @param     : Map  : SRVC_PVSN_BGNG_YMD(서비스제공시작일자), SRVC_PVSN_END_YMD(서비스제공종료일자), UNT_TASKWK_SE_CD(단위업무구분코드), RESRCE_NM(자원멍)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectSrvcGrPvsnHstrList.do")
	public View selectSrvcGrPvsnHstrList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> list = srvcGrPvsnService.selectSrvcGrPvsnHstrList(request, dataRequest);
		dataRequest.setResponse("dsSrvcGrPvsnHstrList", list);

		return new JSONDataView();
	}

	/**
	* @Method    : 서비스집단제공 서비스실행사업번호 기준 사례대상자 목록조회
	* @param     : Map  : SRVC_EXCN_BIZ_NO(서비스실행사업번호), UNT_TASKWK_SE_CD(단위업무구분코드), RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectBizNoExcnCaseTrprList.do")
	public View selectBizNoExcnCaseTrprList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> list = srvcGrPvsnService.selectBizNoExcnCaseTrprList(dataRequest);
		dataRequest.setResponse("dsBizNoCaseTrprList", list);

		return new JSONDataView();
	}
	
	/**
	* @Method    : 서비스실행사업번호 기준 사례대상자 목록조회
	* @param     : Map  : SRVC_EXCN_BIZ_NO(서비스실행사업번호), UNT_TASKWK_SE_CD(단위업무구분코드)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectBizNoCaseTrprList.do")
	public View selectBizNoCaseTrprList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> list = srvcGrPvsnService.selectBizNoCaseTrprList(dataRequest);
		dataRequest.setResponse("dsBizNoCaseTrprList", list);

		return new JSONDataView();
	}
}

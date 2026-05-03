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
import isry.itgcm.casemng.caseunity.service.CaseAftfctService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : CaseAftfctController.java
 * @프로그램 설명 : 사후관리 Controller Class
 * 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 5. 31. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 5. 31.
 * @수정내용      : 
 * 
 */
@Controller
@RequestMapping(value = "/isry/itgcm/casemng/caseunity")
public class CaseAftfctController {

	@Resource(name = "caseAftfctService")
	private CaseAftfctService caseAftfctService;
	
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
	@RequestMapping(value = "/onLoadCaseReasse.do")
	public View onLoadCaseReasse(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

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
	* @Method    : 사후계획 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCaseAftfctPlanList.do")
	public View selectCaseAftfctPlanList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = caseAftfctService.selectCaseAftfctPlanList(dataRequest);
		dataRequest.setResponse("dsAftfctPlanList", list);

		return new JSONDataView();
	}

	/**
	* @Method    : 사후계획 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/processCaseAftfctPlanDetail.do")
	public View processCaseAftfctPlanDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		caseAftfctService.processCaseAftfctPlanDetail(request, dataRequest);

		return new JSONDataView();
	}

	/**
	* @Method    : 사후관리 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCaseAftfctMngList.do")
	public View selectCaseAftfctMngList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = caseAftfctService.selectCaseAftfctMngList(dataRequest);
		dataRequest.setResponse("dsAftfctMngList", list);

		return new JSONDataView();
	}

	/**
	* @Method    : 사후관리 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/processCaseAftfctMngDetail.do")
	public View processCaseAftfctMngDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		caseAftfctService.processCaseAftfctMngDetail(request, dataRequest);
		
		return new JSONDataView();
	}

	/**
	* @Method    : 사후관리담당자 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/processCaseAftfctPicDetail.do")
	public View processCaseAftfctPicDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		caseAftfctService.processCaseAftfctPicDetail(request, dataRequest);
		
		return new JSONDataView();
	}

	/**
	* @Method    : 사후종료 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCaseAftfctTrmnList.do")
	public View selectCaseAftfctTrmnList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = caseAftfctService.selectCaseAftfctTrmnList(dataRequest);
		dataRequest.setResponse("dsAftfctTrmnList", list);

		return new JSONDataView();
	}

	/**
	* @Method    : 사후종료 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/processCaseAftfctTrmnDetail.do")
	public View processCaseAftfctTrmnDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		caseAftfctService.processCaseAftfctTrmnDetail(request, dataRequest);
		
		return new JSONDataView();
	}

	/**
	* @Method    : 재사정 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCaseReasseList.do")
	public View selectCaseReasseList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = caseAftfctService.selectCaseReasseList(dataRequest);
		dataRequest.setResponse("dsCaseReasseList", list);

		return new JSONDataView();
	}

	/**
	* @Method    : 재사정 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/processCaseReasseDetail.do")
	public View processCaseReasseDetail(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		caseAftfctService.processCaseReasseDetail(request, dataRequest);
		
		return new JSONDataView();
	}

	/**
	* @Method    : 사후담당자 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCaseAftfctPicList.do")
	public View selectCaseAftfctPicList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = caseAftfctService.selectCaseAftfctPicList(dataRequest);
		dataRequest.setResponse("dsCaseAftfctPicList", list);

		return new JSONDataView();
	}

	/**
	* @Method    : 사후종결 심사담당자 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectAftfctTrmnSrngPicList.do")
	public View selectAftfctTrmnSrngPicList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = caseAftfctService.selectAftfctTrmnSrngPicList(dataRequest);
		dataRequest.setResponse("dsAftfctTrmnSrngPicList", list);

		return new JSONDataView();
	}

	/**
	* @Method    : 재사정 심사담당자 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@RequestMapping(value = "/selectCaseReasseSrngPicList.do")
	public View selectCaseReasseSrngPicList(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		List<Map<String, Object>> list = caseAftfctService.selectCaseReasseSrngPicList(dataRequest);
		dataRequest.setResponse("dsCaseReasseSrngPicList", list);

		return new JSONDataView();
	}
}

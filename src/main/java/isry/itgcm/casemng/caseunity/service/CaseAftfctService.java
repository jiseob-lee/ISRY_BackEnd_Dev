/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;


/**
 * @파일명        : CaseAftfctService.java
 * @프로그램 설명	: 사후관리 Servic Class
 * 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 5. 31. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 5. 31.
 * @수정내용      : 
 * 
 */
public interface CaseAftfctService {

	/**
	* @Method    : 사후계획 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseAftfctPlanList(DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 사후계획 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public void processCaseAftfctPlanDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 사후관리 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseAftfctMngList(DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 사후관리 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public void processCaseAftfctMngDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 사후관리담당자 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public void processCaseAftfctPicDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 사후종료 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseAftfctTrmnList(DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 사후종료 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public void processCaseAftfctTrmnDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 재사정 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseReasseList(DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 재사정 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public void processCaseReasseDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 사후담당자 목록조회
	* @param     : Map  : TRPR_INFO_NO(대상자정보번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseAftfctPicList(DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 사후종결 심사담당자 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectAftfctTrmnSrngPicList(DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 재사정 심사담당자 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseReasseSrngPicList(DataRequest dataRequest) throws Exception;

}

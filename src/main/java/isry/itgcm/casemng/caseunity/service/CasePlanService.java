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
 * @파일명        : CasePlanService.java
 * @프로그램 설명 	: 사례계획 Service Class
 * 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 6. 13. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 6. 13.
 * @수정내용      : 
 * -                
 * -                
 */
public interface CasePlanService {

	/**
	* @Method    : 사례계획 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCasePlanList(DataRequest dataRequest) throws Exception;
	
	/**
	* @Method    : 사례계획 목표계획내용 조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectGoalPlanCn(DataRequest dataRequest) throws Exception;
	
	/**
	* @Method    : 사례계획 프로그램 목록조회
	* @param     : Map  : RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectProgramList(DataRequest dataRequest) throws Exception;
	
	/**
	* @Method    : 사례계획 프로그램 목록조회2
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectPlanProgramList(DataRequest dataRequest) throws Exception;
	
	/**
	* @Method    : 사례계획이력 목록조회
	* @param     : Map  : PVSN_PRNMNT_BGNG_YMD(제공예정시작일자), PVSN_PRNMNT_END_YMD(제공예정종료일자), RESRCE_NO(자원명)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCasePlanHstrList(DataRequest dataRequest) throws Exception;
	
	/**
	* @Method    : 서비스제공계획 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public Map<String, Object> processCasePlanDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	* @Method    : 서비스실행사업대상 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectSrvcExcnBizTrgtList(DataRequest dataRequest) throws Exception;

}

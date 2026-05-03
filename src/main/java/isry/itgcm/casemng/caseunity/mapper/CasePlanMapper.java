/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


/**
 * @파일명        : CasePlanMapper.java
 * @프로그램 설명 	: 사례계획 Mapper Class
 * 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 6. 13. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 6. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("casePlanMapper")
public interface CasePlanMapper {

	/**
	* @Method    : 사례계획 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCasePlanList(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 사례계획 목표계획내용 조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/
	public List<Map<String, Object>> selectGoalPlanCn(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 프로그램 목록조회
	* @param     : Map  : RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectProgramList(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 계획_프로그램 목록조회
	* @param     : Map  : RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectPlanProgramList(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사례계획이력 목록조회
	* @param     : Map  : PVSN_PRNMNT_BGNG_YMD(제공예정시작일자), PVSN_PRNMNT_END_YMD(제공예정종료일자), RESRCE_NO(자원명)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>>selectCasePlanHstrList(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 서비스제공계획 저장
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int saveSEB300Data(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 서비스제공계획 이력등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int insertSEB301Data(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 서비스제공계획 삭제
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int deleteSEB300Data(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 서비스제공계획 삭제(DEL_YN = 'Y')
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int updateDelYnSEB300(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 서비스제공계획대상자 저장
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int saveSEB310Data(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 서비스제공계획대상자 이력등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int insertSEB311Data(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 서비스제공계획대상자 삭제
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int deleteSEB310Data(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 자원프로그램제공계획대상자 저장
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int saveSEB320Data(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 자원프로그램제공계획대상자 이력등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int insertSEB321Data(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 자원프로그램제공계획대상자 삭제
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int deleteSEB320Data(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 목표계획내용 등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int saveGoalPlanCn(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 목표계획내용 이력등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int insertGoalPlanCnHis(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 서비스실행사업대상 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectSrvcExcnBizTrgtList(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 서비스제공계획(SEB300) 목록 조회
	* @param     : Map  : SRVC_PVSN_PLAN_NO(서비스제공계획번호), RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectSrvcPvsnPlanList(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 서비스제공계획대상자(SEB310) 목록 조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수), SRVC_PVSN_PLAN_NO(서비스제공계획번호), RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectSrvcPvsnPlanTrprList(Map<String, String> paramMap) throws Exception;

}

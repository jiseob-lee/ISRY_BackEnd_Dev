/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.caseunity.mapper;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


/**
 * @파일명        : SrvcGrPvsnPlanMapper.java
 * @프로그램 설명 	: 서비스집단제공계획 Mapper Class
 * 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 6. 28. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 6. 28.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("srvcGrPvsnPlanMapper")
public interface SrvcGrPvsnPlanMapper {

	/**
	* @Method    : 서비스집단제공계획 목록조회
	* @param     : Map  : PLAN_FNDNG_BGNG_YMD(계획수립일자 시작), PLAN_FNDNG_END_YMD(계획수립일자 종료), PVSN_RESRCE_NM(자원명), RSFR_INST_NO(자원제공주체번호), RSFR_INST_NM(자원제공주체명)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectSrvcGrPvsnPlanList(Map<String, Object> paramMap) throws Exception;

	/**
	* @Method    : 서비스집단제공계획 상세조회
	* @param     : Map  : SRVC_PVSN_PLAN_NO(서비스제공계획번호)
	* @return    : map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseGrPlanDetail(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 프로그램 목록조회
	* @param     : Map  : RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectGrPlanProgramList(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 서비스집단제공계획 사례대상자 목록조회
	* @param     : Map  : SRVC_PVSN_PLAN_NO(서비스제공계획번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseTrprList(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 서비스집단제공계획 이력 불러오기
	* @param     : Map  : PVSN_PRNMNT_BGNG_YMD(제공예정시작일자), PVSN_PRNMNT_END_YMD(제공예정종료일자), UNT_TASKWK_SE_CD(단위업무구분코드), RESRCE_NM(자원멍)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectSrvcGrPvsnPlanHstrList(Map<String, Object> paramMap) throws Exception;
	
	/**
	* @Method    : 서비스집단제공계획 서비스실행사업번호 기준 사례대상자 목록조회
	* @param     : Map  : SRVC_EXCN_BIZ_NO(서비스실행사업번호), UNT_TASKWK_SE_CD(단위업무구분코드), RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectBizNoPlanCaseTrprList(Map<String, String> paramMap) throws Exception;

}

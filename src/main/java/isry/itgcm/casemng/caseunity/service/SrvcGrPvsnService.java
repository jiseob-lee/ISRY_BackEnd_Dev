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
 * @파일명        : SrvcGrPvsnService.java
 * @프로그램 설명 	: 서비스집단제공 Service Class
 * 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 6. 30. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 6. 30.
 * @수정내용      : 
 * -                
 * -                
 */
public interface SrvcGrPvsnService {

	/**
	* @Method    : 서비스집단제공 목록조회
	* @param     : Map  : START_DATE(등록일자 시작), END_DATE(등록일자 종료), PVSN_RESRCE_NM(자원명), RSFR_INST_NO(자원제공주체번호), RSFR_INST_NM(자원제공주체명)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public Map<String, Object> selectSrvcGrPvsnList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 서비스집단제공 상세조회
	* @param     : Map  : SRVC_PVSN_NO(서비스제공번호)
	* @return    : map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseGrExcnDetail(DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 서비스집단제공 프로그램 목록조회
	* @param     : Map  : RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectGrExcnProgramList(DataRequest dataRequest) throws Exception;
	
	/**
	* @Method    : 서비스집단제공 사례대상자 목록조회
	* @param     : Map  : SRVC_PVSN_NO(서비스제공번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectExcnCaseTrprList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 서비스집단제공 상세저장(등록,수정,삭제,이력)
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public Map<String, Object> processSrvcGrPvsnDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 서비스집단제공 상세삭제
	* @param     : Map  :
	* @return    : Map 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public void deleteSrvcGrPvsnDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	* @Method    : 서비스집단제공 이력 불러오기
	* @param     : Map  : SRVC_PVSN_BGNG_YMD(서비스제공시작일자), SRVC_PVSN_END_YMD(서비스제공종료일자), UNT_TASKWK_SE_CD(단위업무구분코드), RESRCE_NM(자원멍)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectSrvcGrPvsnHstrList(HttpServletRequest request,DataRequest dataRequest) throws Exception;
	
	/**
	* @Method    : 서비스집단제공 서비스실행사업번호 기준 사례대상자 목록조회
	* @param     : Map  : SRVC_EXCN_BIZ_NO(서비스실행사업번호), UNT_TASKWK_SE_CD(단위업무구분코드), RESRCE_NO(자원번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectBizNoExcnCaseTrprList(DataRequest dataRequest) throws Exception;
	
	/**
	* @Method    : 서비스실행사업번호 기준 사례대상자 목록조회
	* @param     : Map  : SRVC_EXCN_BIZ_NO(서비스실행사업번호), UNT_TASKWK_SE_CD(단위업무구분코드)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectBizNoCaseTrprList(DataRequest dataRequest) throws Exception;

	/**
	* @Method    : 서비스집단제공 실행서비스 목록조회
	* @param     : Map  : SRVC_PVSN_NO(서비스제공번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectGrExcnSrvcBizClList(DataRequest dataRequest) throws Exception;
}

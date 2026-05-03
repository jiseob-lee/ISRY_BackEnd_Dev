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
 * @파일명        : CaseAftfctMapper.java
 * @프로그램 설명 	: 사후관리 Mapper Class
 *
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 5. 31. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 5. 31.
 * @수정내용      : 
 *
 */
@Mapper("caseAftfctMapper")
public interface CaseAftfctMapper {

	/**
	* @Method    : 사후계획 목록조회
	* @param     : Map  : AFTFCT_PLAN_NO(사후계획번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseAftfctPlanList(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사후계획 저장
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int saveCaseAftfctPlanDetail(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사후계획 이력등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int insertCaseAftfctPlanHistory(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사후계획 삭제
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int deleteCaseAftfctPlanDetail(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사후관리 목록조회
	* @param     : Map  : AFTFCT_MNG_NO(사후관리번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseAftfctMngList(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사후관리 저장
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int saveCaseAftfctMngDetail(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사후관리 이력등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int insertCaseAftfctMngHistory(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사후관리 삭제
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int deleteCaseAftfctMngDetail(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사후종료 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseAftfctTrmnList(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사후종료 저장
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int saveCaseAftfctTrmnDetail(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사후종료 이력등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int insertCaseAftfctTrmnHistory(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사후종료 삭제
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int deleteCaseAftfctTrmnDetail(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 재사정 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseReasseList(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 재사정 저장
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int saveCaseReasseDetail(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 재사정 이력등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int insertCaseReasseHistory(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 재사정 삭제
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int deleteCaseReasseDetail(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 재사정 심사담당자 목록조회
	* @param     : Map  : CASE_MNG_NO(사례관리번호), CASE_MNG_ODRNO(사례관리차수)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseReasseSrngPicList(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 재사정심사자 저장
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int saveCaseReasseSrngDetail(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 재사정심사자 이력등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int insertCaseReasseSrngHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 재사정심사자 삭제
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int deleteCaseReasseSrngDetail(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 사후담당자 목록조회
	* @param     : Map  : AFTFCT_MNG_NO(사후관리번호)
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public List<Map<String, Object>> selectCaseAftfctPicList(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사후담당자 저장
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int saveCaseAftfctPicDetail(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사후담당자 이력등록
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int insertCaseAftfctPicHistory(Map<String, String> paramMap) throws Exception;

	/**
	* @Method    : 사후담당자 삭제
	* @param     : Map  : paramMap
	* @return    : void 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int deleteCaseAftfctPicDetail(Map<String, String> paramMap) throws Exception;
	
	/**
	* @Method    : 사후종결여부 확인
	* @param     : Map  : paramMap
	* @return    : int 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	public int selectAftfctCnt(Map<String, String> paramMap) throws Exception;
}

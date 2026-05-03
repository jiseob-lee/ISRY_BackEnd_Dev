/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.link.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : OfcdcLinkAplyMapper.java
 * @프로그램 설명 : 교육청 연계신청
 * - 
 * - 
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 8. 09. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 8. 09. 
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("ofcdcLinkAplyMapper")
public interface OfcdcLinkAplyMapper {
	
	/**
	 * @Method     : insertOfcdcGrFileUld
	 * @Method설명 : 교육청집단연계-파일업로드
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 10. 
 	 */	
	public int insertOfcdcGrFileUld(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertOfcdcGrFileUldDtl
	 * @Method설명 : 교육청집단연계-업로드파일상세
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 10. 
 	 */	
	public int insertOfcdcGrFileUldDtl(Map<String, String> map) throws Exception;
	
	// 관리일련번호(AKA220) 구하기
	public Map<String, Object> selectMngSn220(String sEnfsnNo) throws Exception;
		
	/**
	 * @Method     : selectLinkRqstdoList
	 * @Method설명 : 연계의뢰서 업로드(집단) 조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 10. 
		 */	
	public List<Map<String, String>> selectLinkRqstList(Map<String, String> map) throws Exception;

	// 관리일련번호(SEQUENCES) 구하기
	public Map<String, Object> selectMngSn();

	// 중복등록확인
	public String selectDupChk(Map<String, Object> subMap03);

	// 학생나이구하기
	public String selectStdntAge(String string);
	
	// 교육청집단연계(AKA220) update
	public int updateLinkRqst(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : selectOfcdcSpclaMngList
	 * @Method설명 : 특별관리리스트 조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 16. 
		 */	
	public List<Map<String, String>> selectOfcdcSpclaMngList(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : selectOfcdcErrorInfoList
	 * @Method설명 : 오류정보리스트 조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 16. 
		 */	
	public List<Map<String, String>> selectOfcdcErrorInfoList(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertSrvcPvsnRqst
	 * @Method설명 : SEB400(서비스제공의뢰) insert
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 16. 
 	 */	
	public int insertSrvcPvsnRqst(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: selectInstEnfsnNo
	 * @Method설명 	: 기관번호,담당자종사자번호 구하기
	 * @param      	: paramMap
	 * @return     	: Map 
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 16. 
 	 */
	
	public List<Map<String, Object>> selectInstEnfsnNo(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertTrprInfo
	 * @Method설명 : SEA200(대상자정보) insert
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 17. 
 	 */	
	public int insertTrprInfo(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertSchulwDscntc
	 * @Method설명 : SEA240(학업중단) insert
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 17. 
 	 */	
	public int insertSchulwDscntc(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertAcbgStts
	 * @Method설명 : SEA230(학력상태) insert
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 17. 
 	 */	
	public int insertAcbgStts(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertTrprInfoHstr
	 * @Method설명 : SEA201(대상자정보이력) insert	
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 17. 
 	 */	
	public int insertTrprInfoHstr(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertSchulwDscntcHstr
	 * @Method설명 : SEA241(학업중단이력) insert
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 17. 
 	 */	
	public int insertSchulwDscntcHstr(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertAcbgSttsHstr
	 * @Method설명 : SEA231(학력상태이력) insert
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 17. 
 	 */	
	public int insertAcbgSttsHstr(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertSrvcPvsnRqstHstr
	 * @Method설명 : SEB401(서비스제공의뢰이력) insert
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 24. 
 	 */	
	public int insertSrvcPvsnRqstHstr(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertSrvcPvsnRcpt
	 * @Method설명 : SEB420(서비스제공접수) insert
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 24. 
 	 */	
	public int insertSrvcPvsnRcpt(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : insertSrvcPvsnRcptHstr
	 * @Method설명 : SEB421(서비스제공접수이력) insert	
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 08. 24. 
 	 */	
	public int insertSrvcPvsnRcptHstr(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : moeSchulmCodeList
	 * @Method설명 : 특별관리리스트에 해당하는 code를 조회
	 * @param      : 
	 * @return     : list 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 02. 01. 
 	 */	
	public List<String> moeSchulmCodeList() throws Exception;
	
	/**
	 * @Method     : selectLinkRqstDetList
	 * @Method설명 : 연계의뢰서 업로드(집단) 상세 조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 04. 18.
	 */	
	public List<Map<String, String>> selectLinkRqstDetList(Map<String, String> map) throws Exception;
	
	/**
	 * @Method     : selectMoeCode
	 * @Method설명 : 교육청코드 조회
	 * @param      : inst_no
	 * @return     : String 
	 * @exception  : Exception
	 * @작성자     : Hee Sung Yoon
	 * @작성일     : 2023. 06. 22.
	 */	
	public String selectMoeCode(int instNo) throws Exception;
}

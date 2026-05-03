/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.outsd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
* @Class Name  : InfifIrSsiFs03Mapper.java
* @Description : 복지부 관련 연계  Mapper Class
*
* @author  : Lee.Seung.Yeon
* @since   : 2022. 08. 01.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 08. 01.  Lee.Seung.Yeon    최초작성
* </pre>
*/

@Mapper("linkMohwJobMapper")
public interface LinkMohwJobMapper {
	
	/**
	 * @Method		: selectTrnsmiTrgtList
	 * @Method설명 	: 공유자원 제공서비스 사보정 송신 대상 목록조회
	 * @param      	: paramMap
	 * @return     	: Map 
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 08. 01. 
 	 */	
	public List<Map<String, Object>> selectTrnsmiTrgtList(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: deletePvsnSrv
	 * @Method설명 	: 공유자원 제공 서비스 송신 데이터 삭제
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 08. 01. 
 	 */	
	public void deletePvsnSrv(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: insertPvsnSrv
	 * @Method설명 	: 공유자원 제공 서비스 송신 데이터 등록
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 08. 01. 
 	 */	
	public void insertPvsnSrv(Map<String, Object> map) throws Exception;
	
	/**
	 * @Method		: selectDscsnHstrList
	 * @Method설명 	: 상담이력 사보정 송신 대상 목록조회
	 * @param      	: paramMap
	 * @return     	: Map 
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 08. 03. 
 	 */	
	public List<Map<String, Object>> selectDscsnHstrList(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: deleteDscsnHstr
	 * @Method설명 	: 상담이력 사보정 송신 데이터 삭제
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 08. 03. 
 	 */	
	public void deleteDscsnHstr(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: insertDscsnHstr
	 * @Method설명 	: 상담이력 사보정 송신 데이터 등록
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 08. 03. 
 	 */	
	public void insertDscsnHstr(Map<String, Object> map) throws Exception;
	
	/**
	 * @Method		: selectSrvcHstrList
	 * @Method설명 	: 서비스이력 사보정 송신 연계 목록 조회
	 * @param      	: paramMap
	 * @return     	: Map 
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 03. 
 	 */	
	public List<Map<String, Object>> selectSrvcHstrList(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: deleteSrvcHstr
	 * @Method설명 	: 서비스이력 사보정 송신 연계 데이터 삭제
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 03. 
 	 */	
	public void deleteSrvcHstr(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: insertSrvcHstr
	 * @Method설명 	: 서비스이력 사보정 송신 데이터 등록
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 03. 
 	 */	
	public void insertSrvcHstr(Map<String, Object> map) throws Exception;

	/**
	 * @Method		: selectMbyTrnsmiTrgtList
	 * @Method설명 	: 공유자원 제공주체 사보정 송신 대상 목록조회
	 * @param      	: paramMap
	 * @return     	: Map 
	 * @exception  	: Exception
	 * @작성자     	: Choi.Doo.Il
	 * @작성일     	: 2022. 08. 03. 
 	 */	
	
	public List<Map<String, Object>> selectMbyTrnsmiTrgtList(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: deletePvsnMby
	 * @Method설명 	: 공유자원 제공주체 송신 데이터 삭제
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Choi.Doo.Il
	 * @작성일     	: 2022. 08. 03. 
 	 */	
	public void deletePvsnMby(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: insertPvsnMby
	 * @Method설명 	: 공유자원 제공주체 송신 데이터 등록
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Choi.Doo.Il
	 * @작성일     	: 2022. 08. 03. 
 	 */	
	public void insertPvsnMby(Map<String, Object> map) throws Exception;	
	
	/**
	 * @Method		: selectCaseMngList
	 * @Method설명 	: 사례관리 사보정 송신 대상 목록조회
	 * @param      	: paramMap
	 * @return     	: Map 
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 08. 04. 
 	 */	
	public List<Map<String, Object>> selectCaseMngList(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: deleteCaseMng
	 * @Method설명 	: 사례관리 사보정 송신 데이터 삭제
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 08. 04. 
 	 */	
	public void deleteCaseMng(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: insertCaseMng
	 * @Method설명 	: 사례관리 사보정 송신 데이터 등록
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 08. 04. 
 	 */	
	public void insertCaseMng(Map<String, Object> map) throws Exception;
	
	/**
	 * @Method		: selectMohwSrvcRqstRcptList
	 * @Method설명 	: 보건복지부 서비스의뢰 접수 연계 목록조회
	 * @param      	: paramMap
	 * @return     	: Map 
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 05. 
 	 */	
	
	public List<Map<String, Object>> selectMohwSrvcRqstRcptList(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: selectIndvIdntfcNo
	 * @Method설명 	: 대상자주민번호로 개인식별번호 구하기
	 * @param      	: paramMap
	 * @return     	: Map 
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 05. 
 	 */
	
	public Map<String, Object> selectIndvIdntfcNo(String sTrprRRno) throws Exception;
	
	/**
	 * @Method		: selectRcptInstNo
	 * @Method설명 	: 개인식별번호로  접수기관번호 구하기
	 * @param      	: paramMap
	 * @return     	: Map 
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 05. 
 	 */
	
	public String selectRcptInstNo(String sIndvIdntfcNo) throws Exception;
	
	/**
	 * @Method		: selectUntTaskwkSecd
	 * @Method설명 	: 의뢰서비스기관ID로  단위업무구분코드 구하기
	 * @param      	: paramMap
	 * @return     	: Map 
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 05. 
 	 */
	
	public String selectUntTaskwkSecd(String sRqstSrvInstId) throws Exception;
	
	/**
	 * @Method		: updateSrvcRqstRcpt
	 * @Method설명 	: CAB100(서비스의뢰접수) ESB_SEQ(연계시퀸스) 'Y' update
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 05. 
 	 */	
	public int updateSrvcRqstRcpt(Map<String, String> map) throws Exception;
}

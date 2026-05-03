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
* @Class Name  : LinkMmaRcptJobMapper.java
* @Description : 병무청 관련 연계  Mapper Class
*
* @author  : Lee.Tae.Ho
* @since   : 2022. 08. 04.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 08. 04.  Lee.Tae.Ho    최초작성
* </pre>
*/

@Mapper("linkMmaRcptJobMapper")
public interface LinkMmaRcptJobMapper {	
	
	/**
	 * @Method		: selectMmaSprtList
	 * @Method설명 	: 심리취약 병역의무자지원 구분 및 자원기관정보 연계 목록조회
	 * @param      	: paramMap
	 * @return     	: Map 
	 * @exception  	: Exception
	 * @작성자     	: Choi.Doo.Il
	 * @작성일     	: 2022. 08. 04. 
 	 */	
	public List<Map<String, Object>> selectMmaSprtList(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: deleteMmaSprt
	 * @Method설명 	: 심리취약 병역의무자지원 구분 및 자원기관정보 연계 데이터 삭제
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Choi.Doo.Il
	 * @작성일     	: 2022. 08. 04. 
 	 */	
	public void deleteMmaSprt(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: insertMmaSprt
	 * @Method설명 	: 심리취약 병역의무자지원 구분 및 자원기관정보 연계 등록
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Choi.Doo.Il
	 * @작성일     	: 2022. 08. 04. 
 	 */	
	public void insertMmaSprt(Map<String, Object> map) throws Exception;	
	
	/**
	 * @Method		: selectMmaTrmnList
	 * @Method설명 	: 심리취약한 병역의무자 상담지원 의뢰 종결처리 현황 연계 목록조회
	 * @param      	: paramMap
	 * @return     	: Map 
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 04. 
 	 */	
	public List<Map<String, Object>> selectMmaTrmnList(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: deleteMmaTrmn
	 * @Method설명 	: 심리취약한 병역의무자 상담지원 의뢰 종결처리 현황 연계 데이터 삭제
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 04. 
 	 */	
	public void deleteMmaTrmn(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: insertMmaTrmn
	 * @Method설명 	: 심리취약한 병역의무자 상담지원 의뢰 종결처리 현황 연계 데이터  데이터 등록
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 04. 
 	 */	
	public void insertMmaTrmn(Map<String, Object> map) throws Exception;
	
	/**
	 * @Method		: deleteMmaTrpr
	 * @Method설명 	: 심리취약한 병역의무자 상담지원 의뢰 사례관리 대상자선정결과 연계 데이터 삭제
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 05. 
 	 */	
	public void deleteMmaTrpr(Map<String, String> map) throws Exception;
	
	/**
	 * @Method		: insertMmaTrpr
	 * @Method설명 	: 심리취약한 병역의무자 상담지원 의뢰 사례관리 대상자선정결과 연계 데이터 등록
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Tae.Ho
	 * @작성일     	: 2022. 08. 05. 
 	 */	
	public void insertMmaTrpr(Map<String, Object> map) throws Exception;
	
	/**
	 * @Method		: insertMmaTrpr
	 * @Method설명 	: 병역의무자 상담지원 의뢰접수처리 조회
	 * @param      	: paramMap
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Choi.Doo.Il
	 * @작성일     	: 2022. 10. 04. 
 	 */	
	public List<Map<String, Object>> selectLinkMmaList(Map<String, Object> map) throws Exception;

	//병무청 의뢰접수처리
	public int updateLinkMma(Map<String, String> map) throws Exception;	

	//CAA130 연계상태 Y로
	public int updateEsbStatus(Map<String, String> map) throws Exception;
	
	//대상자 존재유무 확인
	public int selectTrprInfoNo(Map<String, String> map) throws Exception;
	
	//연계상태 확인
	public String selectEsbStatus(Map<String, String> map) throws Exception;
	
	//대상자확인
	public List<Map<String, String>> seachTrpr() throws Exception;

	//CAA100 연계상태 Y로
	public int esbStatus(Map<String, String> map) throws Exception;

	//대상자번호 조회
	public Map<String, String> selectSeqTrprInfoNo(String sEsbSeq) throws Exception;	
	
	/**
	 * @Method		: selectMmaCaseTrprSlctnList
	 * @Method설명 	: 심리취약한 병역의무자 상담지원 의뢰 사례관리 대상자선정 연계 목록조회
	 * @param      	: paramMap
	 * @return     	: Map 
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 10. 26. 
 	 */	
	public List<Map<String, Object>> selectMmaCaseTrprSlctnList(Map<String, String> map) throws Exception;

	/**
	 * @Method		: selectMmaCaseTrmnPrcsList
	 * @Method설명 	: 심리취약한 병역의무자 상담지원 의뢰 종결처리 현황 연계 목록조회
	 * @param      	: paramMap
	 * @return     	: Map 
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 10. 26. 
 	 */	
	public List<Map<String, Object>> selectMmaCaseTrmnPrcsList(Map<String, String> map) throws Exception;
	
	
	/**
	 * @Method명   : selectMmaRqstList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 21. 
	 * @Method설명 : 병역의무자 상담지원의뢰 접수목록 조회
	 */
	public List<Map<String, Object>> selectMmaRqstList(Map<String, String> map) throws Exception;
	
	/**
	 * @Method명   : selectMmaRqstInfo
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 21. 
	 * @Method설명 : 병역의무자 상담지원의뢰 접수정보 조회
	 */
	public List<Map<String, Object>> selectMmaRqstInfo(Map<String, String> map) throws Exception;
	
	/**
	 * @Method명   : selectMmaRqstInfoResult
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 21. 
	 * @Method설명 : 병역의무자 상담지원의뢰 접수결과정보 조회
	 */
	public List<Map<String, Object>> selectMmaRqstInfoResult(Map<String, String> map) throws Exception;
	
	/**
	 * @Method명   : insertMmaRqstResult
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 21. 
	 * @Method설명 : 병역의무자 상담지원의뢰 접수결과 저장
	 */
	public Integer insertMmaRqstResult(Map<String, String> map) throws Exception;
	
	/**
	 * @Method명   : updateMmaRqstResult
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 21. 
	 * @Method설명 : 병역의무자 상담지원의뢰 접수결과 수정
	 */
	public Integer updateMmaRqstResult(Map<String, String> map) throws Exception;
	
	
	public Map<String, String> selectLinkMmaFileInfo(Map<String, String> map) throws Exception;	
}

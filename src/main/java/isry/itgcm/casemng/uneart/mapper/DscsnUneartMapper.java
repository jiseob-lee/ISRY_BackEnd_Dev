/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.uneart.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;


/**
* @Class Name  : DscsnUneartMapper.java
* @Description : 발굴정보 Mapper Class
*
* @author  : Seo.Hae.Seok
* @since   : 2022. 05. 23.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 05. 23.  Seo.Hae.Seok    최초작성
* </pre>
*/
@Mapper("dscsnUneartMapper")
public interface DscsnUneartMapper {
	
	/**
	 * @Method명   : selectUneartDscsnList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 30. 
	 * @Method설명 : 발굴상담 목록조회
	 */
	public List<Map<String, Object>> selectUneartDscsnList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectOutrctList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 30. 
	 * @Method설명 : 아웃리치 목록조회
	 */
	public List<Map<String, Object>> selectOutrctList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectEmrgRescList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 30. 
	 * @Method설명 : 긴급구조 목록조회
	 */
	public List<Map<String, Object>> selectEmrgRescList(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method     : selectDscsnUneartList
	 * @Method설명 : 발굴 목록조회(01:초기상담,02:아웃리치,03.긴급개입)
	 * @param      : paramMap
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public List<Map<String, Object>> selectDscsnUneartList(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method     : selectDscsnUneartDetail
	 * @Method설명 : 발굴(초기상담) 상세조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public Map<String, Object> selectDscsnUneartDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectDscsnUneartActnList
	 * @Method설명 : 발굴(초기상담) 조치내역(대상자)조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public List<Map<String, Object>> selectDscsnUneartActnList(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectDscsnUneartHstrList
	 * @Method설명 : 발굴(초기상담) 이력조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public List<Map<String, Object>> selectDscsnUneartHstrList(Map<String, String> paramMap) throws Exception;

	
	/**
	 * @Method     : selectDscsnUneartDetailModChk
	 * @Method설명 : 발굴(초기상담) 상세 변경체크 
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public String selectDscsnUneartDetailModChk(Map<String, String> paramMap) throws Exception;

	
	/**
	 * @Method     : insertDscsnUneartDetail
	 * @Method설명 : 발굴(초기상담) 상세등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int insertDscsnUneartDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateDscsnUneartDetail
	 * @Method설명 : 발굴(초기상담) 상세수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int updateDscsnUneartDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : deleteDscsnUneartDetail
	 * @Method설명 : 발굴(초기상담) 상세삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int deleteDscsnUneartDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertDscsnUneartHistory
	 * @Method설명 : 발굴(초기상담) 이력등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int insertDscsnUneartHistory(Map<String, String> paramMap) throws Exception;


	/**
	 * @Method     : selectDscsnUneartActnSn
	 * @Method설명 : 발굴(초기상담후) 조치일련번호 발번
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int selectDscsnUneartActnSn(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertDscsnUneartActn
	 * @Method설명 : 발굴(초기상담후) 조치등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int insertDscsnUneartActn(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateDscsnUneartActn
	 * @Method설명 : 발굴(초기상담후) 조치수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int updateDscsnUneartActn(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : deleteDscsnUneartActn
	 * @Method설명 : 발굴(초기상담후) 조치삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int deleteDscsnUneartActn(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteDscsnUneartActnHistory
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 7. 21. 
	 * @Method설명 : 발굴(초기상담후 조치이력삭제)
	 */
	public int deleteDscsnUneartActnHistory(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertDscsnUneartActnHistory
	 * @Method설명 : 발굴(초기상담후) 조치이력등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int insertDscsnUneartActnHistory(Map<String, String> paramMap) throws Exception;

}

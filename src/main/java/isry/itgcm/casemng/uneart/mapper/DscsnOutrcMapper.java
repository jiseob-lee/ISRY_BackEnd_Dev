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
* @Class Name  : DscsnOutrcMapper.java
* @Description : 아웃리치정보 Mapper Class
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
@Mapper("dscsnOutrcMapper")
public interface DscsnOutrcMapper {

	/**
	 * @Method     : selectDscsnOutrcDetail
	 * @Method설명 : 발굴(아웃리치) 상세조회 
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public Map<String, Object> selectDscsnOutrcDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectDscsnOutrcUniteList
	 * @Method설명 : 발굴(아웃리치) 연합거리상담조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public List<Map<String, Object>> selectDscsnOutrcUniteList(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectDscsnOutrcSheltrList
	 * @Method설명 : 발굴(아웃리치) 쉼터자체활동조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public List<Map<String, Object>> selectDscsnOutrcSheltrList(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectDscsnOutrcPrfmncDetail
	 * @Method설명 : 발굴(아웃리치) 지원서비스실적
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public Map<String, Object> selectDscsnOutrcPrfmncDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectDscsnOutrcActnList
	 * @Method설명 : 발굴(아웃리치) 조치현황
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public List<Map<String, Object>> selectDscsnOutrcActnList(Map<String, String> paramMap) throws Exception;


	/**
	 * @Method     : selectDscsnOutrcDetailModChk
	 * @Method설명 : 발굴(아웃리치) 상세 변경체크 
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public String selectDscsnOutrcDetailModChk(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectDscsnOutrcNopeModChk
	 * @Method설명 : 발굴(아웃리치) 인원수 변경체크 
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public String selectDscsnOutrcNopeModChk(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : selectDscsnOutrcPrfmncList
	 * @Method설명 : 발굴(아웃리치) 서비스지원횟수조회
	 * @param      : paramMap
	 * @return     : Map 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public List<Map<String, String>> selectDscsnOutrcPrfmncList(Map<String, String> paramMap) throws Exception;
	
	
	/**
	 * @Method     : insertDscsnOutrcDetail
	 * @Method설명 : 발굴(아웃리치) 상세등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int insertDscsnOutrcDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateDscsnOutrcDetail
	 * @Method설명 : 발굴(아웃리치) 상세수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int updateDscsnOutrcDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateDscsnOutrcDetailNope
	 * @Method설명 : 발굴(아웃리치) 상세인원수수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int updateDscsnOutrcDetailNope(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : deleteDscsnOutrcDetail
	 * @Method설명 : 발굴(아웃리치) 상세삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int deleteDscsnOutrcDetail(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertDscsnOutrcHistory
	 * @Method설명 : 발굴(아웃리치) 이력등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int insertDscsnOutrcHistory(Map<String, String> paramMap) throws Exception;


	/**
	 * @Method     : insertDscsnOutrcActvt
	 * @Method설명 : 발굴(아웃리치) 연합거리상담, 쉼터자체활동 등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int insertDscsnOutrcActvt(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateDscsnOutrcActvt
	 * @Method설명 : 발굴(아웃리치) 연합거리상담, 쉼터자체활동 수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int updateDscsnOutrcActvt(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : deleteDscsnOutrcActvt
	 * @Method설명 : 발굴(아웃리치) 연합거리상담, 쉼터자체활동 삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int deleteDscsnOutrcActvt(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertDscsnOutrcActvtHistory
	 * @Method설명 : 발굴(아웃리치) 연합거리상담, 쉼터자체활동 이력등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int insertDscsnOutrcActvtHistory(Map<String, String> paramMap) throws Exception;


	/**
	 * @Method     : insertDscsnOutrcPrfmnc
	 * @Method설명 : 발굴(아웃리치) 지원서비스실적 등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int insertDscsnOutrcPrfmnc(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateDscsnOutrcPrfmnc
	 * @Method설명 : 발굴(아웃리치) 지원서비스실적 수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int updateDscsnOutrcPrfmnc(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : deleteDscsnOutrcPrfmnc
	 * @Method설명 : 발굴(아웃리치) 지원서비스실적 삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int deleteDscsnOutrcPrfmnc(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertDscsnOutrcPrfmncHistory
	 * @Method설명 : 발굴(아웃리치) 지원서비스실적 이력등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int insertDscsnOutrcPrfmncHistory(Map<String, String> paramMap) throws Exception;


	/**
	 * @Method     : selectDscsnOutrcActnSn
	 * @Method설명 : 발굴(아웃리치) 조치일련번호 발번
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int selectDscsnOutrcActnSn(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertDscsnOutrcActn
	 * @Method설명 : 발굴(아웃리치) 조치현황 등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int insertDscsnOutrcActn(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : updateDscsnOutrcActn
	 * @Method설명 : 발굴(아웃리치) 조치현황 수정
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int updateDscsnOutrcActn(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : deleteDscsnOutrcActn
	 * @Method설명 : 발굴(아웃리치) 조치현황 삭제
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int deleteDscsnOutrcActn(Map<String, String> paramMap) throws Exception;

	/**
	 * @Method     : insertDscsnOutrcActnHistory
	 * @Method설명 : 발굴(아웃리치) 조치현황 이력등록
	 * @param      : paramMap
	 * @return     : int 
	 * @exception  : Exception
	 * @작성자     : Seo.Hae.Seok
	 * @작성일     : 2022. 05. 23. 
 	 */	
	public int insertDscsnOutrcActnHistory(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectUneartActvtSeCd
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 12. 6. 
	 * @Method설명 : 아웃리치 활동구분 저장데이터 조회
	 */
	public List<Map<String, String>> selectUneartActvtSeCd(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectUneartActbtClList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 12. 6. 
	 * @Method설명 : 아웃리치 활동구분 상세화면 조회
	 */
	public Map<String, Object> selectUneartActbtClList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : deleteUneartActvt
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 12. 7. 
	 * @Method설명 : 발굴(아웃리치) 연합거리상담,쉼터자체활동 삭제
	 */
	public int deleteUneartActvt(Map<String, String> paramMap) throws Exception;
}

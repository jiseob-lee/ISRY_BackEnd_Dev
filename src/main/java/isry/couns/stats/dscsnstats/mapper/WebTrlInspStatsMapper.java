/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : WebTrlInspStatsMapper.java
 * @프로그램 설명 : 웹심리검사 통계 Mapper Class
 * - 
 * - 
 * @작성자        : Jeong.Won.Je
 * @작성일        : 2023. 2. 10. 
 * @수정자        : Jeong.Won.Je
 * @수정일        : 2023. 2. 10.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("webTrlInspStatsMapper")
public interface WebTrlInspStatsMapper {
	
	/**
	 * @Method명   : selectWebTrlInspKndList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 15. 
	 * @Method설명 : 웹심리검사 통계_검사 종류 List 조회(검사종류/검사구분/실시건수/댓글건수)
	 */
	public List<Map<String, Object>> selectWebTrlInspKndList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectTwdpsnRelPreconList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 20. 
	 * @Method설명 : 선택된 검사종류가 '대인관계_고민영역&문제원인'인 경우 검사결과현황 조회 
	 */
	public List<Map<String, Object>> selectTwdpsnRelPreconList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectTwdpsnRelResultList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 :선택된 검사종류가 '대인관계_고민영역&문제원인'인 경우 검사결과 조회
	 */
	public List<Map<String, Object>> selectTwdpsnRelResultList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCoupleAngerAspectCrtronPreconList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 선택된 검사종류가 '대학생 연인관계 유형 & 화내기유형 & 삶의 관점척도'인 경우 검사결과현황 조회
	 */
	public List<Map<String, Object>> selectCoupleAngerAspectCrtronPreconList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCoupleAngerAspectCrtronResultList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 : 선택된 검사종류가 '대학생 연인관계 유형 & 화내기유형 & 삶의 관점척도'인 경우 검사결과 조회
	 */
	public List<Map<String, Object>> selectCoupleAngerAspectCrtronResultList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCoseSchulwPreconList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 20. 
	 * @Method설명 : 선택된 검사종류가 '진로&학업_고민영역&문제원인'인 경우 검사결과현황 조회
	 */
	public List<Map<String, Object>> selectCoseSchulwPreconList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCoseSchulwResultList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 : 선택된 검사종류가 '진로&학업_고민영역&문제원인'인 경우 검사결과 조회
	 */
	public List<Map<String, Object>> selectCoseSchulwResultList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCoseDirectivityPreconList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 선택된 검사종류가 '진로지향성'인 경우 검사결과현황 조회
	 */
	public List<Map<String, Object>> selectCoseDirectivityPreconList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCoseDirectivityResultList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 : 선택된 검사종류가 '진로지향성'인 경우 검사결과 조회
	 */
	public List<Map<String, Object>> selectCoseDirectivityResultList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectAddictionPreconList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 선택된 검사종류가 '중독(인터넷중독&스마트폰중독&도박)&스트레스경험'인 경우 검사결과현황 조회
	 */
	public List<Map<String, Object>> selectAddictionStressPreconList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectAddictionStressResultList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 : 선택된 검사종류가 '중독(인터넷중독&스마트폰중독&도박)&스트레스경험'인 경우 검사결과 조회
	 */
	public List<Map<String, Object>> selectAddictionStressResultList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectGnrlCharctPreconList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 선택된 검사종류가 '일반성격'인 경우 검사결과현황 조회
	 */
	public List<Map<String, Object>> selectGnrlCharctPreconList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectGnrlCharctResultList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 23. 
	 * @Method설명 : 선택된 검사종류가 '일반성격'인 경우 검사결과 조회
	 */
	public List<Map<String, Object>> selectGnrlCharctResultList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectEmtStablePreconList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 선택된 검사종류가 '정서안정성'인 경우 검사결과현황 조회
	 */
	public List<Map<String, Object>> selectEmtStablePreconList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectEmtStableResultList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 23. 
	 * @Method설명 : 선택된 검사종류가 '정서안정성'인 경우 검사결과 조회
	 */
	public List<Map<String, Object>> selectEmtStableResultList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectSoctyAnxietParntsADHDDepresPreconList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 선택된 검사종류가 '사회불안&부모용ADHD&우울증'인 경우 검사결과현황 조회
	 */
	public List<Map<String, Object>> selectSoctyAnxietParntsADHDDepresPreconList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectSoctyAnxietParntsADHDDepresResultList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 23. 
	 * @Method설명 : 선택된 검사종류가 '사회불안&부모용ADHD&우울증'인 경우 검사결과 조회
	 */
	public List<Map<String, Object>> selectSoctyAnxietParntsADHDDepresResultList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectMomNurtureEfficaPreconList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 선택된 검사종류가 '어머니의 양육효능감'인 경우 검사결과현황 조회
	 */
	public List<Map<String, Object>> selectMomNurtureEfficaPreconList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectMomNurtureEfficaResultList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 23. 
	 * @Method설명 : 선택된 검사종류가 '어머니의 양육효능감'인 경우 검사결과 조회
	 */
	public List<Map<String, Object>> selectMomNurtureEfficaResultList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectWebTrlInspProbmSttsList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 10. 
	 * @Method설명 : 웹심리검사 문제상태 통계
	 */
	public List<Map<String, Object>> selectWebTrlInspProbmSttsList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectWebTrlInspDgstfnKndLIst
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 웹심리검사 만족도 통계_검사 종류 List 조회(검사종류/응답건수)
	 */
	public List<Map<String, Object>> selectWebTrlInspDgstfnKndList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectWebTrlInspDgstfnKndDetail
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 웹심리검사 만족도 통계_선택한 검사에 대한 만족도 조사 결과 조회
	 */
	public List<Map<String, Object>> selectWebTrlInspDgstfnKndDetail(Map<String, String> paramMap) throws Exception;
}

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
 * @파일명 : MobileManageStatsMapper.java
 * @프로그램 설명 : 설문지 작성을 관리하는 Mapper
 * @작성자 : kim.seong.gyu
 * @작성일 : 2022. 5. 04
 * @수정자 : 
 * @수정일 : 
 * @수정내용 : - -
 */
@Mapper("mobileManageStatsMapper")
public interface MobileManageStatsMapper {

	/**
	 * @Method명   : selectTrgtAndResponseYnStatsList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 사후관리 대상 및 응답 여부 기준 통계 조회
	 */
	public List<Map<String, Object>> selectTrgtAndResponseYnStatsList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectNonResponseCsStatsList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 사후관리 미응답 사유 기준 통계 조회
	 */
	public List<Map<String, Object>> selectNonResponseCsStatsList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectResponseCnStatsList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 사후관리 응답 내용 기준 통계 조회
	 */
	public List<Map<String, Object>> selectResponseCnStatsList(Map<String, String> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectCnsltntPerformanceStatsList
	 * @param 	   : paramMap
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 사후관리 상담사별 실적 기준 통계 조회
	 */
	public List<Map<String, Object>> selectCnsltntPerformanceStatsList(Map<String, String> paramMap) throws Exception;

}

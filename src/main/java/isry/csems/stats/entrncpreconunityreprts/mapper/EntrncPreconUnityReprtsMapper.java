/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.stats.entrncpreconunityreprts.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : EntrncPreconUnityReprtsMapper.java
 * @프로그램 설명 : 입소현황통합보고서 매퍼 인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 3. 2.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 3. 2.
 * @수정내용 : - -
 */
@Mapper("entrncPreconUnityReprtsMapper")
public interface EntrncPreconUnityReprtsMapper {

	/**
	 * @Method명 : selectEntrncPreconUnityReprtsㄴ
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 23.
	 * @Method설명 : 입소현황통합보고서 조회
	 */
	public List<Map<String, Object>> selectEntrncPreconUnityReprts(Map<String, Object> map) throws Exception;

	/**
	 * @Method명 : selectAplyInstReprts
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Seoung.Jae
	 * @작성일 : 2023. 2. 16.
	 * @Method설명 : 신청기관보고서 조회
	 */
	public List<Map<String, Object>> selectAplyInstReprts(Map<String, Object> paramMap) throws Exception;

	/**
	 * @Method명 : selectTotTrprList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 2. 21.
	 * @Method설명 : 집계대상자목록 조회
	 */
	public List<Map<String, String>> selectTotTrprList(Map<String, Object> paramMap) throws Exception;
}

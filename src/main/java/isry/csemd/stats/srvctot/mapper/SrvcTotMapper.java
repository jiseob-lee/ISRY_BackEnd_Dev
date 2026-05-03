/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.stats.srvctot.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : SrvcTotMapper.java
 * @프로그램 설명 : 서비스별집계 Mapper Interface - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 2. 13.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 2. 13.
 * @수정내용 : - -
 */
@Mapper(value = "srvcTotMapper")
public interface SrvcTotMapper {

	/**
	 * @Method명 : selectYrStats
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 13.
	 * @Method설명 : 연도별통계 피벗데이터 조회
	 */
	public List<Map<String, String>> selectYrStats(Map<String, String> reqMap) throws Exception;

	/**
	 * @Method명 : selectPrdCrseEnfsnStats
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 14.
	 * @Method설명 : 기간별통계 & 과정별통계 & 종사자별통계 피벗데이터 조회
	 */
	public List<Map<String, String>> selectPrdCrseEnfsnStats(Map<String, String> reqMap) throws Exception;

	/**
	 * @Method명 : selectPrdCrseEnfsnStatsSum
	 * @param reqMap
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 14.
	 * @Method설명 : 기간별통계 & 과정별통계 & 종사자별통계 합계데이터 조회
	 */
	public List<Map<String, String>> selectPrdCrseEnfsnStatsSum(Map<String, String> reqMap) throws Exception;

}

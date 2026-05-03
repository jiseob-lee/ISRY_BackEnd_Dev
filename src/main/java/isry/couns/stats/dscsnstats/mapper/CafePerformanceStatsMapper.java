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
 * @파일명 : CafePerformanceStatsMapper.java
 * @프로그램 설명 : 위기청소년카페통계(실적)를 관리하는 Mapper
 * @작성자 : kim.seong.gyu
 * @작성일 : 2022. 5. 04
 * @수정자 : 
 * @수정일 : 
 * @수정내용 : - -
 */
@Mapper("cafePerformanceStatsMapper")
public interface CafePerformanceStatsMapper {

	
	public List<Map<String, Object>> selectcafePerformanceStats(Map<String, Object> mapParam) throws Exception;
	
	
}

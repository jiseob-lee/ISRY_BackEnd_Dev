/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.statsmgmt.stats.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : StatsMapper.java
 * @프로그램 설명 : 통계관리 매퍼 인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 7. 11.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 7. 11.
 * @수정내용 : - -
 */
@Mapper("statsMapper")
public interface StatsMapper {

	public List<Map<String, String>> selectTrprDetailStatusStatsList(Map<String, String> map) throws Exception;

	public List<Map<String, String>> selectMonthExecStatusStatsList(Map<String, String> map) throws Exception;

	public List<Map<String, Object>> selectRecruitStatsList(Map<String, String> paramMap) throws Exception;

	public List<Map<String, Object>> selectProgressStatsList(Map<String, String> paramMap) throws Exception;

	public List<Map<String, Object>> selectTrprNumberOfAgeList(Map<String, String> paramMap)throws Exception;

	public List<Map<String, Object>> selectReportStatsStudntSlctList(Map<String, String> paramMap)throws Exception;

	public List<Map<String, Object>> selectReportStatsStudntEduList(Map<String, String> paramMap)throws Exception;
	
	public List<Map<String, Object>> selectReportStatsStudntResultList(Map<String, String> paramMap)throws Exception;
}

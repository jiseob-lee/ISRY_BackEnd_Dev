/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.stats.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : StatsDrmgsMapper.java
 * @프로그램 설명 : 학교밖지원센터 통계
 * @작성자        : Hee Sung Yoon
 * @작성일        : 2022. 12. 23. 
 * @수정자        : Hee Sung Yoon
 * @수정일        : 2022. 12. 23. 
 * @수정내용      : 학교밖청소년지원센터 통계
*/

@Mapper("statsDrmgsMapper")
public interface StatsDrmgsMapper {

	// 직업역량강화 통계
	public List<Map<String, Object>> selectOccpAbilitStats(Map<String, Object> paramMap) throws Exception;
	
	// 학업중단숙려제 통계
	public List<Map<String, Object>> selectMeditationStats(Map<String, Object> paramMap) throws Exception;
	
	// 경기도사업 통계
	public List<Map<String, Object>> selectGgBizStats(Map<String, Object> paramMap) throws Exception;
	
	// 경기도사업 통계 코드리스트
	public List<Map<String, Object>> selectGgCodeList(String id) throws Exception;
	
	// 경기도사업 통계
	public List<Map<String, Object>> selectGgBizStatsTrpr(Map<String, Object> paramMap) throws Exception;
}

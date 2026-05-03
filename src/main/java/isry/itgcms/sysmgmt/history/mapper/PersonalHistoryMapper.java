/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.history.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : PersonalHistoryMapper.java
 * @프로그램 설명 : 사용자의 이력 조회 및 상세조회 Mapper
 * @작성자 : Ji-Seob.Lee
 * @작성일 : 2022. 10. 8.
 * @수정자 : Ji-Seob.Lee
 * @수정일 : 2022. 10. 8.
 * @수정내용 : - -
 */

@Mapper("personalHistoryMapper")
public interface PersonalHistoryMapper {

	public Integer selectWorkerHistoryCount(Map<String, Object> dmSearchMap) throws Exception;
	
	public List<Map<String, Object>> selectWorkerHistory(Map<String, Object> dmSearchMap) throws Exception;

	public Integer selectYouthGuardianHistoryCount(Map<String, Object> dmSearchMap) throws Exception;
	
	public List<Map<String, Object>> selectYouthGuardianHistory(Map<String, Object> dmSearchMap) throws Exception;

	public Integer selectPersonalInfoHistoryCount(Map<String, Object> dmSearchMap) throws Exception;
	
	public List<Map<String, Object>> selectPersonalInfoHistory(Map<String, Object> dmSearchMap) throws Exception;

	public Integer selectLoginUserHistoryCount(Map<String, Object> dmSearchMap) throws Exception;
	
	public List<Map<String, Object>> selectLoginUserHistory(Map<String, Object> dmSearchMap) throws Exception;

	// 종사자 이력 정보 기입
	public void insertWorkerInfoHistory(Map<String, String> map) throws Exception;

	// 청소년, 보호자 이력 정보 기입
	public void insertYouthGuardianInfoHistory(Map<String, String> map) throws Exception;

	// 개인 이력 정보 기입
	public void insertPersonalInfoHistory(Map<String, String> map) throws Exception;

}

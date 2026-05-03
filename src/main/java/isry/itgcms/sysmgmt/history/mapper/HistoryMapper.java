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
 * @파일명 : HistoryMapper.java
 * @프로그램 설명 : 이력 조회 및 상세조회 Mapper
 * @작성자 : Park.Kyu.Young
 * @작성일 : 2022. 4. 7.
 * @수정자 : Park.Kyu.Young
 * @수정일 : 2022. 4. 7.
 * @수정내용 : - -
 */

@Mapper("historyMapper")
public interface HistoryMapper {

	/**
	 * @Method명   : selectProgramHistoryTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 : 프로그램이력 totalCount 조회
	 */
	public Integer selectProgramHistoryTotalCount(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : selectProgramHistory
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 : 프로그램이력 조회
	 */
	public List<Map<String, Object>> selectProgramHistory(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : selectMenuHistoryTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 : 메뉴이력 totalCount 조회
	 */
	public Integer selectMenuHistoryTotalCount(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : selectMenuHistory
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 : 메뉴이력 조회
	 */
	public List<Map<String, Object>> selectMenuHistory(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : selectDeptHistoryTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 : 부서이력 totalCount 조회
	 */
	public Integer selectDeptHistoryTotalCount(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : selectDeptHistory
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 : 부서이력 조회
	 */
	public List<Map<String, Object>> selectDeptHistory(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : selectInstituteHistoryTotalCount
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 : 통합기관이력 totalCount 조회
	 */
	public Integer selectInstituteHistoryTotalCount(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : selectInstituteHistory
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 : 통합기관이력 조회
	 */
	public List<Map<String, Object>> selectInstituteHistory(Map<String, Object> map) throws Exception;

	/**
	 * @Method명   : selectOrg
	 * @return
	 * @throws Exception
	 * @작성자     : Park.Kyu.Young
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 : 상위기관 조회
	 */
	public List<Map<String, Object>> selectOrg() throws Exception;

	public Integer selectRightsHistoryCount(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectRightsHistory(Map<String, Object> map) throws Exception;

	public Integer selectRightsHistoryGroupCount(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectRightsHistoryGroup(Map<String, Object> map) throws Exception;

	public Integer selectRightsHistoryUserMenuCount(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectRightsHistoryUserMenu(Map<String, Object> map) throws Exception;

}

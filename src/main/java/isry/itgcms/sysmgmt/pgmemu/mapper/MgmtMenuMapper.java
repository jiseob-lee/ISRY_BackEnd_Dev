/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.pgmemu.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : MgmtMenuMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 11. 23. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 11. 23.
 * @수정내용      : 
 * -                
 * -                
 */

@Mapper("mgmtMenuMapper")
public interface MgmtMenuMapper {

	public List<Map<String, Object>> selectMenu(Map<String, String> map) throws Exception;

	public List<Map<String, Object>> selectUserMenu(Map<String, String> map) throws Exception;
	
	public List<Map<String, Object>> selectMenuPivot(Map<String, Object> vo) throws Exception;
	
	public void deleteAllMenu(List<Integer> list) throws Exception;
	
	public void saveMenu(Map<String, Object> map) throws Exception;
	
	public int selectMaxMenuId() throws Exception;
	
	public List<Map<String, Object>> selectRootMenu() throws Exception;
	
	public void insertMenuHistory(Map<String, Object> map) throws Exception;

	public String selectTopMenuCd(String topMenuId) throws Exception;
	
	public String selectTopMenuNm(String untTaskwk) throws Exception;
	
	public String getUntTaskwk(int topMenu) throws Exception;
	
	public void insertSAB301(List<Map<String, String>> list) throws Exception;
	
	public void deleteSAB300(List<Map<String, String>> list) throws Exception;
	
	public void insertSAB251(List<Map<String, String>> list) throws Exception;
	
	public void deleteSAB250(List<Map<String, String>> list) throws Exception;
	
	public void deleteSAB210(List<Map<String, String>> list) throws Exception;
	
	/**
	 * @Method명   : selectMenuDetails
	 * @param menuId
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 21. 
	 * @Method설명 : 메뉴 상세 조회
	 */
	List<Map<String, Object>> selectMenuDetails(String menuId) throws Exception;

	public List<String> selectUserMenuIdList(String rightId) throws Exception;
	
	public Integer selectUserMenuCount(String userId) throws Exception;
	
	public void updateUserMenuUpdateCountIncrease(String userId) throws Exception;
	
	public void updateUserMenuUpdateCountIncreaseAll() throws Exception;
	
	public String selectUserMenuCached(String userId) throws Exception;
	
	public void saveUserMenuCache(Map<String, String> paramMap) throws Exception;
}

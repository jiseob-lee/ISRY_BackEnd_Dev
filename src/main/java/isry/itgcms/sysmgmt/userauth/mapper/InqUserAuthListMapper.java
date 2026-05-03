/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : InqUserAuthListMapper.java
 * @프로그램 설명 : 사용자별 권한 조회
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
@Mapper("inqUserAuthListMapper")
public interface InqUserAuthListMapper {

	//public List<Map<String, Object>> selectMenu1() throws Exception;

	public List<Map<String, Object>> selectUserPivot(Map<String, Object> vo) throws Exception;

	public List<Map<String, Object>> selectUserRights2(Map<String, String> map) throws Exception;
	
	public List<Map<String, Object>> selectUserRightsFromGroupRights(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : selectUserAuthList
	 * @param map
	 * @return
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2023. 2. 7. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectUserAuthList(Map<String, String> map) throws Exception;
	
	/**
	 * @Method명   : selectUserAuthList
	 * @param map
	 * @return
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2023. 2. 7. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectAuthrtList(Map<String, String> map) throws Exception;
	
}

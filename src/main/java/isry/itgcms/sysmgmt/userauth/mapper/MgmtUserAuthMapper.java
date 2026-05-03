/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.userauth.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 * @파일명        : MgmtGrpAuthMapper.java
 * @프로그램 설명 : 그룹별 권한 저장 매퍼
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 11. 29. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 11. 29.
 * @수정내용      : 
 * -                
 * -
 */

@Mapper("mgmtUserAuthMapper")
public interface MgmtUserAuthMapper {
	
	public List<Integer> getUserAuthExists(Map<String, Object> paramMap) throws Exception;
	
	/**
	 * @Method명   : selectUserAuthList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 22. 
	 * @Method설명 : 사용자별 메뉴 권한 목록 조회
	 */
	public List<Map<String, Object>> selectUserAuthList(Map<String, Object> mapParam) throws Exception;
	
	public void saveUserAuth(Map<String, Object> map) throws Exception;
	
	public void saveUserAuthHistory(Map<String, Object> map) throws Exception;
	
	public void deleteUserAuth(Map<String, Object> paramMap) throws Exception;
	
	public void deleteUserAuthHistory(Map<String, Object> paramMap) throws Exception;
	
	public void saveUserDetailAuths(Map<String, Object> paramMap) throws Exception;
	
	public void saveUserDetailAuthsHistory(Map<String, Object> paramMap) throws Exception;
	
	public void deleteUnavailUserAuth() throws Exception;
	
	/**
	 * @Method명   : deleteUserAuthByAuthrtIds
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 2. 
	 * @Method설명 : 사용자별 메뉴권한 일괄 삭제 (권한아이디 기준)
	 */
	public int deleteUserAuthByAuthrtIds(Map<String, Object> mapParam) throws Exception;

	/**
	 * 사용자의 사용자 권한을 모두 삭제한다. 그룹 권한을 넣기 위해 기존의 사용자 권한을 모두 삭제한다.
	 * @param userId
	 * @throws Exception
	 */
	public void deleteUserAuthWithId(Map<String, Object> mapParam) throws Exception;
	
	public void deleteUserAuthWithIdHistory(Map<String, String> map) throws Exception;
	
	/**
	 * 그룹 권한을 사용자 권한에 하나씩 넣는다.
	 * @param map
	 * @throws Exception
	 */
	public void insertUserAuthWithGroupAuth(Map<String, Object> map) throws Exception;
	
	public void insertUserAuthWithGroupAuthHistory(Map<String, Object> map) throws Exception;
	
	/**
	 * @Method명   : insertUserDetailAuthsByInstAuth
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 : 사용자별 메뉴 권한 초기 설정
	 */
	public int insertUserDetailAuthsByInstAuth(Map<String, Object> mapParam) throws Exception;
	
}

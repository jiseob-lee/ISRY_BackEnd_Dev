/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.mapper;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : MgmtAuthGrpMapper.java
 * @프로그램 설명 : 권한 그룹 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 3. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("mgmtAuthGrpMapper")
public interface MgmtAuthGrpMapper {
	
	public void deleteAllAuthGrp() throws Exception;
	
	public void saveAuthGrp(Map<String, String> map) throws Exception;
	
	public void saveAuthGrpHistory(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : saveUserMenuAuth
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 사용자별 메뉴권한 저장
	 * <pre>
	 * 	- 원본 테이블 : 권한별사용자 (SAB250) & 권한별 메뉴 (SAB300)
	 * 	- 대상 테이블 : 사용자별 메뉴권한 (SAB250)
	 * </pre>
	 */
	int saveUserMenuAuth(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectUserMenuAuthChgHistoryInfos
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 사용자별 메뉴권한 이력 변경데이터 조회
	 */
	List<Map<String, Object>> selectUserMenuAuthChgHistoryInfos(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectMenuNoListByAuthrtIds
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 21. 
	 * @Method설명 : 권한아이디로 부터 메뉴 번호 목록 조회
	 */
	List<Map<String, Object>> selectMenuNoListByAuthrtIds(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectUserMenuAuthHistoryInfos
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 28. 
	 * @Method설명 : 사용자별 메뉴권한 이력 데이터 조회
	 */
	List<Map<String, Object>> selectUserMenuAuthHistoryInfos(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : insertUserMenuAuthHistory
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 사용자별 메뉴권한 이력 저장
	 */
	int insertUserMenuAuthHistory(Map<String, Object> mapParam) throws Exception;
}

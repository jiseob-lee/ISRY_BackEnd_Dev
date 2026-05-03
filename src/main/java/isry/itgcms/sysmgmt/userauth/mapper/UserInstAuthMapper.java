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
 * @파일명        : UserInstAuthMapper.java
 * @프로그램 설명 : 사용자별 기관 권한
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2023. 2. 19. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2023. 2. 19.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("userInstAuthMapper")
public interface UserInstAuthMapper {
	
	/**
	 * @Method명   : selectUserInstAuthList
	 * @param userId	사용자아이디
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 19. 
	 * @Method설명 : 사용자별 기관 권한 목록 조회
	 */
	List<Map<String, Object>> selectUserInstAuthList(String userId) throws Exception;
	
	/**
	 * @Method명   : selectUpInstDetails
	 * @param srchInstNo	검색할 기관번호
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 5. 3. 
	 * @Method설명 : 상위기관 상세 조회
	 */
	List<Map<String, Object>> selectUpInstDetails(String srchInstNo) throws Exception;

	/**
	 * @Method명   : searchLwprtInstList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 하위기관 목록 조회
	 */
	List<Integer> searchLwprtInstList(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : insertUserInstAuth
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 23. 
	 * @Method설명 : 사용자별 기관 권한 등록
	 */
	int insertUserInstAuth(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : updateUserInstAuth
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 24. 
	 * @Method설명 : 사용자별 기관 권한 수정
	 */
	int updateUserInstAuth(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : deleteUserInstAuth
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 24. 
	 * @Method설명 : 사용자별 기관 권한 삭제
	 */
	int deleteUserInstAuth(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : deleteUserInstAuthByReset
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 : 사용자별 기관 권한 삭제 (주기관 제외)
	 */
	int deleteUserInstAuthByReset(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : updateUserInstAuthByReset
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 : 사용자별 기관 권한 초기화
	 */
	int updateUserInstAuthByReset(Map<String, Object> mapParam) throws Exception;
}

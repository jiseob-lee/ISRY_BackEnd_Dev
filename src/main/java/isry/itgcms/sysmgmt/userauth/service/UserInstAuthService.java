/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

import isry.itgcms.sysmgmt.userauth.vo.UserInstAuthVO;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : UserInstAuthService.java
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
public interface UserInstAuthService {
	
	/** 기관번호 목록 key */
	String KEY_INST_NOS = "INST_NOS";
	
	/** 역할 유형: 총괄관리자 */
	String TYPE_ROLE_GENERAL_ADMIN = "ROLE_GENERAL_ADMIN";
	
	/** 역할 유형: 기관관리자 */
	String TYPE_ROLE_INST_ADMIN = "ROLE_INST_ADMIN";
	
	/** 역할 유형: 사업담당자 */
	String TYPE_ROLE_BIZ_WORKER = "ROLE_BIZ_WORKER";
	
	/** 역할 유형: 담당자 */
	String TYPE_ROLE_WORKER = "ROLE_WORKER";
	
	/** 역할 유형: 사용자 (청소년 및 보호자) */
	String TYPE_ROLE_USER = "ROLE_USER";
	
	/** 역할 유형: 시스템관리자여부 */
	String TYPE_IS_SYSTEM_MNGR = "IS_SYSTEM_MNGR";
	
	/**
	 * @Method명   : storeSession
	 * @param loginVO		로그인 VO
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 19. 
	 * @Method설명 : 사용자별 기관 권한 세션 적재
	 */
	void storeSession(UserDetailsVO loginVO) throws Exception;
	
	/**
	 * @Method명   : destorySession
	 * @param request
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 19. 
	 * @Method설명 : 사용자별 기관 권한 세션 삭제
	 */
	void destorySession(HttpServletRequest request) throws Exception;
	
	/**
	 * @Method명   : getOgdpInstNo
	 * @param request
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 19. 
	 * @Method설명 : 소속(주)기관번호 조회
	 */
	Integer getOgdpInstNo(HttpServletRequest request) throws Exception;
	
	/**
	 * @Method명   : checkAuthrtRole
	 * @param request
	 * @param roleName		역할명
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 4. 14. 
	 * @Method설명 : 권한에 대한 역할 체크
	 * <pre>
	 * 	- 사용자 세션에 저장된 기관 권한 목록 (SAB230) 에서 해당 되는 역할이 존재하는 체크한다.
	 * 	- 예) ROLE_GENERAL_ADMIN, ROLE_INST_ADMIN, ROLE_BIZ_WORKER, ROLE_WORKER, ROLE_USER)
	 * </pre>
	 */
	Boolean checkAuthrtRole(HttpServletRequest request, String roleName) throws Exception;
	
	/**
	 * @Method명   : getUserInstAuthVO
	 * @param request
	 * @param instNo	검색할 기관번호
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 2. 
	 * @Method설명 : 사용자별 기관 권한 조회
	 */
	UserInstAuthVO getUserInstAuthVO(HttpServletRequest request, Integer instNo) throws Exception;
	
	/**
	 * @Method명   : getAprvInstNoList
	 * @param request
	 * @param mapParam	Map 형식의 검색 데이터
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 7. 
	 * @Method설명 : 승인 기관번호 정보 조회
	 */
	<K, V> Map<String, Object> getAprvInstNoInfo(HttpServletRequest request, Map<? super K, ? super V> mapParam) throws Exception;
	
	/**
	 * @Method명   : getAprvInstNoList
	 * @param request
	 * @param dataRequest
	 * @param dataMapId		검색조건 데이터맵 ID (기본값: dmSearch)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 9. 
	 * @Method설명 : 승인 기관번호 정보 조회
	 */
	Map<String, Object> getAprvInstNoInfo(HttpServletRequest request, DataRequest dataRequest, String dataMapId) throws Exception;
	
	/**
	 * @Method명   : checkInstAuth
	 * @param request
	 * @param dataRequest
	 * @param dataMapId		검색조건 데이터맵 ID (기본값: dmSearch)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 : 사용자별 기관 권한 체크
	 */
	Map<String, Object> checkInstAuth(HttpServletRequest request, DataRequest dataRequest, String dataMapId) throws Exception;
	
	/**
	 * @Method명   : getUnitSysAuthItems
	 * @param request
	 * @param dataRequest
	 * @param dataMapId		검색조건 데이터맵 ID (기본값: dmSearch)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 사용자별 기관 권한 목록 조회
	 */
	List<Map<String, Object>> getUserInstAuthItems(HttpServletRequest request, DataRequest dataRequest, String dataMapId) throws Exception;
	
	/**
	 * @Method명   : updateInstAuthSession
	 * @param request
	 * @param dataRequest
	 * @param dataMapId
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 기관 권한 세션 업데이트
	 */
	Map<String, Object> updateInstAuthSession(HttpServletRequest request, DataRequest dataRequest, String dataMapId) throws Exception;
	
	/**
	 * @Method명   : createInstSrchParams
	 * @param request
	 * @param dataRequest
	 * @param dataMapId		검색조건 데이터맵 ID (기본값: dmSearch)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 기관 검색 Parameter 생성 (사용자별 기관 권한)
	 */
	Map<String, Object> createInstSrchParams(HttpServletRequest request, DataRequest dataRequest, String dataMapId) throws Exception;
	
	/**
	 * @Method명   : createInstSrchParams
	 * @param request
	 * @param mapParam	Map 형식의 검색 데이터
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21.
	 * @Method설명 : 기관 검색 Parameter 생성 (사용자별 기관 권한)
	 */
	<K, V> Map<String, Object> createInstSrchParams(HttpServletRequest request, Map<? super K, ? super V> mapParam) throws Exception;
	
	/**
	 * @Method명   : createInstSrchParams
	 * @param request
	 * @param untTaskwkSeCd		단위업무구분코드 (필수)
	 * @return
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 기관 검색 Parameter 생성 (사용자별 기관 권한)
	 */
	Map<String, Object> createInstSrchParams(HttpServletRequest request, String untTaskwkSeCd) throws Exception;

}

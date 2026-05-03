/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.userauth.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * 
 * @파일명        : MgmtUserAuthService.java
 * @프로그램 설명 : 사용자 권한 저장
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

public interface MgmtUserAuthService {
	
	public void saveUserAuth(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public void saveUserDetailAuths(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : deleteAllUserMenuAuth
	 * @param request
	 * @param mapParam
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 2. 
	 * @Method설명 : 사용자별 메뉴 권한 삭제 (사용자아이디 기준)
	 */
	public void deleteAllUserMenuAuth(HttpServletRequest request, Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectUserAuthList
	 * @param request
	 * @param dataRequest
	 * @param dataMapId		Parameter 데이터맵 ID (기본값: dmSearch)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 24. 
	 * @Method설명 : 사용자별 메뉴 권한 목록 조회
	 */
	List<Map<String, Object>> selectUserAuthList(HttpServletRequest request, DataRequest dataRequest, String dataMapId) throws Exception;
	
	/**
	 * @Method명   : selectUserAuthList
	 * @param request
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 24. 
	 * @Method설명 : 사용자별 메뉴 권한 목록 조회
	 */
	List<Map<String, Object>> selectUserAuthList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception;
}

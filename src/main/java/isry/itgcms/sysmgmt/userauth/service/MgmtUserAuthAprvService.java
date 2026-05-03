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

/**
 * @파일명        : MgmtUserAuthAprvService.java
 * @프로그램 설명 : 사용자 권한 승인 관리
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2023. 2. 21. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2023. 2. 21.
 * @수정내용      : 
 * -                
 * -                
 */
public interface MgmtUserAuthAprvService {
	
	/**
	 * @Method명   : selectAprvAdminInstList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 8. 
	 * @Method설명 : 승인관리자 기관 목록 조회
	 */
	List<Map<String, Object>> selectAprvAdminInstList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectUserAuthAprvList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 사용자별 권한 승인 목록 조회
	 */
	List<Map<String, Object>> selectUserAuthAprvList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectUserAuthAprvDetails
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 22. 
	 * @Method설명 : 사용자별 권한 승인 상세 조회
	 */
	List<Map<String, Object>> selectUserAuthAprvDetails(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : processUserAuthAplyByReject
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 23. 
	 * @Method설명 : 사용자별 권한 승인 반려 처리
	 */
	Map<String, Object> processUserAuthAplyByReject(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : processUserAuthAplyByApproval
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 23. 
	 * @Method설명 : 사용자별 권한 승인 처리
	 */
	Map<String, Object> processUserAuthAplyByApproval(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : processUserAuthAplyByCancel
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 6. 
	 * @Method설명 : 사용자별 권한 신청 취소 처리
	 */
	Map<String, Object> processUserAuthAplyByCancel(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}

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
 * @파일명        : UserAuthAplyService.java
 * @프로그램 설명 : 사용자 권한 신청
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2023. 2. 20. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2023. 2. 20.
 * @수정내용      : 
 * -                
 * -                
 */
public interface UserAuthAplyService {

	/**
	 * @Method명   : selectAplyInstList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 20. 
	 * @Method설명 : 신청 기관 목록 조회 (콤보박스)
	 */
	List<Map<String, Object>> selectAplyInstList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectComboDataList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 콤보박스 데이터 조회
	 */
	List<Map<String, Object>> selectComboDataList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : saveUserAuthAply
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 2. 20. 
	 * @Method설명 : 사용자별 권한 신청 저장
	 */
	Map<String, Object> saveUserAuthAply(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}

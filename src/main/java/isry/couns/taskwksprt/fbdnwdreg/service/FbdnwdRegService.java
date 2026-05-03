/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.fbdnwdreg.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : FbdnwdRegService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : 박찬호¸
 * @작성일        : 2022. 5. 19. 
 * @수정자        : 박찬호¸
 * @수정일        : 2022. 5. 19.
 * @수정내용      : 
 * -                
 * -                
 */
public interface FbdnwdRegService {
	/**
	 * @Method명   : selectFbdnwdRegList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : 박찬호
	 * @작성일     : 2022. 5. 19. 
	 * @Method설명 : 금칙어 목록 조회
	 */
	List<Map<String, Object>> selectFbdnwdRegList(Map<String, Object> mapParam) throws Exception;	
	
	 /**
	 * @Method명   : saveFbdnwdReg
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : 박찬호
	 * @작성일     : 2022. 5. 19. 
	 * @Method설명 : 금칙어 등록 및 삭제
	 */
	Map<String, Object> saveFbdnwdReg(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}

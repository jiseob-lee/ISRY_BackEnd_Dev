/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.crtrinfo.resrce.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;
import com.clipsoft.org.apache.http.HttpRequest;

/**
 * @파일명        : ProgramExcnMngService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 8. 5. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 8. 5.
 * @수정내용      : 
 * -                
 * -                
 */
public interface ProgramExcnMngService {
	
	
	/**
	 * @Method명   : selectProgramExcnMngList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 5. 
	 * @Method설명 : 프로그램 실행관리 목록 조회
	 */
	public List<Map<String, Object>> selectProgramExcnMngList (HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : saveResrceProgrmExcnHrList
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 5. 
	 * @Method설명 : 자원프로그램실행시간 저장
	 */
	public void saveResrceProgrmExcnHrList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}

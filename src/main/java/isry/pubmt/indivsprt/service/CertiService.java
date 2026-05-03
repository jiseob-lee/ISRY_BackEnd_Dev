/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubmt.indivsprt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : CertiService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.seong.gyu
 * @작성일        : 2022. 02. 24.
 * @수정자        : 
 * @수정일        : 
 * @수정내용      : 
 * -                
 * -                
 */
public interface CertiService {

	/**
	 * @Method명   : selectCertiList
	 * @return
     * @작성자     : Kim.seong.gyu
     * @작성일     : 2022. 02. 24.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCertiList(Map<String, String> mapParam) throws Exception;
	
	List<Map<String, Object>> selectCertiColList(Map<String, String> mapParam) throws Exception;
	
	List<Map<String, Object>> selectCertiColDataList(Map<String, String> mapParam) throws Exception;
	
	

	/**
	 * @Method명   : saveCertiList
	 * @param dataRequest
     * @작성자     : Kim.seong.gyu
     * @작성일     : 2022. 02. 24. 
	 * @Method설명 :
	 */
	 Map<String, Object> saveCertiList(HttpServletRequest request, DataRequest dataRequest);

	/**
	 * @Method명   : selectSysDate
	 * @return
	 * @throws Exception 
     * @작성자     : Kim.seong.gyu
     * @작성일     : 2022. 02. 24.
	 * @Method설명 :
	 */
	String selectSysDate() throws Exception;

		
}

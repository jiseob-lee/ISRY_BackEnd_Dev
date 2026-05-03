/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.sample.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;


/**
 * @파일명        : SrvcResrceMngService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : You Minsang
 * @작성일        : 2022. 4. 29. 
 * @수정자        : You Minsang
 * @수정일        : 2022. 4. 29.
 * @수정내용      : 
 * -                
 * -                
 */
public interface SrvcResrceMngService {

	/**
	 * @Method명   : selectSrvcResrceMngList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 4. 29. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSrvcResrceMngList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : saveSrvcResrceMngList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 4. 
	 * @Method설명 :
	 */
	Map<String, Object> saveSrvcResrceMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectSrvcResrceDtlMngList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 4. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSrvcResrceDtlMngList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectSrvcResrceDtlProgramList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 9. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSrvcResrceDtlProgramList(Map<String, Object> mapParam) throws Exception;


}

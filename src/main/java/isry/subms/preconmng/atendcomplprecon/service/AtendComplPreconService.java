/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.atendcomplprecon.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : AtendComplPreconService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2023. 6. 26. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2023. 6. 26.
 * @수정내용      : 
 * -                
 * -                
 */
public interface AtendComplPreconService {

	/**
	 * @Method명   : selectAtendComplPreconList
	 * @param dataRequest
	 * @return
	 * @throws Exception 
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 6. 26. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectAtendComplPreconList(DataRequest dataRequest, HttpServletRequest request) throws Exception;

	/**
	 * @Method명   : selectAtendComplPreconMngList
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 6. 29. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectAtendComplPreconMngList(DataRequest dataRequest);

	/**
	 * @Method명   : saveAtendComplPreconMng
	 * @param dataRequest
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 6. 30. 
	 * @Method설명 :
	 */
	void saveAtendComplPreconMng(DataRequest dataRequest);

	/**
	 * @Method명   : saveAtendComplPreconList
	 * @param dataRequest
	 * @return
	 * @throws Exception 
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 7. 3. 
	 * @Method설명 :
	 */
	void saveAtendComplPreconList(DataRequest dataRequest) throws Exception;

}

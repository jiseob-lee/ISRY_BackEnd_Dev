/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.dyncBrd.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : DyncCreateBrdService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : You Minsang
 * @작성일        : 2021. 12. 20. 
 * @수정자        : You Minsang
 * @수정일        : 2021. 12. 20.
 * @수정내용      : 
 * -                
 * -                
 */
public interface DyncCreateBrdService {
	
	/**
	 * @Method명   : selectRootMenuList
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 4. 5. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectRootMenuList() throws Exception;
	
	/**
	 * @Method명   : selectCreateBoardList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 2. 3. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCreateBoardList(Map<String, String> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectCreateBoardColList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 2. 4. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCreateBoardColList(Map<String, String> mapParam) throws Exception;	
	
	/**
	 * @param request 
	 * @Method명   : saveCreateBoardList
	 * @param dataRequest
	 * @return 
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 2. 3. 
	 * @Method설명 :
	 */
	Map<String, Object> saveCreateBoardList(HttpServletRequest request, DataRequest dataRequest);

	/**
	 * @Method명   : selectBoardProgramInfo
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 4. 7. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectBoardProgramInfo(Map<String, String> mapParam) throws Exception;

	/**
	 * @Method명   : getCmmnsCdTotalCount
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 6. 17. 
	 * @Method설명 :
	 */
	int getCmmnsCdTotalCount(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectCreateBoardcmmnsCdList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 6. 17. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCreateBoardcmmnsCdList(Map<String, Object> mapParam) throws Exception;
		
}

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
 * @파일명        : TstBoardDevService.java
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
public interface NoticeBoardService {
		 
	/**
	 * @Method명   : getTotalCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 :
	 */
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	
	/**
	 * @Method명   : selectNoticeBoardList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectNoticeBoardList(Map<String, Object> mapParam) throws Exception;	
	
	 /**
	 * @Method명   : saveNoticeBoardList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 :
	 */
	Map<String, Object> saveNoticeBoardList(HttpServletRequest request, DataRequest dataRequest) throws Exception;


	/**
	 * @Method명   : selectNoticeBoardDtlList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectNoticeBoardDtlList(Map<String, Object> mapParam) throws Exception;


	/**
	 * @Method명   : updateNoticeBoardDtlList
	 * @param mapParam
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 3. 
	 * @Method설명 :
	 */
	void updateNoticeBoardDtlList(Map<String, Object> mapParam) throws Exception;

		
}

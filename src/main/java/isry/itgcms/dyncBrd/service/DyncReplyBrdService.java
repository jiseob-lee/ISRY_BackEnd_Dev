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
 * @파일명        : DyncReplyBrdService.java
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
public interface DyncReplyBrdService {

	List<Map<String, Object>> selectDynamicReplyBoardList(Map<String, Object> mapParam) throws Exception;
		
	/**
	 * @param request 
	 * @Method명   : saveBoardList
	 * @param dataRequest
	 * @작성자     : You Minsang
	 * @작성일     : 2021. 12. 21. 
	 * @Method설명 :
	 */
	 Map<String, Object> saveDynamicReplyBoardList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	
	/**
	 * @Method명   : deleteDynamicReplyBoardList
	 * @param request
	 * @param dataRequest
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 6. 13. 
	 * @Method설명 :
	 */
	void deleteMstDynamicReplyBoardList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectDynamicReplyBoardCommentList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 6. 15. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDynamicReplyBoardReplyList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectDynamicReplyBoardDtlList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 27. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDynamicReplyBoardDtlList(Map<String, Object> mapParam) throws Exception;
	
}

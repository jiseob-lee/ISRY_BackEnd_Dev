/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.itgBrd.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;


/**
 * @파일명        : ItgBrdCmnCmntService.java
 * @프로그램 설명    : 통합 게시판 공통 댓글 서비스
 * - 
 * - 
 * @작성자        : You Minsang
 * @작성일        : 2022. 6. 30. 
 * @수정자        : You Minsang
 * @수정일        : 2022. 6. 30.
 * @수정내용      : 
 * -                
 * -                
 */
public interface ItgBrdCmnCmntService {

	/**
	 * @Method명   : selectItgBrdCmntList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 21. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectItgBrdCmntList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : saveItgBrdCmntList
	 * @param request
	 * @param dataRequest
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 21. 
	 * @Method설명 :
	 */
	void saveItgBrdCmntList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectSysItgBrdCmntList
	 * @param mapParam
	 * @return
	 * @작성자     : Lee Seoungjae
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 시스템문의사항 - 원본 selectItgBrdCmntList
	 */
	List<Map<String, Object>> selectSysItgBrdCmntList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : saveSysItgBrdCmntList
	 * @param request
	 * @param dataRequest
	 * @작성자     : Lee Seoungjae
	 * @작성일     : 2023. 2. 21. 
	 * @Method설명 : 시스템문의사항 - 원본 saveItgBrdCmntList
	 */
	void saveSysItgBrdCmntList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
		
}

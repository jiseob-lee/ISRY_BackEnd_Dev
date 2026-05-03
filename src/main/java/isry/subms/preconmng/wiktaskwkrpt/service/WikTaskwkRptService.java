/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.wiktaskwkrpt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : WikTaskWorkRptService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Seoung.Jae
 * @작성일        : 2022. 6. 10. 
 * @수정자        : Lee.Seoung.Jae
 * @수정일        : 2022. 6. 10.
 * @수정내용      : 
 * -                
 * -                
 */
public interface WikTaskwkRptService {
	
	/**
	 * 
	 * @Method명   : selectWikTaskwkList
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 20. 
	 * @Method설명 : 주간업무보고 목록조회
	 */
	public List<Map<String, String>> selectWikTaskwkList(DataRequest dataRequest) throws Exception;
	
	/**
	 * 
	 * @Method명   : saveWikTaskwkRpt
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 20. 
	 * @Method설명 : 주간업무보고 등록/수정
	 */
	public Map<String, Object> saveWikTaskwkRpt(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectWikTaskwk
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 6. 14. 
	 * @Method설명 : 상세조회
	 */
	public Map<String, List<Map<String, Object>>> selectWikTaskwk(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectWikTaskwkSearch
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Seoung.Jae
	 * @작성일     : 2022. 11. 1. 
	 * @Method설명 :
	 */
	Map<String, List<Map<String, Object>>> selectWikTaskwkSearch(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}

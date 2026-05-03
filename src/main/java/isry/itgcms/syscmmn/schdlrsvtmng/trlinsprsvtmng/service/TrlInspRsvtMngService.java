/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.schdlrsvtmng.trlinsprsvtmng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : TrlInspRsvtMngService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 7. 6. 
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 7. 6.
 * @수정내용      : 
 * -                
 * -                
 */
public interface TrlInspRsvtMngService {
	
	
	
	/**
	 * @Method명   : selectTaskwkSeCd
	 * @param requestMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 6. 27. 
	 * @Method설명 :
	 */
	public String selectTaskwkSeCd(Map<String, Object> requestMap) throws Exception;

	

	/**
	 * @Method명   : trlInspRsvtMngDetail
	 * @param request
	 * @param dataRequest
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 12. 
	 * @Method설명 :
	 */
	public void trlInspRsvtMngDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;



	/**
	 * @Method명   : getTrlInspRsvtMngListTotalCount
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 13. 
	 * @Method설명 :
	 */
	public int getTrlInspRsvtMngListTotalCount(Map<String, Object> mapParam) throws Exception;



	/**
	 * @Method명   : selectTrlInspRsvtMngList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 13. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectTrlInspRsvtMngList(Map<String, Object> mapParam) throws Exception;



	/**
	 * @param request 
	 * @Method명   : selectTrlInspRsvtMngDetail
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 14. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectTrlInspRsvtMngDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;



	/**
	 * @Method명   : selectChcTrlInsp
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 14. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectChcTrlInsp(DataRequest dataRequest) throws Exception;



	/**
	 * @Method명   : getSelectDateTrlInspRsvtMngListCount
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 :
	 */
	public int getSelectDateTrlInspRsvtMngListCount(Map<String, Object> mapParam) throws Exception;



	/**
	 * @Method명   : selectDateTrlInspRsvtMngList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectDateTrlInspRsvtMngList(Map<String, Object> mapParam) throws Exception;



	/**
	 * @Method명   : getTrlInspRsvtMngDailyListTotalCount
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 :
	 */
	public int getTrlInspRsvtMngDailyListTotalCount(Map<String, Object> mapParam) throws Exception;



	/**
	 * @Method명   : selectTrlInspRsvtMngDailyList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectTrlInspRsvtMngDailyList(Map<String, Object> mapParam) throws Exception;




}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.schdlrsvtmng.cscaltmnt.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : CscAltmntService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 7. 22. 
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 7. 22.
 * @수정내용      : 
 * -                
 * -                
 */
public interface CscAltmntService {

	/**
	 * @Method명   : selectTaskwkSeCd
	 * @param requestMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 22. 
	 * @Method설명 :
	 */
	String selectTaskwkSeCd(Map<String, Object> requestMap) throws Exception;

	/**
	 * @Method명   : getCscListTotalCount
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 :
	 */
	int getCscListTotalCount(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectCscList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCscList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectRsvctmList
	 * @param mapDate
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectRsvctmList(Map<String, Object> mapDate) throws Exception;

	/**
	 * @Method명   : saveCscDetail
	 * @param request
	 * @param dataRequest
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 27. 
	 * @Method설명 :
	 */
	void saveCscDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectCscDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCscDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectCscListUseY
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCscListUseY(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : saveCscAltmntDetail
	 * @param request
	 * @param dataRequest
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 29. 
	 * @Method설명 :
	 */
	void saveCscAltmntDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	

	

	/**
	 * @Method명   : getCscAltmntRsvtListTotalCount
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 29. 
	 * @Method설명 :
	 */
	int getCscAltmntRsvtListTotalCount(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : checkRsvtHrDpcn
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 29. 
	 * @Method설명 :
	 */
	String checkRsvtHrDpcn(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	

	/**
	 * @Method명   : selectCscAltmntPreconList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 1. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCscAltmntPreconList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectCcAltmntRsvtList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 3. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCscAltmntRsvtList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectedCscAltmntRsvtSearchList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 5. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectedCscAltmntRsvtSearchList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectedCscAltmntPreconSearchList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 5. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectedCscAltmntPreconSearchList(Map<String, Object> mapParam) throws Exception;

	

	/**
	 * @Method명   : selectDateWeeklyList
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 8. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDateWeeklyList(DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectCscAltmntDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 9. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCscAltmntDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	

}

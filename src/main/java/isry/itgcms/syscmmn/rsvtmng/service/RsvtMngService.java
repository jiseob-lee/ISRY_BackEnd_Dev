/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.rsvtmng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : RsvtMngService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Seong.Ok
 * @작성일        : 2022. 8. 29. 
 * @수정자        : Kim.Seong.Ok
 * @수정일        : 2022. 8. 29.
 * @수정내용      : 
 * -                
 * -                
 */
public interface RsvtMngService {

	/**
	 * @Method명   : selectTaskwkSeCd
	 * @param requestMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 29. 
	 * @Method설명 : 단위업무구분 코드 조회 TASKWK_SYS_SE_CD
	 */
	String selectTaskwkSeCd(Map<String, Object> requestMap) throws Exception;

	/**
	 * @Method명   : getResrceClMngListTotalCount
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 29. 
	 * @Method설명 :
	 */
	int getResrceClMngListTotalCount(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectResrceClMngList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 29. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectResrceClMngList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : saveResrceClMngDtl
	 * @param request
	 * @param dataRequest
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 :
	 */
	void saveResrceClMngDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectResrceClMngDtl
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 1. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectResrceClMngDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectResrceClMngUseYList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 2. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectResrceClMngUseYlist(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectResrceNmDpcnChkList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 5. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectResrceNmDpcnChkList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : resrceNmDpcnChk
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 5. 
	 * @Method설명 :
	 */
	Map<String, Object> resrceNmDpcnChk(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	

	/**
	 * @Method명   : resrceRsvtDpcnChk
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 7. 
	 * @Method설명 :
	 */
	String resrceRsvtDpcnChk(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : saveResrceRsvtDtl
	 * @param request
	 * @param dataRequest
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 7. 
	 * @Method설명 :
	 */
	void saveResrceRsvtDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectDailyRsvtPreconList
	 * @param mapParam
	 * @return throws Exception;
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 14. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDailyRsvtPreconList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectWeeklyRsvtPreconList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 14. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectWeeklyRsvtPreconList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectRsvtPreconList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectRsvtPreconList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectRsvtAltmntPreconList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectRsvtAltmntPreconList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectResrceRsvtDtl
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectResrceRsvtDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectTrprPtcptnPsbltyYlist
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 2. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectTrprPtcptnPsbltyYlist(HttpServletRequest request, DataRequest dataRequest) throws Exception;
		
	/**
	 * @Method명   : selectFcltyThngList
	 * @param mapParam
	 * @return throws Exception;
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 03. 
	 * @Method설명 : 시설 및 물품목록조회
	 */
	public List<Map<String, Object>> selectFcltyThngList(String codeId,String userId,String instNo) throws Exception;

	/**
	 * @Method명   : saveSchdlRsvtDtl
	 * @param request
	 * @param dataRequest
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 4. 
	 * @Method설명 :
	 */
	void saveSchdlRsvtDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	

	/**
	 * @Method명   : selectSchdlRsvtList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 8.  throws Exception;
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSchdlRsvtList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectSchdlRsvtTrprDtl
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 9. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSchdlRsvtTrprDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectSchdlRsvtDtl
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 9. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSchdlRsvtDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectSchdlRsvtPicDtl
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 9. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSchdlRsvtPicDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : cancleSchdlRsvtDtl
	 * @param request
	 * @param dataRequest
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 10. 
	 * @Method설명 :
	 */
	void cancleSchdlRsvtDtl(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectDailyList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDailyList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectedMonthsRsvtCnt
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 15. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectedMonthsRsvtCnt(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectDailyPopUpList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 4. 26. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDailyPopUpList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	

}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry2.itgcms.syscmmn.rsvtmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : RsvtMngMapper.java
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
@Mapper("rsvtMng2Mapper")
public interface RsvtMng2Mapper {

	/**
	 * @Method명   : selectTaskwkSeCd
	 * @param requestMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 29. 
	 * @Method설명 : 단위업무구분 코드 조회 TASKWK_SYS_SE_CD
	 */
	String selectTaskwkSeCd(Map<String, Object> requestMap);

	/**
	 * @Method명   : insertResrceClMngDtl
	 * @param mapIns
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 :
	 */
	void insertResrceClMngDtl(Map<String, String> mapIns);

	/**
	 * @Method명   : updateResrceClMngDtl
	 * @param mapUpd
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 :
	 */
	void updateResrceClMngDtl(Map<String, String> mapUpd);

	/**
	 * @Method명   : deleteResrceClMngDtl
	 * @param mapDel
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 31. 
	 * @Method설명 :
	 */
	void deleteResrceClMngDtl(Map<String, String> mapDel);

	/**
	 * @Method명   : getResrceClMngListTotalCount
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 1. 
	 * @Method설명 :
	 */
	int getResrceClMngListTotalCount(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectResrceClMngList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 1. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectResrceClMngList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectResrceClMngDtl
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 1. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectResrceClMngDtl(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectResrceClMngUseYlist
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 2. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectResrceClMngUseYlist(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectResrceNmDpcnCheckList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 5. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectResrceNmDpcnChkList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectResrceNmDpcnCheckItypeList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 5. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectResrceNmDpcnInsertTypeChkList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectResrceNmDpcnCheckUtypeList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 5. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectResrceNmDpcnUpdateTypeChkList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectResrceRsvtDpcnInsertTypeCheckList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 7. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectResrceRsvtDpcnInsertTypeCheckList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectResrceRsvtDpcnUpdateTypeCheckList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 7. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectResrceRsvtDpcnUpdateTypeCheckList(Map<String, Object> mapParam);

	/**
	 * @Method명   : insertResrceRsvtDtl
	 * @param mapIns
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 7. 
	 * @Method설명 :
	 */
	void insertResrceRsvtDtl(Map<String, String> mapIns);

	/**
	 * @Method명   : selectDailyRsvtPreconList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 14. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDailyRsvtPreconList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectDayOfWeek
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 14. 
	 * @Method설명 :
	 */
	int selectDayOfWeek(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectWeeklyRsvtPreconList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 14. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectWeeklyRsvtPreconList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectRsvtPreconList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectRsvtPreconList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectResrceRsvtDtl
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 15. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectResrceRsvtDtl(Map<String, Object> mapParam);

	/**
	 * @Method명   : updateResrceRsvtDtl
	 * @param mapUpd
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 16. 
	 * @Method설명 :
	 */
	void updateResrceRsvtDtl(Map<String, String> mapUpd);

	/**
	 * @Method명   : deleteResrceRsvtDtl
	 * @param mapDel
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 9. 16. 
	 * @Method설명 :
	 */
	void deleteResrceRsvtDtl(Map<String, String> mapDel);

	/**
	 * @Method명   : selectTrprPtcptnPsbltyYlist
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 2. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectTrprPtcptnPsbltyYlist(Map<String, Object> mapParam);
	
	/**
	 * @Method명   : selectFcltyThngList
	 * @param stiring
	 * @return 
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 03. 
	 * @Method설명 : 시설 및 물품목록조회
	 */
	public List<Map<String, Object>> selectFcltyThngList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : insertMmsContentsInfo
	 * @param map
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 :
	 */
	void insertMmsContentsInfo(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : insertMsgData
	 * @param map
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 :
	 */
	void insertMsgData(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : insertSchdlRsvtTrpr
	 * @param mapTrpr
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 :
	 */
	void insertSchdlRsvtTrpr(Map<String, String> mapTrpr) throws Exception;

	/**
	 * @Method명   : insertSchdlRsvtPic
	 * @param mapPic
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 :
	 */
	void insertSchdlRsvtPic(Map<String, String> mapPic) throws Exception;

	/**
	 * @Method명   : insertSchdlRsvtDtl
	 * @param mapRsvtInfo
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 :
	 */
	void insertSchdlRsvtDtl(Map<String, String> mapRsvtInfo) throws Exception;

	/**
	 * @Method명   : selectSchdlRsvtList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 8. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSchdlRsvtList(Map<String, String> mapParam) throws Exception;

	/**
	 * @Method명   : selectSchdlRsvtTrprDtlList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 9. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSchdlRsvtTrprDtlList(Map<String, String> mapParam) throws Exception;

	/**
	 * @Method명   : selectSchdlRsvtDtl
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 9. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSchdlRsvtDtl(Map<String, String> mapParam) throws Exception;

	/**
	 * @Method명   : selectSchdlRsvtPicDtl
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 9. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSchdlRsvtPicDtl(Map<String, String> mapParam) throws Exception;

	/**
	 * @Method명   : updateCancleSchdlRsvtDtl
	 * @param mapRsvtInfo
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 10. 
	 * @Method설명 :
	 */
	void updateCancleSchdlRsvtDtl(Map<String, String> mapRsvtInfo) throws Exception;

	/**
	 * @Method명   : updateSchdlRsvtTrpr
	 * @param mapTrpr
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 10. 
	 * @Method설명 :
	 */
	void updateSchdlRsvtTrpr(Map<String, String> mapTrpr) throws Exception;

	/**
	 * @Method명   : updateSchdlRsvtPic
	 * @param mapPic
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 10. 
	 * @Method설명 :
	 */
	void updateSchdlRsvtPic(Map<String, String> mapPic) throws Exception;

	/**
	 * @Method명   : selectDailyList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDailyList(Map<String, String> mapParam) throws Exception;

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
	 * @Method명   : selectRprsTelno
	 * @param dmDtlParamMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 12. 21. 
	 * @Method설명 : throws Exception;
	 */
	String selectRprsTelno(Map<String, String> dmDtlParamMap) throws Exception;

	

}

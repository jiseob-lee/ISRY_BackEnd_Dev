/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.schdlrsvtmng.cscaltmnt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : CscAltmntMapper.java
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
@Mapper("cscAltmntMapper")
public interface CscAltmntMapper {

	/**
	 * @Method명   : selectTaskwkSeCd
	 * @param requestMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 22. 
	 * @Method설명 :
	 */
	String selectTaskwkSeCd(Map<String, Object> requestMap);

	/**
	 * @Method명   : getCscListTotalCount
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 :
	 */
	int getCscListTotalCount(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectCscList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCscList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectRsvctmList
	 * @param mapDate
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 25. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectRsvctmList(Map<String, Object> mapDate);

	/**
	 * @Method명   : insertCscDetail
	 * @param mapIns
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 :
	 */
	void insertCscDetail(Map<String, String> mapIns);

	/**
	 * @Method명   : selectCscDetail
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCscDetail(Map<String, Object> mapParam);

	/**
	 * @Method명   : updateCscDetail
	 * @param mapUpd
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 :
	 */
	void updateCscDetail(Map<String, String> mapUpd);

	/**
	 * @Method명   : selectCscListUseY
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCscListUseY(Map<String, Object> mapParam);

	/**
	 * @Method명   : deleteCscDetail
	 * @param mapDelselectCscListUseYn
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 28. 
	 * @Method설명 :
	 */
	void deleteCscDetail(Map<String, String> mapDel);

	/**
	 * @Method명   : getCscAltmntRsvtListTotalCount
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 29. 
	 * @Method설명 :
	 */
	int getCscAltmntRsvtListTotalCount(Map<String, Object> mapParam);

	

	/**
	 * @Method명   : insertCscAltmntDetail
	 * @param mapIns
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 29. 
	 * @Method설명 :
	 */
	void insertCscAltmntDetail(Map<String, String> mapIns);

	/**
	 * @Method명   : selectCscAltmntRsvtList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 1. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCscAltmntRsvtList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectedCscAltmntRsvtSearchList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 5. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectedCscAltmntRsvtSearchList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectDayOfWeek
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 8. 
	 * @Method설명 :
	 */
	int selectDayOfWeek(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectDateWeeklyList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 8. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDateWeeklyList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectCscAltmntDetail
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 9. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCscAltmntDetail(Map<String, Object> mapParam);

	/**
	 * @Method명   : updateCscAltmntDetail
	 * @param mapUpd
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 9. 
	 * @Method설명 :
	 */
	void updateCscAltmntDetail(Map<String, String> mapUpd);

	/**
	 * @Method명   : getAltmntRsvtDpcnItypeList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 11. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> getAltmntRsvtDpcnItypeList(Map<String, Object> mapParam);
	
	
	/**
	 * @Method명   : getAltmntRsvtDpcnUtypeList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 29. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> getAltmntRsvtDpcnUtypeList(Map<String, Object> mapParam);

	/**
	 * @Method명   : deleteCscAltmntDetail
	 * @param mapDel
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 8. 11. 
	 * @Method설명 :
	 */
	void deleteCscAltmntDetail(Map<String, String> mapDel);

}

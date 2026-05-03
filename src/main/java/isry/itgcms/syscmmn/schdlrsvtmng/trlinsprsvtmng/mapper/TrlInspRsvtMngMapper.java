/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.schdlrsvtmng.trlinsprsvtmng.mapper;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : TrlInspRsvtMngMapper.java
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
@Mapper("trlInspRsvtMngMapper")
public interface TrlInspRsvtMngMapper {

	/**
	 * @Method명   : selectTaskwkSeCd
	 * @param requestMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 6. 
	 * @Method설명 :
	 */
	String selectTaskwkSeCd(Map<String, Object> requestMap);

	/**
	 * @Method명   : selectUserInfo
	 * @param loginMap
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 11. 
	 * @Method설명 :
	 */
	Map<String, String> selectUserInfo(Map<String, Object> loginMap);

	/**
	 * @Method명   : insertTrlInspRsvtMngDetail
	 * @param mapIns
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 13. 
	 * @Method설명 :
	 */
	void insertTrlInspRsvtMngDetail(Map<String, String> mapIns);

	/**
	 * @Method명   : insertChcTrlInsp
	 * @param mapIns
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 13. 
	 * @Method설명 :
	 */
	void insertChcTrlInsp(Map<String, String> mapIns);

	/**
	 * @Method명   : getTrlInspRsvtMngListTotalCount
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 13. 
	 * @Method설명 :
	 */
	int getTrlInspRsvtMngListTotalCount(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectTrlInspRsvtMngList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 13. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectTrlInspRsvtMngList(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectChcTrlInspList
	 * @param object
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 13. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectChcTrlInspList(Object object);

	/**
	 * @Method명   : selectTrlInspRsvtMngDetail
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 14. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectTrlInspRsvtMngDetail(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectChcTrlInsp
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 14. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectChcTrlInsp(Map<String, Object> mapParam);

	/**
	 * @Method명   : deleteChcTrlInsp
	 * @param mapParam
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 :
	 */
	void deleteChcTrlInsp(Map<String, Object> mapParam);

	/**
	 * @Method명   : updateTrlInspRsvtMngDetail
	 * @param mapUpd
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 :
	 */
	void updateTrlInspRsvtMngDetail(Map<String, String> mapUpd);

	/**
	 * @Method명   : deleteTrlInspRsvtMngDetail
	 * @param mapDel
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 :
	 */
	void deleteTrlInspRsvtMngDetail(Map<String, String> mapDel);

	/**
	 * @Method명   : getTrlInspRsvtMngDailyListTotalCount
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 :
	 */
	int getTrlInspRsvtMngDailyListTotalCount(Map<String, Object> mapParam);

	/**
	 * @Method명   : selectTrlInspRsvtMngDailyList
	 * @param mapParam
	 * @return
	 * @작성자     : Kim.Seong.Ok
	 * @작성일     : 2022. 7. 19. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectTrlInspRsvtMngDailyList(Map<String, Object> mapParam);

	

}

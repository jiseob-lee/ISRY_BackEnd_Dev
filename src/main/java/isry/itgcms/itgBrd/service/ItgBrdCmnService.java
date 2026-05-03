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
 * @파일명 : itgBrdCmnService.java
 * @프로그램 설명 : - -
 * @작성자 : You Minsang
 * @작성일 : 2022. 6. 30.
 * @수정자 : You Minsang
 * @수정일 : 2022. 6. 30.
 * @수정내용 : - -
 */
public interface ItgBrdCmnService {

	/**
	 * @Method명 : selectInstCodeList
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 5.
	 * @Method설명 :
	 */
//	List<Map<String, Object>> selectInstCodeList() throws Exception;

	/**
	 * @Method명 : selectInstCodeList
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 5.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectInstCode(Integer instNo) throws Exception;

	/**
	 * @Method명 : selectDeptCodeList
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDeptCodeList() throws Exception;

	/**
	 * @Method명 : getTotalCount
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 :
	 */
	int getTotalCount(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : selectItgNtcBrdList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectItgCmnBrdList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : selectCtgrySeCdList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCtgrySeCdList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : saveItgBrdCmnList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 :
	 */
	Map<String, Object> saveItgBrdCmnList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : selectItgBrdDtlList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 5.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectItgBrdDtlList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : deleteItgNtcBrd
	 * @param request
	 * @param dataRequest
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 6.
	 * @Method설명 :
	 */
	void deleteItgNtcBrd(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명 : selectItgBrdDtlTaskSysCdList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectItgBrdDtlTaskSysCdList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : selectAllCtgrySeCdList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectAllCtgrySeCdList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : selectItgCmnCtgtybInstList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 8.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCtgtybInstList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : selectItgCmnBrdImprtnList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectItgCmnBrdImprtnList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : selectCtpvCodeList
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCtpvCodeList() throws Exception;

	/**
	 * @Method명 : selectSggCodeList
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 11.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSggCodeList() throws Exception;
	
	/**
	 * @Method명 : selectSysItgBrdDtlList
	 * @param mapParam
	 * @return
	 * @작성자 : Lee Seoungjae
	 * @작성일 : 2023. 2. 21.
	 * @Method설명 : 시스템문의사항 관련 - 원본 selectItgBrdDtlList
	 */
	List<Map<String, Object>> selectSysItgBrdDtlList(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명 : selectSysItgBrdDtlTaskSysCdList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 7.
	 * @Method설명 : 시스템문의사항 관련 - 원본 selectItgBrdDtlTaskSysCdList
	 */
	List<Map<String, Object>> selectSysItgBrdDtlTaskSysCdList(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명 : saveSysItgBrdCmnList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 4.
	 * @Method설명 : 시스템문의사항 관련 - 원본 saveItgBrdCmnList
	 */
	Map<String, Object> saveSysItgBrdCmnList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectSysCtgrySeCdList
	 * @param mapParam
	 * @return
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 4. 3. 
	 * @Method설명 : 시스템문의사항 관련 - 원본 selectCtgrySeCdList
	 */
	List<Map<String, Object>> selectSysCtgrySeCdList(Map<String, Object> mapParam) throws Exception;
}

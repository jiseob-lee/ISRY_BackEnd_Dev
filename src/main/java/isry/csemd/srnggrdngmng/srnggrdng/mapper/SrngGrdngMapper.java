/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.srnggrdngmng.srnggrdng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : SrngGrdngMapper.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2022. 10. 4.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2022. 10. 4.
 * @수정내용 : - -
 */
@Mapper("csemdSrngGrdngMapper")
public interface SrngGrdngMapper {

	/**
	 * @Method명 : selectAplyRcptCd
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectAplyRcptCd();

	/**
	 * @Method명 : selectScrennList
	 * @param dtlMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectScrennList(Map<String, String> dtlMap) throws Exception;

	/**
	 * @Method명 : selectGrdngList
	 * @param dtlMap
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 :
	 */
	List<Map<String, String>> selectGrdngList(Map<String, String> dtlMap) throws Exception;

	/**
	 * @Method명 : updateSrngGrdngPop_Screnn
	 * @param map
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 :
	 */
	void updateSrngGrdngPopScrenn(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : insertSrngGrdngPopGrdng
	 * @param map
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 :
	 */
	void insertSrngGrdngPopGrdng(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : updateSrngGrdngPopGrdng
	 * @param map
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 10. 4.
	 * @Method설명 :
	 */
	void updateSrngGrdngPopGrdng(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : selectAFA100
	 * @param map
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 11. 4.
	 * @Method설명 :
	 */

	Map<String, String> selectAFA100(Map<String, String> map);

	/**
	 * @Method명 : selectScrenn
	 * @param paraMap
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 11. 4.
	 * @Method설명 : 면접심사채점표 스크리닝 폼 조회
	 */
	List<Map<String, Object>> selectScrenn(Map<String, String> paraMap) throws Exception;

	/**
	 * @Method명 : selectIntrvwSchdlList
	 * @param requestMap
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 4.
	 * @Method설명 : 면접일정관리 목록 조회
	 */
	List<Map<String, Object>> selectIntrvwSchdlList(Map<String, String> requestMap) throws Exception;

	/**
	 * @Method명 : chkIntrvwSchdlMng
	 * @param dmSearch
	 * @return
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 4.
	 * @Method설명 : 면접일정관리 존재여부 확인
	 */
	int chkIntrvwSchdlMng(Map<String, String> requestMap) throws Exception;

	/**
	 * @Method명 : insertIntrvwSchdlMng
	 * @param dmSearch
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 1. 4.
	 * @Method설명 : 면접일정관리 저장
	 */
	void insertIntrvwSchdlMng(Map<String, String> requestMap) throws Exception;

	/**
	 * @Method명   : updateIntrvwSchdlMng
	 * @param dmSearch
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 면접일정관리 수정
	 */
	void updateIntrvwSchdlMng(Map<String, String> requestMap) throws Exception;

	/**
	 * @Method명   : chkSavedIntrvwSchdl
	 * @param deletedDsList
	 * @return
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 저장된 일정 확인
	 */
	int chkSavedIntrvwSchdl(Map<String, String> requestMap) throws Exception;

	/**
	 * @Method명   : deleteIntrvwSchdlMng
	 * @param deletedDsList
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 면접일정관리 삭제
	 */
	void deleteIntrvwSchdlMng(Map<String, String> requestMap) throws Exception;

	/**
	 * @Method명   : selectIntrvwAplcntList
	 * @param dmTmpParam
	 * @return
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 면접참여자 조회
	 */
	List<Map<String, Object>> selectIntrvwAplcntList(Map<String, String> requestMap) throws Exception;

	/**
	 * @Method명   : updateAplyRcptIntrvwSchdl
	 * @param updatedDsList
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 신청접수 면접예약 수정
	 */
	void updateAplyRcptIntrvwSchdl(Map<String, String> requestMap) throws Exception;

}

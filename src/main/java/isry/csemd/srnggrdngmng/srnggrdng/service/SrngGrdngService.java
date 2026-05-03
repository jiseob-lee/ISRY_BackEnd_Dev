/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.srnggrdngmng.srnggrdng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : SrngGrdngService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2022. 10. 4. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2022. 10. 4.
 * @수정내용      : 
 * -                
 * -                
 */
public interface SrngGrdngService {
	
	/**
	 * @Method명   : selectAplyRcptCd
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 4. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectAplyRcptCd();

	/**
	 * @Method명   : selectScrennList
	 * @param request
	 * @param dataRequest 
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 4. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectScrennList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectGrdngList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 4. 
	 * @Method설명 :
	 */
	List<Map<String, String>> selectGrdngList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : saveSrngGrdngPop
	 * @param request
	 * @param dataRequest
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 10. 4. 
	 * @Method설명 :
	 */
	void saveSrngGrdngPop(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectScrenn
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception 
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2022. 11. 4. 
	 * @Method설명 : 면접심사채점표 스크리닝 폼 조회
	 */
	List<Map<String, Object>> selectScrenn(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectIntrvwSchdlList
	 * @param dmSearch
	 * @return
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 1. 4. 
	 * @Method설명 : 면접일정관리목록조회
	 */
	List<Map<String, Object>> selectIntrvwSchdlList(Map<String, String> requestMap) throws Exception;

	/**
	 * @Method명   : insertIntrvwSchdlMng
	 * @param dataRequest
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 1. 4. 
	 * @Method설명 : 면접일정관리 등록
	 */
	void insertIntrvwSchdlMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : updateIntrvwSchdlMng
	 * @param request
	 * @param dataRequest
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 면접일정관리 수정
	 */
	void updateIntrvwSchdlMng(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : deleteIntrvwSchdlMng
	 * @param dataRequest
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 면접일정관리 삭제
	 */
	void deleteIntrvwSchdlMng(DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectIntrvwAplcntList
	 * @param dataRequest
	 * @return
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 면접참여자 조회
	 */
	List<Map<String, Object>> selectIntrvwAplcntList(DataRequest dataRequest) throws Exception;

	

}

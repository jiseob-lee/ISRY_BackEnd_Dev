/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.taskwkandatdmng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface TaskwkReprtsService {
	
	/**
	 * 
	 * @Method명   : selectTaskwkReprtsList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 4. 
	 * @Method설명 : 업무보고서 목록 조회
	 */
	List<Map<String, Object>> selectTaskwkReprtsList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectTaskwkReprtsDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 4. 
	 * @Method설명 : 업무보고서 상세 조회
	 */
	List<Map<String, Object>> selectTaskwkReprtsDetail(DataRequest dataRequest) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectTaskwkReprtsDetailByMblaDscsn
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 2. 
	 * @Method설명 : 업무보고서 상세 조회 (모바일상담 - 수정시 조회)
	 */
	List<Map<String, Object>> selectTaskwkReprtsDetailByMblaDscsn(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectOvtimeAplyHistbDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 22. 
	 * @Method설명 : 시간외 근무 신청 목록 조회
	 */
	List<Map<String, Object>> selectOvtimeAplyHistbDetail(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectChttDscsnListByDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 29. 
	 * @Method설명 : 업무보고서 상세 > 채팅상담목록 조회
	 */
	List<Map<String, Object>> selectChttDscsnListByDetail(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectNtabrdDscsnListByDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 29. 
	 * @Method설명 : 업무보고서 상세 > 게시판상담목록 조회
	 */
	List<Map<String, Object>> selectNtabrdDscsnListByDetail(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectOutrcDscsnListByDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 30. 
	 * @Method설명 : 업무보고서 상세 > 아웃리치상담목록 조회
	 */
	List<Map<String, Object>> selectOutrcDscsnListByDetail(DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : selectChttDscsnHistbInqDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 25. 
	 * @Method설명 : 업무보고서 상세 > 채팅상담내역 조회
	 */
	List<Map<String, Object>> selectChttDscsnHistbInqDetail(DataRequest dataRequest) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectChttDscsnHistbList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 8. 
	 * @Method설명 : 업무보고서 등록 > 채팅상담목록 조회
	 */
	List<Map<String, Object>> selectChttDscsnHistbList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectNtabrdDscsnHistbInqDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 28. 
	 * @Method설명 : 업무보고서 상세 > 게시판상담내역 조회
	 */
	List<Map<String, Object>> selectNtabrdDscsnHistbInqDetail(DataRequest dataRequest) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectNtabrdDscsnHistList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 8. 
	 * @Method설명 : 업무보고서 등록 > 게시판상담목록 조회
	 */
	List<Map<String, Object>> selectNtabrdDscsnHistList(DataRequest dataRequest) throws Exception;
	
	List<Map<String, Object>> selectAYC260List(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectTaskwkReprtsRegData
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 7. 
	 * @Method설명 : 업무보고서 등록 관련 초기 데이터 조회
	 */
	Map<String, Object> selectTaskwkReprtsRegData(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : insertTaskwkReprts
	 * @param dataRequest
	 * @return	등록 처리 결과
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 9. 
	 * @Method설명 : 업무보고서 등록 처리
	 */
	Map<String, Object> insertTaskwkReprts(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * 
	 * @Method명   : updateTaskwkReprts
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 1.
	 * @Method설명 : 업무보고서 수정 처리
	 */
	Map<String, Object> updateTaskwkReprts(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * 
	 * @Method명   : deleteTaskwkReprts
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 4. 
	 * @Method설명 : 업무보고서 삭제 처리
	 */
	Map<String, Object> deleteTaskwkReprts(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : updateTaskwkReprtsByDetail
	 * @param dataRequest
	 * @return	수정 처리 결과
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 11. 23. 
	 * @Method설명 : 업무보고서 상세(팝업) 수정 처리
	 */
	Map<String, Object> updateTaskwkReprtsByDetail(DataRequest dataRequest) throws Exception;
}

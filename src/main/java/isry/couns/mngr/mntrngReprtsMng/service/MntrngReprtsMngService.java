/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.mntrngReprtsMng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : MntrngReprtsMngService.java
 * @프로그램 설명 : 모니터링 보고서
 * - 
 * - 
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 9. 28. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 9. 28. 
 * @수정내용      : 
 * -                
 * -                
 */

public interface MntrngReprtsMngService {

	// 모니터링 보고서 목록 조회
	public List<Map<String, Object>> selectMntrngReprtsList(Map<String, Object> mapParam) throws Exception;

	// 사이버상담 목록조회
	public List<Map<String, Object>> selectCyberDscsnList(DataRequest dataRequest) throws Exception;
	// 사이버아웃리치 목록조회
	public List<Map<String, Object>> selectOutreachList(DataRequest dataRequest) throws Exception;
	// 모바일상담 목록조회
	public List<Map<String, Object>> selectMobileList(DataRequest dataRequest) throws Exception;

	// 위기및연계 게시글
	public List<Map<String, Object>> selectCrisisLinkBbsctt(DataRequest dataRequest) throws Exception;
	// 위기및연계 유형별건수
	public List<Map<String, Object>> selectCrisisLinkTypeNocs(DataRequest dataRequest) throws Exception;

	// 모니터링 보고서 관린 등록일 중복 check 	
	public List<Map<String, Object>> selectWorkAltMntCrtYmdCheckList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * 
	 * @Method명   : insertMntrngReprts
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 12. 
	 * @Method설명 : 모니터링 보고서 등록
	 */
	Map<String, Object> insertMntrngReprts(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * 
	 * @Method명   : updateMntrngReprts
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 26. 
	 * @Method설명 : 모니터링 보고서 수정
	 */
	Map<String, Object> updateMntrngReprts(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * 
	 * @Method명   : deleteMntrngReprts
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 9. 
	 * @Method설명 : 모니터링 보고서 삭제
	 */
	Map<String, Object> deleteMntrngReprts(DataRequest dataRequest) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectMntrngReprtsDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 15. 
	 * @Method설명 : 모니터링 보고서 상세 조회
	 */
	List<Map<String, Object>> selectMntrngReprtsDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
}

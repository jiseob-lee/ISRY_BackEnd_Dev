/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.cmmn.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : CounsService.java
 * @프로그램 설명 : 청소년상담 공통 서비스 (인터페이스)
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2022. 12. 28. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2022. 12. 28.
 * @수정내용      : 
 * -                
 * -                
 */
public interface CounsService {

	/** 
	 * @Method명   : selectUnitTaskWorkSeCode
	 * @param deptCd	부서코드 값
	 * @return		UNT_TASKWK_SE_CD (단위업무구분코드)
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 28. 
	 * @Method설명 : 단위업무구분코드 조회
	 */
	String selectUnitTaskWorkSeCode(String deptCd) throws Exception;
	
	/**
	 * @Method명   : selectOrgDeptCombo
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 28. 
	 * @Method설명 : 기관별 부서 목록 조회 (콤보박스)
	 */
	List<Map<String, Object>> selectOrgDeptCombo(HttpServletRequest request) throws Exception;
	
	/**
	 * @Method명   : deleteCnsltntAsgn
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 6. 2. 
	 * @Method설명 : 각 게시글 상담자 할당 Delete
	 */
	public void deleteCnsltntAsgn(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	/**
	 * @Method명   : processAnsCmptnAutoSndng
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @param 	   : mapParam
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 6. 5. 
	 * @Method설명 : 각 게시글 답변 완료 자동 발송
	 */
	public void processAnsCmptnAutoSndng(HttpServletRequest request, DataRequest dataRequest, Map<String, String> mapParam) throws Exception;
}

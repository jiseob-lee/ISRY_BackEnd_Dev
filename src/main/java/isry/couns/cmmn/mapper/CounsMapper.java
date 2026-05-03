/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.cmmn.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : CounsMapper.java
 * @프로그램 설명 : 청소년상담 공통 Mapper
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
@Mapper("counsMapper")
public interface CounsMapper {

	/**
	 * @Method명   : selectUnitTaskWorkSeCode
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 28. 
	 * @Method설명 : 단위업무구분코드 조회
	 */
	String selectUnitTaskWorkSeCode(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectOrgDeptCombo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 28. 
	 * @Method설명 : 기관별 부서 목록 조회 (콤보박스)
	 */
	List<Map<String, Object>> selectOrgDeptCombo(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : deleteCnsltntAsgn
	 * @param 	   : mapParam
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 6. 2. 
	 * @Method설명 : 비밀게시판 상담자 할당 Delete
	 */
	void deleteCnsltntAsgn(Map<String, String> mapParam);
	
	/**
	 * @Method명   : updateEmlSndngYN
	 * @param 	   : mapParam
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 6. 5. 
	 * @Method설명 : 이메일발송여부 = 'Y' 업데이트
	 */
	void updateEmlSndngYN(Map<String, String> mapParam);
}

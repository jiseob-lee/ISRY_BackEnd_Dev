/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.ctfctissumng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : ConsttSrchMapper.java
 * @프로그램 설명 : 상담사 검색 매퍼
 * - 
 * - 
 * @작성자        : Myeong.Jae.Cheol
 * @작성일        : 2022. 10. 31. 
 * @수정자        : Myeong.Jae.Cheol
 * @수정일        : 2022. 10. 31.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("consttSrchMapper")
public interface ConsttSrchMapper {
	
	/**
	 * @Method명   : selectConsttCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 12. 29. 
	 * @Method설명 : 상담사 총원 조회
	 */
	Integer selectConsttCount(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectConsttList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2022. 10. 31. 
	 * @Method설명 : 상담사 목록 조회
	 */
	List<Map<String, Object>> selectConsttList(Map<String, Object> mapParam) throws Exception;
}

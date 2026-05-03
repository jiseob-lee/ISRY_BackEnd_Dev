/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2023 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.subms.casemng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : CaseMngSubmsMapper.java
 * @프로그램 설명 : 이주배경 사례관리 관련 Mapper Interface - -
 * @작성자 : Lee.SangHoon
 * @작성일 : 2023. 8. 7.
 * @수정자 : Lee.SangHoon
 * @수정일 : 2023. 8. 7.
 * @수정내용 : - -
 */
@Mapper("caseMngSubmsMapper")
public interface CaseMngSubmsMapper {

	/**
	 * @Method명 : selectCaseinqList
	 * @param paramMap2
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 8. 7.
	 * @Method설명 : 사례목록 조회
	 */
	public List<Map<String, Object>> selectCaseinqList(Map<String, Object> paramMap2) throws Exception;

	/**
	 * @Method명 : caseinqListCount
	 * @param paramMap2
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.SangHoon
	 * @작성일 : 2023. 8. 7.
	 * @Method설명 : 사례목록 총갯수 조회
	 */
	public String caseinqListCount(Map<String, Object> paramMap2) throws Exception;

}

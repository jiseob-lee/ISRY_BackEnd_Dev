/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.casemng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : CsemdCaseMngMapper.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seung.Yeon
 * @작성일 : 2022. 9. 13.
 * @수정자 : Lee.Seung.Yeon
 * @수정일 : 2022. 9. 13.
 * @수정내용 : - -
 */
@Mapper("csemdCaseMngMapper")
public interface CsemdCaseMngMapper {

	// 사례관리_계획 상세조회
	public List<Map<String, Object>> selectCaseMngPlanDetail(Map<String, String> map) throws Exception;

	// 개별화계획 등록
	public int insertAFA410(Map<String, String> map) throws Exception;

	// 개별화계획 수정
	public int updateAFA410(Map<String, String> map) throws Exception;

	// 약물복용 정보 조회
	public List<Map<String, Object>> selectDrfstfTakngInfo(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : selectQustnbSndngHstr
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 3. 7. 
	 * @Method설명 : 사례관리번호가 비어있는 설문발송이력 조회
	 */
	public List<Map<String, Object>> selectQustnbSndngHstr(Map<String, String> map) throws Exception;

	/**
	 * @Method명   : updateSBB600
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 3. 7. 
	 * @Method설명 : 설문발송이력 수정
	 */
	public int updateSBB600(Map<String, Object> map) throws Exception;
}

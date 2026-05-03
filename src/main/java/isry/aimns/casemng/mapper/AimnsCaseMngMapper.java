/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.aimns.casemng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : AimnsCaseMngMapper.java
 * @프로그램 설명 : 사례관리>실행&종결 화면의 고유항목 매퍼 인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 10. 12.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 10. 12.
 * @수정내용 : - -
 */
@Mapper("aimnsCaseMngMapper")
public interface AimnsCaseMngMapper {

	public List<Map<String, Object>> selectEduComplSchdl(Map<String, String> map) throws Exception;

	public void insertEduComplSchdl(Map<String, String> map) throws Exception;

	public void updateEduComplSchdl(Map<String, String> map) throws Exception;

	/**
	 * @Method명 : selectPvsnResrceNm
	 * @param paramMap2
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 6. 7.
	 * @Method설명 :
	 */
	public List<Map<String, Object>> selectPvsnResrceNm(Map<String, Object> paramMap2);

	/**
	 * @Method명 : selectinqList
	 * @param paramMap2
	 * @return
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 6. 7.
	 * @Method설명 :
	 */
	public String selectInqCntList(Map<String, Object> paramMap2);

	/**
	 * @Method명 : saveEduCmplSchdlMng
	 * @param map
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 6. 7.
	 * @Method설명 :
	 */
	public void saveEduCmplSchdlMng(Map<String, Object> map) throws Exception;

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

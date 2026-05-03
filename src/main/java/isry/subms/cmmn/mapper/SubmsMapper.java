/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.cmmn.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : SubmsMapper.java
 * @프로그램 설명 : 이주배경 공통 매퍼 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 5. 13.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 5. 13.
 * @수정내용 : - -
 */
@Mapper("submsMapper")
public interface SubmsMapper {

	public List<Map<String, Object>> selectBizYrCombo(Map<String, String> map) throws Exception;

	public List<Map<String, Object>> selectSrvcExcnBizCombo(Map<String, String> map) throws Exception;

	public List<Map<String, Object>> selectInstNmCombo(Map<String, String> map) throws Exception;

	public List<Map<String, Object>> selectResrceNmCombo(Map<String, String> map) throws Exception;

}

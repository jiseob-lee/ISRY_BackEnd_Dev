/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.atendprecon.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : AtendPreconMapper.java
 * @프로그램 설명 : 출석현황 매퍼 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 6. 13.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 6. 13.
 * @수정내용 : - -
 */
@Mapper("atendPreconMapper")
public interface AtendPreconMapper {

	public List<Map<String, String>> selectAtendPreconList(Map<String, String> map) throws Exception;

	public List<Map<String, String>> selectAtendList(Map<String, String> map) throws Exception;

	public void insertAtend(Map<String, String> map) throws Exception;

	public void deleteAtend(Map<String, String> map) throws Exception;

	public void insertAtendHstr(Map<String, String> map) throws Exception;

	public Map<String, String> selectAtend(Map<String, String> map) throws Exception;

	public Map<String, String> selectAtendByTrpr(Map<String, String> map) throws Exception;

	public List<Map<String, String>> selectAtendRmCnByTrpr(Map<String, String> map) throws Exception;

	public void deleteFile(Map<String, String> map) throws Exception;
}

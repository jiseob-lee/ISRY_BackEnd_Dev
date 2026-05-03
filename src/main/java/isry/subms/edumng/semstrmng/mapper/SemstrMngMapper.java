/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.edumng.semstrmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : SemstrMngMapper.java
 * @프로그램 설명 : 학기관리 매퍼 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 5. 6.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 5. 6.
 * @수정내용 : - -
 */
@Mapper("semstrMngMapper")
public interface SemstrMngMapper {

	public List<Map<String, String>> selectSemstrMngList(Map<String, String> map) throws Exception;

	public List<Map<String, String>> selectSemstrMng(Map<String, String> map) throws Exception;

	public void insertSemstrMng(Map<String, String> map) throws Exception;

	public void updateSemstrMng(Map<String, String> map) throws Exception;

}

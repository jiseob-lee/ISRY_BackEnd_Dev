/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.casemng.trlinsp.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : TrlInspMapper.java
 * @프로그램 설명 : 회기보고등록 매퍼 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 11. 15.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 11. 15.
 * @수정내용 : - -
 */
@Mapper("trlInspMapper")
public interface TrlInspMapper {

	public List<Map<String, Object>> selectTrlInsp(Map<String, String> requestMap) throws Exception;

	public String selectTrlInspMngNo(Map<String, String> map) throws Exception;

	public void insertTrlInsp(Map<String, String> map) throws Exception;

	public void updateTrlInsp(Map<String, String> map) throws Exception;

	public void deleteTrlInsp(Map<String, String> map) throws Exception;

}

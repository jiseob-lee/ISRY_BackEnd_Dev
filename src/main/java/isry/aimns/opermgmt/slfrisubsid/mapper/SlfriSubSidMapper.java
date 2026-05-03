/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.opermgmt.slfrisubsid.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : SlfriSubSidMapper.java
 * @프로그램 설명 : 자립장려금 매퍼 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 6. 24.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 6. 24.
 * @수정내용 : - -
 */
@Mapper("slfriSubSidMapper")
public interface SlfriSubSidMapper {

	public List<Map<String, String>> selectSlfrisubSidStatusList(Map<String, Object> map) throws Exception;

	public List<Map<String, String>> selectSlfriSubSidList(Map<String, String> map) throws Exception;

	public List<Map<String, String>> selectSlfriSubSidInfo(Map<String, String> map) throws Exception;

	public void insertSlfriSubSid(Map<String, String> map) throws Exception;

	public void updateSlfriSubSid(Map<String, String> map) throws Exception;

	public void deleteSlfriSubSid(Map<String, String> map) throws Exception;

	public void insertSlfriSubSidHstr(Map<String, String> map) throws Exception;

	public String selectSlfriSubSidCheck(Map<String, String> map) throws Exception;
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.cmmncode.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : MgmtCmmnCodeMapper.java
 * @프로그램 설명 : 공통 코드 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 30. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 30.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("mgmtCmmnCodeMapper")
public interface MgmtCmmnCodeMapper {
	
	public int selectMaxCodeId() throws Exception;

	public int selectMaxCodeValueId() throws Exception;

	public void saveCode(Map<String, String> map) throws Exception;
	
	public void deleteCode(List<String> listCodeId) throws Exception;
	
	public List<Map<String, Object>> selectCode(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectCodeValue(String codeId) throws Exception;

	public void saveCodeValue(Map<String, String> map) throws Exception;
	
	//public void deleteCodeValue(List<String> listCodeId) throws Exception;
	public void deleteCodeValue(String codeId) throws Exception;
	
	public List<Map<String, Object>> selectUnitSystem(String codeId) throws Exception;
	
	public void insertCodeSystem(Map<String, String> map) throws Exception;
	
	public void deleteCommonCodeSystem(String codeId) throws Exception;
	
	public void insertCommonCodeSystem(Map<String, String> map) throws Exception;
	
	public void updateCodeDesc(Map<String, Object> map) throws Exception;
	
	public void deleteCodeSystem() throws Exception;
	
	public void deleteCodeVal() throws Exception;

	public List<Map<String, Object>> selectCommonCode(String codeId) throws Exception;

	public List<Map<String, Object>> selectCommonCode2(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectCommonCodeUnit(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectCommonCodeAllUnit(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectCommonCodeList(Integer systemId) throws Exception;
	
	public List<Map<String, Object>> selectCommonCodeTotalList(Integer systemId) throws Exception;

	public Integer selectCodeIdDuplicate(Map<String, Object> paramMap) throws Exception;

	public List<Map<String, Object>> selectOrgUnitSystem(Map<String, String> map) throws Exception;

	public void updateCodeId(Map<String, String> map) throws Exception;
	
	public void updateCodeValueId(Map<String, String> map) throws Exception;

	public String getSysDate(Map<String, String> map) throws Exception;

	public List<Map<String, Object>> selectCodeValueUnitSystem(Map<String, Object> map) throws Exception;
	
	public void saveCodeValueUnitSystem(Map<String, Object> map) throws Exception;
	
	public void deleteCodeValueUnitSystem(Map<String, Object> map) throws Exception;
	
	// 회원 가입 전용 가입의 시스템 사용 권한
	public List<Map<String, Object>> selectCommonCodeJoinRights() throws Exception;

	// 1분 전 시간 구하기
	public String getSysDateMinus1Minute(Map<String, String> map) throws Exception;
		
}

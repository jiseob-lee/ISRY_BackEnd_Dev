/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.cmmncode.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : MgmtCmmnCodeService.java
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
public interface MgmtCmmnCodeService {
	
	public Map<String, Integer> selectMaxCodeId() throws Exception;

	public Map<String, Integer> selectMaxCodeValueId() throws Exception;

	public List<Map<String, Object>> selectCode(DataRequest dataRequest) throws Exception;
	
	public void saveCode(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public List<Map<String, Object>> selectCodeValue(DataRequest dataRequest) throws Exception;
	
	public void saveCodeValue(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectUnitSystem(DataRequest dataRequest) throws Exception;
	
	public void saveUnitSystem(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectCommonCode(String codeId) throws Exception;
	
	public List<Map<String, Object>> selectCommonCode(String codeId, String upCmmnsCdValue) throws Exception;
	
	public List<Map<String, Object>> selectCommonCodeUnit(String codeId, String unitCode) throws Exception;
	
	public List<Map<String, Object>> selectCommonCodeList(Integer systemId) throws Exception;
	
	public List<Map<String, Object>> selectCommonCodeTotalList(Integer systemId) throws Exception;
	
	public Map<String, Integer> selectCodeIdDuplicate(DataRequest dataRequest) throws Exception;
	
	public List<Map<String, Object>> selectOrgUnitSystem(DataRequest dataRequest) throws Exception;
	
	public String getSysDate(String STR_FORMAT) throws Exception;

	public List<Map<String, Object>> selectCodeValueUnitSystem(DataRequest dataRequest) throws Exception;
	
	public void saveCodeValueUnitSystem(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	// 회원 가입 전용 가입의 시스템 사용 권한
	public List<Map<String, Object>> selectCommonCodeJoinRights() throws Exception;
}

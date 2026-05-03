/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.config.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : MgmtCmmnConfigMapper.java
 * @프로그램 설명 : 환경설정 관리
 * - 
 * - 
 * @작성자        : Hee Sung Yoon
 * @작성일        : 2023.01.16. 
 * @수정자        : Hee Sung Yoon
 * @수정일        : 2023.01.16.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("mgmtCmmnConfigMapper")
public interface MgmtCmmnConfigMapper {
	
	public List<Map<String, Object>> selectConfigList(String stngId) throws Exception;
	
	public void insertConfigList(Map<String, String> map) throws Exception;
	
	public void updateConfigList(Map<String, String> map) throws Exception;
	
	public void deleteConfigList(String stngId) throws Exception;
	
	public List<Map<String, Object>> selectConfigListLog(String stngId) throws Exception;
	
	public void insertConfigListLog(Map<String, String> map) throws Exception;
}

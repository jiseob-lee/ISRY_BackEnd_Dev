/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.logging.mapper;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : SystemLoggingMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2022. 1. 24. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2022. 1. 24.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("systemLoggingMapper")
public interface SystemLoggingMapper {
	
	public void insertSystemLog(Map<String, Object> map) throws Exception;

	public Integer getMenuId(String callPage) throws Exception;

	public String getMenuNm(String callPage) throws Exception;
	
	public String checkUserRights(Map<String, Object> map) throws Exception;
	
	public String checkGroupRights(Map<String, Object> map) throws Exception;
	
	public Integer checkUserRightsExists(Map<String, Object> map) throws Exception;
	
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.pgmemu.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import isry.itgcms.sysmgmt.pgmemu.service.ProgramStatusVO;
import isry.itgcms.sysmgmt.pgmemu.service.ProgramVO;
import isry.itgcms.sysmgmt.pgmemu.service.WorkUnitVO;

/**
 * @파일명        : InqProgListMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 11. 23. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 11. 23.
 * @수정내용      : 
 * -                
 * -                
 */

@Mapper("inqProgListMapper")
public interface InqProgListMapper {
	
	public List<Map<String, Object>> selectProgram(Map<String, String> map) throws Exception;

	public List<Map<String, Object>> selectWorkUnit() throws Exception;
	
	public List<Map<String, Object>> selectProgramStatus() throws Exception;

	public void insertProgram(Map<String, String> map) throws Exception;
	
	public void updateProgram(Map<String, String> map) throws Exception;
	
	public void deleteProgram(Map<String, String> map) throws Exception;
	
	public void insertProgramHistory(Map<String, String> map) throws Exception;

}

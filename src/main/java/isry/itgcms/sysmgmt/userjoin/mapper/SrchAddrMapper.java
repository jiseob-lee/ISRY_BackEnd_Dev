/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userjoin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : SrchAddrMapper.java
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

@Mapper("srchAddrMapper")
public interface SrchAddrMapper {

	public Cursor<Object> selectAddr(Map<String, String> search) throws Exception;

	public String selectSido(Map<String, String> sido) throws Exception;
	
	public List<Map<String, Object>> selectAddrArea() throws Exception;
	
	public List<Map<String, Object>> selectSidoArea() throws Exception;
	
	public List<Map<String, Object>> selectSgg() throws Exception;
}

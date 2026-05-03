/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : InqAuthGrpListMapper.java
 * @프로그램 설명 : 권한 그룹 조회
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 3. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("inqAuthGrpListMapper")
public interface InqAuthGrpListMapper {
	
	public List<Map<String, Object>> selectAuthGrp(Map<String, String> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectAuthGrpWithId(String id) throws Exception;
	
	public Map<String, Object> selectMaxAuthrtId() throws Exception;
}

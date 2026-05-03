/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.test.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : TestMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 6. 20. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 6. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("testMapper")
public interface TestMapper {

	// 통합기관
	public List<Map<String, String>> selectSAA000() throws Exception;
	
	public void updateSAA000(Map<String, String> map) throws Exception;
	
	// 종사자
	public List<Map<String, String>> selectSCA100() throws Exception;
	
	public void updateSCA100(Map<String, String> map) throws Exception;
	
	// 개인정보
	public List<Map<String, String>> selectSCA300() throws Exception;
	
	public void updateSCA300(Map<String, String> map) throws Exception;
	
}

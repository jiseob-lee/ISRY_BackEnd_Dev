/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.operrpt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명 : OperRptMapper.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Seoung.Jae
 * @작성일 : 2022. 5. 16.
 * @수정자 : Lee.Seoung.Jae
 * @수정일 : 2022. 5. 16.
 * @수정내용 : - -
 */
@Mapper("operRptMapper")
public interface OperRptMapper {
	public List<Map<String, String>> selectOperRptList(Map<String, String> mapParam) throws Exception;

	public List<Map<String, String>> selectOperRpt(Map<String, String> map) throws Exception;

	public void insertOperRpt(Map<String, String> map) throws Exception;

	public void updateOperRpt(Map<String, String> map) throws Exception;

	public void deleteOperRpt(Map<String, String> map) throws Exception;
}

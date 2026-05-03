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
 * @파일명        : InqOrgListMapper.java
 * @프로그램 설명 : 기관 조회
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 1. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 1.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("inqOrgListMapper")
public interface InqOrgListMapper {
	
	public List<Map<String, String>> selectOrg(Map<String, Object> paramMap) throws Exception;
	
	public Integer selectOrgCount(Map<String, Object> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectOrgPaging(Map<String, Object> paramMap) throws Exception;
	
	public Map<String, Object> selectOrgDetail(Map<String, Integer> map) throws Exception;

	public Map<String, Object> selectOrgDetailHistoryData(Map<String, Object> map) throws Exception;
	
	public List<Map<String, String>> selectInstituteType() throws Exception;
	
	public List<Map<String, String>> selectOrgName(String instNm) throws Exception;
	
	public Map<String, Object> selectOrgRestArea(Integer instNo) throws Exception;

	public List<Map<String, Object>> selectNewInstituteList(Map<String, String> map) throws Exception;
	
	public List<Map<String, String>> resrceSelectOrg(Map<String, Object> paramMap) throws Exception;
	
	public Integer selectOrgAuthryCount(Map<String, Object> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectOrgAuthryPaging(Map<String, Object> paramMap) throws Exception;
	
	// 사용자 소속 기관의 대표 전화번호 구하기
	public String selectRepresentativePhone(String userId) throws Exception;
	
	public List<Map<String, Object>> selectOrgDetailHistory(Map<String, Object> param) throws Exception;
	
	public Map<String, Object> selectOrgRenameDetail(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectOrgList(Map<String, Object> param) throws Exception;
}

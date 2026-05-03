/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.dclrandsgstd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : BbserrListMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Park Chan Ho
 * @작성일        : 2022. 5. 23. 
 * @수정자        : Park Chan Ho
 * @수정일        : 2022. 5. 23.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("bbserrListMapper")
public interface BbserrListMapper {
	public List<Map<String, Object>> selectCommonCode(String codeId) throws Exception;
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbserrList(Map<String, Object> mapParam) throws Exception;	
	
	public int insertBbserrList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbserrDetail(Map<String, Object> mapParam) throws Exception; // 게시글 상세보기
	
	public int plusRdcntNocs(Map<String, Object> mapParam) throws Exception;	// 조회수 증가시키기
	
	public void updateBbserrDetail(Map<String, String> mapParam) throws Exception;	// 게시글 상세보기 수정 
	
	public void deleteBbserrDetail(Map<String, String> mapParam) throws Exception;	// 게시글 상세보기 삭제 
}

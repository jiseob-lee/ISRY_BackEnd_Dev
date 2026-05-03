/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.pubms.casemng.sheltrreg.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : LinkInstMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 6. 3. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 6. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("sheltrRegMapper")
public interface SheltrRegMapper {
	
	public List<Map<String, String>> selectReqById(Map<String, String> map) throws Exception;
	public void saveData(Map<String, String> map) throws Exception;
	public void deleteData(Map<String, String> map) throws Exception;
	
	public List<Map<String, String>> selectAsessRcordById(Map<String, String> map) throws Exception; 
	public List<Map<String, String>> selectSprtPensnById(Map<String, String> map) throws Exception;
	public List<Map<String, String>> selectRthousSprtById(Map<String, String> map) throws Exception;

	public void saveAsessRcordData(Map<String, String> map) throws Exception;
	public void saveSprtPensnData(Map<String, String> map) throws Exception;
	public void saveRthousSprtData(Map<String, String> map) throws Exception;

	public void deleteSprtPensnData(Map<String, String> map) throws Exception;
	public void deleteRthousSprtData(Map<String, String> map) throws Exception;
	
	public List<Map<String, String>> selectSheltrCaseList(Map<String, Object> map) throws Exception;
	public List<Map<String, String>> selectSlfrlCaseList(Map<String, Object> map) throws Exception;

	/* paging 처리 수정 20230609 Song Tae Soo*/
	public List<Map<String, Object>> selectSlfrlCasePagingList(Map<String, Object> map) throws Exception;
	public List<Map<String, Object>> selectSheltrCasePagingList(Map<String, Object> map) throws Exception;
	
	public String selectSLfrlCaseListCount(Map<String, Object> map) throws Exception;
	public String selectSheltrCaseListCount(Map<String, Object> map) throws Exception;
}

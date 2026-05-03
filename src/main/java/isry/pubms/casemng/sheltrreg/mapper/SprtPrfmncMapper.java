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
 * @파일명        : SprtPrfmncMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2023. 4. 20. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2023. 4. 20.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("sprtPrfmncMapper")
public interface SprtPrfmncMapper {
	
	public List<Map<String, String>> selectSheltrSprtList(Map<String, Object> map) throws Exception;
	public List<Map<String, String>> selectSlfrlSprtList(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectSheltrSprtPagingList(Map<String, Object> map) throws Exception;
	public List<Map<String, Object>> selectSlfrlSprtPagingList(Map<String, Object> map) throws Exception;
	
	public String selectSheltrSprtPagingCount(Map<String, Object> map) throws Exception;
	public String selectSLfrlSprtPagingCount(Map<String, Object> map) throws Exception;

	public void saveData(Map<String, String> map) throws Exception;

}

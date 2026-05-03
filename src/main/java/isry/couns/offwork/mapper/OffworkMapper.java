package isry.couns.offwork.mapper;

import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : OffworkMapper.java
 * @프로그램 설명 : 퇴근처리
 * - 
 * - 
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 10. 05. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 10. 05. 
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("offworkMapper")
public interface OffworkMapper{
	
	// 퇴근처리 기본정보 조회
	public Map<String, Object> selectLvffcPrcsBassInfo(Map<String, String> map) throws Exception;
	// 퇴근처리 저장
	public int UpdateLvffcPrcs(Map<String, String> map) throws Exception;	
	
	
}

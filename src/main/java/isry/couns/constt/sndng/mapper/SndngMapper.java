package isry.couns.constt.sndng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : SndngMapper.java
 * @프로그램 설명 : 이음-e 발송
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
@Mapper("sndngMapper")
public interface SndngMapper{
	
	// 독려문자발송 조회
	public List<Map<String, Object>> selectChrctrSndngList(Map<String, String> paramMap) throws Exception;
	// 발송내역 조회
	public List<Map<String, Object>> selectSndngHistbList(Map<String, String> paramMap) throws Exception;
	// ISRY_SMS.MMS_CONTENTS_INFO 
	public void insertLMS1(Map<String, Object> map) throws Exception;
	// ISRY_SMS.MSG_DATA 
	public void insertLMS2(Map<String, Object> map) throws Exception;
	// AYE100
	public void updateAYE100(Map<String, Object> map) throws Exception;
	
}

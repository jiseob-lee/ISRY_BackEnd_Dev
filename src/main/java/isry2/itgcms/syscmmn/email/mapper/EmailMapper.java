/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry2.itgcms.syscmmn.email.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : EmailMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 7. 18. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 7. 18.
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("emailMapper")
public interface EmailMapper {
	
	public Integer selectMailIdx() throws Exception;
	
	public void insertEmailFile(Map<String, Object> fileMap) throws Exception;
	
	public void insertEmail(Map<String, Object> map) throws Exception;

	public Integer selectEmailHistoryCount(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectEmailHistory(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectEncList(Integer seqIdx) throws Exception;

	public int deleteDmailInfo(Integer seqIdx) throws Exception;
	
	public void deleteEncDmail(Integer seqIdx) throws Exception;
	
}

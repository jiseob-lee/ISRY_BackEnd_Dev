/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry2.itgcms.syscmmn.sms.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : SmsMapper.java
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
@Mapper("smsMapper")
public interface SmsMapper {
	
	public void insertSMS(Map<String, Object> map) throws Exception;
	
	public void insertLMS1(Map<String, Object> map) throws Exception;
	
	public void insertLMS2(Map<String, Object> map) throws Exception;
	
	public Integer selectMmsContentsInfoSeq() throws Exception;
	
	public Integer selectSmsHistoryCount(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectSmsHistory(Map<String, Object> map) throws Exception;

	public void deleteMmsContentsInfo(Integer contSeq) throws Exception;
	
	public void deleteMsgData(Integer msgSeq) throws Exception;
	
}

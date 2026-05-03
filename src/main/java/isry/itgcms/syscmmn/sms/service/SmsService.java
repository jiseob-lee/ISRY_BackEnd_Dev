/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.sms.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : SmsService.java
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
public interface SmsService {

	public void sendSMS(SmsMessageVO smsMessage) throws Exception;
	
	public void insertSMS(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public void insertLMS(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Integer selectSmsHistoryCount(HttpServletRequest request, Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectSmsHistory(HttpServletRequest request, Map<String, Object> map) throws Exception;

	// 사용자 소속 기관의 대표 전화번호 구하기
	public Map<String, String> selectRepresentativePhone(HttpServletRequest request) throws Exception;
	
	// SMS 발송 예약 취소
	public void processSmsCancel(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.syscmmn.email.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

import isry.itgcms.syscmmn.email.vo.EmailMessageVO;

/**
 * @파일명        : EmailService.java
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
public interface EmailService {
	
	public void insertEmail(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public void insertEmailVO(HttpServletRequest request, EmailMessageVO emailMessageVO) throws Exception;
	
	public Integer selectEmailHistoryCount(HttpServletRequest request, Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectEmailHistory(HttpServletRequest request, Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectEmailDetailAttachList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public void processEmailCancel(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}

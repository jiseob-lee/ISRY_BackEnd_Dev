/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.sndng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : SndngService.java
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

public interface SndngService {

	// 독려문자발송 조회
	public List<Map<String, Object>> selectChrctrSndngList(DataRequest dataRequest) throws Exception;	
	// 발송내역 조회
	public List<Map<String, Object>> selectSndngHistbList(DataRequest dataRequest) throws Exception;
	// SMS 보내기
	public void saveSms(HttpServletRequest request, DataRequest dataRequest) throws Exception;
		
}

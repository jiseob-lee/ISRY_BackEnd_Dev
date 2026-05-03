/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.offwork.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : OffworkService.java
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

public interface OffworkService {

	// 퇴근처리 기본정보 조회
	public Map<String, Object> selectLvffcPrcsBassInfo(HttpServletRequest request,DataRequest dataRequest) throws Exception;
	// 퇴근처리 저장
	public Map<String, String> lvffcPrcsSave(HttpServletRequest request, DataRequest dataRequest) throws Exception;
		
}

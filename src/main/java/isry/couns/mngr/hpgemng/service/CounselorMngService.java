/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.hpgemng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : CounselorMngService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Hai.Ryong
 * @작성일        : 2023. 3. 24. 
 * @수정자        : Kim.Hai.Ryong
 * @수정일        : 2023. 3. 24.
 * @수정내용      : 
 * -                
 * -                
 */
public interface CounselorMngService {

	List<Map<String, Object>> counselMngList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception;
	
	void updateCounselMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
		
}


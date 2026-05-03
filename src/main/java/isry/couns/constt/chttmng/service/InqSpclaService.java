/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.chttmng.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface InqSpclaService {
	/**
	 * @파일명        : SpclaService.java
	 * @프로그램 설명 :
	 * - 
	 * - 
	 * @작성자        : Song.Young.Il
	 * @작성일        : 2022. 5. 4. 
	 * @수정자        : Song.Young.Il
	 * @수정일        : 2022. 5. 4.
	 * @수정내용      : 
	 * -                
	 * -                
	 */
	
	List<Map<String , Object>> selectSpclaList(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectSpclaDetail(Map<String, Object> mapParam);
	
	int getTotalCount(Map<String, Object> mapParam);
	
	Map<String, Object> saveSpclaBoardList(HttpServletRequest request, DataRequest dataRequest);
	
	public void saveClientName(HttpServletRequest request, DataRequest dataRequest);
}

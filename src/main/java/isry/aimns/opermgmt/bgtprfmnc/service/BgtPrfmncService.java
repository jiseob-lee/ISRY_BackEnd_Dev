/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.aimns.opermgmt.bgtprfmnc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : BgtPrfmncService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Seoung.Jae
 * @작성일        : 2022. 6. 27. 
 * @수정자        : Lee.Seoung.Jae
 * @수정일        : 2022. 6. 27.
 * @수정내용      : 
 * -                
 * -                
 */
public interface BgtPrfmncService {
	
	
	List<Map<String, String>> selectBgtPrfmncList(DataRequest dataRequest) throws Exception;

	void selectBgtPrfmnc(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	List<Map<String, Object>> selectBgtPrfmncOnLoad(HttpServletRequest request) throws Exception;

	void saveBgtPrfmnc(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	void selectBgtPrfmncStatusList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	void selectBgtPrfmncExist(HttpServletRequest request, DataRequest dataRequest);
	
}

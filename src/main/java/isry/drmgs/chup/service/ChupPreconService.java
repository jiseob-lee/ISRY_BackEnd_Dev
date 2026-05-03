/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.chup.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : ChupPreconService.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2022. 7. 13. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2022. 7. 13.
 * @수정내용      : 
 * -                
 * -                
 */
public interface ChupPreconService {

	// 건강검진 현황 조회
	public List<Map<String, Object>> selectChupPreconList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	// 건강검진 현황 조회
	public List<Map<String, Object>> selectChupPopupList(DataRequest dataRequest) throws Exception;
	
	//아동학대 위기스크리닝 목록 조회
	public Map<String, Object> selectChilIltrtCrisisScrennList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	//아동학대 위기스크리닝 정보 조회
	public Map<String, Object> selectChilIltrtCrisisScrennInfo(DataRequest dataRequest) throws Exception;
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.stats.hlcampsrvcprfmnc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : HlCampSrvcPrfmncService.java
 * @프로그램 설명 : 치유캠프, 가족치유캠프 서비스실적 서비스 인터페이스 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 5. 16.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 5. 16.
 * @수정내용 : - -
 */
public interface HlCampSrvcPrfmncService {

	/**
	 * @Method명 : selectHlCampSrvcPrfmncList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 5. 16.
	 * @Method설명 : 치유캠프, 가족치유캠프 서비스실적통계조회
	 */
	public List<Map<String, Object>> selectHlCampSrvcPrfmncList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception;

}

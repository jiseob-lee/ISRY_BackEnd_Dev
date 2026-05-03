/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.stats.srvctot.service;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명 : SrvcTotService.java
 * @프로그램 설명 : 서비스별집계 Service Interface - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 2. 13.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 2. 13.
 * @수정내용 : - -
 */
public interface SrvcTotService {

	/**
	 * 
	 * @Method명 : selectYrStats
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 13.
	 * @Method설명 : 연도별통계 조회
	 */
	void selectYrStats(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	/**
	 * @Method명   : selectPrdCrseEnfsnStats
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Lee.Sang.Hoon
	 * @작성일     : 2023. 2. 14. 
	 * @Method설명 : 기간별통계 & 과정별통계 & 종사자별통계 조회
	 */
	void selectPrdCrseEnfsnStats(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.couns.stats.consttprfmnc.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface ConPerformanceStatsService {

	List<Map<String, Object>> selectconPerformanceStats(Map<String, Object> mapParam, HttpServletRequest request) throws Exception;
	
	
	/**
	 * @Method명   : selectCnsltntPerformanceStatsList
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 28. 
	 * @Method설명 : 부서코드 값에 따른 상담자별 실적 조회
	 */
	public void selectCnsltntPerformanceStatsList(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}

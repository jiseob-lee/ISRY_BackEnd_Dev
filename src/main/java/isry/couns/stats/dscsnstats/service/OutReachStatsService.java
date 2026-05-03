/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.couns.stats.dscsnstats.service;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface OutReachStatsService   {
	 	
	/**
	 * @Method명   : selectoutReachStats
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 아웃리치 통계 조회 Method (방법별 및 상담사별 실적)
	 */
	public void selectoutReachStats(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}

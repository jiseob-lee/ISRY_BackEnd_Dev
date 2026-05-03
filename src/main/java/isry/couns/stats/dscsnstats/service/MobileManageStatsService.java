/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.couns.stats.dscsnstats.service;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface MobileManageStatsService {
	
	/**
	 * @Method명   : selectAfterFactMngStatsList
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws     : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 사후관리 통계 조회(대상 및 응답여부/미응답 사유/응답내용/사후관리 실적)
	 */
	public void selectAfterFactMngStatsList(HttpServletRequest request, DataRequest dataRequest) throws Exception;

}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.service;

import java.util.List;
import java.util.Map;

public interface MonthlyStatsService {
	 	
	/**
	 * 
	 * @Method명   : selectMonthlyStats
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : 
	 * @작성일     : 2022. 5. 23. 
	 * @Method설명 : 월별통계 화면의 월별챠트통계(비밀상담,공개상담,채팅상담)
	 */
	Map<String, Object> selectMonthlyStats(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * 
	 * @Method명   : selectMonthlyStatsDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : 
	 * @작성일     : 2022. 5. 23. 
	 * @Method설명 : 월별통계 화면의 월상세통계
	 */
	List<Map<String, Object>> selectMonthlyStatsDetail(Map<String, Object> mapParam) throws Exception;
	
}

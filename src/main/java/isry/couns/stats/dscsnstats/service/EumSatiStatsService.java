/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.couns.stats.dscsnstats.service;

import java.util.List;
import java.util.Map;

public interface EumSatiStatsService   {
	 	
	/**
	 * 
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kang.Hwa.Young
	 * @작성일     : 2022. 3. 22. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selecteumSatiStats(Map<String, Object> mapParam) throws Exception;

	
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.userjoin.service;

import java.util.List;
import java.util.Map;

public interface SrchAddrService {
	
	public List<Map<String, String>> selectAddr(String search) throws Exception;
	
	public List<Map<String, Object>> selectAddrArea() throws Exception;
	
	public List<Map<String, Object>> selectSido() throws Exception;
	
	public List<Map<String, Object>> selectSgg() throws Exception;
}

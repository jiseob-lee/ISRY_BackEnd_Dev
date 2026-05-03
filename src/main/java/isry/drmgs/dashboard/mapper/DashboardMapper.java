/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.dashboard.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * @파일명        : CnterPreconEnfsnMapper.java
 * @프로그램 설명 : 센터별 종사자 현황
 * - 
 * - 
 * @작성자        : Hee Sung Yoon
 * @작성일        : 2022. 8. 3o. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 8. 3o. 
 * @수정내용      : 
 * -                
 * -                
 */
@Mapper("dashboardMapper")
public interface DashboardMapper {
	
	public int selectLinkCnt(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectChartData(Map<String, String> map) throws Exception;
	
	public List<Map<String, Object>> selectBarChartData(Map<String, String> map) throws Exception;
	
	public List<Map<String, Object>> selectLatelyLink(Map<String, String> map) throws Exception;
}

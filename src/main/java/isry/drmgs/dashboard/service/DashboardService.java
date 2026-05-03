/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.dashboard.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @파일명        : DashboardService.java
 * @프로그램 설명 : 센터별 종사자 현황
 * - 
 * - CnterPreconEnfsnService
 * @작성자        : Hee Sung Yoon
 * @작성일        : 2022. 8. 3o. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 8. 3o. 
 * @수정내용      : 센터별 종사자 현황
 * -                
 * -                
 */
public interface DashboardService {
	List<Map<String, Object>> selectEduLinkCnt(Map<String, Object> map) throws Exception;

	List<Map<String, Object>> selectPicLinkCnt(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectLinkCnt(Map<String, Object> map) throws Exception;
	
	List<Map<String, Object>> selectChartData(Map<String, String> map) throws Exception;
	
	List<Map<String, Object>> selectBarChartData(Map<String, String> map) throws Exception;
	
	List<Map<String, Object>> selectLatelyLink(Map<String, String> map) throws Exception;
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.service;

import java.util.List;
import java.util.Map;

public interface WeeklyStatsService {
	 	
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
	List<Map<String, Object>> list(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listConstt(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> listChtt(Map<String, Object> mapParam) throws Exception;
	
//	List<Map<String, Object>> listMm(Map<String, Object> mapParam) throws Exception;
	Map<String, Object> listMm(Map<String, Object> mapParam) throws Exception;
	
	Map<String, Object> listConsttMm(Map<String, Object> mapParam) throws Exception;

	Map<String, Object> listChttMm(Map<String, Object> mapParam) throws Exception;
	
//	List<Map<String, Object>> listConsttMm(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> listHour(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listConsttHour(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listChttHour(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listChttProblem(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listProblem(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listConsttProblem(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listChttDscsn(Map<String, Object> mapParam) throws Exception;
	
}

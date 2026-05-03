package isry.couns.stats.dscsnstats.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("weeklyStatsMapper")
public interface WeeklyStatsMapper{
	
	/**
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> list(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listConstt(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listChtt(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listChttHour(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listChttProblem(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listMm(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listChttMm(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listConsttMm(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listHour(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listConsttHour(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listProblem(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listConsttProblem(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> listChttDscsn(Map<String, Object> mapParam) throws Exception;
}

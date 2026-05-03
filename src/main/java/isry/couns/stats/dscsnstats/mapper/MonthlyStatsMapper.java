package isry.couns.stats.dscsnstats.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("monthlyStatsMapper")
public interface MonthlyStatsMapper{
	
	/**
	 * @Method명   : monthCount
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectMonthlyStats(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : monthCount
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectMonthStatsChart(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : monthCount
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectMonthlyStatsDetail(Map<String, Object> mapParam) throws Exception;
	
}

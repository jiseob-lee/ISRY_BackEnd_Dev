package isry.couns.stats.dscsnstats.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("problemAreaStatsMapper")
public interface ProblemAreaStatsMapper{
	
	/**
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @작성자     : 
	 * @작성일     : 2022. 5. 23. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> list(Map<String, Object> mapParam) throws Exception;

}

package isry.couns.stats.dscsnstats.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("actualUserStatsMapper")
public interface ActualUserStatsMapper{
	
	/**
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> listAaaa(Map<String, Object> mapParam) throws Exception;

}

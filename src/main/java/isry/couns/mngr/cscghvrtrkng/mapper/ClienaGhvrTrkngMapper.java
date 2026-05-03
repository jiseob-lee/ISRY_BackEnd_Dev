package isry.couns.mngr.cscghvrtrkng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("clienaGhvrTrkngMapper")
public interface ClienaGhvrTrkngMapper{
	
	/**
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectClienaGhvrTrkngList(Map<String, Object> mapParam) throws Exception;

}

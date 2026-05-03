package isry.couns.mngr.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("consttSrvyExmnInptCdMapper")
public interface ConsttSrvyExmnInptCdMapper{
	
	/**
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCombo1List(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectConsttSrvyExmnInptCdList(Map<String, Object> mapParam) throws Exception;

}

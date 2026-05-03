package isry.couns.mngr.etxpyreqstdmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("workEtxpyUntpcMngMapper")
public interface WorkEtxpyUntpcMngMapper{
	
	/**
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectWorkEtxpyUntpcMngList(Map<String, Object> mapParam) throws Exception;

	int mergeWorkEtxpyUntpcMng(Map<String, Object> mapParam) throws Exception;

//	int deleteWorkEtxpyUntpcMng(Map<String, Object> mapParam) throws Exception;
	int ddd(Map<String, Object> mapParam) throws Exception;

}

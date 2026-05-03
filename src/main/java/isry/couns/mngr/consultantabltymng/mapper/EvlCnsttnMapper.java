package isry.couns.mngr.consultantabltymng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("evlCnsttnMapper")
public interface EvlCnsttnMapper{
	
	/**
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectEvlCnsttnList(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectNgtmWorkEtxpyReqstdMngDetail(Map<String, Object> mapParam) throws Exception;

	int mergeWorkEtxpyUntpcMng(Map<String, Object> mapParam) throws Exception;

//	int deleteWorkEtxpyUntpcMng(Map<String, Object> mapParam) throws Exception;
	int ddd(Map<String, Object> mapParam) throws Exception;

}

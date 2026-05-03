package isry.couns.taskwksprt.etxpyaplyandinq.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ngtmWorkEtxpyAplyMapper")
public interface NgtmWorkEtxpyAplyMapper{
	
	/**
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectNgtmWorkEtxpyAplyList(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectNgtmWorkEtxpyAplyList2(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectNgtmWorkEtxpyAplyList3(Map<String, Object> mapParam) throws Exception;

	int insertNgtmWorkEtxpyAply(Map<String, Object> mapParam) throws Exception;

}

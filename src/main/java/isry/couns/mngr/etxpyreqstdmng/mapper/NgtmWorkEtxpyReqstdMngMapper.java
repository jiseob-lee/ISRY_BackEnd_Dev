package isry.couns.mngr.etxpyreqstdmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ngtmWorkEtxpyReqstdMngMapper")
public interface NgtmWorkEtxpyReqstdMngMapper{
	
	/**
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectNgtmWorkEtxpyReqstdMngList(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectNgtmWorkEtxpyReqstdMngDetail1(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectNgtmWorkEtxpyReqstdMngDetail2(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectNgtmWorkEtxpyReqstdMngDetail3(Map<String, Object> mapParam) throws Exception;

	int saveNgtmWorkEtxpyReqstdMngDetail(Map<String, Object> mapParam) throws Exception;

}

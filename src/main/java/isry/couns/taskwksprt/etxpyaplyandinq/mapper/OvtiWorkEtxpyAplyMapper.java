package isry.couns.taskwksprt.etxpyaplyandinq.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ovtiWorkEtxpyAplyMapper")
public interface OvtiWorkEtxpyAplyMapper{
	
	/**
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectOvtiWorkEtxpyAplyList1(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectOvtiWorkEtxpyAplyList2(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectOvtiWorkEtxpyAplyList3(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectOvtiWorkEtxpyAplyList4(Map<String, Object> mapParam) throws Exception;

	int insertOvtiWorkEtxpyAply(Map<String, String> mapParam) throws Exception;

}

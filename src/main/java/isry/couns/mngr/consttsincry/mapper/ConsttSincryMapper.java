package isry.couns.mngr.consttsincry.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("consttSincryMapper")
public interface ConsttSincryMapper{
	
	/**
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCombo1List(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectCombo3List(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectConsttSincryCnsltntList(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectConsttSincryDalyList(Map<String, Object> mapParam) throws Exception;

}

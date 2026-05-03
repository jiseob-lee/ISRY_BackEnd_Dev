package isry.couns.mngr.workaltmntmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ofwkUpcsdClngMapper")
public interface OfwkUpcsdClngMapper{
	
	List<Map<String, Object>> selectOfwkUpcsdClngList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> searchComboBoxAprv(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectOfwkUpcsdClngDetail(Map<String, Object> mapParam) throws Exception;
	//List<Map<String, Object>> selectEvdyDscsnClsList(Map<String, Object> mapParam) throws Exception;
	int updateOfwkUpcsdClng(Map<String, Object> mapParam) throws Exception;
	
	Integer insertOfwkUpcsdClngBatch(Map<String, Object> mapParam) throws Exception;
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
}

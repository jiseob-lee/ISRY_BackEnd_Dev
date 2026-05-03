package isry.couns.taskwksprt.taskwkandatdmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ofwkUpcsdClngSprtMapper")
public interface OfwkUpcsdClngSprtMapper{
	
	List<Map<String, Object>> selectOfwkUpcsdClngList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectOfwkUpcsdClngRegDtl(Map<String, Object> mapParam) throws Exception;
	
	int insertOfwkUpcsdClng(Map<String, Object> mapParam) throws Exception;
}

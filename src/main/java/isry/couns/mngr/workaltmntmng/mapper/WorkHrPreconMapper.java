package isry.couns.mngr.workaltmntmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("workHrPreconMapper")
public interface WorkHrPreconMapper{
	
	List<Map<String, Object>> selectWorkHrPreconList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWorkHrPreconDetail(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectAllWorkHrPrecon(Map<String, Object> mapParam) throws Exception;
}

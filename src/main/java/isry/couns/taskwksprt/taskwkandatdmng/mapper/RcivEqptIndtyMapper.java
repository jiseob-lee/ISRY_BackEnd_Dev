package isry.couns.taskwksprt.taskwkandatdmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("rcivEqptIndtyMapper")
public interface RcivEqptIndtyMapper{
	
	List<Map<String, Object>> searchComboOptionRcivEq(Map<String, Object> mapParam) throws Exception;
	
	Map<String, Object> selectUserInfo(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectRcivEqptIndtyList(Map<String, Object> mapParam) throws Exception;
	
	int insertRcivEqptIndty(Map<String, Object> mapParam) throws Exception;
	
	int insertRcivEqptIndty1(Map<String, Object> mapParam) throws Exception;
}

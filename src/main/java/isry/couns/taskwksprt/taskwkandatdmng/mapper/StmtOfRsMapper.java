package isry.couns.taskwksprt.taskwkandatdmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("stmtOfRsMapper")
public interface StmtOfRsMapper{
	
	Map<String, Object> selectUserInfo(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectStmtOfRsList(Map<String, Object> mapParam) throws Exception;

	int insertStmtOfRs(Map<String, Object> mapParam) throws Exception;
	
	int deleteStmtOfRs(Map<String, Object> mapParam) throws Exception;


}

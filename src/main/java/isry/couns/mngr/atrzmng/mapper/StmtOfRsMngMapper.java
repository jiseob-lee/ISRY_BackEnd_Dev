package isry.couns.mngr.atrzmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("stmtOfRsMngMapper")
public interface StmtOfRsMngMapper{
	
	List<Map<String, Object>> searchComboBoxAprv(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectStmtOfRsMngList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectStmtOfRsMngDetail(Map<String, Object> mapParam) throws Exception;
//	
//	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	int updateStmtOfRsMng(Map<String, Object> mapParam) throws Exception;
	
	int deleteStmtOfRsMng(Map<String, Object> mapParam) throws Exception;
	
}

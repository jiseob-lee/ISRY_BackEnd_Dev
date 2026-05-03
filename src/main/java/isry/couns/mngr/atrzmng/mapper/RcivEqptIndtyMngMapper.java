package isry.couns.mngr.atrzmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("rcivEqptIndtyMngMapper")
public interface RcivEqptIndtyMngMapper{
	
	List<Map<String, Object>> searchComboItem(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectRcivEqptIndtyMngList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectRcivEqptindtyMngDetail(Map<String, Object> mapParam) throws Exception;
	
	int updateRcivEqptIndtyMng(Map<String, Object> mapParam) throws Exception;
	
	int updateRcivEqptIndtyMng1(Map<String, Object> mapParam) throws Exception;
	
	int deleteRciveEqptIndtyMng(Map<String, Object> mapParam) throws Exception;
	
	int deleteRciveEqptIndtyMng1(Map<String, Object> mapParam) throws Exception;
	//	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
}

package isry.couns.mngr.atrzmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("workChgMngMapper")
public interface WorkChgMngMapper{
	
	List<Map<String, Object>> searchComboBoxAprv(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWorkChgMngList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWorkChgMngDetail(Map<String, Object> mapParam) throws Exception;
	
	
	
	int processWorkChgMng1(Map<String, Object> mapParam) throws Exception;
	
	int processWorkChgMng2(Map<String, Object> mapParam) throws Exception;
	
	int processWorkChgMng3(Map<String, Object> mapParam) throws Exception;
	
	int processWorkChgMng4(Map<String, Object> mapParam) throws Exception;
	
	int processWorkChgMng5(Map<String, Object> mapParam) throws Exception;
	
	Map<String, String> processWorkChgMngBatch(Map<String, String> mapParam) throws Exception;

	List<Map<String, Object>> selectWorkChgMngSms(Map<String, Object> mapParam) throws Exception;
	
	int processWorkChgMngSms1(Map<String, Object> mapParam) throws Exception;
	
	int processWorkChgMngSms2(Map<String, Object> mapParam) throws Exception;
	
	int processWorkChgMngSms3(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> processWorkChgMngBatchList(Map<String, Object> mapParam) throws Exception;

	int processWorkChgMngBatchUpdate1(Map<String, Object> mapParam) throws Exception;
	
	int processWorkChgMngBatchUpdate2(Map<String, Object> mapParam) throws Exception;
	
	int processWorkChgMngBatchUpdate3(Map<String, Object> mapParam) throws Exception;
	
	int processWorkChgMngBatchUpdate4(Map<String, Object> mapParam) throws Exception;
	
	int processWorkChgMngBatchUpdate5(Map<String, Object> mapParam) throws Exception;
	
}

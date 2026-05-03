package isry.couns.mngr.atrzmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("tlcmtChgReqstdMngMapper")
public interface TlcmtChgReqstdMngMapper{
	
	List<Map<String, Object>> selectTlcmtChgReqstdMngList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectTlcmtChgReqstdMngDetail(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> searchComboBoxAprv(Map<String, Object> mapParam) throws Exception;
	
	int updateTlcmtChgReqstdMng(Map<String, Object> mapParam) throws Exception;
	
	int deleteTlcmtChgReqstdMng(Map<String, Object> mapParam) throws Exception;
//	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
}

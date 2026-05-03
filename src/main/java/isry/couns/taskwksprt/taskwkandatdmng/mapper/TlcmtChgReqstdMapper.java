package isry.couns.taskwksprt.taskwkandatdmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("tlcmtChgReqstdMapper")
public interface TlcmtChgReqstdMapper{
	Map<String, Object> selectUserInfo(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectTlcmtChgReqstdList(Map<String, Object> mapParam) throws Exception;
	
	int insertTlcmtChgReqstd(Map<String, Object> mapParam) throws Exception;
	
	int deleteTlcmtChgReqstd(Map<String, Object> mapParam) throws Exception;
	
}

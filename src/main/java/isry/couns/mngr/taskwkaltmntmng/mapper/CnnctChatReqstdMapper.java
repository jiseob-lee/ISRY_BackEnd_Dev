package isry.couns.mngr.taskwkaltmntmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("cnnctChatReqstdMapper")
public interface CnnctChatReqstdMapper{
	
	List<Map<String, Object>> selectCnnctChatReqstdList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectCnnctChatReqstdDetail(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectCnnctChatReqstdDetailInfo(Map<String, Object> mapParam) throws Exception;	
	
	List<Map<String, Object>> selectCnnctChatReqstdExpInfo(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectCnnctChatRcritTrgtInfo(Map<String, Object> mapParam) throws Exception;
	
	int processCnnctChatReqstd(Map<String, Object> mapParam) throws Exception;
	
	int updateCnnctChatReqstd(Map<String, Object> mapParam) throws Exception;
	
	int updateFileAffi(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> searchComboOption() throws Exception;
	
}

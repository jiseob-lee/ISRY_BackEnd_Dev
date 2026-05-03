package isry.couns.mngr.cnnctchatconstt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("cnnctChatConsttMapper")
public interface CnnctChatConsttMapper{
	
	List<Map<String, Object>> selectCnnctChatConsttList(Map<String, Object> mapParam) throws Exception;
	
	int deleteCnnctChatConstt(Map<String, Object> mapParam) throws Exception;
	
	int insertCnnctChatConstt(List<Map<String, String>> Param) throws Exception;
	
}

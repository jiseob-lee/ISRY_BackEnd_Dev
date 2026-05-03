package isry.couns.constt.etcntabrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bbsContentlistMapper")

public interface BbsContentlistMapper{
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectInqBbsContentList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> bbsContentList(String codeId) throws Exception;
	
	List<Map<String, Object>> selectBbsContentDetail(Map<String, Object> mapParam) throws Exception;
	
	int updateBbsContent(Map<String, String> mapUpd);

	void insertBbsContentReply (Map<String, String> mapUpd);
}

package isry.couns.constt.crisisyngbgscafemng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bbsCafeDataListMapper")

public interface BbsCafeDataListMapper{
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> BbscafedataCode(String codeId) throws Exception;

	List<Map<String, Object>> selectInqBbscafedataList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbscafedataDetail(Map<String, Object> mapParam) throws Exception;
	
	void updateBbscafedata(Map<String, String> mapUpd);

	void insertBbscafedata(Map<String, String> mapIns);
	
	void deleteBbscafedata(Map<String, String> mapDel);

}

package isry.couns.constt.crisisyngbgscafemng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bbsCafeWorkListMapper")

public interface BbsCafeWorkListMapper{
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectBbsCafeWorkInit(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsCafeWorkList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsCafeWorkDetail(Map<String, Object> mapParam) throws Exception;
	
	void updateBbscafework(Map<String, String> mapUpd);

	void insertBbscafework(Map<String, String> mapIns);
	
	void deleteBbscafework(Map<String, String> mapDel);

}

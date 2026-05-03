package isry.couns.constt.etcntabrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bbsExperienceListMapper")

public interface BbsExperienceListMapper{
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> bbsExperienceCode(String codeId) throws Exception;

	List<Map<String, Object>> selectBbsExperienceList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectbbsExperienceDetail(Map<String, Object> mapParam) throws Exception;
	
	void updateBbsExperience(Map<String, String> mapUpd);

	void insertBbsExperience(Map<String, String> mapIns);
	
	void deleteBbsExperience(Map<String, String> mapDel);

}

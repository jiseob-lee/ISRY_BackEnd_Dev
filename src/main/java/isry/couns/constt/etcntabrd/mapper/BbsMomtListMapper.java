package isry.couns.constt.etcntabrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bbsMomtListMapper")

public interface BbsMomtListMapper{
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;

	List<Map<String, Object>> selectBbsMomtList(Map<String, Object> mapParam) throws Exception;
	
	Map<String, Object> selectBbsMomtDetail(Map<String, String> mapParam) throws Exception;
	
	void updateBbsMomt(Map<String, String> mapUpd);
	
	void updateBbsMomtFile(Map<String, Object> mapParam);

	void insertBbsMomtReg(Map<String, String> mapIns);
	
	void deleteBbsMomt(Map<String, String> mapDel);
	
	int insertBbsMomtFileReg(Map<String, String> mapParam);
	
	int updateChmtBass(Map<String, String> paramMap);
	
	int deleteChmtBass(Map<String, String> paramMap);
	
	int deleteChmtDetail(Map<String, String> paramMap);

}

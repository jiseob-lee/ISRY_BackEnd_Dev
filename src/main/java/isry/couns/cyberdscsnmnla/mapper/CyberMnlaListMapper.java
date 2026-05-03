package isry.couns.cyberdscsnmnla.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("cyberMnlaListMapper")
public interface CyberMnlaListMapper{
	
	List<Map<String, Object>> cyberMnlaCode(String codeId) throws Exception;
	
	List<Map<String, Object>> cyberMnlaCodeS(String codeId) throws Exception;
	
	List<Map<String, Object>> selectInqCyberMnlaDetail(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectInqCyberMnlaList(Map<String, Object> mapParam) throws Exception;

	void updateCyberMnlaProc(Map<String, String> mapUpd);

	void insertCyberMnlaReg(Map<String, String> mapIns);
	
	void deleteCyberMnlaProc(Map<String, String> mapDel);
}

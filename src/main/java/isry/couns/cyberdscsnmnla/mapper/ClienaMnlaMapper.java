package isry.couns.cyberdscsnmnla.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("clienaMnlaMapper")
public interface ClienaMnlaMapper{
	
	List<Map<String, Object>> clienaMnlaCode(String codeId) throws Exception;
	
	List<Map<String, Object>> clienaMnlaCodeS(String codeId) throws Exception;
	
	List<Map<String, Object>> selectClienaMnlaDetail(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectClienaMnlaList(Map<String, Object> mapParam) throws Exception;

	void updateClienaMnlaProc(Map<String, String> mapUpd);

	void insertClienaMnlaReg(Map<String, String> mapIns);
	
	void deleteClienaMnlaProc(Map<String, String> mapDel);
}

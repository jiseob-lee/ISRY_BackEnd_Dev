package isry.couns.cyberdscsnmnla.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("laborMnlaMapper")
public interface LaborMnlaMapper{
	

	
	List<Map<String, Object>> subOnLoad(Map<String, Object> mapParam) throws Exception;
//	
//	List<Map<String, Object>> selectClienaMnlaList(Map<String, Object> mapParam) throws Exception;
//
//	void updateClienaMnlaProc(Map<String, String> mapUpd);

	void insertLaborMnlaReg(Map<String, String> mapIns);
	
//	void deleteClienaMnlaProc(Map<String, String> mapDel);
}

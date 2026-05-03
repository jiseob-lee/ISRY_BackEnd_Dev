package isry.couns.mngr.workaltmntmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("mngrMntrgSchdlMapper")
public interface MngrMntrgSchdlMapper{
	
	List<Map<String, Object>> selectMngrMntrgSchdlList(Map<String, Object> mapParam) throws Exception;
	
	Map<String, Object> selectMngrMntrgSchdlDelInfo(Map<String, Object> mapParam) throws Exception;

	int insertMngrMntrgSchdl(Map<String, Object> mapParam) throws Exception;
	
	int updateMngrMntrgSchdl(Map<String, Object> mapParam) throws Exception;
	
	int deleteMngrMntrgSchdl(Map<String, Object> mapParam) throws Exception;
	
}

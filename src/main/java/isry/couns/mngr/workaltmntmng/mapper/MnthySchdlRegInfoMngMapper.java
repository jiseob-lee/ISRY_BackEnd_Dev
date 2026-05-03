package isry.couns.mngr.workaltmntmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("mnthySchdlRegInfoMngMapper")
public interface MnthySchdlRegInfoMngMapper{
	
	List<Map<String, Object>> selectMnthySchdlRegInfoMngList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectMnthySchdlRegModAsgnNocs(Map<String, String> mapParam) throws Exception;
	
	int processMnthySchdlRegInfoMng(Map<String, String> mapParam) throws Exception;
	
	int processMnthySchdlRegInfoMng2(Map<String, String> mapParam) throws Exception;
	
	int deleteMnthySchdlRegInfoMng(Map<String, String> mapParam) throws Exception;
	
	int deleteMnthySchdlRegInfoMng2(Map<String, String> mapParam) throws Exception;
	
	int insertMnthySchdlRegInfoMng(Map<String, String> mapParam) throws Exception;
	
	// -----------------------------------------------------------------------------------------------
	
	List<Map<String, Object>> selectChcMnthySchdlList(Map<String, String> mapParam) throws Exception;
	
	Map<String, Object> selectChcMnthySchdlAsgnInfo(Map<String, String> mapParam) throws Exception;
	
	int updateChcMnthySchdlList(Map<String, String> mapParam) throws Exception;
	
	int deleteChcMnthySchdlList(Map<String, String> mapParam) throws Exception;
	
	int processMnthyAsgnInfo(Map<String, String> mapParam) throws Exception;
	
	int selectChcMnthyDayCnt(Map<String, String> mapParam) throws Exception;
	
	int deleteChcMnthyDayAll(Map<String, String> mapParam) throws Exception;
	
	// -----------------------------------------------------------------------------------------------
	
	int insertMnthySchdlRegInfoMng2(Map<String, String> mapParam) throws Exception;
	
	Map<String, String> insertMnthySchdlRegInfoMngCopy(Map<String, String> mapParam) throws Exception;
	
}

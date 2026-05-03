package isry.couns.constt.etcntabrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bbsVocListMapper")

public interface BbsVocListMapper{
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsVocList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsVocDetail(Map<String, Object> mapParam) throws Exception;
	
	void updateBbsVocProc(Map<String, String> mapUpd);

//	void insertBbsVocReg(Map<String, String> mapIns);
	
	void deleteBbsVocProc(Map<String, String> mapDel);
	
//	---------------------------------------답글
	List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) throws Exception;
	
	void insertBbsRespodVoc(Map<String, String> mapIns);
	
	int insertBbsRespodVoc1(Map<String, String> mapIns);

	void updateBbsRespodVoc(Map<String, String> mapUpd);
	
	void updateBbsRespodVoc1(Map<String, String> mapUpd);
	
	void deleteBbsRespodVoc(Map<String, String> mapDel);
	
	void deleteBbsRespodVoc1(Map<String, String> mapDel);
}

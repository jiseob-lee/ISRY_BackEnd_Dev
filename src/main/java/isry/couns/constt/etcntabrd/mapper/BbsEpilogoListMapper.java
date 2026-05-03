package isry.couns.constt.etcntabrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bbsEpilogoListMapper")

public interface BbsEpilogoListMapper{
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsEpilogoList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsEpilogoList1(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsEpilogoList2(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsEpilogoDetail(Map<String, Object> mapParam) throws Exception;
	
	void updateRdcntDtlList(Map<String, Object> mapParam);
	
	void updateBbsEpilogo(Map<String, String> mapUpd);
	
	int updateBbsEpilogo160(Map<String, String> mapUpd);

	void insertbbsRespodEpilogo(Map<String, String> mapIns);
	
	void insertbbsRespodEpilogo160(Map<String, String> mapUpd);
	
	void deleteBbsEpilogo(Map<String, String> mapDel);
	
	//답글
	List<Map<String, Object>> selectBbsEpilogoRplyDetail(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsEpilogoRplyDetail2(Map<String, Object> mapParam) throws Exception;

	int insertBbsRespodEpilogo(Map<String, String> mapIns);

	void updateBbsRespodEpilogo(Map<String, String> mapUpd);
	
	void updateBbsRespodEpilogo100(Map<String, String> mapUpd);
	
	void deleteBbsRespodEpilogo(Map<String, String> mapDel);
	
	List<Map<String, Object>> insertCounselor(Map<String, Object> mapParam);
	
	List<Map<String, Object>> selectCounselor(Map<String, Object> mapParam);
	
	void insertCounselor2(Map<String, Object> mapParam);
	
	void updateCounselor(Map<String, Object> mapParam);

	List<Map<String, Object>> selectMemo(Map<String, Object> mapParam);
	
	void updateBbsEpilogoRespod(Map<String, Object> mapAns);
	
	public void processEpilgMemo(Map<String, String> mapParam);
}

package isry.couns.constt.unansntabrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bbsopcListMapper")

public interface BbsopcListMapper{
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsopcList(Map<String, Object> mapParam) throws Exception;

	//답변완료
	List<Map<String, Object>> selectBbsopcListY(Map<String, Object> mapParam) throws Exception;
	
	//미답변
	List<Map<String, Object>> selectBbsopcListN(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbsopcDetail(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> counselorList(Map<String, Object> mapParam) throws Exception;
	
	void insertBbsopc(Map<String, String> mapIns);

	void updateBbsopc(Map<String, String> mapUpd);
	
	void deleteBbsopc(Map<String, String> mapDel);
	
//	-----------------------------------------------------위기관리구분
	void insertCrisis(Map<String, Object> mapParam);
	
	int selectCrisBrdCnt(Map<String, Object> mapParam);//
	
	int selectCrisPrsCnt(Map<String, Object> mapParam);//
	
	void insertCrisisBoard(Map<String, Object> mapParam);//
	
	void updateCrisisBoard(Map<String, Object> mapParam);//
	
	void insertCrisisPerson(Map<String, Object> mapParam);//
	
	void updateCrisisPerson(Map<String, Object> mapParam);//
	
//	------------------------------------------------------답글

	List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) throws Exception;
	
	void insertRespod(Map<String, String> mapIns);

	void updateRespod(Map<String, String> mapUpd);
	
	void deleteRespod(Map<String, String> mapDel);
	
//	--------------------------------------	
	void insertCounselor(Map<String, String> mapIns);
	
	void updateCounselor(Map<String, String> mapUpd);

	List<Map<String, Object>> counselorBoardList(Map<String, Object> mapParam) throws Exception;

}

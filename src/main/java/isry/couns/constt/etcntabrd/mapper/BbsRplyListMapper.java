package isry.couns.constt.etcntabrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bbsRplyListMapper")

public interface BbsRplyListMapper{
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectInqBbsRplyList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectInqBbsRplyListDetail(Map<String, Object> mapParam) throws Exception;
	
	void updateRdcntDtlList(Map<String, Object> mapParam);
	
	void updateBbsRplyProc(Map<String, String> mapUpd);

	void insertBbsRplyReg(Map<String, String> mapIns);
	
	void deleteBbsRplyProc(Map<String, String> mapDel);
//	----------------------------------------------------- 댓글
	List<Map<String, Object>> subRplyList(Map<String, Object> mapParam) throws Exception;
	
	void updateBbsDetailRplyProc(Map<String, String> mapUpd);

	void insertBbsDetailRplyReg(Map<String, String> mapIns);
	
	void deleteBbsDetailRplyProc(Map<String, String> mapDel);
	
	void updateRdcnt(Map<String, Object> mapParam);
}

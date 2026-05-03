package isry.couns.constt.bbserr.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("bbsErrListMapper")

public interface BbsErrListMapper{
	
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	void bbserrDtlCnt(Map<String, Object> mapParam);

	void bbserrResCnt(Map<String, Object> mapParam);
	
	List<Map<String, Object>> BbsErrListCmbErr(String codeId) throws Exception;
	
	List<Map<String, Object>> BbsErrListCmbPrgrs(String codeId) throws Exception;

	List<Map<String, Object>> BbsErrListCmbSxdc(String codeId) throws Exception;

	List<Map<String, Object>> selectBbserrList(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectBbserrDetail(Map<String, Object> mapParam) throws Exception;
	
	void updateBbserr(Map<String, String> mapUpd);

	void insertBbserr(Map<String, String> mapIns);
	
	void deleteBbserr(Map<String, String> mapDel);
	
//	---------------------------------------답글
	List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) throws Exception;
	
	void insertBbserrRply(Map<String, String> mapIns);

	void updateBbserrRply(Map<String, String> mapUpd);
	
	void deleteBbserrRply(Map<String, String> mapDel);

}

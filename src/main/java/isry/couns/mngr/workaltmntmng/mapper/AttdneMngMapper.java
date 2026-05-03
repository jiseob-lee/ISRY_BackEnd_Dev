package isry.couns.mngr.workaltmntmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("attdneMngMapper")
public interface AttdneMngMapper{
	
	List<Map<String, Object>> selectAttdneMngList(Map<String, Object> mapParam) throws Exception;
	
	void insertAttdneMng(Map<String, String> mapIns);

	void updateAttdneMng(Map<String, String> mapUpd);
	
	void deleteAttdneMng(Map<String, String> mapUpd);
	
	void processAttdneMngMonth(Map<String, Object> mapParam);
	
}

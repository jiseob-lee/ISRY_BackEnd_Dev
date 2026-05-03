package isry.couns.mngr.workaltmntmng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("evdyDscsnClsMapper")
public interface EvdyDscsnClsMapper{
	
	List<Map<String, Object>> selectEvdyDscsnClsList(Map<String, Object> mapParam) throws Exception;
	
}

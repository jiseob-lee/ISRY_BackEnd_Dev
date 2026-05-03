package isry.couns.mngr.cscghvrtrkng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("consttGhvrTrkngMapper")
public interface ConsttGhvrTrkngMapper{
	
	List<Map<String, Object>> selectConsttGhvrTrkngList(Map<String, Object> mapParam) throws Exception;

}

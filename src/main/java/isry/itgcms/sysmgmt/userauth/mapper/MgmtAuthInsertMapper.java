package isry.itgcms.sysmgmt.userauth.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("mgmtAuthInsertMapper")
public interface MgmtAuthInsertMapper {
	
	public List<Map<String, Object>> selectGrpAuth(Map<String, Object> map) throws Exception;
	
	public void insertGrpAuthToUserAuth(Map<String, Object> map) throws Exception;
	
	public Integer insertGrpAuthToUserAuthCount(Map<String, Object> map) throws Exception;
	
	public void insertGrpAuthToUserAuthHistoryInsert(Map<String, Object> map) throws Exception;
	
	public void insertGrpAuthToUserAuthHistoryUpdate(Map<String, Object> map) throws Exception;
}

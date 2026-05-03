package isry.itgcms.sysmgmt.logview.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("logViewMapper")
public interface LogViewMapper {

	public List<Map<String, Object>> selectSystemLog(Map<String, Object> map) throws Exception;
	
	public Integer selectSystemLogTotalCount(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectLogInOutLog(Map<String, Object> map) throws Exception;
	
	public Integer selectLogInOutLogTotalCount(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectErrorLog(Map<String, Object> map) throws Exception;
	
	public Integer selectErrorLogTotalCount(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> selectErrorDetail(Integer logMngNo) throws Exception;
	
}

package isry.itgcms.sysmgmt.logview.service;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

public interface LogViewService {
	
	public List<Map<String, Object>> selectSystemLog(Map<String, Object> map) throws Exception;
	
	public Integer selectSystemLogTotalCount(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectLogInOutLog(Map<String, Object> map) throws Exception;
	
	public Integer selectLogInOutLogTotalCount(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> selectLogInOutLog2(Map<String, Object> map) throws Exception;
	
	public Integer selectLogInOutLogTotalCount2(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectErrorLog(Map<String, Object> map) throws Exception;
	
	public Integer selectErrorLogTotalCount(Map<String, Object> map) throws Exception;
	
	public Map<String, Object> selectErrorDetail(DataRequest dataRequest) throws Exception;
	
}

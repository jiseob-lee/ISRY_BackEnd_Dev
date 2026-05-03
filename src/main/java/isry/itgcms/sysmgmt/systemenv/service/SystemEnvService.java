package isry.itgcms.sysmgmt.systemenv.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface SystemEnvService {
	
	public List<Map<String, Object>> selectAdminIp() throws Exception;
	
	public void saveAdminIp(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public void deleteAdminIp(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public boolean checkAdminIp(HttpServletRequest request) throws Exception;
	
	public boolean checkDeveloperIp(HttpServletRequest request) throws Exception;

	
	public List<Map<String, Object>> selectSecondSkipIp() throws Exception;
	
	public void saveSecondSkipIp(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	
	
	public List<Map<String, Object>> selectSecondSkipId() throws Exception;
	
	public void saveSecondSkipId(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	
}

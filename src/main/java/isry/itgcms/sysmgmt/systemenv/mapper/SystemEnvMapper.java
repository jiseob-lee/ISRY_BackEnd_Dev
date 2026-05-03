package isry.itgcms.sysmgmt.systemenv.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("systemEnvMapper")
public interface SystemEnvMapper {
	
	public Map<String, String> selectAdminIp() throws Exception;
	
	public Map<String, String> selectDeveloperIp() throws Exception;
	
	public List<Map<String, Object>> selectAdminIpUseY() throws Exception;
	
	public void saveAdminIp(Map<String, String> map) throws Exception;

	public void deleteAdminIp(Map<String, String> map) throws Exception;
	
	public void saveIpAllowList(Map<String, String> map) throws Exception;
	
	public void saveIpAllowHistory(Map<String, String> map) throws Exception;

	
	public void saveSecondSkipIpList(Map<String, String> map) throws Exception;
	
	public void saveSecondSkipIpHistory(Map<String, String> map) throws Exception;
	
	
	public void saveSecondSkipIdList(Map<String, String> map) throws Exception;
	
	public void saveSecondSkipIdHistory(Map<String, String> map) throws Exception;
	
}

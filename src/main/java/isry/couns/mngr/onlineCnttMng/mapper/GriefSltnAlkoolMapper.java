package isry.couns.mngr.onlineCnttMng.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("griefSltnAlkoolMapper")
public interface GriefSltnAlkoolMapper{
	
	public List<Map<String, Object>> selectGriefSltnAlkoolThemaList(Map<String, String> paramMap) throws Exception;
	
	public int InsertGriefSltnAlkoolThema(Map<String, String> map) throws Exception;	
	
	public int InsertGriefSltnAlkool(Map<String, String> map) throws Exception;
	
	public Map<String, Object> selectGriefSltnAlkoolThemaUpdate(Map<String, String> map) throws Exception;
	
	public Map<String, Object> selectGriefSltnAlkoolUpdate(Map<String, String> map) throws Exception;
	
	public int DeleteGriefSltnAlkoolThema(Map<String, String> map) throws Exception;
	
	public int DeleteGriefSltnAlkool(Map<String, String> map) throws Exception;	
	
	public int UpdateGriefSltnAlkoolThema(Map<String, String> map) throws Exception;
	
	public int UpdateGriefSltnAlkool(Map<String, String> map) throws Exception;
	
	public List<Map<String, Object>> selectGriefSltnAlkool(Map<String, String> paramMap) throws Exception;
	
	public List<Map<String, Object>> selectGriefSltnAlkoolList(Map<String, String> paramMap) throws Exception;
}

package isry.itgcms.sysmgmt.personalinfo.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("myPageMapper")
public interface MyPageMapper {
	
	public void saveMyPage(Map<String, Object> map) throws Exception;
	
	public List<Map<String, Object>> selectMyPage(String userId) throws Exception;
	
	public Integer selectCheckedMyPage(Map<String, Object> map) throws Exception;
}

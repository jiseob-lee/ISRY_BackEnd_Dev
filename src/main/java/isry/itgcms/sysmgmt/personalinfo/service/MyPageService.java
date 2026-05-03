package isry.itgcms.sysmgmt.personalinfo.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface MyPageService {
	
	public void saveMyPage(HttpServletRequest request, DataRequest dateRequest) throws Exception;
	
	public List<Map<String, Object>> selectMyPage(HttpServletRequest request, DataRequest dateRequest) throws Exception;
	
	public boolean selectCheckedMyPage(HttpServletRequest request, DataRequest dateRequest) throws Exception;
	
}

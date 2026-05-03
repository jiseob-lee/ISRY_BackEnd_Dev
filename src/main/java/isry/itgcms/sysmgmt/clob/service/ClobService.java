package isry.itgcms.sysmgmt.clob.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface ClobService {
	
	public List<Map<String, Object>> selectClob(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}

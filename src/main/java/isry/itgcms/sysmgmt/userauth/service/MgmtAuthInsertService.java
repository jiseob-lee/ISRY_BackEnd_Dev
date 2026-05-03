package isry.itgcms.sysmgmt.userauth.service;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface MgmtAuthInsertService {
	
	public void insertGrpAuth(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
}

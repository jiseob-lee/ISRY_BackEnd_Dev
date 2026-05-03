package isry.sample.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;


/**
 * @Class Name : CmnFileService.java
 * @Description : CmnFileService Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 *
 * @author tomatosystem
 * @since 
 * @version
 * @see
 *
 */
public interface CmnFileService {

	List<Map<String, Object>> selectCmnFileList(Map<String, String> mapParam);

	Map<String, String> uploadCmnFile(HttpServletRequest request, DataRequest dataRequest) throws IOException, Exception;
	
	List<Map<String, String>> uploadCmnFileSeperate(HttpServletRequest request, DataRequest dataRequest) throws IOException, Exception;

	int deleteCmnFile(ParameterGroup dsFile);

	int deleteCmnFileByAttcFileNo(Map<String, String> mapParam);
		
	void imageUploadCmnFile(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws IOException, Exception;

}

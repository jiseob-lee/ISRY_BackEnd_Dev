package isry.sample.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @Class Name : TstGridDevService.java
 * @Description : TstGridDevService Class
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
public interface TstGridDevService {

	List<Map<String, Object>> selectCmnTmpRegList(Map<String, String> mapParam) throws Exception;

	void selectCmnTmpRegRowHandler(Map<String, String> mapParam, HttpServletResponse response) throws Exception;

	void saveCmnTmpReg(DataRequest dataRequest);

	void saveCmnTmpRegTab(DataRequest dataRequest);

	void saveCmnTmpRegWithFile(DataRequest dataRequest);

}

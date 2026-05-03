package isry.sample.service;

import java.util.List;
import java.util.Map;

import com.cleopatra.protocol.data.DataRequest;

/**
 * @Class Name : TstGridGridDevService.java
 * @Description : TstGridGridDevService Class
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
public interface TstGridGridDevService {

	List<Map<String, Object>> selectCmnTmpRegFeeList(Map<String, String> mapParam) throws Exception;

	void saveCmnTmpReg(DataRequest dataRequest);

	void saveCmnTmpRegFee(DataRequest dataRequest);

}

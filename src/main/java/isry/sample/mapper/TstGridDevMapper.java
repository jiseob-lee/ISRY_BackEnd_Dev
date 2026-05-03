package isry.sample.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 응용 샘플(CMN_TMP_REG, CMN_TMP_REG_FEE)에 관한 데이터처리 매퍼 클래스
 *
 * @author  tomatosystem
 * @since 
 * @version 1.0
 * @see <pre>
 *  == 개정이력(Modification Information) ==
 *
 *          수정일          수정자           수정내용
 *  ----------------    ------------    ---------------------------
 *
 * </pre>
 */
@Mapper("tstGridDevMapper")
public interface TstGridDevMapper  {

	List<Map<String, Object>> selectCmnTmpRegList(Map<String, String> mapParam) throws Exception;
	
	List<Map<String, Object>> selectCmnTmpRegList(Map<String, String> mapParam, ResultHandler<HashMap<String, Object>> resultHandler) throws Exception;

	void deleteCmnTmpReg(Map<String, String> map);

	void insertCmnTmpReg(Map<String, String> map);

	void updateCmnTmpReg(Map<String, String> map);

	List<Map<String, Object>> selectCmnTmpRegFeeList(Map<String, String> mapParam);

	void deleteCmnTmpRegFee(Map<String, String> map);

	void insertCmnTmpRegFee(Map<String, String> map);

	void updateCmnTmpRegFee(Map<String, String> map);

}

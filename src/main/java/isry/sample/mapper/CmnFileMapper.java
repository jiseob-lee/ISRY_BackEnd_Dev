package isry.sample.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 파일 sample에 관한 데이터처리 매퍼 클래스
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

@Mapper("cmnFileMapper")
public interface CmnFileMapper {

	List<Map<String, Object>> selectCmnFileList(Map<String, String> mapParam);

	int deleteCmnAttcFile(Map<String, Object> file);

	int insertCmnFile(Map<String, String> mapParam);
	
	public Integer selectAttcFileNo() throws Exception;
	
}

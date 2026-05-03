package isry.sample.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

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


@Mapper("cmnCodeMapper")
public interface CmnCodeMapper {

	/**
	 * @Method명   : sysout
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 11. 4. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCmnCodeList(Map<String, String> mapParam);

}

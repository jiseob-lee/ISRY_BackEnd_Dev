package isry.sample.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("aimnsTrainessCostMapper")
public interface AimnsTrainessCostMapper{
	
	/**
	 * @Method명   : selectTrainessCostList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectTrainessCostList(Map<String, Object> mapParam) throws Exception;
	
}

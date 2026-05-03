package isry.itgcms.itgBrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("itgQnaBrdMapper")
public interface ItgQnaBrdMapper  {

	/**
	 * @Method명   : selectItgQnaBrdList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 15. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectItgQnaBrdList(Map<String, Object> mapParam) throws Exception;
	
}

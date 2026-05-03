package isry.itgcms.itgBrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("itgBrdCmnCmntMapper")
public interface ItgBrdCmnCmntMapper  {

	/**
	 * @Method명   : selectItgBrdCmntList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 21. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectItgBrdCmntList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : insertItgBrdCmntList
	 * @param mapIns
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 21. 
	 * @Method설명 :
	 */
	void insertItgBrdCmntList(Map<String, String> mapIns) throws Exception;

	/**
	 * @Method명   : updateItgBrdCmntList
	 * @param mapUpd
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 21. 
	 * @Method설명 :
	 */
	void updateItgBrdCmntList(Map<String, String> mapUpd) throws Exception;

	/**
	 * @Method명   : deleteItgBrdCmntList
	 * @param mapDel
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 21. 
	 * @Method설명 :
	 */
	void deleteItgBrdCmntList(Map<String, String> mapDel) throws Exception;
	
}

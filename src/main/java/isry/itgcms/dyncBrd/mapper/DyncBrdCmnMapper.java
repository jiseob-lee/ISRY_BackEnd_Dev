package isry.itgcms.dyncBrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 * @파일명        : DyncBrdCmnMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : You Minsang
 * @작성일        : 2022. 07. 25. 
 * @수정자        : You Minsang
 * @수정일        : 2022. 07. 25.
 * @수정내용      : 
 * -                
 * -
 */
@Mapper("dyncBrdCmnMapper")
public interface DyncBrdCmnMapper  {

	/**
	 * @Method명   : selectDyncBrdCmnInfoList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 :
	 */
	Map<String, Object> selectDyncBrdCmnInfoList(Map<String, String> mapParam) throws Exception;

	/**
	 * @Method명   : selectDyncBrdCmnColList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDyncBrdCmnColList(Map<String, String> mapParam) throws Exception;
	

	/**
	 * @Method명   : selectDyncBrdCmnColDataList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 27. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDyncBrdCmnColDataList(Map<String, Object> mapParam) throws Exception;
	

	/**
	 * @Method명   : selectDyncBrdCmnDtlList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 27. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDyncBrdCmnDtlList(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : updateDyncBrdRdcntList
	 * @param mapParam
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 7. 26. 
	 * @Method설명 :
	 */
	void updateDyncBrdCmnRdcntList(Map<String, Object> mapParam) throws Exception;
		
	
	void insertDyncBrdCmnDtlList(Map<String, String> mapIns) throws Exception;

	void updateDyncBrdCmnDtlList(Map<String, String> mapUpd) throws Exception;

	void deleteDyncBrdCmnDtlList(Map<String, String> mapDel) throws Exception;
	
	
	void insertDyncBrdCmnColDataList(Map<String, String> mapParam) throws Exception;
	
	void updateDyncBrdCmnColDataList(Map<String, String> mapParam) throws Exception;
	
	void deleteDyncBrdCmnColDataList(Map<String, String> mapParam) throws Exception;


	
	


}

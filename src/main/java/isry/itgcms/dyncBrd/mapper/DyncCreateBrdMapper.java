package isry.itgcms.dyncBrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 * @파일명        : DyncCreateBrdMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : You Minsang
 * @작성일        : 2021. 12. 20. 
 * @수정자        : You Minsang
 * @수정일        : 2021. 12. 20.
 * @수정내용      : 
 * -                
 * -
 */
@Mapper("dyncCreateBrdMapper")
public interface DyncCreateBrdMapper  {
	
	/**
	 * @Method명   : selectRootMenuList
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 4. 5. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectRootMenuList() throws Exception;
	
	/**
	 * @Method명   : selectCreateBoardList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 2. 3. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCreateBoardList(Map<String, String> mapParam) throws Exception;
	

	/**
	 * @Method명   : selectBoardProgramInfo
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 4. 7. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectBoardProgramInfo(Map<String, String> mapParam) throws Exception;


	/**
	 * @Method명   : selectCreateBoardColList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 2. 4. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCreateBoardColList(Map<String, String> mapParam) throws Exception;
	
	/**
	 * @Method명   : insertCreateBoardList
	 * @param mapIns
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 2. 3. 
	 * @Method설명 :
	 */
	void insertCreateBoardList(Map<String, String> mapIns);

	/**
	 * @Method명   : updateCreateBoardList
	 * @param mapUpd
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 2. 3. 
	 * @Method설명 :
	 */
	void updateCreateBoardList(Map<String, String> mapUpd);

	/**
	 * @Method명   : deleteCreateBoardList
	 * @param mapDel
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 2. 3. 
	 * @Method설명 :
	 */
	void deleteCreateBoardList(Map<String, String> mapDel);
	
	/**
	 * @Method명   : insertColInfoCreateBoardList
	 * @param mapIns
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 2. 3. 
	 * @Method설명 :
	 */
	void insertColInfoCreateBoardList(Map<String, String> mapIns);

	/**
	 * @Method명   : updateColInfoCreateBoardList
	 * @param mapUpd
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 2. 3. 
	 * @Method설명 :
	 */
	void updateColInfoCreateBoardList(Map<String, String> mapUpd);

	/**
	 * @Method명   : deleteColInfoCreateBoardList
	 * @param mapDel
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 2. 3. 
	 * @Method설명 :
	 */
	void deleteColInfoCreateBoardList(Map<String, String> mapDel);

	/**
	 * @Method명   : getCmmnsCdTotalCount
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 6. 17. 
	 * @Method설명 :
	 */
	int getCmmnsCdTotalCount(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : selectCreateBoardcmmnsCdList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 6. 17. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectCreateBoardcmmnsCdList(Map<String, Object> mapParam) throws Exception;

}

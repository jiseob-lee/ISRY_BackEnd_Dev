package isry.sample.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 * @파일명        : NoticeBoardMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Song.Young.Il
 * @작성일        : 2021. 12. 20. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2021. 12. 20.
 * @수정내용      : 
 * -                
 * -
 */
@Mapper("noticeBoardMapper")
public interface NoticeBoardMapper  {
	
	/**
	 * @Method명   : getTotalCount
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 :
	 */
	int getTotalCount(Map<String, Object> mapParam) throws Exception;
	
	/**
	 * @Method명   : selectNoticeBoardList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectNoticeBoardList(Map<String, Object> mapParam) throws Exception;	
	

	/**
	 * @Method명   : selectNoticeBoardDtlList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectNoticeBoardDtlList(Map<String, Object> mapParam) throws Exception;	
	

	/**
	 * @Method명   : updateNoticeBoardDtlList
	 * @param mapParam
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 3. 
	 * @Method설명 :
	 */
	void updateNoticeBoardDtlList(Map<String, Object> mapParam);
	
	/**
	 * @Method명   : insertNoticeBoardList
	 * @param map
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 :
	 */
	void insertNoticeBoardList(Map<String, String> map);	

	/**
	 * @Method명   : updateNoticeBoardList
	 * @param map
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 :
	 */
	void updateNoticeBoardList(Map<String, String> map);	
	
	/**
	 * @Method명   : deleteNoticeBoardList
	 * @param map
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 5. 2. 
	 * @Method설명 :
	 */
	void deleteNoticeBoardList(Map<String, String> map);




}

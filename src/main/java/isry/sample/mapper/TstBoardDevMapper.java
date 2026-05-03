package isry.sample.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 * @파일명        : TstBoardDevMapper.java
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
@Mapper("tstBoardDevMapper")
public interface TstBoardDevMapper  {
	
	/**
	 * @Method명   : selectBoardList
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2021. 12. 20. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectBoardList(Map<String, Object> mapParam) throws Exception;

	void deleteBoardList(Map<String, String> map);

	void insertBoardList(Map<String, String> map);

	void updateBoardList(Map<String, String> map);

	/**
	 * @Method명   : selectSysDate
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2021. 12. 21. 
	 * @Method설명 :
	 */
	String selectSysDate(Map<String, String> mapParam);

	/**
	 * @Method명   : getTotalCount
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2021. 12. 30. 
	 * @Method설명 :
	 */
	String getTotalCount();

}

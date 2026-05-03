package isry.itgcms.dyncBrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 * @파일명 : DynamicCmntBoardMapper.java
 * @프로그램 설명 : - -
 * @작성자 : You Minsang
 * @작성일 : 2021. 12. 20.
 * @수정자 : You Minsang
 * @수정일 : 2021. 12. 20.
 * @수정내용 : - -
 */
@Mapper("dyncCmntBrdMapper")
public interface DyncCmntBrdMapper {

	
	/**
	 * @Method명 : selectDynamicCmntBoardList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 27.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDynamicCmntBoardList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명 : selectDynamicCmntBoardCmntList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 27.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDynamicCmntBoardCmntList(Map<String, Object> mapParam) throws Exception;

	void insertDynamicCmntBoardCmntList(Map<String, String> mapIns) throws Exception;

	void updateDynamicCmntBoardCmntList(Map<String, String> mapUpd) throws Exception;

	void deleteDynamicCmntBoardCmntList(Map<String, String> mapDel) throws Exception;

}

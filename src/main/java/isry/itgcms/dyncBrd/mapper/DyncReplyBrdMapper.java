package isry.itgcms.dyncBrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 * @파일명        : DyncReplyBrdMapper.java
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
@Mapper("dyncReplyBrdMapper")
public interface DyncReplyBrdMapper  {
		
	List<Map<String, Object>> selectDynamicReplyBoardList(Map<String, Object> mapParam) throws Exception;
		
	/**
	 * @Method명   : selectDynamicReplyBoardCommentList
	 * @param mapParam
	 * @return
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 6. 15. 
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectDynamicReplyBoardReplyList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : updateRdcntDynamicReplyBoardReplyList
	 * @param mapParam
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 6. 23. 
	 * @Method설명 :
	 */
	void updateRdcntDynamicReplyBoardReplyList(Map<String, Object> mapParam);
	

	void deleteDynamicReplyBoardReplyList(Map<String, String> mapDel);
	
	void insertDynamicReplyBoardReplyList(Map<String, String> mapIns);

	void updateDynamicReplyBoardReplyList(Map<String, String> mapUpd);
	
}

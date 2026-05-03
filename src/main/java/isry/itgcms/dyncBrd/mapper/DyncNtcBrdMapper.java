package isry.itgcms.dyncBrd.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 * @파일명 : SampleBoardMapper.java
 * @프로그램 설명 : - -
 * @작성자 : Song.Young.Il
 * @작성일 : 2021. 12. 20.
 * @수정자 : Song.Young.Il
 * @수정일 : 2021. 12. 20.
 * @수정내용 : - -
 */
@Mapper("dyncNtcBrdMapper")
public interface DyncNtcBrdMapper {

	/**
	 * @Method명 : selectSampleBoardList
	 * @param mapParam
	 * @return
	 * @작성자 : You Minsang
	 * @작성일 : 2022. 7. 27.
	 * @Method설명 :
	 */
	List<Map<String, Object>> selectSampleBoardList(Map<String, Object> mapParam) throws Exception;

}

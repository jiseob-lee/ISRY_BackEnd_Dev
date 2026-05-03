package isry.pubmt.indivsprt.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 
 * @파일명        : CertiMapper.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.seong.gyu
 * @작성일        : 2022. 02. 24.
 * @수정자        : 
 * @수정일        : 
 * @수정내용      : 
 * -                
 * -
 */
@Mapper("certiMapper")
public interface CertiMapper  {
	
	String selectSysDate(Map<String, String> mapParam);
	
	List<Map<String, Object>> selectCertiList(Map<String, String> mapParam) throws Exception;
	
	List<Map<String, Object>> selectCertiColList(Map<String, String> mapParam) throws Exception;

	List<Map<String, Object>> selectCertiColDataList(Map<String, String> mapParam) throws Exception;
	

	void deleteCertiList(Map<String, String> map);

	void insertCertiList(Map<String, String> map);

	void updateCertiList(Map<String, String> map);
	
	void saveCertiColData(Map<String, String> map);
	
	void deleteCertiColData(Map<String, String> map);
	
	
	
	


}

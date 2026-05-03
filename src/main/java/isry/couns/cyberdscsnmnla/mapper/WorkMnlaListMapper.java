package isry.couns.cyberdscsnmnla.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("workMnlaListMapper")
public interface WorkMnlaListMapper{
	
	List<Map<String, Object>> workMnlaCode(String codeId) throws Exception;
	
	List<Map<String, Object>> workMnlaCodeS(String codeId) throws Exception;
	
	List<Map<String, Object>> selectWorkMnlaDetail(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectWorkMnlaList(Map<String, Object> mapParam) throws Exception;

	/**
	 * @Method명   : updateClienaMnlaProc
	 * @param mapUpd
	 * @작성자     : Song.Young.Il
	 * @작성일     : 2022. 5. 18. 
	 * @Method설명 :
	 */
	void updateWorkMnlaProc(Map<String, String> mapUpd);

	void insertWorkMnlaReg(Map<String, String> mapIns);
	
	void deleteWorkMnlaProc(Map<String, String> mapDel);
	
	
}

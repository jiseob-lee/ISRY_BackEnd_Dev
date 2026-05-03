package isry.couns.cyberdscsnmnla.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("faqMnlaListMapper")
public interface FaqMnlaListMapper{
	
	List<Map<String, Object>> faqMnlaCode(String codeId) throws Exception;
	
	List<Map<String, Object>> faqMnlaCodeS(String codeId) throws Exception;
	
	List<Map<String, Object>> selectFaqMnlaDetail(Map<String, Object> mapParam) throws Exception;
	
	List<Map<String, Object>> selectInqfaqMnlaList(Map<String, Object> mapParam) throws Exception;

	void updateFaqMnlaProc(Map<String, String> mapUpd);

	void insertFaqMnlaReg(Map<String, String> mapIns);
	
	void deleteFaqMnlaProc(Map<String, String> mapDel);
}

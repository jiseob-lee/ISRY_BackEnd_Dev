package isry.itgcms.syscmmn.noticepopup.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("noticePopupMapper")
public interface NoticePopupMapper {
	
	public Integer selectSeq() throws Exception;
	
	public void insertPopup(Map<String, String> map) throws Exception;
	
	public void updatePopup(Map<String, String> map) throws Exception;
	
	public void deletePopup(Map<String, String> map) throws Exception;
	
	public void savePopup(Map<String, String> map) throws Exception;

	public Integer selectPopupCount(Map<String, Object> dmSearchMap) throws Exception;
	
	public List<Map<String, Object>> selectPopupList(Map<String, Object> dmSearchMap) throws Exception;
	
	public List<Map<String, Object>> selectNoticePopupList(Map<String, String> map) throws Exception;
	
}

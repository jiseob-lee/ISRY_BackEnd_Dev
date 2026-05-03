package isry.itgcms.syscmmn.noticepopup.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.cleopatra.protocol.data.DataRequest;

public interface NoticePopupService {
	
	public Integer selectPopupSeq() throws Exception;

	public void savePopup(HttpServletRequest request, DataRequest dataRequest) throws Exception;

	public void deletePopup(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public void savePopupDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception;
	
	public Integer selectPopupCount(Map<String, Object> dmSearchMap) throws Exception;
	
	public List<Map<String, Object>> selectPopupList(Map<String, Object> dmSearchMap) throws Exception;
	
	public List<Map<String, Object>> selectNoticePopupList() throws Exception;
	public List<Map<String, Object>> selectNoticePopupListInner() throws Exception;
	public List<Map<String, Object>> selectNoticePopupListOuter() throws Exception;
	
}

package isry.itgcms.syscmmn.noticepopup.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.syscmmn.noticepopup.mapper.NoticePopupMapper;
import isry.itgcms.syscmmn.noticepopup.service.NoticePopupService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Service("noticePopupService")
public class NoticePopupServiceImpl implements NoticePopupService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "noticePopupMapper")
	private NoticePopupMapper noticePopupMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public Integer selectPopupSeq() throws Exception {
		
		log.debug("test");
		
		return noticePopupMapper.selectSeq();
	}

	@Override
	public void savePopup(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsList");
		
		List<Map<String, String>> insertList = parameterGroup.getInsertedRowList();
		List<Map<String, String>> updateList = parameterGroup.getUpdatedRowList();
		List<Map<String, String>> deleteList = parameterGroup.getDeletedRowList();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		if (insertList != null && insertList.size() > 0) {
			for (int i=0; i < insertList.size(); i++) {
				Map<String, String> insertMap = insertList.get(i);
				insertMap.put("USER_ID", userId2);
				noticePopupMapper.insertPopup(insertMap);
			}
		}
		if (updateList != null && updateList.size() > 0) {
			for (int i=0; i < updateList.size(); i++) {
				Map<String, String> updateMap = updateList.get(i);
				updateMap.put("USER_ID", userId2);
				noticePopupMapper.updatePopup(updateMap);
			}
		}
		if (deleteList != null && deleteList.size() > 0) {
			for (int i=0; i < deleteList.size(); i++) {
				Map<String, String> deleteMap = deleteList.get(i);
				noticePopupMapper.deletePopup(deleteMap);
			}
		}
	}

	@Override
	public void deletePopup(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmData");

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}

		Map<String, String> deleteMap = new HashMap<>();
		deleteMap.put("POPUP_NO", parameterGroup.getValue("POPUP_NO"));
		
		noticePopupMapper.deletePopup(deleteMap);
	}
	
	@Override
	public void savePopupDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmData");
		Map<String, String> map = parameterGroup.getSingleValueMap();
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		map.put("USER_ID", userId2);
		
		noticePopupMapper.savePopup(map);
	}
	
	@Override
	public Integer selectPopupCount(Map<String, Object> dmSearchMap) throws Exception {
		//ScpDb scpDb = new ScpDb();
		//String nmEncpt = scpDb.scpEncB64((String)dmSearchMap.get("FRST_RGTR_NM"));
		//dmSearchMap.put("FRST_RGTR_NM_ENCPT", nmEncpt);
		return noticePopupMapper.selectPopupCount(dmSearchMap);
	}
	
	@Override
	public List<Map<String, Object>> selectPopupList(Map<String, Object> dmSearchMap) throws Exception {
		//ScpDb scpDb = new ScpDb();
		//String nmEncpt = scpDb.scpEncB64((String)dmSearchMap.get("FRST_RGTR_NM"));
		//dmSearchMap.put("FRST_RGTR_NM_ENCPT", nmEncpt);
		List<Map<String, Object>> list1 = noticePopupMapper.selectPopupList(dmSearchMap);
		List<Map<String, Object>> list2 = new ArrayList<>();
		if (list1 != null && list1.size() > 0) {
			for (int i=0; i < list1.size(); i++) {
				Map<String, Object> map1 = list1.get(i);
				//map1.put("FRST_RGTR_NM", scpDb.scpDecB64((String)map1.get("FRST_RGTR_NM")));
				list2.add(map1);
			}
		}
		return list2;
	}

	@Override
	public List<Map<String, Object>> selectNoticePopupList() throws Exception {
		return noticePopupMapper.selectNoticePopupList(null);
	}
	@Override
	public List<Map<String, Object>> selectNoticePopupListInner() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("OPEN_LC_VALUE", "inner");
		return noticePopupMapper.selectNoticePopupList(map);
	}
	@Override
	public List<Map<String, Object>> selectNoticePopupListOuter() throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("OPEN_LC_VALUE", "outer");
		return noticePopupMapper.selectNoticePopupList(map);
	}
}

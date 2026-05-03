package isry.itgcms.sysmgmt.personalinfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.logging.mapper.SystemLoggingMapper;
import isry.itgcms.sysmgmt.personalinfo.mapper.MyPageMapper;
import isry.itgcms.sysmgmt.personalinfo.mapper.PersonalInfoMapper;
import isry.itgcms.sysmgmt.personalinfo.service.MyPageService;
import isry.itgcms.sysmgmt.personalinfo.service.PersonalInfoService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;

@Service("myPageService")
public class MyPageServiceImpl extends IsryBaseServiceImpl implements MyPageService {

	@Resource(name="myPageMapper")
    private MyPageMapper myPageMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public void saveMyPage(HttpServletRequest request, DataRequest dateRequest) throws Exception {

		String authAppId = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		Integer authMenuNo = request.getParameter("_AUTH_MENU_NO") == null || "".equals(request.getParameter("_AUTH_MENU_NO"))
				? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));

		log.debug("#### _AUTH_APP_ID : " + authAppId);
		log.debug("#### _AUTH_MENU_NO : " + authMenuNo);

		if (authMenuNo == 1150) {  // 내 정보 수정
			return;
		}
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		if (userId != null && !"".equals(userId)) {
			Map<String, Object> map = new HashMap<>();
			map.put("USER_ID", userId);
			map.put("MENU_NO", authMenuNo);
			map.put("SRTNG_SQNCE", 0);
			
			myPageMapper.saveMyPage(map);
		}
	}

	@Override
	public List<Map<String, Object>> selectMyPage(HttpServletRequest request, DataRequest dateRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		List<Map<String, Object>> list = new ArrayList<>();
		
		if (userId != null && !"".equals(userId)) {
			list = myPageMapper.selectMyPage(userId);
		}
		
		return list;
	}
	
	@Override
	public boolean selectCheckedMyPage(HttpServletRequest request, DataRequest dateRequest) throws Exception {
		boolean result = false;

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		//String authAppId = request.getParameter("_AUTH_APP_ID") == null ? "" : request.getParameter("_AUTH_APP_ID");
		Integer authMenuNo = request.getParameter("_AUTH_MENU_NO") == null || "".equals(request.getParameter("_AUTH_MENU_NO"))
				? 0 : Integer.parseInt(request.getParameter("_AUTH_MENU_NO"));

		Map<String, Object> map = new HashMap<>();
		map.put("USER_ID", userId);
		map.put("MENU_NO", authMenuNo);

		Integer count = myPageMapper.selectCheckedMyPage(map);
		if (count != null && count > 0) {
			result = true;
		}
		
		return result;
	}
}

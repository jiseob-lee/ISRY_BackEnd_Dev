package isry.itgcms.sysmgmt.userauth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcms.sysmgmt.pgmemu.mapper.MgmtMenuMapper;
import isry.itgcms.sysmgmt.userauth.mapper.MgmtAuthInsertMapper;
import isry.itgcms.sysmgmt.userauth.service.MgmtAuthInsertService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Service("mgmtAuthInsertService")
public class MgmtAuthInsertServiceImpl implements MgmtAuthInsertService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name="mgmtAuthInsertMapper")
    private MgmtAuthInsertMapper mgmtAuthInsertMapper;
	
	@Resource(name="mgmtMenuMapper")
    private MgmtMenuMapper mgmtMenuMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public void insertGrpAuth(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		ParameterGroup paramUserId = dataRequest.getParameterGroup("dsUserId");
		ParameterGroup paramGroupAuthId = dataRequest.getParameterGroup("dsGroupAuthId");
		
		List<Map<String, String>> listUserId = paramUserId.getAllRowList();
		List<Map<String, String>> listGroupAuthId = paramGroupAuthId.getAllRowList();
		
		for (int k=0; k < listUserId.size(); k++) {
		
			String userId = listUserId.get(k).get("userId");
			
			String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
			Map<String, String> map1 = new HashMap<>();
			map1.put("profile", profile);
			map1.put("USER_ID", userId);
			
			// 사용자별 메뉴가 있는지 체크한다.
			List<Map<String, Object>> menuList = mgmtMenuMapper.selectUserMenu(map1);
						
			// 사용자 권한이 있을 때 처리하는 부분
			if (menuList != null && menuList.size() > 0) {
				
				Map<String, Object> map = new HashMap<>();
				List<String> list = new ArrayList<>();
				for (int l=0; l < listGroupAuthId.size(); l++) {
					list.add(listGroupAuthId.get(l).get("authId"));
				}
				map.put("list", list);
				
				List<Map<String, Object>> authList = mgmtAuthInsertMapper.selectGrpAuth(map);
				
				log.debug("#### authList size : " + authList.size());
				
				Map<String, Object> map2 = new HashMap<>();
				map2.put("USER_ID2", userId2);
				map2.put("USER_ID", userId);
					
				for (int j=0; j < authList.size(); j++) {
					Map<String, Object> map3 = authList.get(j);
					map2.put("MENU_NO", map3.get("MENU_NO"));
					map2.put("INQ_BUTTON_USE_YN", map3.get("INQ_BUTTON_USE_YN"));
					map2.put("DTL_INQ_BUTTON_USE_YN", map3.get("DTL_INQ_BUTTON_USE_YN"));
					map2.put("INITL_BUTTON_USE_YN", map3.get("INITL_BUTTON_USE_YN"));
					map2.put("REG_BUTTON_USE_YN", map3.get("REG_BUTTON_USE_YN"));
					map2.put("MDFCN_BUTTON_USE_YN", map3.get("MDFCN_BUTTON_USE_YN"));
					map2.put("DEL_BUTTON_USE_YN", map3.get("DEL_BUTTON_USE_YN"));
					map2.put("MNG_BUTTON_USE_YN", map3.get("MNG_BUTTON_USE_YN"));
					
					Integer cnt = mgmtAuthInsertMapper.insertGrpAuthToUserAuthCount(map2);
					
					mgmtAuthInsertMapper.insertGrpAuthToUserAuth(map2);

					if (cnt == 0) {
						mgmtAuthInsertMapper.insertGrpAuthToUserAuthHistoryInsert(map2);
					} else {
						mgmtAuthInsertMapper.insertGrpAuthToUserAuthHistoryUpdate(map2);
					}
					
				}

			}
		}
	}
}

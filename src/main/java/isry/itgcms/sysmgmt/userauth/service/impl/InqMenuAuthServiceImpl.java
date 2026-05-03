/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.logging.mapper.SystemLoggingMapper;
import isry.itgcms.sysmgmt.userauth.mapper.InqMenuAuthMapper;
import isry.itgcms.sysmgmt.userauth.service.InqMenuAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : InqMenuAuthServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 2. 17. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 2. 17.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("inqMenuAuthService")
public class InqMenuAuthServiceImpl extends IsryBaseServiceImpl implements InqMenuAuthService {

	@Resource(name="inqMenuAuthMapper")
    private InqMenuAuthMapper inqMenuAuthMapper;

	@Resource(name = "systemLoggingMapper")
	private SystemLoggingMapper systemLoggingMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public Map<String, Object> selectMenuAuth(HttpServletRequest request) throws Exception {

		//HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		//String authAppId = request.getParameter("_AUTH_APP_ID");
		String authMenuNo = request.getParameter("_AUTH_MENU_NO");
		if (authMenuNo == null || "".equals(authMenuNo)) {
			return null;
		}

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		if ("1150".equals(authMenuNo)  // 내 정보 수정
				|| "4803".equals(authMenuNo)) {  // 업무및메뉴권한신청
			Map<String, Object> clearMap = new HashMap<>();
			clearMap.put("INQ_BUTTON_USE_YN", "Y");
			clearMap.put("DTL_INQ_BUTTON_USE_YN", "Y");
			clearMap.put("INITL_BUTTON_USE_YN", "Y");
			clearMap.put("REG_BUTTON_USE_YN", "Y");
			clearMap.put("MDFCN_BUTTON_USE_YN", "Y");
			clearMap.put("DEL_BUTTON_USE_YN", "Y");
			clearMap.put("MNG_BUTTON_USE_YN", "Y");
        	return clearMap;
		}
		
		Map<String, String> map = new HashMap<>();
		map.put("AUTH_MENU_NO", authMenuNo);
		map.put("USER_ID", userId);
		
		//Integer menuId = request.getHeader("_AUTH_APP_ID") == null || "".equals(request.getHeader("_AUTH_APP_ID"))
				//? 0 : systemLoggingMapper.getMenuId(request.getHeader("_AUTH_APP_ID"));

		Map<String, Object> map2 = new HashMap<>();
		map2.put("USER_ID", userId);
		//map2.put("MENU_NO", menuId);
		//map2.put("ACT", "VIEW1");

		// 메뉴 그룹별 권한 외에 사용자별 권한 설정이 있는지 체크한다.
		Integer checkCount = systemLoggingMapper.checkUserRightsExists(map2);
		
		return inqMenuAuthMapper.selectUserAuth(map);
	}
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.userauth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.pgmemu.mapper.MgmtMenuMapper;
import isry.itgcms.sysmgmt.userauth.mapper.InqUserAuthListMapper;
import isry.itgcms.sysmgmt.userauth.service.InqUserAuthListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.ScpDb;

/**
 * 
 * @파일명        : InqUserAuthListServiceImpl.java
 * @프로그램 설명 : 사용자별 권한 조회
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 11. 23. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 11. 23.
 * @수정내용      : 
 * -                
 * -
 */
@Service("inqUserAuthListService")
public class InqUserAuthListServiceImpl extends IsryBaseServiceImpl implements InqUserAuthListService {

	@Resource(name="inqUserAuthListMapper")
    private InqUserAuthListMapper inqUserAuthListMapper;

	@Resource(name="mgmtMenuMapper")
    private MgmtMenuMapper mgmtMenuMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectUserAuth(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup param = dataRequest.getParameterGroup("dmSearchInfo");
		
		String untTaskwkSeCd = null;
		String menuNo = null;
		
		if (param != null) {
			if (param.getValue("UNT_TASKWK_SE_CD") != null && !"".equals(param.getValue("UNT_TASKWK_SE_CD"))) {
				String[] tmpArr = param.getValue("UNT_TASKWK_SE_CD").split("\\|");
				untTaskwkSeCd = tmpArr[0];
				menuNo = tmpArr[1];
			}
		}
		
		Map<String, Object> map = new HashMap<>();

		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
		Map<String, String> map2 = new HashMap<>();
		map2.put("profile", profile);

		String untTaskwk = "";
		
		if (untTaskwkSeCd != null && !"".equals(untTaskwkSeCd)) {
			untTaskwk = untTaskwkSeCd;
			
		} else {
			
			HttpSession session = request.getSession();
			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
			untTaskwk = loginVO.getUntTaskwk();
			
			if (untTaskwk == null || "".equals(untTaskwk) || "ETC".equals(untTaskwk) || "U15".equals(untTaskwk)) {
				List<Map<String, Object>> rootMenuList = mgmtMenuMapper.selectRootMenu();
				if (rootMenuList != null && rootMenuList.size() > 0) {
					untTaskwk = (String)rootMenuList.get(0).get("UNT_TASKWK_SE_CD");
				} else {
					untTaskwk = "0";
				}
			}
		}
		
		map2.put("UNT_TASKWK", untTaskwk);
		map2.put("MENU_NO", menuNo);
		map2.put("JOB", "selectUserAuth");
				
		List<Map<String, Object>> menuList = mgmtMenuMapper.selectMenu(map2);
		StringJoiner strJoiner = new StringJoiner(", ");
		String pivot = "";
		String header = "USER_ID2|USER_ID|USER_NAME";
		String menuId = "USER_ID2|USER_ID|USER_NAME";

		for (int i=0; i < menuList.size(); i++) {
			
			Map<String, Object> menu = menuList.get(i);
			String menuNm = menu.get("MENU_NM") == null ? "널" : (String)menu.get("MENU_NM");
			//if (isNumeric(menuNm.substring(0, 1))) {
				//menuNm = "n" + menuNm;
			//}
			
			//String replaceMeunuNm = menuNm.replace(" ", "").replace("-", "").replace("(", "")
					//.replace(")", "").replace("+", "").replace("/", "");
			
			//header += "|" + replaceMeunuNm;
			//menuId += "|" + menu.getMenuId();
			//pivot = "'" + menu.getMenuId() + "' AS " + replaceMeunuNm;
			header += "|" + menuNm;
			menuId += "|I" + menu.get("MENU_NO");
			pivot = "'" + menu.get("MENU_NO") + "' AS I" + menu.get("MENU_NO");
			
			strJoiner.add(pivot);
		}
		
		
		Map<String, Object> vo = new HashMap<>();
		
		
		if (param != null) {
			vo.put("searchField", param.getValue("searchField"));
			if ("name".equals(param.getValue("searchField"))) {
				ScpDb scpDb = new ScpDb();
				vo.put("searchValue", "____" + param.getValue("searchValue"));
				vo.put("searchValue2", scpDb.scpEncB64(param.getValue("searchValue")));
			} else {
				vo.put("searchValue", param.getValue("searchValue"));
				vo.put("searchValue2", "");
			}
		}
		
		List<Map<String, Object>> menuPivot = null;
		List<Map<String, Object>> menuPivot2 = new ArrayList<>();
		
		vo.put("pivotStr", strJoiner.toString());
		
		log.debug("pivotStr : " + vo.get("pivotStr"));
		
		vo.put("resultList", new ArrayList<HashMap<String, Object>>());
		
		if (pivot != null && !"".equals(pivot)) {
			//menuPivot = inqUserAuthListMapper.selectUserPivot(vo);
			inqUserAuthListMapper.selectUserPivot(vo);
			menuPivot = (List<Map<String, Object>>)vo.get("resultList");
			ScpDb scpDb = new ScpDb();
			for (int i=0; i < menuPivot.size(); i++) {
				Map<String, Object> map1 = menuPivot.get(i);
				String userName = (String)map1.get("USER_NAME");
				String userName2 = (String)map1.get("USER_NAME2");
				String userNamePart = userName.substring(userName.indexOf(" (") + 2, userName.indexOf(")"));
				userNamePart = scpDb.scpDecB64(userNamePart);
				userName = userName.substring(0, userName.indexOf(" (")) + " (" + userNamePart + ")";
				userName2 = scpDb.scpDecB64(userName2);
				map1.put("USER_NAME", userName);
				map1.put("USER_NAME2", userName2);
				menuPivot2.add(map1);
			}
		}
		
		map.put("header", header);
		map.put("menuId", menuId);
		map.put("menuPivot", menuPivot2);
		map.put("menuList", menuList);
		
		return map;
	}

	@Override
	public List<Map<String, Object>> selectUserAuth2(String userId) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("userId", userId);
		List<Map<String, Object>> menuList = inqUserAuthListMapper.selectUserRights2(map);
		return menuList;
	}

	public boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	@Override
	public List<Map<String, Object>> selectUserAuthList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ScpDb scpDb = new ScpDb();
		ParameterGroup param = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> map = param.getSingleValueMap();
		if("name".equals(map.get("searchField"))) {
			map.put("searchValue", scpDb.scpEncB64(map.get("searchValue")));
		}
		List<Map<String, Object>> menuList = inqUserAuthListMapper.selectUserAuthList(map);
		//userNamePart = scpDb.scpDecB64(userNamePart);
		for(Map<String, Object> m : menuList) {
			try {
				m.put("FLNM_ENCPT", scpDb.scpDecB64(String.valueOf(m.get("FLNM_ENCPT"))));
			}catch (Exception e) {
				// TODO: handle exception
				m.put("FLNM_ENCPT", m.get("FLNM_ENCPT"));
			}
		}
		return menuList;
	}
	
	@Override
	public List<Map<String, Object>> selectAuthrtList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ScpDb scpDb = new ScpDb();
		ParameterGroup param = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> map = param.getSingleValueMap();
		List<Map<String, Object>> menuList = inqUserAuthListMapper.selectAuthrtList(map);
		return menuList;
	}
	
}

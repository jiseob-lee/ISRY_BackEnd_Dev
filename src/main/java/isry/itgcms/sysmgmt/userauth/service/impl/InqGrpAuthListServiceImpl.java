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
import isry.itgcms.sysmgmt.userauth.mapper.InqGrpAuthListMapper;
import isry.itgcms.sysmgmt.userauth.service.InqGrpAuthListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * 
 * @파일명        : InqGrpAuthListServiceImpl.java
 * @프로그램 설명 : 그룹별 권한 조회
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
@Service("inqGrpAuthListService")
public class InqGrpAuthListServiceImpl extends IsryBaseServiceImpl implements InqGrpAuthListService {

	@Resource(name="inqGrpAuthListMapper")
    private InqGrpAuthListMapper inqGrpAuthListMapper;

	@Resource(name="mgmtMenuMapper")
    private MgmtMenuMapper mgmtMenuMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectGrpAuth(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmRightsGroup");
		
		String groupName = null;
		String untTaskwkSeCd = null;
		String menuNo = null;
		
		if (param != null) {
			groupName = param.getValue("groupName");
			if (param.getValue("UNT_TASKWK_SE_CD") != null && !"".equals(param.getValue("UNT_TASKWK_SE_CD"))) {
				String[] tmpArr = param.getValue("UNT_TASKWK_SE_CD").split("\\|");
				untTaskwkSeCd = tmpArr[0];
				menuNo = tmpArr[1];
			}
		}
		
		Map<String, Object> map = new HashMap<>();

		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
		Map<String, String> map1 = new HashMap<>();
		map1.put("profile", profile);
		
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
		
		map1.put("UNT_TASKWK", untTaskwk);
		map1.put("MENU_NO", menuNo);
		map1.put("JOB", "selectGrpAuth");
		
		List<Map<String, Object>> menuList = mgmtMenuMapper.selectMenu(map1);
		StringJoiner strJoiner = new StringJoiner(", ");
		String pivot = "";
		String header = "AUTHRT_ID2|AUTHRT_ID|AUTHRT_NM";
		String menuId = "AUTHRT_ID2|AUTHRT_ID|AUTHRT_NM";
		
		for (int i=0; i < menuList.size(); i++) {
			
			Map<String, Object> menu = menuList.get(i);
			String menuNm = menu.get("MENU_NM") == null ? "널" : (String)menu.get("MENU_NM");
			//if (isNumeric(menuNm.substring(0, 1))) {
				//menuNm = "n" + menuNm;
			//}
			
			//String replaceMeunuNm = menuNm.replace(" ", "").replace("-", "").replace("(", "")
					//.replace(")", "").replace("+", "").replace("/", "");
			
			//header += "|" + replaceMeunuNm;
			header += "|" + menuNm;
			menuId += "|I" + menu.get("MENU_NO");
			pivot = "'" + menu.get("MENU_NO") + "' AS I" + menu.get("MENU_NO");
			
			strJoiner.add(pivot);
		}
		
		List<Map<String, Object>> menuPivot = null;
		
		Map<String, Object> vo = new HashMap<>();
		vo.put("pivotStr", strJoiner.toString());
		vo.put("groupName", groupName);
		vo.put("resultList", new ArrayList<HashMap<String, Object>>());
		if (pivot != null && !"".equals(pivot)) {
			inqGrpAuthListMapper.selectGrpPivot(vo);
			menuPivot = (List<Map<String, Object>>)vo.get("resultList");
			log.debug("#### menuPivot : " + menuPivot.size());
			log.debug("#### resultList : " + ((List<Map<String, Object>>)vo.get("resultList")).size());
		}
		
		map.put("header", header);
		map.put("menuId", menuId);
		map.put("menuPivot", menuPivot);
		map.put("menuList", menuList);
		return map;
	}

	@Override
	public List<Map<String, Object>> selectGrpAuth2(String rightId) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("rightId", rightId);
		List<Map<String, Object>> menuList = inqGrpAuthListMapper.selectGrpAuth2(map);
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
	
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.userauth.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.pgmemu.service.MgmtMenuService;
import isry.itgcms.sysmgmt.userauth.mapper.MgmtUserAuthMapper;
import isry.itgcms.sysmgmt.userauth.service.MgmtUserAuthService;
import isry.itgcms.sysmgmt.userauth.vo.UserMenuVO;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.StringUtil;

/**
 * 
 * @파일명        : MgmtUserAuthServiceImpl.java
 *작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 11. 29. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 11. 29.
 * @수정내용      : 
 * -                
 * -
 */

@Service("mgmtUserAuthService")
public class MgmtUserAuthServiceImpl extends IsryBaseServiceImpl implements MgmtUserAuthService {

	@Resource(name="mgmtUserAuthMapper")
    private MgmtUserAuthMapper mgmtUserAuthMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@Resource(name="mgmtMenuService")
	private MgmtMenuService mgmtMenuService;
	
	@Override
	public void saveUserAuth(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		//ParameterGroup param = dataRequest.getParameterGroup("dsRights");

		ParameterGroup paramRightStr = dataRequest.getParameterGroup("dsRightsStr");
		ParameterGroup paramRightKeys = dataRequest.getParameterGroup("dmRightsKeys");
		
		//if (param != null) {
		if (paramRightStr != null && paramRightKeys != null) {
			
			//log.debug("#### size : " + param.getAllRowList().size());
			log.debug("#### size : " + paramRightStr.getAllRowList().size());

			Map<String, String> keysMap = paramRightKeys.getSingleValueMap();
			String[] keysArr = keysMap.get("keysStr").split(",");
			
			
			List<Map<String, String>> list = paramRightStr.getAllRowList();
			//Map<String, String> map  = null;
			
			List<Map<String, Object>> listSave = new ArrayList<>();
			List<Map<String, Object>> listDelete = new ArrayList<>();
			Map<String, Object> mapSave = null;
			
			for (int i=0; i < list.size(); i++) {
				//map = list.get(i);
				//Set<String> set = map.keySet();
				
				log.debug("#### rightsStr : " + list.get(i).get("rightsStr"));
				
				String[] rightsArr = list.get(i).get("rightsStr").split(",");
				
				log.debug("rightsArr.length : " + rightsArr.length + ".");
				
				//for (int j=0; j < rightsArr.length; j++) {
					//log.debug(j + " : " + rightsArr[j] + ".");
				//}
				String userId = rightsArr[0];
				
				//String userId = map.get("USER_ID");

				listSave.clear();
				listDelete.clear();
				
				//Iterator<String> iter = set.iterator();
				
				//log.debug("###############");

				Map<String, Object> paramMap = new HashMap<>();
				
				List<Integer> paramList = new ArrayList<>();
				
				//while (iter.hasNext()) {
				for (int j=0; j < keysArr.length; j++) {
					//String menuId = iter.next();
					String menuId = keysArr[j];
					if (menuId.startsWith("I")) {
						mapSave = new HashMap<>();
						mapSave.put("userId", userId);
						paramMap.put("userId", userId);
						//log.debug("userId : " + userId);
						mapSave.put("menuId", Integer.parseInt(menuId.substring(1)));
						//if ("Y".equals(map.get(menuId))) {
						if ("Y".equals(rightsArr[j])) {
							paramList.add(Integer.parseInt(menuId.substring(1)));
							listSave.add(mapSave);
							//log.debug("save : " + Integer.parseInt(menuId.substring(1)));
						} else {
							listDelete.add(mapSave);
							log.debug("delete : " + Integer.parseInt(menuId.substring(1)));
						}
					}
				}

				paramMap.put("list", paramList);
				List<Integer> menuIdList = new ArrayList<>();
				if (paramList != null && paramList.size() > 0) {
					menuIdList = mgmtUserAuthMapper.getUserAuthExists(paramMap);
				}
				for (int j=0; j < menuIdList.size(); j++) {
					for (int k=listSave.size() - 1; k >= 0; k--) {
						if (Integer.valueOf(listSave.get(k).get("menuId").toString()).intValue() == menuIdList.get(j).intValue()) {
							listSave.remove(k);
							break;
						}
					}
				}
				if (listSave != null && listSave.size() > 0) {
					for (int j=0; j < listSave.size(); j++) {
						Map<String, Object> map = listSave.get(j);
						mgmtUserAuthMapper.saveUserAuth(map);
						map.put("USER_ID2", userId2);
						mgmtUserAuthMapper.saveUserAuthHistory(map);
					}
				}
				//for (int k=0; k < listSave.size(); k++) {
					//mgmtGrpAuthMapper.saveGrpAuth(listSave.get(k));
				//}
				
				paramMap.put("list", listDelete);
				//for (int k=0; k < listDelete.size(); k++) {
				if (listDelete != null && listDelete.size() > 0) {
					for (int j=0; j < listDelete.size(); j++) {
						Map<String, Object> map = listDelete.get(j);
						mgmtUserAuthMapper.deleteUserAuth(map);
						map.put("USER_ID2", userId2);
						mgmtUserAuthMapper.deleteUserAuthHistory(map);
					}
				}
				//}
			}
			
			mgmtUserAuthMapper.deleteUnavailUserAuth();
		}

	}

	@Override
	public void saveUserDetailAuths(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		//ParameterGroup param = dataRequest.getParameterGroup("dsMenuList");
		ParameterGroup param2 = dataRequest.getParameterGroup("dmUserId");
		ParameterGroup param = dataRequest.getParameterGroup("dsMenuStr");
		
		//if (param != null) {
		if (param != null && param2 != null) {

			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
			String userId2 = "";
			if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
				userId2 = loginVO.getId();
			}
			
			List<Map<String, String>> list = param.getAllRowList();
			String userId = param2.getValue("userId");
			log.debug("#### userId : " + userId);
			//Map<String, String> map1  = null;
			Map<String, Object> map2  = null;
			
			Map<String, Object> deleteParam = new HashMap<>();
			deleteParam.put("userId", userId);
			mgmtUserAuthMapper.deleteUserAuthWithId(deleteParam);
			
			Map<String, String> map5 = new HashMap<>();
			map5.put("userId", userId);
			map5.put("USER_ID2", userId2);
			mgmtUserAuthMapper.deleteUserAuthWithIdHistory(map5);
			
			for (int i=0; i < list.size(); i++) {
				//map1 = list.get(i);
				String[] rightsArr = list.get(i).get("menuStr").split(",");
				map2 = new HashMap<>();
				map2.put("USER_ID", userId);
				map2.put("USER_ID2", userId2);
				map2.put("MENU_NO", Integer.parseInt(rightsArr[0])); //map1.get("MENU_NO")));
				map2.put("RIGHT_VIEW", rightsArr[1]); //map1.get("RIGHT_VIEW"));
				map2.put("RIGHT_DETAIL", rightsArr[2]); //map1.get("RIGHT_VIEW"));
				map2.put("RIGHT_INIT", rightsArr[3]); //map1.get("RIGHT_INIT"));
				map2.put("RIGHT_CREATE", rightsArr[4]); //map1.get("RIGHT_CREATE"));
				map2.put("RIGHT_UPDATE", rightsArr[5]); //map1.get("RIGHT_UPDATE"));
				map2.put("RIGHT_DELETE", rightsArr[6]); //map1.get("RIGHT_DELETE"));
				map2.put("RIGHT_MANAGE", rightsArr[7]); //map1.get("RIGHT_MANAGE"));
				if ("Y".equals(rightsArr[8])) {
					mgmtUserAuthMapper.saveUserDetailAuths(map2);
					mgmtUserAuthMapper.saveUserDetailAuthsHistory(map2);
				}else {
					
				}
			}

			Map<String, Object> menuMap  = new HashMap<>();
			List<UserMenuVO> userMenuList = new ArrayList<UserMenuVO>();
			for (int i=0; i < list.size(); i++) {
				UserMenuVO userMenu = new UserMenuVO();
				String[] rightsArr = list.get(i).get("menuStr").split(",");

				userMenu.setMenuNo(Integer.parseInt(rightsArr[0]));
				userMenu.setInqButtonUseYn(rightsArr[1]);
				userMenu.setDtlInqButtonUseYn(rightsArr[2]);
				userMenu.setRegButtonUseYn(rightsArr[4]);
				userMenu.setMdfcnButtonUseYn(rightsArr[5]);
				userMenu.setDelButtonUseYn(rightsArr[6]);
				
				userMenuList.add(userMenu);
			}
			
			ObjectMapper mapper = new ObjectMapper();
			
			String strMenuJson = mapper.writeValueAsString(userMenuList);
			
			menuMap.put("USER_ID", userId);
			menuMap.put("MENU", strMenuJson);
			menuMap.put("USER_ID2", userId2);
			
			// 로그인 메뉴목록 테이블에 업데이트 회수를 1 증가
			mgmtMenuService.increaseMenuUpdateCountByUserId(userId);
		}
	}
	
	/**
	 * @Method명   : deleteAllUserMenuAuth
	 * @param request
	 * @param mapParam
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 2. 
	 * @Method설명 : 사용자별 메뉴 권한 삭제 (사용자아이디 기준)
	 */
	@Override
	public void deleteAllUserMenuAuth(HttpServletRequest request, Map<String, Object> mapParam) throws Exception {
		// 사용자 정보 조회
		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		if (userDetailsVO == null) {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		// 기존 메뉴 권한 전체 삭제
		String userId = (String) mapParam.get("USER_ID");
		
		Map<String, Object> deleteParam = new HashMap<>();
		deleteParam.put("userId", userId);
		
		// 삭제 제외할 메뉴 번호 목록 설정
		if (mapParam.containsKey("NOT_IN_MENU_NOS")) {
			deleteParam.put("NOT_IN_MENU_NOS", mapParam.get("NOT_IN_MENU_NOS"));
		}
		
		mgmtUserAuthMapper.deleteUserAuthWithId(deleteParam);
		
		// 사용자별 메뉴권한 삭제 이력
		Map<String, String> histortParam = new LinkedHashMap<>();
		histortParam.put("userId", userId);
		histortParam.put("USER_ID2", userDetailsVO.getId());
		mgmtUserAuthMapper.deleteUserAuthWithIdHistory(histortParam);
	}
	
	/**
	 * @Method명   : selectUserAuthList
	 * @param request
	 * @param dataRequest
	 * @param dataMapId		Parameter 데이터맵 ID (기본값: dmSearch)
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 24. 
	 * @Method설명 : 사용자별 메뉴 권한 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectUserAuthList(HttpServletRequest request, DataRequest dataRequest,
			String dataMapId) throws Exception {
		
		log.info("##### selectUserAuthList(request, dataRequest, dataMapId) init...");
		
		// Parameter 조회
		if (StringUtil.isEmpty(dataMapId)) {
			dataMapId = "dmSearch";
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup(dataMapId);
		if (paramGroup == null) {
			throw new AppWorksException("Parameter 데이터맵이 없습니다.", Alert.ERROR);
		}
		log.debug("##### selectUserAuthList: {}", paramGroup);
		
		// SAB250 (사용자별 메뉴권한) 목록 조회 
		Map<String, Object> mapParam = new LinkedHashMap<>(paramGroup.getSingleValueMap());
		List<Map<String, Object>> results = mgmtUserAuthMapper.selectUserAuthList(mapParam);
		
		return results;
	}
	
	/**
	 * @Method명   : selectUserAuthList
	 * @param request
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Myeong.Jae.Cheol
	 * @작성일     : 2023. 3. 24. 
	 * @Method설명 : 사용자별 메뉴 권한 목록 조회
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> selectUserAuthList(HttpServletRequest request, Map<String, Object> mapParam)
			throws Exception {
		
		// 사용자아이디 (필수)
		String userId = (String) mapParam.get("USER_ID");
				
		// SAB250 (사용자별 메뉴권한) 목록 조회
		Map<String, Object> srchParamMap = new LinkedHashMap<>();
		srchParamMap.put("USER_ID", userId);					// 메뉴 권한 조회할 사용자아이디
		
		// 권한아이디 목록 취합 - 단위업무별 메뉴별 권한 목록
		List<String> authrtIdList = new ArrayList<>();
		if (mapParam.containsKey("AUTHRT_SE_CDS")) {
			List<String> authrtSeCds = (List<String>) mapParam.get("AUTHRT_SE_CDS");
			authrtIdList.addAll(authrtSeCds);
		} else {
			/* 권한구분코드 (AUTHRT_SE_CD) => 권한아이디로 맵핑 (AUTHRT_ID) */
			String authrtId = (String) mapParam.get("AUTHRT_SE_CD");
			authrtIdList = Arrays.asList(authrtId);
		}
		srchParamMap.put("AUTHRT_IDS", authrtIdList);	// 권한아이디 (복수)
		
		// 제외할 메뉴 번호 목록 설정
		if (mapParam.containsKey("NOT_IN_MENU_NOS")) {
			srchParamMap.put("NOT_IN_MENU_NOS", mapParam.get("NOT_IN_MENU_NOS"));
		}
		
		// SAB250 (사용자별 메뉴권한) 목록 조회 
		List<Map<String, Object>> results = mgmtUserAuthMapper.selectUserAuthList(srchParamMap);
		
		return results;
	}
}

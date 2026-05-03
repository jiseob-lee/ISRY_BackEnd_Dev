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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.pgmemu.service.MgmtMenuService;
import isry.itgcms.sysmgmt.userauth.mapper.InqUserDtlMapper;
import isry.itgcms.sysmgmt.userauth.mapper.MgmtGrpAuthMapper;
import isry.itgcms.sysmgmt.userauth.service.MgmtGrpAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.UserException;

/**
 * 
 * @파일명        : MgmtGrpAuthServiceImpl.java
 * @프로그램 설명 : 그룹별 권한 저장
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 11. 29. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 11. 29.
 * @수정내용      : 
 * -                
 * -
 */

@Service("mgmtGrpAuthService")
public class MgmtGrpAuthServiceImpl extends IsryBaseServiceImpl implements MgmtGrpAuthService {

	@Resource(name="mgmtGrpAuthMapper")
    private MgmtGrpAuthMapper mgmtGrpAuthMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name="inqUserDtlMapper")
	private InqUserDtlMapper inqUserDtlMapper;

	@Resource(name="mgmtMenuService")
	private MgmtMenuService mgmtMenuService;

	@Override
	public void saveGroupAuth(HttpServletRequest request, DataRequest dataRequest) throws Exception {

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
			
			List<Map<String, Integer>> listSave = new ArrayList<>();
			List<Map<String, Integer>> listDelete = new ArrayList<>();
			Map<String, Integer> mapSave = null;
			
			for (int i=0; i < list.size(); i++) {
				//map = list.get(i);
				String[] rightsArr = list.get(i).get("rightsStr").split(",");
				//Set<String> set = map.keySet();
				//Integer rightId = Integer.parseInt(map.get("AUTHRT_ID"));
				Integer rightId = Integer.parseInt(rightsArr[0]);
				
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
						mapSave.put("rightId", rightId);
						paramMap.put("rightId", rightId);
						//log.debug("rightId : " + rightId);
						mapSave.put("menuId", Integer.parseInt(menuId.substring(1)));
						//if ("Y".equals(map.get(menuId))) {
						if ("Y".equals(rightsArr[j])) {
							paramList.add(Integer.parseInt(menuId.substring(1)));
							listSave.add(mapSave);
							//log.debug("save : " + Integer.parseInt(menuId.substring(1)));
						} else {
							listDelete.add(mapSave);
							//log.debug("delete : " + Integer.parseInt(menuId.substring(1)));
						}
					}
				}

				paramMap.put("list", paramList);
				List<Integer> menuIdList = new ArrayList<>();
				if (paramList != null && paramList.size() > 0) {
					menuIdList = mgmtGrpAuthMapper.getGrpAuthExists(paramMap);
				}
				for (int j=0; j < menuIdList.size(); j++) {
					for (int k = listSave.size() - 1; k >= 0; k--) {
						//log.debug("#### listSave : " + listSave.get(k).get("menuId") + ", menuIdList : " + menuIdList.get(j) + ", compare : " + (listSave.get(k).get("menuId").intValue() == menuIdList.get(j).intValue()));
						if (listSave.get(k).get("menuId").intValue() == menuIdList.get(j).intValue()) {
							listSave.remove(k);
							//log.debug("#### menuId " + menuIdList.get(j) + " removed.");
							break;
						}
					}
				}
				if (listSave != null && listSave.size() > 0) {
					for (int j=0; j < listSave.size(); j++) {
						Map<String, Object> map = new HashMap<>(listSave.get(j));
						mgmtGrpAuthMapper.saveGrpAuth(map);
						map.put("DATAA_CHG_SE_CD", "I");
						map.put("USER_ID", userId2);
						mgmtGrpAuthMapper.saveGrpAuthHistory(map);
					}
				}
				//for (int k=0; k < listSave.size(); k++) {
					//mgmtGrpAuthMapper.saveGrpAuth(listSave.get(k));
				//}
				
				//paramMap.put("list", listDelete);
				//for (int k=0; k < listDelete.size(); k++) {
				if (listDelete != null && listDelete.size() > 0) {
					for (int j=0; j < listDelete.size(); j++) {
						//mgmtGrpAuthMapper.deleteGrpAuth(paramMap);
						Map<String, Object> map = new HashMap<>(listDelete.get(j));
						mgmtGrpAuthMapper.deleteGrpAuth(map);
						map.put("DATAA_CHG_SE_CD", "D");
						map.put("USER_ID", userId2);
						mgmtGrpAuthMapper.saveGrpAuthHistory(map);
					}
				}
				//}
			}
			
			mgmtGrpAuthMapper.deleteUnavailGrpAuth();
		}

	}
	
	public void saveGroupDetailAuths(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId2 = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId2 = loginVO.getId();
		}
		
		String strGroupAuthrtSeCd = loginVO.getGroupAuthrtSeCd();
		
		//if (!"100".equals(strGroupAuthrtSeCd) && !"200".equals(strGroupAuthrtSeCd)) {
			//throw new UserException("errors.onlySystemTotalAuth");
		//}
		
		//ParameterGroup param = dataRequest.getParameterGroup("dsMenuList");
		//ParameterGroup param2 = dataRequest.getParameterGroup("dmRightId");
		ParameterGroup param = dataRequest.getParameterGroup("dsMenuStr");
		ParameterGroup param2 = dataRequest.getParameterGroup("dmRightId");
		
		//Map<String, String> map5 = param2.getSingleValueMap();
		//mgmtGrpAuthMapper.deleteGrpAuthAll(map5);
		
		//if (param != null) {
		if (param != null && param2 != null) {

			List<Map<String, String>> list = param.getAllRowList();
			String rightId = param2.getValue("rightId");
			log.debug("#### rightId : " + rightId);
			//Map<String, String> map1  = null;
			Map<String, Object> map2  = null;
			
			List<Map<String, Object>> menuAuthsListUpdate = new ArrayList<>();
			List<Map<String, Object>> menuAuthsListDelete = new ArrayList<>();
					
			for (int i=0; i < list.size(); i++) {
				
				String[] rightsArr = list.get(i).get("menuStr").split(",");
				//map1 = list.get(i);
				//if ("128".equals(rightsArr[0])) {
					//log.debug("#### menuStr : " + list.get(i).get("menuStr"));
				//}
				map2 = new HashMap<>();
				
				map2.put("AUTHRT_ID", Integer.parseInt(rightId));
				map2.put("MENU_NO", Integer.parseInt(rightsArr[0])); //map1.get("MENU_NO")));
				
				map2.put("RIGHT_VIEW", rightsArr[1]); //map1.get("RIGHT_VIEW"));
				map2.put("INQ_BUTTON_USE_YN", rightsArr[1]); //map1.get("RIGHT_VIEW"));
				
				map2.put("RIGHT_DETAIL", rightsArr[2]); //map1.get("RIGHT_VIEW"));
				map2.put("DTL_INQ_BUTTON_USE_YN", rightsArr[2]); //map1.get("RIGHT_VIEW"));
				
				map2.put("RIGHT_INIT", rightsArr[3]); //map1.get("RIGHT_INIT"));
				map2.put("INITL_BUTTON_USE_YN", rightsArr[3]); //map1.get("RIGHT_INIT"));
				
				map2.put("RIGHT_CREATE", rightsArr[4]); //map1.get("RIGHT_CREATE"));
				map2.put("REG_BUTTON_USE_YN", rightsArr[4]); //map1.get("RIGHT_CREATE"));
				
				map2.put("RIGHT_UPDATE", rightsArr[5]); //map1.get("RIGHT_UPDATE"));
				map2.put("MDFCN_BUTTON_USE_YN", rightsArr[5]); //map1.get("RIGHT_UPDATE"));
				
				map2.put("RIGHT_DELETE", rightsArr[6]); //map1.get("RIGHT_DELETE"));
				map2.put("DEL_BUTTON_USE_YN", rightsArr[6]); //map1.get("RIGHT_DELETE"));
				
				map2.put("RIGHT_MANAGE", rightsArr[7]); //map1.get("RIGHT_MANAGE"));
				map2.put("MNG_BUTTON_USE_YN", rightsArr[7]); //map1.get("RIGHT_MANAGE"));
				
				map2.put("USER_ID", userId2);
				
				
				
				if ("N".equals(rightsArr[1]) && "N".equals(rightsArr[2]) 
						&& "N".equals(rightsArr[3]) && "N".equals(rightsArr[4]) 
						&& "N".equals(rightsArr[5]) && "N".equals(rightsArr[6]) 
						&& "N".equals(rightsArr[7])) {
					menuAuthsListDelete.add(map2);
					
			        Map<String, Object> delMap = new HashMap<>();
			        delMap.put("rightId", rightId);
			        delMap.put("menuId", rightsArr[0]);
			        
					mgmtGrpAuthMapper.deleteGrpAuth(delMap);
					map2.put("DATAA_CHG_SE_CD", "D");
					mgmtGrpAuthMapper.saveGroupDetailAuthsHistory(map2);
					
				} else {
					menuAuthsListUpdate.add(map2);
					
					Map<String, Object> delMap = new HashMap<>();
			        delMap.put("rightId", rightId);
			        delMap.put("menuId", rightsArr[0]);
			        
					mgmtGrpAuthMapper.deleteGrpAuth(delMap);
					
					mgmtGrpAuthMapper.saveGrpAuthAll(map2);
					map2.put("DATAA_CHG_SE_CD", "U");
					mgmtGrpAuthMapper.saveGroupDetailAuthsHistory(map2);
				}
			}
			
			
			// 수정된 그룹 권한을 반영할 개인 목록을 구한다.
			List<String> userList = "9999999".equals(rightId) 
					? mgmtGrpAuthMapper.selectGrpAuthPersonsSysMgr() 
					: mgmtGrpAuthMapper.selectGrpAuthPersons(rightId);
			
			
			// 개개인들에게 수정된 그룹 권한을 적용한다.
			for (int i=0; i < userList.size(); i++) {
				
				String user = userList.get(i);

				
				List<Map<String, String>> userInstituteAuthList = inqUserDtlMapper.selectUserInstituteAuthList(user);

				String inAuthrtId = "";
				List<String> inAuthrtList = new ArrayList<>();
				
				boolean sysMngrYn = false;
				
				for (int j=0; j < userInstituteAuthList.size(); j++) {
					Map<String, String> userInstituteAuth = userInstituteAuthList.get(j);
					if (!rightId.equals(userInstituteAuth.get("AUTHRT_SE_CD"))) {
						inAuthrtList.add("'" + userInstituteAuth.get("AUTHRT_SE_CD") + "'");
					}
					if ("Y".equals(userInstituteAuth.get("SYS_MNGR_YN"))) {
						sysMngrYn = true;
					}
				}
				
				if (inAuthrtList.size() > 0) {
					inAuthrtId = "(" + String.join(", ", inAuthrtList);
				} else {
					inAuthrtId = "('0'";
				}
				
				if (sysMngrYn) {
					inAuthrtId += ",'9999999'";
				}
				
				inAuthrtId += ")";
				
				Map<String, String> paramMap = new HashMap<>();
				
				paramMap.put("userId", user);
				paramMap.put("rightId", rightId);
				paramMap.put("userId2", userId2);
				
				paramMap.put("IN_AUTHRT_ID", inAuthrtId);
				
				// 사용자별 메뉴 권한 목록을 구한다.
				//List<Map<String, String>> menuAuthsList = mgmtGrpAuthMapper.selectMenuAuths(paramMap);
				
				for (int j=0; j < menuAuthsListUpdate.size(); j++) {
					
					Map<String, Object> menuAuth = menuAuthsListUpdate.get(j);
					menuAuth.put("USER_ID", user);
					menuAuth.put("USER_ID2", userId2);
					menuAuth.put("IN_AUTHRT_ID", inAuthrtId);
					
					paramMap.put("menuNo", String.valueOf(menuAuth.get("MENU_NO")));
					
					int count = mgmtGrpAuthMapper.selectMenuAuthCount(paramMap);
					
					if (count == 0) {
						
						mgmtGrpAuthMapper.insertPersonalGrpAuth(menuAuth);
						
						menuAuth.put("DATAA_CHG_SE_CD", "I");  // 신규 입력
						
						mgmtGrpAuthMapper.insertPersonalGrpAuthHistory(menuAuth);
						
					} else {
						
						mgmtGrpAuthMapper.updatePersonalGrpAuth(menuAuth);
						
						menuAuth.put("DATAA_CHG_SE_CD", "U");  // 수정
						
						mgmtGrpAuthMapper.insertPersonalGrpAuthHistory(menuAuth);
					}
				}
				//mgmtGrpAuthMapper.savePersonalGrpAuth(paramMap);
				//mgmtGrpAuthMapper.savePersonalGrpAuthHistory(paramMap);

				
				for (int j=0; j < menuAuthsListDelete.size(); j++) {
					
					Map<String, Object> deleteMenuAuth = menuAuthsListDelete.get(j);
					
					deleteMenuAuth.put("USER_ID", user);
					deleteMenuAuth.put("USER_ID2", userId2);
					
					deleteMenuAuth.put("IN_AUTHRT_ID", inAuthrtId);
					
					paramMap.put("menuNo", String.valueOf(deleteMenuAuth.get("MENU_NO")));
					
					int count = mgmtGrpAuthMapper.selectMenuAuthCount(paramMap);
					
					int existsOtherCount = mgmtGrpAuthMapper.selectExistsMenuAuth(deleteMenuAuth);
					
					if (count > 0 && existsOtherCount == 0) {
						
						mgmtGrpAuthMapper.deleteMenuAuths(deleteMenuAuth);

						deleteMenuAuth.put("DATAA_CHG_SE_CD", "D");  // 삭제
						
						mgmtGrpAuthMapper.insertPersonalGrpAuthHistory(deleteMenuAuth);
					}
				}
			}
			
			// 로그인 메뉴목록 테이블에 업데이트 회수를 1 증가
			mgmtMenuService.increaseMenuUpdateCountByRightId(rightId);
		}
	}
}

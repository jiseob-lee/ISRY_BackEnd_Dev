/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.pgmemu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.personalinfo.mapper.MyPageMapper;
import isry.itgcms.sysmgmt.pgmemu.mapper.MgmtMenuMapper;
import isry.itgcms.sysmgmt.pgmemu.service.MgmtMenuService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * 
 * @파일명        : MgmtMenuServiceImpl.java
 * @프로그램 설명 : 메뉴 관리 서비스
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
@Service("mgmtMenuService")
public class MgmtMenuServiceImpl extends IsryBaseServiceImpl implements MgmtMenuService {

	//private int batchCount = 100;
	
	@Resource(name="mgmtMenuMapper")
    private MgmtMenuMapper mgmtMenuMapper;

	@Resource(name = "myPageMapper")
	private MyPageMapper myPageMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public Map<String, Object> selectMenu(HttpServletRequest request) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		
		List<Map<String, Object>> menuList = new ArrayList<>();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		String appId = request.getParameter("_AUTH_APP_ID");
		
		log.info("### appId : " + appId);
		
		String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
		Map<String, String> map1 = new HashMap<>();
		map1.put("profile", profile);
		
		menuList = mgmtMenuMapper.selectMenu(map1);
		
		/*
		if (appId != null && (appId.endsWith("/MenuManage.clx") || appId.endsWith("/MenuView.clx"))) {
			
			// 메뉴 관리, 메뉴 조회에서는 전체 메뉴를 구한다.
			menuList = mgmtMenuMapper.selectMenu(map1);
			
		} else if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {

			map1.put("USER_ID", loginVO.getId());
			
			ObjectMapper mapper = new ObjectMapper();
			
			String userMenuStr = mgmtMenuMapper.selectUserMenuCached(loginVO.getId());
			
			if (userMenuStr == null) {
				
				// 사용자별 메뉴를 우선으로 구한다.
				menuList = mgmtMenuMapper.selectUserMenu(map1);
				
				String jsonString = mapper.writeValueAsString(menuList);

				Map<String, String> paramMap = new HashMap<>();
				paramMap.put("userId", loginVO.getId());
				paramMap.put("jsonString", jsonString);
				
				mgmtMenuMapper.saveUserMenuCache(paramMap);
				
			} else {
				
				menuList = mapper.readValue(userMenuStr, new TypeReference<List<Map<String, Object>>>(){});
				
			}
			
		} else {
			menuList = mgmtMenuMapper.selectMenu(map1);
		}
		*/

		//Map<String,String[]> paramMap = request.getParameterMap();
		//String formData = new Gson().toJson(paramMap);
		//log.debug("#### formData : " + formData);
		String bookmark = request.getParameter("@d1#isBookmark");
		log.debug("#### bookmark : " + bookmark);
		boolean isBookmark = false;
		if (bookmark != null && "1".equals(bookmark)) {
			isBookmark = true;
		}
		
		if (appId == null || !(!isBookmark && appId != null && (appId.endsWith("/MenuManage.clx") || appId.endsWith("/MenuView.clx")))) {
			List<Map<String, Object>> myPageList = "".equals(userId) ? null : myPageMapper.selectMyPage(userId);
			
			if (myPageList != null && myPageList.size() > 2) {
				menuList.addAll(myPageList);
			}
		}
		
		StringJoiner strJoiner = new StringJoiner(", ");
		
		String pivot = "";
		String header = "AUTHRT_ID|AUTHRT_NM";
		String menuId = "AUTHRT_ID|AUTHRT_NM";
		
		for (int i=0; i < menuList.size(); i++) {
			
			Map<String, Object> menu = menuList.get(i);
			String menuNm = menu.get("MENU_NM") == null ? "널" : (String)menu.get("MENU_NM");
			//if (isNumeric(menuNm.substring(0, 1))) {
			if (StringUtils.isNumeric(menuNm.substring(0, 1))) {
				menuNm = "n" + menuNm;
			}
			
			String replaceMeunuNm = menuNm.replace(" ", "").replace("-", "").replace("(", "")
					.replace(")", "").replace("+", "").replace("/", "");
			
			header += "|" + replaceMeunuNm;
			menuId += "|" + menu.get("MENU_NO");
			pivot = "'" + menu.get("MENU_NO") + "' AS \"" + replaceMeunuNm + "\"";
			
			strJoiner.add(pivot);
		}
		
		/*
		List<Map<String, Object>> menuPivot = null;

		Map<String, Object> vo = new HashMap<>();
		vo.put("pivotStr", pivot);
		//vo.put("pivotStr", strJoiner.toString());
		vo.put("resultList", new ArrayList<HashMap<String, Object>>());
		
		if (pivot != null && !"".equals(pivot)) {
			//menuPivot = mgmtMenuMapper.selectMenuPivot(vo);
			mgmtMenuMapper.selectMenuPivot(vo);
			menuPivot = (List<Map<String, Object>>)vo.get("resultList");
		}
		*/

		map.put("header", header);
		map.put("menuId", menuId);
		//map.put("menuPivot", menuPivot);
		map.put("menuList", menuList);
		
		return map;
	}

	@Override
	public void saveMenu(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsMenuListStr");
		ParameterGroup[] parameterGroups = dataRequest.getParameterGroups();
		log.debug("#### parameterGroups.length : " + parameterGroups.length);
		for (int i=0; i < parameterGroups.length; i++) {
			log.debug("#### " + i + " : " + parameterGroups[i].getId());
		}
		if (parameterGroup == null) {
			log.debug("#### dsMenuList is null.");
		} else {
			log.debug("#### dsMenuList is not null.");
		}
		
		List<Map<String, String>> allRowsStr = parameterGroup.getAllRowList();
		List<Map<String, String>> allRows = new ArrayList<>();
		log.debug("#### allRowsStr.size() : " + allRowsStr.size());
		
		String untTaskwk = "";
		int topMenu = 0;
		
		for (int i=0; i < allRowsStr.size(); i++) {
			
			String str = allRowsStr.get(i).get("str");
			log.debug("################# " + str);
			String[] strArr = str.split("\\|", -1);
			Map<String, String> row = new HashMap<>();
			row.put("MENU_NO",  strArr[0]);
			row.put("MENU_NM",  strArr[1]); 
			row.put("URL_ADDR",  strArr[2]);
			row.put("PROGRM_NM",  strArr[3]); 
			row.put("UP_MENU_ID",  strArr[4]); 
			row.put("UP_MENU_NM",  strArr[5]); 
			row.put("MENU_LEVELA_NO",  strArr[6]); 
			row.put("RM_CN",  strArr[7]); 
			row.put("PROGRM_ID",  strArr[8]); 
			row.put("PARA_CN",  strArr[9]); 
			row.put("TOP_MENU_ID",  strArr[10]); 
			row.put("TOP_MENU_NM",  strArr[11]); 
			row.put("TWO_DEPTH_MENU_ID",  strArr[12]); 
			row.put("TWO_DEPTH_MENU_NM",  strArr[13]);
			row.put("ROW_STATE_STR",  strArr[14]);
			
			if (topMenu == 0 || topMenu != Integer.parseInt(strArr[10])) {
				topMenu = Integer.parseInt(strArr[10]);
				untTaskwk = mgmtMenuMapper.getUntTaskwk(topMenu);
				if (untTaskwk == null || "".equals(untTaskwk)) {
					untTaskwk = "ETC";
				}
			}
			
			row.put("UNT_TASKWK_SE_CD",  untTaskwk);
			
			allRows.add(row);
		}
		
		parameterGroup = dataRequest.getParameterGroup("dsDeletedMenuId");
		
		List<Map<String, String>> deletedRows = new ArrayList<>();
		
		if (parameterGroup != null) {
			deletedRows = parameterGroup.getAllRowList();
		}
		
		List<Integer> listMenuId = new ArrayList<>();
		for (int i=0; i < allRows.size(); i++) {
			listMenuId.add(Integer.parseInt(allRows.get(i).get("MENU_NO")));
		}
		for (int i=0; i < deletedRows.size(); i++) {
			listMenuId.add(Integer.parseInt(deletedRows.get(i).get("MENU_NO")));
		}
		if (listMenuId != null && listMenuId.size() > 0) {
			mgmtMenuMapper.deleteAllMenu(listMenuId);
		}
		
		for (int i=0; i < allRows.size(); i++) {
			Map<String, Object> map = new HashMap<>(allRows.get(i));
			map.put("USER_ID", userId);
			map.put("INDEX", i + 1);
			map.put("DATAA_CHG_SE_CD", "U".equals((String)map.get("ROW_STATE_STR")) ? "U" : "I");	//데이터변경 구분코드 "신규"
			//mgmtMenuMapper.saveMenu(allRows);
			mgmtMenuMapper.saveMenu(map);
			mgmtMenuMapper.insertMenuHistory(map);
		}
		
		if(deletedRows != null && deletedRows.size() > 0) {
			//20230303-SAB300 삭제, SAB301이력관리 추가
			mgmtMenuMapper.insertSAB301(deletedRows);
			mgmtMenuMapper.deleteSAB300(deletedRows);
			//20230303-SAB250 삭제, SAB251 이력관리 추가
			mgmtMenuMapper.insertSAB251(deletedRows);
			mgmtMenuMapper.deleteSAB250(deletedRows);
			//20230303-SAB210 삭제
			//이슈가 있어서 삭제 불가
//			mgmtMenuMapper.deleteSAB210(deletedRows);
		}
		
		// 로그인 메뉴목록 테이블에 업데이트 회수를 1 증가
		mgmtMenuMapper.updateUserMenuUpdateCountIncreaseAll();
		
		/*
		Iterator<ParameterRow> allRows = parameterGroup.getAllRows();

		int maxMenuId = mgmtMenuMapper.selectMaxMenuId();

		//mgmtMenuMapper.deleteAllMenu();
		
		Connection con = null;
        PreparedStatement pstmt = null ;

        String sql = "INSERT INTO MENU "
        		+ "		( "
        		+ "			MENU_NO, "
        		+ "			MENU_NM, "
        		+ "			CALL_PAGE, "
        		+ "			UP_MENU_ID, "
        		+ "			ICON, "
        		+ "			MENU_LVL, "
        		+ "			DESCR, "
        		+ "			USE_YN, "
        		+ "			PGM_ID, "
        		+ "			PARAMETER, "
        		+ "			LIST_ORDER "
        		+ "		) "
        		+ "		VALUES "
        		+ "		( "
        		+ "			?, "
        		+ "			?, "
        		+ "			?, "
        		+ "			?, "
        		+ "			?, "
        		+ "			?, "
        		+ "			?, "
        		+ "			?, "
        		+ "			?, "
        		+ "			?, "
        		+ "			? "
        		+ "		) ";

		Map<String, String> map = null;

		try {

        	DataSource ds = (DataSource)ApplicationContextProvider.getApplicationContext().getBean("egov.dataSource");
            con = ds.getConnection();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM MENU");
            
            pstmt = con.prepareStatement(sql) ;

			for (int i=0; allRows.hasNext(); i++) {

				map = allRows.next().toMap();
				
				LOGGER.debug("all : " + map.get("menuNm") + " - " + map.get("callPage") + " - " + map.get("parameter") + " - " + map.get("upMenuId"));
				
				//mgmtMenuMapper.saveMenu(map);
				int menuId = map.get("menuId") == null || "".equals(map.get("menuId")) || "null".equals(map.get("menuId")) ? ++maxMenuId : Integer.parseInt(map.get("menuId"));
				
	            pstmt.setInt(1, menuId);
	        	pstmt.setString(2, map.get("menuNm"));
				pstmt.setString(3, map.get("callPage"));
				pstmt.setInt(4, map.get("upMenuId") == null || "".equals(map.get("upMenuId")) || "null".equals(map.get("upMenuId")) || "-1".equals(map.get("upMenuId")) ? 0 : Integer.parseInt(map.get("upMenuId")));
				pstmt.setString(5, map.get("icon"));
				pstmt.setInt(6, Integer.parseInt(map.get("menuLvl")));
				pstmt.setString(7, map.get("desc"));
				//pstmt.setInt(8, map.get("topMenuId") == null || "".equals(map.get("topMenuId")) || "null".equals(map.get("topMenuId")) ? menuId : Integer.parseInt(map.get("topMenuId")));
				pstmt.setString(8, map.get("useYn"));
				pstmt.setString(9, map.get("pgmId"));
				pstmt.setString(10, map.get("parameter"));
				pstmt.setInt(11, i + 1);
	
	            // addBatch에 담기
	            pstmt.addBatch();
	
	            // 파라미터 Clear
	            pstmt.clearParameters();
	
	            //if ((i % 300) == 0) {
	            	//writer.print("preventtimeout"); writer.flush(); System.out.println("write");
	            //}
	
	            // OutOfMemory를 고려하여 만건 단위로 커밋
	            if ((i % batchCount) == 0) {
	
	                // Batch 실행
	                pstmt.executeBatch();
	
	                // Batch 초기화
	                pstmt.clearBatch();
	
	                // 커밋
	                con.commit();
	            }
	        }
	
	        // 커밋되지 못한 나머지 구문에 대하여 커밋
	        pstmt.executeBatch();
	        con.commit();
	
	    } catch (Exception e) {

	    	e.printStackTrace();
	
	        try {
	            con.rollback();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	        
	        throw e;
	
	    } finally {
	        if (pstmt != null) try { pstmt.close(); pstmt = null; } catch(SQLException ex){}
	        if (con != null) try { con.close(); con = null; } catch(SQLException ex){}
	    }
	    */

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
	public Map<String, Integer> selectMaxMenuId() throws Exception {
		Map<String, Integer> map = new HashMap<>();
		map.put("maxMenuId", mgmtMenuMapper.selectMaxMenuId());
		return map;
	}
	
	@Override
	public List<Map<String, Object>> selectRootMenu() throws Exception {
		return mgmtMenuMapper.selectRootMenu();
	}

	@Override
	public String selectTopMenuCd(String topMenuId) throws Exception {
		return mgmtMenuMapper.selectTopMenuCd(topMenuId);
	}
	
	@Override
	public void increaseMenuUpdateCountByRightId(String rightId) throws Exception {
		List<String> userIdList = mgmtMenuMapper.selectUserMenuIdList(rightId);
		for (int i=0; i < userIdList.size(); i++) {
			increaseMenuUpdateCountByUserId(userIdList.get(i));
		}
	}
	
	@Override
	public void increaseMenuUpdateCountByUserId(String userId) throws Exception {
		Integer userMenuCount = mgmtMenuMapper.selectUserMenuCount(userId);
		if (userMenuCount == 1) {
			mgmtMenuMapper.updateUserMenuUpdateCountIncrease(userId);
		}
	}
	
}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.pgmemu.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.pgmemu.service.MgmtMenuService;
import isry.itgcms.sysmgmt.userauth.service.InqMenuAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService3;

/**
 * 
 * @파일명        : MgmtMenuController.java
 * @프로그램 설명 : 메뉴 관리
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 11. 23. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 11. 23.
 * @수정내용      : 2022. 11. 11. 사용자 정보를 맵으로 담는 메서드 생성
 * -                
 * -
 */
@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/pgmemu")
public class MgmtMenuController extends IsryBaseController {

	/**
	 * 메뉴 관리 서비스
	 */
	@Resource(name = "mgmtMenuService")
	private MgmtMenuService mgmtMenuService;

	/**
	 * 매뉴 권한 조회 서비스
	 */
	@Resource(name = "inqMenuAuthService")
	private InqMenuAuthService inqMenuAuthService;
	
	/**
	 * 공통코드 서비스
	 */
	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Autowired
	private RedisService3 redisService;
	
	/**
	 * 
	 * @Method명   : selectMenu
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 메뉴를 조회한다.
	 */
	@RequestMapping(value = "/selectMenu.do")
	public View selectMenu(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		Map<String, Object> map = mgmtMenuService.selectMenu(request);
		
		dataRequest.setResponse("header", map.get("header"));
		dataRequest.setResponse("menuId", map.get("menuId"));
		dataRequest.setResponse("dsMenuList", map.get("menuList"));

		Map<String, Object> loginMap = selectUserMap(request);
		
		dataRequest.setResponse("dmUserInfo", loginMap);
		
		dataRequest.setResponse("dsTaskWorkDbList", mgmtCmmnCodeService.selectCommonCodeUnit("MAIN_DASHBOARDS_BREAKDOWN", userVo == null ? "" : userVo.getUntTaskwk()));
		dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명   : selectUserInfo
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 사용자 정보를 조회한다.
	 */
	@RequestMapping(value = "/selectUserInfo.do")
	public View selectUserInfo(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		Map<String, Object> loginMap = selectUserMap(request);
		dataRequest.setResponse("dmUserInfo", loginMap);
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectUserMap
	 * @param request
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 사용자 정보를 맵으로 담는다.
	 */
	private Map<String, Object> selectUserMap(HttpServletRequest request) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		Map<String, Object> loginMap = new HashMap<>();
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginMap.put("USER_ID", loginVO.getId());
			loginMap.put("USER_NM", loginVO.getUserName());
			loginMap.put("AGE", loginVO.getAge());
			loginMap.put("AGENCY_CONTACTS", loginVO.getAgencyContacts());
			loginMap.put("BIRTHDATE", loginVO.getBirthdate());
			loginMap.put("EMAIL", loginVO.getEmail());
			loginMap.put("GENDER", loginVO.getGender());
			loginMap.put("L_LOGIN_IP", loginVO.getIp());
			loginMap.put("MEMBER_TYPE", loginVO.getMemberType());
			loginMap.put("MOBILE", loginVO.getMobile());
			loginMap.put("RGN_CD", loginVO.getRgnSeCd());
			loginMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwkSeCd());
			loginMap.put("TOP_MENU_NO", loginVO.getTopMenuNo());
			loginMap.put("ENFSN_NO", loginVO.getEnfsnNo());
			
			loginMap.put("INST_NO", loginVO.getInstNo());
			loginMap.put("INST_NM", loginVO.getInstNm());
			
			loginMap.put("INST_TYPE_SE_CD", loginVO.getInstTypeSeCd());
			
			loginMap.put("WRD_TELNO", loginVO.getWrdTelno());
			loginMap.put("SIDO_NM", loginVO.getSidoNm());
			loginMap.put("SIGUNGU_NM", loginVO.getSigunguNm());
			
			loginMap.put("DEPT_CD", loginVO.getDeptCd());
			loginMap.put("DEPT_NM", loginVO.getDeptNm());
			
			loginMap.put("USER_INST_NO", loginVO.getUserInstNo());
			loginMap.put("YNGBGS_PRTCR_NO", loginVO.getYngbgsPrtcrNo());
			loginMap.put("INDV_IDNTFC_NO", loginVO.getIndvIdntfcNo());
			
			loginMap.put("MANAGER_YN", loginVO.getManagerYn());
			
			String profile = EgovProperties.getProperty("globals", "isry.globals.profile");
			
			loginMap.put("PROFILE", profile);
			
			loginMap.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());	// 그룹권한구분코드
			//loginMap.put("GROUP_AUTHRT_SE_CD", "200");	// 그룹권한구분코드
			loginMap.put("AUTHRT_SE_CD", loginVO.getAuthrtSeCd());				// 권한구분코드
		}
		
		return loginMap;
	}
	
	/**
	 * 
	 * @Method명   : selectButtonUseYn
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 버튼의 사용 여부를 조회한다.
	 */
	@RequestMapping(value = "/selectButtonUseYn.do")
	public View selectButtonUseYn(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));
		
		return new JSONDataView();
	}
		
	/**
	 * 
	 * @Method명   : selectRootMenu
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 루트 메뉴를 조회한다.
	 */
	@RequestMapping(value = "/selectRootMenu.do")
	public View selectRootMenu(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dsRootMenuList", mgmtMenuService.selectRootMenu());
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : saveMenu
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 메뉴를 저장한다.
	 */
	@RequestMapping(value = "/saveMenu.do")
	public View saveMenu(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		mgmtMenuService.saveMenu(request, dataRequest);
		
		return new JSONDataView();
	}
	
	/**
	 * 
	 * @Method명   : selectMaxMenuId
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 메뉴의 신규 아이디를 구한다.
	 */
	@RequestMapping(value = "/selectMaxMenuId.do")
	public View selectMaxMenuId(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		
		dataRequest.setResponse("dmMaxMenuId", mgmtMenuService.selectMaxMenuId());
		
		return new JSONDataView();
	}

	/**
	 * 
	 * @Method명   : setTopMenuCd
	 * @param request
	 * @param response
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Ji.Seob
	 * @작성일     : 2022. 11. 11. 
	 * @Method설명 : 최상위 메뉴 코드를 설정한다.
	 */
	@RequestMapping(value = "/setTopMenuCd.do")
	public View setTopMenuCd(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String topMenuId = "";
		String untTaskwk = "";
		ParameterGroup param = dataRequest.getParameterGroup("dmTopMenu");
		
		if (param != null) {
			topMenuId = param.getValue("TOP_MENU_ID");
		}
		
		if (topMenuId != null && !"".equals(topMenuId)) {

			untTaskwk = mgmtMenuService.selectTopMenuCd(topMenuId);

			HttpSession session = request.getSession();
			String redisKey = "LOGIN||SESSION||" + (String)session.getAttribute("userId") + "||" + session.getId();
			UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
			
			if (untTaskwk != null && !"".equals(untTaskwk)) {

				if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
					loginVO.setUntTaskwk(untTaskwk);
					
					String profile = EgovProperties.getProperty("globals", "isry.globals.profile");

					//if ("local".equals(profile) || "pre".equals(profile)) {
					if ("local".equals(profile)) {
						session.setAttribute("loginVO", loginVO);
					} else {
						redisService.insertRedisMap(redisKey, loginVO.getMap());
					}
				}
				
			// 단위 업무 구분 코드가 없을 때는 ETC 로 세팅한다.
			} else {
				
				untTaskwk = "ETC";

				if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
					loginVO.setUntTaskwk(untTaskwk);
					
					String profile = EgovProperties.getProperty("globals", "isry.globals.profile");

					//if ("local".equals(profile) || "pre".equals(profile)) {
					if ("local".equals(profile)) {
						session.setAttribute("loginVO", loginVO);
					} else {
						redisService.insertRedisMap(redisKey, loginVO.getMap());
					}
				}
				
			}
		}
		
		Map<String, String> map = new HashMap<>();
		map.put("TOP_MENU_ID", topMenuId);
		map.put("UNT_TASKWK", untTaskwk);
		
		dataRequest.setResponse("dmTopMenu", map);
		
		return new JSONDataView();
	}
	
}

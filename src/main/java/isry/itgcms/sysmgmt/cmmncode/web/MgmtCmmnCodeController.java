/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.
 * Copyright (C) 2021 by MOGEF , All right All right reserved.
 ******************************************************************************************/
package isry.itgcms.sysmgmt.cmmncode.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.spring.JSONDataView;

import isry.base.IsryBaseController;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.pgmemu.service.MgmtMenuService;
import isry.itgcms.sysmgmt.userauth.service.InqMenuAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.IP;

/**
 * @파일명        : MgmtCmmnCodeController.java
 * @프로그램 설명 : 공통 코드 관리
 * -
 * -
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 12. 30.
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 12. 30.
 * @수정내용      :
 * -
 * -
 */
@Controller
@RequestMapping(value = "/isry/itgcm/sysmgmt/cmmncode")
public class MgmtCmmnCodeController extends IsryBaseController {

	@Resource(name = "mgmtCmmnCodeService")
	private MgmtCmmnCodeService mgmtCmmnCodeService;

	@Resource(name = "mgmtMenuService")
	private MgmtMenuService mgmtMenuService;

	@Resource(name = "inqMenuAuthService")
	private InqMenuAuthService inqMenuAuthService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	@RequestMapping(value = "/selectMaxCodeId.do")
	public View selectMaxCodeId(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dmMaxCodeId", mgmtCmmnCodeService.selectMaxCodeId());

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectMaxCodeValueId.do")
	public View selectMaxCodeValueId(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dmMaxCodeValueId", mgmtCmmnCodeService.selectMaxCodeValueId());

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectCode.do")
	public View selectCode(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		//Map<String, Object> map = mgmtCmmnCodeService.selectCode();
		//dataRequest.setResponse("header", map.get("header"));
		//dataRequest.setResponse("menuId", map.get("menuId"));
		//dataRequest.setResponse("dsAllMenu", map.get("menuPivot"));
		//List<Map<String, Object>> list = mgmtMenuService.selectRootMenu();
		List<Map<String, Object>> list = mgmtCmmnCodeService.selectCommonCode("UNT_SYS_SE_CD");
		//List<Map<String, Object>> list2 = new ArrayList<>();
		//MenuVO menuVO = new MenuVO();
		//menuVO.setMenuId(null);
		//menuVO.setMenuNm("- 전체 -");
		//menuVO.setMenuLvl(1);
		//menuVO.setUpMenuId("0");
		//menuVO.setTopMenuId(0);
		//Map<String, Object> map = new HashMap<>();
		//map.put("MENU_NO", null);
		//map.put("MENU_NM", "- 전체 -");
		//map.put("MENU_LVL", 1);
		//map.put("UP_MENU_ID", "0");
		//map.put("TOP_MENU_ID", 0);
		//list2.add(map);
		//for (int i=0; i < list.size(); i++) {
			//list2.add(list.get(i));
		//}
		dataRequest.setResponse("dsCommonCode", mgmtCmmnCodeService.selectCode(dataRequest));
		dataRequest.setResponse("dsUnitSystem", list);

		dataRequest.setResponse("dmButtonUseYn", inqMenuAuthService.selectMenuAuth(request));

		return new JSONDataView();
	}

	@RequestMapping(value = "/saveCode2.do")
	public View saveCode2(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String ip = IP.getClientIP(request);

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		log.debug("#### This is saveCode.do.");

		//String[] arr = dataRequest.getParameterNames();
		//if (arr == null) {
			//log.debug("####1 arr is null.");
		//} else {
			//log.debug("#### arr.size : " + arr.length);
			//for (int i=0; i < arr.length; i++) {
				//log.debug("####1 ParameterName : " + i + " : " + arr[i]);
			//}
		//}

		//List<String> list = dataRequest.getParameterGroupNames();
		//if (list != null) {
			//log.debug("#### list.size : " + list.size());
			//for (int i=0; i < list.size(); i++) {
				//log.debug("####1 ParameterGroupName : " + i + " : " + list.get(i));
			//}
		//} else {
			//log.debug("####1 list is null.");
		//}

		// 10.33.2.62 : 이지섭
		// 10.33.2.73 : 이명상
		// 10.33.2.171 : 반재정
		// 10.33.2.192 : 이인성
		// 10.33.2.70 : 윤희성
		// 10.33.2.94 : 명재철
		// 10.33.2.170 : 송태수
		// 211.217.107.152 : 사무실인터넷
		// 10.114.131.100 : 부산 행정망
		// 211.104.243.46 : 부산 인터넷
		//if ("10.33.2.47".equals(ip) || "10.33.2.91".equals(ip) || "10.33.2.62".equals(ip) || "10.33.2.73".equals(ip)
				//|| "10.33.2.97".equals(ip) || "10.33.2.171".equals(ip) || "10.33.2.170".equals(ip)
				//|| "10.33.2.192".equals(ip)|| "10.33.2.70".equals(ip) || "10.33.2.94".equals(ip)
				//|| "211.217.107.152".equals(ip) || "10.114.131.100".equals(ip) || "211.104.243.46".equals(ip)) {
			mgmtCmmnCodeService.saveCode(request, dataRequest);
		//} else {
			//Map<String, String> msgMap = new HashMap<>();
			//msgMap.put("msg", "권한이 없는 IP 입니다.");
			//dataRequest.setResponse("dmMsg", msgMap);
		//}

		return new JSONDataView();
	}

	@RequestMapping(value = "/saveCodeValue.do")
	public View saveCodeValue(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		String ip = IP.getClientIP(request);

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		//if ("10.33.2.47".equals(ip) || "10.33.2.91".equals(ip) || "10.33.2.62".equals(ip) || "10.33.2.73".equals(ip)
				//|| "10.33.2.97".equals(ip) || "10.33.2.171".equals(ip) || "10.33.2.170".equals(ip)
				//|| "10.33.2.192".equals(ip)|| "10.33.2.70".equals(ip) || "10.33.2.94".equals(ip)
				//|| "211.217.107.152".equals(ip) || "10.114.131.100".equals(ip) || "211.104.243.46".equals(ip)) {
			mgmtCmmnCodeService.saveCodeValue(request, dataRequest);
		//} else {
			//Map<String, String> msgMap = new HashMap<>();
			//msgMap.put("msg", "권한이 없는 IP 입니다.");
			//dataRequest.setResponse("dmMsg", msgMap);
		//}

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectCodeValue.do")
	public View selectCodeValue(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {
		dataRequest.setResponse("dsCommonCodeValue", mgmtCmmnCodeService.selectCodeValue(dataRequest));
		return new JSONDataView();
	}

	@RequestMapping(value = "/selectUnitSystem.do")
	public View selectUnitSystem(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsUnitSystem", mgmtCmmnCodeService.selectUnitSystem(dataRequest));
		dataRequest.setResponse("dsRootMenuList", mgmtMenuService.selectRootMenu());

		dataRequest.setResponse("dsUnitSystem2", mgmtCmmnCodeService.selectCommonCode("UNT_SYS_SE_CD"));

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectOrgUnitSystem.do")
	public View selectOrgUnitSystem(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsOrgUnitSystem", mgmtCmmnCodeService.selectOrgUnitSystem(dataRequest));
		dataRequest.setResponse("dsUnitSystemList", mgmtCmmnCodeService.selectCommonCode("UNT_SYS_SE_CD"));

		return new JSONDataView();
	}

	@RequestMapping(value = "/saveUnitSystem.do")
	public View saveUnitSystem(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		//int i = 1/0;

		mgmtCmmnCodeService.saveUnitSystem(request, dataRequest);

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectCommonCode.do")
	public View selectCommonCode(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmCommonCodeParam");

		String codeId = null;
		String upCmmnsCdValue = null;
		String dsName = null;

		if (param != null) {
			if (param.getValue("codeId") != null && !"".equals(param.getValue("codeId"))) {
				codeId = param.getValue("codeId");
			}
			if (param.getValue("upCmmnsCdValue") != null && !"".equals(param.getValue("upCmmnsCdValue"))) {
				upCmmnsCdValue = param.getValue("upCmmnsCdValue");
			}
			if (param.getValue("dsName") != null && !"".equals(param.getValue("dsName"))) {
				dsName = param.getValue("dsName");
			}
		}

		if (codeId == null) {
			String param1 = request.getParameter("codeId");
			if (param1 != null && !"".equals(param1)) {
				codeId = param1;
			}
		}

		if (upCmmnsCdValue == null) {
			String param3 = request.getParameter("upCmmnsCdValue");
			if (param3 != null && !"".equals(param3)) {
				upCmmnsCdValue = param3;
			}
		}

		if (dsName == null) {
			String param2 = request.getParameter("dsName");
			if (param2 != null && !"".equals(param2)) {
				dsName = param2;
			}
		}

		if (dsName != null && !"".equals(dsName)) {
			dataRequest.setResponse(dsName, codeId == null ? null : mgmtCmmnCodeService.selectCommonCode(codeId, upCmmnsCdValue));
		}

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectCommonCodeUnit.do")
	public View selectCommonCodeUnit(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		List<Map<String, String>> param = dataRequest.getParameterGroup("dsCommParam").getAllRowList();

		for (Map<String, String> map : param) {

			String codeId = "";
			String unitCode = "";
			String dsName = "";

			if (map != null) {
				if (map.get("CMMNS_ID") != null && !"".equals(map.get("CMMNS_ID"))) {
					codeId = map.get("CMMNS_ID");
				}
				if (map.get("UNIT_CODE") != null && !"".equals(map.get("UNIT_CODE"))) {
					unitCode = map.get("UNIT_CODE");
				}
				if (map.get("DS_NAME") != null && !"".equals(map.get("DS_NAME"))) {
					dsName = map.get("DS_NAME");
				}
			}

			if (codeId == null) {
				String param1 = request.getParameter("codeId");
				if (param1 != null && !"".equals(param1)) {
					codeId = param1;
				}
			}

			if (unitCode == null) {
				String param3 = request.getParameter("unitCode");
				if (param3 != null && !"".equals(param3)) {
					unitCode = param3;
				}
			}

			if (dsName == null) {
				String param2 = request.getParameter("dsName");
				if (param2 != null && !"".equals(param2)) {
					dsName = param2;
				}
			}

			if (dsName != null && !"".equals(dsName)) {
				dataRequest.setResponse(dsName, codeId == null ? null : mgmtCmmnCodeService.selectCommonCodeUnit(codeId, unitCode));
			}

		}

		return new JSONDataView();
	}

	@RequestMapping(value = "/selectCommonCodeGroup.do")
	public View selectCommonCodeGroup(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		ParameterGroup param = dataRequest.getParameterGroup("dmSystemId");
		Integer systemId = null;

		if (param != null) {
			if (param.getValue("systemId") != null && !"".equals(param.getValue("systemId"))) {
				systemId = Integer.valueOf(param.getValue("systemId"));
			}
		}

		if (systemId == null) {
			String param1 = request.getParameter("systemId");
			if (param1 != null && !"".equals(param1)) {
				systemId = Integer.valueOf(param1);
			}
		}

		dataRequest.setResponse("dsCommonCodeList", systemId == null ? null : mgmtCmmnCodeService.selectCommonCodeList(systemId));
		dataRequest.setResponse("dsCommonCodeTotalList", systemId == null ? null : mgmtCmmnCodeService.selectCommonCodeTotalList(systemId));

		return new JSONDataView();
	}

	@RequestMapping(value = "/checkCodeIdDuplicate.do")
	public View checkCodeIdDuplicate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dmCheckCodeIdDuplicate", mgmtCmmnCodeService.selectCodeIdDuplicate(dataRequest));

		return new JSONDataView();
	}

	@RequestMapping(value = "/getSysDate.do")
	public View getSysDate(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dmSysDate", mgmtCmmnCodeService.getSysDate("YYYY-MM-DD"));

		return new JSONDataView();
	}


	@RequestMapping(value = "/selectCodeValueUnitSystem.do")
	public View selectCodeValueUnitSystem(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> list = mgmtCmmnCodeService.selectCodeValueUnitSystem(dataRequest);

		dataRequest.setResponse("dsCodeValueUnitSystem", list);

		dataRequest.setResponse("dsUnitSystemList", mgmtCmmnCodeService.selectCommonCode("UNT_TASKWK_SE_CD"));

		return new JSONDataView();
	}

	@RequestMapping(value = "/saveCodeValueUnitSystem.do")
	public View saveCodeValueUnitSystem(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		mgmtCmmnCodeService.saveCodeValueUnitSystem(request, dataRequest);

		return new JSONDataView();
	}


	@RequestMapping(value = "/selectCommonCodeJoinRights.do")
	public View selectCommonCodeJoinRights(HttpServletRequest request, HttpServletResponse response, DataRequest dataRequest) throws Exception {

		dataRequest.setResponse("dsDividingRoles", mgmtCmmnCodeService.selectCommonCodeJoinRights());

		return new JSONDataView();
	}

}

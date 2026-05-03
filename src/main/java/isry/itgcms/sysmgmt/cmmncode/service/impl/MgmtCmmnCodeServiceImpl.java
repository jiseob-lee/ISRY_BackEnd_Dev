/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.cmmncode.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.cmmncode.mapper.MgmtCmmnCodeMapper;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : MgmtCmmnCodeServiceImpl.java
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
@Service("mgmtCmmnCodeService")
public class MgmtCmmnCodeServiceImpl extends IsryBaseServiceImpl implements MgmtCmmnCodeService {

	@Resource(name="mgmtCmmnCodeMapper")
    private MgmtCmmnCodeMapper mgmtCmmnCodeMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public Map<String, Integer> selectMaxCodeId() throws Exception {
		Map<String, Integer> map = new HashMap<>();
		map.put("maxCodeId", mgmtCmmnCodeMapper.selectMaxCodeId());
		return map;
	}

	@Override
	public Map<String, Integer> selectMaxCodeValueId() throws Exception {
		Map<String, Integer> map = new HashMap<>();
		map.put("maxCodeValueId", mgmtCmmnCodeMapper.selectMaxCodeValueId());
		return map;
	}
	
	@Override
	public List<Map<String, Object>> selectCode(DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup1 = dataRequest.getParameterGroup("dmMenuId");
		ParameterGroup parameterGroup2 = dataRequest.getParameterGroup("dmParam");
		
		Integer menuId = null;
		
		if (parameterGroup1 != null) {
			if (parameterGroup1.getValue("menuId") != null && !"".equals(parameterGroup1.getValue("menuId"))) {
				menuId = Integer.parseInt(parameterGroup1.getValue("menuId"));
			}
		}
		
		String cmmnsCdId = "";
		String cmmnsCdNm = "";
		String unitSystem = "";
		
		if (parameterGroup2 != null) {
			if (parameterGroup2.getValue("CMMNS_CD_ID") != null && !"".equals(parameterGroup2.getValue("CMMNS_CD_ID"))) {
				cmmnsCdId = parameterGroup2.getValue("CMMNS_CD_ID");
			}
			if (parameterGroup2.getValue("CMMNS_CD_NM") != null && !"".equals(parameterGroup2.getValue("CMMNS_CD_NM"))) {
				cmmnsCdNm = parameterGroup2.getValue("CMMNS_CD_NM");
			}
			if (parameterGroup2.getValue("UNIT_SYSTEM") != null && !"".equals(parameterGroup2.getValue("UNIT_SYSTEM"))) {
				unitSystem = parameterGroup2.getValue("UNIT_SYSTEM");
			}
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("menuId", menuId);
		map.put("cmmnsCdId", cmmnsCdId);
		map.put("cmmnsCdNm", cmmnsCdNm);
		map.put("unitSystem", unitSystem);
		
		return mgmtCmmnCodeMapper.selectCode(map);
	}
	
	@Override
	public void saveCode(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		//List<String> list = dataRequest.getParameterGroupNames();
		//if (list != null) {
			//for (int i=0; i < list.size(); i++) {
				//log.debug("#### ParameterGroupName : " + i + " : " + list.get(i));
			//}
		//}
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsCommonCodeStr");
		
		List<Map<String, String>> allRows1 = parameterGroup.getAllRowList();
		List<Map<String, String>> allRows = new ArrayList<>();
		
		if (allRows1 != null && allRows1.size() > 0) {
			
			for (int i=0; i < allRows1.size(); i++) {
				
				Map<String, String> mapStr = allRows1.get(i);
				String str = new String(Base64.getDecoder().decode(mapStr.get("str")));
				String[] arr = str.split("\\|", -1);
			    
                String CMMNS_CD_ID = arr[0];
                String UP_CMMNS_CD_ID = arr[1];
                String CMMNS_CD_NM = arr[2];
                String CMMNS_CD_DSCRP_CN = arr[3];
                String ADDTNG_MNG_VALUE_NM1 = arr[4];
                String ADDTNG_MNG_VALUE_NM2 = arr[5];
                String ADDTNG_MNG_VALUE_NM3 = arr[6];
                String ADDTNG_MNG_VALUE_NM4 = arr[7];
                String ADDTNG_MNG_VALUE_NM5 = arr[8];
                String CMMNS_CD_LEVELA_NO = arr[9];
                String CMMNS_CD_ID_ORI = arr[10];
                String UNT_SYS_SE_CD = arr[11];
                String CMMNS_CD_CN = arr[12];
                String CMMNS_CD_USE_SCRIN_CN = arr[13];
                
                Map<String, String> mapRow = new HashMap<>();
                mapRow.put("CMMNS_CD_ID", CMMNS_CD_ID);
                mapRow.put("UP_CMMNS_CD_ID", UP_CMMNS_CD_ID);
                mapRow.put("CMMNS_CD_NM", CMMNS_CD_NM);
                mapRow.put("CMMNS_CD_DSCRP_CN", CMMNS_CD_DSCRP_CN);
                mapRow.put("ADDTNG_MNG_VALUE_NM1", ADDTNG_MNG_VALUE_NM1);
                mapRow.put("ADDTNG_MNG_VALUE_NM2", ADDTNG_MNG_VALUE_NM2);
                mapRow.put("ADDTNG_MNG_VALUE_NM3", ADDTNG_MNG_VALUE_NM3);
                mapRow.put("ADDTNG_MNG_VALUE_NM4", ADDTNG_MNG_VALUE_NM4);
                mapRow.put("ADDTNG_MNG_VALUE_NM5", ADDTNG_MNG_VALUE_NM5);
                mapRow.put("CMMNS_CD_LEVELA_NO", CMMNS_CD_LEVELA_NO);
                mapRow.put("CMMNS_CD_ID_ORI", CMMNS_CD_ID_ORI);
                mapRow.put("UNT_SYS_SE_CD", UNT_SYS_SE_CD);
                mapRow.put("CMMNS_CD_CN", CMMNS_CD_CN);
                mapRow.put("CMMNS_CD_USE_SCRIN_CN", CMMNS_CD_USE_SCRIN_CN);
                
                allRows.add(mapRow);
			}
		}
		
		parameterGroup = dataRequest.getParameterGroup("dsDeletedCodeId");
		
		List<Map<String, String>> deletedRows = new ArrayList<>();
		if (parameterGroup != null) {
			deletedRows = parameterGroup.getAllRowList();
		}
		
		parameterGroup = dataRequest.getParameterGroup("dmMenuId");
		String menuId = null;
		if (parameterGroup != null) {
			menuId = parameterGroup.getValue("menuId");
		}
		
		List<String> listCodeId = new ArrayList<>();
		//for (int i=0; i < allRows.size(); i++) {
			//listMenuId.add(Integer.parseInt(allRows.get(i).get("menuId")));
		//}
		for (int i=0; i < deletedRows.size(); i++) {
			listCodeId.add(deletedRows.get(i).get("CMMNS_CD_ID"));
		}
		
		if (listCodeId != null && listCodeId.size() > 0) {
			mgmtCmmnCodeMapper.deleteCode(listCodeId);
		}

		for (int i=0; i < allRows.size(); i++) {
			Map<String, String> saveMap = allRows.get(i);
			saveMap.put("USER_ID", userId);
			mgmtCmmnCodeMapper.saveCode(saveMap);

			if (!saveMap.get("CMMNS_CD_ID").equals(saveMap.get("CMMNS_CD_ID_ORI"))) {
				mgmtCmmnCodeMapper.updateCodeId(saveMap);
				mgmtCmmnCodeMapper.updateCodeValueId(saveMap);
			}
			
			if (menuId != null && !"".equals(menuId)) {
				saveMap.put("UNT_SYS_SE_CD", menuId);
				mgmtCmmnCodeMapper.insertCodeSystem(saveMap);
			}
		}

		parameterGroup = dataRequest.getParameterGroup("dmCodeDesc");

		String codeDesc = "";
		String codeContents = "";
		String codeScreen = "";
		
		if (parameterGroup != null) {
			if (parameterGroup.getValue("codeDesc") != null) {
				codeDesc = parameterGroup.getValue("codeDesc");
			}
			if (parameterGroup.getValue("codeContents") != null) {
				codeContents = parameterGroup.getValue("codeContents");
			}
			if (parameterGroup.getValue("codeScreen") != null) {
				codeScreen = parameterGroup.getValue("codeScreen");
			}
		}
		
		parameterGroup = dataRequest.getParameterGroup("dmCodeId");
		String codeId = null;
		if (parameterGroup != null) {
			codeId = parameterGroup.getValue("codeId");
		}
		
		if (codeId != null && !"".equals(codeId)) {
			Map<String, Object> map = new HashMap<>();
			map.put("codeId", codeId);
			map.put("codeDesc", codeDesc);
			map.put("codeContents", codeContents);
			map.put("codeScreen", codeScreen);
			map.put("USER_ID", userId);
			mgmtCmmnCodeMapper.updateCodeDesc(map);
		}
		
		mgmtCmmnCodeMapper.deleteCodeSystem();
		mgmtCmmnCodeMapper.deleteCodeVal();
	}

	@Override
	public List<Map<String, Object>> selectCodeValue(DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmCodeId");
		String codeId = parameterGroup.getValue("codeId");
		
		return mgmtCmmnCodeMapper.selectCodeValue(codeId);
	}
	
	@Override
	public void saveCodeValue(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsCommonCodeValue");
		
		List<Map<String, String>> allRows = new ArrayList<>();
		if (parameterGroup != null) {
			allRows = parameterGroup.getAllRowList();
		}
		
		//parameterGroup = dataRequest.getParameterGroup("dsDeletedCodeValueId");
		
		//List<Map<String, String>> deletedRows = new ArrayList<>();
		//if (parameterGroup != null) {
			//deletedRows = parameterGroup.getAllRowList();
		//}

		parameterGroup = dataRequest.getParameterGroup("dmCodeDesc");
		String codeDesc = "";
		String add1Desc = "";
		String add2Desc = "";
		String add3Desc = "";
		String add4Desc = "";
		String add5Desc = "";
		if (parameterGroup != null) {
			if (parameterGroup.getValue("codeDesc") != null) {
				codeDesc = parameterGroup.getValue("codeDesc");
			}
			if (parameterGroup.getValue("add1Desc") != null) {
				add1Desc = parameterGroup.getValue("add1Desc");
			}
			if (parameterGroup.getValue("add2Desc") != null) {
				add2Desc = parameterGroup.getValue("add2Desc");
			}
			if (parameterGroup.getValue("add3Desc") != null) {
				add3Desc = parameterGroup.getValue("add3Desc");
			}
			if (parameterGroup.getValue("add4Desc") != null) {
				add4Desc = parameterGroup.getValue("add4Desc");
			}
			if (parameterGroup.getValue("add5Desc") != null) {
				add5Desc = parameterGroup.getValue("add5Desc");
			}
		}

		parameterGroup = dataRequest.getParameterGroup("dmCodeId");
		String codeId = null;
		if (parameterGroup != null) {
			codeId = parameterGroup.getValue("codeId");
		}
		mgmtCmmnCodeMapper.deleteCodeValue(codeId);
		
		//List<String> listCodeId = new ArrayList<>();
		//for (int i=0; i < allRows.size(); i++) {
			//listMenuId.add(Integer.parseInt(allRows.get(i).get("menuId")));
		//}
		//for (int i=0; i < deletedRows.size(); i++) {
			//listCodeId.add(deletedRows.get(i).get("CODE_VALUE_ID"));
		//}
		//if (listCodeId != null && listCodeId.size() > 0) {
			//mgmtCmmnCodeMapper.deleteCodeValue(listCodeId);
		//}

		for (int i=0; i < allRows.size(); i++) {
			Map<String, String> saveMap = allRows.get(i);
			saveMap.put("USER_ID", userId);
			saveMap.put("SRTNG_SQNCE", String.valueOf(i + 1));
			mgmtCmmnCodeMapper.saveCodeValue(saveMap);
		}
		
		if (codeId != null && !"".equals(codeId)) {
			Map<String, Object> map = new HashMap<>();
			map.put("codeId", codeId);
			map.put("codeDesc", codeDesc);
			map.put("add1Desc", add1Desc);
			map.put("add2Desc", add2Desc);
			map.put("add3Desc", add3Desc);
			map.put("add4Desc", add4Desc);
			map.put("add5Desc", add5Desc);
			map.put("USER_ID", userId);
			mgmtCmmnCodeMapper.updateCodeDesc(map);
		}
	}
	
	@Override
	public List<Map<String, Object>> selectUnitSystem(DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmCommonCode");
		
		String codeId = null;
		if (parameterGroup != null) {
			codeId = parameterGroup.getValue("CMMNS_CD_ID");
		}
		
		if (codeId == null) {
			return null;
		}
		
		return mgmtCmmnCodeMapper.selectUnitSystem(codeId);	
	}
	
	@Override
	public void saveUnitSystem(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsUnitSystem");
		List<Map<String, String>> saveUnitSystem = new ArrayList<>();
		if (parameterGroup != null) {
			saveUnitSystem = parameterGroup.getAllRowList();
		}
		
		log.debug("#### saveUnitSystem.size : " + saveUnitSystem.size());
		
		//parameterGroup = dataRequest.getParameterGroup("dsUnitSystemDelete");
		//List<Map<String, String>> deleteUnitSystem = new ArrayList<>();
		//if (parameterGroup != null) {
			//deleteUnitSystem = parameterGroup.getAllRowList();
		//}
		
		parameterGroup = dataRequest.getParameterGroup("dmCommonCode");
		String codeId = null;
		if (parameterGroup != null) {
			codeId = parameterGroup.getValue("CMMNS_CD_ID");
		}
		
		if (codeId != null && !"".equals(codeId)) {
			mgmtCmmnCodeMapper.deleteCommonCodeSystem(codeId);
		}
		
		for (int i=0; i < saveUnitSystem.size(); i++) {
			Map<String, String> map = saveUnitSystem.get(i);
			map.put("USER_ID", userId);
			if (map.get("CMMNS_CD_ID") != null && !"".equals(map.get("CMMNS_CD_ID"))
				&& map.get("UNT_SYS_SE_CD") != null && !"".equals(map.get("UNT_SYS_SE_CD"))) {
				mgmtCmmnCodeMapper.insertCommonCodeSystem(map);
			}
		}
	}
	
	@Override
	public List<Map<String, Object>> selectCommonCode(String codeId) throws Exception {
		if (codeId == null) {
			return null;
		}
		return mgmtCmmnCodeMapper.selectCommonCode(codeId);
	}
	
	@Override
	public List<Map<String, Object>> selectCommonCode(String codeId, String upCmmnsCdValue) throws Exception {
		if (codeId == null) {
			return null;
		}
		Map<String, Object> map  = new HashMap<>();
		map.put("codeId", codeId);
		map.put("upCmmnsCdValue", upCmmnsCdValue);
		
		return mgmtCmmnCodeMapper.selectCommonCode2(map);
	}

	@Override
	public List<Map<String, Object>> selectCommonCodeUnit(String codeId, String unitCode) throws Exception {
		if (codeId == null) {
			return null;
		}
		Map<String, Object> map  = new HashMap<>();
		map.put("codeId", codeId);
		map.put("unitCode", unitCode);
		
		if("".equals(unitCode) || unitCode == null) {
			return mgmtCmmnCodeMapper.selectCommonCode(codeId);
		}else {
			return mgmtCmmnCodeMapper.selectCommonCodeUnit(map);	
		}
		
		
	}

	@Override
	public List<Map<String, Object>> selectCommonCodeList(Integer systemId) throws Exception {
		if (systemId == null) {
			return null;
		}
		return mgmtCmmnCodeMapper.selectCommonCodeList(systemId);
	}
	
	@Override
	public List<Map<String, Object>> selectCommonCodeTotalList(Integer systemId) throws Exception {
		if (systemId == null) {
			return null;
		}
		return mgmtCmmnCodeMapper.selectCommonCodeTotalList(systemId);
	}
	
	@Override
	public Map<String, Integer> selectCodeIdDuplicate(DataRequest dataRequest) throws Exception {

		Map<String, Integer> map = new HashMap<>();
		
		map.put("duplicateCount", 0);
		
		ParameterGroup param1 = dataRequest.getParameterGroup("dmMenuId");
		Integer systemId = null;
		
		if (param1 != null) {
			if (param1.getValue("menuId") != null && !"".equals(param1.getValue("menuId"))) {
				systemId = Integer.valueOf(param1.getValue("menuId"));
			} else {
				return map;
			}
		} else {
			return map;
		}

		ParameterGroup param2 = dataRequest.getParameterGroup("dsCommonCode");
		List<Map<String, String>> codeList = new ArrayList<>();
		if (param2 != null) {
			codeList = param2.getAllRowList();
			if (codeList == null || codeList.size() == 0) {
				return map;
			}
		} else {
			return map;
		}
		
		Map<String, Object> paramMap = new HashMap<>();
		
		paramMap.put("systemId", systemId);
		paramMap.put("codeList", codeList);
		
		Integer duplicateCount = mgmtCmmnCodeMapper.selectCodeIdDuplicate(paramMap);
		
		map.put("duplicateCount", duplicateCount);
		
		return map;
	}


	@Override
	public List<Map<String, Object>> selectOrgUnitSystem(DataRequest dataRequest) throws Exception {

		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmOrgInfo");
		
		String orgCode = null;
		if (parameterGroup != null) {
			if (parameterGroup.getValue("ORG_CODE") != null && !"".equals(parameterGroup.getValue("ORG_CODE"))) {
				orgCode = parameterGroup.getValue("ORG_CODE");
			}
		}
		
		if (orgCode == null) {
			return null;
		}
		
		Map<String, String> map = new HashMap<>();
		map.put("INST_CD", orgCode);
		return mgmtCmmnCodeMapper.selectOrgUnitSystem(map);
	}
	
	@Override
	public String getSysDate(String STR_FORMAT) throws Exception {
		Map<String, String> map = new HashMap<>();
		map.put("STR_FORMAT", STR_FORMAT);
		return mgmtCmmnCodeMapper.getSysDate(map);
	}

	@Override
	public List<Map<String, Object>> selectCodeValueUnitSystem(DataRequest dataRequest) throws Exception {
		
		ParameterGroup dmCodeInfo = dataRequest.getParameterGroup("dmCodeInfo");
		Map<String, String> dmCodeInfoMap = dmCodeInfo.getSingleValueMap();
		
		Map<String, Object> map = new HashMap<>();
		map.put("CMMNS_CD_ID", dmCodeInfoMap.get("CODE_ID"));
		map.put("CMMNS_CD_VALUE", dmCodeInfoMap.get("CODE_VALUE"));
		
		List<Map<String, Object>> list = mgmtCmmnCodeMapper.selectCodeValueUnitSystem(map);
		
		return list;
	}
	
	@Override
	public void saveCodeValueUnitSystem(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		ParameterGroup dmCodeInfo = dataRequest.getParameterGroup("dmCodeInfo");
		Map<String, String> dmCodeInfoMap = dmCodeInfo.getSingleValueMap();

		Map<String, Object> map1 = new HashMap<>();
		map1.put("CMMNS_CD_ID", dmCodeInfoMap.get("CODE_ID"));
		map1.put("CMMNS_CD_VALUE", dmCodeInfoMap.get("CODE_VALUE"));
		
		mgmtCmmnCodeMapper.deleteCodeValueUnitSystem(map1);
		
		
		ParameterGroup dsCodeValueUnitSystem = dataRequest.getParameterGroup("dsCodeValueUnitSystem");
		List<Map<String, String>> dsCodeValueUnitSystemList = dsCodeValueUnitSystem.getAllRowList();
		
		log.debug("#### dsCodeValueUnitSystemList size : " + dsCodeValueUnitSystemList.size());
		
		Map<String, Object> map = new HashMap<>();
		map.put("USER_ID", userId);
		
		for (int i=0; i < dsCodeValueUnitSystemList.size(); i++) {
			
			Map<String, String> codeValueMap = dsCodeValueUnitSystemList.get(i);
			
			log.debug("#### CMMNS_CD_ID : " + codeValueMap.get("CMMNS_CD_ID"));
			log.debug("#### CMMNS_CD_VALUE : " + codeValueMap.get("CMMNS_CD_VALUE"));
			log.debug("#### UNT_TASKWK_SE_CD : " + codeValueMap.get("UNT_TASKWK_SE_CD"));
			log.debug("#### UNIT_SYSTEM_NAME : " + codeValueMap.get("UNIT_SYSTEM_NAME"));
			
			map.put("CMMNS_CD_ID", codeValueMap.get("CMMNS_CD_ID"));
			map.put("CMMNS_CD_VALUE", codeValueMap.get("CMMNS_CD_VALUE"));
			map.put("UNT_TASKWK_SE_CD", codeValueMap.get("UNT_TASKWK_SE_CD"));

			mgmtCmmnCodeMapper.saveCodeValueUnitSystem(map);
		}
	}


	// 회원 가입 전용 가입의 시스템 사용 권한
	@Override
	public List<Map<String, Object>> selectCommonCodeJoinRights() throws Exception {
		return mgmtCmmnCodeMapper.selectCommonCodeJoinRights();
	}
}

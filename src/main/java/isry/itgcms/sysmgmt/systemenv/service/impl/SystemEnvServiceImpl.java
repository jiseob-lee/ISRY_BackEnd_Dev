package isry.itgcms.sysmgmt.systemenv.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import isry.itgcms.util.IpAddressMatcher;
import isry.itgcms.sysmgmt.systemenv.mapper.SystemEnvMapper;
import isry.itgcms.sysmgmt.systemenv.service.SystemEnvService;
import isry.itgcms.sysmgmt.systemenv.vo.AllowVO;
import isry.itgcms.sysmgmt.systemenv.vo.SecondSkipIdVO;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.IP;

@Service("systemEnvService")
public class SystemEnvServiceImpl implements SystemEnvService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="systemEnvMapper")
    private SystemEnvMapper systemEnvMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectAdminIp() throws Exception {
		
		List<Map<String, Object>> adminIpList = new ArrayList<>();
		
		Map<String, String> map = systemEnvMapper.selectAdminIp();
		String allowListStr = map == null ? "" : map.get("STNG_PARA_VALUE1");
		
		if (allowListStr != null && !"".equals(allowListStr.trim())) {

			ObjectMapper mapper = new ObjectMapper();
			List<AllowVO> allowIpList = Arrays.asList(mapper.readValue(allowListStr, AllowVO[].class));
			
			for (int i=0; i < allowIpList.size(); i++) {
				
				AllowVO allowVO = allowIpList.get(i);
				
				Map<String, Object> listMap = new HashMap<>();
				
				String ipStr = allowVO.getIp();
				String[] ipArr = ipStr.split("\\.");
				
				listMap.put("IP_NO", i + 1);
				listMap.put("IP1", ipArr[0]);
				listMap.put("IP2", ipArr[1]);
				listMap.put("IP3", ipArr[2]);
				listMap.put("IP4", ipArr[3]);
				listMap.put("USE_YN", allowVO.getUse());
				listMap.put("RM_DTL_CN", allowVO.getDesc());
				listMap.put("DOT", ".");
				
				adminIpList.add(listMap);
			}
		}
		
		return adminIpList;
	}

	@Override
	public void saveAdminIp(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		List<AllowVO> allowList = new ArrayList<>();
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsList");
		
		//List<Map<String, String>> insertList = parameterGroup.getInsertedRowList();
		//List<Map<String, String>> updateList = parameterGroup.getUpdatedRowList();
		List<Map<String, String>> deleteList = parameterGroup.getDeletedRowList();
		
		
		List<Map<String, String>> list = parameterGroup.getAllRowList();
		
		if (list != null && list.size() > 0 && deleteList != null && deleteList.size() > 0) {
			for (int i = list.size() - 1; i >= 0; i--) {
				log.debug("#### map : " + list.get(i).toString());
				Map<String, String> listMap = list.get(i);
				for (int j=0; j < deleteList.size(); j++) {
					Map<String, String> listMapDel = deleteList.get(j);
					if (listMap.get("IP1").equals(listMapDel.get("IP1"))
						&& listMap.get("IP2").equals(listMapDel.get("IP2"))
						&& listMap.get("IP3").equals(listMapDel.get("IP3"))
						&& listMap.get("IP4").equals(listMapDel.get("IP4"))) {
						list.remove(i);
						break;
					}
				}
			}
		}
		
		if (list != null && list.size() > 0) {
		
			for (int i=0; i < list.size(); i++) {
				
				Map<String, String> listMap = list.get(i);
				
				AllowVO allowVO = new AllowVO();
				
				String ipStr = listMap.get("IP1") + "." + listMap.get("IP2") 
					+ "." + listMap.get("IP3") + "." + listMap.get("IP4");
				
				allowVO.setIp(ipStr);
				allowVO.setUse(listMap.get("USE_YN"));
				allowVO.setDesc(listMap.get("RM_DTL_CN"));
				
				allowList.add(allowVO);
			}
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(allowList);
			
			Map<String, String> map = new HashMap<>();
			map.put("ALLOW_LIST_STR", jsonString);
			map.put("USER_ID", userId);
			
			systemEnvMapper.saveIpAllowList(map);
			systemEnvMapper.saveIpAllowHistory(map);
		
		} else {

			Map<String, String> map = new HashMap<>();
			map.put("ALLOW_LIST_STR", "");
			map.put("USER_ID", userId);
			
			systemEnvMapper.saveIpAllowList(map);
			systemEnvMapper.saveIpAllowHistory(map);
		}
		
		/*
		if (insertList != null) {
			for (int i=0; i < insertList.size(); i++) {
				Map<String, String> map = insertList.get(i);
				map.put("USER_ID", userId);
				systemEnvMapper.saveAdminIp(map);
			}
		}
		if (updateList != null) {
			for (int i=0; i < updateList.size(); i++) {
				Map<String, String> map = updateList.get(i);
				map.put("USER_ID", userId);
				systemEnvMapper.saveAdminIp(map);
			}
		}
		if (deleteList != null) {
			for (int i=0; i < deleteList.size(); i++) {
				Map<String, String> map = deleteList.get(i);
				map.put("USER_ID", userId);
				systemEnvMapper.deleteAdminIp(map);
			}
		}
		*/
	}

	@Override
	public void deleteAdminIp(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		systemEnvMapper.deleteAdminIp(null);
	}
	
	@Override
	public boolean checkAdminIp(HttpServletRequest request) throws Exception {
		
		//List<Map<String, Object>> ipList = systemEnvMapper.selectAdminIpUseY();
		//List<Map<String, Object>> ipList = selectAdminIp();
		
		String allowIPs = "";
				
		Map<String, String> map = systemEnvMapper.selectAdminIp();
		String allowListStr = map == null ? "" : map.get("STNG_PARA_VALUE1");
		
		if (allowListStr != null && !"".equals(allowListStr.trim())) {
			ObjectMapper mapper = new ObjectMapper();
			List<AllowVO> allowIpList = Arrays.asList(mapper.readValue(allowListStr, AllowVO[].class));
			
			for (int i=0; i < allowIpList.size(); i++) {

				if (!"Y".equals(allowIpList.get(i).getUse())) {
					continue;
				}
				
				if (allowIPs.length() > 0) {
					allowIPs += ",";
				}
				
				String ip = allowIpList.get(i).getIp();
				String[] ipArr = ip.split("\\.");
				
				if ("*".equals(ipArr[1])) {
					ip = ipArr[0] + ".0.0.0/8";
				} else if ("*".equals(ipArr[2])) {
					ip = ipArr[0] + "." + ipArr[1] + ".0.0/16";
				} else if ("*".equals(ipArr[3])) {
					ip = ipArr[0] + "." + ipArr[1] + "." + ipArr[2] + ".0/24";
				}
				
				allowIPs += ip;
			}
		}
		
		String ip = IP.getClientIP(request);
		
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}
		
		//String[] ipArr = ip.split("\\.");
		
		//log.debug("#### ipArr[0] : " + ipArr[0]);
		//log.debug("#### ipArr[1] : " + ipArr[1]);
		//log.debug("#### ipArr[2] : " + ipArr[2]);
		//log.debug("#### ipArr[3] : " + ipArr[3]);
		
		if (allowIPs.length() > 0) {

			String[] allowIPArray = allowIPs.split(",");
			
			boolean allowIpCheck = false;
			
			for (int i=0; i < allowIPArray.length; i++) {
				if (matches(ip, allowIPArray[i])) {
					allowIpCheck = true;
				}
			}
			
			if (allowIpCheck) {
				//System.out.println("ok");
				return true;
			} else {
				//System.out.println("XXXX");
			}
			
			/*
			for (int i=0; i < ipList.size(); i++) {
				Map<String, Object> adminIpMap = ipList.get(i);
				
				boolean flag1 = false;
				boolean flag2 = false;
				boolean flag3 = false;
				boolean flag4 = false;
				
				if (ipArr[0].equals(adminIpMap.get("IP1")) || "*".equals(adminIpMap.get("IP1"))) {
					flag1 = true;
				} else {
					continue;
				}
				if (ipArr[1].equals(adminIpMap.get("IP2")) || "*".equals(adminIpMap.get("IP2"))) {
					flag2 = true;
				} else {
					continue;
				}
				if (ipArr[2].equals(adminIpMap.get("IP3")) || "*".equals(adminIpMap.get("IP3"))) {
					flag3 = true;
				} else {
					continue;
				}
				if (ipArr[3].equals(adminIpMap.get("IP4")) || "*".equals(adminIpMap.get("IP4"))) {
					flag4 = true;
				} else {
					continue;
				}
				if (flag1 && flag2 && flag3 && flag4) {
					return true;
				}
			}
			*/
		}
		
		return false;
	}
	

	@Override
	public boolean checkDeveloperIp(HttpServletRequest request) throws Exception {
		
		/*
		List<Map<String, Object>> ipList = new ArrayList<>();

		Map<String, Object> map1 = new HashMap<>();
		map1.put("IP1", "10");
		map1.put("IP2", "33");
		map1.put("IP3", "*");
		map1.put("IP4", "*");
		ipList.add(map1);

		Map<String, Object> map2 = new HashMap<>();
		map2.put("IP1", "*");
		map2.put("IP2", "*");
		map2.put("IP3", "*");
		map2.put("IP4", "*");
		ipList.add(map2);
		
		String ip = IP.getClientIP(request);
		
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}
		
		String[] ipArr = ip.split("\\.");
		
		log.debug("#### ipArr[0] : " + ipArr[0]);
		log.debug("#### ipArr[1] : " + ipArr[1]);
		log.debug("#### ipArr[2] : " + ipArr[2]);
		log.debug("#### ipArr[3] : " + ipArr[3]);
		
		if (ipList != null) {
			for (int i=0; i < ipList.size(); i++) {
				Map<String, Object> adminIpMap = ipList.get(i);
				
				boolean flag1 = false;
				boolean flag2 = false;
				boolean flag3 = false;
				boolean flag4 = false;
				
				if (ipArr[0].equals(adminIpMap.get("IP1")) || "*".equals(adminIpMap.get("IP1"))) {
					flag1 = true;
				} else {
					continue;
				}
				if (ipArr[1].equals(adminIpMap.get("IP2")) || "*".equals(adminIpMap.get("IP2"))) {
					flag2 = true;
				} else {
					continue;
				}
				if (ipArr[2].equals(adminIpMap.get("IP3")) || "*".equals(adminIpMap.get("IP3"))) {
					flag3 = true;
				} else {
					continue;
				}
				if (ipArr[3].equals(adminIpMap.get("IP4")) || "*".equals(adminIpMap.get("IP4"))) {
					flag4 = true;
				} else {
					continue;
				}
				if (flag1 && flag2 && flag3 && flag4) {
					return true;
				}
			}
		}
		
		return false;
		*/
		
		boolean all = false;

		String ip = IP.getClientIP(request);
		
		if ("0:0:0:0:0:0:0:1".equals(ip)) {
			ip = "127.0.0.1";
		}
		
		

		String allowIPs = "";
				
		Map<String, String> map = systemEnvMapper.selectDeveloperIp();
		String allowListStr = map == null ? "" : map.get("STNG_PARA_VALUE2");
		
		if (allowListStr != null && !"".equals(allowListStr.trim())) {
			ObjectMapper mapper = new ObjectMapper();
			List<AllowVO> allowIpList = Arrays.asList(mapper.readValue(allowListStr, AllowVO[].class));
			
			for (int i=0; i < allowIpList.size(); i++) {

				if (!"Y".equals(allowIpList.get(i).getUse())) {
					continue;
				}
				
				if (allowIPs.length() > 0) {
					allowIPs += ",";
				}
				
				String ip1 = allowIpList.get(i).getIp();
				String[] ipArr = ip1.split("\\.");
				if ("*".equals(ipArr[0])) {
					all = true;
					continue;
				} else if ("*".equals(ipArr[1])) {
					ip1 = ipArr[0] + ".0.0.0/8";
				} else if ("*".equals(ipArr[2])) {
					ip1 = ipArr[0] + "." + ipArr[1] + ".0.0/16";
				} else if ("*".equals(ipArr[3])) {
					ip1 = ipArr[0] + "." + ipArr[1] + "." + ipArr[2] + ".0/24";
				}
				
				allowIPs += ip1;
			}
		}
				
		//String allowIPs = "10.33.2.0/24,10.188.131.0/24,10.188.99.0/24,116.67.91.0/24,221.217.107.152";
		if (allowIPs == null || allowIPs.length() == 0) {
			//allowIPs = "10.33.2.0/24";  // 개발자 사무실 아이피 대역
			allowIPs = "";
		}
		
		log.debug("#### developerIps : " + allowIPs);
		
		String[] allowIPArray = allowIPs.split(",");
		boolean allowIpCheck = false;
		for (int i=0; i < allowIPArray.length; i++) {
			if (matches(ip, allowIPArray[i])) {
				allowIpCheck = true;
			}
		}
		

		String loginId = request.getParameter("userId") == null ? "" : request.getParameter("userId");
		
		String allowIdStr = map == null ? "" : map.get("STNG_PARA_VALUE4");

		ObjectMapper mapper = new ObjectMapper();
		//List<Map<String, String>> allowIpList = Arrays.asList(mapper.readValue(allowListStr, AllowVO[].class));
		List<Map<String, String>> allowIdList = "".equals(allowIdStr) || allowIdStr == null ? null : 
			mapper.readValue(allowIdStr, new TypeReference<List<Map<String, String>>>(){});
		
		boolean allowIdCheck = false;
		
		if (!"".equals(loginId) && allowIdList != null && allowIdList.size() > 0) {
			for (int i=0; i < allowIdList.size(); i++) {
				Map<String, String> allowIdMap = allowIdList.get(i);
				if ("Y".equals(allowIdMap.get("use"))) {
					if (loginId.equals(allowIdMap.get("id"))) {
						allowIdCheck = true;
						break;
					}
				}
			}
		}
		
		if (allowIpCheck || allowIdCheck) {
			//System.out.println("ok");
			return true;
		} else {
			//System.out.println("XXXX");
			return all;
		}
		
	}
	

	private boolean matches(String ip, String subnet) {
	    IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(subnet);
	    return ipAddressMatcher.matches(ip);
	}


	
	
	@Override
	public List<Map<String, Object>> selectSecondSkipIp() throws Exception {
		
		List<Map<String, Object>> adminIpList = new ArrayList<>();
		
		Map<String, String> map = systemEnvMapper.selectAdminIp();
		String allowListStr = map == null ? "" : map.get("STNG_PARA_VALUE2");
		
		if (allowListStr != null && !"".equals(allowListStr.trim())) {

			ObjectMapper mapper = new ObjectMapper();
			List<AllowVO> allowIpList = Arrays.asList(mapper.readValue(allowListStr, AllowVO[].class));
			
			for (int i=0; i < allowIpList.size(); i++) {
				
				AllowVO allowVO = allowIpList.get(i);
				
				Map<String, Object> listMap = new HashMap<>();
				
				String ipStr = allowVO.getIp();
				String[] ipArr = ipStr.split("\\.");
				
				listMap.put("IP_NO", i + 1);
				listMap.put("IP1", ipArr[0]);
				listMap.put("IP2", ipArr[1]);
				listMap.put("IP3", ipArr[2]);
				listMap.put("IP4", ipArr[3]);
				listMap.put("USE_YN", allowVO.getUse());
				listMap.put("RM_DTL_CN", allowVO.getDesc());
				listMap.put("DOT", ".");
				
				adminIpList.add(listMap);
			}
		}
		
		return adminIpList;
	}

	@Override
	public void saveSecondSkipIp(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		List<AllowVO> allowList = new ArrayList<>();
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsList");
		
		//List<Map<String, String>> insertList = parameterGroup.getInsertedRowList();
		//List<Map<String, String>> updateList = parameterGroup.getUpdatedRowList();
		List<Map<String, String>> deleteList = parameterGroup.getDeletedRowList();
		
		
		List<Map<String, String>> list = parameterGroup.getAllRowList();
		
		if (list != null && list.size() > 0 && deleteList != null && deleteList.size() > 0) {
			for (int i = list.size() - 1; i >= 0; i--) {
				log.debug("#### map : " + list.get(i).toString());
				Map<String, String> listMap = list.get(i);
				for (int j=0; j < deleteList.size(); j++) {
					Map<String, String> listMapDel = deleteList.get(j);
					if (listMap.get("IP1").equals(listMapDel.get("IP1"))
						&& listMap.get("IP2").equals(listMapDel.get("IP2"))
						&& listMap.get("IP3").equals(listMapDel.get("IP3"))
						&& listMap.get("IP4").equals(listMapDel.get("IP4"))) {
						list.remove(i);
						break;
					}
				}
			}
		}
		
		if (list != null && list.size() > 0) {
		
			for (int i=0; i < list.size(); i++) {
				
				Map<String, String> listMap = list.get(i);
				
				AllowVO allowVO = new AllowVO();
				
				String ipStr = listMap.get("IP1") + "." + listMap.get("IP2") 
					+ "." + listMap.get("IP3") + "." + listMap.get("IP4");
				
				allowVO.setIp(ipStr);
				allowVO.setUse(listMap.get("USE_YN"));
				allowVO.setDesc(listMap.get("RM_DTL_CN"));
				
				allowList.add(allowVO);
			}
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(allowList);
			
			Map<String, String> map = new HashMap<>();
			map.put("ALLOW_LIST_STR", jsonString);
			map.put("USER_ID", userId);
			
			systemEnvMapper.saveSecondSkipIpList(map);
			systemEnvMapper.saveSecondSkipIpHistory(map);
		
		} else {

			Map<String, String> map = new HashMap<>();
			map.put("ALLOW_LIST_STR", "");
			map.put("USER_ID", userId);
			
			systemEnvMapper.saveSecondSkipIpList(map);
			systemEnvMapper.saveSecondSkipIpHistory(map);
		}
		
		/*
		if (insertList != null) {
			for (int i=0; i < insertList.size(); i++) {
				Map<String, String> map = insertList.get(i);
				map.put("USER_ID", userId);
				systemEnvMapper.saveAdminIp(map);
			}
		}
		if (updateList != null) {
			for (int i=0; i < updateList.size(); i++) {
				Map<String, String> map = updateList.get(i);
				map.put("USER_ID", userId);
				systemEnvMapper.saveAdminIp(map);
			}
		}
		if (deleteList != null) {
			for (int i=0; i < deleteList.size(); i++) {
				Map<String, String> map = deleteList.get(i);
				map.put("USER_ID", userId);
				systemEnvMapper.deleteAdminIp(map);
			}
		}
		*/
	}

	//@Override
	//public void deleteSecondSkipIp(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		//systemEnvMapper.deleteSecondSkipIp(null);
	//}
	
	
	
	
	@Override
	public List<Map<String, Object>> selectSecondSkipId() throws Exception {
		
		List<Map<String, Object>> adminIpList = new ArrayList<>();
		
		Map<String, String> map = systemEnvMapper.selectAdminIp();
		String allowListStr = map == null ? "" : map.get("STNG_PARA_VALUE4");
		
		if (allowListStr != null && !"".equals(allowListStr.trim())) {

			ObjectMapper mapper = new ObjectMapper();
			List<SecondSkipIdVO> allowIpList = Arrays.asList(mapper.readValue(allowListStr, SecondSkipIdVO[].class));
			
			for (int i=0; i < allowIpList.size(); i++) {
				
				SecondSkipIdVO allowVO = allowIpList.get(i);
				
				Map<String, Object> listMap = new HashMap<>();
				
				String ipStr = allowVO.getId();
				//String[] ipArr = ipStr.split("\\.");
				
				listMap.put("ID_NO", i + 1);
				listMap.put("LOGIN_ID", ipStr);
				listMap.put("USE_YN", allowVO.getUse());
				listMap.put("RM_DTL_CN", allowVO.getDesc());
				
				adminIpList.add(listMap);
			}
		}
		
		return adminIpList;
	}

	@Override
	public void saveSecondSkipId(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		List<SecondSkipIdVO> allowList = new ArrayList<>();
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dsList");
		
		//List<Map<String, String>> insertList = parameterGroup.getInsertedRowList();
		//List<Map<String, String>> updateList = parameterGroup.getUpdatedRowList();
		List<Map<String, String>> deleteList = parameterGroup.getDeletedRowList();
		
		
		List<Map<String, String>> list = parameterGroup.getAllRowList();
		
		if (list != null && list.size() > 0 && deleteList != null && deleteList.size() > 0) {
			for (int i = list.size() - 1; i >= 0; i--) {
				log.debug("#### map : " + list.get(i).toString());
				Map<String, String> listMap = list.get(i);
				for (int j=0; j < deleteList.size(); j++) {
					Map<String, String> listMapDel = deleteList.get(j);
					if (listMap.get("LOGIN_ID").equals(listMapDel.get("LOGIN_ID"))) {
						list.remove(i);
						break;
					}
				}
			}
		}
		
		if (list != null && list.size() > 0) {
		
			for (int i=0; i < list.size(); i++) {
				
				Map<String, String> listMap = list.get(i);
				
				SecondSkipIdVO secondSkipIdVO = new SecondSkipIdVO();
				
				String ipStr = listMap.get("LOGIN_ID");
				
				secondSkipIdVO.setId(ipStr);
				secondSkipIdVO.setUse(listMap.get("USE_YN"));
				secondSkipIdVO.setDesc(listMap.get("RM_DTL_CN"));
				
				allowList.add(secondSkipIdVO);
			}
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(allowList);
			
			Map<String, String> map = new HashMap<>();
			map.put("ALLOW_LIST_STR", jsonString);
			map.put("USER_ID", userId);
			
			systemEnvMapper.saveSecondSkipIdList(map);
			systemEnvMapper.saveSecondSkipIdHistory(map);
		
		} else {

			Map<String, String> map = new HashMap<>();
			map.put("ALLOW_LIST_STR", "");
			map.put("USER_ID", userId);
			
			systemEnvMapper.saveSecondSkipIdList(map);
			systemEnvMapper.saveSecondSkipIdHistory(map);
		}
		
		/*
		if (insertList != null) {
			for (int i=0; i < insertList.size(); i++) {
				Map<String, String> map = insertList.get(i);
				map.put("USER_ID", userId);
				systemEnvMapper.saveAdminIp(map);
			}
		}
		if (updateList != null) {
			for (int i=0; i < updateList.size(); i++) {
				Map<String, String> map = updateList.get(i);
				map.put("USER_ID", userId);
				systemEnvMapper.saveAdminIp(map);
			}
		}
		if (deleteList != null) {
			for (int i=0; i < deleteList.size(); i++) {
				Map<String, String> map = deleteList.get(i);
				map.put("USER_ID", userId);
				systemEnvMapper.deleteAdminIp(map);
			}
		}
		*/
	}

	//@Override
	//public void deleteSecondSkipId(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		//systemEnvMapper.deleteSecondSkipId(null);
	//}

}

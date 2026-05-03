/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.config.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.itgcm.casemng.uneart.service.impl.UnRegCaseServiceImpl;
import isry.itgcms.sysmgmt.cmmncode.mapper.MgmtCmmnCodeMapper;
import isry.itgcms.sysmgmt.cmmncode.service.MgmtCmmnCodeService;
import isry.itgcms.sysmgmt.config.mapper.MgmtCmmnConfigMapper;
import isry.itgcms.sysmgmt.config.service.MgmtCmmnConfigService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.CommUtils;
import isry.redis.service.RedisService;

/**
 * @파일명        : MgmtCmmnConfigServiceImpl.java
 * @프로그램 설명 : 환경설정 관리
 * - 
 * - 
 * @작성자        : Hee Sung Yoon
 * @작성일        : 2023.01.16. 
 * @수정자        : Hee Sung Yoon
 * @수정일        : 2023.01.16.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("mgmtCmmnConfigService")
public class MgmtCmmnConfigServiceImpl implements MgmtCmmnConfigService {

	@Resource(name="mgmtCmmnConfigMapper")
    private MgmtCmmnConfigMapper mgmtCmmnConfigMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MgmtCmmnConfigServiceImpl.class);
	
	public List<Map<String, Object>> selectConfigList(String stngId) throws Exception {
		List<Map<String, Object>> configList = new ArrayList<Map<String,Object>>();
		configList = mgmtCmmnConfigMapper.selectConfigList(stngId);
		return configList;
	}

	public void insertConfigList(HttpServletRequest request, Map<String, String> map) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		map.put("USER_ID", userId);
		mgmtCmmnConfigMapper.insertConfigList(map);
	}
	
	public void updateConfigList(HttpServletRequest request, Map<String, String> map) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		map.put("USER_ID", userId);
		mgmtCmmnConfigMapper.updateConfigList(map);
	}
	
	public void deleteConfigList(String stngId) throws Exception {
		mgmtCmmnConfigMapper.deleteConfigList(stngId);
	}
	
	public List<Map<String, Object>> selectConfigListLog(String stngId) throws Exception {
		List<Map<String, Object>> configList = new ArrayList<Map<String,Object>>();
		configList = mgmtCmmnConfigMapper.selectConfigListLog(stngId);
		return configList;
	}
	
	public void insertConfigListLog(HttpServletRequest request, Map<String, String> map) throws Exception {
		String userId = CommUtils.getUserId(userLoginService.getLoginSessionVO(request)); //userId 가져오기
		map.put("USER_ID", userId);
		mgmtCmmnConfigMapper.insertConfigList(map);
	}
}

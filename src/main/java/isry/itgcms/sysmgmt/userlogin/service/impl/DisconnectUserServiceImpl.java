/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userlogin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.personalinfo.mapper.PersonalInfoMapper;
import isry.itgcms.sysmgmt.userlogin.mapper.DisconnectUserMapper;
import isry.itgcms.sysmgmt.userlogin.service.DisconnectUserService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : DisconnectUserServiceImpl.java
 * @프로그램 설명 : 사용자 접속 차단
 * - 
 * - 
 * @작성자        : Ji.Seob.Lee
 * @작성일        : 2022. 3. 31. 
 * @수정자        : Ji.Seob.Lee
 * @수정일        : 2022. 3. 31.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("disconnectUserService")
public class DisconnectUserServiceImpl extends IsryBaseServiceImpl implements DisconnectUserService {

	@Resource(name="disconnectUserMapper")
    private DisconnectUserMapper disconnectUserMapper;

	@Resource(name="personalInfoMapper")
    private PersonalInfoMapper personalInfoMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectDisconnectUser(Map<String, Object> dmSearchMap) throws Exception {

		List<Map<String, Object>> list = disconnectUserMapper.selectDisconnectUser(dmSearchMap);
		//List<Map<String, Object>> list2 = new ArrayList<>();
		
		//if (list == null || list.size() == 0) {
			//return list2;
		//}
		
		//ScpDb scpDb = new ScpDb();
		
		//for (int i=0; i < list.size(); i++) {
			//Map<String, Object> map = list.get(i);
			//map.put("USER_NM", Masking.nameMasking(scpDb.scpDecB64((String)map.get("USER_NM"))));
			//list2.add(map);
		//}
		
		return list;
	}
	
	@Override
	public void saveDisconnectUser(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmParam");
		
		Map<String, String> map = parameterGroup.getSingleValueMap();

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		map.put("USER_ID2", userId);
		
		disconnectUserMapper.saveDisconnectUser(map);
		
		if ("N".equals(map.get("DISCONNECT_YN"))) {
			disconnectUserMapper.insetLoginHistory(map.get("USER_ID"));
		}
		
		map.put("USER_CNTN_INTRCP_YN", map.get("DISCONNECT_YN"));
		
		map.put("DATAA_CHG_SE_CD", "U");
		personalInfoMapper.insertUserInfoHistory(new HashMap<String, Object>(map));
	}
	
	@Override
	public Integer selectDisconnectUserCount(Map<String, Object> map) throws Exception {
		return disconnectUserMapper.selectDisconnectUserCount(map);
	}

	@Override
	public Map<String, String> selectDisconnectUserInfo(String userId) throws Exception {
		return disconnectUserMapper.selectDisconnectUserInfo(userId);
	}
	
}

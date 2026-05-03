/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.actvt.service.impl;

import java.util.ArrayList;
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
import isry.drmgs.actvt.mapper.ActvtSafetyMuaiasMapper;
import isry.drmgs.actvt.service.ActvtSafetyMuaiasService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : ActvtSafetyMuaiasServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2022. 7. 14. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2022. 7. 14.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("actvtSafetyMuaiasService")
public class ActvtSafetyMuaiasServiceImpl extends IsryBaseServiceImpl implements ActvtSafetyMuaiasService {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
//	@Resource(name="trprInqService")
//	private TrprInqService trprInqService;
	
	@Resource(name="actvtSafetyMuaiasMapper")
	private ActvtSafetyMuaiasMapper actvtSafetyMuaiasMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	public List<Map<String, Object>> selectActvtSafetyMuaiasList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		List<Map<String, Object>> rtnMap = new ArrayList<Map<String,Object>>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
		HttpSession session   = request.getSession();
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
        
        /* 20230126_강화영_권한 적용_시작 */
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) -> {
			paramMap2.put(StrKey, StrValue);
		}); /* 형변환 */

		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/* 20230126_강화영_권한 적용_종료 */
        
		return actvtSafetyMuaiasMapper.selectActvtSafetyMuaiasList(paramMap2);
	}	
	
	public List<Map<String, Object>> selectActvtSafetyMuaiasPopupList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		List<Map<String, Object>> rtnList = new ArrayList<Map<String,Object>>();
		ParameterGroup parameterGroup = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> paramMap = parameterGroup.getSingleValueMap();
				
		rtnList = actvtSafetyMuaiasMapper.selectActvtSafetyMuaiasPopupList(paramMap); // 대상조회
		if(rtnList.size() == 0) {
			rtnList = actvtSafetyMuaiasMapper.selectActvtSafetyMuaiasPopupList2(paramMap); // 비대상조회
		}
		
		return rtnList;
	}
}

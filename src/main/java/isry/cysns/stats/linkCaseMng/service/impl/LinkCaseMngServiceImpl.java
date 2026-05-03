/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.cysns.stats.linkCaseMng.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;

import isry.cysns.stats.linkCaseMng.mapper.LinkCaseMngMapper;
import isry.cysns.stats.linkCaseMng.service.LinkCaseMngService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : LinkCaseMngServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Hye.Sun
 * @작성일        : 2023. 5. 12. 
 * @수정자        : Lee.Hye.Sun
 * @수정일        : 2023. 5. 12.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("linkCaseMngService")
public class LinkCaseMngServiceImpl implements LinkCaseMngService{
	
	@Resource(name = "linkCaseMngMapper")
	private LinkCaseMngMapper linkCaseMngMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;	
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;
	
	/**
	 * @Method명   : selectLinkCaseMngList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2023. 5. 12. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectLinkCaseMngList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		Map<String, Object> dmMap = new HashMap<String, Object>();
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
//		dmSearch.forEach((strKey, strValue) -> {
//			dmMap.put(strKey, strValue);
//		});
		
		dmMap.putAll(dmSearch);
		
		dmMap.put("ARR_RELM_SE", dmSearch.get("RELM_SE").split(","));
		dmMap.put("ARR_LEVEL_SE", dmSearch.get("LEVEL_SE").split(","));
		dmMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		/*20230126_강화영_권한 적용_시작*/
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());
//		dmSearch.forEach((StrKey, StrValue) ->{ dmMap.put(StrKey, StrValue); }); /* 형변환*/
		
		dmMap.put("INST_NOS", comMap.get("INST_NOS"));
		dmMap.put("ENFSN_NO", loginVO.getEnfsnNo());
		dmMap.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/	
		
		return linkCaseMngMapper.selectLinkCaseMngList(dmMap);
	}


}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.workaltmntmng.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.LoginVO;
import isry.base.IsryBaseServiceImpl;
import isry.couns.mngr.workaltmntmng.mapper.GitpleChttWorkHistoryMapper;
import isry.couns.mngr.workaltmntmng.service.GitpleChttWorkHistoryService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : GitpleChttWorkHistoryServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kim.Hai.Ryong
 * @작성일        : 2023. 5. 3. 
 * @수정자        : Kim.Hai.Ryong
 * @수정일        : 2023. 5. 3.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("GitpleChttWorkHistoryServiceImpl")
public class GitpleChttWorkHistoryServiceImpl extends IsryBaseServiceImpl implements GitpleChttWorkHistoryService {

	
	@Resource(name = "GitpleChttWorkHistoryMapper")
	private GitpleChttWorkHistoryMapper GitpleChttWorkHistoryMapper;
	
	@Resource(name="userLoginService")
	private UserLoginService UserLoginService;
	
	/**
	 * @Method명   : GitpleChttWorkHistoryList
	 * @param request
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.Hai.Ryong
	 * @작성일     : 2023. 5. 3. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> GitpleChttWorkHistoryList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception {
		
		// 사용자 정보 조회
		UserDetailsVO loginVO = UserLoginService.getLoginSessionVO(request);
		String sUserId = loginVO.getId();
		
		mapParam.put("sUserId", sUserId);
				
		return GitpleChttWorkHistoryMapper.selectGitpleChttWorkHistoryList(mapParam);
	}

}

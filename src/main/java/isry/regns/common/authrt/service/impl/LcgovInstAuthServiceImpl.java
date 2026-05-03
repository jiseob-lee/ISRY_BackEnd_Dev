/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.regns.common.authrt.service.impl;

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

import isry.itgcms.sysmgmt.userauth.service.impl.UserInstAuthServiceImpl;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.regns.common.authrt.mapper.LcgovInstAuthMapper;
import isry.regns.common.authrt.service.LcgovInstAuthService;

/**
 * @파일명        : PopupServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Myeong.Sang
 * @작성일        : 2022. 6. 3. 
 * @수정자        : Lee.Myeong.Sang
 * @수정일        : 2022. 6. 3.
 * @수정내용      : 
 * -                
 * -                
 */

@Service("lcgovInstAuthService")
public class LcgovInstAuthServiceImpl implements LcgovInstAuthService{
	
	@Resource(name = "lcgovInstAuthMapper")
	private LcgovInstAuthMapper lcgovInstAuthMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;

	private static final Logger LOGGER = LoggerFactory.getLogger(LcgovInstAuthServiceImpl.class);

	/**
	 * @Method명   : selectAuthrtList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Myeong.Sang
	 * @작성일     : 2023. 7. 24. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> createInstSrchParams(HttpServletRequest request) throws Exception {

		UserDetailsVO userDetailsVO = userLoginService.getLoginSessionVO(request);
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("INST_NO", String.valueOf(userDetailsVO.getInstNo()));
		
		List<Integer> resultList = lcgovInstAuthMapper.selectLcgovInstList(paramMap);

		Map<String, Object> resultMap = new HashMap<>();

		LOGGER.debug("##### createInstSrchParams: resultList.size()={}", resultList.size());

		
		resultMap.put(KEY_INST_NOS, resultList);
		
		return resultMap;
		

	}


}

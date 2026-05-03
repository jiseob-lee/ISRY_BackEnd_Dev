/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.preconmng.eduprecon.service.impl;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.subms.preconmng.eduprecon.mapper.EduPreconMapper;
import isry.subms.preconmng.eduprecon.service.EduPreconService;

/**
 * @파일명 : eduPreconServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2023. 7. 7.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2023. 7. 7.
 * @수정내용 : - -
 */
@Service("eduPreconService")
public class EduPreconServiceImpl implements EduPreconService {

	@Resource(name = "eduPreconMapper")
	private EduPreconMapper eduPreconMapper;

	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	

	/**
	 * @Method명 : selectEduPreconList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2023. 7. 7.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectEduPreconList(DataRequest dataRequest, HttpServletRequest request)
			throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		//권한적용
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, loginVO.getUntTaskwk());

		paramMap.putAll(dmSearch.getSingleValueMap());
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		paramMap.put("INST_NOS", comMap.get("INST_NOS"));
		
		return eduPreconMapper.selectEduPreconList(paramMap);
	}

}

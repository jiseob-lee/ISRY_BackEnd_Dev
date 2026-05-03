/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.stats.nowenstprecon.service.impl;


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;

import isry.csemd.stats.nowenstprecon.mapper.NowEnstPreconMapper;
import isry.csemd.stats.nowenstprecon.service.NowEnstPreconService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : NowEnstPreconServiceImpl.java
 * @프로그램 설명 : 현재입교생현황 서비스임플리먼트 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 2. 6.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 2. 6.
 * @수정내용 : - -
 */
@Service("nowEnstPreconService")
public class NowEnstPreconServiceImpl implements NowEnstPreconService {

	// 세션정보호출 관련 서비스
	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	// 현재입교생현황 관련 매퍼
	@Resource(name = "nowEnstPreconMapper")
	NowEnstPreconMapper nowEnstPreconMapper;

	/**
	 * @Method명 : selectNowEnstPrecon
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 6.
	 * @Method설명 : 현재입교생현황조회
	 */
	@Override
	public void selectNowEnstPrecon(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		Map<String, String> dmSearch = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		dmSearch.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());
		
		dataRequest.setResponse("dsList", nowEnstPreconMapper.selectNowEnstPrecon(dmSearch));
	}

}

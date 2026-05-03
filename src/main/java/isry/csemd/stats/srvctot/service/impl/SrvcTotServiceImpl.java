/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csemd.stats.srvctot.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;

import isry.csemd.stats.srvctot.mapper.SrvcTotMapper;
import isry.csemd.stats.srvctot.service.SrvcTotService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : SrvcTotServiceImpl.java
 * @프로그램 설명 : 서비스별집계 Service Implement - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2023. 2. 13.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2023. 2. 13.
 * @수정내용 : - -
 */
@Service(value = "srvcTotService")
public class SrvcTotServiceImpl implements SrvcTotService {

	// 세션정보호출 관련 서비스
	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	// 서비스별집계 관련 매퍼
	@Resource(name = "srvcTotMapper")
	private SrvcTotMapper srvcTotMapper;

	/**
	 * @Method명 : selectYrStats
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 13.
	 * @Method설명 : 연도별통계 조회
	 */
	@Override
	public void selectYrStats(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> reqMap = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		reqMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());

		dataRequest.setResponse("dsYrStatsPivot", srvcTotMapper.selectYrStats(reqMap));

	}

	/**
	 * @Method명 : selectPrdCrseEnfsnStats
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2023. 2. 14.
	 * @Method설명 : 기간별통계 & 과정별통계 & 종사자별통계 조회
	 */
	@Override
	public void selectPrdCrseEnfsnStats(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);

		Map<String, String> reqMap = dataRequest.getParameterGroup("dmSearch").getSingleValueMap();
		reqMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());

		// 피봇시킬 데이터 조회
		dataRequest.setResponse("dsSrvcStatsPivot", srvcTotMapper.selectPrdCrseEnfsnStats(reqMap));

		// 피봇시킨 데이터가 들어가는 데이터셋
		dataRequest.setResponse("dsSrvcStats", srvcTotMapper.selectPrdCrseEnfsnStatsSum(reqMap));
	}
}

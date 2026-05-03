/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.couns.stats.dscsnstats.mapper.OutReachStatsMapper;
import isry.couns.stats.dscsnstats.service.OutReachStatsService;




@Service("outReachStatsService")
public class OutReachStatsServiceimpl extends IsryBaseServiceImpl implements OutReachStatsService   {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "outReachStatsMapper")
	private OutReachStatsMapper outReachStatsMapper;

	/**
	 * @Method명   : selectoutReachStats
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 아웃리치 통계 조회 Method (방법별 및 상담사별 실적)
	 */
	@Override
	public void selectoutReachStats(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("dmSearch ::: " + paramMap);
		
		List<Map<String, Object>> dsMethodStateList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> dsCnsltntPrfmncStateList = new ArrayList<Map<String,Object>>();
		
		try {
			
			dsMethodStateList = outReachStatsMapper.selectMethodOutreachStatesList(paramMap);
			dsCnsltntPrfmncStateList = outReachStatsMapper.selectCnsltntPrfmncOutreachStatesList(paramMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LOGGER.debug("상담사별 실적 통계 List 갯수 ::: " + dsCnsltntPrfmncStateList.size());
		
		dataRequest.setResponse("dsMethodStateList", dsMethodStateList);
		dataRequest.setResponse("dsCnsltntPrfmncStateList", dsCnsltntPrfmncStateList);
		
	}


}
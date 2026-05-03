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

import isry.couns.stats.dscsnstats.mapper.MobileManageStatsMapper;
import isry.couns.stats.dscsnstats.service.MobileManageStatsService;
import isry.itgcms.util.ScpDb;



@Service("mobileManageStatsService")
public class MobileManageStatsServiceimpl extends IsryBaseServiceImpl implements MobileManageStatsService   {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	ScpDb  scpDb  = new ScpDb();

	@Resource(name = "mobileManageStatsMapper")
	private MobileManageStatsMapper mobileManageStatsMapper;

	/**
	 * @Method명   : selectAfterFactMngStatsList
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 27. 
	 * @Method설명 : 사후관리 통계 조회(대상 및 응답여부/미응답 사유/응답내용/사후관리 실적)
	 */
	@Override
	public void selectAfterFactMngStatsList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("dmSearch ::: " + paramMap);
		
		List<Map<String, Object>> dsTrgtAndResponseYnStateList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> dsNonResponseCsStateList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> dsResponseCnStateList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> dsCnsltntPerformanceStatsList = new ArrayList<Map<String,Object>>();
		
		try {
			
			dsTrgtAndResponseYnStateList = mobileManageStatsMapper.selectTrgtAndResponseYnStatsList(paramMap);
			dsNonResponseCsStateList = mobileManageStatsMapper.selectNonResponseCsStatsList(paramMap);
			dsResponseCnStateList = mobileManageStatsMapper.selectResponseCnStatsList(paramMap);
			dsCnsltntPerformanceStatsList = mobileManageStatsMapper.selectCnsltntPerformanceStatsList(paramMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		dataRequest.setResponse("dsTrgtAndResponseYnStateList", dsTrgtAndResponseYnStateList);
		dataRequest.setResponse("dsNonResponseCsStateList", dsNonResponseCsStateList);
		dataRequest.setResponse("dsResponseCnStateList", dsResponseCnStateList);
		dataRequest.setResponse("dsCnsltntPerformanceStatsList", dsCnsltntPerformanceStatsList);
		
	}
}
/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import com.tomatosystem.exbuilder6.core.util.StringUtil;

import isry.base.IsryBaseServiceImpl;
import isry.couns.stats.dscsnstats.mapper.ProblemAreaStatsMapper;
import isry.couns.stats.dscsnstats.service.ProblemAreaStatsService;

@Service
public class ProblemAreaStatsServiceImpl extends IsryBaseServiceImpl implements ProblemAreaStatsService {

	@Resource(name = "problemAreaStatsMapper")
	private ProblemAreaStatsMapper mapper;
	
	/**
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> list(Map<String, Object> mapParam) throws Exception {
		
		Map<String, Object> result = new HashMap<>();

		List<Map<String, Object>> dsList = new ArrayList<Map<String,Object>>();
    	dsList = mapper.list(mapParam);	

        result.put("dsList", dsList);
        
        return result;        
	}

}

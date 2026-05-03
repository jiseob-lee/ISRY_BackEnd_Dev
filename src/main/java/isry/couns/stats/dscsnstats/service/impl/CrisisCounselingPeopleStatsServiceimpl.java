/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.base.IsryBaseServiceImpl;
import isry.couns.stats.dscsnstats.mapper.CrisisCounselingPeopleStatsMapper;
import isry.couns.stats.dscsnstats.service.CrisisCounselingPeopleStatsService;

@Service("crisisCounselingPeopleStatsService")
public class CrisisCounselingPeopleStatsServiceimpl extends IsryBaseServiceImpl implements CrisisCounselingPeopleStatsService   {

	@Resource(name = "crisisCounselingPeopleStatsMapper")
	private CrisisCounselingPeopleStatsMapper crisisCounselingPeopleStatsMapper;




	/**
	 * @Method명   : selectcrisisCounselingPeopleStats
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectcrisisCounselingPeopleStats(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return crisisCounselingPeopleStatsMapper.selectcrisisCounselingPeopleStats(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectcrisisCounselingPeopleStats2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return crisisCounselingPeopleStatsMapper.selectcrisisCounselingPeopleStats2(mapParam);
	}

}

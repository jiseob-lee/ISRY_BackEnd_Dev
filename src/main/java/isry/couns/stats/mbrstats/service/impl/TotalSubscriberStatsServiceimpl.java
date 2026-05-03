/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.mbrstats.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.util.StringUtil;

import isry.base.IsryBaseServiceImpl;
import isry.couns.stats.mbrstats.mapper.TotalSubscriberStatsMapper;
import isry.couns.stats.mbrstats.service.TotalSubscriberStatsService;


 
/**
 * @param <TotalSubscriberStatsMapper>
 * @param <selecttotalSubscriberStats>
 * @파일명 : SurvshtMmnServiceImpl.java
 * @프로그램 설명 : 설문지 작성을 관리하는 ServiceImpl
 * @작성자 : kim.seong.gyu
 * @작성일 : 2022. 5. 04
 * @수정자 : 
 * @수정일 : 
 * @수정내용 : - -
 */
@Service("totalSubscriberStatsService")
public class TotalSubscriberStatsServiceimpl extends IsryBaseServiceImpl implements TotalSubscriberStatsService {

	@Resource(name = "totalSubscriberStatsMapper")
	private TotalSubscriberStatsMapper mapper;


	/**
	 * @Method명   : selecttotalSubscriberStats
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 6. 8. 
	 * @수정자     : Kim Hai Ryong
	 * @수정일     : 2023. 3. 8.  
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectTotalSubscriberStats(Map<String, Object> mapParam) throws Exception {
		
        return mapper.selectTotalSubscriberStats(mapParam);	
	}

	@Override
	public List<Map<String, Object>> selectTotalSubscriberStatsDetail(Map<String, Object> mapParam) throws Exception {
		
		log.debug("@@@##" + mapParam.toString());
        return mapper.selectTotalSubscriberStatsDetail(mapParam);	
	}

	protected int getLastDay(String yearMonth) {
		Calendar cal = Calendar.getInstance();	
		cal.set(Integer.parseInt( yearMonth.substring(0, 4) ), Integer.parseInt(yearMonth.substring(4, 6))-1, 1);		
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
	}

}

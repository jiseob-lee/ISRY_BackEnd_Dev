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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tomatosystem.exbuilder6.core.util.StringUtil;

import isry.base.IsryBaseServiceImpl;
import isry.couns.stats.dscsnstats.mapper.ActualUserStatsMapper;
import isry.couns.stats.dscsnstats.service.ActualUserStatsService;

@Service("actualUserStatsService")
public class ActualUserStatsServiceImpl extends IsryBaseServiceImpl implements ActualUserStatsService {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource(name = "actualUserStatsMapper")
	private ActualUserStatsMapper actualUserStatsMapper;
	
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
	public List<Map<String, Object>> list(Map<String, Object> mapParam) throws Exception {
		
        log.debug("ActualUserStatsServiceImpl.....");
        String startDate = String.valueOf(mapParam.get("startDate"));
        String endDate = String.valueOf(mapParam.get("endDate"));
        String startYear = startDate.substring(0, 4);
        String endYear = endDate.substring(0, 4);
        
        ArrayList<String> month =  new ArrayList<String>();
        if(startYear.equals(endYear)) {
        	int startYM = Integer.parseInt(startDate.substring(0, 6));
        	int endYM = Integer.parseInt(endDate.substring(0, 6));
        	for(int i=startYM;i<=endYM;i++) {
        		month.add(String.valueOf(i));
        	}       	
        }else {
        	int startYY = Integer.parseInt(startDate.substring(0, 4));
        	int endYY = Integer.parseInt(endDate.substring(0, 4));
        	int mm = Integer.parseInt(startDate.substring(4, 6));
        	
            log.debug("startYY : "+startYY);
            log.debug("endYY : "+endYY);
        	for(int y=startYY;y<endYY;y++) {
                log.debug("yyyyy : "+y);
            	for(int i=mm;i<=12;i++) {
	        		String k = "0"+String.valueOf(i);
	        		month.add(startYear+k.substring(k.length()-2, k.length()));
	        	}  
            	mm = 1;
        	}
            mm = Integer.parseInt(endDate.substring(4, 6));
        	for(int i=1;i<=mm;i++) {
        		String k = "0"+String.valueOf(i);
        		month.add(endYear+k.substring(k.length()-2, k.length()));
        	}         	
        }
        mapParam.put("month", month);

        log.debug("mapParam =====: "+mapParam.toString());

        return actualUserStatsMapper.listAaaa(mapParam);
		
	}

}

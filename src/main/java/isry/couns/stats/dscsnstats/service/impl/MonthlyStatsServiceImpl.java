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
import isry.couns.mngr.consttsincry.service.ConsttSincryService;
import isry.couns.stats.dscsnstats.mapper.MonthlyStatsMapper;
import isry.couns.stats.dscsnstats.service.MonthlyStatsService;
import isry.itgcms.util.Masking;
import isry.itgcms.util.ScpDb;

@Service("monthlyStatsService")
public class MonthlyStatsServiceImpl extends IsryBaseServiceImpl implements MonthlyStatsService {

	@Resource(name = "monthlyStatsMapper")
	private MonthlyStatsMapper monthlyStatsMapper;
	
	@Override
	public Map<String, Object> selectMonthlyStats(Map<String, Object> mapParam) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		
        String startDate = String.valueOf(mapParam.get("startDate"));
        String endDate = String.valueOf(mapParam.get("endDate"));
        String startYear = startDate.substring(0, 4);
        String endYear = endDate.substring(0, 4);
        String writeType = String.valueOf(mapParam.get("writeType"));
        
        ArrayList<String> month =  new ArrayList<String>();
        if(startYear.equals(endYear)) {
        	int startYM = Integer.parseInt(startDate.substring(0, 6));
        	int endYM = Integer.parseInt(endDate.substring(0, 6));
        	for(int i=startYM;i<=endYM;i++) {
        		month.add("A"+String.valueOf(i));
        	}       	
        }else {
        	int mm = Integer.parseInt(startDate.substring(4, 6));
        	
        	for(int i=mm;i<=12;i++) {
        		String k = "0"+String.valueOf(i);
        		month.add("A"+startYear+k.substring(k.length()-2, k.length()));
        	}  
        	mm = Integer.parseInt(endDate.substring(4, 6));
        	for(int i=1;i<=mm;i++) {
        		String k = "0"+String.valueOf(i);
        		month.add("A"+endYear+k.substring(k.length()-2, k.length()));
        	}         	
        }
        mapParam.put("month", month);
        
        List<Map<String, Object>> dsList = new ArrayList<Map<String,Object>>();
//        List<Map<String, Object>> dsChartList = new ArrayList<Map<String,Object>>();

    	dsList = monthlyStatsMapper.selectMonthlyStats(mapParam);	
		for (Map<String, Object> map : dsList) {
			int ssum = 0;
			for(int i=0; i < month.size(); i++) {
				if (map.get(month.get(i)) == null ) continue;
				ssum += Integer.parseInt(map.get(month.get(i)).toString());
			}
			map.put("SSUM", ssum);
		}
		
    	
//    	dsChartList = monthlyStatsMapper.selectMonthStatsChart(mapParam);

        result.put("dsList", dsList);
//        result.put("dsChartList", dsChartList);
        
        return result;        
		
	}
	
	@Override
	public List<Map<String, Object>> selectMonthlyStatsDetail(Map<String, Object> mapParam) throws Exception {
		
        String yearMonth = String.valueOf(mapParam.get("yearMonth"));
        String writeType = String.valueOf(mapParam.get("writeType"));
        
        ArrayList<String> day =  new ArrayList<String>();
        int lastDay = this.getLastDay(yearMonth);
        
        for(int i=1; i<=lastDay ; i++) {
        	day.add( StringUtil.lPad(String.valueOf(i) , 2, "0") );
        }

        mapParam.put("ddday", day);
//System.out.println("mapParam======"+mapParam.toString());
        
       	return monthlyStatsMapper.selectMonthlyStatsDetail(mapParam);	
		
	}
	
	protected int getLastDay(String yearMonth) {
		Calendar cal = Calendar.getInstance();	
		cal.set(Integer.parseInt( yearMonth.substring(0, 4) ), Integer.parseInt(yearMonth.substring(4, 6))-1, 1);		
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
	}
}

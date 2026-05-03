/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.couns.stats.dscsnstats.mapper.WeeklyStatsMapper;
import isry.couns.stats.dscsnstats.service.WeeklyStatsService;

@Service
public class WeeklyStatsServiceImpl extends IsryBaseServiceImpl implements WeeklyStatsService {

	@Resource(name = "weeklyStatsMapper")
	private WeeklyStatsMapper mapper;
	
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
		return mapper.list(mapParam);
	}
	
	/**
	 * @Method명   : listConstt
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee TaeHo
	 * @작성일     : 2023. 1. 13. 
	 * @Method설명 : 요일별통계_상담자글
	 */
	@Override
	public List<Map<String, Object>> listConstt(Map<String, Object> mapParam) throws Exception {
		return mapper.listConstt(mapParam);
	}
	
	/**
	 * @Method명   : listChtt
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee TaeHo
	 * @작성일     : 2023. 1. 13. 
	 * @Method설명 : 요일별통계_채팅
	 */
	@Override
	public List<Map<String, Object>> listChtt(Map<String, Object> mapParam) throws Exception {
		return mapper.listChtt(mapParam);
	}
	
	/**
	 * @Method명   : listChttHour
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee TaeHo
	 * @작성일     : 2023. 1. 13. 
	 * @Method설명 : 시간별통계_채팅
	 */
	@Override
	public List<Map<String, Object>> listChttHour(Map<String, Object> mapParam) throws Exception {
		return mapper.listChttHour(mapParam);
	}
	
	/**
	 * @Method명   : listChttProblem
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee TaeHo
	 * @작성일     : 2023. 1. 13.
	 * @수정자		 : Kim HaiRyong
	 * @수정일		 : 2023.02.21 
	 * @Method설명 : 문제상태별통계_내담자_채팅
	 */
	@Override
	public List<Map<String, Object>> listChttProblem(Map<String, Object> mapParam) throws Exception {
		return mapper.listChttProblem(mapParam);
	}
	
	/**
	 * @Method명   : listProblem
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee TaeHo
	 * @작성일     : 2023. 1. 13. 
	 * @수정자		 : Kim HaiRyong
	 * @수정일		 : 2023.02.21 
	 * @Method설명 : 문제상태별통계_내담자_채팅이외
	 */
	@Override
	public List<Map<String, Object>> listProblem(Map<String, Object> mapParam) throws Exception {
		return mapper.listProblem(mapParam);
	}
	
	/**
	 * @Method명   : listConsttProblem
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim HaiRyong
	 * @작성일     : 2023.07.06. 
	 * @수정자		 : 
	 * @수정일		 :  
	 * @Method설명 : 문제상태별통계_상담자_채팅이외 
	 */
	@Override
	public List<Map<String, Object>> listConsttProblem(Map<String, Object> mapParam) throws Exception {
		return mapper.listConsttProblem(mapParam);
	}
	
	/**
	 * @Method명   : listMm
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee TaeHo
	 * @작성일     : 2023. 1. 13. 
	 * @Method설명 : 월별통계_내담자글
	 */
	@Override
//	public List<Map<String, Object>> listMm(Map<String, Object> mapParam) throws Exception {
//		return mapper.listMm(mapParam);
//	}
	public Map<String, Object> listMm(Map<String, Object> mapParam) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		
        String startDate = String.valueOf(mapParam.get("startDate"));
        String endDate = String.valueOf(mapParam.get("endDate"));
        String startYM = startDate.substring(0, 6);
        String endYM = endDate.substring(0, 6);
        String writeType = String.valueOf(mapParam.get("writeType"));
        
        ArrayList<String> month =  new ArrayList<String>();
//        if(startYear.equals(endYear)) {
//        	int startYM = Integer.parseInt(startDate.substring(0, 6));
//        	int endYM = Integer.parseInt(endDate.substring(0, 6));
//        	for(int i=startYM;i<=endYM;i++) {
//        		month.add("A"+String.valueOf(i));
//        	}       	
//        }else {
//        	int mm = Integer.parseInt(startDate.substring(4, 6));
//        	
//        	for(int i=mm;i<=12;i++) {
//        		String k = "0"+String.valueOf(i);
//        		month.add("A"+startYear+k.substring(k.length()-2, k.length()));
//        	}  
//        	mm = Integer.parseInt(endDate.substring(4, 6));
//        	for(int i=1;i<=mm;i++) {
//        		String k = "0"+String.valueOf(i);
//        		month.add("A"+endYear+k.substring(k.length()-2, k.length()));
//        	}         	
//        }
        
        for (int i= Integer.valueOf(startYM); i <= Integer.valueOf(endYM); i++) {
            log.debug("for startYM ::: " + i);
            if (String.valueOf(i).substring(4, 6) == "13") {
                i += 88;
                log.debug("String.valueOf(i).substring(4, 2) + 1 ::: " + i);
            }
            month.add("A"+String.valueOf(i));
        }
        
        
        mapParam.put("month", month);
        
        List<Map<String, Object>> dsListMm = new ArrayList<Map<String,Object>>();
//        List<Map<String, Object>> dsChartList = new ArrayList<Map<String,Object>>();

        dsListMm = mapper.listMm(mapParam);	
		for (Map<String, Object> map : dsListMm) {
			int ssum = 0;
			for(int i=0; i < month.size(); i++) {
				if (map.get(month.get(i)) == null ) {
					map.put(month.get(i), 0);
					continue;
				}
				ssum += Integer.parseInt(map.get(month.get(i)).toString());
			}
			map.put("SSUM", ssum);
		}

        result.put("dsListMm", dsListMm);
        
        return result;        
		
	}
	
	/**
	 * @Method명   : listChttMm
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee TaeHo
	 * @작성일     : 2023. 1. 13. 
	 * @Method설명 : 월별통계_내담자글_채팅
	 */
	@Override
	public Map<String, Object> listChttMm(Map<String, Object> mapParam) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		
        String startDate = String.valueOf(mapParam.get("startDate"));
        String endDate = String.valueOf(mapParam.get("endDate"));
//        String startYear = startDate.substring(0, 4);
//        String endYear = endDate.substring(0, 4);
        String startYM = startDate.substring(0, 6);
        String endYM = endDate.substring(0, 6);
        String writeType = String.valueOf(mapParam.get("writeType"));
        
        ArrayList<String> month =  new ArrayList<String>();
//        if(startYear.equals(endYear)) {
//        	int startYM = Integer.parseInt(startDate.substring(0, 6));
//        	int endYM = Integer.parseInt(endDate.substring(0, 6));
//        	for(int i=startYM;i<=endYM;i++) {
//        		month.add("A"+String.valueOf(i));
//        	}       	
//        }else {
//        	int mm = Integer.parseInt(startDate.substring(4, 6));
//        	
//        	for(int i=mm;i<=12;i++) {
//        		String k = "0"+String.valueOf(i);
//        		month.add("A"+startYear+k.substring(k.length()-2, k.length()));
//        	}  
//        	mm = Integer.parseInt(endDate.substring(4, 6));
//        	for(int i=1;i<=mm;i++) {
//        		String k = "0"+String.valueOf(i);
//        		month.add("A"+endYear+k.substring(k.length()-2, k.length()));
//        	}         	
//        }
        
        for (int i= Integer.valueOf(startYM); i <= Integer.valueOf(endYM); i++) {
            log.debug("for startYM ::: " + i);
            if (String.valueOf(i).substring(4, 6) == "13") {
                i += 88;
                log.debug("String.valueOf(i).substring(4, 2) + 1 ::: " + i);
            }
            month.add("A"+String.valueOf(i));
        }
        
        mapParam.put("month", month);
        
        List<Map<String, Object>> dsListChttMm = new ArrayList<Map<String,Object>>();
//        List<Map<String, Object>> dsChartList = new ArrayList<Map<String,Object>>();

        dsListChttMm = mapper.listChttMm(mapParam);	
		for (Map<String, Object> map : dsListChttMm) {
			int ssum = 0;
			for(int i=0; i < month.size(); i++) {
				if (map.get(month.get(i)) == null ) {
					map.put(month.get(i), 0);
					continue;
				}
				ssum += Integer.parseInt(map.get(month.get(i)).toString());
			}
			map.put("SSUM", ssum);
		}

        result.put("dsListChttMm", dsListChttMm);
        
        return result;        
		
	}
	
	/**
	 * @Method명   : listConsttMm
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee TaeHo
	 * @작성일     : 2023. 1. 16. 
	 * @Method설명 : 월별통계_상담자글
	 */
	@Override
	public Map<String, Object> listConsttMm(Map<String, Object> mapParam) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		
        String startDate = String.valueOf(mapParam.get("startDate"));
        String endDate = String.valueOf(mapParam.get("endDate"));
//        String startYear = startDate.substring(0, 4);
//        String endYear = endDate.substring(0, 4);
        String startYM = startDate.substring(0, 6);
        String endYM = endDate.substring(0, 6);
        String writeType = String.valueOf(mapParam.get("writeType"));
        
        ArrayList<String> month =  new ArrayList<String>();
//        if(startYear.equals(endYear)) {
//        	int startYM = Integer.parseInt(startDate.substring(0, 6));
//        	int endYM = Integer.parseInt(endDate.substring(0, 6));
//        	for(int i=startYM;i<=endYM;i++) {
//        		month.add("A"+String.valueOf(i));
//        	}       	
//        }else {
//        	int mm = Integer.parseInt(startDate.substring(4, 6));
//        	
//        	for(int i=mm;i<=12;i++) {
//        		String k = "0"+String.valueOf(i);
//        		month.add("A"+startYear+k.substring(k.length()-2, k.length()));
//        	}  
//        	mm = Integer.parseInt(endDate.substring(4, 6));
//        	for(int i=1;i<=mm;i++) {
//        		String k = "0"+String.valueOf(i);
//        		month.add("A"+endYear+k.substring(k.length()-2, k.length()));
//        	}         	
//        }
        for (int i= Integer.valueOf(startYM); i <= Integer.valueOf(endYM); i++) {
            log.debug("for startYM ::: " + i);
            if (String.valueOf(i).substring(4, 6) == "13") {
                i += 88;
                log.debug("String.valueOf(i).substring(4, 2) + 1 ::: " + i);
            }
            month.add("A"+String.valueOf(i));
        }
        
        mapParam.put("month", month);
        
        List<Map<String, Object>> dsListMm = new ArrayList<Map<String,Object>>();
//        List<Map<String, Object>> dsChartList = new ArrayList<Map<String,Object>>();

        dsListMm = mapper.listConsttMm(mapParam);	
		for (Map<String, Object> map : dsListMm) {
			int ssum = 0;
			for(int i=0; i < month.size(); i++) {
				if (map.get(month.get(i)) == null ) {
					map.put(month.get(i), 0);
					continue;
				}
				ssum += Integer.parseInt(map.get(month.get(i)).toString());
			}
			map.put("SSUM", ssum);
		}

        result.put("dsListMm", dsListMm);
        
        return result;        
		
	}
	
	/**
	 * @Method명   : listHour
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee TaeHo
	 * @작성일     : 2023. 1. 13. 
	 * @Method설명 : 시간대별통계_내담자글
	 */
	@Override
	public List<Map<String, Object>> listHour(Map<String, Object> mapParam) throws Exception {
		return mapper.listHour(mapParam);
	}
	
	/**
	 * @Method명   : listConsttHour
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee TaeHo
	 * @작성일     : 2023. 1. 13. 
	 * @Method설명 : 시간대별통계_상담자글
	 */
	@Override
	public List<Map<String, Object>> listConsttHour(Map<String, Object> mapParam) throws Exception {
		return mapper.listConsttHour(mapParam);
	}
	
	/**
	 * @Method명   : listChttDscsn
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Lee TaeHo
	 * @작성일     : 2023. 1. 19. 
	 * @Method설명 : 채팅상담실적통계
	 */
	@Override
	public List<Map<String, Object>> listChttDscsn(Map<String, Object> mapParam) throws Exception {
		return mapper.listChttDscsn(mapParam);
	}


}

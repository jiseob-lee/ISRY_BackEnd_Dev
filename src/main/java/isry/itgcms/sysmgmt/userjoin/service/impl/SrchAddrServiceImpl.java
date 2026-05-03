/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부 및 위기 청소년 통합 지원 시스템 관리팀의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/

package isry.itgcms.sysmgmt.userjoin.service.impl;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.cursor.Cursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userjoin.mapper.SrchAddrMapper;
import isry.itgcms.sysmgmt.userjoin.service.SrchAddrService;

/**
 * 
 * @파일명        : SrchAddrServiceImpl.java
 * @프로그램 설명 : 주소 검색 서비스
 * - 
 * - 
 * @작성자        : Lee.Ji.Seob
 * @작성일        : 2021. 11. 23. 
 * @수정자        : Lee.Ji.Seob
 * @수정일        : 2021. 11. 23.
 * @수정내용      : 
 * -                
 * -
 */
@Service("srchAddrService")
public class SrchAddrServiceImpl extends IsryBaseServiceImpl implements SrchAddrService {

	@Resource(name="srchAddrMapper")
    private SrchAddrMapper srchAddrMapper;
	
	@Override
	public List<Map<String, String>> selectAddr(String search1) throws Exception {
		
		Instant start = Instant.now();

		Map<String, String> paramMap1 = new HashMap<>();
		String[] tmpArr = search1.split(" ");
		List<String> list = new ArrayList<>();
		for (int i=0; i < tmpArr.length; i++) {
			if (!"".equals(tmpArr[i].trim())) {
				list.add(tmpArr[i].trim());
			}
		}
		
		Map<String, String> paramMap2 = new HashMap<>();
		paramMap2.put("CTPV_NM", list.get(0));
		String sido = srchAddrMapper.selectSido(paramMap2);
		if (sido != null && !"".equals(sido)) {
			paramMap1.put("si", sido);
			list.remove(0);
		}
		if (list.size() > 0 && (list.get(0).endsWith("시") || list.get(0).endsWith("군") || list.get(0).endsWith("구"))) {
			paramMap1.put("gu", list.get(0));
			list.remove(0);
		}
		for (int i=0; i < list.size(); i++) {
			if (isNumeric(list.get(i))) {
				paramMap1.put("no", list.get(i));
				list.remove(i);
				break;
			}
		}
		if (list.size() > 0) {
			paramMap1.put("keyword", list.get(0));
		}
		
		//List<String> list1 = srchAddrMapper.selectAddr(paramMap1);
		Cursor<Object> cur = srchAddrMapper.selectAddr(paramMap1);
		List<String> list1 = new ArrayList<>();
		for (Object ob : cur) {
			//LOGGER.debug("#### ob2 : " + ob);
			if (list1.size() >= 200) {
				break;
			}
			if (!list1.contains((String)ob)) {
				//LOGGER.debug("#### ob1 : " + ob);
				list1.add((String)ob);
			}
		}
		//LOGGER.debug("#### size : " + list1.size());
		
		Instant finish1 = Instant.now();
		
		//Set<String> set = new HashSet<>(list1);
		//List<String> sortedList = new ArrayList<>(set);
		Collections.sort(list1);
		//list = sortedList;
		//list = sortedList.subList(0, sortedList.size() > 100 ? 100 : sortedList.size());
		List<Map<String, String>> returnList = new ArrayList<>();
		for (int i=0; i < list1.size(); i++) {
			String[] tmpArr1 = list1.get(i).split("\\|");
			Map<String, String> map = new HashMap<>();
			map.put("addr", tmpArr1[0]);
			map.put("zipNo", tmpArr1[1]);
			map.put("sidoName", tmpArr1[2]);
			map.put("sigunguName", tmpArr1[3]);
			map.put("jibeonJuso", tmpArr1[4]);
			returnList.add(map);
		}
		
		Instant finish2 = Instant.now();
		
		long timeElapsed1 = Duration.between(start, finish1).toMillis();
		long timeElapsed2 = Duration.between(finish1, finish2).toMillis();
		
		log.debug("timeElapsed1 : " + timeElapsed1);
		log.debug("timeElapsed2 : " + timeElapsed2);
		
		return returnList;
	}
	
	public boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}

	public List<Map<String, Object>> selectAddrArea() throws Exception {
		return srchAddrMapper.selectAddrArea();
	}

	@Override
	public List<Map<String, Object>> selectSido() throws Exception {
		return srchAddrMapper.selectSidoArea();
	}
	
	@Override
	public List<Map<String, Object>> selectSgg() throws Exception {
		return srchAddrMapper.selectSgg();
	}
}

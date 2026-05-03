/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.etcstats.service.impl;

import java.util.ArrayList;
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

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.couns.stats.etcstats.mapper.hopeDownloadStatsMapper;
import isry.couns.stats.etcstats.service.hopeDownloadStatsService;

/**
 * @param <selectHopeDownloadStats>
 * @파일명 : SurvshtMmnServiceImpl.java
 * @프로그램 설명 : 설문지 작성을 관리하는 ServiceImpl
 * @작성자 : kim.seong.gyu
 * @작성일 : 2022. 5. 04
 * @수정자 : 
 * @수정일 : 
 * @수정내용 : - -
 */
@Service("hopeDownloadStatsService")
public class hopeDownloadStatsServiceImpl<hopeDownloadStats> extends IsryBaseServiceImpl implements hopeDownloadStatsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "hopeDownloadStatsMapper")
	private hopeDownloadStatsMapper hopeDownloadStatsMapper;

	/**
	 * @Method명   : selectWorkListFrom
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 5. 31. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> hopeDownloadStats(Map<String, String> mapParam) throws Exception {
		// TODO Auto-generated method stub
		
		LOGGER.debug("파라미터 ::: "+ mapParam);
		
		return hopeDownloadStatsMapper.selectHopeDownloadStats(mapParam);
	}


	

}

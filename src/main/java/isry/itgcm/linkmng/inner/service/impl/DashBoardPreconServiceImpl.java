/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.linkmng.inner.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import egovframework.com.cmm.service.EgovProperties;
import isry.itgcm.casemng.caseunity.mapper.CaseRegMapper;
import isry.itgcm.linkmng.inner.mapper.DashBoardPreconMapper;
import isry.itgcm.linkmng.inner.service.DashBoardPreconService;
import isry.itgcms.util.DateUtil;

/**
 * @파일명     	: DashBoardPreconServiceImpl.java
 * @프로그램 설명	: 메인 대시보드에 표현되는 현황별 집계 데이터 생성을 위한 배치
 * @작성자      	: Lee.Seung.Yeon
 * @작성일      	: 2022. 11. 3.
 * @수정자      	: Lee.Seung.Yeon
 * @수정일      	: 2022. 11. 3.
 * @수정내용    	: 
 * -                
 * -                
 */
@Service("dashBoardPreconService")
public class DashBoardPreconServiceImpl implements DashBoardPreconService {

	@Resource(name="dashBoardPreconMapper")
    public DashBoardPreconMapper dashBoardPreconMapper;

	@Resource(name="caseRegMapper")
    public CaseRegMapper caseRegMapper;

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);	

	/**
	 * @Method		: createPreconTotData
	 * @Method설명 	: 현황별 집계 데이터 생성
	 * @param      	: 
	 * @return     	:  
	 * @exception  	: Exception
	 * @작성자     	: Lee.Seung.Yeon
	 * @작성일     	: 2022. 11. 3.
 	 */	
	@Override
	public void createPreconTotData() throws Exception {
		
		LOGGER.debug("=========== 현황별 집계 데이터 생성 START : createPreconTotData ===========");

		String serverName = System.getProperty("SERVER"); // 서버명
		if(!"rybwas11".equals(serverName)) {
			return;
		}

		int inqCnt = 0; //조회건수
		int regCnt = 0; //등록건수

		Map<String, String> paramMap = new HashMap<>();

		// SAB990 데이터 삭제
		dashBoardPreconMapper.deleteSAB990(paramMap);

		String startDate = DateUtil.addMonth(DateUtil.getToday().substring(0,6) + "01", -1);
		String endDate   = "";

		int year  = Integer.parseInt(startDate.substring(0,4));
		int month = Integer.parseInt(startDate.substring(4,6)) + 1;

		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);

		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		endDate = startDate.substring(0,6) + lastDay;

//		LOGGER.debug("시작일자 : " + startDate);
//		LOGGER.debug("종료일자 : " + endDate);

		paramMap.clear();
		paramMap.put("START_DATE", startDate);
		paramMap.put("END_DATE"  , endDate);

		// 현황별 집계 데이터 조회
		List<Map<String, Object>> list = caseRegMapper.selectDashBoardPreconTot(paramMap);
		for(Map<String, Object> map : list) {

			inqCnt++;

			// SAB990 데이터 등록
			dashBoardPreconMapper.insertSAB990(map);

			regCnt++;
		}

		LOGGER.debug("=========== 현황별 집계 데이터 생성 END : createPreconTotData ===========");

		LOGGER.debug("***************** 처리결과*****************");
		LOGGER.debug("*** 조회건수 : " + inqCnt);
		LOGGER.debug("*** 등록건수 : " + regCnt);
		LOGGER.debug("******************************************");
	}

}

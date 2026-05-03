/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.dashboard.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.drmgs.dashboard.mapper.DashboardMapper;
import isry.drmgs.dashboard.service.DashboardService;

/**
 * @파일명        : CnterPreconEnfsnServiceImpl.java
 * @프로그램 설명 : 센터별 종사자 현황
 * - 
 * - 
 * @작성자        : Hee Sung Yoon
 * @작성일        : 2022. 8. 3o. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 8. 3o. 
 * @수정내용      : 
 * -                
 * -                
 */
@Service("DashboardService")
public class DashboardServiceImpl extends IsryBaseServiceImpl implements DashboardService {
	
	@Resource(name = "dashboardMapper")
	private DashboardMapper dashboardMapper;
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	/**
	 * @Method명 : selectEduLinkCnt
	 * @param Map
	 * @return List
	 * @throws Exception
	 * @작성자 : Hee Sung Yoon
	 * @작성일 : 2022. 9. 7.
	 * @Method설명 : 교육청 연계 신규신청 건수
	 */
	@Override
	public List<Map<String, Object>> selectEduLinkCnt(Map<String, Object> map) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		map.put("RINTRV", 0); 
		int eduCnt = dashboardMapper.selectLinkCnt(map);
		
		Map<String, Object> mapEduCnt = new HashMap<String, Object>();
		
		mapEduCnt.put("S_TYPE", "연계 신규신청");
		mapEduCnt.put("I_CNT", eduCnt + " 건");
		list.add(mapEduCnt);
		
		map.put("RINTRV", 1); // 재의뢰 건수 조회
		int eduRintrvCnt = dashboardMapper.selectLinkCnt(map);
		Map<String, Object> mapCnt = new HashMap<String, Object>();
		mapCnt.put("S_TYPE", "재의뢰 요청");
		mapCnt.put("I_CNT", eduRintrvCnt + " 건");
		list.add(mapCnt);
		return list;
	}
	
	/**
	 * @Method명 : selectPicLinkCnt
	 * @param Map
	 * @return List
	 * @throws Exception
	 * @작성자 : Hee Sung Yoon
	 * @작성일 : 2022. 9. 7.
	 * @Method설명 : 경찰청 연계 신규신청 건수
	 */
	@Override
	public List<Map<String, Object>> selectPicLinkCnt(Map<String, Object> map) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		map.put("RINTRV", 0); 
		int picCnt = dashboardMapper.selectLinkCnt(map);
		
		Map<String, Object> mapPicCnt = new HashMap<String, Object>();
		
		mapPicCnt.put("S_TYPE", "연계 신규신청");
		mapPicCnt.put("I_CNT", picCnt + " 건");
		list.add(mapPicCnt);
		
		map.put("RINTRV", 1); // 재의뢰 건수 조회
		int picRintrvCnt = dashboardMapper.selectLinkCnt(map);
		Map<String, Object> mapCnt = new HashMap<String, Object>();
		mapCnt.put("S_TYPE", "재의뢰 요청");
		mapCnt.put("I_CNT", picRintrvCnt + " 건");
		list.add(mapCnt);
		return list;
	}
	
	/**
	 * @Method명 : selectLinkCnt
	 * @param Map
	 * @return List
	 * @throws Exception
	 * @작성자 : Hee Sung Yoon
	 * @작성일 : 2022. 9. 7.
	 * @Method설명 : 교육청, 경찰청 외 연계 신규신청 건수
	 */
	@Override
	public List<Map<String, Object>> selectLinkCnt(Map<String, Object> map) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		map.put("RINTRV", 0); 
		int linkCnt = dashboardMapper.selectLinkCnt(map);
		
		Map<String, Object> mapCnt = new HashMap<String, Object>();
		
		mapCnt.put("S_TYPE", "연계 신규신청");
		mapCnt.put("I_CNT", linkCnt + " 건");
		list.add(mapCnt);
		
		return list;
	}
	
	/**
	 * @Method명 : selectChartData
	 * @param Map
	 * @return List
	 * @throws Exception
	 * @작성자 : Hee Sung Yoon
	 * @작성일 : 2022. 9. 13.
	 * @Method설명 : pie차트 데이터
	 */
	@Override
	public List<Map<String, Object>> selectChartData(Map<String, String> map) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list = dashboardMapper.selectChartData(map);
		
		return list;
	}
	
	/**
	 * @Method명 : selectBarChartData
	 * @param Map
	 * @return List
	 * @throws Exception
	 * @작성자 : Hee Sung Yoon
	 * @작성일 : 2022. 9. 13.
	 * @Method설명 : bar차트 데이터
	 */
	@Override
	public List<Map<String, Object>> selectBarChartData(Map<String, String> map) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list = dashboardMapper.selectBarChartData(map);
		
		return list;
	}
	
	/**
	 * @Method명 : selectLatelyLink
	 * @param Map
	 * @return List
	 * @throws Exception
	 * @작성자 : Hee Sung Yoon
	 * @작성일 : 2022. 9. 13.
	 * @Method설명 : 최근등록현황
	 */
	@Override
	public List<Map<String, Object>> selectLatelyLink(Map<String, String> map) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list = dashboardMapper.selectLatelyLink(map);
		
		return list;
	}
}


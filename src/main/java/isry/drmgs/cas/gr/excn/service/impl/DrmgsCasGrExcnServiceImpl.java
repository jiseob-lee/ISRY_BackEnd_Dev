/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.drmgs.cas.gr.excn.service.impl;

import isry.drmgs.cas.gr.excn.mapper.DrmgsCasGrExcnMapper;
import isry.drmgs.cas.gr.excn.service.DrmgsCasGrExcnService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.cleopatra.spring.JSONDataView;

import egovframework.com.cmm.service.EgovProperties;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * @파일명        : DrmgsCasGrExcnServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 7. 13. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 7. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("drmgsCasGrExcnService")
public class DrmgsCasGrExcnServiceImpl implements DrmgsCasGrExcnService {
	
	@Resource(name="drmgsCasGrExcnMapper")
	private DrmgsCasGrExcnMapper drmgsCasGrExcnMapper;
	
	@Override
	public List<Map<String, Object>> selectChkList(DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> searchParamMap = new HashMap<String, String>();
		searchParamMap.put("SRVC_PVSN_NO", searchParam.getValue("SRVC_PVSN_NO"));
		searchParamMap.put("RESRCE_NO", searchParam.getValue("RESRCE_NO"));
		return drmgsCasGrExcnMapper.selectChkList(searchParamMap);
	}
	
	@Override
	public void saveChkList(DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> delMap = new HashMap<String, String>();
		delMap.put("SRVC_PVSN_NO", searchParam.getValue("SRVC_PVSN_NO"));
		delMap.put("RESRCE_NO", searchParam.getValue("RESRCE_NO"));
		drmgsCasGrExcnMapper.delChkList(delMap);
		
		ParameterGroup searchParamList = dataRequest.getParameterGroup("dsServiceChek");
		Iterator<ParameterRow> insertRows = searchParamList.getAllRows();
		
    	while (insertRows.hasNext()) {
			Map<String, String> map = insertRows.next().toMap();
			drmgsCasGrExcnMapper.insChkList(map);
    	}
	}
	
	@Override
	public void saveDayList(DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> delMap = new HashMap<String, String>();
		delMap.put("SRVC_PVSN_NO", searchParam.getValue("SRVC_PVSN_NO"));
		delMap.put("RESRCE_NO", searchParam.getValue("RESRCE_NO"));
		drmgsCasGrExcnMapper.delDayList(delMap);
		drmgsCasGrExcnMapper.delChkList(delMap);
		
		Map<String, String> updateMap = new HashMap<String, String>();
		updateMap.put("SRVC_PVSN_NO", searchParam.getValue("SRVC_PVSN_NO"));
		updateMap.put("RESRCE_NO", searchParam.getValue("RESRCE_NO"));
		updateMap.put("SRVC_PVSN_WHDA_YN", searchParam.getValue("SRVC_PVSN_WHDA_YN"));
		drmgsCasGrExcnMapper.updatePvsnWhda(updateMap); // 서비스제공전일여부 update
		
		ParameterGroup searchParamList = dataRequest.getParameterGroup("dsDays");
		Iterator<ParameterRow> insertRows = searchParamList.getAllRows();
		
    	while (insertRows.hasNext()) {
			Map<String, String> map = insertRows.next().toMap();
			drmgsCasGrExcnMapper.insDayList(map);
    	}
	}
	
	@Override
	public List<Map<String, Object>> selectDayList(DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmParam");
		Map<String, String> searchParamMap = new HashMap<String, String>();
		searchParamMap.put("SRVC_PVSN_NO", searchParam.getValue("SRVC_PVSN_NO"));
		searchParamMap.put("RESRCE_NO", searchParam.getValue("RESRCE_NO"));
		return drmgsCasGrExcnMapper.selectDayList(searchParamMap);
	}
}

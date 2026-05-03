/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.workaltmntmng.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.util.StringUtil;

import isry.base.IsryBaseServiceImpl;
import isry.couns.mngr.workaltmntmng.mapper.EvdyDscsnClsMapper;
import isry.couns.mngr.workaltmntmng.mapper.MnthySchdlRegInfoMngMapper;
import isry.couns.mngr.workaltmntmng.service.EvdyDscsnClsService;


@Service
public class EvdyDscsnClsServiceImpl extends IsryBaseServiceImpl implements EvdyDscsnClsService {

	@Resource(name = "evdyDscsnClsMapper")
	private EvdyDscsnClsMapper evdyDscsnClsMapper;
	
	/**
	 * @Method명   : selectEvdyDscsnClsList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 9. 20. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectEvdyDscsnClsList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return evdyDscsnClsMapper.selectEvdyDscsnClsList(mapParam);
	}
	
	
	
}

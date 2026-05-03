/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.icbtgmng.service.impl;

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
import isry.couns.mngr.atrzmng.mapper.RcivEqptIndtyMngMapper;
import isry.couns.mngr.atrzmng.mapper.WorkChgMngMapper;
import isry.couns.mngr.atrzmng.service.RcivEqptIndtyMngService;
import isry.couns.mngr.atrzmng.service.WorkChgMngService;
import isry.couns.mngr.icbtgmng.mapper.IcbtgConsttChcMapper;
import isry.couns.mngr.icbtgmng.service.IcbtgConsttChcService;
import isry.couns.mngr.workaltmntmng.mapper.EvdyDscsnClsMapper;
import isry.couns.mngr.workaltmntmng.mapper.MnthySchdlRegInfoMngMapper;


@Service
public class IcbtgConsttChcServiceImpl extends IsryBaseServiceImpl implements IcbtgConsttChcService {

	@Resource(name = "icbtgConsttChcMapper")
	private IcbtgConsttChcMapper icbtgConsttChcMapper;
	
	/**
	 * @Method명   : selectIcbtgConsttChcList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 24.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectIcbtgConsttChcList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return icbtgConsttChcMapper.selectIcbtgConsttChcList(mapParam);
	}
	
/**
	 * @Method명   : insertIcbtgConsttChc
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 24. 
	 * @Method설명 :
	 */
	@Override
	public int insertIcbtgConsttChc(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return icbtgConsttChcMapper.insertIcbtgConsttChc(mapParam);
	}
	
}

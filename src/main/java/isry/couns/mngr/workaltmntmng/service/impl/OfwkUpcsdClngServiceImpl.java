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
import isry.couns.mngr.workaltmntmng.mapper.OfwkUpcsdClngMapper;
import isry.couns.mngr.workaltmntmng.service.OfwkUpcsdClngService;


@Service
public class OfwkUpcsdClngServiceImpl extends IsryBaseServiceImpl implements OfwkUpcsdClngService {

	@Resource(name = "ofwkUpcsdClngMapper")
	private OfwkUpcsdClngMapper ofwkUpcsdClngMapper;
	
	
	/**
	 * @Method명   : selectOfwkUpcsdClngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 30. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectOfwkUpcsdClngList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return ofwkUpcsdClngMapper.selectOfwkUpcsdClngList(mapParam);
	}
	
	/**
	 * @Method명   : searchComboBoxAprv
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 18. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> searchComboBoxAprv(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return ofwkUpcsdClngMapper.searchComboBoxAprv(mapParam);
	}
	
	/**
	 * @Method명   : selectOfwkUpcsdClngDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 30. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectOfwkUpcsdClngDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return ofwkUpcsdClngMapper.selectOfwkUpcsdClngDetail(mapParam);
	}
	
	/**
	 * @Method명   : insertOfwkUpcsdClngBatch
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 30.
	 * @수정자	   : Jeong.Won.Je
	 * @수정일	   : 2023.06.28 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> insertOfwkUpcsdClngBatch(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		System.out.println(ofwkUpcsdClngMapper.insertOfwkUpcsdClngBatch(mapParam));
		
		int resultVal = 1;
		
		paramMap.put("retVal", resultVal);
		
		return paramMap;
	}
	
	/**
	 * @Method명   : updateOfwkUpcsdClng
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 5. 30. 
	 * @Method설명 :
	 */
	@Override
	public int updateOfwkUpcsdClng(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return ofwkUpcsdClngMapper.updateOfwkUpcsdClng(mapParam);
	}
	
	
	
	
}

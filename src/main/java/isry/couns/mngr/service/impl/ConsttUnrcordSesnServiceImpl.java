/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.couns.mngr.mapper.ConsttUnrcordSesnMapper;
import isry.couns.mngr.service.ConsttUnrcordSesnService;

@Service("consttUnrcordSesnService")
public class ConsttUnrcordSesnServiceImpl extends IsryBaseServiceImpl implements ConsttUnrcordSesnService {

	@Resource(name = "consttUnrcordSesnMapper")
	private ConsttUnrcordSesnMapper mapper;
	
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
	public List<Map<String, Object>> selectCombo1List(Map<String, Object> mapParam) throws Exception {
		return mapper.selectCombo1List(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectUnrecordSesnList(Map<String, Object> mapParam) throws Exception {
		return mapper.selectUnrecordSesnList(mapParam);
	}

}

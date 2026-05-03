/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.uneartmng.cnterinfo.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.uneartmng.cnterinfo.mapper.CnterInfoMapper;
import isry.uneartmng.cnterinfo.service.CnterInfoService;



/**
 * @파일명        : GitpleEventMapper.java
 * @프로그램 설명 	: 깃플챗 이벤트를 저장한다.
 * @작성자        : Hee.Sung.Yoon
 * @작성일        : 2022. 5. 26. 
 * @수정자        : Hee.Sung.Yoon
 * @수정일        : 2022. 5. 26.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("cnterInfoService")
public class CnterInfoServiceImpl implements CnterInfoService {

	@Resource(name="cnterInfoMapper")
    public CnterInfoMapper cnterInfoMapper;
	
	@Override
	public List<Map<String, String>> selectCode(String cmmnsCdId) throws Exception {
		return cnterInfoMapper.selectCode(cmmnsCdId);
	}
	
	@Override
	public Map<String, String> selectCnterInfo(Map<String, String> map) throws Exception {
		return cnterInfoMapper.selectCnterInfo(map);
	}
	
}
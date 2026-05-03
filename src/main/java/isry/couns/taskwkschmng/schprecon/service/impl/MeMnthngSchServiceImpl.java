/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwkschmng.schprecon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.base.IsryBaseServiceImpl;
import isry.couns.taskwkschmng.schprecon.mapper.MeMnthngSchMapper;
import isry.couns.taskwkschmng.schprecon.mapper.MnthngSchdlMapper;
import isry.couns.taskwkschmng.schprecon.service.MeMnthngSchService;
import isry.couns.taskwkschmng.schprecon.service.MnthngSchdlService;


@Service("meMnthngSchService")
public class MeMnthngSchServiceImpl extends IsryBaseServiceImpl implements MeMnthngSchService {

	@Resource(name = "meMnthngSchMapper")
	private MeMnthngSchMapper mapper;
	
	/**
	 * @Method명   : selectMeMnthngSchList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 29. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectMeMnthngSchList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return mapper.selectMeMnthngSchList(mapParam);
	}

}

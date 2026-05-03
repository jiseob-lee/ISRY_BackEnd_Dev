/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcm.bizcmmns.cmmns.mapper.SrvcExcnBizMapper;
import isry.itgcm.bizcmmns.cmmns.service.SrvcExcnBizService;

/**
 * @파일명        : SrvcExcnBizServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Lee.Jun.Yeong
 * @작성일        : 2022. 5. 24. 
 * @수정자        : Lee.Jun.Yeong
 * @수정일        : 2022. 5. 24.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("srvcExcnBizService")
public class SrvcExcnBizServiceImpl implements SrvcExcnBizService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);	

	@Resource(name="srvcExcnBizMapper")
	private SrvcExcnBizMapper srvcExcnBizMapper;
	
	/**
	* @Method    : 서비스실행사업 목록조회
	* @param     : Map
	* @return    : list 
	* @exception : MyException
	* @see       : cmm.ROLE
	*/	
	@Override
	public List<Map<String, Object>> selectSrvcExcnBizList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		LOGGER.debug("selectSrvcExcnBizList.paramGroup=[" + paramGroup + "]");
		if (paramGroup == null) {
			throw new AppWorksException("사업연도를 입력 바랍니다.");
		}
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return srvcExcnBizMapper.selectSrvcExcnBizList(paramMap);
	}
	
}

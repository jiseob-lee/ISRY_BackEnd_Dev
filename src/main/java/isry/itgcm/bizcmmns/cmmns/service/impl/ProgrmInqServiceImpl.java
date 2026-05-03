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

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcm.bizcmmns.cmmns.mapper.ProgrmInqMapper;
import isry.itgcm.bizcmmns.cmmns.service.ProgrmInqService;

/**
 * @파일명        : ProgrmInqServiceImpl.java
 * @프로그램 설명 : 자원프로그램목록을 조회하는 팝업
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 6. 15. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 6. 15.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("progrmInqService")
public class ProgrmInqServiceImpl implements ProgrmInqService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	@Resource(name = "progrmInqMapper")
	private ProgrmInqMapper progrmInqMapper;

	/**
	 * @Method명   : selectProgrmInqList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 6. 15. 
	 * @Method설명 : 자원프로그램목록을 조회
	 */
	@Override
	public List<Map<String, String>> selectProgrmInqList(DataRequest dataRequest) throws Exception {
		
		Map<String, String> mapParam = new HashMap<String, String>();
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		
		mapParam = searchParam.getSingleValueMap();
		LOGGER.debug("searchParamsearchParam " + searchParam);
		
		for (String key : mapParam.keySet()) {
			LOGGER.debug("mapParammapParam" + key, mapParam.get(key));
			
		}
		return progrmInqMapper.selectProgrmInqList(mapParam);
	} 

}

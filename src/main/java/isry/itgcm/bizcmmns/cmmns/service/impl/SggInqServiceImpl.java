/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service.impl;

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
import isry.itgcm.bizcmmns.cmmns.mapper.SggInqMapper;
import isry.itgcm.bizcmmns.cmmns.service.SggInqService;

/**
 * @파일명        : SsgInqServiceImpl.java
 * @프로그램 설명 : 시군구를 조회하는 팝업
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 5. 25. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 5. 25.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("ssgInqService")
public class SggInqServiceImpl implements SggInqService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "sggInqMapper")
	private SggInqMapper sggInqMapper;

	/**
	 * @Method명   : selectSsgInqList
	 * @param datarequest
	 * @return List
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 5. 25. 
	 * @Method설명 : 시군구 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectSggInqList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup 	 = dataRequest.getParameterGroup("dmSearch");	
		LOGGER.debug("selectSggInqList=[" + paramGroup + "]");
		Map<String, String> mapParam = paramGroup.getSingleValueMap();
		
		return sggInqMapper.selectSggInqList(mapParam);
	}

	/**
	 * @Method명   : selectSggCodeList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 22. 
	 * @Method설명 : 시도, 시군구 코드 조회
	 */
	@Override
	public List<Map<String, Object>> selectSggCodeList(Map<String, String> paramMap) throws Exception {
		
		if (paramMap == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		LOGGER.debug("selectSggCodeList.paramMap=[" + paramMap + "]");
		
		String sCmmnsCdId = "";
		
		// 공통코드아이디
		sCmmnsCdId = paramMap.get("CMMNS_CD_ID");  
		if (sCmmnsCdId==null || sCmmnsCdId.equals("null") || sCmmnsCdId.equals("")) {
			sCmmnsCdId = "X";
		}
		return sggInqMapper.selectSggCodeList(paramMap);
	}

}

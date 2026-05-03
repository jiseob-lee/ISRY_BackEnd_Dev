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
import isry.itgcm.bizcmmns.cmmns.mapper.EmdInqMapper;
import isry.itgcm.bizcmmns.cmmns.service.EmdInqService;

/**
 * @파일명        : EmdInqServiceImpl.java
 * @프로그램 설명 : 시군구,읍면동 조회팝업
 * - 
 * - 
 * @작성자        : Kwon.Min.Seo
 * @작성일        : 2022. 10. 14. 
 * @수정자        : Kwon.Min.Seo
 * @수정일        : 2022. 10. 14.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("emdInqService")
public class EmdInqServiceImpl implements EmdInqService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "emdInqMapper")
	private EmdInqMapper emdInqMapper;
	
	/**
	 * @Method명   : selectEmdInqList
	 * @param datarequest
	 * @return List
	 * @throws Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 10. 14. 
	 * @Method설명 : 시군구,읍면동 조회
	 */
	@Override
	public List<Map<String, Object>> selectEmdInqList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup 	 = dataRequest.getParameterGroup("dmSearch");	
		LOGGER.debug("selectEmdInqList=[" + paramGroup + "]");
		Map<String, String> mapParam = paramGroup.getSingleValueMap();
		
		return emdInqMapper.selectEmdInqList(mapParam);
	}
	
	/**
	 * @Method명   : selectEmdCodeList
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 10. 14. 
	 * @Method설명 : 읍면동, 시군구 코드 조회
	 */
	@Override
	public List<Map<String, Object>> selectEmdCodeList(Map<String, String> paramMap) throws Exception {
		
		if (paramMap == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("selectSggCodeList.paramMap=[" + paramMap + "]");
		
		String sCmmnsCdId = "";
		
		// 공통코드아이디
		sCmmnsCdId = paramMap.get("CMMNS_CD_ID");  
		if (sCmmnsCdId==null || sCmmnsCdId.equals("null") || sCmmnsCdId.equals("")) {
			sCmmnsCdId = "X";
		}
		
		if("SGG_CD".equals(sCmmnsCdId)) {
			return emdInqMapper.selectSggCodeList(paramMap);
		}else if("STDG_CD".equals(sCmmnsCdId)) {
			return emdInqMapper.selectStdgCodeList(paramMap);
		}else {
			return emdInqMapper.selectEmdCodeList(paramMap);
		}		
	}
	
	/**
	 * @Method명   : selectSggCtpvCodeList
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 27. 
	 * @Method설명 : 시도코드 조회
	 */
	@Override
	public List<Map<String, Object>> selectSggCtpvCodeList() throws Exception {
		
		Map<String, String> paramMap = null;
		
		return emdInqMapper.selectSggCtpvCodeList(paramMap);
	}	

	/**
	 * @Method명   : selectSsgCodeList
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 3. 
	 * @Method설명 : 시군구 코드조회
	 */
	@Override
	public List<Map<String, Object>> selectSsgCodeList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		LOGGER.debug("selectSsgCodeList.paramGroup=[" + paramGroup + "]");				
				
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return emdInqMapper.selectSggCodeList(paramMap);
	}

	/**
	 * @Method명   : selectDongInqList
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 4. 3. 
	 * @Method설명 : 법정읍면동코드 조회(시도명, 시군구명)
	 */
	@Override
	public List<Map<String, Object>> selectDongInqList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup 	 = dataRequest.getParameterGroup("dmSearch");	
		
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("selectDongInqList.paramGroup=[" + paramGroup + "]");		
		
		Map<String, String> mapParam = paramGroup.getSingleValueMap();
		
		return emdInqMapper.selectDongInqList(mapParam);
	}


}

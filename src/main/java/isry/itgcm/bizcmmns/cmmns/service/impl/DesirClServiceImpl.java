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
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.DesirClMapper;
import isry.itgcm.bizcmmns.cmmns.service.DesirClService;

/**
 * @파일명        : DesirClServiceImpl.java
 * @프로그램 설명 :
 * - 
 * -  
 * @작성자        : Kwon.Min.Seo
 * @작성일        : 2022. 10. 13. 
 * @수정자        : Kwon.Min.Seo
 * @수정일        : 2022. 10. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("desirClService")
public class DesirClServiceImpl extends EgovAbstractServiceImpl implements DesirClService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="desirClMapper")
    private DesirClMapper desirClMapper;
	
	/**
	 * @Method     : selectDesirClList
	 * @Method설명 : 복지부 욕구분류 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 10. 13.
 	 */	
	@Override
	public List<Map<String, Object>> selectDesirClList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("selectDesirClList.paramGroup=[" + paramGroup + "]");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		//Map<String, String> paramMap = new HashMap<>();
		
		String sLclasSeCd = "";
		String sMclasSeCd = "";
		
		// 복지부욕구대분류구분코드
		sLclasSeCd = paramGroup.getValue("MOHW_DESIR_LCLAS_SE_CD");  
		if (sLclasSeCd==null || sLclasSeCd.equals("null") || sLclasSeCd.equals("")) {
			sLclasSeCd = "";
			//paramMap.put("MOHW_DESIR_LCLAS_SE_CD", sLclasSeCd);
		}
		
		// 복지부욕구중분류구분코드
		sMclasSeCd = paramGroup.getValue("MOHW_DESIR_MLSFC_SE_CD");  
		if (sMclasSeCd==null || sMclasSeCd.equals("null") || sMclasSeCd.equals("")) {
			sMclasSeCd = "";
			//paramMap.put("MOHW_DESIR_MLSFC_SE_CD", sMclasSeCd);
		}
		
		LOGGER.debug("selectDesirClList.sLclasSeCd=[" + sLclasSeCd + "]");
		LOGGER.debug("selectDesirClList.sMclasSeCd=[" + sMclasSeCd + "]");
		
		return desirClMapper.selectDesirClList(paramMap);
	}
	
}
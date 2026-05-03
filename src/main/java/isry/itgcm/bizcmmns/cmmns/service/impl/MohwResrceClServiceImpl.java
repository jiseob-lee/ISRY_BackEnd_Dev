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
import isry.base.IsryBaseServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.MohwResrceClMapper;
import isry.itgcm.bizcmmns.cmmns.service.MohwResrceClService;

/**
 * @파일명        : MohwResrceClServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Kang.Hwa.Young
 * @작성일        : 2022. 10. 7. 
 * @수정자        : Kang.Hwa.Young
 * @수정일        : 2022. 10. 7.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("mohwResrceClService")
public class MohwResrceClServiceImpl extends IsryBaseServiceImpl implements MohwResrceClService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "mohwResrceClMapper")
	private MohwResrceClMapper mohwResrceClMapper;
	
//	@Override
//	public List<Map<String, Object>> selectMohwResrceClList(DataRequest dataRequest) throws Exception{
//		return null;
//	}
	/**
	 * @Method     : selectMohwResrceClList
	 * @Method설명 : 복지부 자원분류 목록조회
	 * @param      : dataRequest
	 * @return     : ListMap 
	 * @exception  : Exception
	 * @작성자     : Kwon.Min.Seo
	 * @작성일     : 2022. 10. 25. 
 	 */	
	@Override
	public List<Map<String, Object>> selectMohwResrceClList(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없읍니다.", Alert.ERROR);
		}
		LOGGER.debug("selectSrvcResrceClList.paramGroup=[" + paramGroup + "]");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		//Map<String, String> paramMap = new HashMap<>();
		
		String sLclasSeCd = "";
		String sMclasSeCd = "";
		String sSclasSeCd = "";
		
		
		// 복지부자원대분류구분코드
		sLclasSeCd = paramGroup.getValue("MOHW_RESRCE_LCLAS_SE_CD");  
		if (sLclasSeCd==null || sLclasSeCd.equals("null") || sLclasSeCd.equals("")) {
			sLclasSeCd = "";
			//paramMap.put("MOHW_RESRCE_LCLAS_SE_CD", sLclasSeCd);
		}
		
		// 복지부자원중분류구분코드
		sMclasSeCd = paramGroup.getValue("MOHW_RESRCE_MLSFC_SE_CD");  
		if (sMclasSeCd==null || sMclasSeCd.equals("null") || sMclasSeCd.equals("")) {
			sMclasSeCd = "";
			//paramMap.put("MOHW_RESRCE_MLSFC_SE_CD", sMclasSeCd);
		}
		
		// 복지부자원소분류구분코드
		sSclasSeCd = paramGroup.getValue("MOHW_RESRCE_SCLAS_SE_CD");
		if (sSclasSeCd==null || sSclasSeCd.equals("null") || sSclasSeCd.equals("")) {
			sSclasSeCd = "";
			//paramMap.put("MOHW_RESRCE_SCLAS_SE_CD", sSclasSeCd);
		}
		
		LOGGER.debug("selectSrvcResrceClList.sLclasSeCd=[" + sLclasSeCd + "]");
		LOGGER.debug("selectSrvcResrceClList.sMclasSeCd=[" + sMclasSeCd + "]");
		LOGGER.debug("selectSrvcResrceClList.sSclasSeCd=[" + sSclasSeCd + "]");
		
		return mohwResrceClMapper.selectMohwResrceClList(paramMap);
	}
}

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
import isry.itgcm.bizcmmns.cmmns.mapper.ProbmSttsInqMapper;
import isry.itgcm.bizcmmns.cmmns.service.ProbmSttsInqService;

/**
 * @파일명        : ProbmSttsInqServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자     	: Kwon.Min.Seo
 * @작성일     	: 2022. 07. 29. 
 * @수정자     	: Kwon.Min.Seo
 * @수정일     	: 2022. 07. 29. 
 * @수정내용		:
 * -
 * -
 */
@Service("probmSttsInqService")
public class ProbmSttsInqServiceImpl extends EgovAbstractServiceImpl implements ProbmSttsInqService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name ="probmSttsInqMapper")
	private ProbmSttsInqMapper probmSttsInqMapper;
	
	/**
	 * @Method     	: selectProbmSttsInqList
	 * @Method설명 	: 문제상태 및 원인 목록조회
	 * @param      	: dataRequest
	 * @return     	: ListMap 
	 * @exception  	: Exception
	 * @작성자   	: Kwon.Min.Seo
	 * @작성일     	: 2022. 07. 29. 
 	 */	
	@Override
	public List<Map<String, Object>> selectProbmSttsInqList(DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 자료가 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("selectProbmSttsInqList.paramGroup=[" + paramGroup + "]");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		//Map<String, String> paramMap = new HashMap<>();
		
		// 문제상태대분류구분코드 설정
		String sProbmSttsLclasSeCd = "";
		// 문제상태중분류구분코드 설정
		String sProbmSttsMlsfcSeCd = "";
		// 문제상태소분류구분코드 설정
		String sProbmSttsSclasSeCd = "";
		// 문제원인대분류구분코드 설정
		String sProbmCasLclasSeCd  = "";
		// 문제원인소분류구분코드 설정
		String sProbmCasSclasSeCd  = "";
		
		// 문제상태대분류구분코드
		sProbmSttsLclasSeCd = paramGroup.getValue("PROBM_STTS_LCLAS_SE_CD");  
		if (sProbmSttsLclasSeCd == null || sProbmSttsLclasSeCd.equals("null") || sProbmSttsLclasSeCd.equals("")) {
			sProbmSttsLclasSeCd = "";
			//paramMap.put("SRVC_RESRCE_LCLAS_SE_CD", sLclasSeCd);
		}
		
		// 문제상태중분류구분코드
		sProbmSttsMlsfcSeCd = paramGroup.getValue("PROBM_STTS_MLSFC_SE_CD");  
		if (sProbmSttsMlsfcSeCd == null || sProbmSttsMlsfcSeCd.equals("null") || sProbmSttsMlsfcSeCd.equals("")) {
			sProbmSttsMlsfcSeCd = "";
			//paramMap.put("SRVC_RESRCE_MLSFC_SE_CD", sMclasSeCd);
		}
		
		// 문제상태소분류구분코드
		sProbmSttsSclasSeCd = paramGroup.getValue("PROBM_STTS_SCLAS_SE_CD");
		if (sProbmSttsSclasSeCd == null || sProbmSttsSclasSeCd.equals("null") || sProbmSttsSclasSeCd.equals("")) {
			sProbmSttsSclasSeCd = "";
			//paramMap.put("SRVC_RESRCE_SCLAS_SE_CD", sSclasSeCd);
		}
		
		// 문제원인대분류구분코드
		sProbmCasLclasSeCd = paramGroup.getValue("PROBM_CAS_LCLAS_SE_CD");
		if (sProbmCasLclasSeCd == null || sProbmCasLclasSeCd.equals("null") || sProbmCasLclasSeCd.equals("")) {
			sProbmCasLclasSeCd = "";
			//paramMap.put("SRVC_RESRCE_DTL_SE_CD",   sDtlSeCd);
		}
		
		// 문제원인소분류구분코드
		sProbmCasSclasSeCd = paramGroup.getValue("PROBM_CAS_SCLAS_SE_CD");
		if (sProbmCasSclasSeCd == null || sProbmCasSclasSeCd.equals("null") || sProbmCasSclasSeCd.equals("")) {
			sProbmCasSclasSeCd = "";
			//paramMap.put("SRVC_RESRCE_DTL_SE_CD",   sDtlSeCd);
		}

		return probmSttsInqMapper.selectProbmSttsInqList(paramMap);
	}

}

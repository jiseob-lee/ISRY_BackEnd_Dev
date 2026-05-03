/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.dscsnstats.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.couns.stats.dscsnstats.mapper.WebTrlInspStatsMapper;
import isry.couns.stats.dscsnstats.service.WebTrlInspStatsService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;

/**
 * @파일명        : WebTrlInspStatsServiceImpl.java
 * @프로그램 설명 : 웹심리검사 통계 ServiceImpl Class
 * - 
 * - 
 * @작성자        : Jeong.Won.Je
 * @작성일        : 2023. 2. 10. 
 * @수정자        : Jeong.Won.Je
 * @수정일        : 2023. 2. 10.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("webTrlInspStatsService")
public class WebTrlInspStatsServiceImpl extends IsryBaseServiceImpl implements WebTrlInspStatsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "webTrlInspStatsMapper")
	private WebTrlInspStatsMapper webTrlInspStatsMapper;
	
	@Resource(name = "userLoginService")
	private UserLoginService userLoginService;

	/**
	 * @Method명   : selectWebTrlInspKndList
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 15. 
	 * @Method설명 : 웹심리검사 통계_검사 종류 List 조회(검사종류/검사구분/실시건수/댓글건수)
	 */
	@Override
	public List<Map<String, Object>> selectWebTrlInspKndList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("dmSearch ::: " + paramMap);
		
		List<Map<String, Object>> webTrlInspKndList = new ArrayList<Map<String,Object>>();
		
		try {
			webTrlInspKndList = webTrlInspStatsMapper.selectWebTrlInspKndList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return webTrlInspKndList;
	}

	/**
	 * @Method명   : selectWebTrlInspKndDetail
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 20. 
	 * @Method설명 : 선택한 웹심리검사에 대한 검사결과현황과 검사결과에 대한 내역 조회
	 */
	@Override
	public void selectWebTrlInspKndDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String trlInspKndNo = "";			// 선택한 심리검사종류 번호
		List<Map<String, Object>> preconList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("dmSearch ::: " + paramMap);
		
		trlInspKndNo = paramMap.get("WEPOS_MLSFC_SN");
		
		LOGGER.debug("trlInspKndNo ::: " + trlInspKndNo);
		
		if ("11".equals(trlInspKndNo)) {
			paramMap.put("WEPOS_SCLAS_SN", "1");
			
			preconList = webTrlInspStatsMapper.selectTwdpsnRelPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectTwdpsnRelResultList(paramMap);
		} else if ("12".equals(trlInspKndNo)) {
			paramMap.put("WEPOS_SCLAS_SN", "2");
			
			preconList = webTrlInspStatsMapper.selectTwdpsnRelPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectTwdpsnRelResultList(paramMap);
		} else if ("16".equals(trlInspKndNo)) {
			paramMap.put("TRL_INSP_MNG_NO", "UR");
			
			preconList = webTrlInspStatsMapper.selectCoupleAngerAspectCrtronPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectCoupleAngerAspectCrtronResultList(paramMap);
		} else if ("21".equals(trlInspKndNo)) {
			paramMap.put("TRL_INSP_MNG_NO", "01");
			
			preconList = webTrlInspStatsMapper.selectCoseSchulwPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectCoseSchulwResultList(paramMap);
		} else if ("22".equals(trlInspKndNo)) {
			paramMap.put("TRL_INSP_MNG_NO", "02");
			
			preconList = webTrlInspStatsMapper.selectCoseSchulwPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectCoseSchulwResultList(paramMap);
		} else if ("23".equals(trlInspKndNo)) {
			paramMap.put("TRL_INSP_MNG_NO", "03");
			
			preconList = webTrlInspStatsMapper.selectCoseSchulwPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectCoseSchulwResultList(paramMap);
		} else if ("24".equals(trlInspKndNo)) {
			paramMap.put("TRL_INSP_MNG_NO", "04");
			
			preconList = webTrlInspStatsMapper.selectCoseSchulwPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectCoseSchulwResultList(paramMap);
		} else if ("25".equals(trlInspKndNo)) {
			
			preconList = webTrlInspStatsMapper.selectCoseDirectivityPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectCoseDirectivityResultList(paramMap);
		} else if ("26".equals(trlInspKndNo)) {
			
		} else if ("31".equals(trlInspKndNo)) {
			paramMap.put("PATTERN_TITLE", "청소년 인터넷중독 자가진단");
			paramMap.put("TRL_INSP_MNG_NO", "KK");
			
			preconList = webTrlInspStatsMapper.selectAddictionStressPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectAddictionStressResultList(paramMap);
		} else if ("32".equals(trlInspKndNo)) {
			paramMap.put("PATTERN_TITLE", "청소년 인터넷중독 관찰자");
			paramMap.put("TRL_INSP_MNG_NO", "KP");
			
			preconList = webTrlInspStatsMapper.selectAddictionStressPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectAddictionStressResultList(paramMap);
		} else if ("33".equals(trlInspKndNo)) {
			paramMap.put("PATTERN_TITLE", "유아동 인터넷중독 관찰자");
			paramMap.put("TRL_INSP_MNG_NO", "KC");
			
			preconList = webTrlInspStatsMapper.selectAddictionStressPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectAddictionStressResultList(paramMap);
		} else if ("34".equals(trlInspKndNo)) {
			paramMap.put("PATTERN_TITLE", "청소년 스마트폰중독 자가진단");
			paramMap.put("TRL_INSP_MNG_NO", "SS");
			
			preconList = webTrlInspStatsMapper.selectAddictionStressPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectAddictionStressResultList(paramMap);
		} else if ("35".equals(trlInspKndNo)) {
			paramMap.put("PATTERN_TITLE", "유아동 스마트폰중독 관찰자");
			paramMap.put("TRL_INSP_MNG_NO", "SC");
			
			preconList = webTrlInspStatsMapper.selectAddictionStressPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectAddictionStressResultList(paramMap);
		} else if ("36".equals(trlInspKndNo)) {
			paramMap.put("PATTERN_TITLE", "청소년 도박문제");
			
			preconList = webTrlInspStatsMapper.selectAddictionStressPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectAddictionStressResultList(paramMap);
		} else if ("41".equals(trlInspKndNo)) {
			paramMap.put("TRL_INSP_MNG_NO", "05");
			
			preconList = webTrlInspStatsMapper.selectGnrlCharctPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectGnrlCharctResultList(paramMap);
		} else if ("410".equals(trlInspKndNo)) {
			
		} else if ("43".equals(trlInspKndNo)) {
			
			preconList = webTrlInspStatsMapper.selectEmtStablePreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectEmtStableResultList(paramMap);
		} else if ("45".equals(trlInspKndNo)) {
			paramMap.put("PATTERN_TITLE", "스트레스 경험");
			
			preconList = webTrlInspStatsMapper.selectAddictionStressPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectAddictionStressResultList(paramMap);
		} else if ("46".equals(trlInspKndNo)) {
			paramMap.put("TRL_INSP_MNG_NO", "ANGR");
			
			preconList = webTrlInspStatsMapper.selectCoupleAngerAspectCrtronPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectCoupleAngerAspectCrtronResultList(paramMap);
		} else if ("47".equals(trlInspKndNo)) {
			paramMap.put("TRL_INSP_MNG_NO", "LIFE");
			
			preconList = webTrlInspStatsMapper.selectCoupleAngerAspectCrtronPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectCoupleAngerAspectCrtronResultList(paramMap);
		} else if ("48".equals(trlInspKndNo)) {
			paramMap.put("PATTERN_TITLE", "사회 불안 검사");
			
			preconList = webTrlInspStatsMapper.selectSoctyAnxietParntsADHDDepresPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectSoctyAnxietParntsADHDDepresResultList(paramMap);
		} else if ("51".equals(trlInspKndNo)) {
			paramMap.put("TRL_INSP_MNG_NO", "MOM");
			
			preconList = webTrlInspStatsMapper.selectMomNurtureEfficaPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectMomNurtureEfficaResultList(paramMap);
		} else if ("52".equals(trlInspKndNo)) {
			paramMap.put("PATTERN_TITLE", "부모용 ADHD 검사");
			
			preconList = webTrlInspStatsMapper.selectSoctyAnxietParntsADHDDepresPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectSoctyAnxietParntsADHDDepresResultList(paramMap);
		} else if ("53".equals(trlInspKndNo)) {
			paramMap.put("PATTERN_TITLE", "우울증(CES-D)");
			
			preconList = webTrlInspStatsMapper.selectSoctyAnxietParntsADHDDepresPreconList(paramMap);
			resultList = webTrlInspStatsMapper.selectSoctyAnxietParntsADHDDepresResultList(paramMap);
		} else {
			
		}
		
		dataRequest.setResponse("dsWebTrlInspPreconList", preconList);
		dataRequest.setResponse("dsWebTrlInspResultList", resultList);
		
	}

	/**
	 * @Method명   : selectWebTrlInspProbmSttsList
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 10. 
	 * @Method설명 : 웹심리검사 문제상태 통계
	 */
	@Override
	public List<Map<String, Object>> selectWebTrlInspProbmSttsList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("dmSearch ::: " + paramMap);
		
		List<Map<String, Object>> webTrlInspStatsList = new ArrayList<Map<String,Object>>();
		
		try {
			webTrlInspStatsList = webTrlInspStatsMapper.selectWebTrlInspProbmSttsList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return webTrlInspStatsList;
	}

	/**
	 * @Method명   : selectWebTrlInspDgstfnKndList
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 웹심리검사 만족도 통계_검사 종류 List 조회(검사종류/응답건수)
	 */
	@Override
	public List<Map<String, Object>> selectWebTrlInspDgstfnKndList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("dmSearch ::: " + paramMap);
		
		List<Map<String, Object>> webTrlInspDgstfnKndList = new ArrayList<Map<String,Object>>();
		
		try {
			webTrlInspDgstfnKndList = webTrlInspStatsMapper.selectWebTrlInspDgstfnKndList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return webTrlInspDgstfnKndList;
	}

	/**
	 * @Method명   : selectWebTrlInspDgstfnKndDetail
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 13. 
	 * @Method설명 : 웹심리검사 만족도 통계_선택한 검사에 대한 만족도 조사 결과 조회
	 */
	@Override
	public List<Map<String, Object>> selectWebTrlInspDgstfnKndDetail(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("dmSearch ::: " + paramMap);
		
		List<Map<String, Object>> webTrlInspDgstfnKndDetail = new ArrayList<Map<String,Object>>();
		
		try {
			webTrlInspDgstfnKndDetail = webTrlInspStatsMapper.selectWebTrlInspDgstfnKndDetail(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return webTrlInspDgstfnKndDetail;
	}

}

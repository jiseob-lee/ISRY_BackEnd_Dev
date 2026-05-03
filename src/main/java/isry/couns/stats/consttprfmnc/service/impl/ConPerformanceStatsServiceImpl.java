/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.stats.consttprfmnc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.couns.stats.consttprfmnc.mapper.ConPerformanceStatsMapper;
import isry.couns.stats.consttprfmnc.service.ConPerformanceStatsService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;


 
/**
 * @param <selectHopeDownloadStats>
 * @파일명 : SurvshtMmnServiceImpl.java
 * @프로그램 설명 : 설문지 작성을 관리하는 ServiceImpl
 * @작성자 : kim.seong.gyu
 * @작성일 : 2022. 5. 04
 * @수정자 : 
 * @수정일 : 
 * @수정내용 : - -
 */
@Service("conPerformanceStatsService")
public class ConPerformanceStatsServiceImpl extends IsryBaseServiceImpl implements ConPerformanceStatsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "conPerformanceStatsMapper")
	private ConPerformanceStatsMapper conPerformanceStatsMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectconPerformanceStats(Map<String, Object> mapParam, HttpServletRequest request) throws Exception {
		
		String sEnfsnRoleSeCd = ""; // 종사자의 역할구분코드		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sEnfsnRoleSeCd = loginVO.getEnfsnRoleSeCd();
		} 
//		System.out.println("종사자의 역할구분코드 조회 0000 :::::::::::::: "+ sEnfsnRoleSeCd);
//		System.out.println("mapParam 0000 :::::::::::::: "+ mapParam.toString());
		
		if("3".equals(sEnfsnRoleSeCd)) { // 3:종사자
			return conPerformanceStatsMapper.selectconPerformanceStats1(mapParam);
		}else {
			return conPerformanceStatsMapper.selectconPerformanceStats(mapParam);
		}		
		
	}

	/**
	 * @Method명   : selectCnsltntPerformanceStatsList
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 2. 28. 
	 * @Method설명 : 부서코드 값에 따른 상담자별 실적 조회
	 */
	@Override
	public void selectCnsltntPerformanceStatsList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		String selectedCmbVal = "";				// 선택된 부서코드 값
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		LOGGER.debug("dmSearch ::: " + paramMap);
		
		List<Map<String, Object>> dsCnsltntPerformanceStatsList = new ArrayList<Map<String,Object>>();
		
		try {
			dsCnsltntPerformanceStatsList = conPerformanceStatsMapper.selectCnsltntPerformanceStatsList(paramMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LOGGER.debug("dsCnsltntPerformanceStatsList ::: " + dsCnsltntPerformanceStatsList.size());
		
		dataRequest.setResponse("dsCnsltntPerformanceStatsList", dsCnsltntPerformanceStatsList);
		
	}

}

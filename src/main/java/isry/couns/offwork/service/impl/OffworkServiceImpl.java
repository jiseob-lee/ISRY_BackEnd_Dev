/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.offwork.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.couns.offwork.mapper.OffworkMapper;
import isry.couns.offwork.service.OffworkService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : OffworkServiceImpl.java
 * @프로그램 설명 : 퇴근처리
 * - 
 * - 
 * @작성자        : Lee.Tae.Ho
 * @작성일        : 2022. 10. 05. 
 * @수정자        : Lee.Tae.Ho
 * @수정일        : 2022. 10. 04. 
 * @수정내용      : 
 * -                
 * -                
 */
@Service("offworkService")
public class OffworkServiceImpl extends IsryBaseServiceImpl implements OffworkService {	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name="offworkMapper")
	private OffworkMapper offworkMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명	 : selectLvffcPrcsBassInfo
	 * @param	 : dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 	 : Lee.Tae.Ho
	 * @작성일  	 : 2022. 10. 05. 
	 * @Method설명 : 퇴근처리 기본정보 조회
	 * @사용안함 : 2023.07.26
	 */
	@Override
	public Map<String,Object> selectLvffcPrcsBassInfo(HttpServletRequest request,DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		Map<String, Object> rtn = new HashMap<>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		dmOutcomeDetailMap.put("CONSTT_ID", sUserId);
		
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		rtn = offworkMapper.selectLvffcPrcsBassInfo(dmOutcomeDetailMap);
		
		return rtn;
	}
	
	/**
	 * @Method명   : lvffcPrcsSave
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Tae.Ho
	 * @작성일     : 2022. 10. 05. 
	 * @Method설명 : 퇴근처리 저장
	 */
	@Override
	public Map<String, String> lvffcPrcsSave(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String sUserId      = ""; // 세션정보의 유저ID
		String sUserIp      = ""; // 세션정보의 유저IP
		
		// 세션정보 가져오기
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			sUserId = loginVO.getId();
			sUserIp = loginVO.getIp();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmDetail");
		Map<String, String> dmOutcomeDetailMap = searchParam.getSingleValueMap();
		
		dmOutcomeDetailMap.put("LVFFC_IP_ADDR", sUserIp);
		dmOutcomeDetailMap.put("FRST_RGTR_ID", sUserId);
		dmOutcomeDetailMap.put("LAST_MDFR_ID", sUserId);
		
		LOGGER.debug("dmOutcomeDetailMap 111::::::::::::" + dmOutcomeDetailMap.toString());
		
		offworkMapper.UpdateLvffcPrcs(dmOutcomeDetailMap); // 상담원출퇴근관리(AYC495)
				
		return null;
	}
	
}




/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwkschmng.schprecon.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.base.IsryBaseServiceImpl;
import isry.couns.taskwkschmng.schprecon.mapper.workChgAplylMapper;
import isry.couns.taskwkschmng.schprecon.service.workChgAplyService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : SurvshtMmnServiceImpl.java
 * @프로그램 설명 : 설문지 작성을 관리하는 ServiceImpl
 * @작성자 : kim.seong.gyu
 * @작성일 : 2022. 5. 04
 * @수정자 : 
 * @수정일 : 
 * @수정내용 : - -
 */
@Service("workChgAplyService")
public class workChgAplyServiceImpl extends IsryBaseServiceImpl implements workChgAplyService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name = "userLoginService")
	UserLoginService userLoginService;
	
	@Resource(name = "workChgAplyMapper")
	private workChgAplylMapper workChgAplyMapper;

	/**
	 * @Method명   : selectWorkListFrom
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 5. 31. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectWorkListFrom(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return workChgAplyMapper.selectWorkListFrom(mapParam);
	}

	/**
	 * @Method명   : selectWorkListTo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 5. 31. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectWorkListTo(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return workChgAplyMapper.selectWorkListTo(mapParam);
	}

	/**
	 * @Method명   : insertWorkChgAply
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 3. 16. 
	 * @Method설명 :
	 */
	@Override
	public void insertWorkChgAply(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String loginId = "";			// session ID
		int notAprvCnt = 0;				// 승인불가 count
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		} else {
			throw new AppWorksException("session정보가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSave");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		paramMap.put("loginId", loginId);
		paramMap.put("APRV_PSBLTY_YN", "Y");
		
		List<Map<String, Object>> chkResultList = new ArrayList<Map<String,Object>>();
		
		try {
			chkResultList = workChgAplyMapper.selectWorkChgAplyChkList(paramMap);
			LOGGER.debug("체크 결과 ::: " + chkResultList);
			
			for (Map<String, Object> map : chkResultList) {
				String chkResultTitle = map.get("RET_TITLE").toString();
				int chkResultVal = Integer.parseInt(map.get("RET_VAL").toString());
				
				if (chkResultVal >= 6) {
					LOGGER.debug("성공한 결과값 ::: " + chkResultVal);
				} else {
					LOGGER.debug("실패한 결과값 ::: " + chkResultVal);
					notAprvCnt++;
				}
			}
			
			if (notAprvCnt > 0) {
				paramMap.replace("APRV_PSBLTY_YN", "N");
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			workChgAplyMapper.insertWorkChgAply(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @Method명   : selectWorkChgAplyList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 5. 31. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectWorkChgAplyList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return workChgAplyMapper.selectWorkChgAplyList(mapParam);
	}

	/**
	 * @Method명   : deleteWorkChgAply
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Park Jongman
	 * @작성일     : 2022. 5. 31. 
	 * @Method설명 :
	 */
	@Override
	public int deleteWorkChgAplyDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return workChgAplyMapper.deleteWorkChgAplyDetail(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectWorkChgAplyDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return workChgAplyMapper.selectWorkChgAplyDetail(mapParam);
	}

}

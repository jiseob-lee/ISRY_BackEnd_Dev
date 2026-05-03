/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.stats.operprfmnc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.subms.stats.operprfmnc.mapper.OperPrfmncMapper;
import isry.subms.stats.operprfmnc.service.OperPrfmncService;

/**
 * @파일명 : OperPrfmncServiceImpl.java
 * @프로그램 설명 : 운영실적 서비스 임플리먼트 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 5. 16.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 5. 16.
 * @수정내용 : - -
 */
@Service("operPrfmncService")
public class OperPrfmncServiceImpl extends IsryBaseServiceImpl implements OperPrfmncService {

	@Resource(name = "operPrfmncMapper")
	OperPrfmncMapper operPrfmncMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectNmprPreconList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 16.
	 * @Method설명 : 인원현황 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectNmprPreconList(HttpServletRequest request, Map<String, Object> map) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		String unt_taskwk = loginVO.getUntTaskwk();
		map.put("UNT_TASKWK_SE_CD", unt_taskwk);
		
		return operPrfmncMapper.selectNmprPreconList(map);
	}

	/**
	 * @Method명 : selectNmprAchivRateList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 17.
	 * @Method설명 : 누적인원 및 달성률 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectNmprAchivRateList(HttpServletRequest request, Map<String, Object> map) throws Exception {
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		String unt_taskwk = loginVO.getUntTaskwk();
		map.put("UNT_TASKWK_SE_CD", unt_taskwk);
		
		return operPrfmncMapper.selectNmprAchivRateList(map);
	}

	/**
	 * @Method명 : selectPrtpntTrgtPreconList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 18.
	 * @Method설명 : 참여자대상별현황 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectPrtpntTrgtPreconList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return operPrfmncMapper.selectPrtpntTrgtPreconList(map);
	}

	/**
	 * @Method명 : selectPrtpntTrprLinkPreconList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 18.
	 * @Method설명 : 참자여대상자 연계 현황 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectPrtpntTrprLinkPreconList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return operPrfmncMapper.selectPrtpntTrprLinkPreconList(map);
	}

	/**
	 * @Method명 : selectEareEduPrgrsPreconList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 18.
	 * @Method설명 : 영역별 교육진행현황 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectEareEduPrgrsPreconList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return operPrfmncMapper.selectEareEduPrgrsPreconList(map);
	}

	/**
	 * @Method명 : selectProgrmEduPrgrsPreconList
	 * @param map
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 18.
	 * @Method설명 : 프로그램별 교육진행현황 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectProgrmEduPrgrsPreconList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return operPrfmncMapper.selectProgrmEduPrgrsPreconList(map);
	}

	/**
	 * @Method명   : selectSemstrCombo
	 * @param request
	 * @return
	 * @throws Exception
	 * @작성자     : Lee.Hye.Sun
	 * @작성일     : 2022. 9. 2. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectSemstrCombo(HttpServletRequest request) throws Exception {
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);
		
		Map<String, String> userInfoMap = new HashMap<String, String>();
		
		userInfoMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());
		if (!userVo.getAgencyContacts().split("/")[3].equals("4")) {
			userInfoMap.put("INST_NO", Integer.toString(userVo.getInstNo()));
		}
		return operPrfmncMapper.selectSemstrCombo(userInfoMap);
	}

}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.subms.cmmn.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;
import isry.subms.cmmn.mapper.SubmsMapper;
import isry.subms.cmmn.service.SubmsService;

/**
 * @파일명 : SubmsServiceImpl.java
 * @프로그램 설명 : 이주배경 공통 서비스 임플 - -
 * @작성자 : Lee.Sang.Hoon
 * @작성일 : 2022. 5. 13.
 * @수정자 : Lee.Sang.Hoon
 * @수정일 : 2022. 5. 13.
 * @수정내용 : - -
 */
@Service("submsService")
public class SubmsServiceImpl extends IsryBaseServiceImpl implements SubmsService {

	@Resource(name = "submsMapper")
	SubmsMapper submsMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectBizYrCombo
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 13.
	 * @Method설명 : 사업연도 콤보 데이터 조회
	 */
	@Override
	public List<Map<String, Object>> selectBizYrCombo(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> userInfoMap = new HashMap<String, String>();
		userInfoMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		return submsMapper.selectBizYrCombo(userInfoMap);
	}

	/**
	 * @Method명 : selectSrvcExcnBizCombo
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 13.
	 * @Method설명 : 서비스 실행 사업 콤보 데이터 조회
	 */
	@Override
	public List<Map<String, Object>> selectSrvcExcnBizCombo(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> userInfoMap = new HashMap<String, String>();
		userInfoMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());

		return submsMapper.selectSrvcExcnBizCombo(userInfoMap);
	}

	/**
	 * @Method명 : selectInstNmCombo
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 5. 16.
	 * @Method설명 : 기관명 콤보 데이터 조회
	 */
	@Override
	public List<Map<String, Object>> selectInstNmCombo(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> userInfoMap = new HashMap<String, String>();
		userInfoMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());
		userInfoMap.put("INST_NO", Integer.toString(userVo.getInstNo()));
		userInfoMap.put("INST_TYPE_SE_CD", userVo.getInstTypeSeCd());

		return submsMapper.selectInstNmCombo(userInfoMap);
	}

	/**
	 * @Method명 : selectResrceNmCombo
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Sang.Hoon
	 * @작성일 : 2022. 6. 21.
	 * @Method설명 : 과정명 콤보 데이터 조회
	 */
	@Override
	public List<Map<String, Object>> selectResrceNmCombo(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		UserDetailsVO userVo = userLoginService.getLoginSessionVO(request);

		Map<String, String> userInfoMap = new HashMap<String, String>();
		userInfoMap.put("UNT_TASKWK_SE_CD", userVo.getUntTaskwk());
		userInfoMap.put("INST_NO", Integer.toString(userVo.getInstNo()));
		userInfoMap.put("INST_TYPE_SE_CD", userVo.getInstTypeSeCd());
		
		return submsMapper.selectResrceNmCombo(userInfoMap);
	}
}

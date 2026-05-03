/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.couns.cmmn.util.CounsUtils;
import isry.couns.mngr.mapper.ConsttSrvyExmnInptCdMapper;
import isry.couns.mngr.service.ConsttSrvyExmnInptCdService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

@Service("consttSrvyExmnInptCdService")
public class ConsttSrvyExmnInptCdServiceImpl extends IsryBaseServiceImpl implements ConsttSrvyExmnInptCdService {

	@Resource(name = "consttSrvyExmnInptCdMapper")
	private ConsttSrvyExmnInptCdMapper mapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : list
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : You Minsang
	 * @작성일     : 2022. 3. 16. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectCombo1List(Map<String, Object> mapParam) throws Exception {
		return mapper.selectCombo1List(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectConsttSrvyExmnInptCdList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception {
		
		HttpSession session = request.getSession();
		
		// 사용자 정보 조회
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		// 단위업무구분코드 설정
		mapParam.put("untTaskwkSeCd", loginVO.getUntTaskwkSeCd());
		
		return mapper.selectConsttSrvyExmnInptCdList(mapParam);
	}

}

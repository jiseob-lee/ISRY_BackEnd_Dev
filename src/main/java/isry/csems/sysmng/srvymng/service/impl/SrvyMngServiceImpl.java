/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.csems.sysmng.srvymng.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;

import isry.csems.sysmng.srvymng.mapper.SrvyMngMapper;
import isry.csems.sysmng.srvymng.service.SrvyMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명 : SrvyMngServiceImpl.java
 * @프로그램 설명 : - -
 * @작성자 : Lee.Hye.Sun
 * @작성일 : 2022. 11. 3.
 * @수정자 : Lee.Hye.Sun
 * @수정일 : 2022. 11. 3.
 * @수정내용 : - -
 */
@Service(value = "csemsSrvyMngService")
public class SrvyMngServiceImpl implements SrvyMngService {

	@Resource(name = "csemsSrvyMngMapper")
	SrvyMngMapper srvyMngMapper;

	@Resource(name = "srvyMngMapper")
	isry.csemd.sysmng.srvymng.mapper.SrvyMngMapper csemdSrvyMngMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명 : selectSrvySndngList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자 : Lee.Hye.Sun
	 * @작성일 : 2022. 11. 3.
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, String>> selectSrvySndngList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");

		Map<String, String> mapParam = dmSearch.getAllRowList().get(0);

		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String untTaskwkSeCd = loginVO.getUntTaskwk(); // 단위업무구분코드

		mapParam.put("UNT_TASKWK_SE_CD", untTaskwkSeCd);

		List<Map<String, String>> result = srvyMngMapper.selectSrvySndngList(mapParam);

		return result;
	}

}

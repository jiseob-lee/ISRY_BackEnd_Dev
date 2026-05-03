/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.consultantabltymng.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseServiceImpl;
import isry.couns.mngr.consultantabltymng.mapper.RaterMngMapper;
import isry.couns.mngr.consultantabltymng.service.RaterMngService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Service("raterMngService")
public class RaterMngServiceImpl extends IsryBaseServiceImpl implements RaterMngService {

	@Resource(name = "raterMngMapper")
	private RaterMngMapper mapper;
	
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
	public List<Map<String, Object>> selectNgtmWorkEtxpyReqstdMngList(Map<String, Object> mapParam) throws Exception {
		return mapper.selectNgtmWorkEtxpyReqstdMngList(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectNgtmWorkEtxpyReqstdMngDetail(Map<String, Object> mapParam) throws Exception {
		return mapper.selectNgtmWorkEtxpyReqstdMngDetail(mapParam);
	}

}

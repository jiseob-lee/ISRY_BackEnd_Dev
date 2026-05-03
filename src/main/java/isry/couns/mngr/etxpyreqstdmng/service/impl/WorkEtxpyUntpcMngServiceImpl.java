/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.etxpyreqstdmng.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseServiceImpl;
import isry.couns.mngr.etxpyreqstdmng.mapper.WorkEtxpyUntpcMngMapper;
import isry.couns.mngr.etxpyreqstdmng.service.WorkEtxpyUntpcMngService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Service("workEtxpyUntpcMngService")
public class WorkEtxpyUntpcMngServiceImpl extends IsryBaseServiceImpl implements WorkEtxpyUntpcMngService {

	@Resource(name = "workEtxpyUntpcMngMapper")
	private WorkEtxpyUntpcMngMapper mapper;
	
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
	public List<Map<String, Object>> selectWorkEtxpyUntpcMngList(Map<String, Object> mapParam) throws Exception {
		return mapper.selectWorkEtxpyUntpcMngList(mapParam);
	}

	@Override
	public int mergeWorkEtxpyUntpcMng(Map<String, Object> mapParam) throws Exception {
		return mapper.mergeWorkEtxpyUntpcMng(mapParam);
	}

	@Override
	public int deleteWorkEtxpyUntpcMng(Map<String, Object> mapParam) throws Exception {
//		return mapper.deleteWorkEtxpyUntpcMng(mapParam);
		return mapper.ddd(mapParam);
	}

}

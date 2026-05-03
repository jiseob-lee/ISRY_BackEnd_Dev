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
import isry.couns.mngr.etxpyreqstdmng.mapper.OvtiWorkEtxpyAplyMngMapper;
import isry.couns.mngr.etxpyreqstdmng.service.OvtiWorkEtxpyAplyMngService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Service("ovtiWorkEtxpyAplyMngService")
public class OvtiWorkEtxpyAplyMngServiceImpl extends IsryBaseServiceImpl implements OvtiWorkEtxpyAplyMngService {

	@Resource(name = "ovtiWorkEtxpyAplyMngMapper")
	private OvtiWorkEtxpyAplyMngMapper mapper;
	
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
	public List<Map<String, Object>> selectOvtiWorkEtxpyAplyMngList(Map<String, Object> mapParam) throws Exception {
		return mapper.selectOvtiWorkEtxpyAplyMngList(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectOvtiWorkEtxpyAplyMngDetail1(Map<String, Object> mapParam) throws Exception {
		return mapper.selectOvtiWorkEtxpyAplyMngDetail1(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectOvtiWorkEtxpyAplyMngDetail2(Map<String, Object> mapParam) throws Exception {
		return mapper.selectOvtiWorkEtxpyAplyMngDetail2(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectOvtiWorkEtxpyAplyMngDetail3(Map<String, Object> mapParam) throws Exception {
		return mapper.selectOvtiWorkEtxpyAplyMngDetail3(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectOvtiWorkEtxpyAplyMngDetail4(Map<String, Object> mapParam) throws Exception {
		return mapper.selectOvtiWorkEtxpyAplyMngDetail4(mapParam);
	}

}

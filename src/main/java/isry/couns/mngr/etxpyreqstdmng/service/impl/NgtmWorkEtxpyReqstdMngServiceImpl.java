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
import isry.couns.mngr.etxpyreqstdmng.mapper.NgtmWorkEtxpyReqstdMngMapper;
import isry.couns.mngr.etxpyreqstdmng.service.NgtmWorkEtxpyReqstdMngService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Service("ngtmWorkEtxpyReqstdMngService")
public class NgtmWorkEtxpyReqstdMngServiceImpl extends IsryBaseServiceImpl implements NgtmWorkEtxpyReqstdMngService {

	@Resource(name = "ngtmWorkEtxpyReqstdMngMapper")
	private NgtmWorkEtxpyReqstdMngMapper mapper;
	
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
	public List<Map<String, Object>> selectNgtmWorkEtxpyReqstdMngDetail1(Map<String, Object> mapParam) throws Exception {
		return mapper.selectNgtmWorkEtxpyReqstdMngDetail1(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectNgtmWorkEtxpyReqstdMngDetail2(Map<String, Object> mapParam) throws Exception {
		return mapper.selectNgtmWorkEtxpyReqstdMngDetail2(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectNgtmWorkEtxpyReqstdMngDetail3(Map<String, Object> mapParam) throws Exception {
		return mapper.selectNgtmWorkEtxpyReqstdMngDetail3(mapParam);
	}

	@Override
	public int saveNgtmWorkEtxpyReqstdMngDetail(Map<String, Object> mapParam) throws Exception {
		return mapper.saveNgtmWorkEtxpyReqstdMngDetail(mapParam);
	}

}

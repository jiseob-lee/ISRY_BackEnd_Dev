/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.etxpyaplyandinq.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.base.IsryBaseServiceImpl;
import isry.couns.taskwksprt.etxpyaplyandinq.mapper.NgtmWorkEtxpyAplyMapper;
import isry.couns.taskwksprt.etxpyaplyandinq.service.NgtmWorkEtxpyAplyService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Service("ngtmWorkEtxpyAplyService")
public class NgtmWorkEtxpyAplyServiceImpl extends IsryBaseServiceImpl implements NgtmWorkEtxpyAplyService {

	@Resource(name = "ngtmWorkEtxpyAplyMapper")
	private NgtmWorkEtxpyAplyMapper ngtmWorkEtxpyAplyMapper;
	
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
	public List<Map<String, Object>> selectNgtmWorkEtxpyAplyList(Map<String, Object> mapParam) throws Exception {
		return ngtmWorkEtxpyAplyMapper.selectNgtmWorkEtxpyAplyList(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectNgtmWorkEtxpyAplyList2(Map<String, Object> mapParam) throws Exception {
		return ngtmWorkEtxpyAplyMapper.selectNgtmWorkEtxpyAplyList2(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectNgtmWorkEtxpyAplyList3(Map<String, Object> mapParam) throws Exception {
		return ngtmWorkEtxpyAplyMapper.selectNgtmWorkEtxpyAplyList3(mapParam);
	}
	
	@Override
	public int insertNgtmWorkEtxpyAply(Map<String, Object> mapParam) throws Exception {
		return ngtmWorkEtxpyAplyMapper.insertNgtmWorkEtxpyAply(mapParam);
	}
	
	;
	

}

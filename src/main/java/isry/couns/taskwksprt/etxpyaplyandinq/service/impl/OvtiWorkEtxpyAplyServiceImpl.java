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
import isry.couns.taskwksprt.etxpyaplyandinq.mapper.OvtiWorkEtxpyAplyMapper;
import isry.couns.taskwksprt.etxpyaplyandinq.service.NgtmWorkEtxpyAplyService;
import isry.couns.taskwksprt.etxpyaplyandinq.service.OvtiWorkEtxpyAplyService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Service("ovtiWorkEtxpyAplyService")
public class OvtiWorkEtxpyAplyServiceImpl extends IsryBaseServiceImpl implements OvtiWorkEtxpyAplyService {

	@Resource(name = "ovtiWorkEtxpyAplyMapper")
	private OvtiWorkEtxpyAplyMapper mapper;
	
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
	public List<Map<String, Object>> selectOvtiWorkEtxpyAplyList1(Map<String, Object> mapParam) throws Exception {
		return mapper.selectOvtiWorkEtxpyAplyList1(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectOvtiWorkEtxpyAplyList2(Map<String, Object> mapParam) throws Exception {
		return mapper.selectOvtiWorkEtxpyAplyList2(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectOvtiWorkEtxpyAplyList3(Map<String, Object> mapParam) throws Exception {
		return mapper.selectOvtiWorkEtxpyAplyList3(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectOvtiWorkEtxpyAplyList4(Map<String, Object> mapParam) throws Exception {
		return mapper.selectOvtiWorkEtxpyAplyList4(mapParam);
	}
	
	@Override
	public int insertOvtiWorkEtxpyAply(Map<String, String> mapParam) throws Exception {
		return mapper.insertOvtiWorkEtxpyAply(mapParam);
	}
	
	;
	

}

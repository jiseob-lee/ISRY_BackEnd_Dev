/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.taskwkandatdmng.service.impl;

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
import isry.couns.taskwksprt.taskwkandatdmng.mapper.OfwkUpcsdClngSprtMapper;
import isry.couns.taskwksprt.taskwkandatdmng.mapper.RcivEqptIndtyMapper;
import isry.couns.taskwksprt.taskwkandatdmng.mapper.StmtOfRsMapper;
import isry.couns.taskwksprt.taskwkandatdmng.mapper.TaskwkReprtsMapper;
import isry.couns.taskwksprt.taskwkandatdmng.service.OfwkUpcsdClngSprtService;
import isry.couns.taskwksprt.taskwkandatdmng.service.RcivEqptIndtyService;
import isry.couns.taskwksprt.taskwkandatdmng.service.StmtOfRsService;
import isry.couns.taskwksprt.taskwkandatdmng.service.TaskwkReprtsService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Service("ofwkUpcsdClngSprtService")
public class OfwkUpcsdClngSprtServiceImpl extends IsryBaseServiceImpl implements OfwkUpcsdClngSprtService {

	@Resource(name = "ofwkUpcsdClngSprtMapper")
	private OfwkUpcsdClngSprtMapper ofwkUpcsdClngSprtMapper;
	
	/**
	 * @Method명   : insertOfwkUpcsdClng
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 6. 
	 * @Method설명 :
	 */
	@Override
	public int insertOfwkUpcsdClng(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return ofwkUpcsdClngSprtMapper.insertOfwkUpcsdClng(mapParam);
	}
	
	/**
	 * @Method명   : selectOfwkUpcsdClngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 6. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectOfwkUpcsdClngList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return ofwkUpcsdClngSprtMapper.selectOfwkUpcsdClngList(mapParam);
	}
	
	/**
	 * @Method명   : selectOfwkUpcsdClngRegDtl
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 6. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectOfwkUpcsdClngRegDtl(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return ofwkUpcsdClngSprtMapper.selectOfwkUpcsdClngRegDtl(mapParam);
	}
	
}

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
import isry.couns.taskwksprt.taskwkandatdmng.mapper.StmtOfRsMapper;
import isry.couns.taskwksprt.taskwkandatdmng.mapper.TaskwkReprtsMapper;
import isry.couns.taskwksprt.taskwkandatdmng.mapper.TlcmtChgReqstdMapper;
import isry.couns.taskwksprt.taskwkandatdmng.service.StmtOfRsService;
import isry.couns.taskwksprt.taskwkandatdmng.service.TaskwkReprtsService;
import isry.couns.taskwksprt.taskwkandatdmng.service.TlcmtChgReqstdService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

@Service("tlcmtChgReqstdService")
public class TlcmtChgReqstdServiceImpl extends IsryBaseServiceImpl implements TlcmtChgReqstdService {

	@Resource(name = "tlcmtChgReqstdMapper")
	private TlcmtChgReqstdMapper tlcmtChgReqstdMapper;
	
	/**
	 * @Method명   : selectUserInfo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 9. 8. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> selectUserInfo(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return tlcmtChgReqstdMapper.selectUserInfo(mapParam);
	}
	
	/**
	 * @Method명   : selectTlcmtChgReqstdList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 5. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectTlcmtChgReqstdList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return tlcmtChgReqstdMapper.selectTlcmtChgReqstdList(mapParam);
	}
	
	/**
	 * @Method명   : insertTlcmtChgReqstd
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 5. 
	 * @Method설명 :
	 */
	@Override
	public int insertTlcmtChgReqstd(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return tlcmtChgReqstdMapper.insertTlcmtChgReqstd(mapParam);
	}
	
	/**
	 * @Method명   : deleteTlcmtChgReqstd
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 5. 
	 * @Method설명 :
	 */
	@Override
	public int deleteTlcmtChgReqstd(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return tlcmtChgReqstdMapper.deleteTlcmtChgReqstd(mapParam);
	}

	
}

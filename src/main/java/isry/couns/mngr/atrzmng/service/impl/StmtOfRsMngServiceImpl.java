/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.atrzmng.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.couns.cmmn.util.CounsUtils;
import isry.couns.mngr.atrzmng.mapper.StmtOfRsMngMapper;
import isry.couns.mngr.atrzmng.service.StmtOfRsMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class StmtOfRsMngServiceImpl extends IsryBaseServiceImpl implements StmtOfRsMngService {

	@Resource(name = "stmtOfRsMngMapper")
	private StmtOfRsMngMapper stmtOfRsMngMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : searchComboBoxAprv
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 20. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> searchComboBoxAprv(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return stmtOfRsMngMapper.searchComboBoxAprv(mapParam);
	}
	
	/**
	 * @Method명   : selectStmtOfRsMngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectStmtOfRsMngList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception {
		
		HttpSession session = request.getSession();
		
		// 사용자 정보 조회
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		// 단위업무구분코드 설정
		mapParam.put("untTaskwkSeCd", loginVO.getUntTaskwkSeCd());
		
		return stmtOfRsMngMapper.selectStmtOfRsMngList(mapParam);
	}
	
	/**
	 * @Method명   : selectStmtOfRsMngDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectStmtOfRsMngDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return stmtOfRsMngMapper.selectStmtOfRsMngDetail(mapParam);
	}
	
	/**
	 * @Method명   : updateStmtOfRsMng
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 20. 
	 * @Method설명 :
	 */
	@Override
	public int updateStmtOfRsMng(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return stmtOfRsMngMapper.updateStmtOfRsMng(mapParam);
	}
	
	/**
	 * @Method명   : deleteStmtOfRsMng
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 20. 
	 * @Method설명 :
	 */
	@Override
	public int deleteStmtOfRsMng(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return stmtOfRsMngMapper.deleteStmtOfRsMng(mapParam);
	}
	
	
}

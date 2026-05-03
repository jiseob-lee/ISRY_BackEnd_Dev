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
import isry.couns.mngr.atrzmng.mapper.RcivEqptIndtyMngMapper;
import isry.couns.mngr.atrzmng.service.RcivEqptIndtyMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class RcivEqptIndtyMngServiceImpl extends IsryBaseServiceImpl implements RcivEqptIndtyMngService {

	@Resource(name = "rcivEqptIndtyMngMapper")
	private RcivEqptIndtyMngMapper rcivEqptIndtyMngMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectRcivEqptIndtyMngList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectRcivEqptIndtyMngList(HttpServletRequest request, Map<String, Object> mapParam) throws Exception {
		
		HttpSession session = request.getSession();
		
		// 사용자 정보 조회
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		// 단위업무구분코드 설정
		mapParam.put("untTaskwkSeCd", loginVO.getUntTaskwkSeCd());
		
		return rcivEqptIndtyMngMapper.selectRcivEqptIndtyMngList(mapParam);
	}
	
	/**
	 * @Method명   : selectRcivEqptindtyMngDetail
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectRcivEqptindtyMngDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return rcivEqptIndtyMngMapper.selectRcivEqptindtyMngDetail(mapParam);
	}
	
	/**
	 * @Method명   : searchComboItem
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 17. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> searchComboItem(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return rcivEqptIndtyMngMapper.searchComboItem(mapParam);
	}
	
	/**
	 * @Method명   : updateRcivEqptIndtyMng
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 21. 
	 * @Method설명 :
	 */
	@Override
	public int updateRcivEqptIndtyMng(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return rcivEqptIndtyMngMapper.updateRcivEqptIndtyMng(mapParam);
	}
	
	/**
	 * @Method명   : updateRcivEqptIndtyMng1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 21. 
	 * @Method설명 :
	 */
	@Override
	public int updateRcivEqptIndtyMng1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return rcivEqptIndtyMngMapper.updateRcivEqptIndtyMng1(mapParam);
	}
	
	/**
	 * @Method명   : deleteRciveEqptIndtyMng
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 21. 
	 * @Method설명 :
	 */
	@Override
	public int deleteRciveEqptIndtyMng(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return rcivEqptIndtyMngMapper.deleteRciveEqptIndtyMng(mapParam);
	}
	
	/**
	 * @Method명   : deleteRciveEqptIndtyMng1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 21. 
	 * @Method설명 :
	 */
	@Override
	public int deleteRciveEqptIndtyMng1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return rcivEqptIndtyMngMapper.deleteRciveEqptIndtyMng1(mapParam);
	}
	
	
	
	
}

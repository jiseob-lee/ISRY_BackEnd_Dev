/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.taskwkandatdmng.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import isry.base.IsryBaseServiceImpl;
import isry.couns.taskwksprt.taskwkandatdmng.mapper.RcivEqptIndtyMapper;
import isry.couns.taskwksprt.taskwkandatdmng.service.RcivEqptIndtyService;

@Service("rcivEqptIndtyService")
public class RcivEqptIndtyServiceImpl extends IsryBaseServiceImpl implements RcivEqptIndtyService {

	@Resource(name = "rcivEqptIndtyMapper")
	private RcivEqptIndtyMapper rcivEqptIndtyMapper;
	
	/**
	 * @Method명   : searchComboOptionRcivEq
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 5. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> searchComboOptionRcivEq(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return rcivEqptIndtyMapper.searchComboOptionRcivEq(mapParam);
	}

	/**
	 * @Method명   : selectUserInfo
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 로그인 사용자 정보 조회
	 */
	@Override
	public Map<String, Object> selectUserInfo(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return rcivEqptIndtyMapper.selectUserInfo(mapParam);
	}
	
	/**
	 * @Method명   : selectRcivEqptIndtyList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 5. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectRcivEqptIndtyList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return rcivEqptIndtyMapper.selectRcivEqptIndtyList(mapParam);
	}
	
	/**
	 * @Method명   : insertRcivEqptIndty
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 5. 
	 * @Method설명 :
	 */
	@Override
	public int insertRcivEqptIndty(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return rcivEqptIndtyMapper.insertRcivEqptIndty(mapParam);
	}
	
	/**
	 * @Method명   : insertRcivEqptIndty1
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 7. 5. 
	 * @Method설명 :
	 */
	@Override
	public int insertRcivEqptIndty1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return rcivEqptIndtyMapper.insertRcivEqptIndty1(mapParam);
	}

}

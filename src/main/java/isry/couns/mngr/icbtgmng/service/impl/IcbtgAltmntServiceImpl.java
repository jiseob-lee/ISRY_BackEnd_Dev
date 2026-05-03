/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.mngr.icbtgmng.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;
import com.tomatosystem.exbuilder6.core.util.StringUtil;

import isry.base.IsryBaseServiceImpl;
import isry.couns.mngr.atrzmng.mapper.RcivEqptIndtyMngMapper;
import isry.couns.mngr.atrzmng.mapper.WorkChgMngMapper;
import isry.couns.mngr.atrzmng.service.RcivEqptIndtyMngService;
import isry.couns.mngr.atrzmng.service.WorkChgMngService;
import isry.couns.mngr.icbtgmng.mapper.IcbtgAltmntMapper;
import isry.couns.mngr.icbtgmng.mapper.IcbtgConsttChcMapper;
import isry.couns.mngr.icbtgmng.service.IcbtgAltmntService;
import isry.couns.mngr.icbtgmng.service.IcbtgConsttChcService;
import isry.couns.mngr.workaltmntmng.mapper.EvdyDscsnClsMapper;
import isry.couns.mngr.workaltmntmng.mapper.MnthySchdlRegInfoMngMapper;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class IcbtgAltmntServiceImpl extends IsryBaseServiceImpl implements IcbtgAltmntService {

	@Resource(name = "icbtgAltmntMapper")
	private IcbtgAltmntMapper icbtgAltmntMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectIcbtgAltmntList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Youngtae Yoo
	 * @작성일     : 2022. 6. 24. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectIcbtgAltmntList(Map<String, String> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return icbtgAltmntMapper.selectIcbtgAltmntList(mapParam);
	}
	
	/**
	 * @Method명   : insertIcbtgAltmnt
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : int
	 * @throws     : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 9. 2. 
	 * @Method설명 : IcbtgConsttChcServiceImpl(작성자 : 유영태) → IcbtgAltmntServiceImpl로 복사
	 */
	@Override
	public int insertIcbtgAltmnt(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> dsListMap = paramGroup.getAllRowList();
		Map<String, String> paramMap = dsListMap.get(0);
		int retVal = 0;
		//System.out.println("인큐베이팅 paramMap = [ " + paramMap + " ]");
		//log.debug("인큐베이팅 등록 paramMap = [ " + paramMap + " ]");
		if( icbtgAltmntMapper.selectIcbtgAltmntYmdCheckList(paramMap).size() == 0 ) {
			retVal = icbtgAltmntMapper.insertIcbtgAltmnt(paramMap);
		} 
		return retVal;
	}



	/**
	 * @Method명   : updateIcbtgAltmnt
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : int
	 * @throws     : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 9. 1. 
	 * @Method설명 :
	 */
	@Override
	public int updateIcbtgAltmnt(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String userId = "";	// 세션정보의 유저ID
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> dsListMap = paramGroup.getAllRowList();
		Map<String, String> paramMap = dsListMap.get(0);
		
		// 세션정보 가져오기
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if(loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		} else {
			throw new AppWorksException("세션정보 자료가 없습니다.", Alert.ERROR);
		}
		
		paramMap.put("USER_ID", userId);
		
		//System.out.println("paramMap = [ " + paramMap + " ]");
		
		icbtgAltmntMapper.updateIcbtgAltmnt(paramMap);
				
		return 0;
	}
	
	/**
	 * @Method명   : deleteIcbtgAltmnt
	 * @param      : request
	 * @param      : dataRequest
	 * @return     : int
	 * @throws     : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2022. 9. 1. 
	 * @Method설명 :
	 */
	@Override
	public int deleteIcbtgAltmnt(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsList");
		List<Map<String, String>> dsListMap = paramGroup.getAllRowList();
//		System.out.println("dsListMap = [ " + dsListMap.get(0) + " ] ");
		Map<String, String> paramMap = dsListMap.get(0);
		
		icbtgAltmntMapper.deleteIcbtgAltmnt(paramMap);
		
		return 0;
	}
		
}

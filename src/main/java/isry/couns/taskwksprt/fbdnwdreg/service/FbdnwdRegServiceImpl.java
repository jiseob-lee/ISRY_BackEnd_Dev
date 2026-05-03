/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.taskwksprt.fbdnwdreg.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;

import isry.couns.taskwksprt.fbdnwdreg.mapper.FbdnwdRegMapper;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.Masking;
import isry.redis.service.RedisService;

/**
 * @파일명        : FbdnwdRegServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : 박찬호¸
 * @작성일        : 2022. 5. 19. 
 * @수정자        : 박찬호¸
 * @수정일        : 2022. 5. 19.
 * @수정내용      : 
 * -                
 * -                
 */
@Service
public class FbdnwdRegServiceImpl implements FbdnwdRegService {

	@Resource(name = "FbdnwdRegMapper")
	private FbdnwdRegMapper fbdnwdRegMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectFbdnwdRegList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : 박찬호¸
	 * @작성일     : 2022. 5. 19. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> selectFbdnwdRegList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return fbdnwdRegMapper.selectFbdnwdRegList(mapParam);
	}

	/**
	 * @Method명   : saveFbdnwdReg
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : 박찬호¸
	 * @작성일     : 2022. 5. 19. 
	 * @Method설명 :
	 */
	@Override
	public Map<String, Object> saveFbdnwdReg(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsList = dataRequest.getParameterGroup("dsList");

		Iterator<ParameterRow> insertedRows = dsList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsList.getDeletedRows();
		//Masking masking = new Masking();
				
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("loginId", userId);
			fbdnwdRegMapper.insertFbdnwdReg(mapIns);
		}

		while (updatedRows.hasNext()) {
		  Map<String, String> mapUpd = updatedRows.next().toMap();
		  mapUpd.put("loginId", userId);
		  fbdnwdRegMapper.updateFbdnwdReg(mapUpd);
		}
		 

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("loginId", userId);
			fbdnwdRegMapper.deleteFbdnwdReg(mapDel);			
			
		}

		return null;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

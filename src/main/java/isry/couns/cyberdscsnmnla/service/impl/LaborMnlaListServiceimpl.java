/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.cyberdscsnmnla.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
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
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.util.StringUtil;

import isry.couns.cyberdscsnmnla.mapper.LaborMnlaMapper;
import isry.couns.cyberdscsnmnla.service.LaborMnlaListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class LaborMnlaListServiceimpl implements LaborMnlaListService {


	@Resource(name = "laborMnlaMapper")
	private LaborMnlaMapper laborMnlaMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : onLoadList
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Song.Young.Il
	 * @작성일     : 2022. 5. 13. 
	 * @Method설명 :
	 */
	@Override
	public List<Map<String, Object>> subOnLoad(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return laborMnlaMapper.subOnLoad(mapParam);
	}
//
//
//	@Override
//	public List<Map<String, Object>> selectCyberMnlaList(Map<String, Object> mapParam) throws Exception {
//		// TODO Auto-generated method stub
//		return cyberMnlaListMapper.selectInqCyberMnlaList(mapParam);
//	}
//
	@Override
	public void saveLaborMnlaProc(HttpServletRequest request, DataRequest dataRequest) {
		
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
		

		Iterator<ParameterRow> insertedRows = dsBoardList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsBoardList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsBoardList.getDeletedRows();
		
//		System.out.println("dsBoardList.getinsertedRows()"+insertedRows.hasNext());
//		System.out.println("dsBoardList.getDeletedRows()"+deletedRows.hasNext());
		
		
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			
			laborMnlaMapper.insertLaborMnlaReg(mapIns);

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
//			cyberMnlaListMapper.updateCyberMnlaProc(mapUpd);	
			
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
//			cyberMnlaListMapper.deleteCyberMnlaProc(mapDel);			
			
		}
		
	}



	
}

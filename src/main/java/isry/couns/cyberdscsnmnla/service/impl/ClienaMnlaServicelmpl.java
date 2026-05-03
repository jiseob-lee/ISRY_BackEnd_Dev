/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.cyberdscsnmnla.service.impl;

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

import isry.couns.cyberdscsnmnla.mapper.ClienaMnlaMapper;
import isry.couns.cyberdscsnmnla.service.ClienaMnlaService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;


@Service
public class ClienaMnlaServicelmpl implements ClienaMnlaService {


	@Resource(name = "clienaMnlaMapper")
	private ClienaMnlaMapper clienaMnlaMapper;

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
	public List<Map<String, Object>> clienaMnlaCode(String codeId) throws Exception {
		// TODO Auto-generated method stub
		return clienaMnlaMapper.clienaMnlaCode(codeId);
	}
	
	@Override
	public List<Map<String, Object>> clienaMnlaCodeS(String codeId) throws Exception {
		// TODO Auto-generated method stub
		return clienaMnlaMapper.clienaMnlaCodeS(codeId);
	}
	
	
	@Override
	public List<Map<String, Object>> selectClienaMnlaDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return clienaMnlaMapper.selectClienaMnlaDetail(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectClienaMnlaList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return clienaMnlaMapper.selectClienaMnlaList(mapParam);
	}

	@Override
	public Map<String, Object> saveClienaMnlaProc(HttpServletRequest request, DataRequest dataRequest) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		
		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");

		Iterator<ParameterRow> insertedRows = dsBoardList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsBoardList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsBoardList.getDeletedRows();		
		
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
			
			clienaMnlaMapper.insertClienaMnlaReg(mapIns);

			mapReturn.put("MNLA_NO", mapIns.get("MNLA_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			clienaMnlaMapper.updateClienaMnlaProc(mapUpd);		
			
			mapReturn.put("MNLA_NO", mapUpd.get("MNLA_NO"));
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			clienaMnlaMapper.deleteClienaMnlaProc(mapDel);			
			
		}
		return mapReturn;
	}
	
}

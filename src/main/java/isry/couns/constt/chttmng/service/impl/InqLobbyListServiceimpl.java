/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.chttmng.service.impl;

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
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.couns.constt.chttmng.mapper.InqLobbyListMapper;
import isry.couns.constt.chttmng.service.InqLobbyListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;

/**
 * @파일명        : BbsonmServicelmpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Song.Young.Il
 * @작성일        : 2022. 5. 13. 
 * @수정자        : Song.Young.Il
 * @수정일        : 2022. 5. 13.
 * @수정내용      : 
 * -                
 * -                
 */

@Service("InqLobbyListService")
public class InqLobbyListServiceimpl implements InqLobbyListService{
	
	@Resource(name = "InqLobbyListMapper")
	private InqLobbyListMapper inqLobbyListMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectInqLobbyList(Map<String, Object> mapParam) {
		
		return inqLobbyListMapper.selectInqLobbyList(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectInqLobbyDetail(Map<String, Object> mapParam) {
		
		return inqLobbyListMapper.selectInqLobbyDetail(mapParam);
	}

	@Override
	public Map<String, Object> saveLobbyList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
		
		String DupliYn = dsBoardList.getValue(0, "DPCN_YN");
		Iterator<ParameterRow> insertedRows = dsBoardList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsBoardList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsBoardList.getDeletedRows();
				
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		String userId = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
//		System.out.println("중복 여부 === " + DupliYn);
		if ("Y".equals(DupliYn)) {
			Map<String, String> dsBoardMap = dsBoardList.getSingleValueMap();
			dsBoardMap.put("FRST_RGTR_ID", userId);
			dsBoardMap.put("LAST_MDFR_ID", userId);
//			System.out.println("뭐냐 이건 === " + dsBoardMap);
			
			
			int delResultVal = inqLobbyListMapper.deleteLobby(dsBoardMap);
			if (delResultVal == 1) {
				inqLobbyListMapper.insertLobby(dsBoardMap);
			} else {
				throw new AppWorksException("내담자ID와 일치하는 데이터가 없습니다.");
			}
			
		} else {
			while (insertedRows.hasNext()) {

				Map<String, String> mapIns = insertedRows.next().toMap();
				
				mapIns.put("FRST_RGTR_ID", userId);
				mapIns.put("LAST_MDFR_ID", userId);
				inqLobbyListMapper.insertLobby(mapIns);
				// 게시글 번호 키값 셋팅
				mapReturn.put("CHTT_LOG_ESNTAL_NO", inqLobbyListMapper.selectBrdMaxCnt());

			}
			while (updatedRows.hasNext()) {

				Map<String, String> mapUpd = updatedRows.next().toMap();
				mapUpd.put("LAST_MDFR_ID", userId);
				inqLobbyListMapper.updateLobby(mapUpd);
				
				mapReturn.put("CHTT_LOG_ESNTAL_NO", mapUpd.get("CHTT_LOG_ESNTAL_NO"));

			}

			while (deletedRows.hasNext()) {
				
				Map<String, String> mapDel = deletedRows.next().toMap();
				mapDel.put("LAST_MDFR_ID", userId);
				inqLobbyListMapper.deleteLobby(mapDel);			
				
			}
		}

		return mapReturn;
	}
	
	@Override
	public int getTotalCount(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return inqLobbyListMapper.getTotalCount(mapParam);
	}

	@Override
	public void inqLobbyDtlCnt(Map<String, Object> mapParam) {
		
		inqLobbyListMapper.inqLobbyDtlCnt(mapParam);
	}
	
	@Override
	public int selectIdCheck(HttpServletRequest request, DataRequest dataRequest) {
		Map<String, Object> mapParam = new HashMap<String, Object>();
		
		ParameterGroup searchParam = dataRequest.getParameterGroup("dmIdCheck");
		mapParam.put("ID", searchParam.getValue("ID"));
		
		return inqLobbyListMapper.selectIdCheck(mapParam);
		
	}
}

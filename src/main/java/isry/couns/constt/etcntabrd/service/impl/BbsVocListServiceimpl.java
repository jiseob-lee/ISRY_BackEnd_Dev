/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.etcntabrd.service.impl;

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

import isry.couns.cmmn.service.CounsService;
import isry.couns.constt.etcntabrd.mapper.BbsVocListMapper;
import isry.couns.constt.etcntabrd.service.BbsVocListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class BbsVocListServiceimpl implements BbsVocListService {


	@Resource(name = "bbsVocListMapper")
	private BbsVocListMapper bbsVocListMapper;
	
	@Resource(name = "counsService")
	private CounsService counsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : subOnLoad
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.seong.gen
	 * @작성일     : 2022. 5. 27. 
	 * @Method설명 :
	 */
	@Override
	public int getTotalCount(Map<String, Object> mapParam) throws Exception {
		
		return bbsVocListMapper.getTotalCount(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectBbsVocList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsVocListMapper.selectBbsVocList(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectBbsVocDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsVocListMapper.selectBbsVocDetail(mapParam);
	}
	
	@Override
	public Map<String, Object> saveBbsVocProc(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
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
			dsBoardList.setValue(0, "WRTR_NM_ENCPT", dsBoardList.getValue("WRTR_NM_ENCPT"));
			
			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);

//			bbsVocListMapper.insertBbsVocReg(mapIns);
			
//			mapReturn.put("RETE_ESNTAL_NO", mapIns.get("RETE_ESNTAL_NO"));
//			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbsVocListMapper.updateBbsVocProc(mapUpd);	
			
//			mapReturn.put("RETE_ESNTAL_NO", mapUpd.get("RETE_ESNTAL_NO"));
//			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
			
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsVocListMapper.deleteBbsVocProc(mapDel);			
		}
		return mapReturn;
	}	
	
//	---------------------------------------------------답글
	
	@Override//답글(추가(insertBbsRespodVoc), 수정(updateBbsRespodVoc), 삭제(deleteBbsRespodVoc))
	public Map<String, Object> saveBbsRespodVoc(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsRplyList = dataRequest.getParameterGroup("dsRplyList");
		
//		System.out.println("dsRplyList 0000000000 ::::::" + dsRplyList.toString());
		
		Iterator<ParameterRow> insertedRows = dsRplyList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsRplyList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsRplyList.getDeletedRows();
	
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
			dsRplyList.setValue(0, "WRTR_NM_ENCPT", dsRplyList.getValue("WRTR_NM_ENCPT"));

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
//			bbsVocListMapper.insertBbsRespodVoc(mapIns);
			
			// Mapper Insert Method
			int result = bbsVocListMapper.insertBbsRespodVoc1(mapIns);
			
			if (result == 1) {
				// CounsService Method 호출
				counsService.processAnsCmptnAutoSndng(request, dataRequest, mapIns);
			}

			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
			mapReturn.put("RETE_ESNTAL_NO", mapIns.get("RETE_ESNTAL_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
//			bbsVocListMapper.updateBbsRespodVoc(mapUpd);
			bbsVocListMapper.updateBbsRespodVoc1(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
			mapReturn.put("RETE_ESNTAL_NO", mapUpd.get("RETE_ESNTAL_NO"));
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
//			bbsVocListMapper.deleteBbsRespodVoc(mapDel);
			bbsVocListMapper.deleteBbsRespodVoc1(mapDel);	
		}
		
		return mapReturn;
	}
	
	@Override
	public List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsVocListMapper.selectRespodDetail(mapParam);
	}
	
}

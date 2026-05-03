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

import isry.couns.constt.etcntabrd.mapper.BbsRplyListMapper;
import isry.couns.constt.etcntabrd.service.BbsRplyListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class BbsRplyListServiceImpl implements BbsRplyListService {


	@Resource(name = "bbsRplyListMapper")
	private BbsRplyListMapper bbsRplyListMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : subOnLoad
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.seong.gen
	 * @작성일     : 2022. 5. 25. 
	 * @Method설명 :
	 */
	@Override
	public int getTotalCount(Map<String, Object> mapParam) throws Exception {
		
		return bbsRplyListMapper.getTotalCount(mapParam);
	}
	
	public List<Map<String, Object>> selectInqBbsRplyList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsRplyListMapper.selectInqBbsRplyList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectInqBbsRplyListDetail(Map<String, Object> mapParam) throws Exception {
		String strCreateYn = (String) mapParam.get("CREATE_YN");
		
		if(!strCreateYn.equals("Y")) {
			// 조회수 추가
			bbsRplyListMapper.updateRdcntDtlList(mapParam);
		}
		
		return bbsRplyListMapper.selectInqBbsRplyListDetail(mapParam);
	}
	
	@Override
	public Map<String, Object> saveBbsRplyProc(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");

		Iterator<ParameterRow> insertedRows = dsBoardList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsBoardList.getUpdatedRows();
		Iterator<ParameterRow> deleteRows = dsBoardList.getDeletedRows();
		
	
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
			bbsRplyListMapper.insertBbsRplyReg(mapIns);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbsRplyListMapper.updateBbsRplyProc(mapUpd);	
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
		}
		
		while (deleteRows.hasNext()) {
			Map<String, String> mapDel = deleteRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsRplyListMapper.deleteBbsRplyProc(mapDel);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapDel.get("BBSCTT_ESNTAL_NO"));
		}

		return mapReturn;
	}
//	---------------------------------------------------댓글
	@Override
	public List<Map<String, Object>> subRplyList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsRplyListMapper.subRplyList(mapParam);
	}
	
	
	@Override
	public Map<String, Object> saveBbsDetailRply(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
		
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsRplyBoard = dataRequest.getParameterGroup("dsRplyList");

		Iterator<ParameterRow> insertedRows = dsRplyBoard.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsRplyBoard.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsRplyBoard.getDeletedRows();		
		
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
			bbsRplyListMapper.insertBbsDetailRplyReg(mapIns);
			
			mapReturn.put("CMNT_ESNTAL_NO", mapIns.get("CMNT_ESNTAL_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbsRplyListMapper.updateBbsDetailRplyProc(mapUpd);
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsRplyListMapper.deleteBbsDetailRplyProc(mapDel);			
		}
		return mapReturn;
	}


}

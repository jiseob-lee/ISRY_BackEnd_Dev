/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.medscsnntabrd.service.impl;

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

import isry.couns.constt.medscsnntabrd.mapper.BbsgomListMapper;
import isry.couns.constt.medscsnntabrd.service.BbsgomListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class BbsgomListServiceimpl implements BbsgomListService{
	
	@Resource(name = "bbsgomListMapper")
	private BbsgomListMapper BbsgomListMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : subOnLoad
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.seong.gen
	 * @작성일     : 2022. 6. 9. 
	 * @Method설명 :
	 */
	@Override
	public int getTotalCount(Map<String, Object> mapParam) throws Exception {
		
		return BbsgomListMapper.getTotalCount(mapParam);
	}
	
	@Override//고민해결백과(댓글)본인상담
	public List<Map<String, Object>> selectBbsgomList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return BbsgomListMapper.selectBbsgomList(mapParam);
	}
	
	@Override//고민해결백과(댓글)답변완료
	public List<Map<String, Object>> selectBbsgomListY(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return BbsgomListMapper.selectBbsgomListY(mapParam);
	}
	
	@Override//고민해결백과(댓글)미답변
	public List<Map<String, Object>> selectBbsgomListN(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return BbsgomListMapper.selectBbsgomListN(mapParam);
	}
	
	
	@Override
	public List<Map<String, Object>> selectBbsgomDetail(Map<String, Object> mapParam) throws Exception {
		String strCreateYn = (String) mapParam.get("CREATE_YN");

		if(!strCreateYn.equals("Y")) {
			
//			System.out.println("rrrrrr::"+mapParam.get("BBSCTT_ESNTAL_NO"));
			// 조회수 추가
			BbsgomListMapper.updateRdcntDtlList(mapParam);
		}
		
		return BbsgomListMapper.selectBbsgomDetail(mapParam);
	}
	
	@Override
	public void insertMemo(Map<String, Object> mapParam) {
		if(BbsgomListMapper.selectMemo(mapParam).size() == 0) {			
			BbsgomListMapper.insertMemo(mapParam);
		}else {
			BbsgomListMapper.updateMemo(mapParam);
		}
	}
	
	@Override//게시글(추가(insertBbsgom), 수정(updateBbsgom), 삭제(deleteBbsgom))
	public Map<String, Object> saveBbsgom(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsList");

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
			BbsgomListMapper.insertBbsgom(mapIns);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			BbsgomListMapper.updateBbsgom(mapUpd);	
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			BbsgomListMapper.deleteBbsgom(mapDel);			
		}
		
		return mapReturn;
	}
////	---------------------------------------------------댓글
	@Override
	public List<Map<String, Object>> selectRplyDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return BbsgomListMapper.selectRplyDetail(mapParam);
	}
	
	
	@Override//댓글(추가(insertRply), 수정(updateRply), 삭제(deleteRply))
	public Map<String, Object> saveRply(HttpServletRequest request, DataRequest dataRequest) {
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
			BbsgomListMapper.insertRply(mapIns);
			
			mapReturn.put("CMNT_ESNTAL_NO", mapIns.get("CMNT_ESNTAL_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			BbsgomListMapper.updateRply(mapUpd);
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			BbsgomListMapper.deleteRply(mapDel);			
		}
		return mapReturn;
	}

	@Override
	public List<Map<String, Object>> counselorList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return BbsgomListMapper.counselorList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> counselorBoardList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return BbsgomListMapper.counselorBoardList(mapParam);
	}

	@Override
	public Map<String, Object> saveCounselor(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsCounselorList = dataRequest.getParameterGroup("dsCounselorList");
		

		Iterator<ParameterRow> insertedRows = dsCounselorList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsCounselorList.getUpdatedRows();
		
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
			BbsgomListMapper.insertCounselor(mapIns);
			
			mapReturn.put("INDEX_SN", mapIns.get("INDEX_SN"));
		}
		
		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			BbsgomListMapper.updateCounselor(mapUpd);
			
			mapReturn.put("INDEX_SN", mapUpd.get("INDEX_SN"));
			
		}
		
		return mapReturn;
	}
}

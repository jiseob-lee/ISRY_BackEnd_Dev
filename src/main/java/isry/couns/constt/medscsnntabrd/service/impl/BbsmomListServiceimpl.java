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

import isry.couns.constt.medscsnntabrd.mapper.BbsmomListMapper;
import isry.couns.constt.medscsnntabrd.service.BbsmomListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class BbsmomListServiceimpl implements BbsmomListService{
	
	@Resource(name = "bbsmomListMapper")
	private BbsmomListMapper bbsmomListMapper;

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
		
		return bbsmomListMapper.getTotalCount(mapParam);
	}
	
	@Override//본인상담
	public List<Map<String, Object>> selectBbsmomList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsmomListMapper.selectBbsmomList(mapParam);
	}
	
	@Override//답변완료
	public List<Map<String, Object>> selectBbsmomListY(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsmomListMapper.selectBbsmomListY(mapParam);
	}
	
	@Override//미답변
	public List<Map<String, Object>> selectBbsmomListN(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsmomListMapper.selectBbsmomListN(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectBbsmomDetail(Map<String, Object> mapParam) throws Exception {
		String strCreateYn = (String) mapParam.get("CREATE_YN");
				
		if(!strCreateYn.equals("Y")) {
			// 조회수 추가
			bbsmomListMapper.updateRdcntDtlList(mapParam);
		}
		
		// TODO Auto-generated method stub
		return bbsmomListMapper.selectBbsmomDetail(mapParam);
	}

	@Override//게시글(추가(insertBbsmom), 수정(updateBbsmom), 삭제(deleteBbsmom))
	public Map<String, Object> saveBbsmom(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");

//		Iterator<ParameterRow> insertedRows = dsBoardList.getInsertedRows();
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
		
//		while (insertedRows.hasNext()) {
//
//			Map<String, String> mapIns = insertedRows.next().toMap();
//			mapIns.put("FRST_RGTR_ID", userId);
//			mapIns.put("LAST_MDFR_ID", userId);
//			BbsgomListMapper.insertBbsgom(mapIns);
//			
//			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
//		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbsmomListMapper.updateBbsmom(mapUpd);	
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsmomListMapper.deleteBbsmom(mapDel);		
		}
		
		return mapReturn;
	}
	
	
	
//	---------------------------------------------------답글
	
	@Override//답글(추가(insertRespod), 수정(updateRespod), 삭제(deleteRespod))
	public Map<String, Object> saveBbsmomRply(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsRplyList");
		
//		System.out.println("가자:"+dsBoardList.toString());

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
			mapIns.put("WRTR_NM_ENCPT", dsBoardList.getValue("WRTR_NM_ENCPT"));
			bbsmomListMapper.insertRespod(mapIns);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
			mapReturn.put("RETE_ESNTAL_NO", mapIns.get("RETE_ESNTAL_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbsmomListMapper.updateRespod(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
			mapReturn.put("RETE_ESNTAL_NO", mapUpd.get("RETE_ESNTAL_NO"));
			
//			System.out.println("게시글:"+mapUpd.get("BBSCTT_ESNTAL_NO")+"답글:"+mapUpd.get("RETE_ESNTAL_NO"));
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsmomListMapper.deleteRespod(mapDel);	
		}
		
		return mapReturn;
	}


	@Override
	public List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsmomListMapper.selectRespodDetail(mapParam);
	}

	@Override
	public List<Map<String, Object>> counselorList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsmomListMapper.counselorList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> counselorBoardList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsmomListMapper.counselorBoardList(mapParam);
	}
	
	@Override
	public void insertMemo(Map<String, Object> mapParam) {
		if(bbsmomListMapper.selectMemo(mapParam).size() == 0) {			
			bbsmomListMapper.insertMemo(mapParam);
		}else {
			bbsmomListMapper.updateMemo(mapParam);
		}
	}
	
	@Override
	public Map<String, Object> saveCounselor(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsCounselorList = dataRequest.getParameterGroup("dsCounselorList");
		

		Iterator<ParameterRow> insertedRows = dsCounselorList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsCounselorList.getUpdatedRows();
//		System.out.println("qwqwqwqw::"+insertedRows.hasNext());
//		System.out.println("qwqwqwqw2::"+updatedRows.hasNext());
		
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
			bbsmomListMapper.insertCounselor(mapIns);
			
			mapReturn.put("INDEX_SN", mapIns.get("INDEX_SN"));
		}
		
		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbsmomListMapper.updateCounselor(mapUpd);
			
			mapReturn.put("INDEX_SN", mapUpd.get("INDEX_SN"));
			
		}
		
		return mapReturn;
	}
}

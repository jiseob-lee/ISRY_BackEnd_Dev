/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.unansntabrd.service.impl;


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

import isry.couns.constt.unansntabrd.mapper.BbsopcListMapper;
import isry.couns.constt.unansntabrd.service.BbsopcListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class BbsopcListServiceimpl implements BbsopcListService {


	@Resource(name = "bbsopcListMapper")
	private BbsopcListMapper bbsopcListMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : subOnLoad
	 * @param mapParam
	 * @return
	 * @throws Exception
	 * @작성자     : Kim.seong.gen
	 * @작성일     : 2022. 6. 8. 
	 * @Method설명 :
	 */
	@Override
	public int getTotalCount(Map<String, Object> mapParam) throws Exception {
		
		return bbsopcListMapper.getTotalCount(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectBbsopcList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsopcListMapper.selectBbsopcList(mapParam);
	}
	
	@Override//답변완료
	public List<Map<String, Object>> selectBbsopcListY(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsopcListMapper.selectBbsopcListY(mapParam);
	}
	
	@Override//미답변
	public List<Map<String, Object>> selectBbsopcListN(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsopcListMapper.selectBbsopcListN(mapParam);
	}
	
	@Override
	public void insertCrisis(HttpServletRequest request, DataRequest dataRequest) {
		
		Map<String, Object> mapParam = new HashMap<>();
		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
		//System.out.println("dsBoardList임다::"+dsBoardList.toString());
		
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
		
		mapParam.put("BBSCTT_ESNTAL_NO", dsBoardList.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("CRISIS_TYPE_SE_CD", dsBoardList.getValue("CRISIS_TYPE_SE_CD"));
		mapParam.put("CLIENA_ID", dsBoardList.getValue("USER_ID"));
		mapParam.put("WRTR_NM_ENCPT", dsBoardList.getValue("WRTR_NM_ENCPT"));
		mapParam.put("FRST_RGTR_ID", userId);
		mapParam.put("LAST_MDFR_ID", userId);
		mapParam.put("USER_ID", dsBoardList.getValue("USER_ID"));
		mapParam.put("WORK_YMD", dsBoardList.getValue("WORK_YMD"));
		//System.out.println("mapParam::"+mapParam.toString());
		
		int crisBrdCnt = bbsopcListMapper.selectCrisBrdCnt(mapParam);
		int crisPrsCnt = bbsopcListMapper.selectCrisPrsCnt(mapParam);
		
		//위기상담게시판에 게시글 존재여부 확인
		if(crisBrdCnt > 0) {			
			bbsopcListMapper.updateCrisisBoard(mapParam); //존재시 update
		}else {
			bbsopcListMapper.insertCrisisBoard(mapParam); //미존재시 insert
		}
		
		//위기상담개인에 게시글 존재여부 확인
		if(crisPrsCnt > 0) {
			bbsopcListMapper.updateCrisisPerson(mapParam); //존재시 update
		}else {
			bbsopcListMapper.insertCrisisPerson(mapParam); //미존재시 insert
		}
		bbsopcListMapper.insertCrisis(mapParam);
		
	}


	@Override
	public List<Map<String, Object>> selectBbsopcDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsopcListMapper.selectBbsopcDetail(mapParam);
	}


	@Override
	public Map<String, Object> saveBbsopc(HttpServletRequest request, DataRequest dataRequest) {
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
			bbsopcListMapper.insertBbsopc(mapIns);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);

			bbsopcListMapper.updateBbsopc(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsopcListMapper.deleteBbsopc(mapDel);				
		}
		
		return mapReturn;
	}

//	-----------------------------------------------답글
	@Override
	public Map<String, Object> saveRespod(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsRplyList");
		

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
			bbsopcListMapper.insertRespod(mapIns);

			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
			mapReturn.put("RETE_ESNTAL_NO", mapIns.get("RETE_ESNTAL_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbsopcListMapper.updateRespod(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
			mapReturn.put("RETE_ESNTAL_NO", mapUpd.get("RETE_ESNTAL_NO"));
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsopcListMapper.deleteRespod(mapDel);	
		}
		
		return mapReturn;
	}
	
	@Override
	public List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsopcListMapper.selectRespodDetail(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> counselorList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsopcListMapper.counselorList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> counselorBoardList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsopcListMapper.counselorBoardList(mapParam);
	}
	
	@Override
	public Map<String, Object> saveCounselor(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsCounselorList = dataRequest.getParameterGroup("dsCounselorList");
		

		Iterator<ParameterRow> insertedRows = dsCounselorList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsCounselorList.getUpdatedRows();
		//System.out.println("qwqwqwqw::"+insertedRows.hasNext());
		//System.out.println("qwqwqwqw2::"+updatedRows.hasNext());
		
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
			bbsopcListMapper.insertCounselor(mapIns);
			
			mapReturn.put("INDEX_SN", mapIns.get("INDEX_SN"));
		}
		
		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbsopcListMapper.updateCounselor(mapUpd);
			
			mapReturn.put("INDEX_SN", mapUpd.get("INDEX_SN"));
			
		}
		
		return mapReturn;
	}
}

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

import isry.couns.constt.medscsnntabrd.mapper.BbssoldListMapper;
import isry.couns.constt.medscsnntabrd.service.BbssoldListService;
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

@Service("bbssoldListService")
public class BbssoldListServiceimpl implements BbssoldListService{
	
	@Resource(name = "bbssoldListMapper")
	private BbssoldListMapper bbssoldListMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectBbssoldList(Map<String, Object> mapParam) {
		
		return bbssoldListMapper.selectBbssoldList(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectBbssoldDetail(Map<String, Object> mapParam) {

		return bbssoldListMapper.selectBbssoldDetail(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectBbssoldReplyList(Map<String, Object> mapParam) {

		return bbssoldListMapper.selectBbssoldReplyList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> insertCounselor(Map<String, Object> mapParam) {

		return bbssoldListMapper.insertCounselor(mapParam);
	}

	@Override
	public Map<String, Object> saveBbssoldReply(HttpServletRequest request, DataRequest dataRequest) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsReply = dataRequest.getParameterGroup("dsReply");
		Iterator<ParameterRow> insertedRows = dsReply.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsReply.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsReply.getDeletedRows();
		
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
			mapIns.put("WRTR_NM_ENCPT", dsReply.getValue("WRTR_NM_ENCPT"));
			bbssoldListMapper.insertReply(mapIns);

			// 게시글 번호 키값 셋팅
			mapReturn.put("CMNT_ESNTAL_NO", mapIns.get("CMNT_ESNTAL_NO"));

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("WRTR_NM_ENCPT", dsReply.getValue("WRTR_NM_ENCPT"));
			bbssoldListMapper.updateReply(mapUpd);
			
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbssoldListMapper.deleteReply(mapDel);			
		}

		return mapReturn;
	}

	@Override
	public void bbssoldDtlCnt(Map<String, Object> mapParam) {
		
		bbssoldListMapper.bbssoldDtlCnt(mapParam);
	}

	@Override
	public void deleteBbssold(HttpServletRequest request, DataRequest dataRequest) {

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
		String userId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = null;
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		Iterator<ParameterRow> deletedRows = dsBoardList.getDeletedRows();
		
		Map<String, String> mapDel = deletedRows.next().toMap();
		mapDel.put("LAST_MDFR_ID", userId);
		bbssoldListMapper.deleteBbssold(mapDel);
	}
	
	@Override
	public void insertCrisis(HttpServletRequest request, DataRequest dataRequest) {
		Map<String, Object> mapParam = new HashMap<>();
		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
		
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
		mapParam.put("CLIENA_ID", dsBoardList.getValue("CLIENA_ID"));
		mapParam.put("WRTR_NM_ENCPT", dsBoardList.getValue("WRTR_NM_ENCPT"));
		mapParam.put("FRST_RGTR_ID", userId);
		mapParam.put("LAST_MDFR_ID", userId);
		mapParam.put("USER_ID", dsBoardList.getValue("USER_ID"));
		mapParam.put("WORK_YMD", dsBoardList.getValue("WORK_YMD"));
		
		int crisBrdCnt = bbssoldListMapper.selectCrisBrdCnt(mapParam);
		int crisPrsCnt = bbssoldListMapper.selectCrisPrsCnt(mapParam);
		
		//위기상담게시판에 게시글 존재여부 확인
		if(crisBrdCnt > 0) {			
			bbssoldListMapper.updateCrisisBoard(mapParam); //존재시 update
		}else {
			bbssoldListMapper.insertCrisisBoard(mapParam); //미존재시 insert
		}
		
		//위기상담개인에 게시글 존재여부 확인
		if(crisPrsCnt > 0) {
			bbssoldListMapper.updateCrisisPerson(mapParam); //존재시 update
		}else {
			bbssoldListMapper.insertCrisisPerson(mapParam); //미존재시 insert
		}
		bbssoldListMapper.insertCrisis(mapParam);
	}
	
	@Override
	public int getTotalCount(Map<String, Object> mapParam) {

		return bbssoldListMapper.getTotalCount(mapParam);
	}
	
	@Override
	public void insertMemo(Map<String, Object> mapParam) {
		if(bbssoldListMapper.selectMemo(mapParam).size() == 0) {			
			bbssoldListMapper.insertMemo(mapParam);
		}else {
			bbssoldListMapper.updateMemo(mapParam);
		}
	}
	
	@Override
	public void updateProbmStts(Map<String, Object> mapParam) {
		bbssoldListMapper.updateProbmStts(mapParam);
	}
	
	@Override
	public void saveCounselor(Map<String, Object> mapParam) {
		if(bbssoldListMapper.selectCounselor(mapParam).size() == 0) {			
			bbssoldListMapper.insertCounselor2(mapParam);
		}else {
			bbssoldListMapper.updateCounselor(mapParam);
		}
	}
}

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
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.couns.constt.medscsnntabrd.mapper.BbseumListMapper;
import isry.couns.constt.medscsnntabrd.service.BbseumListService;
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

@Service("bbseumListService")
public class BbseumListServiceimpl implements BbseumListService{
	
	@Resource(name = "bbseumListMapper")
	private BbseumListMapper bbseumListMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> nonRepSelectBbseumList(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbseumListMapper.nonRepSelectBbseumList(mapParam);
	}

	@Override
	public List<Map<String, Object>> repSelectBbseumList(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbseumListMapper.repSelectBbseumList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectBbseumList(Map<String, Object> mapParam) {
		
		return bbseumListMapper.selectBbseumList(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectBbseumDetail(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbseumListMapper.selectBbseumDetail(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbseumListMapper.selectRespodDetail(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> insertCounselor(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbseumListMapper.insertCounselor(mapParam);
	}

	@Override
	public Map<String, Object> saveBbseumList(HttpServletRequest request, DataRequest dataRequest) {
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
		
		String userIp = request.getRemoteAddr();
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			mapIns.put("CNTN_IP_ADDR", userIp);
			bbseumListMapper.insertBbseum(mapIns);

			// 게시글 번호 키값 셋팅
			mapReturn.put("BBSCTT_ESNTAL_NO", bbseumListMapper.selectBrdMaxCnt());

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbseumListMapper.updateBbseum(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));

		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbseumListMapper.deleteBbseum(mapDel);			
			
		}

		return mapReturn;
	}

	@Override
	public Map<String, Object> saveRespod(HttpServletRequest request, DataRequest dataRequest) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsDtlReply = dataRequest.getParameterGroup("dsDtlReply");
		
		if(dsDtlReply.getValue("SCHL_VIOLNC_YN") == null || dsDtlReply.getValue("SCHL_VIOLNC_YN").equals("")) {
			dsDtlReply.setValue(0, "SCHL_VIOLNC_YN", "N");
		}
		if(dsDtlReply.getValue("LABOR_YN") == null || dsDtlReply.getValue("LABOR_YN").equals("")) {
			dsDtlReply.setValue(0, "LABOR_YN", "N");
		}
		if(dsDtlReply.getValue("CYBER_VIOLNC_YN") == null || dsDtlReply.getValue("CYBER_VIOLNC_YN").equals("")) {
			dsDtlReply.setValue(0, "CYBER_VIOLNC_YN", "N");
		}
		
		Iterator<ParameterRow> insertedRows = dsDtlReply.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsDtlReply.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsDtlReply.getDeletedRows();
				
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
//			System.out.println("SSsssss4");
			bbseumListMapper.insertRespod(mapIns);
			
			Map<String, Object> mapAns = new HashMap<String, Object>();
			mapAns.put("BBSCTT_ESNTAL_NO", dsDtlReply.getValue("BBSCTT_ESNTAL_NO"));
			mapAns.put("ANS_CN", dsDtlReply.getValue("RETE_CN"));
			bbseumListMapper.updateBbseumRespod(mapAns);
			// 게시글 번호 키값 셋팅
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbseumListMapper.updateRespod(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbseumListMapper.deleteRespod(mapDel);			
			
		}

		return mapReturn;
	}

	@Override
	public void bbseumDtlCnt(Map<String, Object> mapParam) {
		
		bbseumListMapper.bbseumDtlCnt(mapParam);
	}
	
	@Override
	public void respodDtlCnt(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		bbseumListMapper.respodDtlCnt(mapParam);
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
		mapParam.put("FRST_RGTR_ID", userId);
		mapParam.put("LAST_MDFR_ID", userId);
		mapParam.put("USER_ID", dsBoardList.getValue("USER_ID"));
		mapParam.put("WORK_YMD", dsBoardList.getValue("WORK_YMD"));
		
		int crisBrdCnt = bbseumListMapper.selectCrisBrdCnt(mapParam);
		int crisPrsCnt = bbseumListMapper.selectCrisPrsCnt(mapParam);
		
		//위기상담게시판에 게시글 존재여부 확인
		if(crisBrdCnt > 0) {			
			bbseumListMapper.updateCrisisBoard(mapParam); //존재시 update
		}else {
			bbseumListMapper.insertCrisisBoard(mapParam); //미존재시 insert
		}
		
		//위기상담개인에 게시글 존재여부 확인
		if(crisPrsCnt > 0) {
			bbseumListMapper.updateCrisisPerson(mapParam); //존재시 update
		}else {
			bbseumListMapper.insertCrisisPerson(mapParam); //미존재시 insert
		}
		bbseumListMapper.insertCrisis(mapParam);
	}
	
	@Override
	public int getTotalCount(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbseumListMapper.getTotalCount(mapParam);
	}
	
	@Override
	public void insertMemo(Map<String, Object> mapParam) {
		if(bbseumListMapper.selectMemo(mapParam).size() == 0) {			
			bbseumListMapper.insertMemo(mapParam);
		}else {
			bbseumListMapper.updateMemo(mapParam);
		}
	}
	
	@Override
	public void saveCounselor(Map<String, Object> mapParam) {
		if(bbseumListMapper.selectCounselor(mapParam).size() == 0) {			
			bbseumListMapper.insertCounselor2(mapParam);
		}else {
			bbseumListMapper.updateCounselor(mapParam);
		}
	}
	
	@Override
	public List<Map<String, String>> eumContentList(DataRequest dataRequest) {
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = dmSearch.getSingleValueMap();
		
		return bbseumListMapper.eumContentList(paramMap);
	}
	
	@Override
	public void eumContentInsert(HttpServletRequest request, DataRequest dataRequest) {
//		Map<String, String> mapParam = new HashMap<>();
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		
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
		
		Map<String, String> paramMap = dmSearch.getSingleValueMap();
		
		paramMap.put("FRST_RGTR_ID", userId);
		paramMap.put("LAST_MDFR_ID", userId);
		
		
		bbseumListMapper.insertAYE230(paramMap);
		bbseumListMapper.updateAYE200(paramMap);
	}
	
	@Override
	public void saveMail(HttpServletRequest request, DataRequest dataRequest) {
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmMail");
		Map<String, String> paramMap = dmSearch.getSingleValueMap();
		
		bbseumListMapper.updateMail(paramMap);
		
	}
	

}

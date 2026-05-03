/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbsout.service.impl;

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

import isry.couns.constt.bbsout.mapper.BbsoutListMapper;
import isry.couns.constt.bbsout.service.BbsoutListService;
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

@Service("bbsoutListService")
public class BbsoutListServiceimpl implements BbsoutListService{
	
	@Resource(name = "bbsoutListMapper")
	private BbsoutListMapper bbsoutListMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectBbsoutList(Map<String, Object> mapParam) {
		
		return bbsoutListMapper.selectBbsoutList(mapParam);
	}

	@Override
	public int getTotalCount(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbsoutListMapper.getTotalCount(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectBbsoutDetail(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbsoutListMapper.selectBbsoutDetail(mapParam);
	}

	@Override
	public void bbsoutDtlCnt(Map<String, Object> mapParam) {
		
		bbsoutListMapper.bbsoutDtlCnt(mapParam);
	}

	@Override
	public Map<String, Object> saveBbsoutList(HttpServletRequest request, DataRequest dataRequest) {
		
		String userId = "";
		
		UserDetailsVO loginVO = null;
		
		try {
			loginVO = userLoginService.getLoginSessionVO(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
		
		Iterator<ParameterRow> insertedRows = dsBoardList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsBoardList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsBoardList.getDeletedRows();
		
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		
		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			mapIns.put("WRTR_NM_ENCPT", dsBoardList.getValue("WRTR_NM_ENCPT"));
			
			bbsoutListMapper.insertBbsout(mapIns);
			
			// 게시글 번호 키값 셋팅
			mapReturn.putAll(mapIns);

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("WRTR_NM_ENCPT", dsBoardList.getValue("WRTR_NM_ENCPT"));
			
			bbsoutListMapper.updateBbsout(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));

		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			
			bbsoutListMapper.deleteBbsout(mapDel);			
			
		}

		return mapReturn;
	}

	@Override
	public void insertCrisis(HttpServletRequest request, DataRequest dataRequest) {
		Map<String, Object> mapParam = new HashMap<>();
		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList"); 
//		System.out.println("DDD : "+dsBoardList.toString());
		
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
//		System.out.println("DDDDD : "+mapParam.toString());
//		int crisBrdCnt = bbsoutListMapper.selectCrisBrdCnt(mapParam);
//		int crisPrsCnt = bbsoutListMapper.selectCrisPrsCnt(mapParam);
//		
//		//위기상담게시판에 게시글 존재여부 확인
//		if(crisBrdCnt > 0) {			
//			bbsoutListMapper.updateCrisisBoard(mapParam); //존재시 update
//		}else {
			bbsoutListMapper.insertCrisisBoard(mapParam); //미존재시 insert
//		}
		
		//위기상담개인에 게시글 존재여부 확인
//		if(crisPrsCnt > 0) {
//			bbsoutListMapper.updateCrisisPerson(mapParam); //존재시 update
//		}else {
			bbsoutListMapper.insertCrisisPerson(mapParam); //미존재시 insert
//		}
		bbsoutListMapper.insertCrisis(mapParam);
	}

}

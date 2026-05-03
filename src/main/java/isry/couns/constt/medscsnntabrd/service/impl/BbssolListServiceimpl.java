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

import isry.couns.cmmn.service.CounsService;
import isry.couns.constt.medscsnntabrd.mapper.BbssolListMapper;
import isry.couns.constt.medscsnntabrd.service.BbssolListService;
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

@Service("bbssolListService")
public class BbssolListServiceimpl implements BbssolListService{
	
	@Resource(name = "bbssolListMapper")
	private BbssolListMapper bbssolListMapper;
	
	@Resource(name = "counsService")
	private CounsService counsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> nonRepSelectBbssolList(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbssolListMapper.nonRepSelectBbssolList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> repSelectBbssolList(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbssolListMapper.repSelectBbssolList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectBbssolList(Map<String, Object> mapParam) {
		
		return bbssolListMapper.selectBbssolList(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectBbssolDetail(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbssolListMapper.selectBbssolDetail(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbssolListMapper.selectRespodDetail(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> insertCounselor(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbssolListMapper.insertCounselor(mapParam);
	}

	@Override
	public Map<String, Object> saveBbssolList(HttpServletRequest request, DataRequest dataRequest) {
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
			mapIns.put("WRTR_NM_ENCPT", dsBoardList.getValue("WRTR_NM_ENCPT"));
			mapIns.put("EML_ADDR_ENCPT", dsBoardList.getValue("EML_ADDR_ENCPT"));
			bbssolListMapper.insertBbssol(mapIns);

			// 게시글 번호 키값 셋팅
			mapReturn.put("BBSCTT_ESNTAL_NO", bbssolListMapper.selectBrdMaxCnt());

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("WRTR_NM_ENCPT", dsBoardList.getValue("WRTR_NM_ENCPT"));
			mapUpd.put("EML_ADDR_ENCPT", dsBoardList.getValue("EML_ADDR_ENCPT"));
			bbssolListMapper.updateBbssol(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));

		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbssolListMapper.deleteBbssol(mapDel);			
			
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
			mapIns.put("WRTR_NM_ENCPT", dsDtlReply.getValue("WRTR_NM_ENCPT"));
			bbssolListMapper.insertRespod(mapIns);
			
			Map<String, Object> mapAns = new HashMap<String, Object>();
			mapAns.put("BBSCTT_ESNTAL_NO", dsDtlReply.getValue("BBSCTT_ESNTAL_NO"));
			mapAns.put("ANS_CN", dsDtlReply.getValue("RETE_CN"));
			bbssolListMapper.updateBbssolRespod(mapAns);
			// 게시글 번호 키값 셋팅
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("WRTR_NM_ENCPT", dsDtlReply.getValue("WRTR_NM_ENCPT"));
			bbssolListMapper.updateRespod(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbssolListMapper.deleteRespod(mapDel);			
			
		}

		return mapReturn;
	}

	@Override
	public void bbssolDtlCnt(Map<String, Object> mapParam) {
		
		bbssolListMapper.bbssolDtlCnt(mapParam);
	}
	
	@Override
	public void respodDtlCnt(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		bbssolListMapper.respodDtlCnt(mapParam);
	}

	@Override
	public void insertCrisis(HttpServletRequest request, DataRequest dataRequest) {
		Map<String, Object> mapParam = new HashMap<>();
		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
//System.out.println("DDD : "+dsBoardList.toString());		
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
		
		mapParam.put("CNSLTNT_NM_ENCPT", dsBoardList.getValue("CNSLTNT_NM_ENCPT"));
		mapParam.put("USER_ID", dsBoardList.getValue("USER_ID"));
		mapParam.put("WORK_YMD", dsBoardList.getValue("REG_DT"));
		mapParam.put("BBSCTT_ESNTAL_NO", dsBoardList.getValue("BBSCTT_ESNTAL_NO"));
		mapParam.put("CRISIS_TYPE_SE_CD", dsBoardList.getValue("CRISIS_TYPE_SE_CD"));
		mapParam.put("CLIENA_ID", dsBoardList.getValue("CLIENA_ID"));
		mapParam.put("WRTR_NM_ENCPT", dsBoardList.getValue("WRTR_NM_ENCPT"));
		mapParam.put("FRST_RGTR_ID", userId);
		mapParam.put("LAST_MDFR_ID", userId);
		mapParam.put("CNSLTNT_NM_ENCPT", dsBoardList.getValue("CNSLTNT_NM_ENCPTW"));

	
//System.out.println("DDD : insertCrisis : 001 : \n ");		
		int crisBrdCnt = bbssolListMapper.selectCrisBrdCnt(mapParam);
		int crisPrsCnt = bbssolListMapper.selectCrisPrsCnt(mapParam);
		
		//위기상담게시판에 게시글 존재여부 확인
		if(crisBrdCnt > 0) {			
//			System.out.println("DDD : insertCrisis : 002 : \n ");		
			bbssolListMapper.updateCrisisBoard(mapParam); //존재시 update
		}else {
//			System.out.println("DDD : insertCrisis : 003 : \n ");		
			bbssolListMapper.insertCrisisBoard(mapParam); //미존재시 insert
		}
		
//		System.out.println("DDD : insertCrisis : 004 : \n ");		
		//위기상담개인에 게시글 존재여부 확인
		if(crisPrsCnt > 0) {
//			System.out.println("DDD : insertCrisis : 005 : \n ");		
			bbssolListMapper.updateCrisisPerson(mapParam); //존재시 update
		}else {
//			System.out.println("DDD : insertCrisis : 006 : \n ");		
			bbssolListMapper.insertCrisisPerson(mapParam); //미존재시 insert
		}
//		System.out.println("DDD : insertCrisis : 007 : \n ");		
		bbssolListMapper.insertCrisis(mapParam);
		
		bbssolListMapper.updateBbssolDetail(mapParam);

		

	}
	
	@Override
	public int getTotalCount(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbssolListMapper.getTotalCount(mapParam);
	}
	
	@Override
	public void insertMemo(Map<String, Object> mapParam) {
		if(bbssolListMapper.selectMemo(mapParam).size() == 0) {			
			bbssolListMapper.insertMemo(mapParam);
		}else {
			bbssolListMapper.updateMemo(mapParam);
		}
	}
	
	@Override
	public void updateSupvSlctnCaseYn(Map<String, Object> mapParam) {
		
		bbssolListMapper.updateSupvSlctnCaseYn(mapParam);	
	}
	
	@Override
	public void saveCounselor(Map<String, Object> mapParam) {
		if(bbssolListMapper.selectCounselor(mapParam).size() == 0) {			
			bbssolListMapper.insertCounselor2(mapParam);
		}else {
			bbssolListMapper.updateCounselor(mapParam);
		}
	}
	
	@Override
	public List<Map<String, String>> ssolContentList(DataRequest dataRequest) {
		ParameterGroup dmSearch = dataRequest.getParameterGroup("dmSearch");
		Map<String, String> paramMap = dmSearch.getSingleValueMap();
		
		return bbssolListMapper.solContentList(paramMap);
	}
	
	@Override
	public void ssolContentInsert(HttpServletRequest request, DataRequest dataRequest) {
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
		
		
		bbssolListMapper.insertAYE310(paramMap);
		bbssolListMapper.updateAYE300(paramMap);
	}
	
	@Override
	public List<Map<String, Object>> selectMemo(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbssolListMapper.selectMemo(mapParam);
	}
	
	@Override
	public Map<String, Object> saveBbssol(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList	= dataRequest.getParameterGroup("dsBoardList");
		ParameterGroup dsDtlReply	= dataRequest.getParameterGroup("dsDtlReply");
		
		// 메인 글
		Iterator<ParameterRow> insertedRows	= dsBoardList.getInsertedRows();
		Iterator<ParameterRow> updatedRows	= dsBoardList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows	= dsBoardList.getDeletedRows();
		
		// 답글
		Iterator<ParameterRow> insertedRepRows	= dsDtlReply.getInsertedRows();
		Iterator<ParameterRow> updatedRepRows	= dsDtlReply.getUpdatedRows();
		Iterator<ParameterRow> deletedRepRows	= dsDtlReply.getDeletedRows();

/* 
 * 2023.04.12 수정 : 메모 Insert/Update/Delete의 경우 New 서브미션(subInsertMemo) 생성 
 */
//		// 메모
//		ParameterGroup dsMemoCn		= dataRequest.getParameterGroup("dsMemoCn");
//		Iterator<ParameterRow> insertedMemoRows	= dsMemoCn.getInsertedRows();
//		Iterator<ParameterRow> updatedMemoRows	= dsMemoCn.getUpdatedRows();
//		Iterator<ParameterRow> deletedMemoRows	= dsMemoCn.getDeletedRows();
				
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
		
		// [s] 메인 글
		while (insertedRows.hasNext()) {
			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID"	, userId);
			mapIns.put("LAST_MDFR_ID"	, userId);
			mapIns.put("WRTR_NM_ENCPT"	, dsBoardList.getValue("WRTR_NM_ENCPT"));
			mapIns.put("EML_ADDR_ENCPT"	, dsBoardList.getValue("EML_ADDR_ENCPT"));
			
			bbssolListMapper.insertBbssol(mapIns);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", bbssolListMapper.selectBrdMaxCnt());
		}

		while (updatedRows.hasNext()) {
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID"	, userId);
			mapUpd.put("WRTR_NM_ENCPT"	, dsBoardList.getValue("WRTR_NM_ENCPT"));
			mapUpd.put("EML_ADDR_ENCPT"	, dsBoardList.getValue("EML_ADDR_ENCPT"));
			
			bbssolListMapper.updateBbssol(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
		}

		while (deletedRows.hasNext()) {			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			
			bbssolListMapper.deleteBbssol(mapDel);						
		}
		// [e] 메인 글
		
		
		// [s] 답글
		while (insertedRepRows.hasNext()) {
			Map<String, String> mapIns = insertedRepRows.next().toMap();			
			mapIns.put("FRST_RGTR_ID"	, userId);
			mapIns.put("LAST_MDFR_ID"	, userId);
			mapIns.put("WRTR_NM_ENCPT"	, mapIns.get("WRTR_NM_ENCPT"));
			
			// 답글 insert
			if (!"".equals(mapIns.get("RETE_TTL_NM")) || !"".equals(mapIns.get("RETE_CN"))) {
				int result = bbssolListMapper.insertRespod(mapIns);
				
				if (result == 1) {
					// CounsService Method 호출
					counsService.processAnsCmptnAutoSndng(request, dataRequest, mapIns);
				}
			}
			
			Map<String, Object> mapAns = new HashMap<String, Object>();
			mapAns.put("BBSCTT_ESNTAL_NO"	, dsDtlReply.getValue("BBSCTT_ESNTAL_NO"));
			mapAns.put("ANS_CN"				, dsDtlReply.getValue("RETE_CN"));
			
			// 메인 게시글에 답글 +1 및 입력 내용 업데이트
			bbssolListMapper.updateBbssolRespod(mapAns);

			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));

		}

		while (updatedRepRows.hasNext()) {
			Map<String, String> mapUpd = updatedRepRows.next().toMap();			
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("WRTR_NM_ENCPT", dsDtlReply.getValue("WRTR_NM_ENCPT"));
			
			bbssolListMapper.updateRespod(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
		}

		while (deletedRepRows.hasNext()) {
			Map<String, String> mapDel = deletedRepRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbssolListMapper.deleteRespod(mapDel);
		}
		// [e] 답글
		
/* 
 * 2023.04.12 수정 : 메모 Insert/Update/Delete의 경우 New 서브미션(subInsertMemo) 생성 
 */	
//		// [s] 메모
//		while (insertedMemoRows.hasNext()) {			
//			Map<String, String> mapIns		= insertedMemoRows.next().toMap();
//			Map<String, Object> mapParam	= new HashMap<String, Object>();
//			mapParam.put("BBSCTT_ESNTAL_NO"	, mapIns.get("BBSCTT_ESNTAL_NO"));
//			mapParam.put("BBSCTT_TYPE_SE_CD", "01");
//			mapParam.put("CONSTT_ID"		, mapIns.get("CONSTT_ID"));
//			mapParam.put("MEMO_NM"			, mapIns.get("MEMO_NM"));			
//			mapParam.put("FRST_RGTR_ID"		, userId);
//			mapParam.put("LAST_MDFR_ID"		, userId);
//			
//			// 답글 insert
//			bbssolListMapper.insertMemo(mapParam);
//		}
//
//		while (updatedMemoRows.hasNext()) {
//			Map<String, String> mapUpd = updatedMemoRows.next().toMap();
//			Map<String, Object> mapParam	= new HashMap<String, Object>();
//			mapParam.put("BBSCTT_ESNTAL_NO"	, mapUpd.get("BBSCTT_ESNTAL_NO"));
//			mapParam.put("BBSCTT_TYPE_SE_CD", "01");
//			mapParam.put("CONSTT_ID"		, mapUpd.get("CONSTT_ID"));
//			mapParam.put("MEMO_NM"			, mapUpd.get("MEMO_NM"));			
//			mapParam.put("FRST_RGTR_ID"		, userId);
//			mapParam.put("LAST_MDFR_ID"		, userId);
//			
//			bbssolListMapper.updateMemo(mapParam);
//			
//			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
//		}
//
//		while (deletedMemoRows.hasNext()) {
//			Map<String, String> mapDel = deletedMemoRows.next().toMap();
//			mapDel.put("BBSCTT_TYPE_SE_CD", "01");
//			mapDel.put("LAST_MDFR_ID", userId);
//			bbssolListMapper.deleteMemo(mapDel);
//		}
//		// [e] 메모

		return mapReturn;
	}

	/**
	 * @Method명   : updateCase
	 * @param 	   : mapParam
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 4. 12. 
	 * @Method설명 : 슈퍼비전 사례 선정 처리
	 */
	@Override
	public void updateCase(Map<String, Object> mapParam) {
		
		bbssolListMapper.updateCase(mapParam);
	}
}

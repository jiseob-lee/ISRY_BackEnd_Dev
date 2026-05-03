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
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import isry.couns.cmmn.service.CounsService;
import isry.couns.constt.etcntabrd.mapper.BbsEpilogoListMapper;
import isry.couns.constt.etcntabrd.service.BbsEpilogoListService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.redis.service.RedisService;


@Service
public class BbsEpilogoListServiceimpl implements BbsEpilogoListService {


	@Resource(name = "bbsEpilogoListMapper")
	private BbsEpilogoListMapper bbsEpilogoListMapper;
	
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
		
		return bbsEpilogoListMapper.getTotalCount(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectBbsEpilogoList(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsEpilogoListMapper.selectBbsEpilogoList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectBbsEpilogoList1(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsEpilogoListMapper.selectBbsEpilogoList1(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectBbsEpilogoList2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsEpilogoListMapper.selectBbsEpilogoList2(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectBbsEpilogoDetail(Map<String, Object> mapParam) throws Exception {
		String strCreateYn = (String) mapParam.get("CREATE_YN");
		
		if(!strCreateYn.equals("Y")) {

//			System.out.println("rrrrrr::"+mapParam.get("BBSCTT_ESNTAL_NO"));
			// 조회수 추가
			bbsEpilogoListMapper.updateRdcntDtlList(mapParam);
		}
		return bbsEpilogoListMapper.selectBbsEpilogoDetail(mapParam);
	}
	
	@Override//게시글(등록,수정,삭제)
	public Map<String, Object> saveBbsEpilogoProc(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");

//		System.out.println("saveBbsEpilogoProc:::::::::::::::::::::::::" + dsBoardList.toString());
		
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
			
//			System.out.println("insertedRows:::::::::::::::::::::::::");
			
			dsBoardList.setValue(0, "WRTR_NM_ENCPT", dsBoardList.getValue("WRTR_NM_ENCPT"));
			
			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);

			bbsEpilogoListMapper.insertbbsRespodEpilogo(mapIns);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));

		}

		while (updatedRows.hasNext()) {
			
//			System.out.println("updatedRows:::::::::::::::::::::::::");

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbsEpilogoListMapper.updateBbsEpilogo(mapUpd);	
			int updateResult = bbsEpilogoListMapper.updateBbsEpilogo160(mapUpd);	
			
//			System.out.println("updatedRows:::::::::::::::::::::::::" + updateResult);
			if(updateResult == 0) {
				mapUpd.put("FRST_RGTR_ID", userId);
				bbsEpilogoListMapper.insertbbsRespodEpilogo160(mapUpd);
			}
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
			
		}

		while (deletedRows.hasNext()) {
			
//			System.out.println("deletedRows:::::::::::::::::::::::::");
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsEpilogoListMapper.deleteBbsEpilogo(mapDel);				
		}
		return mapReturn;
	}	
	
	@Override
	public List<Map<String, Object>> selectBbsEpilogoRplyDetail(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsEpilogoListMapper.selectBbsEpilogoRplyDetail(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectBbsEpilogoRplyDetail2(Map<String, Object> mapParam) throws Exception {
		// TODO Auto-generated method stub
		return bbsEpilogoListMapper.selectBbsEpilogoRplyDetail2(mapParam);
	}
	
	@Override//답글(등록,수정,삭제)
	public Map<String, Object> saveBbsEpilogoReProc(HttpServletRequest request, DataRequest dataRequest) {
		// TODO Auto-generated method stub
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsRplyList = dataRequest.getParameterGroup("dsRplyList");

		Iterator<ParameterRow> insertedRows = dsRplyList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsRplyList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsRplyList.getDeletedRows();		
		
//		System.out.println("insertedRows::"+insertedRows.hasNext());
		
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

			bbsEpilogoListMapper.insertBbsRespodEpilogo(mapIns);
			
			mapReturn.put("RETE_ESNTAL_NO", mapIns.get("RETE_ESNTAL_NO"));
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			bbsEpilogoListMapper.updateBbsRespodEpilogo(mapUpd);
			bbsEpilogoListMapper.updateBbsRespodEpilogo100(mapUpd);	
			
			mapReturn.put("RETE_ESNTAL_NO", mapUpd.get("RETE_ESNTAL_NO"));
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
			
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsEpilogoListMapper.deleteBbsRespodEpilogo(mapDel);				
		}
		return mapReturn;
	}
	
	@Override
	public List<Map<String, Object>> insertCounselor(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbsEpilogoListMapper.insertCounselor(mapParam);
	}
	
	@Override
	public void saveCounselor(Map<String, Object> mapParam) {
		if(bbsEpilogoListMapper.selectCounselor(mapParam).size() == 0) {			
			bbsEpilogoListMapper.insertCounselor2(mapParam);
		}else {
			bbsEpilogoListMapper.updateCounselor(mapParam);
		}
	}
	
	@Override
	public List<Map<String, Object>> selectMemo(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbsEpilogoListMapper.selectMemo(mapParam);
	}
	
	@Override
	public Map<String, Object> saveBbsEpilogoAll(HttpServletRequest request, DataRequest dataRequest) throws Exception{
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		
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

		ParameterGroup dsBoardList	= dataRequest.getParameterGroup("dsBoardList");
		ParameterGroup dsRplyList	= dataRequest.getParameterGroup("dsRplyList");
		
		// 메인 글
		Iterator<ParameterRow> updatedRows	= dsBoardList.getUpdatedRows();
		
		// 답글
		Iterator<ParameterRow> insertedRepRows	= dsRplyList.getInsertedRows();
		Iterator<ParameterRow> updatedRepRows	= dsRplyList.getUpdatedRows();
		Iterator<ParameterRow> deletedRepRows	= dsRplyList.getDeletedRows();
		
		// [s] 메인 글
		while (updatedRows.hasNext()) {
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID"	, userId);
			
			bbsEpilogoListMapper.updateBbsEpilogo(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
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
				int result = bbsEpilogoListMapper.insertBbsRespodEpilogo(mapIns);
				
				if (result == 1) {
					// CounsService Method 호출
					counsService.processAnsCmptnAutoSndng(request, dataRequest, mapIns);
				}
			}
			
			Map<String, Object> mapAns = new HashMap<String, Object>();
			mapAns.put("BBSCTT_ESNTAL_NO"	, mapIns.get("BBSCTT_ESNTAL_NO"));
			mapAns.put("ANS_CN"				, mapIns.get("RETE_CN"));
			
			// 메인 게시글에 답글 +1 및 입력 내용 업데이트
			bbsEpilogoListMapper.updateBbsEpilogoRespod(mapAns);
			

			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));

		}

		while (updatedRepRows.hasNext()) {
			Map<String, String> mapUpd = updatedRepRows.next().toMap();			
			mapUpd.put("LAST_MDFR_ID", userId);
			
			bbsEpilogoListMapper.updateBbsRespodEpilogo(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
		}

		while (deletedRepRows.hasNext()) {
			Map<String, String> mapDel = deletedRepRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsEpilogoListMapper.deleteBbsRespodEpilogo(mapDel);
		}
		// [e] 답글

		return mapReturn;
	}

	/**
	 * @Method명   : insertEpilgMemo
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 4. 26. 
	 * @Method설명 : 사이버상담후기 메모 저장/수정
	 */
	@Override
	public void insertEpilgMemo(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		String loginId = "";			// session ID
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		} else {
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsBoardList");
		Map<String, String> updateParam = paramGroup.getUpdatedRowList().get(0);
		
		updateParam.put("loginId", loginId);
		
		bbsEpilogoListMapper.processEpilgMemo(updateParam);
	}

}

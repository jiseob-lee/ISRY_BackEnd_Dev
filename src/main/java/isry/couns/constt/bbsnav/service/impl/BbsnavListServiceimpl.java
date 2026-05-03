/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.bbsnav.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.couns.constt.bbsnav.mapper.BbsnavListMapper;
import isry.couns.constt.bbsnav.service.BbsnavListService;
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

@Service("bbsnavListService")
public class BbsnavListServiceimpl implements BbsnavListService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "bbsnavListMapper")
	private BbsnavListMapper bbsnavListMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectBbsnavList(Map<String, Object> mapParam) {
		
		return bbsnavListMapper.selectBbsnavList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> nonRepSelectBbsnavList(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbsnavListMapper.nonRepSelectBbsnavList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> repSelectBbsnavList(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbsnavListMapper.repSelectBbsnavList(mapParam);
	}

	@Override
	public int getTotalCount(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbsnavListMapper.getTotalCount(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectBbsnavDetail(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbsnavListMapper.selectBbsnavDetail(mapParam);
	}

	@Override
	public void bbsnavDtlCnt(Map<String, Object> mapParam) {
		
		bbsnavListMapper.bbsnavDtlCnt(mapParam);
	}

	@Override
	public Map<String, Object> saveBbsnavList(HttpServletRequest request, DataRequest dataRequest) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		// 메인 글
		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
		Iterator<ParameterRow> insertedRows = dsBoardList.getInsertedRows();
		Iterator<ParameterRow> updatedRows = dsBoardList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows = dsBoardList.getDeletedRows();
				
		// 답글
		ParameterGroup dsDtlReply	= dataRequest.getParameterGroup("dsDtlReply");
//		System.out.println("답글 존재 ::: " + dsDtlReply);
//		System.out.println("답글 rowSize ::: " + dsDtlReply.rowSize());
		
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
			
			bbsnavListMapper.insertBbsnav(mapIns);
			
			// 게시글 번호 키값 셋팅
			mapReturn.putAll(mapIns);
		}

		while (updatedRows.hasNext()) {
			Map<String, String> mapUpd = updatedRows.next().toMap();
			
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("WRTR_NM_ENCPT", dsBoardList.getValue("WRTR_NM_ENCPT"));
			
			bbsnavListMapper.updateBbsnav(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
		}

		while (deletedRows.hasNext()) {
			Map<String, String> mapDel = deletedRows.next().toMap();
			
			mapDel.put("LAST_MDFR_ID", userId);
			mapDel.put("BBSCTT_TYPE_SE_CD", dsBoardList.getValue("BBSCTT_TYPE_SE_CD"));
			
			bbsnavListMapper.deleteBbsnav(mapDel);			
		}
		
		// [s] 답글
		if (dsDtlReply.rowSize() > 0) {
			Iterator<ParameterRow> insertedRepRows	= dsDtlReply.getInsertedRows();
			Iterator<ParameterRow> updatedRepRows	= dsDtlReply.getUpdatedRows();
			Iterator<ParameterRow> deletedRepRows	= dsDtlReply.getDeletedRows();
			
			while (insertedRepRows.hasNext()) {
				Map<String, String> mapIns = insertedRepRows.next().toMap();	
				
//				System.out.println("RETE_CN ::: " + mapIns.get("RETE_CN"));
				
				if(!"".equals(mapIns.get("RETE_CN"))) {
					mapIns.put("FRST_RGTR_ID"	, userId);
					mapIns.put("LAST_MDFR_ID"	, userId);
					
					// 답글 insert
					bbsnavListMapper.insertRespod(mapIns);
					
					mapReturn.put("RETE_ESNTAL_NO", mapIns.get("RETE_ESNTAL_NO"));
					mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));
				}

			}

			while (updatedRepRows.hasNext()) {
				Map<String, String> mapUpd = updatedRepRows.next().toMap();		
				
				mapUpd.put("LAST_MDFR_ID", userId);
				
				bbsnavListMapper.updateRespod(mapUpd);
				
				mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
			}

			while (deletedRepRows.hasNext()) {
				Map<String, String> mapDel = deletedRepRows.next().toMap();
				
				mapDel.put("LAST_MDFR_ID", userId);
				
				bbsnavListMapper.deleteRespod(mapDel);
			}
		}
		// [e] 답글

		return mapReturn;
	}

	@Override
	public List<Map<String, Object>> selectSulmun(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbsnavListMapper.selectSulmun(mapParam);
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
		
		int crisBrdCnt = bbsnavListMapper.selectCrisBrdCnt(mapParam);
		int crisPrsCnt = bbsnavListMapper.selectCrisPrsCnt(mapParam);
		
		//위기상담게시판에 게시글 존재여부 확인
		if(crisBrdCnt > 0) {			
			bbsnavListMapper.updateCrisisBoard(mapParam); //존재시 update
		}else {
			bbsnavListMapper.insertCrisisBoard(mapParam); //미존재시 insert
		}
		
		//위기상담개인에 게시글 존재여부 확인
		if(crisPrsCnt > 0) {
			bbsnavListMapper.updateCrisisPerson(mapParam); //존재시 update
		}else {
			bbsnavListMapper.insertCrisisPerson(mapParam); //미존재시 insert
		}
		bbsnavListMapper.insertCrisis(mapParam);
	}

	@Override
	public void updateCase(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		bbsnavListMapper.updateCase(mapParam);
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
			bbsnavListMapper.insertRespod(mapIns);
			
			Map<String, Object> mapAns = new HashMap<String, Object>();
			mapAns.put("BBSCTT_ESNTAL_NO", dsDtlReply.getValue("BBSCTT_ESNTAL_NO"));
			mapAns.put("ANS_CN", dsDtlReply.getValue("RETE_CN"));
			bbsnavListMapper.updateBbsnavRespod(mapAns);
			// 게시글 번호 키값 셋팅
			mapReturn.put("RETE_ESNTAL_NO", mapIns.get("RETE_ESNTAL_NO"));
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("WRTR_NM_ENCPT", dsDtlReply.getValue("WRTR_NM_ENCPT"));
			bbsnavListMapper.updateRespod(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
			mapReturn.put("RETE_ESNTAL_NO", mapUpd.get("RETE_ESNTAL_NO"));
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbsnavListMapper.deleteRespod(mapDel);			
			
		}

		return mapReturn;
	}

	@Override
	public void RespodDtlCnt(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		bbsnavListMapper.RespodDtlCnt(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbsnavListMapper.selectRespodDetail(mapParam);
	}

	/**
	 * @Method명   : selectCounselorList
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @return	   : List
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 : 선택한 일자에 대한 근무자 리스트 출력
	 */
	@Override
	public List<Map<String, Object>> selectCounselorList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmCouns");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return bbsnavListMapper.selectCounselorList(paramMap);
	}

	/**
	 * @Method명   : insertCounselor
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 :
	 */
	@Override
	public void insertCounselor(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String loginId = "";			// session ID
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		} else {
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSaveCounselor");
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		paramMap.put("loginId", loginId);
		
		bbsnavListMapper.processCounselor(paramMap);
	}

	/**
	 * @Method명   : updateReteDlivCmptn
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 : 기타상담게시판 - 카카오톡 오픈채팅 : 내담자에게 답글전달 확인 여부 UPDATE
	 */
	@Override
	public void updateReteDlivCmptn(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		String loginId = "";			// session ID
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		} else {
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsBoardList");
		Iterator<ParameterRow> updateRow = paramGroup.getUpdatedRows();
		
		while (updateRow.hasNext()) {
			Map<String, String> mapUpd = updateRow.next().toMap();
			
			mapUpd.put("loginId", loginId);
			LOGGER.debug("mapUpd ::: " + mapUpd);
			
			bbsnavListMapper.updateReteDlivCmptn(mapUpd);
		}
	}

}

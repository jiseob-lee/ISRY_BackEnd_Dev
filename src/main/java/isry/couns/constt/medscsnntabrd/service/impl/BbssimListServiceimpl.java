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
import isry.couns.cmmn.service.CounsService;
import isry.couns.constt.medscsnntabrd.mapper.BbssimListMapper;
import isry.couns.constt.medscsnntabrd.service.BbssimListService;
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

@Service("bbssimListService")
public class BbssimListServiceimpl implements BbssimListService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "bbssimListMapper")
	private BbssimListMapper bbssimListMapper;
	
	@Resource(name = "counsService")
	private CounsService counsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectBbssimList(Map<String, Object> mapParam) {
		
		return bbssimListMapper.selectBbssimList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectBbssimList1(Map<String, Object> mapParam) {
		
		return bbssimListMapper.selectBbssimList1(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectBbssimList2(Map<String, Object> mapParam) {
		
		return bbssimListMapper.selectBbssimList2(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectBbssimDetail(Map<String, Object> mapParam) {

		return bbssimListMapper.selectBbssimDetail(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectBbssimDetailEum(Map<String, Object> mapParam) {

		return bbssimListMapper.selectBbssimDetailEum(mapParam);
	}

	/**
	 * @Method명   : selectEumMailDetail
	 * @param 	   : mapParam
	 * @return	   : Map
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 1. 31. 
	 * @Method설명 : 발송할 메일 정보 조회(이음-e 마지막 댓글 + 이음-e 정보)
	 */
	@Override
	public Map<String, Object> selectEumMailDetail(Map<String, Object> mapParam) {
		
		return bbssimListMapper.selectEumMailDetail(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> selectBbssimReplyList(Map<String, Object> mapParam) {

		return bbssimListMapper.selectBbssimReplyList(mapParam);
	}

	@Override
	public List<Map<String, Object>> insertCounselor(Map<String, Object> mapParam) {

		return bbssimListMapper.insertCounselor(mapParam);
	}

	@Override
	public Map<String, Object> saveBbssimReply(HttpServletRequest request, DataRequest dataRequest) throws Exception {
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
		String userIp = "";
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			userIp = loginVO.getIp();
		}
		
		while (insertedRows.hasNext()) {

			Map<String, String> mapIns = insertedRows.next().toMap();
			
			String bbscttTypeSeCd = mapIns.get("BBSCTT_TYPE_SE_CD");
			
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			mapIns.put("CNTN_IP_ADDR", userIp);
			
//			System.out.println("mapIns ::::::::" + mapIns.toString());
			
			int result = bbssimListMapper.insertReply(mapIns);
			
			if ("02".equals(bbscttTypeSeCd) || "04".equals(bbscttTypeSeCd) || "14".equals(bbscttTypeSeCd)) {
				if (result == 1) {
					// CounsService Method 호출
					counsService.processAnsCmptnAutoSndng(request, dataRequest, mapIns);
				}
			}

			// 게시글 번호 키값 셋팅
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));

		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("CNTN_IP_ADDR", userIp);
			bbssimListMapper.updateReply(mapUpd);

		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbssimListMapper.deleteReply(mapDel);
		}

		return mapReturn;
	}

	@Override
	public void bbssimDtlCnt(Map<String, Object> mapParam) {
		
		bbssimListMapper.bbssimDtlCnt(mapParam);
	}

	@Override
	public void deleteBbssim(HttpServletRequest request, DataRequest dataRequest) {

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
		bbssimListMapper.deleteBbssim(mapDel);
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
		
		int crisBrdCnt = bbssimListMapper.selectCrisBrdCnt(mapParam);
		int crisPrsCnt = bbssimListMapper.selectCrisPrsCnt(mapParam);
		
		//위기상담게시판에 게시글 존재여부 확인
		if(crisBrdCnt > 0) {			
			bbssimListMapper.updateCrisisBoard(mapParam); //존재시 update
		}else {
			bbssimListMapper.insertCrisisBoard(mapParam); //미존재시 insert
		}
		
		//위기상담개인에 게시글 존재여부 확인
		if(crisPrsCnt > 0) {
			bbssimListMapper.updateCrisisPerson(mapParam); //존재시 update
		}else {
			bbssimListMapper.insertCrisisPerson(mapParam); //미존재시 insert
		}
		bbssimListMapper.insertCrisis(mapParam);
	}
	
	@Override
	public int getTotalCount(Map<String, Object> mapParam) {

		return bbssimListMapper.getTotalCount(mapParam);
	}
	
	@Override
	public void insertMemo(Map<String, Object> mapParam) {
		if(bbssimListMapper.selectMemo(mapParam).size() == 0) {			
			bbssimListMapper.insertMemo(mapParam);
		}else {
			bbssimListMapper.updateMemo(mapParam);
		}
	}
	
	@Override
	public void updateProbmStts(Map<String, Object> mapParam) {
		bbssimListMapper.updateProbmStts(mapParam);
	}
	
	@Override
	public void saveCounselor(Map<String, Object> mapParam) {
		if(bbssimListMapper.selectCounselor(mapParam).size() == 0) {			
			bbssimListMapper.insertCounselor2(mapParam);
		}else {
			bbssimListMapper.updateCounselor(mapParam);
		}
	}
	
	@Override
	public List<Map<String, Object>> selectBbscttTypeSeCd(String codeId) throws Exception {
		if (codeId == null) {
			return null;
		}
		return bbssimListMapper.selectBbscttTypeSeCd(codeId);
	}

	/**
	 * @Method명   : updateBbssim
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 3. 
	 * @Method설명 : 기타 댓글상담 상세 내역 UPDATE
	 */
	@Override
	public void updateBbssim(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String loginId = "";		// session ID
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		} else {
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> updateRowMap = new HashMap<String, String>();
		updateRowMap.put("loginId", loginId);
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsBoardList");
		Iterator<ParameterRow> updateRows = paramGroup.getUpdatedRows();
		
		while (updateRows.hasNext()) {
			updateRowMap.putAll(updateRows.next().toMap());
			LOGGER.debug("updateRowMap ::: " + updateRowMap);
			
			bbssimListMapper.updateBbssim(updateRowMap);
		}
	}

	/**
	 * @Method명   : updateEmailSndng
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 5. 19. 
	 * @Method설명 : 기타 댓글상담 메일발송여부 or 이음-e 메일발송일자 UPDATE
	 */
	@Override
	public void updateEmailSndng(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		String loginId = "";			// session ID
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		} else {
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
		}
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dsBoardList");
		List<Map<String, String>> updateRowList = paramGroup.getUpdatedRowList();
		Map<String, String> updateRowMap = updateRowList.get(0);
		
		updateRowMap.put("loginId", loginId);
		
		LOGGER.debug("EumDetailInfo ::: " + updateRowList.get(0));
		
		try {
			bbssimListMapper.updateEmailSndng(updateRowList.get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

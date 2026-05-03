/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.couns.constt.medscsnntabrd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.cache.CacheException;
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
import isry.couns.constt.medscsnntabrd.mapper.BbscttListMapper;
import isry.couns.constt.medscsnntabrd.service.BbscttListService;
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

@Service("bbscttListService")
public class BbscttListServicelmpl implements BbscttListService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "bbscttListMapper")
	private BbscttListMapper bbscttListMapper;
	
	@Resource(name = "counsService")
	private CounsService counsService;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Override
	public List<Map<String, Object>> selectBbscttList(Map<String, Object> mapParam) {
		
		return bbscttListMapper.selectBbscttList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> nonRepSelectBbscttList(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbscttListMapper.nonRepSelectBbscttList(mapParam);
	}
	
	@Override
	public List<Map<String, Object>> repSelectBbscttList(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbscttListMapper.repSelectBbscttList(mapParam);
	}

	@Override
	public int getTotalCount(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbscttListMapper.getTotalCount(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectBbscttDetail(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbscttListMapper.selectBbscttDetail(mapParam);
	}
	
	@Override
	public void updateBbscttTitle(Map<String, Object> mapParam) {
	 bbscttListMapper.updateBbscttTitle(mapParam);
	}

	@Override
	public void bbscttDtlCnt(Map<String, Object> mapParam) {
		
		bbscttListMapper.bbscttDtlCnt(mapParam);
	}

	@Override
	public Map<String, Object> saveBbscttList(HttpServletRequest request, DataRequest dataRequest) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsBoardList = dataRequest.getParameterGroup("dsBoardList");
//		System.out.println("dddddddddddddddddddd"+dsBoardList.toString());
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
			bbscttListMapper.insertBbsctt(mapIns);

			// 게시글 번호 키값 셋팅
			mapReturn.put("BBSCTT_ESNTAL_NO", bbscttListMapper.selectBrdMaxCnt());
//			System.out.println("ssssssssssssssss:"+bbscttListMapper.selectBrdMaxCnt());
		}

		while (updatedRows.hasNext()) {

			Map<String, String> mapUpd = updatedRows.next().toMap();
//			System.out.println("mapUpd ::: " + mapUpd.toString());
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("WRTR_NM_ENCPT", dsBoardList.getValue("WRTR_NM_ENCPT"));
			mapUpd.put("EML_ADDR_ENCPT", dsBoardList.getValue("EML_ADDR_ENCPT"));
			bbscttListMapper.updateBbsctt(mapUpd);
			
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));
//			System.out.println("eeeeeeeeeeeeeeeeeee:"+bbscttListMapper.selectBrdMaxCnt());
		}

		while (deletedRows.hasNext()) {
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbscttListMapper.deleteBbsctt(mapDel);			
//			System.out.println("dddddddddddddddddddd:"+bbscttListMapper.selectBrdMaxCnt());
		}

		return mapReturn;
	}

	@Override
	public List<Map<String, Object>> selectSulmun(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbscttListMapper.selectSulmun(mapParam);
	}

	@Override
	public List<Map<String, Object>> insertCounselor(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbscttListMapper.insertCounselor(mapParam);
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
		
		int crisBrdCnt = bbscttListMapper.selectCrisBrdCnt(mapParam);
		int crisPrsCnt = bbscttListMapper.selectCrisPrsCnt(mapParam);
		
		//위기상담게시판에 게시글 존재여부 확인
		if(crisBrdCnt > 0) {			
			bbscttListMapper.updateCrisisBoard(mapParam); //존재시 update
		}else {
			bbscttListMapper.insertCrisisBoard(mapParam); //미존재시 insert
		}
		
		//위기상담개인에 게시글 존재여부 확인
		if(crisPrsCnt > 0) {
			bbscttListMapper.updateCrisisPerson(mapParam); //존재시 update
		}else {
			bbscttListMapper.insertCrisisPerson(mapParam); //미존재시 insert
		}
		bbscttListMapper.insertCrisis(mapParam);
		
	}

	@Override
	public void updateCase(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		bbscttListMapper.updateCase(mapParam);
	}

	@Override
	public Map<String, Object> saveRespod(HttpServletRequest request, DataRequest dataRequest) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();

		ParameterGroup dsDtlReply = dataRequest.getParameterGroup("dsDtlReply");
//		System.out.println("DACDACADCAC::"+dsDtlReply.toString());
		
//		if(dsDtlReply.getValue("SCHL_VIOLNC_YN") == null || dsDtlReply.getValue("SCHL_VIOLNC_YN").equals("")) {
//			dsDtlReply.setValue(0, "SCHL_VIOLNC_YN", "N");
//		}
//		if(dsDtlReply.getValue("LABOR_YN") == null || dsDtlReply.getValue("LABOR_YN").equals("")) {
//			dsDtlReply.setValue(0, "LABOR_YN", "N");
//		}
//		if(dsDtlReply.getValue("CYBER_VIOLNC_YN") == null || dsDtlReply.getValue("CYBER_VIOLNC_YN").equals("")) {
//			dsDtlReply.setValue(0, "CYBER_VIOLNC_YN", "N");
//		}
		
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
//			System.out.println("1111111111::"+dsDtlReply.toString());
			
			Map<String, String> mapIns = insertedRows.next().toMap();
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			mapIns.put("WRTR_NM_ENCPT", dsDtlReply.getValue("WRTR_NM_ENCPT"));
			bbscttListMapper.insertRespod(mapIns);
			
			Map<String, Object> mapAns = new HashMap<String, Object>();
			mapAns.put("BBSCTT_ESNTAL_NO", dsDtlReply.getValue("BBSCTT_ESNTAL_NO"));
			mapAns.put("ANS_CN", dsDtlReply.getValue("RETE_CN"));
			bbscttListMapper.updateBbscttRespod(mapAns);
			
			// 게시글 번호 키값 셋팅
			mapReturn.put("BBSCTT_ESNTAL_NO", mapIns.get("BBSCTT_ESNTAL_NO"));

		}

		while (updatedRows.hasNext()) {			
//			System.out.println("2222222222::"+dsDtlReply.toString());
			
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("WRTR_NM_ENCPT", dsDtlReply.getValue("WRTR_NM_ENCPT"));
			bbscttListMapper.updateRespod(mapUpd);
//			bbscttListMapper.updateRespod100(mapUpd);
			mapReturn.put("BBSCTT_ESNTAL_NO", mapUpd.get("BBSCTT_ESNTAL_NO"));

		}

		while (deletedRows.hasNext()) {
//			System.out.println("33333333::"+dsDtlReply.toString());
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			bbscttListMapper.deleteRespod(mapDel);			
			
		}

		return mapReturn;
	}

	@Override
	public void RespodDtlCnt(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		bbscttListMapper.RespodDtlCnt(mapParam);
	}

	@Override
	public List<Map<String, Object>> selectRespodDetail(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbscttListMapper.selectRespodDetail(mapParam);
	}

	@Override
	public void insertVoc(Map<String, Object> mapParam) {
		// 조회된 고객의소리 게시판 글이 없을경우
		int vocCnt = bbscttListMapper.selectVocCnt(mapParam);
		if(vocCnt == 0) {			
			bbscttListMapper.insertVoc(mapParam);
		}
	}
	
	@Override
	public List<Map<String, Object>> selectMemo(Map<String, Object> mapParam) {
		// TODO Auto-generated method stub
		return bbscttListMapper.selectMemo(mapParam);
	}

	@Override
	public void insertMemo(Map<String, Object> mapParam) {
		if(bbscttListMapper.selectMemo(mapParam).size() == 0) {			
			bbscttListMapper.insertMemo(mapParam);
		}else {
			bbscttListMapper.updateMemo(mapParam);
		}
	}
	
	@Override
	public void saveCounselor(Map<String, Object> mapParam) {
		if(bbscttListMapper.selectCounselor(mapParam).size() == 0) {			
			bbscttListMapper.insertCounselor2(mapParam);
		}else {
			bbscttListMapper.updateCounselor(mapParam);
		}
	}

	@Override
	public List<Map<String, String>> selectSrvyResultList(Map<String, String> mapParam) {
		
		Map<String, String> indexInfo		= new HashMap<>();
		List<Map<String, String>> dsList	= new ArrayList<Map<String,String>>();
		String bbscttTypeSeCd	=  mapParam.get("BBSCTT_TYPE_SE_CD");
		
		if ("03".equals(bbscttTypeSeCd)) {	// 이음e
			dsList = bbscttListMapper.selectEumSrvyResultList(mapParam);
			
		}else {
			if ("24".equals(bbscttTypeSeCd)) {	// 채팅
				// 설문 색인일련번호 조회_채팅
				indexInfo	=	bbscttListMapper.selectSrvyChttIndexSn(mapParam);
				if (null != indexInfo) {
					mapParam.put("INDEX_SN",	indexInfo.get("INDEX_SN"));
					mapParam.put("TYPE_SE_NM",	indexInfo.get("TYPE_SE_NM"));
				}else {
					mapParam.put("INDEX_SN",	"");
					mapParam.put("TYPE_SE_NM",	"");
				}
			}else {
				// 설문 색인일련번호 조회
				indexInfo	=	bbscttListMapper.selectSrvyIndexSn(mapParam);
				
				if (null != indexInfo) {
					mapParam.put("INDEX_SN",	indexInfo.get("INDEX_SN"));
					mapParam.put("TYPE_SE_NM",	indexInfo.get("TYPE_SE_NM"));
				}else {
					mapParam.put("INDEX_SN",	"");
					mapParam.put("TYPE_SE_NM",	"");
				}	
			}
			
			dsList = bbscttListMapper.selectSrvyResultList(mapParam);
		}
										
		return dsList;
	}

	/**
	 * @Method명   : processSecreNtabrdDtl
	 * @param 	   : request
	 * @param 	   : dataRequest
	 * @throws 	   : Exception
	 * @작성자     : Jeong.Won.Je
	 * @작성일     : 2023. 4. 7. 
	 * @Method설명 : 비밀게시판 게시글 및 답글 Insert/Update/Delete
	 */
	@Override
	public void processSecreNtabrdDtl(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		String loginId = "";		// session ID
		
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			loginId = loginVO.getId();
		} else {
			throw new AppWorksException("세션 정보가 없습니다.", Alert.ERROR);
		}

		// 1. 게시글&답글 데이터셋 가져오기
		ParameterGroup paramBbscttGroup = dataRequest.getParameterGroup("dsBoardList");
		ParameterGroup paramReplyGroup = dataRequest.getParameterGroup("dsDtlReply");
		
		// 2. 게시글 Insert/Update/Delete
		Map<String, String> mapBbscttCRUD = new HashMap<String, String>();
		mapBbscttCRUD.put("loginId", loginId);
		
		Iterator<ParameterRow> insertBbscttRow = paramBbscttGroup.getInsertedRows();
		Iterator<ParameterRow> updateBbscttRow = paramBbscttGroup.getUpdatedRows();
		Iterator<ParameterRow> deleteBbscttRow = paramBbscttGroup.getDeletedRows();
		
		while (insertBbscttRow.hasNext()) {
			
			mapBbscttCRUD.putAll(insertBbscttRow.next().toMap());
			LOGGER.debug("mapBbscttCRUD - Insert ::: " + mapBbscttCRUD);
			// Mapper Insert Method
			bbscttListMapper.insertBbsctt(mapBbscttCRUD);
		}
		
		while (updateBbscttRow.hasNext()) {
			
			mapBbscttCRUD.putAll(updateBbscttRow.next().toMap());
			LOGGER.debug("mapBbscttCRUD - Update ::: " + mapBbscttCRUD);
			// Mapper Update Method
			bbscttListMapper.updateBbsctt(mapBbscttCRUD);
		}
		
		while (deleteBbscttRow.hasNext()) {
			
			mapBbscttCRUD.putAll(deleteBbscttRow.next().toMap());
			LOGGER.debug("mapBbscttCRUD - Delete ::: " + mapBbscttCRUD);
			// Mapper Delete Method
			bbscttListMapper.deleteBbsctt(mapBbscttCRUD);
		}
		
		// 3. 답글 Insert/Update/Delete
		Map<String, String> mapReplyCRUD = new HashMap<String, String>();
		mapReplyCRUD.put("loginId", loginId);
		
		Iterator<ParameterRow> insertReplyRow = paramReplyGroup.getInsertedRows();
		Iterator<ParameterRow> updateReplyRow = paramReplyGroup.getUpdatedRows();
		Iterator<ParameterRow> deleteReplyRow = paramReplyGroup.getDeletedRows();
		
		while (insertReplyRow.hasNext()) {
			mapReplyCRUD.putAll(insertReplyRow.next().toMap());
			LOGGER.debug("mapReplyCRUD - Insert ::: " + mapReplyCRUD);
			// Mapper Insert Method
			if (!"".equals(mapReplyCRUD.get("RETE_TTL_NM")) || !"".equals(mapReplyCRUD.get("RETE_CN"))) {
				int result = bbscttListMapper.insertRespod(mapReplyCRUD);
				
				if (result == 1) {
					// CounsService Method 호출
					counsService.processAnsCmptnAutoSndng(request, dataRequest, mapReplyCRUD);
				}
			}
		}
		
		while (updateReplyRow.hasNext()) {
			mapReplyCRUD.putAll(updateReplyRow.next().toMap());
			LOGGER.debug("mapReplyCRUD - Update ::: " + mapReplyCRUD);
			// Mapper Update Method
			bbscttListMapper.updateRespod(mapReplyCRUD);
		}
		
		while (deleteReplyRow.hasNext()) {
			mapReplyCRUD.putAll(deleteReplyRow.next().toMap());
			LOGGER.debug("mapReplyCRUD - Delete ::: " + mapReplyCRUD);
			// Mapper Delete Method
			bbscttListMapper.deleteRespod(mapReplyCRUD);
		}
		
		LOGGER.debug("mapBbscttCRUD - result ::: " + mapBbscttCRUD);
		LOGGER.debug("mapReplyCRUD - result ::: " + mapReplyCRUD);
		
	}

}
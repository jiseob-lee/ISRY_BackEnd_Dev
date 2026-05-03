/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.casemng.uneart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cleopatra.protocol.data.DataRequest;
import com.cleopatra.protocol.data.ParameterGroup;
import com.cleopatra.protocol.data.ParameterRow;
import com.tomatosystem.exbuilder6.core.constants.Alert;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.casemng.uneart.mapper.EmrgIntrvnMapper;
import isry.itgcm.casemng.uneart.service.EmrgIntrvnService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;


/**
* @Class Name  : EmrgIntrvnServiceImpl.java
* @Description : 긴급개입 ServiceImpl Class
*
* @author  : Kwon.Min.Seo
* @since   : 2022. 06. 14.
* @version : 1.0
* @see
* <pre>
* ------------------------------------------------------------------
* Modification Information
* ------------------------------------------------------------------
* 수정일         수정자          수정내용
* ------------------------------------------------------------------
* 2022. 06. 14.  Kwon.Min.Seo    최초작성
* </pre>
*/
@Service("emrgIntrvnService")
public class EmrgIntrvnServiceImpl extends EgovAbstractServiceImpl implements EmrgIntrvnService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "emrgIntrvnMapper")
	private EmrgIntrvnMapper emrgIntrvnMapper;
	
	@Resource(name = "renuNoMapper")
	private RenuNoMapper renuNoMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	/**
	 * @Method명   : selectRenuNo
	 * @param sessionUserId(세션정보), RenuNoSeCd(채번코드)
	 * @return
	 * @throws Exception 
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 1. 
	 * @Method설명 : 식별번호 채번
	 */
	private String selectRenuNo (String sessionUserId, String RenuNoSeCd) throws Exception {
		String sIdntfcNo = "";
		
		// 식별번호 채번
		Map<String, String> seqMap = new HashMap<>();
		Map<String, Object> valMap = new HashMap<>();			
		
		seqMap.put("USER_ID",       sessionUserId);
		seqMap.put("RENU_NO_SE_CD", RenuNoSeCd);			// 채번코드
		seqMap.put("RENU_YMD",      DateUtil.getToday());	// 현재일자

		// 채번서비스 호출
		valMap   = renuNoMapper.selectCaseMngNoRenu(seqMap);
		
		sIdntfcNo = String.valueOf(valMap.get("RENU_NO"));	// 식별번호 채번
		LOGGER.debug("TrprInqServiceImpl.processTrprInqDetail.sIdntfcNo=[" + sIdntfcNo + "]");	
		
		return sIdntfcNo;
	}	
	
	/**
	 * @Method명   : selectEmrgIntrvnDetail
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 8. 
	 * @Method설명 : 긴급개입 상세화면 조회
	 */
	@Override
	public List<Map<String, Object>> selectEmrgIntrvnDetail(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 긴급개입 정보가 없습니다.", Alert.ERROR);
		}
		LOGGER.debug("EmrgIntrvnServiceImpl.selectEmrgIntrvnDetail.paramGroup=[" + paramGroup + "]");	
		
		Map<String, String> paramMap      = paramGroup.getSingleValueMap();
		
		return emrgIntrvnMapper.selectEmrgIntrvnDetail(paramMap);
	}

	/**
	 * @Method명   : processEmrgIntrvn
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 8. 
	 * @Method설명 : 긴급개입 CRUD
	 */
	@Override
	public Map<String, Object> processEmrgIntrvn(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup processEmrgIntrvn = dataRequest.getParameterGroup("dsEmrgIntrvnDetail");
		
		Iterator<ParameterRow> insertedRows = processEmrgIntrvn.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = processEmrgIntrvn.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = processEmrgIntrvn.getDeletedRows();

		String userId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		int iInsertCnt = 0;
		int iUpdateCnt = 0;
		int iDeleteCnt = 0;
		String sEmrgIntrvnNo = String.valueOf(processEmrgIntrvn.getValue("EMRG_INTRVN_NO")); 					

		Map<String, Object> retMap = new HashMap<>();
		retMap.put("EMRG_INTRVN_NO", sEmrgIntrvnNo);
		
		while (insertedRows.hasNext()) {
			String sts     = "I";
			
			// 긴급개입번호 채번
			if(sEmrgIntrvnNo.isEmpty() || "null".equals(sEmrgIntrvnNo)) sEmrgIntrvnNo = selectRenuNo(userId, "EM");
			LOGGER.debug("긴급개입번호채번=[" + sEmrgIntrvnNo + "]");
			
			Map<String, String> mapIns = insertedRows.next().toMap();
			
			mapIns.put("EMRG_INTRVN_NO" , sEmrgIntrvnNo);			
			mapIns.put("FRST_RGTR_ID"   , userId);
			mapIns.put("LAST_MDFR_ID"   , userId);
			mapIns.put("DATAA_CHG_SE_CD", sts);
			
			emrgIntrvnMapper.insertEmrgIntrvn(mapIns);
			iInsertCnt = emrgIntrvnMapper.insertEmrgIntrvnHistory(mapIns);
			
			if (iInsertCnt > 0) {
				retMap.put("EMRG_INTRVN_NO", mapIns.get("EMRG_INTRVN_NO"));
			}
		}

		while (updatedRows.hasNext()) {
			String sts     = "U";
			
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("DATAA_CHG_SE_CD", sts);
			
			emrgIntrvnMapper.updateEmrgIntrvn(mapUpd);
			iUpdateCnt = emrgIntrvnMapper.insertEmrgIntrvnHistory(mapUpd);
			
			if (iUpdateCnt > 0) {
				retMap.put("EMRG_INTRVN_NO", mapUpd.get("EMRG_INTRVN_NO"));
			}
		}

		while (deletedRows.hasNext()) {
			String sts     = "D";
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			mapDel.put("DATAA_CHG_SE_CD", sts);
			
			emrgIntrvnMapper.deleteEmrgIntrvn(mapDel);
			emrgIntrvnMapper.insertEmrgIntrvnHistory(mapDel);
			
			List<Map<String, Object>> retList = new ArrayList<>();
			retList = emrgIntrvnMapper.selectEmrgIntrvnActnMatter(mapDel);
			
			// 긴급개입조치사항내역 있는경우 함께 삭제처리
			if (retList.size() > 0) {
				for(Map<String, Object> map : retList) {
					Map<String, String> delMap = new HashMap<>();
					for(String keys : map.keySet()) {
						
						delMap.put(keys, String.valueOf(map.get(keys) == null ? "" : map.get(keys)));
						delMap.put("LAST_MDFR_ID", userId);
					}
					
					delMap.put("DATAA_CHG_SE_CD", sts);
					
					emrgIntrvnMapper.deleteEmrgIntrvnActnMatter(delMap);
					emrgIntrvnMapper.insertEmrgIntrvnActnMatterHistory(delMap);
					
					iDeleteCnt++;
				}
				
				if (iDeleteCnt > 0) {
					retMap.put("EMRG_INTRVN_NO", "");
				}
			}
		}
		
		// 긴급개입조치사항 
		if (!sEmrgIntrvnNo.isEmpty()) {
			request.setAttribute("EMRG_INTRVN_NO", String.valueOf(retMap.get("EMRG_INTRVN_NO")));
		} 
		processEmrgIntrvnActnMatter(request, dataRequest);
		
		return retMap;
	}
	
	/**
	 * @Method명   : processEmrgIntrvnActnMatter
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 4. 
	 * @Method설명 : 긴급개입조치사항 CRUD
	 */
	private void processEmrgIntrvnActnMatter(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		ParameterGroup processEmrgIntrvnActnMatter = dataRequest.getParameterGroup("dsEmrgIntrvnActnMatter");
		
		LOGGER.debug("긴급개입조치사항.dataSet=[" + processEmrgIntrvnActnMatter + "]");
		
		Iterator<ParameterRow> insertedRows = processEmrgIntrvnActnMatter.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = processEmrgIntrvnActnMatter.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = processEmrgIntrvnActnMatter.getDeletedRows();

		String userId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		String sEmrgIntrvnNo = String.valueOf(request.getAttribute("EMRG_INTRVN_NO"));					
		Map<String, Object> retMap = new HashMap<>();
		
		while (insertedRows.hasNext()) {
			String sts     = "I";
			int iInsertCnt = 0;
			
			Map<String, String> mapIns = insertedRows.next().toMap();
			
			mapIns.put("EMRG_INTRVN_NO" , sEmrgIntrvnNo);			
			mapIns.put("FRST_RGTR_ID"   , userId);
			mapIns.put("LAST_MDFR_ID"   , userId);
			mapIns.put("DATAA_CHG_SE_CD", sts);
			
			mapIns.put("ACTN_HR", mapIns.get("ACTN_HR").replace(":", ""));
			
			emrgIntrvnMapper.insertEmrgIntrvnActnMatter(mapIns);
			iInsertCnt = emrgIntrvnMapper.insertEmrgIntrvnActnMatterHistory(mapIns);
			
			if (iInsertCnt > 0) retMap.put("EMRG_INTRVN_NO", mapIns.get("EMRG_INTRVN_NO"));
		}

		while (updatedRows.hasNext()) {
			String sts     = "U";
			int iUpdateCnt = 0;
			
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("LAST_MDFR_ID", userId);
			mapUpd.put("DATAA_CHG_SE_CD", sts);
			
			mapUpd.put("ACTN_HR", mapUpd.get("ACTN_HR").replace(":", ""));
			
			emrgIntrvnMapper.updateEmrgIntrvnActnMatter(mapUpd);
			iUpdateCnt = emrgIntrvnMapper.insertEmrgIntrvnActnMatterHistory(mapUpd);
			
			if (iUpdateCnt > 0) retMap.put("EMRG_INTRVN_NO", mapUpd.get("EMRG_INTRVN_NO"));
		}

		while (deletedRows.hasNext()) {
			String sts     = "D";
			int iDeleteCnt = 0;
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			mapDel.put("DATAA_CHG_SE_CD", sts);
			
			emrgIntrvnMapper.deleteEmrgIntrvnActnMatter(mapDel);
			iDeleteCnt = emrgIntrvnMapper.insertEmrgIntrvnActnMatterHistory(mapDel);
			
			if (iDeleteCnt > 0) retMap.put("EMRG_INTRVN_NO", mapDel.get("EMRG_INTRVN_NO"));
		}
	}	

	/**
	 * @Method명   : selectEmrgIntrvnActnMatter
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 4. 
	 * @Method설명 : 긴급개입조치사항 조회
	 */
	@Override
	public List<Map<String, Object>> selectEmrgIntrvnActnMatter(DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup    = dataRequest.getParameterGroup("dmSearch");
		if (paramGroup == null) {
			throw new AppWorksException("조회할 긴급개입조치 정보가 없습니다.", Alert.ERROR);
		}
		
		Map<String, String> paramMap      = paramGroup.getSingleValueMap();
		
		return emrgIntrvnMapper.selectEmrgIntrvnActnMatter(paramMap);
	}
}

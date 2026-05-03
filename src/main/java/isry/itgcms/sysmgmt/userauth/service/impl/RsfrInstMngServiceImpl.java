/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcms.sysmgmt.userauth.service.impl;

import java.util.ArrayList;
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

import isry.base.IsryBaseServiceImpl;
import isry.itgcms.sysmgmt.userauth.mapper.RsfrInstMngMapper;
import isry.itgcms.sysmgmt.userauth.service.MgmtOrgService;
import isry.itgcms.sysmgmt.userauth.service.RsfrInstMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.ScpDb;
import isry.redis.service.RedisService;

/**
 * @파일명        : RsfrInstMngServiceImpl.java
 * @프로그램 설명 : 순수자원제공주체기관 관리 
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2023. 1. 5. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2023. 1. 5.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("rsfrInstMngService")
public class RsfrInstMngServiceImpl extends IsryBaseServiceImpl implements RsfrInstMngService{
	
	@Resource(name="rsfrInstMngMapper")
    private RsfrInstMngMapper rsfrInstMngMapper;
	
	@Resource(name = "mgmtOrgService")
	private MgmtOrgService mgmtOrgService;
	
	@Resource(name="userLoginService")
	private UserLoginService userLoginService;	

	/**
	 * @Method명   : selectRsfrInstMngList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 순수자원제공주체기관 목록
	 */
	@Override
	public List<Map<String, Object>> selectRsfrInstMngList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup parameterGroupDmsearch = dataRequest.getParameterGroup("dmSearch");
			
		Map<String, String> paramMap = parameterGroupDmsearch.getSingleValueMap();
		
		List<Map<String, Object>> retList = new ArrayList<>();
		HttpSession session   = request.getSession();
        UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		/* 
		 * 2023.04.10 Taesoo Song 수정 단위업무 구분 없이 노출 되도록 수정
		 * if(!"U15".equals(loginVO.getUntTaskwk())) { paramMap.put("UNT_TASKWK_SE_CD",
		 * loginVO.getUntTaskwk()); }
		 */
        if ("Y".equals(paramMap.get("OGDP_INST_YN"))) {
        	paramMap.put("OGDP_INST_NO", String.valueOf(loginVO.getInstNo()));
        } else {
        	paramMap.put("OGDP_INST_NO", "");
        }
		retList = rsfrInstMngMapper.selectRsfrInstMngList(paramMap);
		
//		ScpDb scpDb = new ScpDb();
//		for (int idx = 0; idx < retList.size(); idx++) {
//			
//			
//			String sRprsvNmEncpt       = String.valueOf(retList.get(idx).get("RPRSV_NM_ENCPT")); 		/* 대표자명암호화*/
//			if(! "".equals(sRprsvNmEncpt) && ! "null".equals(sRprsvNmEncpt)) {
//				retList.get(idx).put("RPRSV_NM", scpDb.scpDecB64(sRprsvNmEncpt));
//			}
//			String sPicNmEncpt	       = String.valueOf(retList.get(idx).get("PIC_NM_ENCPT"));			/* 담당자명암호화*/
//			if(! "".equals(sPicNmEncpt) && ! "null".equals(sPicNmEncpt)) {
//				retList.get(idx).put("PIC_NM", scpDb.scpDecB64(sPicNmEncpt));
//			}
//			String sPicMblTelnoEncpt = String.valueOf(retList.get(idx).get("PIC_MBL_TELNO_ENCPT"));		/* 담당자휴대전화번호암호화*/
//			if(! "".equals(sPicMblTelnoEncpt) && ! "null".equals(sPicMblTelnoEncpt)) {
//				retList.get(idx).put("PIC_MBL_TELNO", scpDb.scpDecB64(sPicMblTelnoEncpt));
//			}
//			String sPicEmlAddrEncpt    = String.valueOf(retList.get(idx).get("PIC_EML_ADDR_ENCPT"));	/* 담당자이메일주소암호화*/
//			if(! "".equals(sPicEmlAddrEncpt) && ! "null".equals(sPicEmlAddrEncpt)) {
//				retList.get(idx).put("PIC_EML_ADDR", scpDb.scpDecB64(sPicEmlAddrEncpt));
//			}
//		}
		return retList;
	}

	/**
	 * @Method명   : selectRsfrInstDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 5. 
	 * @Method설명 : 순수자원제공주체기관 상세정보/이력
	 */
	@Override
	public Map<String, Object> selectRsfrInstDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		ParameterGroup parameterGroupDmsearch = dataRequest.getParameterGroup("dmSearch");
		
		Map<String, String> paramMap = parameterGroupDmsearch.getSingleValueMap();
		
		ScpDb scpDb = new ScpDb();
		Map<String, Object> retMap = new HashMap<>();
		List<Map<String, Object>> detailList  = new ArrayList<>();
		List<Map<String, Object>> historyList = new ArrayList<>();
		
		detailList  = rsfrInstMngMapper.selectRsfrInstDetail(paramMap);		/* 상세정보*/
		
		retMap.put("detail", detailList);
		
		historyList = rsfrInstMngMapper.selectRsfrInstHistory(paramMap);	/* 이력정보*/
		
		retMap.put("history", historyList);
		
		return retMap;
	}
	
	/**
	 * @Method명   : processRsfrInst
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 9. 
	 * @Method설명 : 순수자원제공주체기관 처리
	 */
	@Override
	public Map<String, Object> processRsfrInst(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup saveSchulwDscntcList = dataRequest.getParameterGroup("dsRsfrInstMngDetail");
		
		log.info("순수자원제공기관.processRsfrInst=[" + saveSchulwDscntcList + "]");
		
		return savesRsfrInst(request, dataRequest);	/* 순수자원제공기관 등록,수정,삭제*/
	}	
	
	/**
	 * @Method명   : savesRsfrInst
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 1. 10. 
	 * @Method설명 : 순수자원제공주체기관 저장/수정/삭제
	 */
	private Map<String, Object> savesRsfrInst(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup saveSchulwDscntcList = dataRequest.getParameterGroup("dsRsfrInstMngDetail");

		Iterator<ParameterRow> insertedRows = saveSchulwDscntcList.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = saveSchulwDscntcList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = saveSchulwDscntcList.getDeletedRows();

		log.info("savesRsfrInst.getRowState=[" + saveSchulwDscntcList.getRowState(0)+ "]");
		
		Map<String, Object> retMap = new HashMap<>();
		
		String sUserId = "";
		String sUntTaskwk = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		sUserId = loginVO.getId();
		sUntTaskwk = loginVO.getUntTaskwk();
		
		while (insertedRows.hasNext()) {
			String sts = "I";
			int iCnt = 0;

			Map<String, String> mapIns = insertedRows.next().toMap();
			
			String sInstNo = String.valueOf(mapIns.get("INST_NO"));
			int iInstNo    = Integer.parseInt(sInstNo);
			
			log.info("기관번호1				=[" + iInstNo+ "]");
			
			Map<String,Object> chkMap = new HashMap<>();
			/* 기관TBL 기관번호 확인*/
			iCnt = rsfrInstMngMapper.selectInstNoExists(iInstNo);
			
			/* 기관번호 있는 경우*/
			int iMaxNextVal = 0;
			int iDuplCnt    = 0;
			if(iCnt > 0) {
				int iChkCnt = 0;
				chkMap = mgmtOrgService.selectMaxInstCd();		     /* 시퀀스 NEXT_VAL  (MAX_INST_CD)*/
				String sMaxIntNo = String.valueOf(chkMap.get("MAX_INST_CD"));
				
				iMaxNextVal = Integer.parseInt(sMaxIntNo);
				
				iChkCnt = rsfrInstMngMapper.selectInstNoExists(iMaxNextVal);
				
				iDuplCnt ++;
				if(iChkCnt > 0) continue;
				
				mapIns.put("INST_NO", String.valueOf(iMaxNextVal));
			}
			
			String sEmdRgnCd = String.valueOf(mapIns.get("EMD_LINK_PBADMS_RGN_CD")); /* 읍면동연계행정지역코드*/
			
			String sRgnCd    = sEmdRgnCd.substring(0, 5) ; 					 
			String sSsgRgnCd = (sEmdRgnCd.substring(0, 5) + "00000"); 			 
			mapIns.put("RGN_CD", sRgnCd);										 	 /* 지역코드*/ 
			mapIns.put("SGG_LINK_PBADMS_RGN_CD", sSsgRgnCd);						 /* 시군구연계행정지역코드*/ 
			
			mapIns.put("APLCNT_ID"      , sUserId); /* 신청자아이디 */
			mapIns.put("FRST_RGTR_ID"   , sUserId); /* 최초등록자아이디 */
			mapIns.put("LAST_MDFR_ID"   , sUserId); /* 최종수정자아이디 */
			mapIns.put("DATAA_CHG_SE_CD", sts); /* 데이터변경구분코드 */

			rsfrInstMngMapper.insertRsfrInst(mapIns);
			rsfrInstMngMapper.insertRsfrInstHistory(mapIns);
			
			/* retrun*/
			retMap.put("INST_NO", mapIns.get("INST_NO"));
			retMap.put("TYPE", sts);	
			
			log.debug("=========== 순수자원제공기관 등록 END : savesRsfrInst ===========");

			log.debug("***************** 처리결과*****************");
			log.debug("*** 중복건수 : " + iDuplCnt);
			log.debug("*** 기관번호 : " + retMap.get("INST_NO"));
			log.debug("*******************************************");			
			
		}
		while (updatedRows.hasNext()) {
			String sts = "U";
			
			Map<String, String> mapUpd = updatedRows.next().toMap();
			
			String sEmdRgnCd = String.valueOf(mapUpd.get("EMD_LINK_PBADMS_RGN_CD")); /* 읍면동연계행정지역코드*/
			
			String sRgnCd    = sEmdRgnCd.substring(0, 5) ; 			 
			String sSsgRgnCd = (sEmdRgnCd.substring(0, 5) + "00000"); 			 
			mapUpd.put("RGN_CD", sRgnCd);										 	 /* 지역코드*/ 
			mapUpd.put("SGG_LINK_PBADMS_RGN_CD", sSsgRgnCd);						 /* 시군구연계행정지역코드*/ 			
			
			mapUpd.put("LAST_MDFR_ID", sUserId);
			mapUpd.put("DATAA_CHG_SE_CD", sts);

			/* 수정이 들어가면 승인상태구분코드는 요청상태로 변경 하도록 변경 2023.04.11 Tae soo Song */
			rsfrInstMngMapper.updateRsfrInst(mapUpd);
			rsfrInstMngMapper.insertRsfrInstHistory(mapUpd);
			
			/* retrun*/
			retMap.put("INST_NO", mapUpd.get("INST_NO"));
			retMap.put("TYPE", sts);	
		}
		while (deletedRows.hasNext()) {
			String sts = "D";

			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", sUserId);
			mapDel.put("DATAA_CHG_SE_CD", sts);

			rsfrInstMngMapper.deleteRsfrInst(mapDel);
			rsfrInstMngMapper.insertRsfrInstHistory(mapDel);
			
			/* retrun*/
			retMap.put("INST_NO", null);			
			retMap.put("TYPE", sts);			
		}		
		
		return retMap;
	}


}

/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.bizcmmns.cmmns.service.impl;

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
import com.cleopatra.protocol.data.RowState;
import com.tomatosystem.exbuilder6.core.exception.AppWorksException;

import egovframework.com.cmm.service.EgovProperties;
import isry.itgcm.bizcmmns.cmmns.mapper.CaseMtgMapper;
import isry.itgcm.bizcmmns.cmmns.mapper.RenuNoMapper;
import isry.itgcm.bizcmmns.cmmns.service.CaseMtgService;
import isry.itgcms.sysmgmt.userauth.service.UserInstAuthService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;
import isry.itgcms.util.DateUtil;
import isry.itgcms.util.StringUtil;

/**
 * @파일명        : CaseMtgServiceImpl.java
 * @프로그램 설명 :
 * - 
 * - 
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 9. 13. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 9. 13.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("caseMtgService")
public class CaseMtgServiceImpl implements CaseMtgService  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);

	@Resource(name="caseMtgMapper")
    private CaseMtgMapper caseMtgMapper;
	
	@Resource(name="renuNoMapper")
    private RenuNoMapper renuNoMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	@Resource(name = "userInstAuthService")
	private UserInstAuthService userInstAuthService;	
	
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
		
		return sIdntfcNo;
	}	

	/**
	 * @Method명   : selectCaseMtgList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 목록 조회
	 */
	@Override
	public List<Map<String, Object>> selectCaseMtgList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		if (paramGroup == null) throw new AppWorksException("조회할 사례대상자 없습니다.");
		LOGGER.debug("selectCaseMtgList.paramGroup      =[" + paramGroup + "]");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
 		String sCaseMtgNo    = String.valueOf(paramMap.get("CASE_MTG_NO"));			// 사례회의번호
		String sCaseMngNo    = String.valueOf(paramMap.get("CASE_MNG_NO"));			// 사례관리번호
		String sCaseMngOdrno = String.valueOf(paramMap.get("CASE_MNG_ODRNO"));		// 사례관리차수번호
		
		LOGGER.debug("selectCaseMtgList.사례회의번호    =[" + sCaseMtgNo + "]");
		LOGGER.debug("selectCaseMtgList.사례관리번호    =[" + sCaseMngNo + "]");
		LOGGER.debug("selectCaseMtgList.사례관리차수번호=[" + sCaseMngOdrno + "]");
		
		List<Map<String, Object>> retList = caseMtgMapper.selectCaseMtgList(paramMap);
		
		return retList;
	}

	/**
	 * @Method명   : selectCaseMtgAtdrnlList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 참석자 조회
	 */
	@Override
	public List<Map<String, Object>> selectCaseMtgAtdrnlList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		if (paramGroup == null) throw new AppWorksException("조회할 사례대상자 없습니다.");
		LOGGER.debug("selectCaseMtgAtdrnlList.paramGroup      =[" + paramGroup + "]");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return caseMtgMapper.selectCaseMtgAtdrnlList(paramMap);
	}

	/**
	 * @Method명   : selectCaseMtgPiclList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 담당자 조회
	 */
	@Override
	public List<Map<String, Object>> selectCaseMtgPiclList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		if (paramGroup == null) throw new AppWorksException("조회할 사례대상자 없습니다.");
		LOGGER.debug("selectCaseMtgPiclList.paramGroup      =[" + paramGroup + "]");
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		return caseMtgMapper.selectCaseMtgPiclList(paramMap);
	}
	
	/**
	 * @Method명   : processCaseMtgList
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 저장, 수정, 삭제
	 */
	@Override
	public void processCaseMtgList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		HttpSession session =  request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		
		String userId = "";
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			request.setAttribute("FRST_RGTR_ID", userId);
			request.setAttribute("LAST_MDFR_ID", userId);
		}
		
		// 사례회의
		saveCaseMtgList(request, dataRequest);

	}
	
	/**
	 * @Method명   : saveCaseMtgList
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의 저장,수정,삭제
	 */
	private void saveCaseMtgList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup saveCaseMtgList = dataRequest.getParameterGroup("dsCaseMtg");
		
		Iterator<ParameterRow> insertedRows = saveCaseMtgList.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = saveCaseMtgList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = saveCaseMtgList.getDeletedRows();
		
	    String sFrstRgtrId = String.valueOf(request.getAttribute("FRST_RGTR_ID"));
	    String sLastMdfrId = String.valueOf(request.getAttribute("LAST_MDFR_ID"));
		
		while (insertedRows.hasNext()) {
			String sts = "I";
			
			Map<String, String> mapIns = insertedRows.next().toMap();
			
		    String RenuNoSeCd    = selectRenuNo (sFrstRgtrId, "MT");				
			
			mapIns.put("CASE_MTG_NO"    , RenuNoSeCd);
			mapIns.put("FRST_RGTR_ID"   , sFrstRgtrId);
			mapIns.put("LAST_MDFR_ID"   , sLastMdfrId);
			mapIns.put("DATAA_CHG_SE_CD", sts);
			
			caseMtgMapper.insertCaseMtg(mapIns);
			caseMtgMapper.insertCaseMtgHistory(mapIns);
		}

		while (updatedRows.hasNext()) {
			String sts = "U";
			
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("FRST_RGTR_ID"   , sFrstRgtrId);
			mapUpd.put("LAST_MDFR_ID"   , sLastMdfrId);
			mapUpd.put("DATAA_CHG_SE_CD", sts);
			
			caseMtgMapper.updateCaseMtg(mapUpd);
			caseMtgMapper.insertCaseMtgHistory(mapUpd);
		}

		while (deletedRows.hasNext()) {
			String sts = "D";
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("DEL_YN"         , "Y");			
			mapDel.put("FRST_RGTR_ID"   , sFrstRgtrId);
			mapDel.put("LAST_MDFR_ID"   , sLastMdfrId);			
			mapDel.put("DATAA_CHG_SE_CD", sts);
			
			caseMtgMapper.deleteCaseMtg(mapDel);
			caseMtgMapper.insertCaseMtgHistory(mapDel);
		}
		
		// 사례회의참석자
		saveCaseMtgAtdrnlList(request, dataRequest);
		// 사례회의담당자
		saveCaseMtgPiclList(request, dataRequest);		
		
	}

	/**
	 * @Method명   : saveCaseMtgAtdrnlList
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 : 사례회의참석자 저장,수정,삭제
	 */
	private void saveCaseMtgAtdrnlList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup saveCaseMtgAtdrnlList = dataRequest.getParameterGroup("dsCaseMtgAtdrn");
		
		Iterator<ParameterRow> insertedRows = saveCaseMtgAtdrnlList.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = saveCaseMtgAtdrnlList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = saveCaseMtgAtdrnlList.getDeletedRows();
		
	    String sFrstRgtrId = String.valueOf(request.getAttribute("FRST_RGTR_ID"));
	    String sLastMdfrId = String.valueOf(request.getAttribute("LAST_MDFR_ID"));
	    
	    String sCaseMtgNo  = String.valueOf(request.getAttribute("CASE_MTG_NO"));
	    LOGGER.debug("사례회의참석자 저장,수정,삭제=[" + sCaseMtgNo +"]");
		
		while (insertedRows.hasNext()) {
			String sts = "I";
			
			Map<String, String> mapIns = insertedRows.next().toMap();
			
//			mapIns.put("CASE_MTG_NO"    , sCaseMtgNo);
			mapIns.put("FRST_RGTR_ID"   , sFrstRgtrId);
			mapIns.put("LAST_MDFR_ID"   , sLastMdfrId);
			mapIns.put("DATAA_CHG_SE_CD", sts);
			
			caseMtgMapper.insertCaseMtgAtdrn(mapIns);
			caseMtgMapper.insertCaseMtgAtdrnHistory(mapIns);
		}

		while (updatedRows.hasNext()) {
			String sts = "U";
			
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("FRST_RGTR_ID"   , sFrstRgtrId);
			mapUpd.put("LAST_MDFR_ID"   , sLastMdfrId);		
			mapUpd.put("DATAA_CHG_SE_CD", sts);
			
			caseMtgMapper.updateCaseMtgAtdrn(mapUpd);
			caseMtgMapper.insertCaseMtgAtdrnHistory(mapUpd);				
		}

		while (deletedRows.hasNext()) {
			String sts = "D";
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("DEL_YN"         , "Y");			
			mapDel.put("FRST_RGTR_ID"   , sFrstRgtrId);
			mapDel.put("LAST_MDFR_ID"   , sLastMdfrId);					
			mapDel.put("DATAA_CHG_SE_CD", sts);
			
			caseMtgMapper.deleteCaseMtgAtdrn(mapDel);
			caseMtgMapper.insertCaseMtgAtdrnHistory(mapDel);	
		}
	}

	/**
	 * @Method명   : saveCaseMtgPiclList
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 9. 13. 
	 * @Method설명 :사례회의담당자 저장,수정,삭제
	 */
	private void saveCaseMtgPiclList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup saveCaseMtgPiclList = dataRequest.getParameterGroup("dsCaseMtgPic");
		
		Iterator<ParameterRow> insertedRows = saveCaseMtgPiclList.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = saveCaseMtgPiclList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = saveCaseMtgPiclList.getDeletedRows();
		
	    String sFrstRgtrId = String.valueOf(request.getAttribute("FRST_RGTR_ID"));
	    String sLastMdfrId = String.valueOf(request.getAttribute("LAST_MDFR_ID"));			
		
	    String sCaseMtgNo  = String.valueOf(request.getAttribute("CASE_MTG_NO"));
	    LOGGER.debug("사례회의담당자 저장,수정,삭제=[" + sCaseMtgNo +"]");	    
	    
		while (insertedRows.hasNext()) {
			String sts = "I";
			
			Map<String, String> mapIns = insertedRows.next().toMap();
			
//			mapIns.put("CASE_MTG_NO"    , sCaseMtgNo);			
			mapIns.put("FRST_RGTR_ID"   , sFrstRgtrId);
			mapIns.put("LAST_MDFR_ID"   , sLastMdfrId);			
			mapIns.put("DATAA_CHG_SE_CD", sts);
			
			caseMtgMapper.insertCaseMtgPic(mapIns);
			caseMtgMapper.insertCaseMtgPicHistory(mapIns);
		}

		while (updatedRows.hasNext()) {
			String sts = "U";
			
			Map<String, String> mapUpd = updatedRows.next().toMap();
			mapUpd.put("FRST_RGTR_ID"   , sFrstRgtrId);
			mapUpd.put("LAST_MDFR_ID"   , sLastMdfrId);				
			mapUpd.put("DATAA_CHG_SE_CD", sts);
			
			caseMtgMapper.updateCaseMtgPic(mapUpd);
			caseMtgMapper.insertCaseMtgPicHistory(mapUpd);				
		}

		while (deletedRows.hasNext()) {
			String sts = "D";
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("DEL_YN"         , "Y");
			mapDel.put("FRST_RGTR_ID"   , sFrstRgtrId);
			mapDel.put("LAST_MDFR_ID"   , sLastMdfrId);			
			mapDel.put("DATAA_CHG_SE_CD", sts);
			
			caseMtgMapper.deleteCaseMtgPic(mapDel);
			caseMtgMapper.insertCaseMtgPicHistory(mapDel);	
		}
	}
	
	

	/**
	 * @Method명   : selectGrCaseMtgList
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 : 집단사례회의 목록
	 */
	@Override
	public List<Map<String, Object>> selectGrCaseMtgList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
			
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		if (paramGroup == null) throw new AppWorksException("조회할 사례대상자 없습니다.");
		LOGGER.debug("selectGrCaseMtgList.paramGroup      =[" + paramGroup + "]");
		HttpSession session   = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);		
		
		Map<String, String> paramMap = paramGroup.getSingleValueMap();
		
		/*20230126_강화영_권한 적용_시작*/
		paramMap.put("UNT_TASKWK_SE_CD", loginVO.getUntTaskwk());		
		Map<String, Object> comMap = userInstAuthService.createInstSrchParams(request, paramMap);
		Map<String, Object> paramMap2 = new HashMap<>();
		paramMap.forEach((StrKey, StrValue) ->{
			paramMap2.put(StrKey, StrValue);
		});
		paramMap2.put("INST_NOS", comMap.get("INST_NOS"));
//		paramMap2.put("checkAll", comMap.get("checkAll"));
		paramMap2.put("ENFSN_NO", loginVO.getEnfsnNo());
		paramMap2.put("GROUP_AUTHRT_SE_CD", loginVO.getGroupAuthrtSeCd());
		/*20230126_강화영_권한 적용_종료*/		
		
		List<Map<String, Object>> retList = caseMtgMapper.selectGrCaseMtgList(paramMap2);
		
		for(int idx = 0; idx < retList.size(); idx++) {
			
			/* 담당자명*/
			String sPicNmEncpt = String.valueOf(retList.get(idx).get("PIC_NM"));
			if(! "".equals(sPicNmEncpt) && ! "null".equals(sPicNmEncpt)) {
				Map<String, String> chkMap = new HashMap<>();
				
				/* 한명일경우 한사람만, 다수일경우 XX외 몇 명으로 표시*/
				chkMap.put("CASE_MTG_NO"   , String.valueOf(retList.get(idx).get("CASE_MTG_NO")));
//				chkMap.put("CASE_MNG_NO"   , String.valueOf(retList.get(idx).get("CASE_MNG_NO")));
//				chkMap.put("CASE_MNG_ODRNO", String.valueOf(retList.get(idx).get("CASE_MNG_ODRNO")));
				List<Map<String, Object>> chkList = caseMtgMapper.selectGrCaseMtgPiclList(chkMap);
				
				if(chkList.size() > 1) {
					String etc = "외 " + String.valueOf((chkList.size() - 1)) + "명";
					
					retList.get(idx).put("PIC_NM", String.valueOf(retList.get(idx).get("PIC_NM")).concat(etc));
				}
			}
		}
		
		return retList;
	}

	/**
	 * @Method명   : selectGrCaseMtgDetail
	 * @param request
	 * @param dataRequest
	 * @return
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 10. 
	 * @Method설명 : 집단사례회의 상세
 	 */
	@Override
	public Map<String, Object> selectGrCaseMtgDetail(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
		ParameterGroup paramGroup = dataRequest.getParameterGroup("dmSearch");
		
		if (paramGroup == null) throw new AppWorksException("조회할 사례회의가 없습니다.");
		LOGGER.debug("selectGrCaseMtgDetail.paramGroup      =[" + paramGroup + "]");
		
		Map<String, Object> retMap = new HashMap<>();
		
		List<Map<String, Object>> caseMtgList      = caseMtgMapper.selectGrCaseMtgDtlList(paramGroup.getSingleValueMap());
		List<Map<String, Object>> caseMtgAtdrnList = caseMtgMapper.selectCaseGrMtgAtdrnlList(paramGroup.getSingleValueMap());
		List<Map<String, Object>> caseMtgPicList   = caseMtgMapper.selectGrCaseMtgPiclList(paramGroup.getSingleValueMap());
		List<Map<String, Object>> caseMtgTrprList  = caseMtgMapper.selectGrCaseMtgTrprList(paramGroup.getSingleValueMap());

		if (caseMtgAtdrnList != null) {
			for (int idx = 0; idx < caseMtgAtdrnList.size(); idx++) {
				String sRmCn = String.valueOf(caseMtgAtdrnList.get(idx).get("RM_CN"));
				if("".equals(sRmCn) && "null".equals(sRmCn)) {
					caseMtgAtdrnList.get(idx).put("RM_CN", "");
				}
			}
		}		
		if (caseMtgPicList != null) {
			for (int idx = 0; idx < caseMtgPicList.size(); idx++) {
				String sRmCn = String.valueOf(caseMtgPicList.get(idx).get("RM_CN"));
				if("".equals(sRmCn) && "null".equals(sRmCn)) {
					caseMtgPicList.get(idx).put("RM_CN", "");
				}				
			}
		}	
		
		retMap.put("caseMtgList"     , caseMtgList);
		retMap.put("caseMtgPicList"  , caseMtgPicList);
		retMap.put("caseMtgAtdrnList", caseMtgAtdrnList);
		retMap.put("caseMtgTrprList" , caseMtgTrprList);
		
		return retMap;
	}
	
	/**
	 * @Method명   : processGrCaseMtgList
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2023. 5. 8. 
	 * @Method설명 : 집단사례회의 저장
	 */
	@Override
	public Map<String,Object> processGrCaseMtgList(HttpServletRequest request, DataRequest dataRequest) throws Exception {

		ParameterGroup paramGroup  = dataRequest.getParameterGroup("dsCaseMtg");
		ParameterGroup paramGroup2 = dataRequest.getParameterGroup("dsCaseTrprList");
		ParameterGroup paramGroup3 = dataRequest.getParameterGroup("dsCaseMtgAtdrn");
		ParameterGroup paramGroup4 = dataRequest.getParameterGroup("dsCaseMtgPic");
		
		if (paramGroup == null) throw new AppWorksException("저장할 사례회의 정보가 없습니다.");
		LOGGER.debug("processGrCaseMtgList.paramGroup        =[" + paramGroup + "]");
		if (paramGroup2 == null) throw new AppWorksException("저장할 대상자 목록이 없습니다.");
		LOGGER.debug("processGrCaseMtgList.paramGroup2       =[" + paramGroup2 + "]");
		
		Map<String, Object> retMap  = new HashMap<>();
		String userId = "";
		String sChkCaseMtgNo = "";	  /* 사례관리번호 체크*/
		String sGetCaseMtgNo = "";    /* 사례회의번호 채번*/
		String sts           = "";	  /* 상태*/
		
		int trprRegCnt = 0;	/* 생성할 사례회의 대상자 건수*/
		
		HttpSession session =  request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
			request.setAttribute("FRST_RGTR_ID", userId);
			request.setAttribute("LAST_MDFR_ID", userId);
		}
		
		/* SEB140 사례회의정보*/
		Map<String, String> saveMap = null;	
		List<Map<String, String>> paramList = paramGroup.getAllRowList();
		saveMap = paramList.get(0);
		
		/* 사례회의번호*/
		sChkCaseMtgNo = String.valueOf(saveMap.get("CASE_MTG_NO"));
		LOGGER.debug("===== processGrCaseMtgList.CASE_MTG_NO(사례회의번호)=[" + sChkCaseMtgNo +"]");
		
		/* 등록*/
		if("".equals(sChkCaseMtgNo) || "null".equals(sChkCaseMtgNo) && sChkCaseMtgNo.length() <= 0) {
			
			/* 사례대상자정보(사례진행중인대상자)*/
			List<Map<String, String>> intList = paramGroup2.getInsertedRowList();
			
			LOGGER.debug("=========== 집단사례회의 등록  START : processGrCaseMtgList ===========");			
			
			sts = "I";
			/* 사례회의번호 채번*/
			sGetCaseMtgNo  = selectRenuNo(userId, "MT");				

			/* 저장 추가데이터*/
			saveMap.put("CASE_MTG_NO"     , sGetCaseMtgNo);	/* 사례회의번호*/
			saveMap.put("FRST_RGTR_ID"    , userId);
			saveMap.put("LAST_MDFR_ID"    , userId);
			saveMap.put("DATAA_CHG_SE_CD" , sts);			
			
			/* 사례회의 등록 대상자 체크*/
			for(int idx = 0; idx < intList.size(); idx++) {
				
				String sCaseMngNo    = String.valueOf(intList.get(idx).get("CASE_MNG_NO"));	     /* 사례관리번호*/
				String sCaseMngOdrno = String.valueOf(intList.get(idx).get("CASE_MNG_ODRNO"));    /* 사례관리번호차수*/
				
				saveMap.put("CASE_MNG_NO"    , sCaseMngNo);
				saveMap.put("CASE_MNG_ODRNO" , sCaseMngOdrno);
				
				/* SEB140 사례회의*/
				caseMtgMapper.insertCaseMtg(saveMap);
				caseMtgMapper.insertCaseMtgHistory(saveMap);	
				
				/* 사례회의참석자*/
				if(paramGroup3.rowSize() > 0) {
					
					for(int i = 0; i < paramGroup3.rowSize(); i++) {
						
						Map<String, String> mapIns = paramGroup3.get(i).toMap();
						
						mapIns.put("CASE_MTG_NO"    , sGetCaseMtgNo);
						mapIns.put("CASE_MNG_NO"    , saveMap.get("CASE_MNG_NO"));
						mapIns.put("CASE_MNG_ODRNO" , saveMap.get("CASE_MNG_ODRNO"));
						mapIns.put("FRST_RGTR_ID"   , saveMap.get("FRST_RGTR_ID"));
						mapIns.put("LAST_MDFR_ID"   , saveMap.get("LAST_MDFR_ID"));
						mapIns.put("DATAA_CHG_SE_CD",sts);
						
						/* SEB160 사례회의참석자*/
						caseMtgMapper.insertCaseMtgAtdrn(mapIns);
						caseMtgMapper.insertCaseMtgAtdrnHistory(mapIns);					
					}
				}
				/* 사례회의담당자*/
				if(paramGroup4.rowSize() > 0) {
					
					for(int i = 0; i < paramGroup4.rowSize(); i++) {
						
						Map<String, String> mapIns = paramGroup4.get(i).toMap();
						
						mapIns.put("CASE_MTG_NO"    , sGetCaseMtgNo);
						mapIns.put("CASE_MNG_NO"    , saveMap.get("CASE_MNG_NO"));
						mapIns.put("CASE_MNG_ODRNO" , saveMap.get("CASE_MNG_ODRNO"));
						mapIns.put("FRST_RGTR_ID"   , saveMap.get("FRST_RGTR_ID"));
						mapIns.put("LAST_MDFR_ID"   , saveMap.get("LAST_MDFR_ID"));
						mapIns.put("DATAA_CHG_SE_CD",sts);
						
						/* SEB170 사례회의담당자*/						
						caseMtgMapper.insertCaseMtgPic(mapIns);
						caseMtgMapper.insertCaseMtgPicHistory(mapIns);						
						
					}
				}
				trprRegCnt++;
			}
			
			retMap.put("CASE_MTG_NO", String.valueOf(saveMap.get("CASE_MTG_NO")));
			
			LOGGER.debug("집단사례회의 생성된 사례회의번호=[" + sGetCaseMtgNo + "]");
			LOGGER.debug("집단사례회의 등록된 사례회의번호=[" + String.valueOf(saveMap.get("CASE_MTG_NO")) + "]");
			LOGGER.debug("집단사례회의 리턴할 사례회의번호=[" + String.valueOf(retMap.get("CASE_MTG_NO")) + "]");
			
			LOGGER.debug("=========== 집단사례회의 등록  END : processGrCaseMtgList ===========");

			LOGGER.debug("***************** 처리결과*****************");
//			LOGGER.debug("*** 조회건수 : " + inqCnt);
			LOGGER.debug("*** 사례회의        등록건수 : " + trprRegCnt);
//			LOGGER.debug("*** 사례회의 참석자 등록건수 : " + caseMtgAtdrnl);
//			LOGGER.debug("*** 사례회의 담당자 등록건수 : " + CaseMtgPic);
//			LOGGER.debug("*** 제외건수 : " + exclCnt);
			LOGGER.debug("******************************************");			
			
			return retMap;
			
		}else {
			LOGGER.debug("=========== 집단사례회의 수정  START : processGrCaseMtgList ===========");		
			
			RowState rowState = null;
			
			saveMap.put("FRST_RGTR_ID"    , userId);
			saveMap.put("LAST_MDFR_ID"    , userId);
			
			/* 사례대상자정보(사례진행중인대상자)*/
			for(int idx = 0; idx < paramGroup2.rowSize(); idx++) {
				
				rowState = paramGroup2.getRowState(idx);
				LOGGER.debug("========== 사례회의대상자.ROWSTATE=[" + rowState + "]");
				LOGGER.debug("==========saveMap=[" + saveMap +"]==========");
					
				saveMap.put("CASE_MNG_NO"      , paramGroup2.get(idx).getValue("CASE_MNG_NO"));
				saveMap.put("CASE_MNG_ODRNO"   , paramGroup2.get(idx).getValue("CASE_MNG_ODRNO"));
				
				RowState caseMtgState = paramGroup.getRowState(0);
				
				retMap.put("CASE_MTG_NO", paramGroup.getValue(0, "CASE_MTG_NO").toString());
				/* 사례회의 수정시*/
				if(RowState.UPDATED == caseMtgState) {
					LOGGER.debug("========== 사례회의.ROW.UPDATE=[" + caseMtgState + "]");
					
					caseMtgMapper.updateCaseMtg(saveMap);
					saveMap.put("FRST_RGTR_ID"    , userId);
					saveMap.put("LAST_MDFR_ID"    , userId);					
					saveMap.put("DATAA_CHG_SE_CD" , "U");
					caseMtgMapper.insertCaseMtgHistory(saveMap);
				}else {
					LOGGER.debug("========== 사례회의.ROW.ETC=[" + caseMtgState + "]");
				}
				
				/* 사례회의번호가 있는경우 사례대상자정보 등록*/
				if(RowState.INSERTED ==  rowState) {
					sts = "I";
					LOGGER.debug("========== 사례회의 사례대상자 신규추가 ==========");
					List<Map<String, Object>> SEB140List = caseMtgMapper.selectSEB140List(saveMap);
					
					saveMap.put("FRST_RGTR_ID"    , userId);
					saveMap.put("LAST_MDFR_ID"    , userId);
					if(SEB140List.size() > 0) {
						LOGGER.debug("==========사례회의있는경우 시작 시작");							
						saveMap.put("DEL_YN"          , "N");
						saveMap.put("DATAA_CHG_SE_CD" , "U");
						
						caseMtgMapper.updateCaseMtg(saveMap);
						caseMtgMapper.insertCaseMtgHistory(saveMap);
						
					}else {
						LOGGER.debug("==========사례회의없는경우 시작 시작");							
						/* SEB140 사례회의*/
						saveMap.put("DEL_YN"          , "N");
						saveMap.put("DATAA_CHG_SE_CD" , sts);						
						caseMtgMapper.insertCaseMtg(saveMap);
						caseMtgMapper.insertCaseMtgHistory(saveMap);
						
						Map<String, String> map = new HashMap<>();
						map.put("CASE_MTG_NO", saveMap.get("CASE_MTG_NO"));
						List<Map<String, Object>> chkSeb160List = caseMtgMapper.selectSEB160List(map);
						if(chkSeb160List.size() > 0) {
							for(Map<String, Object> sKeys : chkSeb160List) {
								Map<String, String> map1 = new HashMap<>();
								for(String keys : sKeys.keySet()) {
									map1.put(keys, StringUtil.isNullToString(sKeys.get(keys)));
								}
								
								if("N".equals(map1.get("DEL_YN"))) {
									map1.put("CASE_MNG_NO"   , saveMap.get("CASE_MNG_NO"));
									map1.put("CASE_MNG_ODRNO", saveMap.get("CASE_MNG_ODRNO"));
									map1.put("FRST_RGTR_ID", userId);
									map1.put("LAST_MDFR_ID", userId);
//									map1.put("DEL_YN", "N");
									caseMtgMapper.insertCaseMtgAtdrn(map1);								
								}
							}
						}
						List<Map<String, Object>> chkSeb170List = caseMtgMapper.selectSEB170List(map);
						if(chkSeb170List.size() > 0) {
							for(Map<String, Object> sKeys : chkSeb170List) {
								Map<String, String> map1 = new HashMap<>();
								for(String keys : sKeys.keySet()) {
									map1.put(keys, StringUtil.isNullToString(sKeys.get(keys)));
								}		
								
								if("N".equals(map1.get("DEL_YN"))) {
									map1.put("CASE_MNG_NO"   , saveMap.get("CASE_MNG_NO"));
									map1.put("CASE_MNG_ODRNO", saveMap.get("CASE_MNG_ODRNO"));
									map1.put("FRST_RGTR_ID", userId);
									map1.put("LAST_MDFR_ID", userId);
//									map1.put("DEL_YN", "N");
									caseMtgMapper.insertCaseMtgPic(map1);							
								}
								
							}
						}
					}
					
					/* 사례회의 참석자*/
					if(paramGroup3.rowSize() > 0) {
						LOGGER.debug("==========사례회의참석자 시작");							
						for(int i = 0; i < paramGroup3.rowSize(); i++) {
							RowState rs3 = paramGroup3.getRowState(i);	
							
							Map<String, String> map1 = paramGroup3.get(i).toMap();
							
							map1.put("CASE_MTG_NO"    , saveMap.get("CASE_MTG_NO"));
							map1.put("CASE_MNG_NO"    , saveMap.get("CASE_MNG_NO"));
							map1.put("CASE_MNG_ODRNO" , saveMap.get("CASE_MNG_ODRNO"));
							map1.put("FRST_RGTR_ID"   , saveMap.get("FRST_RGTR_ID"));
							map1.put("LAST_MDFR_ID"   , saveMap.get("LAST_MDFR_ID"));
							
							List<Map<String, Object>> chkList = caseMtgMapper.selectSEB160List(map1);
							
							/* 등록row*/
							if(RowState.INSERTED == rs3) {
								map1.put("DEL_YN"     , "N");			
								if(chkList.size() > 0) {
									map1.put("DATAA_CHG_SE_CD" , "U");
									caseMtgMapper.updateCaseMtgAtdrn(map1);
								}else {
									map1.put("DATAA_CHG_SE_CD", "I");
									caseMtgMapper.insertCaseMtgAtdrn(map1);
								}
								caseMtgMapper.insertCaseMtgAtdrnHistory(map1);									
							}else if(RowState.UPDATED == rs3) {
								map1.put("DEL_YN"     , "N");			
								if(chkList.size() > 0) {
									map1.put("DATAA_CHG_SE_CD" , "U");
									caseMtgMapper.updateCaseMtgAtdrn(map1);
								}else {
									map1.put("DATAA_CHG_SE_CD", "I");
									caseMtgMapper.insertCaseMtgAtdrn(map1);
								}								
								caseMtgMapper.insertCaseMtgAtdrnHistory(map1);									
							}else if(RowState.DELETED == rs3) {
								map1.put("DEL_YN"     , "Y");			
								if(chkList.size() > 0) {
									map1.put("DATAA_CHG_SE_CD" , "U");
									caseMtgMapper.updateCaseMtgAtdrn(map1);
								}else {
									map1.put("DATAA_CHG_SE_CD", "I");
									caseMtgMapper.insertCaseMtgAtdrn(map1);
								}								
								caseMtgMapper.insertCaseMtgAtdrnHistory(map1);									
							}
//							caseMtgMapper.insertCaseMtgAtdrnHistory(map1);									
						}
					}
					/* 사례회의 담당자*/
					if(paramGroup4.rowSize() > 0) {
						LOGGER.debug("==========사례회의담당자 시작");						
						for(int i = 0; i < paramGroup4.rowSize(); i++) {
							RowState rs4 = paramGroup4.getRowState(i);	
							
							Map<String, String> map1 = paramGroup4.get(i).toMap();
							
							map1.put("CASE_MTG_NO"    , saveMap.get("CASE_MTG_NO"));
							map1.put("CASE_MNG_NO"    , saveMap.get("CASE_MNG_NO"));
							map1.put("CASE_MNG_ODRNO" , saveMap.get("CASE_MNG_ODRNO"));
							map1.put("FRST_RGTR_ID"   , saveMap.get("FRST_RGTR_ID"));
							map1.put("LAST_MDFR_ID"   , saveMap.get("LAST_MDFR_ID"));
							
							List<Map<String, Object>> chkList = caseMtgMapper.selectSEB170List(map1);
							
							/* 등록row*/
							if(RowState.INSERTED == rs4) {
								map1.put("DEL_YN"     , "N");			
								if(chkList.size() > 0) {
									map1.put("DATAA_CHG_SE_CD" , "U");
									caseMtgMapper.updateCaseMtgPic(map1);
								}else {
									map1.put("DATAA_CHG_SE_CD", "I");
									caseMtgMapper.insertCaseMtgPic(map1);
								}
								caseMtgMapper.insertCaseMtgPicHistory(map1);									
							}else if(RowState.UPDATED == rs4) {
								map1.put("DEL_YN"     , "N");			
								if(chkList.size() > 0) {
									map1.put("DATAA_CHG_SE_CD" , "U");
									caseMtgMapper.updateCaseMtgPic(map1);
								}else {
									map1.put("DATAA_CHG_SE_CD", "I");
									caseMtgMapper.insertCaseMtgPic(map1);
								}								
								caseMtgMapper.insertCaseMtgPicHistory(map1);									
							}else if(RowState.DELETED == rs4) {
								map1.put("DEL_YN"     , "Y");			
								if(chkList.size() > 0) {
									map1.put("DATAA_CHG_SE_CD" , "U");
									caseMtgMapper.updateCaseMtgPic(map1);
								}else {
									map1.put("DATAA_CHG_SE_CD", "I");
									caseMtgMapper.insertCaseMtgPic(map1);
								}
								caseMtgMapper.insertCaseMtgPicHistory(map1);									
								
							}
//							caseMtgMapper.insertCaseMtgPicHistory(map1);									
						}
					}
				/* 사례회의 삭제*/	
				}else if(RowState.DELETED ==  rowState) {
					LOGGER.debug("==========사례회의삭제 시작");
					sts = "D";		
					
					/* SEB140 사례회의*/					
					saveMap.put("DATAA_CHG_SE_CD"  , sts);
					caseMtgMapper.deleteCaseMtg(saveMap);	
					saveMap.put("DEL_YN", "Y");					
					caseMtgMapper.insertCaseMtgHistory(saveMap);	

					// @TODO 2023-08-01 주석	
//					List<Map<String, Object>> SEB160List = caseMtgMapper.selectSEB160List(saveMap);
//					List<Map<String, Object>> SEB170List = caseMtgMapper.selectSEB170List(saveMap);					
//					
//					if(SEB160List.size() > 0) {
//						Map<String, String> map1 = new HashMap<>();
//						
//						for(Map<String, Object> map : SEB160List ) {
//							for(String keys : map.keySet()) {
//								map1.put(keys, String.valueOf(map.get(keys)));
//							}
//							map1.put("DATAA_CHG_SE_CD", sts);
//							map1.put("DEL_YN", "Y");
//
////							caseMtgMapper.updateCaseMtgAtdrnYn(map1);
//							/* 사례참석자 삭제*/
//							caseMtgMapper.deleteCaseMtgAtdrn(map1);
//							caseMtgMapper.insertCaseMtgAtdrnHistory(map1);
//						}
//					}
//					if(SEB170List.size() > 0) {
//						Map<String, String> map1 = new HashMap<>();
//						
//						for(Map<String, Object> map : SEB170List ) {
//							for(String keys : map.keySet()) {
//								map1.put(keys, String.valueOf(map.get(keys)));
//							}
//							map1.put("DATAA_CHG_SE_CD", sts);
//							map1.put("DEL_YN", "Y");
//
////							caseMtgMapper.updateCaseMtgPicYn(map1);
//							/* 사례담당자 삭제*/
//							caseMtgMapper.deleteCaseMtgPic(map1);
//							caseMtgMapper.insertCaseMtgPicHistory(map1);
//						}
//					}
				/* 사례회의 수정*/	
				}else if(RowState.UPDATED ==  rowState) {
					LOGGER.debug("==========사례회의수정 시작");					
					sts = "U";
					
					saveMap.put("DATAA_CHG_SE_CD"  , sts);					
				
				/* 없는경우*/
				}else {
					LOGGER.debug("==========사례회의기타 시작");					
					
					
				}
				
				/* 참석자 담당자 로우추가 or 삭제 */
				/* 사례대상자 변경 없는경우*/
				if(RowState.INSERTED != rowState && RowState.DELETED != rowState && RowState.UPDATED != rowState) {
					LOGGER.debug("===========.사례대상자 변경 없는경우*[" + " 참석자 담당자 로우추가 or 삭제 " +"]============");
					
					LOGGER.debug("==========.paramGroup3=[" + paramGroup3.rowSize() + "]==========");
					LOGGER.debug("==========.paramGroup4=[" + paramGroup4.rowSize() + "]==========");
					
					if(paramGroup3.rowSize() > 0) {
						
						RowState rs3 = null;
						
						for(int i = 0; i < paramGroup3.rowSize(); i++) {
							
							rs3 = paramGroup3.get(i).getState();
							
							if(RowState.INSERTED == rs3) {
								Map<String, String> map1 = paramGroup3.get(i).toMap();
								
								map1.put("CASE_MTG_NO"    , saveMap.get("CASE_MTG_NO"));
								map1.put("CASE_MNG_NO"    , saveMap.get("CASE_MNG_NO"));
								map1.put("CASE_MNG_ODRNO" , saveMap.get("CASE_MNG_ODRNO"));
								map1.put("FRST_RGTR_ID", userId);
								map1.put("LAST_MDFR_ID", userId);										
								
								List<Map<String, Object>> chkList = caseMtgMapper.selectSEB160List(map1);
								
								/* 기존등록된 참석자가 있는경우 update*/
								if(chkList.size() > 0) {
									map1.put("DEL_YN"     , "N");		
									map1.put("DATAA_CHG_SE_CD" , "U");
									
									caseMtgMapper.updateCaseMtgAtdrn(map1);
									caseMtgMapper.insertCaseMtgAtdrnHistory(map1);
								/* 기존등록된 참석자가 없는경우*/
								}else {
									map1.put("DEL_YN"     , "N");			
									map1.put("DATAA_CHG_SE_CD", "I");
									
									caseMtgMapper.insertCaseMtgAtdrn(map1);
									caseMtgMapper.insertCaseMtgAtdrnHistory(map1);							
								}
							}else if(RowState.UPDATED == rs3){
								Map<String, String> map1 = paramGroup3.get(i).toMap();
								
								map1.put("CASE_MTG_NO"     , saveMap.get("CASE_MTG_NO"));
								map1.put("CASE_MNG_NO"     , saveMap.get("CASE_MNG_NO"));
								map1.put("CASE_MNG_ODRNO"  , saveMap.get("CASE_MNG_ODRNO"));								
								map1.put("DATAA_CHG_SE_CD" , "U");							
								
								caseMtgMapper.updateCaseMtgAtdrn(map1);
								caseMtgMapper.insertCaseMtgAtdrnHistory(map1);
							}else if(RowState.DELETED == rs3) {
								
								Map<String, String> map1 = paramGroup3.get(i).toMap();
								
								map1.put("CASE_MTG_NO"     , saveMap.get("CASE_MTG_NO"));
								map1.put("CASE_MNG_NO"     , saveMap.get("CASE_MNG_NO"));
								map1.put("CASE_MNG_ODRNO"  , saveMap.get("CASE_MNG_ODRNO"));	
								map1.put("DEL_YN"          , "Y");								
								map1.put("DATAA_CHG_SE_CD" , "D");							
								
								caseMtgMapper.updateCaseMtgAtdrn(map1);
								caseMtgMapper.insertCaseMtgAtdrnHistory(map1);								
							}
						}
					}
					if(paramGroup4.rowSize() > 0) {
						
						RowState rs4 = null;		
						
						for(int i = 0; i < paramGroup4.rowSize(); i++) {
							rs4 = paramGroup4.get(i).getState();
							
							if(RowState.INSERTED == rs4) {
								
								Map<String, String> map1 = paramGroup4.get(i).toMap();		
								
								map1.put("CASE_MTG_NO"    , saveMap.get("CASE_MTG_NO"));
								map1.put("CASE_MNG_NO"    , saveMap.get("CASE_MNG_NO"));
								map1.put("CASE_MNG_ODRNO" , saveMap.get("CASE_MNG_ODRNO"));
								
								List<Map<String, Object>> chkList = caseMtgMapper.selectSEB170List(map1);	
								
								/* 기존등록된 담당자가 있는경우 update*/
								if(chkList.size() > 0) {
									map1.put("DEL_YN"     , "N");		
									map1.put("DATAA_CHG_SE_CD" , "U");
									map1.put("FRST_RGTR_ID", userId);
									map1.put("LAST_MDFR_ID", userId);										
										
									caseMtgMapper.updateCaseMtgPic(map1);
									caseMtgMapper.insertCaseMtgPicHistory(map1);
								/* 기존등록된 담당자가 없는경우*/
								}else {
									map1.put("DEL_YN"     , "N");			
									map1.put("DATAA_CHG_SE_CD", "I");
									map1.put("FRST_RGTR_ID", userId);
									map1.put("LAST_MDFR_ID", userId);		
									
									caseMtgMapper.insertCaseMtgPic(map1);
									caseMtgMapper.insertCaseMtgPicHistory(map1);							
								}								
							}else if(RowState.UPDATED == rs4){
								Map<String, String> map1 = paramGroup4.get(i).toMap();
								
								map1.put("CASE_MTG_NO"     , saveMap.get("CASE_MTG_NO"));
								map1.put("CASE_MNG_NO"     , saveMap.get("CASE_MNG_NO"));
								map1.put("CASE_MNG_ODRNO"  , saveMap.get("CASE_MNG_ODRNO"));								
								map1.put("DATAA_CHG_SE_CD" , "U");							
								
								caseMtgMapper.updateCaseMtgPic(map1);
								caseMtgMapper.insertCaseMtgPicHistory(map1);								
							}else if(RowState.DELETED == rs4) {
								Map<String, String> map1 = paramGroup4.get(i).toMap();
								
								map1.put("CASE_MTG_NO"     , saveMap.get("CASE_MTG_NO"));
								map1.put("CASE_MNG_NO"     , saveMap.get("CASE_MNG_NO"));
								map1.put("CASE_MNG_ODRNO"  , saveMap.get("CASE_MNG_ODRNO"));	
								map1.put("DEL_YN"          , "Y");								
								map1.put("DATAA_CHG_SE_CD" , "D");							
								
								caseMtgMapper.updateCaseMtgPic(map1);
								caseMtgMapper.insertCaseMtgPicHistory(map1);									
							}
						}
					}					
				}
			}
			LOGGER.debug("=========== 집단사례회의 수정  END : processGrCaseMtgList ===========");
			return retMap;
		}
	}

}

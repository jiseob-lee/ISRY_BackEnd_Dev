/******************************************************************************************
 * 본 프로그램 소스는 여성 가족부의 사전 승인 없이 
 * 임의 복제, 복사, 배포 할 수 없습니다. 위반 시 법적 처벌을 받게 됩니다.  
 * Copyright (C) 2021 by MOGEF , All right All right reserved. 
 ******************************************************************************************/
package isry.itgcm.crtrinfo.resrce.service.impl;

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
import isry.itgcm.crtrinfo.resrce.mapper.ProgramExcnMngMapper;
import isry.itgcm.crtrinfo.resrce.service.ProgramExcnMngService;
import isry.itgcms.sysmgmt.userlogin.service.UserLoginService;
import isry.itgcms.sysmgmt.userlogin.vo.UserDetailsVO;

/**
 * @파일명        : ProgramExcnMngServiceImpl.java
 * @프로그램 설명 : 프로그램 실행관리
 * @작성자        : Yoo.Chi.Hoon
 * @작성일        : 2022. 8. 5. 
 * @수정자        : Yoo.Chi.Hoon
 * @수정일        : 2022. 8. 5.
 * @수정내용      : 
 * -                
 * -                
 */
@Service("programExcnMngService")
public class ProgramExcnMngServiceImpl extends EgovAbstractServiceImpl implements ProgramExcnMngService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovProperties.class);
	
	@Resource(name = "programExcnMngMapper")
	private ProgramExcnMngMapper programExcnMngMapper;

	@Resource(name="userLoginService")
	private UserLoginService userLoginService;
	
	// 프로그램 실행관리
	@Override
	public List<Map<String, Object>> selectProgramExcnMngList(HttpServletRequest request, DataRequest dataRequest)
			throws Exception {
		
		ParameterGroup paramDmSearch = dataRequest.getParameterGroup("dmSearch");
		
		if (paramDmSearch == null) {
			throw new AppWorksException("조회할 목록이 없습니다.");
		}
		Map<String, String> paramMap 	  = paramDmSearch.getSingleValueMap();
	
		// 자원제공기간 검색 fromTo Date
		String paramExcnbgngYmd = paramMap.get("EXCN_BGNG_YMD");	// 실행년월시작일자
		String paramExcnendYmd  = paramMap.get("EXCN_END_YMD");	// 실행년월종료일자
		if (paramExcnbgngYmd == null || paramExcnbgngYmd.length() <= 0 && paramExcnendYmd == null || paramExcnendYmd.length() <= 0) {
			throw new AppWorksException("자원제공기간이 입력되지 않았습니다.", Alert.ERROR);
		}
		
		// 검색 서비스실행사업명 조회
		Map<String, String> srvcMap = new HashMap<>();								// 서비스실행사업명 검색결과 담을 Map
		String sSrvcExcnBizNo = String.valueOf(paramMap.get("SRVC_EXCN_BIZ_NO"));	// 서비스실행사업번호
		String sSrvcExcnBizNm = String.valueOf(paramMap.get("SRVC_EXCN_BIZ_NM"));	// 서비스실행사업명
		if (sSrvcExcnBizNm != null && ! sSrvcExcnBizNm.equals("null") && ! sSrvcExcnBizNm.equals("")) {
			srvcMap = programExcnMngMapper.selectSrvcExcnBizList(paramMap);
			
			if(srvcMap != null && srvcMap.size() > 0) {
				String.valueOf(srvcMap.get("SRVC_EXCN_BIZ_NO"));
				paramMap.put("SRVC_EXCN_BIZ_NO", sSrvcExcnBizNo);
				paramMap.put("SRVC_EXCN_BIZ_NM", sSrvcExcnBizNm);
			}else {
				throw new AppWorksException("검색한 실행사업이 확인 되지 않습니다.", Alert.ERROR);
			}
		}
		
		// 검색 자원제공주체 조회
		Map<String, String> instNmMap = new HashMap<>();								
		String sRsfrInstNo = String.valueOf(paramMap.get("RSFR_INST_NO"));													
		String sRsfrInstNm = String.valueOf(paramMap.get("RSFR_INST_NM"));	
		if (sRsfrInstNm != null && ! sRsfrInstNm.equals("null") && ! sRsfrInstNm.equals("")) {
			instNmMap = programExcnMngMapper.selectUnityInstList(paramMap);
			
			if (instNmMap != null && instNmMap.size() > 0) {
				sRsfrInstNo = String.valueOf(instNmMap.get("INST_NO"));
				paramMap.put("RSFR_INST_NO"    , sRsfrInstNo);
			}else {
				throw new AppWorksException("자원제공주체주체가 확인 되지 않습니다.", Alert.ERROR);	
			}
		}	
		
		// 자원기본 목록 조회
		List<Map<String, Object>> resrceList = new ArrayList<>();
		List<Map<String, Object>> progRetList = new ArrayList<>();
		List<Map<String, Object>> programList = new ArrayList<>();
		
		int iFirstIdx = 0;
		int iLastIdx  = programList.size() - 1;
		int iAddCnt   = 0;
		resrceList = programExcnMngMapper.selectResrceBassList(paramMap);
		
		// 자원기본 테이블 return 목록
		if (resrceList == null || resrceList.size() <= 0) {
			throw new AppWorksException("등록된 자원 목록이 없습니다.");
		}
		
		for (int idx = 0; idx < resrceList.size(); idx++) {
			
			String sResrceNoChk 	 = String.valueOf(resrceList.get(idx).get("RESRCE_NO"));
			String sSrvcExcnBizNmChk = String.valueOf(resrceList.get(idx).get("SRVC_EXCN_BIZ_NM"));	// 자원등록시 필수아님
			String sRsfrInst_NmChk 	 = String.valueOf(resrceList.get(idx).get("RSFR_INST_NM"));
			paramMap.put("RESRCE_NO", sResrceNoChk);
			
			progRetList = programExcnMngMapper.selectProgramExcnMngList(paramMap);
			
			if (progRetList != null && progRetList.size() > 0 ) {
				
				for (int index = 0; index < progRetList.size(); index++) {
					iAddCnt ++;
					Map<String, Object> getMap = new HashMap<>();
					
					getMap.put("SRVC_EXCN_BIZ_NM"	, (sSrvcExcnBizNmChk == "null") ? "" : sSrvcExcnBizNmChk);	/* 서비스실행사업명*/
					getMap.put("RSFR_INST_NM"		, sRsfrInst_NmChk);											/* 자원제공주체*/
					getMap.put("PROGRM_NO"			, progRetList.get(index).get("PROGRM_NO"));					/* 프로그램번호*/
					getMap.put("RESRCE_NO"			, progRetList.get(index).get("RESRCE_NO"));					/* 자원번호*/
					getMap.put("PROGRM_TTL_NM"		, progRetList.get(index).get("PROGRM_TTL_NM"));				/* 프로그램이름*/
					getMap.put("PROGRM_BGNG_YMD"	, progRetList.get(index).get("PROGRM_BGNG_YMD"));			/* 프로그램시작일자*/
					getMap.put("PROGRM_END_YMD" 	, progRetList.get(index).get("PROGRM_END_YMD"));			/* 프로그램종료일자*/
					getMap.put("LCTRE_SN"			, String.valueOf(progRetList.get(index).get("LCTRE_SN")));	/* 강의일련번호*/
					getMap.put("LCTRE_BGNG_YMD"		, progRetList.get(index).get("LCTRE_BGNG_YMD"));			/* 강의시작일자*/
					getMap.put("LCTRE_END_YMD"		, progRetList.get(index).get("LCTRE_END_YMD"));				/* 강의종료일자*/
					getMap.put("PROGRM_BGNG_HR"		, progRetList.get(index).get("PROGRM_BGNG_HR"));			/* 프로그램시작시간*/
					getMap.put("PROGRM_END_HR"		, progRetList.get(index).get("PROGRM_END_HR"));				/* 프로그램종료시간*/
					getMap.put("PROGRM_HR"			, progRetList.get(index).get("PROGRM_HR"));					/* 프로그램시작시간 ~ 프로그램종료시간*/
					
					// 추가컬럼
					getMap.put("PROGRM_DSCRP_CN"		, progRetList.get(index).get("PROGRM_DSCRP_CN"));		/* 프로그램내용*/
					
					// first add 
					if (iAddCnt == 1) {
						programList.add(iFirstIdx, getMap);
					// first add after 
					}else {
						programList.add((iLastIdx + 1), getMap);
					}
				}
			}
		}
		
		if (programList == null || programList.isEmpty() || programList.size() < 0) {
			throw new AppWorksException("조회할 프로그램 목록이 없습니다.", Alert.ERROR);
		}
		
//		for(Map<String, Object> rowMap : programList) {
//			
//		LOGGER.debug("**============================================**");
//		LOGGER.debug("1프로그램번호		  =["+ String.valueOf(rowMap.get("PROGRM_NO")) +"]");
//		LOGGER.debug("1자원번호    		  =["+ String.valueOf(rowMap.get("RESRCE_NO")) +"]");
//		LOGGER.debug("1프로그램명    	  =["+ String.valueOf(rowMap.get("PROGRM_TTL_NM")) +"]");		
//		LOGGER.debug("1프로그램시작일자~종료일자 =["+ String.valueOf(rowMap.get("PROGRM_BGNG_YMD") + " ~ " + String.valueOf(rowMap.get("PROGRM_END_YMD") + "]")));		
//		LOGGER.debug("1강의일련번호		  =["+ String.valueOf(rowMap.get("LCTRE_SN")) +"]");
//		LOGGER.debug("1강의시작일자		  =["+ String.valueOf(rowMap.get("LCTRE_BGNG_YMD")) +"]");
//		LOGGER.debug("1강의종료일자		  =["+ String.valueOf(rowMap.get("LCTRE_END_YMD")) +"]");
//		LOGGER.debug("1프로그램시작시간		  =["+ String.valueOf(rowMap.get("PROGRM_BGNG_HR")) +"]");
//		LOGGER.debug("1프로그램종료시간		  =["+ String.valueOf(rowMap.get("PROGRM_END_HR")) +"]");
//		LOGGER.debug("**============================================**");
//		}
				
		// 프로그램 목록 + 검색기간 
		List<Map<String, Object>> prdList 		 = new ArrayList<>();		// 자원제공시작일자 return List
		List<Map<String, Object>> programPrdList = new ArrayList<>();

		int iFirstIndex = 0;
		int iLastIndex  = programPrdList.size() - 1;
		int iAddCount   = 0;
		// 일자 사이즈에 각 프로그램 리스트 한개씩 삽입
		for (int idx = 0; idx < programList.size(); idx++) {
			Map<String, Object> getMap = new HashMap<>();
			// 프로그램 1개 get 
		    getMap.put("EXCN_BGNG_YMD"		, paramMap.get("EXCN_BGNG_YMD"));							/* 실행년월시작일자*/
		    getMap.put("EXCN_END_YMD"		, paramMap.get("EXCN_END_YMD"));							/* 실행년월종료일자*/
			getMap.put("SRVC_EXCN_BIZ_NM"	, programList.get(idx).get("SRVC_EXCN_BIZ_NM"));			/* 서비스실행사업명*/
			getMap.put("RSFR_INST_NM"		, programList.get(idx).get("RSFR_INST_NM"));				/* 자원제공주체*/
			getMap.put("PROGRM_NO"			, programList.get(idx).get("PROGRM_NO"));					/* 프로그램번호*/
			getMap.put("RESRCE_NO"			, programList.get(idx).get("RESRCE_NO"));					/* 자원번호*/
			getMap.put("PROGRM_TTL_NM"		, programList.get(idx).get("PROGRM_TTL_NM"));				/* 프로그램이름*/
			getMap.put("PROGRM_BGNG_YMD"	, programList.get(idx).get("PROGRM_BGNG_YMD"));				/* 프로그램시작일자*/
			getMap.put("PROGRM_END_YMD" 	, programList.get(idx).get("PROGRM_END_YMD"));				/* 프로그램종료일자*/
			getMap.put("LCTRE_SN"			, programList.get(idx).get("LCTRE_SN"));					/* 강의일련번호*/
			getMap.put("LCTRE_BGNG_YMD"		, programList.get(idx).get("LCTRE_BGNG_YMD"));				/* 강의시작일자*/
			getMap.put("LCTRE_END_YMD"		, programList.get(idx).get("LCTRE_END_YMD"));				/* 강의종료일자*/
			getMap.put("PROGRM_BGNG_HR"		, programList.get(idx).get("PROGRM_BGNG_HR"));				/* 프로그램시작시간*/
			getMap.put("PROGRM_END_HR"		, programList.get(idx).get("PROGRM_END_HR"));				/* 프로그램종료시간*/
			getMap.put("PROGRM_HR"			, programList.get(idx).get("PROGRM_HR"));					/* 프로그램시작시간 ~ 프로그램종료시간*/
			getMap.put("INSTR_ENFSN_NM"		, programList.get(idx).get("INSTR_ENFSN_NM"));				/* 강사종사자성명*/
			getMap.put("PROGRM_DSCRP_CN"	, programList.get(idx).get("PROGRM_DSCRP_CN"));				/* 프로그램내용*/
//			getMap.put("LCTRE_YMD"			, programList.get(idx).get("LCTRE_YMD"));					/* 강의일자*/
//			getMap.put("LCTRE_HR"			, programList.get(idx).get("LCTRE_HR"));					/* 강의시간*/
			
			prdList = programExcnMngMapper.selectResrcePvsnPrdList(getMap);
			
			if (prdList != null && prdList.size() > 0) {
				
				for (int index = 0; index < prdList.size(); index++) {
					iAddCount++;
					
					if (iAddCount == 1) {
						programPrdList.add(iFirstIndex, prdList.get(index));
					}else {
						programPrdList.add((iLastIndex + 1), prdList.get(index));
					}
				}
			}
		}
		
//		for(Map<String, Object> rowMap : programPrdList) {
//		
//		LOGGER.debug("1============================================1");
//		LOGGER.debug("1프로그램번호		  =["+ String.valueOf(rowMap.get("PROGRM_NO")) +"]");
//		LOGGER.debug("1자원번호    		  =["+ String.valueOf(rowMap.get("RESRCE_NO")) +"]");
//		LOGGER.debug("1프로그램명    	  =["+ String.valueOf(rowMap.get("PROGRM_TTL_NM")) +"]");		
//		LOGGER.debug("1프로그램시작일자~종료일자 =["+ String.valueOf(rowMap.get("LCTRE_BGNG_YMD") + " ~ " + String.valueOf(rowMap.get("LCTRE_END_YMD") + "]")));		
//		LOGGER.debug("1강의일련번호		  =["+ String.valueOf(rowMap.get("LCTRE_SN")) +"]");
//		LOGGER.debug("1일별일자            =["+ String.valueOf(rowMap.get("DATETS")) +"]");
//		LOGGER.debug("1============================================1");
//		}
		
		if (programPrdList == null || programPrdList.isEmpty() || programPrdList.size() < 0) {
			throw new AppWorksException("조회할 프로그램 내역이 없습니다.", Alert.ERROR);
		}
		
		// 자원프로그램실행시간
		List<Map<String, Object>> excnHrList 	 = new ArrayList<>();	
		
		for (int idx = 0; idx < programPrdList.size(); idx++) {

			Map<String, Object> getMap = new HashMap<>();
			
			getMap.put("PROGRM_NO", programPrdList.get(idx).get("PROGRM_NO"));
			getMap.put("RESRCE_NO", programPrdList.get(idx).get("RESRCE_NO"));
			getMap.put("LCTRE_SN" , programPrdList.get(idx).get("LCTRE_SN"));
			
			excnHrList = programExcnMngMapper.selectProgramExcnHrList(getMap);
			
			if (excnHrList != null && excnHrList.size() > 0) {
				
				for (int index = 0; index < excnHrList.size(); index++) {
					
					// 자원번호, 프로그램번호, 강의일련번호 같은경우
					String sProgrmNo = String.valueOf(programPrdList.get(idx).get("PROGRM_NO"));
					String sResrceNo = String.valueOf(programPrdList.get(idx).get("RESRCE_NO"));
					String sLctreSn  = String.valueOf(programPrdList.get(idx).get("LCTRE_SN"));
					String sDatets   = String.valueOf(programPrdList.get(idx).get("DATETS"));
					
					// 실행시간 프로그램번호, 자원번호, 강의일련번호
					String sExProgrmNo  = String.valueOf(excnHrList.get(index).get("PROGRM_NO"));
					String sExResrceNo  = String.valueOf(excnHrList.get(index).get("RESRCE_NO"));
					String sExLctreSn   = String.valueOf(excnHrList.get(index).get("LCTRE_SN"));
					
					// 강의일자, 강의시간
					String sExLctreYmd  = String.valueOf(excnHrList.get(index).get("LCTRE_YMD"));
					String sExLctreHr   = String.valueOf(excnHrList.get(index).get("LCTRE_HR"));
					
					// 프로그램 자원번호, 프로그램 체크
					if (sProgrmNo.equals(sExProgrmNo) && sResrceNo.equals(sExResrceNo) && sLctreSn.equals(sExLctreSn) && sDatets.equals(sExLctreYmd)) {
						LOGGER.debug("============================================");
						LOGGER.debug("프로그램번호		  =["+ sProgrmNo +"]");
						LOGGER.debug("자원번호    		  =["+ sResrceNo +"]");
						LOGGER.debug("강의일련번호		  =["+ sLctreSn +"]");
						LOGGER.debug("일별일자            =["+ sDatets +"]");
						LOGGER.debug("실행시간프로그램번호=["+ sExProgrmNo +"]");
						LOGGER.debug("실행시간자원번호    =["+ sExResrceNo +"]");
						LOGGER.debug("실행강의일련번호    =["+ sExLctreSn +"]");
						LOGGER.debug("실행강의일자        =["+ sExLctreYmd +"]");
						LOGGER.debug("실행시간			  =["+ sExLctreHr +"]");
						LOGGER.debug("============================================");

						programPrdList.get(idx).put("LCTRE_YMD", sExLctreYmd);
						programPrdList.get(idx).put("LCTRE_HR" , Float.parseFloat(sExLctreHr));
						programPrdList.get(idx).put("ROWINDEX", idx);
						
					}else if(sProgrmNo.equals(sExProgrmNo) && sResrceNo.equals(sExResrceNo) && sLctreSn.equals(sExLctreSn) && ! sDatets.equals(sExLctreYmd)) {
						programPrdList.get(idx).put("LCTRE_YMD", sDatets);
						programPrdList.get(idx).put("ROWINDEX", idx);
					}
				}
			}
		}
		
//		for(Map<String, Object> rowMap : programPrdList) {
//			
//			LOGGER.debug("2============================================2");
//			LOGGER.debug("프로그램번호		  =["+ String.valueOf(rowMap.get("PROGRM_NO")) +"]");
//			LOGGER.debug("2자원번호    		  =["+ String.valueOf(rowMap.get("RESRCE_NO")) +"]");
//			LOGGER.debug("2프로그램명    	  =["+ String.valueOf(rowMap.get("PROGRM_TTL_NM")) +"]");
//			LOGGER.debug("2강의일련번호		  =["+ String.valueOf(rowMap.get("LCTRE_SN")) +"]");
//			LOGGER.debug("2일별일자           =["+ String.valueOf(rowMap.get("DATETS")) +"]");
//			LOGGER.debug("2실행강의일자       =["+ String.valueOf(rowMap.get("LCTRE_YMD")) +"]");
//			LOGGER.debug("2실행시간			  =["+ String.valueOf(rowMap.get("LCTRE_HR")) +"]");
//			LOGGER.debug("2============================================2");
//		}
		
		List<Map<String, Object>> retList = new ArrayList<>();
		retList = programPrdList;
		
//		return programPrdList;
		return retList;
	}
	
	
	/**
	 * @Method명   : saveResrceProgrmExcnHrList
	 * @param request
	 * @param dataRequest
	 * @throws Exception
	 * @작성자     : Yoo.Chi.Hoon
	 * @작성일     : 2022. 8. 16. 
	 * @Method설명 : 프로그림실행시간 저장
	 */
	@Override
	public void saveResrceProgrmExcnHrList(HttpServletRequest request, DataRequest dataRequest) throws Exception {
		
//		ParameterGroup saveResrceProgrmExcnHrList = dataRequest.getParameterGroup("dsSaveProgramExcnMng");
		ParameterGroup saveResrceProgrmExcnHrList = dataRequest.getParameterGroup("dsProgramExcnMng");
		
		if (saveResrceProgrmExcnHrList == null) {
			
			throw new AppWorksException("수정한 프로그램실행시간이 없습니다.");
		}
		
		LOGGER.debug("saveResrceProgrmExcnHrList=[" + saveResrceProgrmExcnHrList + "]");

		Iterator<ParameterRow> insertedRows = saveResrceProgrmExcnHrList.getInsertedRows();
		Iterator<ParameterRow> updatedRows  = saveResrceProgrmExcnHrList.getUpdatedRows();
		Iterator<ParameterRow> deletedRows  = saveResrceProgrmExcnHrList.getDeletedRows();

		String userId = "";
		HttpSession session = request.getSession();
		UserDetailsVO loginVO = userLoginService.getLoginSessionVO(request);
		if (loginVO != null && loginVO.getId() != null && !"".equals(loginVO.getId())) {
			userId = loginVO.getId();
		}
		
		while (insertedRows.hasNext()) {
			String sts = "I";
			LOGGER.debug("DATAA_CHG_SE_CD.insert=[" + sts + "]");
			
			Map<String, String> mapIns = insertedRows.next().toMap();
			
			mapIns.put("FRST_RGTR_ID", userId);
			mapIns.put("LAST_MDFR_ID", userId);
			mapIns.put("DATAA_CHG_SE_CD", sts);
			
			// 자원프로그램실행시간(SDA320) insert
			programExcnMngMapper.insertResrceProgrmExcnHr(mapIns);
		}
		
		int chkCnt = 0;
		while (updatedRows.hasNext()) {
			String sts = "U";
			LOGGER.debug("DATAA_CHG_SE_CD.update=[" + sts + "]");
			
			Map<String, String> mapUpd = updatedRows.next().toMap();
			
			chkCnt = programExcnMngMapper.selectProgramExcnHrCnt(mapUpd);
			
			if (chkCnt == 0) {
				
				// 등록시 등록 않된경우 프로그램실행시간 (SDA320) 테이블에 등록 되어있지 않음
				String sDatets = String.valueOf(mapUpd.get("DATETS"));
				//LOGGER.debug("saveResrceProgrmExcnHrList.DATETS=[" + sDatets + "]");
				
				mapUpd.put("LCTRE_YMD", sDatets);
				mapUpd.put("FRST_RGTR_ID", userId);
				mapUpd.put("LAST_MDFR_ID", userId);
				mapUpd.put("DATAA_CHG_SE_CD", "I");
				
				// 자원프로그램실행시간(SDA320) insert
				programExcnMngMapper.insertResrceProgrmExcnHr(mapUpd);				
				
			}else if (chkCnt >= 1) {
				
				mapUpd.put("LAST_MDFR_ID", userId);
				mapUpd.put("DATAA_CHG_SE_CD", sts);
				
				programExcnMngMapper.updateResrceProgrmExcnHr(mapUpd);
			}
		}

		while (deletedRows.hasNext()) {
			String sts = "D";
			LOGGER.debug("DATAA_CHG_SE_CD.delete=[" + sts + "]");
			
			Map<String, String> mapDel = deletedRows.next().toMap();
			mapDel.put("LAST_MDFR_ID", userId);
			mapDel.put("DATAA_CHG_SE_CD", sts);
			
//			programExcnMngMapper.deleteResrceProgrmExcnHr(mapDel);
		}
		
	}


	
	
}
